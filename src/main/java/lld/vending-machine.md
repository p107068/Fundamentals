# Vending Machine — Low-Level Design

![Vending machine class diagram](resource/vending-machine.png)

## Revision Snapshot

| Lens | Recall |
| --- | --- |
| Core model | Machine state + inventory reservation + payment session + dispenser |
| Design leverage | State models allowed actions; Strategy selects payment behavior; inventory owns stock |
| Hard problem | Payment, stock, and physical dispense are separate failure boundaries; make uncertainty recoverable |

## 1. Problem Statement

Design a vending machine that:

* Stores multiple products.
* Maintains product quantity.
* Accepts different types of coins.
* Supports cash and UPI payments.
* Allows product selection.
* Dispenses products.
* Handles cancellation/refund.
* Supports multiple machine states.
* Allows new payment methods and states to be added without modifying the core machine.

The design primarily uses:

* **State Design Pattern** → vending-machine behavior
* **Strategy Design Pattern** → payment methods
* **Composition** → machine owns inventory/payment/coin management
* **Encapsulation** → inventory and payment operations are isolated behind dedicated classes.

---

## 2. High-Level Architecture

```text
                    ┌──────────────────┐
                    │  VendingMachine  │
                    └────────┬─────────┘
                             │
          ┌──────────────────┼──────────────────┐
          │                  │                  │
          ▼                  ▼                  ▼
 ProductInventory      PaymentProcessor     CoinManager
          │                  │
          ▼                  ▼
      ItemShelf       PaymentStrategy
                            │
                     ┌──────┴──────┐
                     ▼             ▼
                CashPayment    UPIPayment


                    VendingMachine
                          │
                          ▼
                VendingMachineState
                          │
        ┌─────────────────┼─────────────────┐
        ▼                 ▼                 ▼
    IdleState      ReceiveMoneyState   SelectProductState
                                              │
                                              ▼
                                      DispenseProductState
```

The important architectural decision is that **VendingMachine does not contain state-specific business logic**.

Instead:

```java
machine.getState().receiveMoney(...);
machine.getState().selectProduct(...);
machine.getState().cancelTransaction(...);
```

The current state decides what operation is valid and what the next state should be.

---

## 3. Core Classes

### VendingMachine

The **Context** of the State pattern.

```java
class VendingMachine {

    private VendingMachineState state;
    private ProductInventory productInventory;
    private PaymentProcessor paymentProcessor;
    private CoinManager coinManager;

    public void updateInventory() {}
    public void updateState(VendingMachineState state) {}

    public VendingMachineState getState() {}
    public ProductInventory getProductInventory() {}
    public PaymentProcessor getPaymentProcessor() {}
    public CoinManager getCoinManager() {}
}
```

#### Responsibilities

* Maintain the current state.
* Provide access to inventory.
* Provide access to payment processing.
* Provide access to coin management.
* Coordinate state transitions.

#### Important

`VendingMachine` should **not** contain:

```java
if (state == IDLE) ...
else if (state == HAS_MONEY) ...
```

That would defeat the purpose of the State pattern.

---

## 4. State Design Pattern

### Why State Pattern?

A vending machine behaves differently depending on its current state.

For example:

#### Idle

```text
receiveMoney()       → allowed
selectProduct()      → not allowed
dispenseProduct()    → not allowed
cancelTransaction()  → not meaningful
```

#### Receive Money

```text
receiveMoney()       → add money
selectProduct()      → potentially allowed depending on design
cancelTransaction()  → refund
```

#### Select Product

```text
selectProduct()      → validate selection
cancelTransaction()  → refund
```

#### Dispense Product

```text
dispenseProduct()    → dispense item
```

If everything were implemented inside `VendingMachine`, we would get a large conditional structure.

Instead:

```text
VendingMachine
      │
      ▼
VendingMachineState
      │
      ├── IdleState
      ├── ReceiveMoneyState
      ├── SelectProductState
      └── DispenseProductState
```

This makes adding another state much easier.

---

## 5. VendingMachineState

The common state abstraction.

```java
interface VendingMachineState {

    void receiveMoney(List<Coin> coins);

    void addMoney(List<Coin> coins);

    void cancelTransaction(VendingMachine machine);

    void selectProduct(List<ItemShelf> items);

    void dispenseProduct(List<ItemShelf> items);

    void refund();
}
```

Every state implements the same interface.

#### Why common interface?

The `VendingMachine` only needs to know:

```java
VendingMachineState state;
```

It does not need to know whether the state is:

```text
IdleState
ReceiveMoneyState
SelectProductState
DispenseProductState
```

This gives us polymorphic behavior.

---

## 6. State Lifecycle

The important state transition is:

```text
                 receiveMoney
                     │
                     ▼
                ReceiveMoney
                     │
                selectProduct
                     │
                     ▼
              SelectProduct
                     │
              dispenseProduct
                     │
                     ▼
            DispenseProduct
                     │
                     ▼
                   Idle
```

Cancellation can happen during a transaction:

```text
ReceiveMoney ────── cancel ──────► Idle
SelectProduct ───── cancel ──────► Idle
```

Refund:

```text
transaction
     │
     ▼
refund()
     │
     ▼
return money
     │
     ▼
IdleState
```

---

## 7. IdleState

Initial state of the machine.

```java
class IdleState implements VendingMachineState {

    public void receiveMoney(List<Coin> coins) {
        // Accept money
        // Move to ReceiveMoneyState
    }

    public void addMoney(List<Coin> coins) {}

    public void cancelTransaction(VendingMachine machine) {}

    public void selectProduct(List<ItemShelf> items) {
        // Reject
    }

    public void dispenseProduct(List<ItemShelf> items) {
        // Reject
    }

    public void refund() {}
}
```

#### Responsibility

Accept the beginning of a transaction.

```text
IDLE
  │
  │ insert money
  ▼
RECEIVE MONEY
```

---

## 8. ReceiveMoneyState

Machine has received money.

Responsibilities:

* Track/add inserted money.
* Allow cancellation.
* Allow product selection.
* Transition to product-selection state.

```text
ReceiveMoneyState
        │
        ├── add money
        │
        ├── cancel
        │
        └── select product
                  │
                  ▼
          SelectProductState
```

---

## 9. SelectProductState

Responsible for selecting and validating products.

Typical validation:

```text
Product exists?
      │
      ├── NO → reject
      │
      ▼
Quantity > 0?
      │
      ├── NO → OUT OF STOCK
      │
      ▼
Payment sufficient?
      │
      ├── NO → insufficient funds
      │
      ▼
Product valid
      │
      ▼
DispenseProductState
```

This state should not itself own inventory.

Instead:

```java
machine.getProductInventory()
```

is used.

---

## 10. DispenseProductState

Responsible for the actual dispensing operation.

Typical flow:

```text
DispenseProductState
        │
        ▼
Fetch selected ItemShelf
        │
        ▼
Check quantity
        │
        ▼
Remove item
        │
        ▼
Update inventory
        │
        ▼
Calculate change
        │
        ▼
Return change
        │
        ▼
IdleState
```

After successful completion:

```text
DispenseProductState → IdleState
```

This is important because a completed transaction must reset the machine.

---

## 11. ProductInventory

Responsible for managing all product shelves.

```java
class ProductInventory {

    private Map<String, ItemShelf> itemShelves;
    private List<ItemShelf> shelves;

    public void addShelf(ItemShelf shelf) {}

    public void removeShelf(ItemShelf shelf) {}

    public ItemShelf getShelf(String id) {}

    public List<ItemShelf> getAllShelves() {}
}
```

#### Why separate Inventory?

Do not put this inside `VendingMachine`:

```java
Map<String, ItemShelf> shelves;
```

along with all inventory logic.

Instead:

```text
VendingMachine
      │
      ▼
ProductInventory
      │
      ▼
ItemShelf
```

This gives us a clean **Single Responsibility** boundary.

---

## 12. ItemShelf

Represents a physical/product slot.

```java
class ItemShelf {

    private int id;
    private ItemType type;
    private int price;
    private int quantity;

    public void addItem(int id) {}

    public void addItem(int id, int price) {}

    public void removeItem(int id) {}

    public int getPrice(int id) {}

    public int getQuantity(int id) {}

    public ItemType getItemType() {}
}
```

Example:

```text
Shelf A1
────────────
Product: Coke
Price:   ₹40
Quantity: 5
```

The shelf abstraction is useful because the machine interacts with **slots**, rather than directly managing individual physical products.

---

## 13. ItemType

An enum representing product types.

```java
enum ItemType {
    COKE,
    PEPSI,
    SODA,
    WATER,
    CHIPS,
    CHOCOLATE,
    CANDY
}
```

Using an enum prevents arbitrary strings from representing product types.

Instead of:

```java
"Coke"
```

we use:

```java
ItemType.COKE
```

---

## 14. Coin

Represents a coin.

```java
class Coin {

    private int id;
    private int value;

    public int getId() {}
    public int getValue() {}
}
```

Example:

```text
Coin
 ├── id
 └── value
```

The `id` can represent a physical coin instance, while `value` represents its denomination.

---

## 15. CoinType

Useful abstraction for supported denominations.

```java
enum CoinType {
    QUARTER(25),
    DIME(10),
    NICKEL(5),
    PENNY(1);

    private int value;
}
```

For an Indian implementation, the enum could instead be:

```java
enum CoinType {
    ONE(1),
    TWO(2),
    FIVE(5),
    TEN(10),
    TWENTY(20);
}
```

The important idea is that **supported denominations are configurable**.

---

## 16. CoinManager

Responsible for supported coins and coin-related operations.

```java
class CoinManager {

    private Map<CoinType, Integer> coins;

    public void addSupportedCoinType(CoinType type) {}

    public void removeSupportedCoinType(CoinType type) {}

    public Set<CoinType> getSupportedCoins() {}

    public boolean isSupported(CoinType type) {}
}
```

#### Why separate CoinManager?

It prevents coin-handling logic from leaking into:

```text
VendingMachine
PaymentProcessor
Inventory
States
```

This becomes particularly useful for:

* Change calculation
* Supported denominations
* Coin validation
* Coin availability
* Future cash-management logic

---

## 17. PaymentProcessor

Responsible for payment processing.

```java
class PaymentProcessor {

    private List<PaymentStrategy> strategies;
    private PaymentStatus status;

    public void addStrategy(PaymentStrategy strategy) {}

    public void removeStrategy(PaymentStrategy strategy) {}

    public boolean pay(PaymentRequest request) {}

    public void updateStatus(PaymentStatus status) {}

    public PaymentStatus getStatus() {}
}
```

The important design decision:

> `PaymentProcessor` does not know how every payment method works.

It delegates to a `PaymentStrategy`.

---

## 18. Strategy Design Pattern

```text
                 PaymentStrategy
                       ▲
                       │
              ┌────────┴────────┐
              │                 │
         CashPayment        UPIPayment
```

Interface:

```java
interface PaymentStrategy {

    boolean pay(PaymentRequest request);
}
```

Implementations:

```java
class CashPayment implements PaymentStrategy {

    public boolean pay(PaymentRequest request) {
        // cash payment
    }
}
```

```java
class UPIPayment implements PaymentStrategy {

    public boolean pay(PaymentRequest request) {
        // UPI payment
    }
}
```

---

## 19. Why Strategy Pattern?

Without Strategy:

```java
if (paymentType == CASH) {
    ...
} else if (paymentType == UPI) {
    ...
} else if (paymentType == CARD) {
    ...
}
```

Every new payment method requires modifying existing code.

With Strategy:

```java
processor.addStrategy(new CashPayment());
processor.addStrategy(new UPIPayment());
```

Adding card:

```java
processor.addStrategy(new CardPayment());
```

No modification to `PaymentProcessor`.

This follows:

#### Open/Closed Principle

> Open for extension, closed for modification.

---

## 20. PaymentRequest

Encapsulates payment information.

```java
class PaymentRequest {

    private int amount;
    private PaymentMode paymentMode;
    private String upiId;

    public int getAmount() {}
    public PaymentMode getPaymentMode() {}
    public String getUpiId() {}
}
```

This avoids passing multiple parameters:

```java
pay(amount, paymentMode, upiId, ...)
```

Instead:

```java
pay(PaymentRequest request)
```

It also makes the API extensible.

---

## 21. PaymentMode

```java
enum PaymentMode {
    CASH,
    UPI
}
```

Later:

```java
CARD
WALLET
NET_BANKING
```

can be added.

---

## 22. PaymentStatus

```java
enum PaymentStatus {
    IDLE,
    IN_PROGRESS,
    SUCCESS,
    FAILED,
    CANCELLED
}
```

This models the payment lifecycle.

```text
IDLE
 │
 ▼
IN_PROGRESS
 │
 ├──── SUCCESS
 │
 ├──── FAILED
 │
 └──── CANCELLED
```

---

## 23. Complete Responsibility Matrix

| Component              | Responsibility               |
| ---------------------- | ---------------------------- |
| `VendingMachine`       | Context/orchestration        |
| `VendingMachineState`  | State contract               |
| `IdleState`            | Initial state                |
| `ReceiveMoneyState`    | Accept money                 |
| `SelectProductState`   | Product validation/selection |
| `DispenseProductState` | Product dispensing           |
| `ProductInventory`     | Manage shelves               |
| `ItemShelf`            | Manage product slot          |
| `ItemType`             | Product classification       |
| `Coin`                 | Coin instance                |
| `CoinType`             | Coin denomination            |
| `CoinManager`          | Supported coin management    |
| `PaymentProcessor`     | Payment orchestration        |
| `PaymentStrategy`      | Payment contract             |
| `CashPayment`          | Cash payment                 |
| `UPIPayment`           | UPI payment                  |
| `PaymentRequest`       | Payment input                |
| `PaymentStatus`        | Payment lifecycle            |
| `PaymentMode`          | Payment type                 |

---

## 24. End-to-End Transaction

Suppose:

```text
Coke
Price = ₹40
```

User inserts:

```text
₹20 + ₹20
```

#### Step 1 — Idle

```text
VendingMachine
state = IdleState
```

User inserts money.

```text
IdleState.receiveMoney(coins)
```

Transition:

```text
IdleState
    ↓
ReceiveMoneyState
```

---

#### Step 2 — Receive Money

Money is added.

```text
₹20 + ₹20 = ₹40
```

Machine now allows product selection.

---

#### Step 3 — Select Product

User selects Coke.

```text
selectProduct(COKE)
```

`SelectProductState` checks:

```text
Does shelf exist?       YES
Is quantity > 0?        YES
Is payment sufficient?  YES
```

Transition:

```text
SelectProductState
        ↓
DispenseProductState
```

---

#### Step 4 — Dispense

Machine:

```text
1. Retrieves shelf
2. Removes Coke
3. Decrements quantity
4. Updates inventory
5. Calculates change
6. Returns change if necessary
```

---

#### Step 5 — Reset

```text
DispenseProductState
        ↓
IdleState
```

Machine is ready for another customer.

---

## 25. Cancellation Flow

Suppose:

```text
₹50 inserted
Coke = ₹40
```

User presses cancel.

```text
ReceiveMoneyState
        │
        │ cancel
        ▼
refund()
        │
        ▼
₹50 returned
        │
        ▼
IdleState
```

Important invariant:

> Cancellation must not dispense the product and must return the user's unconsumed money.

---

## 26. Insufficient Payment

Suppose:

```text
Product = ₹40
Inserted = ₹20
```

User selects product.

```text
SelectProductState
        │
        ▼
price = ₹40
paid  = ₹20
        │
        ▼
Reject transaction
```

Machine should **not** dispense.

The user can either:

```text
insert additional money
```

or:

```text
cancel → refund
```

---

## 27. Out-of-Stock Flow

Suppose:

```text
Coke quantity = 0
```

User selects Coke.

```text
SelectProductState
        │
        ▼
Inventory.getShelf("COKE")
        │
        ▼
quantity == 0
        │
        ▼
Reject
```

No inventory mutation should happen.

Depending on the business rules, the transaction can remain in the selection state or be cancelled/refunded.

---

## 28. Payment Failure

For UPI:

```text
Select Product
      ↓
PaymentProcessor
      ↓
UPIPayment
      ↓
UPI Gateway
      ↓
FAILED
```

The machine must not dispense the product when payment has not successfully completed.

```text
Payment FAILED
      ↓
No inventory decrement
      ↓
No dispense
```

This is an important consistency rule.

---

## 29. Important Design Invariants

These are useful points to discuss in an interview.

#### Invariant 1 — Never dispense without successful payment

```text
payment SUCCESS
      ↓
dispense
```

Never:

```text
payment IN_PROGRESS
      ↓
dispense
```

---

#### Invariant 2 — Inventory changes only after successful transaction

```text
payment success
      ↓
reserve/dispense
      ↓
decrement quantity
```

---

#### Invariant 3 — Cancellation must be idempotent

If cancel is called twice:

```text
cancel()
cancel()
```

The user should not receive the refund twice.

---

#### Invariant 4 — Failed payment must not consume inventory

```text
Payment FAILED
      ↓
quantity unchanged
```

---

#### Invariant 5 — Successful transaction returns machine to Idle

```text
Dispense
   ↓
Idle
```

---

## 30. SOLID Principles

### Single Responsibility

Each component has a focused responsibility:

```text
Inventory      → inventory
Payment        → payment
CoinManager    → coins
State          → machine behavior
```

---

### Open/Closed

New payment method:

```java
class CardPayment implements PaymentStrategy
```

No need to modify `PaymentProcessor`.

New state:

```java
class MaintenanceState implements VendingMachineState
```

No need to redesign the machine.

---

### Liskov Substitution

Every:

```text
VendingMachineState
```

implementation should be usable wherever the interface is expected.

---

### Dependency Inversion

`VendingMachine` depends on:

```java
VendingMachineState
```

rather than:

```java
IdleState
ReceiveMoneyState
```

directly.

Likewise:

```java
PaymentProcessor
```

depends on:

```java
PaymentStrategy
```

---

## 31. Why Not Put Everything Inside VendingMachine?

A naïve design would look like:

```java
class VendingMachine {

    void insertMoney() {
        if(state == IDLE) {
            ...
        } else if(state == HAS_MONEY) {
            ...
        } else if(state == SELECTION) {
            ...
        }
    }

    void selectProduct() {
        if(state == IDLE) {
            ...
        } else if(state == HAS_MONEY) {
            ...
        }
    }
}
```

Problems:

* Large conditional logic.
* Violates Open/Closed.
* Difficult to test.
* Difficult to add states.
* State transitions become tightly coupled.
* Business logic becomes concentrated in one class.

State pattern moves that behavior into independent classes.

---

## 32. Why Not Put Payment Logic in States?

You might be tempted to do:

```java
ReceiveMoneyState
    └── CashPayment
    └── UPIPayment
```

But payment processing and machine state are **different axes of change**.

State answers:

> What can the machine do right now?

Strategy answers:

> How should this payment be processed?

Keeping them separate gives:

```text
State dimension
    ↓
Idle / Receive / Select / Dispense

Payment dimension
    ↓
Cash / UPI / Card / Wallet
```

This avoids a combinatorial explosion such as:

```text
CashIdleState
CashReceiveState
CashSelectState
UPIIdleState
UPIReceiveState
UPISelectState
...
```

This is a useful **staff-level design discussion**.

---

## 33. Adding Card Payment

Very small change:

```java
class CardPayment implements PaymentStrategy {

    @Override
    public boolean pay(PaymentRequest request) {
        // Card gateway integration
        return true;
    }
}
```

Then:

```java
paymentProcessor.addStrategy(new CardPayment());
```

Existing state classes don't need to change.

---

## 34. Adding a New State

Suppose we introduce:

```text
MaintenanceState
```

```java
class MaintenanceState implements VendingMachineState {

    // disable customer operations
    // allow maintenance operations
}
```

Then:

```text
VendingMachine
      ↓
VendingMachineState
      ↓
MaintenanceState
```

This is one of the major benefits of State pattern.

---

## 35. Staff-Level Improvements

For a stronger interview design, discuss these as **extensions**, not necessarily as mandatory classes.

## 1. Inventory Reservation

For concurrent users:

```text
select product
      ↓
reserve inventory
      ↓
payment
      ↓
dispense
```

Instead of immediately decrementing inventory.

---

## 2. Concurrency

Two operations could target the same shelf.

Example:

```text
Quantity = 1

Customer A → selects Coke
Customer B → selects Coke
```

Need atomic inventory reservation.

Possible approaches:

```text
Atomic decrement
Optimistic locking
Pessimistic locking
Reservation token
```

---

## 3. Idempotency

Payment callbacks can arrive more than once.

```text
Payment SUCCESS
Payment SUCCESS
```

The system must not dispense twice.

Use:

```text
transactionId
paymentId
idempotencyKey
```

and maintain:

```text
transactionId → transaction status
```

---

## 4. Payment Timeout

For UPI:

```text
IN_PROGRESS
     │
     ├── SUCCESS
     ├── FAILED
     └── TIMEOUT
```

Timeout handling should transition the transaction safely without accidental dispensing.

---

## 5. Hardware Failure

What if:

```text
payment succeeds
        ↓
dispensing motor fails
```

Now we have:

```text
Payment = SUCCESS
Product  = NOT DISPENSED
```

This requires a recovery mechanism:

```text
transaction state
       ↓
DISPENSE_FAILED
       ↓
retry / refund / operator intervention
```

This is a useful failure-handling discussion.

---

## 36. Transaction State vs Machine State

A key distinction for a staff-level interview:

#### Machine state

```text
IDLE
RECEIVE_MONEY
SELECT_PRODUCT
DISPENSE_PRODUCT
```

Represents **what the machine is doing**.

#### Payment state

```text
IDLE
IN_PROGRESS
SUCCESS
FAILED
CANCELLED
```

Represents **what happened to payment**.

They should not be mixed together.

For example:

```text
Machine State = DISPENSE_PRODUCT
Payment Status = SUCCESS
```

is perfectly valid.

---

## 37. Potential Real-World Architecture

For a production vending machine:

```text
             ┌───────────────────┐
             │ Physical Machine  │
             └─────────┬─────────┘
                       │
                Vending Controller
                       │
        ┌──────────────┼──────────────┐
        │              │              │
        ▼              ▼              ▼
    Inventory       Payment        Hardware
     Service        Service        Controller
                       │
                 ┌─────┴─────┐
                 ▼           ▼
               UPI          Card
```

The LLD classes shown in the diagram can represent the **controller/domain layer**, while actual payment gateways and hardware drivers can sit behind interfaces.

---

## 38. Interview Flow — How to Explain This Design

A clean explanation sequence is:

#### Step 1

Start with the main entity:

> "`VendingMachine` is my Context. It maintains inventory, payment processing, coin management, and the current machine state."

#### Step 2

Explain State:

> "The machine's behavior changes based on its current state, so I'm using State pattern instead of large conditional blocks."

```text
Idle
 ↓
ReceiveMoney
 ↓
SelectProduct
 ↓
DispenseProduct
 ↓
Idle
```

#### Step 3

Explain inventory:

```text
ProductInventory
      ↓
ItemShelf
```

> "Inventory owns shelves, and each shelf tracks product type, price and quantity."

#### Step 4

Explain payment:

```text
PaymentProcessor
      ↓
PaymentStrategy
    ↙       ↘
 Cash       UPI
```

> "Payment methods are independent strategies so adding Card doesn't require changing the processor."

#### Step 5

Explain coins:

```text
CoinManager
    ↓
Coin / CoinType
```

> "Coin management is separated so denomination validation and change-related logic don't leak into the machine."

#### Step 6

Discuss failure cases:

```text
Insufficient money
Out of stock
Payment failure
Cancellation
Dispensing failure
```

#### Step 7

Discuss concurrency:

```text
inventory reservation
idempotency
atomic update
```

That takes the design from a basic LLD toward a **staff-level discussion**.

---

## 39. One-Page Mental Model

Remember the entire design as **four independent responsibilities**:

```text
                         VENDING MACHINE
                               │
          ┌────────────────────┼────────────────────┐
          │                    │                    │
          ▼                    ▼                    ▼
       INVENTORY             PAYMENT              COINS
          │                    │                    │
          ▼                    ▼                    ▼
     ProductInventory    PaymentProcessor      CoinManager
          │                    │
          ▼                    ▼
      ItemShelf          PaymentStrategy
                         /           \
                        /             \
                  CashPayment      UPIPayment


                       BEHAVIOR
                          │
                          ▼
                 VendingMachineState
                          │
       ┌──────────────────┼──────────────────┐
       ▼                  ▼                  ▼
    IdleState       ReceiveMoneyState   SelectProductState
                                             │
                                             ▼
                                     DispenseProductState
```

#### Core design patterns

```text
State Pattern
    → machine behavior

Strategy Pattern
    → payment behavior
```

#### Core principles

```text
Separation of concerns
Polymorphism
Open/Closed Principle
Composition over inheritance
Encapsulation
Dependency inversion
```

#### Core production concerns

```text
Concurrency
Inventory reservation
Payment idempotency
Payment timeout
Hardware failure
Recovery/refund
Transaction consistency
```

**The most important interview insight:** don't treat this as merely a "vending machine classes" question. The real design challenge is separating the **machine lifecycle (State)** from **payment mechanism (Strategy)** and **resource management (Inventory/CoinManager)** so each dimension can evolve independently.

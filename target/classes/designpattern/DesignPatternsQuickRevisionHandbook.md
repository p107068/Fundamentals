# Design Patterns — Quick Revision Handbook

This is the short, interview-friendly version derived from the actual implementations in the repository under `designpattern.*`.

## 1) Pattern Categories

### Creational
- Singleton
- Factory
- Factory Method
- Abstract Factory
- Builder
- Prototype

### Structural
- Adapter
- Decorator
- Facade
- Composite
- Proxy

### Behavioral
- Strategy
- Observer
- State
- Template Method
- Command
- Chain of Responsibility
- Iterator
- Mediator

---

## 2) Must-Know Design Fundamentals

### What is a design pattern?
A reusable solution to a common design problem.

### Why do we need it?
Better maintainability, flexibility, cleaner communication, less duplication.

### Pattern vs algorithm
- Algorithm = steps to solve a problem.
- Pattern = structure for organizing code.

### Composition vs inheritance
Prefer composition when behavior can vary independently.
- `Car HAS-A Engine` is usually better than `Engine` being in the inheritance tree.

### IS-A / HAS-A / USES-A
- IS-A: inheritance
- HAS-A: composition/aggregation
- USES-A: temporary dependency

### Association / Aggregation / Composition / Dependency
- Association: objects know each other
- Aggregation: part-of, loosely connected
- Composition: strong ownership
- Dependency: one class uses another

### Coupling / Cohesion / Encapsulation / Abstraction / Polymorphism
- Low coupling is good
- High cohesion is good
- Encapsulation hides internals
- Abstraction exposes essentials
- Polymorphism allows same action in different ways

### Dependency Injection / IoC
- DI: inject dependencies from outside
- IoC: framework or external code controls execution flow

---

## 3) Most Relevant Interview Examples

These are the 5 examples most likely to appear in LLD or interview discussions.

1. Payment system
   - Card, UPI, cash are interchangeable payment methods
   - Pattern: Strategy
   - Why it matters: same action, multiple algorithms

2. Notification system
   - Email + SMS subscribers receive updates from a subject
   - Pattern: Observer
   - Why it matters: event-driven broadcast model

3. E-commerce order flow
   - Inventory check → payment → shipping through one interface
   - Pattern: Facade
   - Why it matters: one clean API hides subsystem complexity

4. Expense approval chain
   - Team lead → manager → higher approval authority
   - Pattern: Chain of Responsibility
   - Why it matters: request flows through multiple handlers

5. Vending machine / ATM
   - Idle → money inserted → dispensing
   - Pattern: State
   - Why it matters: behavior changes by internal state

> Interview rule: if the requirement is “same behavior with different implementation”, think Strategy; if it is “many listeners receive same event”, think Observer; if it is “one API over many subsystems”, think Facade; if it is “state-specific behavior”, think State.

## 4) Quick Pattern Summary

### Singleton
- Purpose: ensure one shared instance
- Real repo example: `SingletonPatternExample.AppConfiguration`
- Example: one app-wide config object shared across the system
- Interview clue: “only one instance”, “global config”
- Use when: one global resource is required
- Avoid when: DI is cleaner and better for testing

### Factory
- Purpose: hide creation logic behind a factory
- Real repo example: `VehicleFactory` in `FactoryPatternExample`
- Example: `create("car")` returns a `Car`; `create("bike")` returns a `Bike`
- Interview clue: “create based on type”
- Use when: object creation logic is complex or repeated

### Factory Method
- Purpose: base class defines workflow; subclass decides creation
- Real repo example: `Restaurant` and `PizzaRestaurant` in `FactoryMethodAbstractFactoryExample`
- Example: `PizzaRestaurant` creates a `Pizza`; another subclass creates a `Burger`
- Interview clue: “same process, different product”

### Abstract Factory
- Purpose: create families of related objects
- Real repo example: `UiFactory`, `LightUiFactory`, `Button`, `CheckBox`
- Example: `LightUiFactory` creates matching light button + checkbox
- Interview clue: “theme, platform, family of products”

### Builder
- Purpose: construct complex object step by step
- Real repo example: `User.Builder` in `BuilderPatternExample`
- Example: `new User.Builder().name(...).age(...).email(...).build()`
- Interview clue: “many optional parameters”, “fluent build API”

### Prototype
- Purpose: clone an existing configured object
- Real repo example: `GameCharacter.copy()` in `PrototypePatternExample`
- Example: clone a warrior before applying damage to the copy
- Interview clue: “copy existing object”, “clone and modify”

### Adapter
- Purpose: make incompatible interfaces work together
- Real repo example: `XmlToJsonAdapter` in `AdapterPatternExample`
- Example: XML source converted to JSON reader without changing the client API
- Interview clue: “existing API has different format”

### Decorator
- Purpose: add behavior dynamically without subclassing
- Real repo example: `CoffeeDecorator`, `MilkDecorator`, `SugarDecorator`
- Example: `new MilkDecorator(new SugarDecorator(new SimpleCoffee()))`
- Interview clue: “stack features”

### Facade
- Purpose: provide a simple interface over a complex subsystem
- Real repo example: `OrderFacade` in `FacadePatternExample`
- Example: `placeOrder("Book", 499.0)` handles inventory + payment + shipping
- Interview clue: “simplify many services into one API”

### Composite
- Purpose: treat individual objects and groups uniformly
- Real repo example: `FileSystemItem`, `File`, `Folder`
- Example: same `print()` works for a single file and a whole folder tree
- Interview clue: “tree structure”, “leaf and group same treatment”

### Proxy
- Purpose: control access to another object
- Real repo example: `AuthorizedDocumentProxy` in `ProxyPatternExample`
- Example: only authorized users can read a document
- Interview clue: “authorization”, “lazy loading”, “stand-in object”

### Strategy
- Purpose: encapsulate interchangeable algorithms
- Real repo example: `PaymentStrategy` and `Payment` in `StrategyPatternExample`
- Example: same checkout can use card, UPI, or cash strategy
- Interview clue: “multiple algorithms”, “switch behavior at runtime”

### Observer
- Purpose: notify multiple subscribers when state changes
- Real repo example: `NotificationSubject`, `EmailSubscriber`, `MobileSubscriber`
- Example: email and SMS subscribers get the same update event
- Interview clue: “publish/subscribe”, “listeners”

### State
- Purpose: object behavior changes with internal state
- Real repo example: `VendingMachine`, `IdleState`, `HasMoneyState`, `DispensingState`
- Example: idle → insert money → select item → dispensing
- Interview clue: “finite states”, “mode-based behavior”

### Template Method
- Purpose: fixed algorithm skeleton; subclass customizes steps
- Real repo example: `ReportGenerator` and `CsvReportGenerator`
- Example: `generate()` calls `readData()` and `formatData()` for CSV or JSON
- Interview clue: “same process, different steps”

### Command
- Purpose: encapsulate a request as an object
- Real repo example: `RemoteControl`, `TurnOnLightCommand`, `TurnOffLightCommand`
- Example: remote presses command object to turn the light on or off
- Interview clue: “request object”, “queue/undo”

### Chain of Responsibility
- Purpose: pass request through multiple handlers
- Real repo example: `ExpenseApprover`, `TeamLead`, `Manager`
- Example: team lead approves small amounts; manager approves bigger ones
- Interview clue: “approval chain”, “pass through handlers”

### Iterator
- Purpose: traverse collection without exposing internals
- Real repo example: `Playlist implements Iterable<String>`
- Example: `for (String song : playlist)` iterates without exposing the array
- Interview clue: “iterate without exposing storage”

### Mediator
- Purpose: centralize communication between objects
- Real repo example: `ChatRoom` and `ChatUser` in `MediatorPatternExample`
- Example: Alice sends to room; room forwards to Bob and Charlie
- Interview clue: “central hub”, “many objects talk via one mediator”

---

## 4) Useful Comparisons

### Strategy vs State
- Strategy: client chooses behavior
- State: context changes internal state and delegates behavior

### Factory vs Abstract Factory
- Factory: one product type
- Abstract Factory: family of related products

### Factory vs Builder
- Factory: create object directly
- Builder: assemble complex object step by step

### Adapter vs Facade
- Adapter: fix incompatible interfaces
- Facade: simplify a complex subsystem

### Decorator vs Proxy
- Decorator: add behavior
- Proxy: control access or mediate use

### Observer vs Mediator
- Observer: subject broadcasts to subscribers
- Mediator: central object routes communication among colleagues

### Command vs Strategy
- Command: encapsulates a request/action
- Strategy: encapsulates an algorithm/behavior choice

---

## 5) Interview Triggers

| Scenario | Think of |
|---|---|
| Many algorithms with same goal | Strategy |
| One object with many states | State |
| Need to notify many listeners | Observer |
| Need to add optional features | Decorator |
| Different formats / APIs need to work together | Adapter |
| Need one API for many subsystems | Facade |
| Create product based on type | Factory |
| Families of products | Abstract Factory |
| Optional parameters and fluent setup | Builder |
| Clone existing config object | Prototype |
| Walk through collection without exposing internals | Iterator |
| Request passes through handlers | Chain of Responsibility |
| Need a request object | Command |
| Many objects need central coordination | Mediator |
| One global shared object | Singleton |
| Same algorithm, varying steps | Template Method |

---

## 6) 30-Second Answers

### Strategy
“Strategy allows us to encapsulate interchangeable algorithms behind a common interface, so the client depends on behavior abstraction instead of concrete implementations. It helps when the same operation can be implemented in multiple ways and we want to avoid large conditional logic.”

### Factory
“Factory centralizes object creation so the calling code depends on the product interface instead of concrete classes. It is useful when creation logic is complex, repeated, or likely to change.”

### Builder
“Builder separates construction from representation and allows us to create complex objects step by step. It is ideal when a class has many optional fields or a long parameter list.”

### Observer
“Observer keeps subject and dependent objects loosely coupled by notifying subscribers when the subject changes. It is useful for event-driven systems and broadcast-style updates.”

### Decorator
“Decorator adds responsibilities dynamically by wrapping an object with the same interface. This is helpful when feature combinations are many but creating subclasses for each combination is impractical.”

### Adapter
“Adapter converts one interface into another so incompatible classes can work together without rewriting existing code. It is essentially a compatibility wrapper.”

### Facade
“Facade hides subsystem complexity behind a single simplified interface. The client calls one high-level method and the facade coordinates the lower-level services.”

### State
“State pattern moves behavior into state-specific classes, letting the context delegate actions based on its current state. This avoids large if-else logic in stateful objects.”

### Command
“Command encapsulates a request as an object, which lets the caller invoke a generic execute method instead of directly calling the receiver. This makes it easy to queue, log, or undo operations.”

### Chain of Responsibility
“Chain of Responsibility passes a request through a series of handlers until one handles it. This decouples the sender from the concrete handler and works well for approvals, filters, and routing.”

### Mediator
“Mediator centralizes communication between related objects so they do not depend directly on each other. It is useful when many components interact and direct coupling becomes messy.”

---

## 7) Quick Prep Rule

For every pattern, be able to answer:
1. What problem does it solve?
2. What is the core idea?
3. Who are the participants?
4. Where is the dependency or composition?
5. When should I use it and when should I avoid it?

---

## 8) Repository Pattern Map

The patterns in this repo are implemented in:
- `designpattern.tier1` — Singleton, Factory, Factory Method + Abstract Factory, Builder, Observer, Strategy
- `designpattern.tier2` — Decorator, State, Template Method, Command
- `designpattern.tier3` — Adapter, Chain of Responsibility, Composite, Facade, Iterator, Mediator, Prototype, Proxy

That is the core set for interviews from this project.

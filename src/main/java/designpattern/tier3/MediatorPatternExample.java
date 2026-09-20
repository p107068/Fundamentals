package designpattern.tier3;

/**
 * Mediator Pattern
 *
 * Intent: Centralize communication between related objects.
 *
 * Use when: Many objects communicate with each other and their direct dependencies grow.
 *
 * Examples: Chat rooms; air-traffic control; dialog boxes coordinating UI controls.
 *
 * Structure: Colleagues send messages to a mediator, which routes them to other colleagues.
 *
 * Benefit: Reduces direct coupling between colleagues.
 *
 * Tradeoff: The mediator can become a complex central object if it handles too much logic.
 */
public final class MediatorPatternExample {
    private MediatorPatternExample() {
    }

    public interface ChatMediator {
        void send(String message, ChatUser sender);
    }

    public static final class ChatRoom implements ChatMediator {
        private ChatUser firstUser;
        private ChatUser secondUser;

        public void addUsers(ChatUser firstUser, ChatUser secondUser) {
            this.firstUser = firstUser;
            this.secondUser = secondUser;
        }

        @Override
        public void send(String message, ChatUser sender) {
            ChatUser receiver = sender == firstUser ? secondUser : firstUser;
            receiver.receive(message);
        }
    }

    public static final class ChatUser {
        private final String name;
        private final ChatMediator mediator;

        public ChatUser(String name, ChatMediator mediator) {
            this.name = name;
            this.mediator = mediator;
        }

        public void send(String message) {
            mediator.send(message, this);
        }

        public void receive(String message) {
            System.out.println(name + " received: " + message);
        }
    }

    public static void main(String[] args) {
        ChatRoom room = new ChatRoom();
        ChatUser alice = new ChatUser("Alice", room);
        ChatUser bob = new ChatUser("Bob", room);
        room.addUsers(alice, bob);

        alice.send("Hello, Bob");
        bob.send("Hello, Alice");
    }
}
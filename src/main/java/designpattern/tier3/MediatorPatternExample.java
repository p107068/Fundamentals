package designpattern.tier3;

import java.util.ArrayList;
import java.util.List;

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
        void sendMessage(String message, ChatUser sender);

        void addUser(ChatUser user);
    }

    public static final class ChatRoom implements ChatMediator {
        private List<ChatUser> users = new ArrayList<ChatUser>();

        @Override
        public void addUser(ChatUser user) {
            users.add(user);
        }

        @Override
        public void sendMessage(String message, ChatUser sender) {
            for (ChatUser user : users) {
                // Don't send message back to sender
                if (user != sender) {
                    user.receiveMessage(message);
                }
            }
        }
    }

    public static final class ChatUser {
        private final String name;
        private final ChatMediator mediator;

        public ChatUser(String name, ChatMediator mediator) {
            this.name = name;
            this.mediator = mediator;
        }

        public void sendMessage(String message) {
            System.out.println(name + " sends: " + message);
            mediator.sendMessage(message, this);
        }

        public void receiveMessage(String message) {
            System.out.println(name + " received: " + message);
        }
    }

    public static void main(String[] args) {
        ChatMediator chatRoom = new ChatRoom();

        ChatUser alice = new ChatUser("Alice", chatRoom);
        ChatUser bob = new ChatUser("Bob", chatRoom);
        ChatUser charlie = new ChatUser("Charlie", chatRoom);

        chatRoom.addUser(alice);
        chatRoom.addUser(bob);
        chatRoom.addUser(charlie);

        alice.sendMessage("Hello everyone!");

        System.out.println();

        bob.sendMessage("Hi Alice!");
    }
}
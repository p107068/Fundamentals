package solid;

/** Demonstrates high-level code depending on an abstraction rather than a concrete detail. */
public final class DependencyInversionExample {
    private DependencyInversionExample() {
    }

    public interface MessageSender {
        void send(String recipient, String message);
    }

    public static final class EmailSender implements MessageSender {
        @Override
        public void send(String recipient, String message) {
            System.out.println("Email to " + recipient + ": " + message);
        }
    }

    public static final class NotificationService {
        private final MessageSender sender;

        public NotificationService(MessageSender sender) {
            this.sender = sender;
        }

        public void notify(String recipient, String message) {
            sender.send(recipient, message);
        }
    }

    public static void main(String[] args) {
        NotificationService service = new NotificationService(new EmailSender());
        service.notify("user@example.com", "Build completed");
    }
}
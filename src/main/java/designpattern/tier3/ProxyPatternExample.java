package designpattern.tier3;

/**
 * Proxy Pattern
 *
 * Intent: Use a stand-in object to control access to another object.
 *
 * Use when: Access needs authorization, caching, lazy loading, logging, or remote calls.
 *
 * Examples: Permission checks before viewing a document; a cached image; a remote API client.
 *
 * Structure: The proxy and real service implement the same interface; the proxy delegates
 * only after applying its access-control behavior.
 *
 * Benefit: Adds control without changing the real service.
 *
 * Tradeoff: Introduces another object and can make request flow less obvious.
 */
public final class ProxyPatternExample {
    private ProxyPatternExample() {
    }

    public interface DocumentService {
        void read(String documentName);
    }

    public static final class RealDocumentService implements DocumentService {
        @Override
        public void read(String documentName) {
            System.out.println("Reading document: " + documentName);
        }
    }

    public static final class AuthorizedDocumentProxy implements DocumentService {
        private final DocumentService realService = new RealDocumentService();
        private final boolean isAuthorized;

        public AuthorizedDocumentProxy(boolean isAuthorized) {
            this.isAuthorized = isAuthorized;
        }

        @Override
        public void read(String documentName) {
            if (isAuthorized) {
                realService.read(documentName);
            } else {
                System.out.println("Access denied for " + documentName);
            }
        }
    }

    public static void main(String[] args) {
        new AuthorizedDocumentProxy(true).read("salary-report.pdf");
        new AuthorizedDocumentProxy(false).read("salary-report.pdf");
    }
}
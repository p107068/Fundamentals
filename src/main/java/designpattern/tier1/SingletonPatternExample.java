package designpattern.tier1;

/**
 * Singleton Pattern
 *
 * Intent: Ensure a class has one shared instance and provide one access point to it.
 *
 * Use when: Exactly one shared object is a real business or infrastructure requirement,
 * such as application-wide configuration.
 *
 * Examples: Application configuration, a shared metrics registry, or a process-wide
 * resource manager when one instance is genuinely required.
 *
 * Structure: A private constructor prevents outside creation; getInstance returns the
 * single instance. This example uses eager initialization, which is thread-safe.
 *
 * Avoid when: You only want convenient access. Singleton adds global state, makes tests
 * harder, and can hide dependencies. Prefer dependency injection in most services.
 */
public final class SingletonPatternExample {
    private SingletonPatternExample() {
    }

    public static class AppConfiguration {
        private static AppConfiguration INSTANCE = new AppConfiguration();

        private AppConfiguration() {
        }

        public static AppConfiguration getInstance() {
            return INSTANCE;
        }

        public String getEnvironment() {
            return "development";
        }
    }

    public static void main(String[] args) {
        AppConfiguration first = AppConfiguration.getInstance();
        AppConfiguration second = AppConfiguration.getInstance();

        System.out.println(first.getEnvironment());
        System.out.println("Same instance: " + (first == second));
    }
}
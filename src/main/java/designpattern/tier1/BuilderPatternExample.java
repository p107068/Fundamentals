package designpattern.tier1;

/**
 * Builder Pattern
 *
 * Intent: Build a complex object step by step, especially when it has many optional fields.
 *
 * Use when: Constructors would require many parameters or repeated overloads.
 *
 * Examples: User profiles with optional age and email; HTTP requests with optional
 * headers and timeouts; database queries with optional filters and sorting.
 *
 * Structure: Builder methods set values and return the builder; build creates the final User.
 *
 * Benefit: Creates readable code and can produce an immutable object with final fields.
 *
 * Tradeoff: Requires a separate builder and is unnecessary for very small objects.
 */
public final class BuilderPatternExample {
    private BuilderPatternExample() {
    }

    public static final class User {
        private final String name;
        private final int age;
        private final String email;

        private User(Builder builder) {
            this.name = builder.name;
            this.age = builder.age;
            this.email = builder.email;
        }

        @Override
        public String toString() {
            return "User{name='" + name + "', age=" + age + ", email='" + email + "'}";
        }

        public static final class Builder {
            private String name;
            private int age;
            private String email;

            public Builder name(String name) {
                this.name = name;
                return this;
            }

            public Builder age(int age) {
                this.age = age;
                return this;
            }

            public Builder email(String email) {
                this.email = email;
                return this;
            }

            public User build() {
                return new User(this);
            }
        }
    }

    public static void main(String[] args) {
        User user = new User.Builder()
                .name("John")
                .age(25)
                .email("x@y.com")
                .build();

        System.out.println(user);
    }
}
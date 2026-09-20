package designpattern.tier2;

/**
 * Template Method Pattern
 *
 * Intent: Define the fixed structure of an algorithm and let subclasses customize
 * selected steps.
 *
 * Use when: Several algorithms follow the same overall process but differ in a few
 * implementation steps.
 *
 * Examples: CSV and JSON report generation; data import pipelines; payment processing
 * with different validation or completion steps.
 *
 * Structure: The template method is final and calls overridable steps in a fixed order.
 * Subclasses customize steps without changing the overall algorithm.
 *
 * Benefit: Reuses the common algorithm structure and prevents steps from being reordered.
 *
 * Tradeoff: Uses inheritance and can become rigid when workflows vary significantly.
 */
public final class TemplateMethodPatternExample {
    private TemplateMethodPatternExample() {
    }

    public static abstract class ReportGenerator {
        public final void generate() {
            readData();
            formatData();
            writeReport();
        }

        protected abstract void readData();

        protected abstract void formatData();

        private void writeReport() {
            System.out.println("Writing report");
        }
    }

    public static final class CsvReportGenerator extends ReportGenerator {
        @Override
        protected void readData() {
            System.out.println("Reading CSV data");
        }

        @Override
        protected void formatData() {
            System.out.println("Formatting data as CSV");
        }
    }

    public static final class JsonReportGenerator extends ReportGenerator {
        @Override
        protected void readData() {
            System.out.println("Reading JSON data");
        }

        @Override
        protected void formatData() {
            System.out.println("Formatting data as JSON");
        }
    }

    public static void main(String[] args) {
        new CsvReportGenerator().generate();
        new JsonReportGenerator().generate();
    }
}
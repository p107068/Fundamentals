package designpattern.tier3;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite Pattern
 *
 * Intent: Treat individual objects and groups of objects uniformly.
 *
 * Use when: Data has a tree structure and clients should handle leaves and groups alike.
 *
 * Examples: Files and folders; organization charts; UI components; product categories.
 *
 * Structure: Leaf and composite objects implement the same Component interface.
 *
 * Benefit: Client code can call the same operation on a single item or an entire group.
 *
 * Tradeoff: A shared interface can make some operations meaningless for leaf objects.
 */
public final class CompositePatternExample {
    private CompositePatternExample() {
    }

    public interface FileSystemItem {
        void print(String indent);
    }

    public static final class File implements FileSystemItem {
        private final String name;

        public File(String name) {
            this.name = name;
        }

        @Override
        public void print(String indent) {
            System.out.println(indent + "- " + name);
        }
    }

    public static final class Folder implements FileSystemItem {
        private final String name;
        private final List<FileSystemItem> children = new ArrayList<FileSystemItem>();

        public Folder(String name) {
            this.name = name;
        }

        public void add(FileSystemItem child) {
            children.add(child);
        }

        @Override
        public void print(String indent) {
            System.out.println(indent + "+ " + name);
            for (FileSystemItem child : children) {
                child.print(indent + "  ");
            }
        }
    }

    public static void main(String[] args) {
        Folder root = new Folder("project");
        root.add(new File("README.md"));

        Folder source = new Folder("src");
        source.add(new File("App.java"));
        root.add(source);

        root.print("");
    }
}
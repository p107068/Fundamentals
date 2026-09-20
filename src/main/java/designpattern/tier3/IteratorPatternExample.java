package designpattern.tier3;

import java.util.Iterator;

/**
 * Iterator Pattern
 *
 * Intent: Traverse a collection without exposing how it stores elements.
 *
 * Use when: Clients need a standard way to visit elements in different collections.
 *
 * Examples: Iterating playlist songs, menu items, database results, or tree nodes.
 *
 * Structure: The collection returns an Iterator that owns the traversal position.
 *
 * Benefit: Clients use the same traversal API even if storage changes.
 *
 * Tradeoff: A custom iterator adds code when a built-in collection is sufficient.
 */
public final class IteratorPatternExample {
    private IteratorPatternExample() {
    }

    public static final class Playlist implements Iterable<String> {
        private final String[] songs;

        public Playlist(String... songs) {
            this.songs = songs;
        }

        @Override
        public Iterator<String> iterator() {
            return new Iterator<String>() {
                private int index;

                @Override
                public boolean hasNext() {
                    return index < songs.length;
                }

                @Override
                public String next() {
                    return songs[index++];
                }
            };
        }
    }

    public static void main(String[] args) {
        Playlist playlist = new Playlist("Song A", "Song B", "Song C");
        for (String song : playlist) {
            System.out.println(song);
        }
    }
}
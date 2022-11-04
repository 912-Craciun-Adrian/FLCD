package domain;

/**
 * Custom Pair class to help us with the Symbol Table
 * @param <K> template for the key of the pair
 * @param <V> template for the value of the pair
 */
public class Pair<K, V>{
    private K key;
    private V value;

    /**
     * The constructor for our Pair class
     * @param key the given key
     * @param value the given value
     */
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    // getters and setters

    public K get_key() {
        return key;
    }

    public void set_key(K key) {
        this.key = key;
    }

    public V get_value() {
        return value;
    }

    public void set_value(V value) {
        this.value = value;
    }

    /**
     * We print the pair as <key, value>
     * @return the string <key, value>
     */
    @Override
    public String toString() {
        return "<" + key + ", " + value + ">";
    }
}
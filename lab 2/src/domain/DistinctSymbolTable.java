package domain;

import java.util.ArrayList;

/**
 * The class for Symbol Table
 */
public class DistinctSymbolTable {
    private int length;
    private ArrayList<ArrayList<String>> symbolTableElements = new ArrayList<>();

    public DistinctSymbolTable() {
    }

    public DistinctSymbolTable(int length) {
        this.length = length;
        ArrayList<String> element;

        for (int i = 0; i < length; ++i)
        {
            element = new ArrayList<>();
            this.symbolTableElements.add(element);
        }
    }

    public DistinctSymbolTable(int length, ArrayList<ArrayList<String>> symbolTableItems) {
        this.length = length;
        this.symbolTableElements = symbolTableItems;
    }

    /**
     * The hash function for our symbol table, returning the ascii sum % length
     * @param element the element that we need to compute the hash code for
     * @return the hash code for a given element
     */
    public int hash_function(String element){
        int ascii_sum = 0;
        for (int i = 0; i < element.length(); ++i)
            ascii_sum += element.charAt(i);

        return ascii_sum % this.length;
    }

    /**
     * This function adds an element to the Symbol Table
     * @param element the given element
     */
    public void add_element(String element){
        int hash_value = hash_function(element);
        if (this.symbolTableElements.get(hash_value).contains(element))
            return;
        this.symbolTableElements.get(hash_value).add(element);
    }

    /**
     * This function removes an element to the Symbol Table
     * @param element the given element
     */
    public void remove_element(String element){
        int hash_value = hash_function(element);
        if (!this.symbolTableElements.get(hash_value).contains(element))
            return;
        this.symbolTableElements.get(hash_value).remove(element);
    }

    /**
     * This function returns true if an element is part of our symbol table and false otherwise
     * @param element the given element
     * @return true if an element is part of our symbol table and false otherwise
     */
    public boolean contains_element(String element){
        int hash_value = hash_function(element);
        return this.symbolTableElements.get(hash_value).contains(element);
    }

    public int getLength() {
        return length;
    }

    public ArrayList<ArrayList<String>> getSymbolTableElements() {
        return symbolTableElements;
    }

    /**
     * This function returns the position of a key in the Symbol Table
     * @param key the given key
     * @return a pair with the hash code position and the corresponding linked list position
     */
    public Pair<Integer, Integer> get_position(String key){
        if (this.contains_element(key)){
            int hash_code_position = this.hash_function(key);
            int linked_list_position = 0;
            for (String linked_list_value : this.symbolTableElements.get(hash_code_position)){
                if (!linked_list_value.equals(key))
                    linked_list_position++;
                else
                    break;
            }

            return new Pair<>(hash_code_position, linked_list_position);
        }
        return new Pair<>(-1, -1);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DISTINCT SYMBOL TABLE -- HASH TABLE").append('\n');
        stringBuilder.append("length = ").append(this.length).append('\n');

        for (int hash_position = 0; hash_position < length; ++hash_position){
            stringBuilder.append("h[").append(hash_position).append("] = ");
            ArrayList<String> elements = this.symbolTableElements.get(hash_position);
            for (String element : elements)
                stringBuilder.append(element).append(" ");
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }
}

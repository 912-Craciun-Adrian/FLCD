package domain;

import java.util.ArrayList;

public class DistinctSymbolTable {
    private int length;
    private ArrayList<ArrayList<String>> symbolTableElements;

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

    public int hash_function(String element){
        int ascii_sum = 0;
        for (int i = 0; i < element.length(); ++i)
            ascii_sum += element.charAt(i);

        return ascii_sum % this.length;
    }

    public void add_element(String element){
        int hash_value = hash_function(element);
        if (this.symbolTableElements.get(hash_value).contains(element))
            return;
        this.symbolTableElements.get(hash_value).add(element);
    }

    public void remove_element(String element){
        int hash_value = hash_function(element);
        if (!this.symbolTableElements.get(hash_value).contains(element))
            return;
        this.symbolTableElements.get(hash_value).remove(element);
    }

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

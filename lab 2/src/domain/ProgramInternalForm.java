package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * The Program Internal Form, first variant with Code and Value (pair of <int, int>)
 */
public class ProgramInternalForm {
    private final List<Pair<Integer, Pair<Integer, Integer>>> pif = new ArrayList<>();

    public void add(Integer code, Pair<Integer, Integer> value) {
        Pair<Integer, Pair<Integer, Integer>> pair = new Pair<>(code, value);
        pif.add(pair);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Pair<Integer, Pair<Integer, Integer>> pair : pif) {
            result.append(pair.get_key()).append(" -> (").append(pair.get_value().get_key()).append(", ").append(pair.get_value().get_value()).append(")\n");
        }
        return result.toString();
    }
}
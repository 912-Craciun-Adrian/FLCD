package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * The Program Internal Form, first variant with Code and Value (pair of <int, int>)
 */
public class ProgramInternalForm {
    private final List<Pair<Integer, Pair<Integer, Integer>>> program_internal_form = new ArrayList<>();

    public void add(Integer element_code, Pair<Integer, Integer> element_value) {
        Pair<Integer, Pair<Integer, Integer>> element = new Pair<>(element_code, element_value);
        program_internal_form.add(element);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Pair<Integer, Pair<Integer, Integer>> element : program_internal_form) {
            result.append(element.get_key()).append(" -> (").append(element.get_value().get_key()).append(", ").append(element.get_value().get_value()).append(")\n");
        }
        return result.toString();
    }
}
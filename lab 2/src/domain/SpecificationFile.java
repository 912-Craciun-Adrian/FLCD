package domain;

import java.util.*;

/**
 * The Specification File
 */
public class SpecificationFile {
    // the operators, separators and reserved words from Lab1b
    private final List<String> operators = Arrays.asList("+", "-", "*", "/", "==", "<", ">", "<=", "=", ">", ">=");
    private final List<String> separators = Arrays.asList("(", ")", "[", "]", "{", "}", ":", ";", " ", ",", "\n", "\t");
    private final List<String> reserved_words = Arrays.asList("array", "char", "const", "else", "if", "integer",
            "read", "fread", "write", "fwrite", "function", "return");
    private final HashMap<String, Integer> codify_list = new HashMap<>();

    public SpecificationFile() {
        codify();
    }

    private void codify(){
        codify_list.put("identifier", 0);
        codify_list.put("constant", 1);
        int counter = 2;

        for (String reservedWord : reserved_words){
            codify_list.put(reservedWord, counter);
            counter++;
        }

        for (String operator : operators){
            codify_list.put(operator, counter);
            counter++;
        }

        for (String separator : separators){
            codify_list.put(separator, counter);
            counter++;
        }
    }

    /**
     * The function checks if the given expression is an identifier
     * @param expression the given expression
     * @return true if the given expression is an identifier
     *         false otherwise
     */
    public boolean is_identifier(String expression) {
        // all characters starting with a to z or A to Z
        String identifier = "^[a-zA-Z]([a-zA-Z_0-9])*$";
        return expression.matches(identifier);
    }

    /**
     * The function checks if the given expression is an constant
     * @param expression the given expression
     * @return true if the given expression is an identifier
     *         false otherwise
     */
    public boolean is_constant(String expression) {
        // regex for a char constant
        String char_constant = "^'[0-9a-zA-Z_ ]'";
        if (expression.matches(char_constant))
            return true;

        // regex for a string constant
        String string_constant = "^\"[0-9a-zA-Z_ ]+\"";
        if (expression.matches(string_constant))
            return true;

        // regex for a numeric constant
        String numeric_constant =
                "^0|[+|-][1-9]([0-9])*|[1-9]([0-9])*|[+|-][1-9]([0-9])*\\.([0-9])*|[1-9]([0-9])*\\.([0-9])*$";
        if (expression.matches(numeric_constant))
            return true;

        return false;
    }

    /**
     * The function checks if the given expression is part of the list of operators
     * @param expression the given expression
     * @return true if the given expression is part of the list of operators
     *         false otherwise
     */
    public boolean is_operator(String expression) {
        return operators.contains(expression);
    }

    /**
     * The function checks if the given characters is part of an operator (= in ==, < in <=, etc.)
     * @param c the given character
     * @return true if the given character is part of the list of an operator (= in ==, < in <=, etc.)
     *         false otherwise
     */
    public boolean is_part_operator(char c) {
        return is_operator(String.valueOf(c));
    }

    /**
     * The function checks if the given expression is part of the list of separators
     * @param expression the given expression
     * @return true if the given expression is part of the list of separators
     *         false otherwise
     */
    public boolean is_separator(String expression) {
        return separators.contains(expression);
    }

    /**
     * The function checks if the given expression is part of the list of reserved words
     * @param expression the given expression
     * @return true if the given expression is part of the list of reserved words
     *         false otherwise
     */
    public boolean is_reserved_word(String expression) {
        return reserved_words.contains(expression);
    }

    /**
     * The function returns the code for a given expression
     * @param expression the given expression
     * @return true if the given expression is part of the list of reserved words
     *         false otherwise
     */
    public Integer get_code(String expression) {
        return codify_list.get(expression);
    }
}
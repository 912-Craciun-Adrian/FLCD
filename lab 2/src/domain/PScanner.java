package domain;

import java.io.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The class for our personalised scanner
 */
public class PScanner {
    private final int size = 20;            // the size of our hash table
    private final DistinctSymbolTable symbol_table = new DistinctSymbolTable(size);         // our symbol table
    private final SpecificationFile specification_file = new SpecificationFile();           // our specification file from lab 1b
    private final ProgramInternalForm program_internal_form = new ProgramInternalForm();    // our pif

    private final String program_file;      // the token file
    private final String PIF_outfile;       // the program internal as output file
    private final String ST_outfile;        // the symbol table as output file

    /**
     * The constructor for our PScanner (personalised scanner)
     * @param program_file the token file, similar to what we have done at Lab1b
     * @param PIF_outfile the program internal form output file
     * @param ST_outfile the symbol table output file
     */
    public PScanner(String program_file, String PIF_outfile, String ST_outfile) {
        this.program_file = program_file;
        this.PIF_outfile = PIF_outfile;
        this.ST_outfile = ST_outfile;
    }

    /**
     * The main function of our person scanner
     */
    public void run() {
        // token_table, left is the token and right is its corresponding counter
        CopyOnWriteArrayList<Pair<String, Integer>> token_table = new CopyOnWriteArrayList<>();
        try {
            File file = new File(this.program_file);
            Scanner file_scanner = new Scanner(file);

            int unique_code = 1;
            // while we still have lines in the input file
            while (file_scanner.hasNextLine()) {
                String line = file_scanner.nextLine();
                // get the tokens from each line and add them to our token list
                CopyOnWriteArrayList<String> tokens = translate_line(line);
                for (String token : tokens)
                    token_table.add(new Pair<>(token, unique_code));
                ++unique_code;
            }
            file_scanner.close();

            // deploy pif and write the result
            fun_deploy_pif(token_table);
            fun_write_output();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function translates a current_line from our input file
     * @param current_line the current_line from the input file
     * @return CopyOnWriteArrayList<String> token_list: a list of tokens from the given current_line
     */
    public CopyOnWriteArrayList<String> translate_line(String current_line) {
        CopyOnWriteArrayList<String> token_list = new CopyOnWriteArrayList<>();

        // We loop through each character in the line and look for tokens
        for (int position_in_line = 0; position_in_line < current_line.length(); ++position_in_line) {
            // operators + - * / < > =
            // we need something else for >= == <=
            if (this.specification_file.is_separator(String.valueOf(current_line.charAt(position_in_line))))
                token_list.add(String.valueOf(current_line.charAt(position_in_line)));

            // if the char is part of the operators == <= >=
            else if (specification_file.is_part_operator(current_line.charAt(position_in_line))) {
                String expression = check_part_operator(current_line, position_in_line);
                token_list.add(expression);
                position_in_line += expression.length() - 1;
            }

            // separators () [] {} : ; , space
            else if (this.specification_file.is_separator(String.valueOf(current_line.charAt(position_in_line))))
                token_list.add(String.valueOf(current_line.charAt(position_in_line)));

            // char
            else if (current_line.charAt(position_in_line) == '\'') {
                String expression = check_char(current_line, position_in_line);
                token_list.add(expression);
                position_in_line += expression.length() - 1;
            }

            // string
            else if (current_line.charAt(position_in_line) == '\"') {
                String expression = check_string(current_line, position_in_line);
                token_list.add(expression);
                position_in_line += expression.length() - 1;
            }

            // numbers starting with +
            // we need to check if + is operator or part of a constant
            else if (current_line.charAt(position_in_line) == '+') {
                String expression = check_plus(current_line, position_in_line, token_list);
                token_list.add(expression);
                position_in_line += expression.length() - 1;
            }

            // numbers starting with -
            // we need to check if - is operator or part of a constant
            else if (current_line.charAt(position_in_line) == '-') {
                String expression = check_minus(current_line, position_in_line, token_list);
                token_list.add(expression);
                position_in_line += expression.length() - 1;
            }

            // otherwise, we try to identify the token
            else {
                String expression = identify_token(current_line, position_in_line);
                token_list.add(expression);
                position_in_line += expression.length() - 1;
            }
        }

        // at least, we return the list of tokens formed
        return token_list;
    }

    /**
     * The function checks if starting from starting_position we have a string
     * @param current_line the current line
     * @param starting_position the starting position that we have to loop the line from
     * @return the string constant that we have built
     */
    public String check_string(String current_line, int starting_position) {
        StringBuilder string_builder = new StringBuilder();

        // we start looping through the line and stop if we are on the end of the line, we find a separator/operator
        for (int position = starting_position; position < current_line.length(); ++position) {
            if (((position == current_line.length() - 2 && current_line.charAt(position + 1) != '\"') || (position == current_line.length() - 1))
                && (specification_file.is_separator(String.valueOf(current_line.charAt(position))) || specification_file.is_operator(String.valueOf(current_line.charAt(position)))))
                break;
            string_builder.append(current_line.charAt(position));
            if (current_line.charAt(position) == '\"' && position != starting_position)
                break;
        }

        return string_builder.toString();
    }

    /**
     * The function checks if starting from starting_position we have a string
     * @param current_line the current current_line
     * @param starting_position the starting position that we have to loop the line from
     * @return the string constant that we have built
     */
    public String check_char(String current_line, int starting_position) {
        StringBuilder constant = new StringBuilder();

        for (int position = starting_position; position < current_line.length(); ++position) {
            if ((specification_file.is_separator(String.valueOf(current_line.charAt(position))) || specification_file.is_operator(String.valueOf(current_line.charAt(position))))
                    &&
                    ((position == current_line.length() - 2 && current_line.charAt(position + 1) != '\'') || (position == current_line.length() - 1)))
                break;
            constant.append(current_line.charAt(position));
            if (current_line.charAt(position) == '\'' && position != starting_position)
                break;
        }

        return constant.toString();
    }

    public String check_minus(String line, int starting_position, CopyOnWriteArrayList<String> token_list) {
        // if we have a constant or an identifier on the position before, it means that - is an operator
        if (specification_file.is_identifier(token_list.get(token_list.size() - 1)) ||
                specification_file.is_constant(token_list.get(token_list.size() - 1)))
            return "-";


        // else, - is part of a number
        StringBuilder token = new StringBuilder();
        token.append('-');

        for (int position = starting_position + 1; position < line.length(); ++position)
            if ((Character.isDigit(line.charAt(position))))
                token.append(line.charAt(position));

        return token.toString();
    }

    public String check_plus(String line, int starting_position, CopyOnWriteArrayList<String> token_list) {
        // if we have a constant or an identifier on the position before, it means that + is an operator
        if (specification_file.is_identifier(token_list.get(token_list.size() - 1)) ||
                specification_file.is_constant(token_list.get(token_list.size() - 1))) {
            return "+";
        }

        // else, + is part of a number
        StringBuilder token = new StringBuilder();
        token.append('+');

        for (int position = starting_position + 1; position < line.length(); ++position)
            if (Character.isDigit(line.charAt(position)))
                token.append(line.charAt(position));

        return token.toString();
    }

    public String check_part_operator(String line, int starting_position) {
        StringBuilder string_builder = new StringBuilder();
        // eg. <=, ==
        // we append the first character then the second
        string_builder.append(line.charAt(starting_position));
        string_builder.append(line.charAt(starting_position + 1));

        if (specification_file.is_operator(string_builder.toString()))
            return string_builder.toString();

        return String.valueOf(line.charAt(starting_position));
    }

    public String identify_token(String line, int starting_position) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int index = starting_position; index < line.length(); ++index) {
            if (specification_file.is_separator(String.valueOf(line.charAt(index))))
                break;
            if (specification_file.is_part_operator(line.charAt(index)))
                break;
            if (line.charAt(index) == ' ')
                break;
            stringBuilder.append(line.charAt(index));
        }
        return stringBuilder.toString();
    }

    public void fun_deploy_pif(CopyOnWriteArrayList<Pair<String, Integer>> tokens) {
        List<String> bad_tokens = new ArrayList<>();
        boolean correct = true;
        for (Pair<String, Integer> token_pair : tokens) {
            String token = token_pair.get_key();

            if (specification_file.is_operator(token) || specification_file.is_reserved_word(token)
                    || specification_file.is_separator(token)) {
                int code = specification_file.get_code(token);
                program_internal_form.add(code, new Pair<>(-1, -1));
            }
            else if (specification_file.is_identifier(token)) {
                symbol_table.add_element(token);
                Pair<Integer, Integer> position = symbol_table.get_position(token);
                program_internal_form.add(0, position);
            }
            else if (specification_file.is_constant(token)) {
                symbol_table.add_element(token);
                Pair<Integer, Integer> position = symbol_table.get_position(token);
                program_internal_form.add(1, position);
            }
            else if (!bad_tokens.contains(token)) {
                bad_tokens.add(token);
                correct = false;
                System.out.println("The program displayed an error at line" + token_pair.get_value() +
                        ": invalid token " + token);
            }
        }

        if (correct) {
            System.out.println("The program does not have any lexical errors");
        }
    }

    public void fun_write_output() {
        try {
            File st_out = new File(ST_outfile);
            FileWriter st_file_writer = new FileWriter(st_out, false);
            BufferedWriter symbolTableWriter = new BufferedWriter(st_file_writer);
            symbolTableWriter.write(symbol_table.toString());
            symbolTableWriter.close();

            File pif_out = new File(PIF_outfile);
            FileWriter pif_file_writer = new FileWriter(pif_out, false);
            BufferedWriter pifWriter = new BufferedWriter(pif_file_writer);
            pifWriter.write(program_internal_form.toString());
            pifWriter.close();
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
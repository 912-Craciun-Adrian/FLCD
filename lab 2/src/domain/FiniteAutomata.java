package domain;

import java.io.File;
import java.util.*;

public class FiniteAutomata {
    public Set<String> all_states;                              // Q
    public Set<String> input_symbols;                           // Σ
    public String initial_state;                                // q
    public Set<String> final_states;                            // F
    public Map<Pair<String, String>, Set<String>> transitions;  // δ

    public FiniteAutomata(String filename){
        this.all_states = new HashSet<>();
        this.input_symbols = new HashSet<>();
        this.final_states = new HashSet<>();
        this.transitions = new HashMap<>();
        ReadFiniteAutomata(filename);
    }

    // In a DFA, for a particular input character, the machine goes to one state only
    public boolean CheckIfDFA(){
        for (var transition : transitions.values())
            if (transition.size() > 1)
                return false;
        return true;
    }

    public boolean VerifySequence(String sequence){
        if (sequence.length() == 0)
            return final_states.contains(initial_state);

        String current_state = initial_state;
        for (int i = 0; i < sequence.length(); ++i){
            Pair<String, String> key = new Pair<>(current_state, String.valueOf(sequence.charAt(i)));
            if (transitions.containsKey(key))
            {
                current_state = transitions.get(key).iterator().next();
            }
            else
            {
                return false;
            }
        }

        return final_states.contains(current_state);
    }

    private void ReadFiniteAutomata(String given_file){
        try {
            File input_file = new File(given_file);
            Scanner file_scanner = new Scanner(input_file);

            // Q
            String all_states_file = file_scanner.nextLine();
            all_states = new HashSet<>(Arrays.asList(all_states_file.split(" ")));

            // Σ
            String alphabet_file = file_scanner.nextLine();
            input_symbols = new HashSet<>(Arrays.asList(alphabet_file.split(" ")));

            // q
            initial_state = file_scanner.nextLine();

            // F
            String finalStatesLine = file_scanner.nextLine();
            final_states = new HashSet<>(Arrays.asList(finalStatesLine.split(" ")));

            // δ
            while (file_scanner.hasNextLine()) {
                String transition_file = file_scanner.nextLine();
                String[] transition = transition_file.split(" ");

                // example transition function : p 0 q

                // p and q need to be in the all_states
                if (all_states.contains(transition[0]) && all_states.contains(transition[2]) && input_symbols.contains(transition[1])) {

                    Pair<String, String> function_states = new Pair<>(transition[0], transition[1]);

                    // if we do not have a set with p and 0 we make it and add q to it
                    if (!transitions.containsKey(function_states)) {
                        Set<String> function_states_set = new HashSet<>();
                        function_states_set.add(transition[2]);
                        transitions.put(function_states, function_states_set);
                    }
                    // else, we add the function to the set
                    else {
                        transitions.get(function_states).add(transition[2]);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String GetAllStates(){
        StringBuilder string_builder = new StringBuilder();
        string_builder.append("States (Q) = { ");
        for (String state : all_states){
            string_builder.append(state).append(" ");
        }
        string_builder.append(" } ");

        return string_builder.toString();
    }

    public String GetInputSymbols(){
        StringBuilder string_builder = new StringBuilder();
        string_builder.append("Alphabet (Input Symbols, Σ) = { ");
        for (String input_symbol : input_symbols){
            string_builder.append(input_symbol).append(" ");
        }
        string_builder.append("}");

        return string_builder.toString();
    }

    public String GetInitialState(){
        return "Initial State (q) = { " +
                initial_state + " }";
    }

    public String GetFinalStates(){
        StringBuilder string_builder = new StringBuilder();
        string_builder.append("Final States (F) = { ");
        for (String final_state : final_states){
            string_builder.append(final_state).append(" ");
        }
        string_builder.append("}");

        return string_builder.toString();
    }

    public String GetTransitions(){
        StringBuilder string_builder = new StringBuilder();
        string_builder.append("Transitions (δ) = \n");
        transitions.forEach((K, V) -> 
                string_builder.append("{").append(K.get_key()).append(",").append(K.get_value()).append("} --> ").append(V).append("\n"));

        return string_builder.toString();
    }
}
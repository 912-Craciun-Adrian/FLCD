import domain.FiniteAutomata;
import domain.PScanner;

import java.util.Scanner;

public class Run {
    public static void display_main_menu(){
        System.out.println("MAIN MENU-------------");
        System.out.println("1: Run Scanner");
        System.out.println("2: Run DFA");
    }

    public static void display_dfa_menu(){
        System.out.println("DFA MENU-------------");
        System.out.println("1: Display all states");
        System.out.println("2: Display alphabet");
        System.out.println("3: Display initial state");
        System.out.println("4: Display all final states");
        System.out.println("5: Display transitions");
        System.out.println("6: Verify DFA sequence");
        System.out.println("7: Make DFA check for identifiers");
        System.out.println("8: Make DFA check for integer constants");
        System.out.println("0: Exit");
    }

    public static void main(String[] args) {
        display_main_menu();
        System.out.println("Your command: ");
        Scanner scanner = new Scanner(System.in);
        int main_menu_option = scanner.nextInt();
        switch (main_menu_option){
            case 1 -> RunScanner();
            case 2 -> RunDFA();
        }
    }

    private static void RunScanner() {
        System.out.println("Running the Scanner on p1");
        PScanner scannerP1 = new PScanner("src/io/p1.txt",
                "src/io/pif1.txt",
                "src/io/st1.txt");
        scannerP1.run();

        System.out.println("Running the Scanner on p2");
        PScanner scannerP2 = new PScanner("src/io/p2.txt",
                "src/io/pif2.txt",
                "src/io/st2.txt");
        scannerP2.run();

        System.out.println("Running the Scanner on p3");
        PScanner scannerP3 = new PScanner("src/io/p3.txt",
                "src/io/pif3.txt",
                "src/io/st3.txt");
        scannerP3.run();

        System.out.println("Running the Scanner on p1err");
        PScanner scannerP1err = new PScanner("src/io/p1err.txt",
                "src/io/pif1err.txt",
                "src/io/st1err.txt");
        scannerP1err.run();
    }

    private static void RunDFA() {
        FiniteAutomata finite_automata = new FiniteAutomata("src/fa_io/fa.txt");
        display_dfa_menu();
        Scanner scanner_dfa_option = new Scanner(System.in);
        while (true) {
            int dfa_menu_option = scanner_dfa_option.nextInt();
            switch (dfa_menu_option) {
                case 0 -> {System.exit(0);}
                case 1 -> System.out.println(finite_automata.GetAllStates());
                case 2 -> System.out.println(finite_automata.GetInputSymbols());
                case 3 -> System.out.println(finite_automata.GetInitialState());
                case 4 -> System.out.println(finite_automata.GetFinalStates());
                case 5 -> System.out.println(finite_automata.GetTransitions());
                case 6 -> {
                    if (finite_automata.CheckIfDFA()) {
                        System.out.println("Give a sequence: ");
                        Scanner scanner_sequence = new Scanner(System.in);
                        String sequence = scanner_sequence.nextLine();

                        if (finite_automata.VerifySequence(sequence))
                            System.out.println("The given sequence is valid!");
                        else
                            System.out.println("Error! The given sequence is invalid!");
                    } else {
                        System.out.println("Error! FA is not DFA! (deterministic)");
                    }
                }
                case 7 -> {
                    finite_automata = new FiniteAutomata("src/fa_io/identifier.txt");
                }
                case 8 -> {
                    finite_automata = new FiniteAutomata("src/fa_io/integer_constant.txt");
                }
            }
        }
    }
}
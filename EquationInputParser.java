import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Josue on 12/12/2016.
 */
public class EquationInputParser {
    private String inputFromKeyboard;
    private MStack stack;
    private ArrayList<MSym> symbols;
    private String equation;

    public EquationInputParser() {
        this.inputFromKeyboard = "";
        this.stack = new MStack();
        this.symbols = new ArrayList<>();
        this.equation = "";
    }

    private void fillStack() {
        char[] letters = this.inputFromKeyboard.toCharArray();
        // removing all spaces
        System.out.println("adding letters");
        for (char letter : letters) {
            if (letter != ' ') {
//                System.out.println("adding letter: " + letter);
                this.stack.push(letter);
            }
        }

        // reverse the stack
        this.stack.reverse();

        this.stack.display();
    }

    private MSym parseSym(String completeSym) {
        String[] value = completeSym.split("=");
        // Because we are using stack it will reverse the string sometimes
        // depending on when we use it so in that case the value will be
        // first so it will prompt error that's why we put an exception
        try {
            return new MSym(value[1], Long.parseLong(value[0]));
        } catch (Exception e) {
            return new MSym(value[0], Long.parseLong(value[1]));
        }
    }

    private void format() {
        String getSym = "";
        System.out.println("Parsing text: ");

        while (!this.stack.isEmpty()) {
            Character character = (Character) this.stack.pop();

//            System.out.println("PArsing letter: " + character);
            if (character == ';') {
                if (getSym.contains("=")) {
                    MSym newSymbol = this.parseSym(getSym);

                    // find position in the list
                    int position = this.isInList(newSymbol);
                    // override value if it is in list
                    if (position != -1)
                        this.symbols.remove(position);

                    this.symbols.add(newSymbol);
                    getSym = "";
                } else {
                    this.equation = getSym;
                    getSym = "";
                }
            } else {
                getSym += character;
            }
        }

        // Checks last string
        if (getSym.contains("=")) {
            this.symbols.add(this.parseSym(getSym));
        } else {
            this.equation = getSym;
        }
    }

    private int isInList(MSym check) {
        int index = 0;
        for (MSym sym : this.symbols) {
            if (check.equals(sym))
                return index;
            index++;
        }

        return -1;
    }

    public String getInput() {
        while (true) {
            if (this.equation.equals(""))
                System.out.println("Input Your equation or everything separated by ';': ");
            else
                System.out.println("Input Your symbols now or many separated by ';': ");

            Scanner keyInput = new Scanner(System.in);
            this.inputFromKeyboard += keyInput.nextLine();

            this.fillStack();

            this.format();
//            System.out.println("Text: " + this.inputFromKeyboard);

            if (this.symbols.size() != 0 && !this.equation.isEmpty()) {
                System.out.println("Would you like to add more?(y/n): ");
                if (keyInput.next().equals("n"))
                    break;
            }

            this.inputFromKeyboard += ";";

//            System.out.println("s: " + this.symbols.size());
//            System.out.println("e: " + this.equation);
        }

        // in case you need it you can have it
        return this.inputFromKeyboard;
    }

    public ArrayList<MSym> getSymbols() {
        return this.symbols;
    }

    public String getEquation() {
        return this.equation;
    }
}

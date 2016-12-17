import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Josue on 12/12/2016.
 */
public class ExpInputParser {
    private String inputFromKeyboard;
    private MQueue queue;
    private ArrayList<ExpVariable> symbols;
    private String equation;
    private int countSigns;

    public ExpInputParser() {
        this.inputFromKeyboard = "";
        this.queue = new MQueue();
        this.symbols = new ArrayList<>();
        this.equation = "";
        this.countSigns = 0;
    }

    private void fillQueue() {
        char[] letters = this.inputFromKeyboard.toCharArray();
        // removing all spaces
        Helpers.log("adding", "letters to queue");
        for (char letter : letters) {
            if (letter != ' ') {
//                System.out.println("adding letter: " + letter);
                this.queue.insert(letter);
            }
        }
    }

    private ExpVariable parseSym(String completeSym) {
        String[] value = completeSym.split("=");
        // Because we are using stack it will reverse the string sometimes
        // depending on when we use it so in that case the value will be
        // first so it will prompt error that's why we put an exception
        try {
            return new ExpVariable(value[1], Long.parseLong(value[0]));
        } catch (Exception e) {
            return new ExpVariable(value[0], Long.parseLong(value[1]));
        }
    }

    private void format() {
        String getSym = "";
        Helpers.log("Parsing", equation);
//        System.out.println("Parsing text: ");

        while (!this.queue.isEmpty()) {
            Character character = (Character) this.queue.remove();
//            System.out.println("PArsing letter: " + character);

            // counting the signs
            if (Helpers.isOperator(character))
                this.countSigns++;

            // break on separator ;
            if (character == ';') {
                // checks if it is a value something like 'apple = 3'
                //      so ti stores it on a list of symbols
                if (getSym.contains("=")) {
                    // get the symbol
                    ExpVariable newSymbol = this.parseSym(getSym);

                    // checks if symbol is in the list
                    // find position in the list
                    int position = this.isInList(newSymbol);
                    // override value if it is in list

                    if (position != -1)     // remove item
                        this.symbols.remove(position);

                    // add it
                    this.symbols.add(newSymbol);
                    getSym = "";
                } else {    // in case we do not find any symbol then it is equation
                    this.equation = getSym;
                    getSym = "";
                }
            } else {    // if we do not find any breaker then lets accumulate it into a string
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

    private void checkForNumbers() {
        String equation = this.equation;
        MQueue eQueue = new MQueue(equation);
        ArrayList<Double> numbersFound = new ArrayList<>();

        String tempNumber = "";

        while (!eQueue.isEmpty()) {
            char c = (char) eQueue.remove();
            try {
                int number = Integer.parseInt(String.valueOf(c));
                tempNumber += number;
            } catch (Exception e) {
                if (!tempNumber.isEmpty()) {
                    System.out.println(tempNumber);
                    numbersFound.add(Double.parseDouble(tempNumber));
                    tempNumber = "";
                }
            }
        }

        // there must be 1 symbol more than signs to check operations
        // if not check if there are numbers declared
        if (numbersFound.size() > 0 && this.countSigns + 1 != this.symbols.size()) {
            System.out.println("We have found that you have numbers.");
            System.out.println("-> Make sure to input the numbers so i can know which ones are the numbers wanted.");
            System.out.println("-> there might be cases where you have 123+apple-");
            System.out.println("-> so you will need to specify if we pick (1 and 23) or (12 or 3)");
            System.out.println("-> now input the numbers separated by ; or by pressing enter and input the other number: ");
            try {
                this.inputNumbers();
            } catch (Exception e) {
                checkForNumbers();
            }
        }
    }

    /**
     * Check if there is some number in the input sometimes it may bring some problems
     *      when we input something like 123+apple/
     *      in that case the computer doesn't know if it has to take 12 and 3 or 1 and 23
     *      so it will check that case and will ask you to tell it what are the numbers
     *      you can input them separated by ';' (e.i: 12;3)
     * @throws Exception when you input a number with a letter (e.i: 12n)
     */
    private void inputNumbers() throws Exception {
        Scanner keyInput = new Scanner(System.in);
        String numbers = keyInput.nextLine();
        MQueue numbersQueue = new MQueue(numbers);

        String num = "";
        double number;

        while (!numbersQueue.isEmpty()) {
            char character = (char) numbersQueue.remove();
            if (character == ';') {     // checks limit
                try {
                    number = Double.parseDouble(num);
                    this.symbols.add(new ExpVariable(num, number));
                } catch (Exception e) {
                    throw new Exception("This number contains characters, please input again.");
                }
            } else {
                num += character;
            }
        }

        // for last or first number
        try {
            number = Double.parseDouble(num);
            this.symbols.add(new ExpVariable(num, number));
        } catch (Exception e) {
            throw new Exception("This number contains characters, please input again.");
        }

        this.checkForNumbers();
    }

    /**
     * Checks if variable is in our variable list
     * @param check: variable to check
     * @return index if found otherwise returns =1
     */
    private int isInList(ExpVariable check) {
        int index = 0;
        for (ExpVariable sym : this.symbols) {
            if (check.equals(sym))
                return index;
            index++;
        }

        return -1;
    }

    /**
     * Will return the expression and will store the expression variables into a list
     *      which you might use it in the future if you need to do operation or want to
     *      convert the expression to infix
     * @return
     */
    public String getInput() {
        while (true) {
            if (this.equation.equals(""))
                System.out.println("Input Your equation or everything separated by ';': ");
            else
                System.out.println("Input Your symbols now or many separated by ';': ");

            Scanner keyInput = new Scanner(System.in);
            this.inputFromKeyboard += keyInput.nextLine();


            this.fillQueue();

            this.format();
//            System.out.println("Text: " + this.inputFromKeyboard);

            if (this.symbols.size() != 0 && !this.equation.isEmpty()) {
                System.out.println("Would you like to add more variables (y/n)?: ");
                if (keyInput.next().equals("n"))
                    break;
            }

            this.inputFromKeyboard += ";";

//            System.out.println("s: " + this.symbols.size());
//            System.out.println("e: " + this.equation);
        }


        this.checkForNumbers();
        // in case you need it you can have it
        return this.inputFromKeyboard;
    }

    public ArrayList<ExpVariable> getSymbols() {
        return this.symbols;
    }

    public String getEquation() {
        return this.equation;
    }
}
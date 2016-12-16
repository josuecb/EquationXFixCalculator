import java.util.ArrayList;

/**
 * Created by Josue on 12/12/2016.
 */
public class MChecker {
    private MStack equationStack;
    private String equation;
    private ArrayList<MSymbols> symbols;
    private MStack formattedStack;
    private String output;

    public MChecker(String equation, ArrayList<MSymbols> symbols) {
        this.formattedStack = new MStack();
        this.equation = equation;
        this.symbols = symbols;
        this.output = "";
        this.fillStack(equation);

        System.out.println(equation);
        System.out.println(symbols.size());
        for (MSymbols s: symbols) {
            System.out.println(s.getLabel());
        }
    }

    private void fillStack(String equation) {
        this.equationStack = new MStack();

        String temp = "";

        for (int index = 0; index < equation.length(); index++) {
            temp += equation.charAt(index);

            int position = Helpers.isInList(this.symbols, temp);
            if (position != -1) {
                this.equationStack.push(this.symbols.get(position));
                temp = "";
            } else if (Helpers.isOperator(equation.charAt(index))) {
                this.equationStack.push(equation.charAt(index));
                temp = "";
            }
        }

        this.equationStack.reverse();
    }

    public String postfixToInfix() {
        MStack stack = new MStack();

        System.out.println();
        System.out.println();
        while (!this.equationStack.isEmpty()) {
            if (!Helpers.isSymbol(this.equationStack.peek())) {
                String previous = (String) stack.pop();
                stack.push("(" + stack.pop() + this.equationStack.pop() + previous + ")");
            } else {
                stack.push(((MSymbols) this.equationStack.pop()).getLabel());
            }
        }
//
//        for (char character : this.equation.toCharArray()) {
//            if (Helpers.isOperator(character)) {
//                String previous = (String) stack.pop();
//                stack.push("(" + stack.pop() + character + previous + ")");
//            } else {
//                stack.push(String.valueOf(character));
//            }
//        }
        return (String) stack.pop();
    }

}

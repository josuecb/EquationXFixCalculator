import java.util.ArrayList;

/**
 * Created by Josue on 12/12/2016.
 */
public class ExpChecker {
    private MStack equationStack;
    private String equation;
    private ArrayList<ExpVariable> variables;

    /**
     * Will work with the list of variables specified
     * and the expression input it doesn't matter if it is infix, prefix, postfix
     * This class will return the infix expression
     *
     * @param equation:    (pre|post|in)fix expression equation
     * @param expVariable: list of variables
     */
    public ExpChecker(String equation, ArrayList<ExpVariable> expVariable) {
        this.equation = equation;
        this.variables = expVariable;
        this.fillStack(equation);

        Helpers.separator();
        Helpers.log("Type", this.getFormatType(equation).toString());
        Helpers.log("raw equation", equation);
        Helpers.separator();
        Helpers.log("variables found", expVariable.size() + "");

        for (ExpVariable s : expVariable) {
            Helpers.log(s.getLabel(), s.getValue() + "");
        }
        Helpers.separator();
    }

    private void fillStack(String equation) {
        this.equationStack = new MStack();

        String temp = "";

        for (int index = 0; index < equation.length(); index++) {
            temp += equation.charAt(index);

            int position = Helpers.isInList(this.variables, temp);
            if (position != -1) {
                this.equationStack.push(this.variables.get(position));
                temp = "";
            } else if (Helpers.isOperator(equation.charAt(index))) {
                this.equationStack.push(equation.charAt(index));
                temp = "";
            }
        }

        // if it is prefix do not reverse stack
        if (this.getFormatType(equation) != ExpType.prefix)
            this.equationStack.reverse();
    }

    /**
     * This method will check what type of
     *
     * @param equation
     * @return
     */
    public ExpType getFormatType(String equation) {
        if (Helpers.isOperator(equation.charAt(0)))
            return ExpType.prefix;
        else if (Helpers.isOperator(equation.charAt(equation.length() - 1)))
            return ExpType.postfix;
        else
            return ExpType.infix;
    }

    /**
     * Converts postfix to infix
     *
     * @return infix expression
     */
    private String postfixToInfix() {
        MStack stack = new MStack();

        while (!this.equationStack.isEmpty()) {
            if (!Helpers.isVariableObject(this.equationStack.peek())) {
                String previous = (String) stack.pop();
                stack.push("(" + stack.pop() + this.equationStack.pop() + previous + ")");
            } else {
                stack.push(((ExpVariable) this.equationStack.pop()).getLabel());
            }
        }

        return (String) stack.pop();
    }


    /**
     * lets suppose we input a prefix: '*'/-applebc-+de/fg)
     * Because the stack is not reversed we can treat it as postfix
     *
     * @return string return infix expression
     */
    private String prefixToInfix() {
        // we are going to start analyzing from the top
        // and we will get (((g/f)-(e+d))*(c/(b-apple))) though it is reversed but
        // the equation will give us the same result
        // however for human readability i decided to inverse into the right way
        return reversePrefixString(this.postfixToInfix());
    }

    /**
     * Since the stack is full it will pop from last character
     * last character may be '(' then it will change it to ')'
     * and vice-verse so we will get this (((apple-b)/c)*((d+e)-(f/g)))
     * Now it will return a readable string
     *
     * @param fromPrefixToInfix
     * @return
     */
    private String reversePrefixString(String fromPrefixToInfix) {
        System.out.println(fromPrefixToInfix);
        MStack stack = new MStack();

        String temp = "";

        for (int index = 0; index < fromPrefixToInfix.length(); index++) {
            char character = fromPrefixToInfix.charAt(index);
            switch (character) {        // converts '(' to ')'
                case '(':
                    character = ')';
                    break;
                case ')':               // converts ')' to '('
                    character = '(';
                    break;
            }

            temp += character;

            // insert everything into a stack
            int position = Helpers.isInList(this.variables, temp);
            if (position != -1) {
                stack.push(this.variables.get(position));
                temp = "";
            } else if (Helpers.isOperator(character)) {
                stack.push(character);
                temp = "";
            } else if (character == '(' || character == ')') {
                stack.push(character);
                temp = "";
            }
        }

        temp = "";

        // converts it to string
        while (!stack.isEmpty()) {
            Object object = stack.peek();
            if (Helpers.isVariableObject(object)) { // checks if it is a expression variable object or a character
                temp += ((ExpVariable) stack.pop()).getLabel();    // gets the label of the expression variable object
            } else {
                temp += stack.pop();
            }
        }

        return temp;
    }


    /**
     * This method is will output always an infix expression
     *
     * @return infix expression
     * @throws Exception if it is a weird expression
     */
    public String getInfix() throws Exception {
        switch (this.getFormatType(this.equation)) {
            case infix:
                return equation;
            case postfix:
                return postfixToInfix();
            case prefix:
                return prefixToInfix();
            default:
                throw new Exception("Make sure to input an equation.");
        }
    }
}

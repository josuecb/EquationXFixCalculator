import java.util.ArrayList;

/**
 * Created by Josue on 12/12/2016.
 */
public class Equation {
    private MQueue infixEquation;
    private ArrayList<ExpVariable> variables;
    private MStack waitStack;
    private double result;
    private int tempCount;

    /**
     * This class will do the infix equation
     *
     * @param infixEquation it must be infix expression
     * @param variables     all the variables to get the value
     */
    public Equation(String infixEquation, ArrayList<ExpVariable> variables) {
        this.waitStack = new MStack();
        this.infixEquation = new MQueue(infixEquation);
        this.variables = variables;
        this.result = 0;
        tempCount = 0;
        Helpers.log("infix expression", infixEquation);
        Helpers.separator();
    }

    /**
     * @return the result of the equation
     */
    public double doEquation() {
        String tempLabel = "";

        while (!this.infixEquation.isEmpty()) {
            char character = (char) this.infixEquation.remove();

            // Here everything will be pushed to a wait list until finding a ')' closing parenthesis
            // if it finds it then it will continue to do operation
            // going backwards until it finds a '(' open parenthesis
            switch (character) {
                case '+':
                case '-':
                case '/':
                case '*':
                case '^':
                    int position = Helpers.isInList(this.variables, tempLabel);

                    if (position != -1 && !tempLabel.isEmpty()) {
                        Helpers.log("pushing", tempLabel);
                        this.waitStack.push(this.variables.get(position)); // adding accumulative label
                    }

                    Helpers.log("pushing", character + "");
                    this.waitStack.push(character); // adding sign

                    // checking next value
                    char c = (char) infixEquation.peekFront();
                    // checks if next character is opening parenthesis
                    if (c == '(') {
                        this.infixEquation.remove();
                        this.waitStack.push(c);
                    }
                    tempLabel = "";
                    break;
                case '(':
                    Helpers.log("pushing", character + "");
                    this.waitStack.push(character);
                    break;
                case ')':
                    int index = Helpers.isInList(this.variables, tempLabel);

                    if (index != -1) {
                        Helpers.log("pushing", this.variables.get(index).getLabel());
                        this.waitStack.push(this.variables.get(index)); // adding accumulative label
                    }

                    this.doOperation();
                    tempLabel = "";
                    break;
                default:
                    tempLabel += character;
            }
        }

        result = ((ExpVariable) this.waitStack.pop()).getValue();
//        System.out.println("Result: " + result);
        return result;
    }

    /**
     * This will do the operation wither it is (+|-|*|/|^)
     */
    private void doOperation() {
        String tempInteger = "";
        boolean flag = true;

        Helpers.separator();
        Helpers.log("found '('", "then we must do calculation");
        Helpers.log("calculating", "infix expression");

        // The flag is if it meets a open parenthesis
        while (!this.waitStack.isEmpty() && flag) {
            Helpers.log("items in wait stack", this.waitStack.size() + "");

            Object object = this.waitStack.pop();

            if (Helpers.isVariableObject(object))
                Helpers.log("removing from wait stack", ((ExpVariable) object).getLabel());
            else
                Helpers.log("removing from wait stack", object + "");

            // checks if object is a char or a variable if it is not a variable
            // then it might be an operator or a parenthesis
            if (!Helpers.isVariableObject(object)) {
                // Here it will do operation and will push the value as if it were a new variable
                // called temp1 or whatever number the tempCount is that is why we increment the tempCount
                // so we do not override any temp variable
                switch ((char) object) {
                    case '+':
                        if (!tempInteger.isEmpty()) {
                            double newInt = ((ExpVariable) this.waitStack.pop()).getValue();
                            double prevInt = Double.parseDouble(tempInteger);
                            flag = removeOpenParen();       // checks if next is a parenthesis if so break
                            tempCount++;                    // increment temp
                            Helpers.log("adding", newInt + " and " + prevInt);
                            // push to waitStack
                            this.waitStack.push(new ExpVariable("temp" + tempCount, newInt + prevInt));
//                            result = newInt + prevInt;
                        } else {
                            double newInt = ((ExpVariable) this.waitStack.pop()).getValue();
                            Helpers.log("adding", result + " and " + newInt);
                            result += newInt;
                        }
                        tempInteger = "";
                        logPeek();
                        break;
                    case '-':
                        if (!tempInteger.isEmpty()) {
                            double newInt = ((ExpVariable) this.waitStack.pop()).getValue();
                            double prevInt = Double.parseDouble(tempInteger);
                            flag = removeOpenParen();

                            tempCount++;
                            Helpers.log("subtracting", newInt + " and " + prevInt);
                            this.waitStack.push(new ExpVariable("temp" + tempCount, newInt - prevInt));
                        } else {
                            double newInt = ((ExpVariable) this.waitStack.pop()).getValue();
                            Helpers.log("subtracting", result + " and " + newInt);
                            result = newInt - result;
                        }
                        tempInteger = "";
                        logPeek();
                        break;
                    case '/':
                        if (!tempInteger.isEmpty()) {
                            double newInt = ((ExpVariable) this.waitStack.pop()).getValue();
                            double prevInt = Double.parseDouble(tempInteger);
                            flag = removeOpenParen();

                            tempCount++;
                            Helpers.log("dividing", newInt + " and " + prevInt);
                            this.waitStack.push(new ExpVariable("temp" + tempCount, newInt / prevInt));
                        } else {
                            double newInt = ((ExpVariable) this.waitStack.pop()).getValue();
                            Helpers.log("dividing", result + " and " + newInt);
                            result = newInt / result;
                        }
                        tempInteger = "";
                        logPeek();
                        break;
                    case '*':
                        if (!tempInteger.isEmpty()) {
                            double newInt = ((ExpVariable) this.waitStack.pop()).getValue();
                            double prevInt = Double.parseDouble(tempInteger);
                            flag = removeOpenParen();

                            tempCount++;
                            Helpers.log("multiplying", newInt + " and " + prevInt);
                            this.waitStack.push(new ExpVariable("temp" + tempCount, newInt * prevInt));
                        } else {
                            double newInt = ((ExpVariable) this.waitStack.pop()).getValue();
                            Helpers.log("multiplying", result + " and " + newInt);
                            result *= newInt;
                        }
                        tempInteger = "";
                        logPeek();
                        break;
                    case '^':
                        if (!tempInteger.isEmpty()) {
                            double newInt = ((ExpVariable) this.waitStack.pop()).getValue();
                            double prevInt = Double.parseDouble(tempInteger);
                            flag = removeOpenParen();

                            tempCount++;
                            Helpers.log("powering", newInt + " and " + prevInt);
                            this.waitStack.push(new ExpVariable("temp" + tempCount, Math.pow(newInt, prevInt)));
                        } else {
                            double newInt = ((ExpVariable) this.waitStack.pop()).getValue();
                            Helpers.log("powering", result + " and " + newInt);
                            result = Math.pow(result, newInt);
                        }
                        tempInteger = "";
                        logPeek();
                        break;
                    case '(':
                        flag = false;
                        break;
                }
            } else {
                tempInteger += ((ExpVariable) object).getValue();
            }
        }
    }

    /**
     * This is only a log
     */
    private void logPeek() {
        try {
            Helpers.log("pussing to wait stack", String.valueOf(((ExpVariable) this.waitStack.peek()).getValue()));
        } catch (Exception e) {
            Helpers.log("pussing to wait stack", (String) this.waitStack.peek());
        }
    }

    /**
     * Checks if next element in the wait stack is '(' if so then return true
     * @return true if next char is a parenthesis
     */
    private boolean removeOpenParen() {
        if (!Helpers.isVariableObject(this.waitStack.peek())) {
            // check prev char
            char prevChar = (char) this.waitStack.pop();
            if (prevChar != '(')
                this.waitStack.push(prevChar);
            else
                return false;
        }
        return true;
    }
}

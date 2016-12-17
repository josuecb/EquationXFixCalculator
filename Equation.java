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
//            System.out.println("removing: " + character);
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
        while (!this.waitStack.isEmpty() && flag) {
            Helpers.log("items in wait stack", this.waitStack.size() + "");

            Object object = this.waitStack.pop();

            if (Helpers.isVariableObject(object))
                Helpers.log("removing from wait stack", ((ExpVariable) object).getLabel());
            else
                Helpers.log("removing from wait stack", object + "");

            if (!Helpers.isVariableObject(object)) {
                switch ((char) object) {
                    case '+':
                        if (!tempInteger.isEmpty()) {
                            double newInt = ((ExpVariable) this.waitStack.pop()).getValue();
                            double prevInt = Double.parseDouble(tempInteger);
                            flag = removeOpenParen();
                            tempCount++;
                            Helpers.log("adding", newInt + " and " + prevInt);
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

    private void logPeek() {
        try {
            Helpers.log("pussing to wait stack", String.valueOf(((ExpVariable) this.waitStack.peek()).getValue()));
        } catch (Exception e) {
            Helpers.log("pussing to wait stack", (String) this.waitStack.peek());
        }
    }

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

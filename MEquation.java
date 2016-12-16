import java.util.ArrayList;

/**
 * Created by Josue on 12/12/2016.
 */
public class MEquation {
    private MQueue infixEquation;
    private ArrayList<MSymbols> symbols;
    private MStack waitStack;
    private double result;
    private int tempCount;

    public MEquation(String infixEquation, ArrayList<MSymbols> symbolss) {
        this.waitStack = new MStack();
        this.infixEquation = new MQueue(infixEquation);
        this.symbols = symbolss;
        this.result = 0;
        tempCount = 0;
        System.out.println("infix: " + infixEquation);
    }

    public void doEquation() {
        String tempLabel = "";

        while (!this.infixEquation.isEmpty()) {
            char character = (char) this.infixEquation.remove();
            System.out.println("removing: " + character);
            switch (character) {
                case '+':
                case '-':
                case '/':
                case '*':
                    int position = Helpers.isInList(this.symbols, tempLabel);

                    if (position != -1 && !tempLabel.isEmpty()) {
                        System.out.println("Pusshing: " + tempLabel);
                        this.waitStack.push(this.symbols.get(position)); // adding accumulative label
                    }

                    System.out.println("Pusshing: " + character);
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
                    this.waitStack.push(character);
                    break;
                case ')':
                    int index = Helpers.isInList(this.symbols, tempLabel);

                    if (index != -1)
                        this.waitStack.push(this.symbols.get(index)); // adding accumulative label

                    this.doOperation();
                    tempLabel = "";
                    break;
                default:
                    tempLabel += character;
            }
        }

        result = ((MSymbols) this.waitStack.pop()).getValue();
        System.out.println("Result: " + result);
    }

    private void doOperation() {

        String tempInteger = "";
        boolean flag = true;

        while (!this.waitStack.isEmpty() && flag) {
            System.out.println("-> Size: " + this.waitStack.size());

            Object object = this.waitStack.pop();
            System.out.println("Op: " + object);

            if (!Helpers.isSymbol(object)) {
                switch ((char) object) {
                    case '+':
                        if (!tempInteger.isEmpty()) {
                            double newInt = ((MSymbols) this.waitStack.pop()).getValue();
                            double prevInt = Double.parseDouble(tempInteger);
                            flag = removeOpenParen();
                            tempCount++;
                            this.waitStack.push(new MSymbols("temp" + tempCount, newInt + prevInt));
//                            result = newInt + prevInt;
                        } else {
                            double newInt = ((MSymbols) this.waitStack.pop()).getValue();
                            result += newInt;
                        }
                        tempInteger = "";
                        System.out.println("Result: " + result);
                        logPeek();
                        break;
                    case '-':
                        if (!tempInteger.isEmpty()) {
                            double newInt = ((MSymbols) this.waitStack.pop()).getValue();
                            double prevInt = Double.parseDouble(tempInteger);
                            flag = removeOpenParen();

                            tempCount++;
                            this.waitStack.push(new MSymbols("temp" + tempCount, newInt - prevInt));
                        } else {
                            double newInt = ((MSymbols) this.waitStack.pop()).getValue();
                            result = newInt - result;
                        }
                        tempInteger = "";
                        System.out.println("Result: " + result);
                        logPeek();
                        break;
                    case '/':
                        if (!tempInteger.isEmpty()) {
                            double newInt = ((MSymbols) this.waitStack.pop()).getValue();
                            double prevInt = Double.parseDouble(tempInteger);
                            flag = removeOpenParen();

                            tempCount++;
                            this.waitStack.push(new MSymbols("temp" + tempCount, newInt / prevInt));
                        } else {
                            double newInt = ((MSymbols) this.waitStack.pop()).getValue();
                            result = newInt / result;
                        }
                        tempInteger = "";
                        System.out.println("Result: " + result);
                        logPeek();
                        break;
                    case '*':
                        if (!tempInteger.isEmpty()) {
                            double newInt = ((MSymbols) this.waitStack.pop()).getValue();
                            double prevInt = Double.parseDouble(tempInteger);
                            flag = removeOpenParen();

                            tempCount++;
                            this.waitStack.push(new MSymbols("temp" + tempCount, newInt * prevInt));
                        } else {
                            double newInt = ((MSymbols) this.waitStack.pop()).getValue();
                            result *= newInt;
                        }
                        tempInteger = "";
                        System.out.println("Result: " + result);
                        logPeek();
                        break;
                    case '(':
                        flag = false;
                        break;
                }
            } else {
                tempInteger += ((MSymbols) object).getValue();
                System.out.println("Value: " + tempInteger);
            }
        }
    }

    private void logPeek() {
        try {
            System.out.println("Peeking: " + ((MSymbols) this.waitStack.peek()).getValue());
        } catch (Exception e) {
            System.out.println("Peeking: " + this.waitStack.peek());
        }
    }

    private boolean removeOpenParen() {
        if (!Helpers.isSymbol(this.waitStack.peek())) {
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

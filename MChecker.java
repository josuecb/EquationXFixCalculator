import java.util.ArrayList;

/**
 * Created by Josue on 12/12/2016.
 */
public class MChecker {
    private MStack equationStack;
    private ArrayList<MSym> symbols;
    private MStack formattedStack;

    public MChecker(String equation, ArrayList<MSym> symbols) {
        this.formattedStack = new MStack();
        this.fillStack(equation);
        this.symbols = symbols;
    }

    private void fillStack(String equation) {
        this.equationStack = new MStack();

        for (int index = equation.length() - 1; index > -1; index--)
            this.equationStack.push(equation.charAt(index));
    }

    public MStack type() {
        while (!this.equationStack.isEmpty()) {
            char character = (char) this.equationStack.pop();

            char nextChar;
            try {
                nextChar = (char) this.equationStack.pop();
            } catch (Exception e) {
                this.formattedStack.push(character);
                break;
            }

            char findSign;
            try {
                findSign = (char) this.equationStack.peek();
            } catch (Exception e) {
                this.formattedStack.push(character);
                this.formattedStack.push(nextChar);
                break;
            }

            // check next character
            if (isCharacter(character) && isCharacter(findSign) && isCharacter(nextChar)) {
                this.formattedStack.push(character);

                this.equationStack.push(nextChar);
            } else if (isCharacter(character) && !isCharacter(findSign) && isCharacter(nextChar)) {
                this.formattedStack.push(character);
                // popping sign
                this.formattedStack.push(this.equationStack.pop());

                this.formattedStack.push(nextChar);
            }

            MStack temp = new MStack();
            while (this.equationStack.size() > 1)
                temp.push(this.equationStack.pop());

            temp.reverse();
            // put sign
            this.formattedStack.push(this.equationStack.pop());

            this.equationStack.clone(temp);
        }

        return this.formattedStack;
    }

    private boolean isCharacter(char character) {
        /*diagnostic*/
//        System.out.println("For " + character + " ");

        switch (character) {
            case '+':
            case '-':                       // precedence 1 contains + or -
            case '*':                       // precedence 2 contains * or /
            case '/':                       // pop the precedence
                return false;
            default:
                return true;
        }
    }
}

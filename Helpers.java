import java.util.ArrayList;

/**
 * Created by Josue on 12/13/2016.
 */
public class Helpers {
    public static boolean isOperator(char character) {
        switch (character) {
            case '+':
            case '-':
            case '*':
            case '/':
            case '^':
                return true;
            default:
                return false;
        }
    }

    public static boolean isSymbol(Object character) {
        return character instanceof MSymbols;
    }

    public static int isInList(ArrayList<MSymbols> symbols, String check) {
        int index = 0;
        for (MSymbols sym : symbols) {
            if (sym.isLabelInList(check))
                return index;
            index++;
        }

        return -1;
    }
}

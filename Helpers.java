import java.util.ArrayList;

/**
 * Created by Josue on 12/13/2016.
 */
public class Helpers {
    /**
     * This will check if character is operator or not
     *
     * @param character: character to be checked
     * @return true: if it is an operator
     */
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

    /**
     * Check if Object is instance of ExpVariable
     *
     * @param objectToCheck: is the object to be checked
     * @return true if it is instance of ExpVariable
     */
    public static boolean isVariableObject(Object objectToCheck) {
        return objectToCheck instanceof ExpVariable;
    }


    /**
     * Check if name is inside the list
     *
     * @param variables: Expression variable list
     * @param check:     string to be checked
     * @return return the index of variables if exists otherwise return -1
     */
    public static int isInList(ArrayList<ExpVariable> variables, String check) {
        int index = 0;
        for (ExpVariable sym : variables) {
            if (sym.isLabelInList(check))
                return index;
            index++;
        }

        return -1;
    }

    /**
     * It outputs a format log that i designed so it outputs everything beautifully
     * @param label: label of the log
     * @param notice: message of the log
     */
    public static void log(String label, String notice) {
        if (!label.isEmpty() && !notice.isEmpty())
            System.out.println(" -> " + label.substring(0, 1).toUpperCase() + label.substring(1) + ": " + notice.substring(0, 1).toUpperCase() + notice.substring(1) + ".");
    }

    /**
     * A separator
     */
    public static void separator(){
        System.out.println(" ============================ ");
    }
}

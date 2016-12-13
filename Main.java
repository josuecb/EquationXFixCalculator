public class Main {

    public static void main(String[] args) {
        EquationInputParser parser = new EquationInputParser();
        parser.getInput();

        MChecker checker = new MChecker(parser.getEquation(), parser.getSymbols());


//        System.out.println("Equation: " + parser.getEquation());
        MStack s = checker.type();
        s.reverse();
        while (!s.isEmpty()) {
            System.out.print(s.pop());
        }
    }


}

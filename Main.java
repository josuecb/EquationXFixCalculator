public class Main {

    public static void main(String[] args) {
        EquationInputParser parser = new EquationInputParser();
        parser.getInput();

        MChecker checker = new MChecker(parser.getEquation(), parser.getSymbols());


//        System.out.println("Equation: " + parser.getEquation());
//        System.out.println(checker.postfixToInfix());
        MEquation equation = new MEquation(checker.postfixToInfix(), parser.getSymbols());
        equation.doEquation();
//        "abc*+de/f*-"
//        applebc*+dg/f*-;apple=1;b=3;c=5;d=7;g=89;f=100
//        applecanadadri+3/;canadadri=1;apple=200
//        infix: ((apple+(b*c))-((d/g)*f))
//        (16 - ((
//        ac+co;ac=1;co=2
//        123+apple-;apple=1
    }


}

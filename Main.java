public class Main {

    public static void main(String[] args) {
        ExpInputParser parser = new ExpInputParser();
        parser.getInput();

        ExpChecker checker = new ExpChecker(parser.getEquation(), parser.getVariables());

        Equation equation = null;

        try {
            equation = new Equation(checker.getInfix(), parser.getVariables());
            System.out.println("\n\nResult of the equation is: " + equation.doEquation());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // examples you can put

//        "abc*+de/f*-"
//        */-applebc-+de/fg;apple=1;b=3;c=5;d=7;g=89;f=100;e=12
//        applebc*+dg/f*-;apple=1;b=3;c=5;d=7;g=89;f=100
//        applecanadadri+3/;canadadri=1;apple=200
//        infix: ((apple+(b*c))-((d/g)*f))
//        ac+co;ac=1;co=2
//        123+apple-;apple=1
    }
//    (a–b)/c*(d + e – f / g)


}

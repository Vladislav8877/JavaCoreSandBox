package s03_streamApi.t01_steamApiBasick;

public class Steam {

    @FunctionalInterface
    public interface MyCalculator {
        double calculator(double a, double b);
    }

    MyCalculator calculator = new MyCalculator() {
        @Override
        public double calculator(double a, double b) {
            return a + b;
        }
    };

    public static void main(String[] args) {
        MyCalculator calculator2 = (a, b) -> a + b;
        System.out.println(calculator2.calculator(2, 2));
    }
}



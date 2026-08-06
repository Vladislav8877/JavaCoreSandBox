package s04_testing.t01_myOldExamples;

public class Debug {
    public static void main(String[] args) {
        one();
    }
        public static void one(){
            int a = 10;
            int b = 20;
            System.out.println("one: a= "+a+" b= "+b);

            two();
            b=1000;
            System.out.println("one: a+b= "+(a+b));
        }
        public static void two(){
            int c = 30;
            int d = 40;
            System.out.println("one: c= "+c+" d= "+d);

            three();
            c = 100;
            System.out.println("two: c+d= "+(c+d));
        }
        public static void three(){
            int e = 98;
            int f = 88;
            System.out.println("one: e= "+e+" f= "+f);
        }
    }


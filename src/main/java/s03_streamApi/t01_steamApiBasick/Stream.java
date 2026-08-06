package s03_streamApi.t01_steamApiBasick;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Locale.filter;

@FunctionalInterface
interface Greeting {
    void sayHello(String name);
}

public class Stream {
    public static void main(String[] args) {

        Greeting greet = new Greeting() {
            @Override
            public void sayHello(String name) {
                System.out.println("привет из анонимного класса - "+name);
            }
        };
        Greeting greetTwo = (name) -> System.out.println("Хай, "+name+"! Это лямбда!");

        greet.sayHello("Vlad");
        greetTwo.sayHello("Vlad");
    }
}

@FunctionalInterface
interface MathOperation {
    int operation(int a, int b);
}

class TaskOne {
    public static void main(String[] args) {

        MathOperation one = new MathOperation() {
            @Override
            public int operation(int a, int b) {
                return a+b;
            }
        };

        MathOperation two = (a,b) -> a * b;

        System.out.println(one.operation(10,20));
        System.out.println(two.operation(2,2));
    }
}

@FunctionalInterface
interface StringJoiner {
    String join(String s1, String s2);
}

class TaskTwo {
    public static void main(String[] args) {

        StringJoiner one = new StringJoiner() {
            @Override
            public String join(String s1, String s2) {
                return s1+" "+s2;
            }
        };

        StringJoiner two = (s1,s2) -> s1+" "+s2;

        System.out.println(one.join("Hello","World"));
        System.out.println(two.join("This is","My car"));
    }
}
//---------------

class TaskThree {
    public static void main(String[] args) {
        Predicate<Integer> isPositive = n -> n > 0;
        System.out.println("5 положительное число?: "+isPositive.test(5));
        System.out.println("-10 положительное число?: "+isPositive.test(-10));
    }
}

class TaskFour {
    public static void main(String[] args) {
        Predicate<String> text = string -> string.length() > 10;
        System.out.println("\"Я больше 10 символов?\": "+text.test("Я больше 10 строк"));
        System.out.println("Java - больше 10 символов?: "+text.test("Java"));
    }
}

class TaskFive {
    public static void main(String[] args) {
        Predicate<Integer> number = n -> n % 2 == 0;
        System.out.println("Число (8) четное?: "+number.test(8));
        System.out.println("Число (15) чётное?: "+number.test(15));
    }
}
// ----------

class TaskSix {
    public static void main(String[] args) {
        Function<String, Integer> stringLength = s -> s.length();
        int len = stringLength.apply("Java Core");
        System.out.println("Длина строки: "+len);

        Function<Integer, String> formatted = value -> "Результат вычислений: "+value;
        String result = formatted.apply(100);
        System.out.println(result);
    }
}

class TaskSeven {
    public static void main(String[] args) {
        Function<Double, String> doubleString = doub -> doub+"$";
        String result = doubleString.apply(100.0);
        System.out.println(result);
    }
}

class TaskEight {
    public static void main(String[] args) {
        Function<Integer, Integer> cub = num -> (int) Math.sqrt(num); // возвращает double
        Function<Integer, Integer> cubTwo = num -> num * num;
        int result = cub.apply(9);
        int resultTwo = cubTwo.apply(9);
        System.out.println(result);
        System.out.println(resultTwo);
    }
}

// /-----------/

class Basick {
    public static void main(String[] args) {

        List<Integer> numbers = List.of(1,2,3,4,5);

        //Императивный стиль
        List<Integer> numbersTwo = new ArrayList<>();
        for (int num : numbers) {
            if (num % 2 == 0) {
                numbersTwo.add(num);
            }
        }

        //Функциональный стиль
        List<Integer> numbersThree = numbers.stream()
                .filter(num -> num % 2 == 0)
                .toList();
    }
}

// аналог Predicate
@FunctionalInterface
interface MessageFilter {
    boolean check(String message);
}

// аналог Consumer
@FunctionalInterface
interface MessageHandler {
    void handle(String message);
}

class MessageBroker {

    public static void processMessages(List<String> messages, MessageFilter filter, MessageHandler handler) {
        for (String msg : messages) {
            if (filter.check(msg)) {
                handler.handle(msg);
            }
        }
    }

    public static void main(String[] args) {

        List<String> logs = Arrays.asList(
                "Error: server down",
                "Info: user logged in",
                "Error: null pointer",
                "Warning: disk full"
        );

        System.out.println("--- ВЫЗОВ 1: Только ошибки ---");
        processMessages(logs, msg -> msg.startsWith("Error"),msg -> System.out.println(msg));

        System.out.println("\n--- ВЫЗОВ 2: Сообщения короче 20 символов капсом ---");
        processMessages(logs, msg -> msg.length() < 20, msg -> System.out.println(msg.toUpperCase()));
    }
}


class MessageBrokerTwo {

    public static void processMessage(List<String> messages, Predicate<String> filter, Consumer<String> handler) {
        messages.stream()
                .filter(filter)
                .forEach(handler);

    }

    public static void main(String[] args) {
        List<String> logs = Arrays.asList(
                "Error: server down",
                "Info: user logged in",
                "Error: null pointer",
                "Warning: disk full"
        );

        System.out.println("--- ВЫЗОВ 1: Только ошибки ---");
        processMessage(logs, msg -> msg.startsWith("Error"), msg -> System.out.println(msg));
        System.out.println("\n--- ВЫЗОВ 2: Сообщения короче 20 символов капсом ---");
        processMessage(logs, msg -> msg.length() < 10, msg -> System.out.println(msg.toUpperCase()));
    }
}

class MessageBrokerThree {

    public static List<String> extraErrorCodes(List<String> messages) {
        return messages.stream()
                .filter(msg -> msg.startsWith("Error"))
                .map(msg -> msg.replace("Error:","").toUpperCase())
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<String> logs = Arrays.asList( // добавить удалить нельзя, set(index, value); - можно // List.of - полностью immutable (add,remove,set - нельзя)
                "Error: server down",
                "Info: user logged in",
                "Error: null pointer",
                "Warning: disk full"
        );
        System.out.println(extraErrorCodes(logs));
    }
}

class MessageBrokerFour {
    public void filterGoodMarks(List<Integer> marks) {
        List<Integer> goodMarks = new ArrayList<>();
        for (int mark : marks) {
            if (mark > 3) {
                goodMarks.add(mark);
            }
        }
        System.out.println("goodMarks: " + goodMarks);
    }


    public static void main(String[] args) {
        MessageBrokerFour good = new MessageBrokerFour();
        List<Integer> marks = Arrays.asList(2,3,4,4,5);
        good.filterGoodMarks(marks);

        List<Integer> bestMark = marks.stream()
                .filter(mark -> mark > 3)
                .toList();
        System.out.println("bestMarks: "+bestMark);
    }
}
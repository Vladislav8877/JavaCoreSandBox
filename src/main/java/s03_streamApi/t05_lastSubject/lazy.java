
package s03_streamApi.t05_lastSubject;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class lazy {
    public static void main(String[] args) {


    /*
    List<Integer> numbers =  ... миллион чисел от 1 до 1000000 ... ;

    int result = numbers.stream()
            .filter(n -> n > 100)        // Промежуточная
            .map(n -> n * 2)             // Промежуточная
            .findFirst()                 // ТЕРМИНАЛЬНАЯ (Найти первый и остановиться)
            .orElse(0);

            метод peek() принимает лямбду Consumer
     */


    List<String> names = Arrays.asList("Alice", "Bob", "Carl", "David");
    Stream<String> nameStream = names.stream();
    nameStream.forEach(System.out::println);

    try {
        nameStream.forEach(System.out::println);
    } catch (IllegalStateException e) {
        System.err.println("IllegalStateException");
    }

    // решение - не сохранять стрим в переменную и вызвать повторно

        names.stream().forEach(System.out::println);
    }
}

class Peek {
    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(1,2,3,4,5);
        List<Integer> result = numbers.stream()
                .peek(n -> System.out.println("Начальное значение: "+n))
                .filter(n -> n % 2 == 0)
                .map(n -> n * n)
                .peek(n -> System.out.println("После map: "+n))
                .toList();
        System.out.println(result);
    }
}

class Home {
    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        int res = numbers.stream()
                .peek(n -> System.out.println("Начальное значение: "+n))
                .filter(n -> n % 2 == 0)
                .map(n -> n * n)
                .findFirst()
                .orElse(0);
    }
}


class GeminiTasks {
    public static void main(String[] args) {

        List<String> words = List.of("Apple", "Banana", "Apricot", "Cherry", "Avocado", "Date");
        List<String> aWords = words.stream()
                .filter(n -> n.startsWith("A"))
                .sorted()
                .toList();
        System.out.println(aWords);

        //---

        words.stream().filter(n -> n.length() > 5).sorted().forEach(System.out::println);

        //---

        boolean hasCherry = words.stream().anyMatch(n -> n.contains("Cherry"));
        // - если будет CherryPie вернет true(потому что найдет подстроку Cherry)
        // - если нужно найти конкретно Cherry используй equals("Cherry");
        System.out.println(hasCherry);

        //---

        String firstEWord = words.stream().filter(n -> n.endsWith("e")).findFirst().orElse("не найдено");
        System.out.println(firstEWord);
    }
}

class GeminiTasksTwo {
    public static void main(String[] args) {

        List<Integer> numbers = List.of(5, 12, 8, 12, 20, 5, 3, 15);
        List<String> words = List.of("Java", "Stream", "API", "Java", "Code");

        List<Integer> multipliedNumbers = numbers.stream()
                .map(n -> n*10)
                .sorted()
                .toList();
        System.out.println(multipliedNumbers);

        //---

        long uniqueCount = words.stream().distinct().count();
        System.out.println(uniqueCount);

        //---

        boolean allPositive = numbers.stream().allMatch(n -> n > 0);
        System.out.println(allPositive);

        //---

        int maxNumber = numbers.stream().max(Integer::compareTo).orElse(0); // .max((a,b) -> a.compareTo(b)).or...
        System.out.println(maxNumber);

        //---

        numbers.stream().skip(2).limit(3).forEach(System.out::println);
    }
}

class UniqueStream {
    public static void main(String[] args) {

        List<Integer> numbers = List.of(5, 12, 8, 12, 20, 5, 3, 15);
        int sumOfNumbers = numbers.stream()
                .mapToInt(Integer::intValue)
                .sum();
        System.out.println(sumOfNumbers);

        double averageOfNumbers = numbers.stream()
                .mapToDouble(Integer::intValue)
                .average()
                .orElse(0.0);
        System.out.println(averageOfNumbers);
    }
}
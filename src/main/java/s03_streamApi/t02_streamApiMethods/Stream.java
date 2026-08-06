package s03_streamApi.t02_streamApiMethods;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Locale.filter;

class StreamExamples {
    public static void main(String[] args) {
        // первый метод - из коллекций
        List<String> namesOne = Arrays.asList("Alice", "Bob");
        Stream<String> streamOne = namesOne.stream(); // берет готовы List и по одному выкладывает элементы на ленту

        // второй метод - из массивов
        String[] nameTwo = {"Alice", "Bob"};
        Stream<String> streamTwo = Arrays.stream(nameTwo); //

        // третий метод - из отдельных элементов
        Stream<Integer> streamThree = Stream.of(10,20,30);

        // четвертый метод - из генераторов
        Stream<Double> randomDouble = Stream.generate(Math::random);
        randomDouble.limit(5).forEach(System.out::println);

        // пятый метод - из итератора по правилам
        Stream<Integer> randomNumbers = Stream.iterate(0, n -> n + 2);
        randomNumbers.limit(5).forEach(System.out::println);
    }
}

class StreamBasics {
    public static void main(String[] args) {

        // из коллекции
        List<String> names = Arrays.asList("Alice", "Bob","Mark");
        Stream<String> streamFromList = names.stream();
        System.out.println("Стрим из коллекции:");
        streamFromList.forEach(System.out::println);

        // из массива
        String[] namesArray =  {"Alice","Bob","Mark"};
        Stream<String> streamFromArray = Arrays.stream(namesArray);
        System.out.println("\nСтрим из массива:");
        streamFromArray.forEach(System.out::println);

        // из генератора
        System.out.println("\nСтримы сгенерированные случайными числами:");
        Stream<Double> randomNumbers = Stream.generate(Math::random);
        randomNumbers.limit(5).forEach(System.out::println);

        // из генератора Stream.iterate()
        System.out.println("\nСтрим, создаваемый методом iterate:");
        Stream<Integer> evenNumbers = Stream.iterate(0, n -> n + 2);
        evenNumbers.limit(5).forEach(System.out::println);
    }
}

class StreamBasics2 {
    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(1,2,3,4,5);
        List<Integer> evenNumbers = numbers.stream()
                .filter(n-> n % 2 == 0)
                .collect(Collectors.toList()); // Терминальная операция собирает отфильтрованные элементы в новый список
        System.out.println("Чётные числа: "+evenNumbers);
        // -----
        List<String> names = Arrays.asList("Alice","Bob","Mark");
        List<String> upperCaseNames = names.stream()
                .map(String::toUpperCase) // Метод map вызывает toUpperCase() для каждого элемента, преобразуя его в верхний регистр
                .collect(Collectors.toList());
        System.out.println("Имена в верхнем регистре: "+upperCaseNames);
        // -----
        List<Integer> num = Arrays.asList(1,2,3,4,5);
        List<Integer> limitNum = num.stream()
                .limit(3)
                .collect(Collectors.toList());
        System.out.println("Первые три элемента: "+limitNum);
    }
}

// -----------------------

class StreamTraining {
    public static void main(String[] args) {
        List<Integer> numbers = Stream.iterate(10, n -> n * 2).limit(7).collect(Collectors.toList());
        // метод .collect берет конвейер Stream, упаковывает числа в List и возвращает тип List
        //-----
        Stream<String> names = Stream.of("admin","guest","root");
        names.filter(n -> n.length() > 4)
                .map(word -> "_ID_"+word.toUpperCase())
                .forEach(System.out::println);
    }
}

class HomeWork {
    public static void main(String[] args) {

        List<String> list = List.of("Apple", "Computer", "Cloud");
        String[] massive = {"qwerty", "table", "pen"};
        Stream<String> stream = Stream.of("Diamond","Cooking","Dinner");
        List<String> anotherStream = stream
                .filter(word -> word.length() < 7)
                .map(String::toUpperCase)
                .toList();
        System.out.println(anotherStream);
        stream.filter(word -> word.length() < 6).map(String::toLowerCase).forEach(System.out::println);
        // exception - две терминальные операции не могут существовать для одного стрима

        List<String> filteredList = list.stream()
                .filter(word -> word.length() > 4)
                .map(String::toUpperCase)
                .toList();
        System.out.println(filteredList);

        List<String> filteredMassive = Arrays.stream(massive)
                .filter(word -> word.length() < 4)
                .map(String::toUpperCase)
                .toList();
        System.out.println(filteredMassive);
    }
}
package s03_streamApi.t03_midlOperation;

import java.util.Arrays;
import java.util.List;

class MapVSFlatMapExample {
    public static void main(String[] args) {

        List<String> sentences = Arrays.asList("Hello Word","Java Streams");

        List<String[]> wordsArray = sentences.stream()
                .map(sentence -> sentence.split(" "))
                .toList();
        System.out.println("MAP: "+wordsArray);

        List<String> wordsList = sentences.stream()
                .flatMap(sentence -> Arrays.stream(sentence.split(" ")))
                .toList();
        System.out.println("FLATMAP: "+wordsList);
    }
}

class Department {
    private String name;
    private List<String> employees;

    public Department(String name, List<String> employees) {
        this.name = name;
        this.employees = employees;
    }

    public List<String> getEmployees() {return employees;}
}

class StreamPractice {
    public static void main(String[] args) {

        Department it = new Department("IT", Arrays.asList("Anna", "Vlad", "John"));
        Department hr = new Department("HR", Arrays.asList("Anna", "Boris"));
        Department sales = new Department("Sales", Arrays.asList("John", "Mike", "Vlad"));

        // Упаковываем все три отдела в один общий список (Компанию).
        List<Department> company = Arrays.asList(it, hr, sales);

        // Попытка получить список всех имен через map
        List<List<String>> wrongResult = company.stream() // Запускаем конвейер. Сейчас по нему едут 3 объекта Department
                .map(Department::getEmployees)            // MAP берет Department, достает из него List<String> и кладет этот лист на ленту.
                .toList();                                // Робот-упаковщик собирает всё в финальную коробку.

        // Мы получили матрешку: Список списков (List<List<String>>).
        // Мы не можем здесь применить .sorted() или .distinct(), потому что люди заперты в списках.
        System.out.println("Результат map: " + wrongResult);

        List<String> correctResult = company.stream()

                // FLATMAP вступает в игру.
                // Он берет Department (dept).
                // Вызывает dept.getEmployees() (получает список людей).
                // Вызывает у этого списка .stream() (превращает список в маленький конвейер).
                // И самое главное: flatMap ВЫВАЛИВАЕТ все имена из этих маленьких конвейеров на ГЛАВНУЮ ленту!
                .flatMap(dept -> dept.getEmployees().stream())
                .distinct()
                .sorted()
                .toList();
        System.out.println("Результат flatMap: " + correctResult);
    }
}
//------

class User {
    String name;
    int age;

    User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {return name;}
    public int getAge() {return age;}
}

class UserExample {
    public static void main(String[] args) {
        List<User> users = Arrays.asList(
                new User("Oleg",25),
                new User("Dima",30),
                new User ("Sasha",40),
                new User ("Anna",16),
                new User("Kostya",22)
        );

        List<String> namesUsers = users.stream()
                .filter(user -> user.getAge() > 25)
                .map(User::getName) // преобразует объект -> в его имя
                .sorted()
                .distinct()
                .toList();
        System.out.println("Полная сортировка: "+namesUsers);
    }
}

class NumberStreamExample {
    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7,8,9);

        List<Integer> result = numbers.stream()
                .filter(num -> num % 2 == 0)
                .map(num -> num * num)
                .sorted()
                .toList();
        System.out.println("Result: "+result);
    }
}

// ------

class HomeWork {
    public static void main(String[] args) {

        List<String> str = Arrays.asList(
                "GoogLe","MackBOOK","APPLe",
                "LapToP","bOOK","qwertyTopic",
                "gad","one","amm...","okeY"
        );

        List<String> words = str.stream()
                .filter(word -> word.length() < 5)
                .map(String::toUpperCase)
                .distinct()
                .limit(3)
                .toList();
        System.out.println("Исходный список: "+str);
        System.out.println("Результат обработки: "+words);
    }
}

class TagAnalyzer {
    public static void main(String[] args) {
        List<String> rawTags = Arrays.asList(
                "java, spring, sql",
                "Python, machine learning , SQL ",
                " java , docker , AWS "
        );

        List<String> usersMessages = rawTags.stream()
                .flatMap(word -> Arrays.stream(word.split(","))) // flatMap взял строку "java, spring, sql",
                .map(word -> word.toLowerCase().trim()) // разорвал её split-ом на массив ["java", " spring", " sql"] и высыпал эти 3 элемента на ленту.
                .distinct()
                .sorted()
                .toList();
        System.out.println(usersMessages);
    }
}

class Server {
    private String name;
    private String type; // "PROD" или "TEST"
    private List<String> admins;

    public Server(String name, String type, List<String> admins) {
        this.name = name;
        this.type = type;
        this.admins = admins;
    }
    public String getType() { return type; }
    public List<String> getAdmins() { return admins; }
}

class SecurityAudit {
    public static void main(String[] args) {
        List<Server> servers = Arrays.asList(
                new Server("Server-1", "PROD", Arrays.asList("vlad", "anna")),
                new Server("Server-2", "TEST", Arrays.asList("john", "vlad")),
                new Server("Server-3", "PROD", Arrays.asList("anna", "mike", "superadmin")),
                new Server("Server-4", "TEST", Arrays.asList("mike"))
        );

        List<String> prod = servers.stream()
                .filter(server -> server.getType().equals("PROD"))
                .flatMap(server -> server.getAdmins().stream())
                .distinct()
                .toList();
        System.out.println(prod);
    }
}
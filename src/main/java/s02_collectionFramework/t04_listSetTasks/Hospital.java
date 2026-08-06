package s02_collectionFramework.t04_listSetTasks;

import java.util.*;

public class Hospital {
    public static void main(String[] args) {

        List<String> patients = new LinkedList<>();
        patients.add("Григорий");
        patients.add("Борис");
        patients.add("Виктор");
        patients.add("Антон");
        System.out.println("Очередь к доктору: "+patients);
        patients.add(1,"Аристарх");
        patients.remove(0);
        System.out.println("Очередь к доктору спустя время: "+patients);
    }
}

class Car {

    String plate;
    String color;

    public Car (String plate, String color) {
        this.plate = plate;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Номер машины: "+plate+" || Цвет машины: "+color;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Car car = (Car) obj;
        return plate.equals(car.plate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plate);
    }
}

class Main {
    public static void main(String[] args) {
        Car carOne = new Car("А777АА","black");
        Car carTwo = new Car("А777АА","blue");

        Set<Car> parking = new HashSet<>();
        parking.add(carOne);
        parking.add(carTwo);
        System.out.println("Parking: "+parking);
        System.out.println("Parking car: "+parking.contains(carTwo));

    }
}

class Message {
    public static void main(String[] args) {

        List<String> chatLog = new ArrayList<>();
        chatLog.add("Анна");     // Первая написала Анна
        chatLog.add("Борис");    // Потом Борис
        chatLog.add("Анна");     // Анна ответила
        chatLog.add("Виктор");   // Ворвался Виктор
        chatLog.add("Борис");    // Борис ответил Виктору
        chatLog.add("Григорий"); // Пришел Григорий

        Set<String> sortedChat = new LinkedHashSet<>(chatLog);
        System.out.println(sortedChat);
    }
}

// 1. Подписываем контракт: "Этот класс можно сравнивать с другими Player"
class Player implements Comparable<Player> {

    String nickname;
    int score;

    public Player(String nickname, int score) {
        this.nickname = nickname;
        this.score = score;
    }

    @Override
    public String toString() {
        return nickname + " ( "+score+" )";
    }

    // 2. Тот самый метод-компас для TreeSet!
    // "this" - это текущий игрок (Я).
    // "other" - это игрок, с которым нас сравнивают (Тот парень).
    @Override
    public int compareTo(Player other) {
        // У класса Integer есть удобный метод compare, который сам возвращает -1, 0 или 1
        int scoreResult = Integer.compare(this.score, other.score);
        // -1 - Я меньше чем тот парень, ставь меня влево
        // 1 - Я больше чем тот парень, ставь меня вправо
        // 0 - Мы равны (если TreeSet получит 0, он подумает что это дубликат и удалит второго игрока)

        if(scoreResult != 0) { // если кол-во очков разное - вопрос решен!
            return scoreResult;
        }

        // если они равны по Integer будет сравнивать по String
        // У класса String уже написан свой compareTo (по буквам), используем его:
        return this.nickname.compareTo(other.nickname);
    }
}

class Start {
    public static void main(String[] args) {

        Set<Player> top = new TreeSet<>();
        top.add(new Player("Viper",1500));
        top.add(new Player("Dragon",2000));
        top.add(new Player("Alpha",1500));

        System.out.println("TreeSet: "+top);
    }
}

class Task implements Comparable<Task> {

    int priority;
    String title;

    public Task(int priority, String title) {
        this.priority = priority;
        this.title = title;
    }

    @Override
    public String toString() {
        return title+" ("+priority+")";
    }

    @Override
    public int compareTo(Task other) {
        int priorityResult = Integer.compare(this.priority, other.priority);
        if(priorityResult != 0) {
            return priorityResult;
        }

        return this.title.compareTo(other.title);
    }


}

class Starts {
    public static void main(String[] args) {

        Set<Task> mainTask = new TreeSet<>();
        mainTask.add(new Task(3,"Уборка офиса"));
        mainTask.add(new Task(1,"Сделать отчет продаж"));
        mainTask.add(new Task(1,"Поздравить начальника"));
        mainTask.add(new Task(2,"Сходить на митинг"));

        System.out.println("TreeSet: "+mainTask);
    }
}

class Log {
    public static void main(String[] args) {

        List<String> rawLogs = new ArrayList<>(List.of(
                "Яблоко","Молоко","Яблоко","Хлеб","Молоко","Сыр"
        ));

        System.out.println("ArrayList: "+rawLogs);

        Set<String> linkedHashSet = new LinkedHashSet<>(rawLogs);
        System.out.println("LinkedHashSet: "+linkedHashSet);

        Set<String> treeSet = new TreeSet<>(linkedHashSet);
        System.out.println("TreeSet: "+treeSet);

        //Внутри стандартных классов String Integer Double метод comparable уже переопределен
        //И TreeSet сортирует значение этих классов согласно этому методу
        //А мои личные классы Car Task Playe глупые, и я сам должен переопределить метод comparable
    }
}
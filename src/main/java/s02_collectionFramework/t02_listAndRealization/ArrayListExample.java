package s02_collectionFramework.t02_listAndRealization;

import java.util.*;

public class ArrayListExample {
    public static void main(String[] args) {

        List<String> shoppingList = new ArrayList<>();
        shoppingList.add("Яйцо");
        shoppingList.add("Курица");
        shoppingList.add("Масло");

        System.out.println("Первый продукт: "+shoppingList.getFirst());

        shoppingList.add(1,"Сыр");
        System.out.println("Список после вставки: "+shoppingList);

        shoppingList.remove(2);
        System.out.println("Список после удаления: "+shoppingList);
    }
}

class LinkedListExample {
    public static void main(String[] args) {

        List<String> taskList = new LinkedList<>();
        taskList.add("Проснуться");
        taskList.add("Позавтракать");
        taskList.add("Пойти на работу");

        System.out.println("Первое действие: "+taskList.get(0));


        // LinkedList добежит до элемента [1], разорвет связи между "Проснуться" и "Позавтракать",
        // и аккуратно свяжет их через новый вагон "Почистить зубы". Никаких сдвигов!
        taskList.add(1,"Почистить зубы");
        System.out.println("Список после вставки: "+taskList);

        taskList.remove("Пойти на работу");
        System.out.println("Список после удаления: "+taskList);
    }
}

class Example {
    public static void main(String[] args) {

        List<String> list;

        list = new ArrayList<>();
        list.add("First");
        list.add("Second");
        System.out.println("ArrayList: "+list);

        list = new LinkedList<>(list);
        list.add("Third");
        list.add("Fourth");
        System.out.println("LinkedList: "+list);
    }
}

class LinkedListLikeQueueExample  {
    public static void main(String[] args) {

        Deque<String> browsHistory = new LinkedList<>();
        browsHistory.addFirst("google.com");
        browsHistory.add("stackoverflow.com");

        System.out.println("LinkedList: "+browsHistory);

        String lastVisited = browsHistory.pollLast();
        System.out.println("LastWeb: "+lastVisited);
        System.out.println("LinkedList: "+browsHistory);
    }
}

class GeminiTasks {
    public static void main(String[] args) {

        List<String> museum = new ArrayList<>();
        museum.add("Меч");
        museum.add("Лук");
        museum.add("Посох");
        museum.add("Щит");

        System.out.println("Хранитель достает артефакт под номером(2): "+museum.get(1)+"\n"+museum); // эта операция 0(1)

        museum.add(0,"Шлем");
        System.out.println("Хранителю передали шлем и он положил его на полку под номером (1): "+museum); // это операция 0(n)
        System.out.println("-------------------------------");

        List<String> musics = new LinkedList<>();
        musics.add("Новая волна");
        musics.add("Небесные поля");
        musics.add("Унисон");

        System.out.println("Список музыки на вечер: "+musics); // это операция 0(n)

        musics.add(1,"Секретная музыка"); // это операция 0(n) - потому что идет поиск элемента(перебор)
        System.out.println("Внесли корректировки: "+musics);

        musics.removeLast(); // это операция 0(1)
        // musics.remove("Унисон"); - для Java 17 и ниже
        System.out.println("Внесли корректировки второй степени: "+musics);
    }
}

package s02_collectionFramework.t09_betterSolutionForYourProject;

import java.util.*;

public class Collections {
    public static void main(String[] args) {

        List<Integer> arrayList = new ArrayList<>();
        Set<Integer> hashSet = new HashSet<>();
        int size = 100_000;
        for (int i = 0; i < size; i++) {
            arrayList.add(i);
            hashSet.add(i);
        }

        int target = size - 1;
        long startTime = System.nanoTime(); // замеряем в наносекундах скорость операции между startTime и durationList
        boolean foundList = arrayList.contains(target);
        long durationList = System.nanoTime() - startTime; // получаем разницу между временем до операции и после(запуск секундомера)
        System.out.println("ArrayList содержит элемент?: " + foundList+". Время поиска: "+durationList+" наносекунд");

        startTime = System.nanoTime(); // одна миллисекунда == 1_000_000 наносекунд, поэтому метод currentTimeMillis() покажет 0
        boolean foundSet = hashSet.contains(target);
        long durationSet = System.nanoTime() - startTime;
        System.out.println("HashSet содержит элемент?: "+foundSet+". Время поиска: "+durationSet+" наносекунд");
    }
}

class CollectionsTwo {
    public static void main(String[] args) {

        Map<String, String> phoneBook = new HashMap<>();
        phoneBook.put("Alica","123-123");
        phoneBook.put("Dima","234-234");
        phoneBook.put("Daniel","456-456");

        String key = "Daniel";
        if (phoneBook.containsKey(key)) {
            System.out.println("Найден номер для: "+key+" - "+phoneBook.get(key));
        } else {
            System.out.println("Запись не найдена...");
        }
        // Такой подход полезен когда нужно быстро находить ассоциированные данные
    }
}

class CollectionsThree {
    public static void main(String[] args) {

        List<Integer> numbers = new ArrayList<>();
        int count = 50_000;
        for (int i = 0; i < count; i++) {
            numbers.add(i);
        }
        numbers.add(25000);

        Set<Integer> hashSet = new HashSet<>(numbers);
        boolean dubFound = hashSet.size() != numbers.size();
        System.out.println("Дубликаты обнаружены в HashSet?: "+dubFound);
    }
}

class Task {
    public static void main(String[] args) {

        List<String> usersFromDb = new ArrayList<>();
        usersFromDb.add("Daniel");
        usersFromDb.add("Angelina");
        usersFromDb.add("Vika");

        List<String> usersFromApi = new ArrayList<>();
        usersFromApi.add("Inna");
        usersFromApi.add("Daria");
        usersFromDb.add("Daniel");
        System.out.println("UsersFromDb равен UsersFromApi?: "+usersFromDb.equals(usersFromApi));
        System.out.println("UsersFromDb равен по размеру с UsersFromApi?: "+(usersFromDb.size() == usersFromApi.size()));

        Set<String> dbSet = new HashSet<>(usersFromDb);
        Set<String> apiSet = new HashSet<>(usersFromApi);
        System.out.println("Set равны?: "+dbSet.equals(apiSet));

        Set<String> commonUsers = new HashSet<>(dbSet);
        commonUsers.retainAll(apiSet); // оставляем только тех, кто есть в Api
        System.out.println("Пользователи в обоих источниках: "+commonUsers);

        Set<String> onlyBdSet = new HashSet<>(dbSet);
        onlyBdSet.removeAll(apiSet); // удаляем всех кто пришел из Api
        System.out.println("Пользователи только в БД: "+onlyBdSet);
    }
}
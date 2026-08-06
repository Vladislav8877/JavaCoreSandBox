package s02_collectionFramework.t06_mapTasks;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class Storage {
    public static void main(String[] args) {

        Map<String, Integer> stock = new HashMap<>();
        stock.put("Apple",100);
        stock.put("Banana", 100);
        stock.put("Orange",100);
        stock.put("Water",100);

        for (Map.Entry<String, Integer> entry : stock.entrySet()) {
            System.out.println("На складе: "+entry.getKey()+" || Количество: "+entry.getValue());
        }

        System.out.println("На склад поступила вода...");
        String newItem = "Water";
        stock.put(newItem, stock.getOrDefault(newItem, 0)+100); // "Дай мне значение по этому ключу. Но если такого ключа нет, не возвращай мне null, а верни ноль!".
        System.out.println("Количество воды: "+stock.get("Water"));

        String itemToSeil = "Banana";
        int sellAmount = 30;
        System.out.println("Запрос на покупку: "+itemToSeil+" || "+sellAmount+"шт.");

        if(!stock.containsKey(itemToSeil)) {
            System.out.println("Ошибка товара: "+itemToSeil+" --> не существует на складе!");
        } else {
            int currentAmount = stock.get(itemToSeil);

            if(currentAmount < sellAmount) {
                System.out.println("Ошибка: недостаточно товара! В наличии всего: "+currentAmount);
            } else {
                stock.put(itemToSeil, currentAmount - sellAmount);
                System.out.println("Успешна продажа! Остаток: "+itemToSeil+" || "+stock.get(itemToSeil));
            }
        }


        String itemToSeil2 = "Water";
        int sellAmount2 = 50;
        System.out.println("Запрос на покупку: "+itemToSeil2+" || Количество: "+sellAmount2);

        if(!stock.containsKey(itemToSeil2)) {
            System.out.println("Ошибка продажи! Товара: "+itemToSeil2+" --> не существует на складе!");
        } else {
            int currentAmount = stock.get(itemToSeil2);

            if(currentAmount < sellAmount2) {
                System.out.println("Ошибка продажи! Остаток: "+itemToSeil2+" || "+stock.get(itemToSeil2));
            } else {
                stock.put(itemToSeil2, currentAmount - sellAmount2);
                System.out.println("Успешная продажа! Остаток: "+itemToSeil2+" || "+stock.get(itemToSeil2));
            }
        }

        for (Map.Entry<String, Integer> entry : stock.entrySet()) {
            System.out.println("На складе: "+entry.getKey()+" || Количество: "+entry.getValue());
        }
    }
}

class Task {
    public static void main(String[] args) {

        String[] words = {"яблоко", "банан", "яблоко", "апельсин", "банан", "яблоко", "киви"};

        Map<String, Integer> items = new HashMap<>();

        for(int i = 0; i < words.length; i++) {
            items.put(words[i], items.getOrDefault(words[i],0)+1);
        }

        /* for(String word : words) {
            items.put(word, items.getOrDefault(word,0)+1);
        }
        */

        for(Map.Entry<String, Integer> entry : items.entrySet()) {
            System.out.println("Key: "+entry.getKey()+ " || Value: "+entry.getValue());
        }
    }
}

class TaskTwo {
    public static void main(String[] args) {

        Map<String, String> questions = new LinkedHashMap<>();
        questions.put("Анна","Не работает интернет!");
        questions.put("Борис","Забыл пароль!");
        questions.put("Анна","Ой, все заработало спасибо!");

        for (Map.Entry<String, String> entry : questions.entrySet()) {
            System.out.println("Пользователь: "+entry.getKey()+ " || Вопрос: "+entry.getValue());
        }
    }
}

class TaskThree {
    public static void main(String[] args) {

        Map<LocalTime, String> dailyDay = new TreeMap<>();
        dailyDay.put(LocalTime.of(14,30),"Обед с клиентом");
        dailyDay.put(LocalTime.of(9,0),"Утренняя планерка");
        dailyDay.put(LocalTime.of(18,0),"Поход в зал");
        dailyDay.put(LocalTime.of(18,15),"Созвон с командой");
        //TreeMap дернет compareTo у объекта LocalTime, а тот сравнит реальные часы и минуты.

        for (Map.Entry<LocalTime, String> entry : dailyDay.entrySet()) {
            System.out.println("Время: "+entry.getKey()+" || Дела: "+entry.getValue());
        }
    }
}
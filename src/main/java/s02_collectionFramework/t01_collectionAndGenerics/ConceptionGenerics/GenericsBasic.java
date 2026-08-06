package s02_collectionFramework.t01_collectionAndGenerics.ConceptionGenerics;

import java.util.*;


public class GenericsBasic {
    public static void main(String[] args) {

       List<String> names = new ArrayList<>();
       names.add("Vladislav");
       names.remove("Vladislav");

        List list = new ArrayList();
        list.add("string"); // так нельзя делать, если будем доставать 123 и использовать как строку - ClassCastException
        list.add(123);
        int one = (int)list.get(1);
    }
}
// -------

class Box<T> { // T - говорит, что этот класс будет работать с типом который мы укажем позже
    private T content; // поле content - будет такого же типа, что и T

    public Box(T content) { // конструктор принимает объект типа T
        this.content = content;
    }

    public T getContent() {return content;} // метод возвращает объект типа T
    public void setContent(T content) {this.content = content;}

    public boolean compare(Box<T> otherBox) { // Box<T> - Тип входящего параметра. Принимает другую коробку, но только с таким же типом, как и у нас.
        return this.content.equals(otherBox.getContent());
    }

    public static void main(String[] args) {

     Box<Integer> intBox = new Box<>(500);
     Box<String> strBox = new Box<>("Hello,Word!");
     Box<String> anotherStrBox = new Box<>("Hello,Word!");

     System.out.println("Содержимое intBox: "+intBox.getContent());
     System.out.println("Содержимое strBox: "+strBox.getContent());
     System.out.println("Коробки с одинаковым содержимым равны: "+strBox.compare(anotherStrBox));

    }
}


class CollectionExample {

    public static <T> void printArray(T[] array) {
        for(T element : array) {
            System.out.print(element+" ");
        }
        System.out.println();
    }

    public static <T> void printInfo(T element) {
        System.out.println("Тип объекта: "+element.getClass().getSimpleName());
        System.out.println("Значение: "+element);

    }
    public static void main(String[] args) {

        List<String> names = new ArrayList<>();
        names.add("Vladislav");
        names.add("Daria");

        for(String name : names) {
            System.out.println(name);
        }

        Integer[] intArray = {1,2,3,4,5,6,7,8,9,10};
        String[] strArray = {"One","two","three"};
        printArray(strArray);
        printArray(intArray);

        CollectionExample collection = new CollectionExample();
        collection.printInfo("Hello");
        collection.printInfo(2000);
    }
}

class HomeWork {
    public static void main(String[] args) {

        List<String> strings = new ArrayList<>();
        strings.add("Vladislav");
        strings.add("Daria");
        strings.add("One");

        for(String str : strings) {
            System.out.println(str);
        }

        List str = new ArrayList();
        str.add("One");
        str.add("Two");
        str.add(200);

        System.out.println(str.get(2));
    }
}

// ----- live-coding

class GameInventory {
    public static void main(String[] args) {

        System.out.println("Дневник Квестов");

        List<String> quests = new LinkedList<>();
        quests.add("Убить дракона");
        quests.add("Собрать 10 трав");
        quests.add("Поговорить с королём");

        for(String quest : quests) {
            System.out.println(quest);
        }

        System.out.println("Рюкзак с ресурсами");

        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("Зелье здоровья",5);
        inventory.put("Золотые монеты",150);
        inventory.put("Шкура волка",2);
        inventory.put("Зелья здоровья",inventory.getOrDefault("Зелье здоровья",0)+10);

        int currentPotions = inventory.get("Зелье здоровья");
        inventory.put("Зелье здоровья", currentPotions + 3);

        System.out.println("Монет в кошельке: "+inventory.get("Золотые монеты"));
        System.out.println("Зелья здоровья в инвентаре "+inventory.get("Зелье здоровья"));
    }
}

class MagicChest<T> {
    private T loot;

    public void putLoot(T item) {
        this.loot = item;
        System.out.println("В сундук что-то положили");
    }

    public T getLoot() {
        T retrievedItem = this.loot;
        this.loot = null;
        return retrievedItem;
    }

    public boolean isEmpty() {
        return this.loot == null;
    }

    public static void main(String[] args) {
        System.out.println("Сундук с золотом");

        MagicChest<Integer> goldChest = new MagicChest<>();
        goldChest.putLoot(1000);

        if(!goldChest.isEmpty()) {
            int myGold = goldChest.getLoot();
            System.out.println("Вы достали золото: "+myGold+" монет");
        }

        System.out.println("Сундук с оружием");

        MagicChest<String> weaponChest = new MagicChest<>();
        weaponChest.putLoot("Меч смерти");

        System.out.println("Вы достали оружие: "+weaponChest.getLoot());
        System.out.println("Сундук с оружием пуст?: "+weaponChest.isEmpty());
    }
}

class MerchantShop {
    public static void main(String[] args) {

        System.out.println("Лавка Торговца");

        Map<String, Integer> priceList = new HashMap<>();
        priceList.put("Зелье Здоровья",55);
        priceList.put("Меч славы",450);
        priceList.put("Яйцо силы",100);

        String itemToHero = "Зелье Здоровья";

        if(priceList.containsKey(itemToHero)) {
            System.out.println(itemToHero+" стоит: "+priceList.get(itemToHero));
        } else {
            System.out.println(itemToHero+": нету в наличии...");
        }

        System.out.println("Весь список:");
        for(String itemName : priceList.keySet()) {
            int itemPrice = priceList.get(itemName);

            System.out.println(itemName+" : "+itemPrice+" золотых.");
        }

        for(Map.Entry<String, Integer> entry : priceList.entrySet()) {
            System.out.println(entry.getKey()+" || "+entry.getValue()+" gold");
        }

        for (String itemName : priceList.keySet()) {
            System.out.println(itemName+" : "+priceList.get(itemName));
        }
    }
}


class GladiatorArena {
    public static void main(String[] args) {

        System.out.println("Арена гладиаторов");

        Queue<String> fightersQueue = new LinkedList<>();
        fightersQueue.offer("Гладиатор");
        fightersQueue.offer("Феникс");
        fightersQueue.offer("Бог ленивых");

        System.out.println("К бою готовится: "+fightersQueue.peek());

        System.out.println("БОИ НАЧИНАЮТЬСЯ");

        while(!fightersQueue.isEmpty()) {
            String hero = fightersQueue.poll();
            System.out.println("На арену выходит: "+hero);
        }
    }
}

class TestCollection {
    public static void main(String[] args) {

        List<Integer> list = new LinkedList<>();
        Random random = new Random();
        for(int i = 0; i < 100; i++) {
            list.add(random.nextInt(20));
        }

        System.out.println(list);
        Iterator<Integer> iterator = list.iterator();
        while(iterator.hasNext()) {
            if(iterator.next() % 2 == 0) {
                iterator.remove();
            }
        }
        System.out.println(list);

        Set<Integer> set = new TreeSet<>(list);
        System.out.println(set);
    }
}
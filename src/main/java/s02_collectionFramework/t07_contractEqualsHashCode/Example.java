package s02_collectionFramework.t07_contractEqualsHashCode;

import java.util.*;

public class Example {
    String title;
    int id;
    double price;

    public Example(int id, String title, double price) {
        this.title = title;
        this.id = id;
        this.price = price;
    }

    @Override
    public String toString() {
        return "ID: " + id + " || Title: " + title + " || Price: " + price;
    }
    @Override
    public boolean equals(Object obj) {
        // 1. Быстрая проверка: если это один и тот же объект в памяти - сразу true! Экономим время.
        if (this == obj) return true;
        // 2. Защита: если пришла пустота (null) ИЛИ объекты из разных классов (сравниваем Яблоко с Машиной) - сразу false.
        if (obj == null || getClass() != obj.getClass()) return false;
        Example example = (Example) obj;
        if (id != example.id) return false;
        // 5. ПОЧЕМУ Double.compare? Дробные числа (double) хранятся в памяти очень криво.
        // Обычное == может дать сбой из-за микроскопических погрешностей.
        // Метод Double.compare сравнивает их безопасно.
        if (Double.compare(example.price, price) != 0) return false;
        // 6. Сравниваем строки (имя). Тернарный оператор проверяет: если наше имя не null, вызываем equals.
        return (title != null ? title.equals(example.title) : example.title == null);
    }

    @Override
    public int hashCode() {
        int result = (title != null ? title.hashCode() : 0) + id;
        // ПОЧЕМУ ТУТ БИТОВЫЕ СДВИГИ (>>> 32)?
        // Нам нужно добавить цену (double) в хэш. Но double занимает 64 бита памяти, а метод hashCode() возвращает int (32 бита).
        // Эти строчки берут длинное 64-битное число, "складывают" его пополам с помощью оператора XOR (^) и впихивают в 32 бита без потери уникальности.
        long temp = Double.doubleToLongBits(price);
        result = 31 * result + (int)(temp ^ (temp >>> 32));
        return result;
    }
}

// Вторая вариация записи

class Book {
    String title;
    String author;
    int year;

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return year == book.year && (Objects.equals(title, book.title) && (Objects.equals(author, book.author)));
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, year);
    }
}

// ------

class Main {
    public static void main(String[] args) {

        Set<Example> examples = new HashSet<>();

        Example exampleOne = new Example(1,"Laptop",1500.00);
        Example exampleTwo = new Example(2,"Mobile Phone",800.00);
        Example exampleThree = new Example(1,"Laptop",1500.00);

        examples.add(exampleOne);
        examples.add(exampleTwo);
        examples.add(exampleThree);

        System.out.println("HashSet count: "+examples.size());
        for(Example e : examples) {
            System.out.println(e);
        }
    }
}

class MainTwo {
    public static void main(String[] args) {

        Map<Example, String> info = new HashMap<>();

        Example exampleOne = new Example(1,"Laptop",1500.00);
        Example exampleTwo = new Example(2,"Mobile Phone",800.00);
        Example exampleThree = new Example(1,"Laptop",1500.00);

        info.put(exampleOne,"Высокопроизводительный ноутбук");
        info.put(exampleTwo,"Удобный телефон");
        // должна произойти перезапись
        info.put(exampleThree,"Дорогой ноутбук");

        System.out.println("HashMap:");
        for (Map.Entry<Example, String> entry : info.entrySet()) {
            System.out.println("Key: "+entry.getKey()+" || Value: "+entry.getValue());
        }

        /*
        for(Example key : info.keySet()) {
            System.out.println("Key: "+key+" || Value: "+info.get(key));
        }
         */
    }
}

class User {
    String email;
    int id;

    public User(String email, int id) {
        this.email = email;
        this.id = id;
    }

    @Override
    public String toString() {
        return "ID: " + id + " || Email: " + email;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

class MainTree {
    public static void main(String[] args) {
        Set<User> user = new HashSet<>();

        User userOne = new User("Vladislav",1);
        User userTwo = new User("Igor",1);

        user.add(userOne);
        user.add(userTwo);
        System.out.println("HashSet count: "+user.size());

        Map<User, String> users = new HashMap<>();

        users.put(userOne,"Vladislav");
        users.put(userTwo,"Igor");

        System.out.println("HashMap:");
        for (Map.Entry<User, String> entry : users.entrySet()) {
            System.out.println("Key: "+entry.getKey()+" || Value: "+entry.getValue());
        }

        User searchUser = new User("Alex",1);
        String res = users.get(searchUser);
        System.out.println("Result: "+res);
    }
}
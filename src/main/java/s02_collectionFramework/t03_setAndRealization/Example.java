package s02_collectionFramework.t03_setAndRealization;

import java.util.*;

public class Example {
    public static void main(String[] args) {

        Set<String> names = new HashSet<>();
        names.add("Anna");
        names.add("Bob");
        names.add("Daniel");
        names.add("Daniel"); // дублируется - будет игнорирована

        System.out.println("HashSet: "+names);

        names = new LinkedHashSet<>();
        names.add("Daniel");
        names.add("Svetlana");
        names.add("Nikolay");
        System.out.println("LinkedHashSet: "+names);
    }
}

class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Person)) return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public String toString() {
        return name +" ("+age +")";
    }
}

class HashSetPersonExample {
    public static void main(String[] args) {
        Set<Person> persons = new HashSet<>();
        persons.add(new Person("Vladislav",22));
        persons.add(new Person("Valera",30));
        persons.add(new Person("Vladislav",22));
        Person person = new Person("Vladislav",22);
        persons.add(person);
        // код из исходников HashMap - if (p.hash == hash && ((p.key == key) || (key != null && key.equals(p.key))));

        System.out.println("HashSet: "+persons);
    }
}

class RemoveDuplicatesExample {
    public static void main(String[] args) {
        List<String> items = new ArrayList<>();
        items.add("Apple");
        items.add("Banana");
        items.add("Orange");
        items.add("Pineapple");
        items.add("Strawberry");
        items.add("Mango");
        items.add("Cherry");
        items.add("Pineapple");
        items.add("Strawberry");
        items.add("Mango");
        items.add("Pineapple");

        System.out.println("ArrayList: "+items);

        Set<String> sort = new HashSet<>(items);
        System.out.println("HashSet: "+sort);

        List<String> debuplicatedList = new ArrayList<>(sort);
        System.out.println("ArrayList: "+debuplicatedList);
    }
}

class HomeWork {
    public static void main(String[] args) {

        Set<String> items = new HashSet<>();
        items.add("Apple");
        items.add("Banana");
        items.add("Orange");
        items.add("Apple");
        items.add("Orange");
        System.out.println("HashSet: "+items);

        Set<String> itemsPartTwo = new LinkedHashSet<>();
        itemsPartTwo.add("Apple");
        itemsPartTwo.add("Banana");
        itemsPartTwo.add("Orange");
        itemsPartTwo.add("Apple");
        itemsPartTwo.add("Orange");
        System.out.println("LinkedHashSet: "+itemsPartTwo);

        Set<String> itemsPartThird = new TreeSet<>();
        itemsPartThird.add("Apple");
        itemsPartThird.add("Orange");
        itemsPartThird.add("Banana");
        itemsPartThird.add("Apple");
        itemsPartThird.add("Orange");
        System.out.println("TreeSet: "+itemsPartThird);
    }
}
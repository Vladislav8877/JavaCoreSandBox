package s02_collectionFramework.t08_iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Example {
    public static void main(String[] args) {

        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            numbers.add(i);
        }

        Iterator<Integer> iterator = numbers.iterator();
        while (iterator.hasNext()) {
            Integer number = iterator.next();
            if (number % 2 == 0) {
                iterator.remove();
            }
        }

        System.out.println("Список после удаления нечётных чисел:");
        for (Integer number : numbers) {
            System.out.print(number+" ");
        }

        List<String> words = new ArrayList<>();
        words.add("A");
        words.add("B");
        words.add("C");
        words.add("D");

        ListIterator<String> ListIterator = words.listIterator(words.size());

        System.out.println("Элементы списка в обратном порядке:");
        while (ListIterator.hasPrevious()) {
            String word = ListIterator.previous();
            System.out.println(word+" ");
        }
    }
}

        class Task {
            public static void main(String[] args) {

                List<String> tasks = new ArrayList<>();
                tasks.add("read book");
                tasks.add("write code");
                tasks.add("send email");
                tasks.add("done: clean room");
                tasks.add("done: buy food");

                Iterator<String> iterator = tasks.iterator();
                while (iterator.hasNext()) {
                    String task = iterator.next();
                    if (task.startsWith("done:")) {
                        iterator.remove();
                    }
                }

                for (String task : tasks) {
                    System.out.println(task);
                }

                System.out.println("----------------");

                List<String> badTasks = new ArrayList<>();
                badTasks.add("done: clean room");
                badTasks.add("write code");
                badTasks.add("done: buy food");

                for (String bad : badTasks) {
                    if (bad.startsWith("done:")) {
                        badTasks.remove(bad); // Ждем взрыва!
                    }
                }
            }
        }
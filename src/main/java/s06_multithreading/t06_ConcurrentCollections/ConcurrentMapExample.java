package s06_multithreading.t06_ConcurrentCollections;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConcurrentMapExample {
    // Создаем пустую многопоточную карту (ключ - строка, значение - число)
    private static final Map<String, Integer> concurrentMap = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {

        Thread writer = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                concurrentMap.put("Key" + i, i);
            }
        });

        Thread reader = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                // Читаем значение по ключу. Это происходит БЕЗ блокировки писателя!
                Integer value = concurrentMap.get("Key" + i);
                if (value != null && value % 100 == 0) {
                    System.out.println("Read value: "+value);
                }
            }
        });

        writer.start();
        reader.start();

        writer.join();
        reader.join();

        System.out.println("Map size: "+concurrentMap.size());
    }
}

// ---

class CopyOnWriteExample {
    private static final List<String> list = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        list.add("initial");

        Thread writer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                // При КАЖДОМ добавлении внутри Java создает новый массив-копию
                list.add("Element: "+i);
            }
        });

        Thread reader = new Thread(() -> {
            for (String s : list) {
                System.out.println("Reader sees: "+s);
            }
        });

        writer.start();
        Thread.sleep(1000);
        reader.start();
        writer.join();
        reader.join();
    }
}

// ---

class ConcurrentQueueExample {
    private static final ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            queue.offer(i);
        }

        Thread consumer = new Thread(() -> {
            while (!queue.isEmpty()) {
                Integer value = queue.poll();
                if (value != null) {
                    System.out.println("Consumed: "+value);
                }
            }
        });

        consumer.start();
        consumer.join();

        System.out.println("Queue now is empty: "+queue.isEmpty());
    }
}

package s06_multithreading.t06_ConcurrentCollections;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

class MessageSystem {
    private static final List<String> database = new CopyOnWriteArrayList<>(Arrays.asList(
            "user1@gmail.com",
            "user2@gmail.com",
            "user3@gmail.com"
    ));

    public static void main(String[] args) throws InterruptedException {

        Thread writer = new Thread(() -> {
            for (int i = 4; i < 9; i++) {
                database.add("user"+i+"@gmail.com");
                try {Thread.sleep(200);} catch (InterruptedException e) {e.printStackTrace();}
            }
        });

        Thread reader = new Thread(() -> {
            for (String user : database) {
                System.out.println("Send message: "+user);
                try {Thread.sleep(300);} catch (InterruptedException e) {e.printStackTrace();}
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

class Tasks {
    private static final ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) throws InterruptedException {

        Thread writerOne = new Thread(() -> {
            for (int i = 1; i <=10 ; i++) {
                queue.offer("Task ("+i+")");
            }
        });

        Thread writerTwo = new Thread(() -> {
            for (int i = 11; i <=20 ; i++) {
                queue.offer("Task ("+i+")");
            }
        });

        Thread readerOne = new Thread(() -> {
            while (!queue.isEmpty()) {
                String task = queue.poll();
                if (task != null) {
                    try {Thread.sleep(50);} catch (InterruptedException e) {e.printStackTrace();}
                    System.out.println("readerOne take: "+task);
                }
            }
        });

        Thread readerTwo = new Thread(() -> {
            while (!queue.isEmpty()) {
                String task = queue.poll();
                if (task != null) {
                    try {Thread.sleep(50);} catch (InterruptedException e) {e.printStackTrace();}
                    System.out.println("readerTwo take: "+task);
                }
            }
        });
        writerOne.start();
        writerTwo.start();
        Thread.sleep(1000);
        readerOne.start();
        readerTwo.start();
        writerOne.join();
        writerTwo.join();
        readerOne.join();
        readerTwo.join();
    }
}

// ---

class Database {
    private static final ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {

        Thread serverOne = new Thread(() -> {
            for (int i = 1 ; i <= 50; i++) {
                cache.put("Session_"+i, "User_"+i);
            }
        });

        Thread serverTwo = new Thread (() -> {
            for (int i = 51; i <= 100; i++) {
                cache.put("Session_"+i, "User_"+i);
            }
        });

        Thread check = new Thread (() -> {
            System.out.println(cache.size());
        });

        serverOne.start();
        serverTwo.start();
        Thread.sleep(1000);
        check.start();
    }
}

// ---

class BlockedQueue {
    private static final LinkedBlockingQueue<String> orderTable = new LinkedBlockingQueue<>(3);

    public static void main(String[] args) throws InterruptedException {

        Thread producer = new Thread (() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    orderTable.put("Заказ №: "+i);
                    System.out.println("Официант добавил Заказ №: "+i);
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread consumer = new Thread (() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                   String order = orderTable.take();
                    System.out.println("Повар готовит [Заказ №: "+order+"]");
                    Thread.sleep(700);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }
}

// ---

class Guests {
    private static final List<String> vipGuests = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) throws InterruptedException {

        Thread promoter = new Thread (() -> {
            for (int i = 1; i <= 1000; i++) {
                vipGuests.add("Гость №: "+i);
            }
        });

        Thread security = new Thread (() -> {
            for (String s : vipGuests) {
                String name = s;
            }
        });

        // решение проблемы исключения, когда итератор проходит по списку и видит что modCount изменился
        // нужно вешать замок synchronized
        /*
        Thread security = new Thread (() -> {
            synchronized(vipGuest) {
            for (String s : vipGuests) {
                String name = s;
            }
         }
       });
         */

        promoter.start();
        security.start();
        promoter.join();
        security.join();
    }
}
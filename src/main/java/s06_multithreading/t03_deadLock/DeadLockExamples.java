package s06_multithreading.t03_deadLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

class DeadlockExample {
    private static final int TRIES = 3;

    public static void main(String[] args) throws InterruptedException {
        Object firstSync = new Object();
        Object secondSync = new Object();

        Thread firstThread = new Thread(() -> {
            for (int i = 0; i < TRIES; i++) {
                synchronized (firstSync) {
                    System.out.println("Первый поток захватил первый монитор");
                    synchronized (secondSync) {
                        System.out.println("Первый поток захватил второй монитор");
                        System.out.println("---");
                    }
                }
            }
        });

        Thread secondThread = new Thread(() -> {
            for (int i = 0; i < TRIES; i++) {
                synchronized (firstSync) {
                    System.out.println("Второй поток захватил второй монитор");
                    synchronized (secondSync) {
                        System.out.println("Второй поток захватил первый монитор");
                        System.out.println("---");
                    }
                }
            }
        });

        firstThread.start();
        secondThread.start();

        firstThread.join();
        secondThread.join();

        // исправить выполнение этой программы можно лишь порядком мониторов - потоки должны
        // в одном и том же порядке захватывать мониторы объектов
    }
}

// ---

class User {
    int id;

    public User(int id) {
        this.id = id;
    }

    public int getId() {return id;}


    public static void main(String[] args) throws InterruptedException {
        User userA = new User(5);
        User userB = new User(10);

        Object firstLock = userA.getId() < userB.getId() ? userA : userB;
        Object secondLock = userA.getId() < userB.getId() ? userB : userA;

        Thread firstThread = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                synchronized (firstLock) {
                    System.out.println("Первый поток захватил монитор(1)");
                    synchronized (secondLock) {
                        System.out.println("Первый поток захватил монитор(2)");
                    }
                }
            }
        });

        Thread secondThread = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                synchronized (firstLock) {
                    System.out.println("Второй поток захватил монитор(1)");
                    synchronized (secondLock) {
                        System.out.println("Второй поток захватил монитор(2)");
                    }
                }
            }
        });

        firstThread.start();
        secondThread.start();

        firstThread.join();
        secondThread.join();
    }
}

// ---

class Printer {
    private final ReentrantLock lock = new ReentrantLock(true);

    public void print(String document) {
        System.out.println(Thread.currentThread().getName()+" подошел к принтеру...");

        try {
            if (lock.tryLock(3, TimeUnit.SECONDS)) {
                try {
                    System.out.println(Thread.currentThread().getName()+" Забрал принтер. Печатаем: "+document);
                    Thread.sleep(5000);
                    System.out.println(Thread.currentThread().getName()+" закончил печать.");
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println(Thread.currentThread().getName()+" не дождался. Отмена печати: "+document);
            }
        } catch (InterruptedException e) {
            // Это исключение вылетит, если кто-то вызовет interrupt() у ожидающего потока
            System.out.println(Thread.currentThread().getName()+" был прерван во время ожидания!");
        }
    }
}

class Main {
    public static void main(String[] args) throws InterruptedException {
        Printer printer = new Printer();

        Thread employeeOne = new Thread(() -> {
            printer.print("годовой отчет(100 страниц)");
        });

        Thread employeeTwo = new Thread(() -> {
            printer.print("мем (2 страницы)");
        });

        employeeOne.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        employeeTwo.start();
    }
}

//---

class Kitchen {
    private static final ReentrantLock pan = new ReentrantLock();     // Сковородка
    private static final ReentrantLock spatula = new ReentrantLock(); // Лопатка

    public static void main(String[] args) {
        // Шеф берет: Сначала Сковородку, потом Лопатку
        Thread chef = new Thread(() -> cook("Шеф", pan, spatula));

        // Су-шеф берет: Сначала Лопатку, потом Сковородку
        Thread sousChef = new Thread(() -> cook("Су-шеф", spatula, pan));

        chef.start();
        sousChef.start();
    }

    private static void cook(String name, ReentrantLock firstTool, ReentrantLock secondTool) {
        while (true) {
            boolean gotFirst = firstTool.tryLock();
            boolean gotSecond = secondTool.tryLock();

            if (gotFirst && gotSecond) {
                try {
                    System.out.println(name+": взял оба инструмента, начинает готовить");
                    Thread.sleep(1000);
                    System.out.println(name+": закончил готовить суп");
                    break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    firstTool.unlock();
                    secondTool.unlock();
                }
            }
            if (gotFirst) firstTool.unlock();
            if (gotSecond) secondTool.unlock();

            try { Thread.sleep(50); } catch (InterruptedException e) {}
        }
    }
}

// ---

class Chest {
    private static final ReentrantLock keyOne = new ReentrantLock();
    private static final ReentrantLock keyTwo = new ReentrantLock();

    public static void open(String name, ReentrantLock firstKey, ReentrantLock secondKey) {
        while (true) {
            boolean gotFirst = firstKey.tryLock();
            boolean gotSecond = secondKey.tryLock();

            if (gotFirst && gotSecond) {
                try {
                    System.out.println(name + ": успел взять два ключа от сундука - берёт золото");
                    Thread.sleep(1000);
                    System.out.println(name + ": взял столько золота сколько смог");
                    break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    firstKey.unlock();
                    secondKey.unlock();
                }
            } else {
                if (gotFirst) firstKey.unlock();
                if (gotSecond) secondKey.unlock();

                try {Thread.sleep(50);} catch (InterruptedException e) {}
            }
        }
    }

    public static void main(String[] args) {

        Thread firstThread = new Thread(() -> {
            open("Ivan", keyOne, keyTwo);
        });

        Thread secondThread = new Thread(() -> {
            open("Vlados", keyTwo, keyOne);
        });
        firstThread.start();
        secondThread.start();
}
    }

// --

class ChestTwo {
    private static final ReentrantLock keyOne = new ReentrantLock();
    private static final ReentrantLock keyTwo = new ReentrantLock();

    public static void open(String name, ReentrantLock firstKey, ReentrantLock secondKey) {
        while (true) {
            try {
                // 1. Пытаемся взять первый ключ мгновенно (не ждем)
                boolean gotFirst = firstKey.tryLock();
                boolean gotSecond = false;

                if (gotFirst) {
                    System.out.println(name + " взял первый ключ и ждет второй...");
                    // 2. А вот тут магия! Ждем второй ключ ровно 1 секунду.
                    // Если Владос в этот момент забирает золото и открывает замок,
                    // Иван тут же подхватит его, не уходя на новый круг цикла!
                    gotSecond = secondKey.tryLock(1, TimeUnit.SECONDS);
                }

                // 3. Проверяем, удалось ли собрать комбо
                if (gotFirst && gotSecond) {
                    try {
                        System.out.println(name + ": успел взять два ключа от сундука - берёт золото");
                        Thread.sleep(1000);
                        System.out.println(name + ": взял столько золота сколько смог");
                        break; // Успех! Уходим из цикла
                    } finally {
                        // Тот самый обязательный finally
                        firstKey.unlock();
                        secondKey.unlock();
                    }
                } else {
                    // Если мы тут, значит 1 секунда прошла, а второй ключ так и не дали.
                    // Придется сбросить всё и пробовать заново.
                    System.out.println(name + " не дождался! Сбрасывает ключи.");
                    if (gotFirst) firstKey.unlock();
                    if (gotSecond) secondKey.unlock();

                    // Спим чуть-чуть перед новой атакой на сундук
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) {
                // Метод с тайм-аутом требует обработки этого исключения,
                // так как ожидание могут прервать извне.
                System.out.println(name + " устал ждать и ушел!");
                break;
            }
        }
    }

    public static void main(String[] args) {

        Thread firstThread = new Thread(() -> {
            open("Ivan", keyOne, keyTwo);
        });

        Thread secondThread = new Thread(() -> {
            open("Vlados", keyTwo, keyOne);
        });
        firstThread.start();
        secondThread.start();
    }
}
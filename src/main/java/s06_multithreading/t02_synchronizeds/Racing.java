package s06_multithreading.t02_synchronizeds;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Racing {
    private static final int N = 100_000_000;
    private static volatile int x = 0;

    public static void main(String[] args) throws InterruptedException {

        Thread incThread = new Thread(() -> {
            for (int i = 0; i < N; i++) {
                x++;
            }
        });

        Thread decThread = new Thread(() -> {
            for (int i = 0; i < N; i++) {
                x--;
            }
        });

        incThread.start();
        decThread.start();

        incThread.join();
        decThread.join();

        System.out.println("x = "+x);
    }
}

//---

class CounterComparison {
    // Создам атомарный счетчик
    private static AtomicInteger atomicCounter = new AtomicInteger(0);
    /*Внутренняя гарантия видимости: Внутри AtomicInteger само значение int помечено модификатором volatile.
    Это значит, что проблемы видимости (Visibility) здесь устранены по умолчанию
    — все потоки всегда видят актуальное значение из RAM*/

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                // Метод incrementAndGet() выполняется целиком за одну неделимую
                // аппаратную инструкцию. Другой поток физически не может
                // вмешаться между чтением и записью.
                atomicCounter.incrementAndGet();
                /*Эта команда заменяет собой операцию counter++.
                В отличие от counter++, которая состоит из 3 шагов и уязвима к прерываниям, incrementAndGet() работает
                на базе аппаратного алгоритма CAS (Compare-And-Swap).*/
            }).start();
        }
        Thread.sleep(2000);
        // Вывод всегда будет строго 1000. Ни одна операция не потеряется.
        System.out.println("Atomic counter value: " + atomicCounter.get());
    }
}

//---

class ThreadsSynchronized {
    private static final int N = 100_000_000;
    private static int x = 0;

    public static void main(String[] args) throws InterruptedException {
        // Создаем пустой объект-заглушку. Нам нужен только его внутренний "Монитор" (замок).
        Object obj = new Object();

        Thread firstThread = new Thread(() -> {
            // Поток 1 захватывает монитор объекта 'monitor'
            for (int i = 0; i < N; i++) {
                synchronized (obj) {
                    x++; // Критическая секция. Сюда не может войти никто другой.
                } // Поток 1 выходит из блока и освобождает монитор
            }
        });

        Thread secondThread = new Thread(() -> {
            for (int i = 0; i < N; i++) {
                synchronized (obj) {
                    // Поток 2 пытается захватить ТОТ ЖЕ САМЫЙ монитор.
                    // Если Поток 1 внутри, Поток 2 блокируется на этой строке и ждет.
                    x--;
                }
            }
        });

        firstThread.start();
        secondThread.start();

        firstThread.join();
        secondThread.join();

        System.out.println("x = "+x);
    }

    // Синхронизированные методы
    public synchronized void increment() {
        // критическая секция
        x++;
    }

    // Синхронизированные блоки
    public void incrementTwo() {
        synchronized (this) {
            x--;
        }
    }
}

class SharedResources {
    // Создаем два РАЗНЫХ замка для РАЗНЫХ задач
    private final Object moneyLock = new Object();
    private final Object userLock = new Object();

    private int balance = 0;
    private int activeUsersCount = 0;

    public void addMoney() {
        synchronized (moneyLock) { // Поток 1 захватывает замок денег
            balance += 100;
        }
    }

    public void incrementUser() {
        synchronized (userLock) { // Поток 2 ОДНОВРЕМЕННО захватывает замок пользователей
            activeUsersCount++;
        }
    }

    /*Поток 1 и Поток 2 будут выполняться абсолютно параллельно, не мешая друг другу, потому что они проверяют разные мониторы разных объектов.
    synchronized для первого потока смотрит на moneyLock, а для второго — на userLock.
    Но если придет Поток 3 и тоже вызовет метод addMoney(), он наткнется на тот же moneyLock, который сейчас держит Поток 1.
    Вот тут synchronized его остановит и заставит ждать.*/
}

//---

class LockExample {
    private static int counter = 0;
    // Создаем объект явной блокировки
    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            for (int i = 0; i < 10000; i++) {
                increment();
            }
        };
        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("counter = "+counter);
    }

    private static void increment() {
        lock.lock(); // ЯВНЫЙ ЗАХВАТ: Блокируем доступ для других потоков
        try {
            counter++; // Критическая секция
        } finally {
            // КРИТИЧЕСКИ ВАЖНО: Разблокировка всегда пишется в блоке finally.
            // Это гарантирует, что замок будет снят даже в случае ошибки в критической секции.
            // Иначе другие потоки зависнут навсегда.
            lock.unlock();
        }
    }
}
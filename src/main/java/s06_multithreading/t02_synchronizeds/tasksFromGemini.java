package s06_multithreading.t02_synchronizeds;

import java.util.concurrent.locks.ReentrantLock;

class BankAccount {
    private int balance = 1000;
    private final Object lock = new Object(); // Создаем личный замок банка

    public void withdraw(int amount) {
        // Блокируем дверь ЕЩЕ ДО ТОГО, как посмотрели на баланс!
        synchronized (lock) {
            if (balance >= amount) {
                try {
                    Thread.sleep(10); // Имитация задержки
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                balance -= amount;
            } else {
                // Эта надпись появится ровно 100 раз, когда баланс станет 0
                System.out.println("Недостаточно средств!");
            }
        } // Открываем замок
    }

    public int getBalance() {
        // Чтение баланса тоже должно быть защищено,
        // чтобы никто не прочитал "промежуточное" значение!
        synchronized (lock) {
            return balance;
        }
    }
}
class Main {
    public static void main(String[] args) throws InterruptedException {
        BankAccount account = new BankAccount();

        Runnable task = () -> {
            for (int i = 0; i < 100; i++) {
                account.withdraw(10);
            }
        };

        Thread brotherOne = new Thread(task);
        Thread brotherTwo = new Thread(task);

        brotherOne.start();
        brotherTwo.start();

        brotherOne.join();
        brotherTwo.join();

        System.out.println("Итоговый баланс: "+account.getBalance());
    }
}

//---

/* class Account {
    private final String name;
    private int balance = 1000;

    public Account(String name) {
        this.name = name;
    }

    public void withdraw(int amount) { this.balance -= amount; }
    public void deposit(int amount) { this.balance += amount; }
    public String getName() { return name; }
}

class MainTwo {
    // Метод перевода денег
    public static void transfer(Account from, Account to, int amount) {
        // Шаг 1: Блокируем счет отправителя
        synchronized (from) {
            System.out.println(Thread.currentThread().getName() + " заблокировал счет " + from.getName());

            // Имитация небольшой задержки сети, чтобы зависание происходило в 100% случаев
            try { Thread.sleep(50); } catch (InterruptedException e) {}

            // Шаг 2: Блокируем счет получателя
            synchronized (to) {
                System.out.println(Thread.currentThread().getName() + " заблокировал счет " + to.getName());

                // Сама транзакция
                from.withdraw(amount);
                to.deposit(amount);
                System.out.println("Перевод успешен!");
            }
        }
    }

    public static void main(String[] args) {
        Account accountA = new Account("Алиса");
        Account accountB = new Account("Боб");

        // Поток 1: Алиса переводит Бобу
        Thread thread1 = new Thread(() -> transfer(accountA, accountB, 100), "Поток 1 (Алиса -> Боб)");

        // Поток 2: Боб переводит Алисе
        Thread thread2 = new Thread(() -> transfer(accountB, accountA, 100), "Поток 2 (Боб -> Алиса)");

        thread1.start();
        thread2.start();
    }
}
В изначальном коде произошло именно это:
Поток 1 (Алиса -> Боб) захватил монитор Алисы и уснул на 50мс.
Поток 2 (Боб -> Алиса) в эту же миллисекунду захватил монитор Боба и уснул на 50мс.
Поток 1 просыпается и пытается захватить монитор Боба. Но он занят Потоком 2.
Поток 2 просыпается и пытается захватить монитор Алисы. Но он занят Потоком 1.
👉 Они встали в мертвую пробку.
 */

class Account {
    private final String name;
    private int balance = 1000;
    public final ReentrantLock lock = new ReentrantLock();

    public Account(String name) {
        this.name = name;
    }

    public void withdraw(int amount) { this.balance -= amount; }
    public void deposit(int amount) { this.balance += amount; }
    public String getName() { return name; }
}

class MainTwo {
    // Метод перевода денег
    public static void transfer(Account from, Account to, int amount) {
        while (true) {
            boolean lockedFrom = from.lock.tryLock();
            boolean lockedTo = to.lock.tryLock();

            if (lockedFrom && lockedTo) {
                try {
                    // Захватили оба счета
                    System.out.println(Thread.currentThread().getName()+" переводит деньги...");
                    from.withdraw(amount);
                    to.deposit(amount);
                    System.out.println("Перевод успешен");
                    break;
                } finally {
                    from.lock.unlock();
                    to.lock.unlock();
                }
            }
            if (lockedFrom) from.lock.unlock();
            if (lockedTo) to.lock.unlock();

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Account accountA = new Account("Алиса");
        Account accountB = new Account("Боб");

        // Поток 1: Алиса переводит Бобу
        Thread thread1 = new Thread(() -> transfer(accountA, accountB, 100), "Поток 1 (Алиса -> Боб)");

        // Поток 2: Боб переводит Алисе
        Thread thread2 = new Thread(() -> transfer(accountB, accountA, 100), "Поток 2 (Боб -> Алиса)");

        thread1.start();
        thread2.start();
    }
}

//---

class Counter {
    private int count = 0;

    public void increment() {
        // Создаем отдельный замок для безопасности!
        Object lock = new Object();

        synchronized (lock) {
            count++;
        }
    }

    public int getCount() {
        return count;
    }
}

class MainThree {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        };

        // Запускаем 10 потоков...
        // (Код запуска и join() опущен для краткости, там всё правильно)

        System.out.println("Итого: " + counter.getCount());
    }
}
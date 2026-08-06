package s06_multithreading.t01_introduceMultithreading;

import java.util.concurrent.Executors;

public class TwoThreads {
    private static final int PRINT_COUNT = 10;
    public static void main(String[] args) {

        // СОЗДАНИЕ ПЕРВОГО ПОТОКА
        // В конструктор класса Thread мы передаем реализацию интерфейса Runnable.
        // () -> { ... } — это лямбда-выражение, которое говорит: "Вот твой кусок кода для выполнения".
        Thread threadOne = new Thread(() -> {
            for (int i = 0; i < PRINT_COUNT; i++) { // внутри {} мы описываем какие действия будет выполнять поток
                System.out.print("o");
            }
        });

        Thread threadTwo = new Thread(() -> {
            for (int i = 0; i < PRINT_COUNT; i++) {
                System.out.print("X");
            }
        });

        // ЗАПУСК ПОТОКОВ
        // Метод .start() делает системный вызов к ОС: "Поставь этого парня в очередь на процессор".
        threadOne.start();
        // ВАЖНО: Код не ждет, пока thread1 закончит работу!
        // Он мгновенно идет дальше и делает системный вызов для thread2.
        threadTwo.start();

        //---

        Runnable runnable = () -> { // Можно явно создавать переменную типа Runnable, не используя лямбда-выражений:
            for (int i = 0; i < PRINT_COUNT; i++) {
                System.out.print("Y");
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}

//---

class VisibilityDemo {
    // static переменная, которая лежит в общей Куче (Heap)
    static volatile boolean running = true;

    public static void main(String[] args) {
        Thread worker = new Thread(() -> {
            // ВНИМАНИЕ: Поток может скопировать значение 'running' из общей памяти
            // в локальный кэш своего ядра процессора для ускорения работы.

            while (running) {

            }
            System.out.println("worker stopped");
        });
        worker.start();

        try {
            // Главный поток засыпает на 1 секунду
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Главный поток проснулся и изменил переменную в общей памяти!
        running = false;
        System.out.println("Main tread set running = false");
    }
}

//---

class VirtualThreadsExample {
    public static void main(String[] args) {

        Thread.startVirtualThread(() -> {
            System.out.println("Привет из виртуального потока: " + Thread.currentThread());
        });

        System.out.println("Основной потом завершился");
    }
}

class ManyVirtualThreadsExample {
    public static void main(String[] args) {

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {

            for (int i = 0; i < 10; i++) {
                executor.submit(() -> {
                    System.out.println("Виртуальный поток: "+Thread.currentThread());
                    return null;
                });
            }
        }
    }
}

/*
Executors — это фабрика-менеджер. Ты больше не создаешь потоки руками через new Thread().
Ты нанимаешь Менеджера (Экзекьютора), который будет делать это за тебя.
--
newVirtualThreadPerTaskExecutor() — это конкретный тип менеджера.
Его правило звучит так: "На каждую новую задачу, которую мне пришлют, я буду создавать абсолютно новый Виртуальный Поток".
--
.submit(...) — мы передаем нашему Менеджеру (executor) лямбда-выражение (инструкцию).
Менеджер берет эту инструкцию, мгновенно рождает новый виртуальный поток, поручает ему эту инструкцию и запускает в работу.
И так 10 000 раз подряд.
--
Метод .submit() очень умный. Он может принимать два типа задач:
Runnable (просто сделай работу и ничего не возвращай).
Callable (сделай работу и верни мне результат, например, вычисленное число).

Написав return null;, мы заставляем Java считать нашу лямбду интерфейсом Callable.
 Поток выполнит печать в консоль и отчитается: "Я закончил, результата для возврата нет (null)".
--
Безопасное выключение: Экзекьюторы всегда нужно создавать внутри try-with-resources.
Это гарантирует, что программа дождется выполнения всех отправленных задач перед тем, как закрыться.
*/

class Home {
    public static int value;
    public static void increment() {value++;}
    public static void main(String[] args) throws InterruptedException {

        Thread threadOne = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                increment();
            }
        });

        Thread threadTwo = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                increment();
            }
        });
        threadOne.start();
        threadTwo.start();

        threadOne.join();
        threadTwo.join();

        System.out.println("value : " + value);
    }
}

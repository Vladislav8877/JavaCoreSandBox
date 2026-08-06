package s06_multithreading.t05_ExServiceCallableFuture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class FixedThreadPoolDemo {
    public static void main(String[] args) throws InterruptedException {

        // Генерируем список задач (очередь)
        List<Runnable> tasks = new ArrayList<>();
        for (int i = 0; i <= 100; i++) {
            final int taskNumber = i; // ?? лямбда требует final переменную
            tasks.add(() -> {
                System.out.println("Задача #: " + taskNumber);
            });
        }

        // создаем пул ровно на 10 потоков
        ExecutorService executor = Executors.newFixedThreadPool(10);

        // закидываем все задачи в пул
        for (Runnable task : tasks) {
            executor.execute(task);
        }

        // закрываем приём новых задач. Без этого программа не завершится
        executor.shutdown();
        // ждём пока пул не закончит старые задачи, проверяем каждую секунду
        while (!executor.awaitTermination(1, TimeUnit.SECONDS)) {}

        System.out.println("Все задачи выполнены");
    }
}

//---

class execute {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);

        // Отправляем первую задачу. Она просто печатает текст и ничего не возвращает.
        service.execute(() -> {
            System.out.println("Задача 1 выполняется в потоке: "+Thread.currentThread().getName());
        });

        service.execute(() -> {
            System.out.println("Задача 2 выполняется в потоке: "+Thread.currentThread().getName());
        });

        // закрываем пул
        service.shutdown();

        /* Мы создаём пул на 2 потока.
        Отправляем две задачи, каждая печатает своё имя потока.
        shutdown() говорит пулу: «Не принимай больше задач и заверши работу, когда все текущие задачи закончатся».
         */
    }
}

class submit {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);

        // Отправляем задачу, которая должна что-то вычислить и ВЕРНУТЬ (return).
        // Получаем взамен коробочку Future.
        Future<Integer> futureResult = service.submit(() -> {
            int sum = 0;
            for (int i = 1; i <= 10; i++) {
                sum += i;
            } return sum;
        });

        try {
            // Ждем, пока поток всё посчитает. Тут главный поток ЗАВИСНЕТ до получения ответа.
            Integer result = futureResult.get();
            System.out.println("Result: "+result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            service.shutdown(); // закрываем пул
        }

        /* submit(…) вернул объект Future.
        futureResult.get() блокирует текущий поток, пока задача не закончит работу, и возвращает результат типа Integer.
        Если во время вычислений произошла ошибка, futureResult.get() выбросит исключение (например, ExecutionException).
        После получения результата мы закрываем пул потоков через shutdown()
         */
    }
}

// ---

class timeout {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);

        Future<Integer> futureResult = service.submit(() -> {
            int sum = 0;
            for (int i = 1; i <= 10; i++) {
                sum += i;
            } return sum;
        });

        try {
            Integer result = futureResult.get(1, TimeUnit.SECONDS);
            System.out.println("Result: "+result);
        } catch (Exception e) {
            System.out.println("Время задачи истекло. Отменяем задачу"); // TimeoutException e
            futureResult.cancel(true);
            e.printStackTrace();
        } finally {
            service.shutdown();
        }
    }
}

class cancel {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);

        Future<Integer> futureResult = service.submit(() -> {
            int sum = 0;
            for (int i = 1; i <= 10; i++) {
                sum += i;
            }
            return sum;
        });

        boolean canceled = futureResult.cancel(true);
        service.shutdown();

        if (canceled) {
            System.out.println("Задача успешно отменилась");
        } else {
            System.out.println("Не удалось отменить задачу. Возможно она уже завершилась");
        }
    }
}

// ---

class parallel {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        int sum = numbers.parallelStream()
                .mapToInt(n -> n * 2)
                .sum();
        System.out.println("Sum: "+sum);
    }
}

class one {
    public static void main(String[] args) {
        List<Callable<String>> tasks = Arrays.asList(
                () -> {Thread.sleep(500); return "Result (1)";},
                () -> {Thread.sleep(300); return "Result (2)";},
                () -> {Thread.sleep(100); return "Result (3)";}
        );

        ExecutorService service = Executors.newFixedThreadPool(3);

        try {
            // Отправляем ВСЕ задачи разом.
            // Главный поток заблокируется тут, пока ВСЕ 3 не завершатся!
            List<Future<String>> futures = service.invokeAll(tasks);

            for (Future<String> future : futures) {
                System.out.println("Result: "+future.get());
            }
        } catch (InterruptedException |  ExecutionException e ) {
            e.printStackTrace();
        } finally {
            service.shutdown();
        }
    }
}

class time {
    public static void main(String[] args) {
        ExecutorService service = Executors.newSingleThreadExecutor();

        Future<?> futureLongTask = service.submit(() -> {
            try {
                Thread.sleep(10_000);
                System.out.println("task is completed");
            } catch (InterruptedException e) {
                System.out.println("task is interrupted");
            }
        });

        try {
            futureLongTask.get(2,TimeUnit.SECONDS);
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            System.out.println("Задача слишком долго выполняется");
            futureLongTask.cancel(true);
        } finally {
            service.shutdown();
        }
    }
}

//---

class p {
    public static void main(String[] args) {
        List<String> words = Arrays.asList("Java", "Concurrency", "Parallel", "Streams");

        long count = words.parallelStream()
                .filter(n -> n.length() > 5)
                .count();
        System.out.println(count);
    }
}
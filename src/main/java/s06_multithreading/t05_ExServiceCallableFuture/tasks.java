package s06_multithreading.t05_ExServiceCallableFuture;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

class Tasks {
    public static void main(String[] args) {

        // 1. Создаем пул на 2 потока
        var service = Executors.newFixedThreadPool(2);

        try {
            Future<Integer> task1 = service.submit(() -> calculateSum(10));
            Future<Integer> task2 = service.submit(() -> calculateSum(20));
            Future<Integer> task3 = service.submit(() -> calculateSum(30));

            System.out.println("Задачи отправлены. Ждем результаты...");

            System.out.println("Результат 1: " + task1.get(3, TimeUnit.SECONDS));
            System.out.println("Результат 2: " + task2.get(3, TimeUnit.SECONDS));
            System.out.println("Результат 3: " + task3.get(3, TimeUnit.SECONDS));

        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            System.err.println("Ошибка или слишком долгое ожидание...");
            e.printStackTrace();
        } finally {
            service.shutdown();
        }
    }

    private static Integer calculateSum(int max) throws InterruptedException {
        System.out.println("Поток " + Thread.currentThread().getName() + " начал работу.");
        int sum = 0;
        for (int i = 0; i <= max; i++) {
            sum += i;
        }
        Thread.sleep(1000); // Имитация долгой работы
        return sum;
    }
}

// ---

class Weather {
    public static void main(String[] args) {
        var service = Executors.newFixedThreadPool(2);

        try {
            Future<String> weatherOne = service.submit(() -> {
                Thread.sleep(1000); return "Северная станция: -5 градусов";
            });
            Future<String> weatherTwo = service.submit(() -> {
                Thread.sleep(1000); return "Южная станция: +35 градусов";
            });
            Future<String> weatherThree = service.submit(() -> {
                Thread.sleep(1000); return "Восточная станция: +15 градусов";
            });

            System.out.println("Задачи отправлены. Ждем результаты...");

            System.out.println("Результат 1: "+weatherOne.get(2,TimeUnit.SECONDS));
            System.out.println("Результат 2: "+weatherTwo.get(2,TimeUnit.SECONDS));
            System.out.println("Результат 3: "+weatherThree.get(2,TimeUnit.SECONDS));
        } catch(InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            service.shutdown();
        }
    }
}

// ---

class multi {
    public static void main(String[] args) {

        List<Callable<Integer>> airlines = Arrays.asList(
                () -> {Thread.sleep(500); return 15560;},
                () -> {Thread.sleep(1000); return 16000;},
                () -> {Thread.sleep(870); return 14500;},
                () -> {Thread.sleep(900); return 18000;}
        );

        var service = Executors.newCachedThreadPool();

        try {
            List<Future<Integer>> futureAirlines = service.invokeAll(airlines);
            for (Future<Integer> future : futureAirlines) {
                System.out.println("Result: "+future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            service.shutdown();
        }
    }
}

//---

class Cancel {
    public static void main(String[] args) {
        var service = Executors.newSingleThreadExecutor();

        Future<String> future = service.submit(() -> {
            System.out.println("Начинаю скачивание");
            Thread.sleep(5000);
            return "Отчёт скачан";
        });

        try {
            System.out.println("Пытаемся получить результат");
            System.out.println("Result: "+future.get(2,TimeUnit.SECONDS));
        } catch (TimeoutException e) {
            System.out.println("Время ожидания вышло, отменяем задачу...");
            future.cancel(true);
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Произошла критическая ошибка выполнения!");
            e.printStackTrace();
        } finally {
            service.shutdown();
        }
    }
}
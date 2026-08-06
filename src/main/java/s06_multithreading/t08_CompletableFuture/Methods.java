package s06_multithreading.t08_CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

public class Methods {
    public static void main(String[] args) throws InterruptedException {

        // Базовые методы запуска (Создание задач)

        // Запускает асинхронную задачу, которая ничего не возвращает.
        CompletableFuture<Void> futureTask = CompletableFuture.runAsync(() -> {
            // выполняется в фоновом потоке, не возвращает результат (VOID)
            System.out.println("Асинхронная задача без возвращаемого значения.");
        });

        // Запускает асинхронную задачу, которая вычисляет и возвращает результат.
        CompletableFuture<Integer> futureResult = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            return 42;
        });

        // ---

        // Принимает рез предыдущего шага, изм его и возвращает новое значение
        CompletableFuture<Integer> chain = CompletableFuture.supplyAsync(() -> 5).thenApply(num -> num * 2) // преобразуем 5 в 10
                .thenApply(num -> num + 3); // преобразуем 10 в 13
        // chain является CompletableFuture<Integer> с результатом 13


        // Принимает рез вып действие(например печать в консоль) но ничего не возвращает
        CompletableFuture.supplyAsync(() -> "Hello").thenAccept(System.out::println); // (text -> sout("Res: " + text);
        // возвращаемый тип CompletableFuture


        // Связывает две задачи последовательно
        CompletableFuture<Integer> composed = CompletableFuture.supplyAsync(() -> 10)
                .thenCompose(num -> CompletableFuture.supplyAsync(() -> num + 20));
        // В отличие от thenApply(), где вернётся CompletableFuture, thenCompose() сделает это
        // «плоской» цепочкой (CompletableFuture).


        // Чтобы объединить результат двух независимых задач
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> 10);
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> 20);

        CompletableFuture<Integer> combined = future1.thenCombine(future2, (res1, res2) -> res1 + res2);


        // Обработка ошибок
        CompletableFuture<Object> safeFuture = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Имитация сбоя");
        }).exceptionally(ex -> {
            System.out.println("Ошибка: " + ex.getMessage());
            return 0; // возвращаем значение по умолчанию
        });

        // универсальный finally блок
        CompletableFuture<Integer> handle = CompletableFuture.supplyAsync(() -> {
            if (true) throw new IllegalStateException("Mistake");
            return 42;
        }).handle((res, ex) -> {
            if (ex != null) {
                System.out.println("Произошло исключение: " + ex.getMessage());
                return -1;
            } else {
                return res * 2;
            }
        });
    }
}

// ---

class TaskOne {
    // Чтение файла(Асинхронно)
    private static CompletableFuture<String> readFileAsync(String filename) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            return "Содержимое файла: " + filename;
        });
    }

    // Сетевой запрос (Асинхронно)
    private static CompletableFuture<String> fitchDataFromServiceAsync(String url) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
            }
            return "Данные от сервиса: " + url;
        });
    }

    public static void main(String[] args) {
        // 1. ЗАПУСК: Оба метода стартуют в фоновых потоках параллельно
        CompletableFuture<String> fileFuture = readFileAsync("files/file1.txt");
        CompletableFuture<String> serviceFuture = fitchDataFromServiceAsync("http://example.com");

        // 2. ОБЪЕДИНЕНИЕ: Ждем оба результата и склеиваем их
        CompletableFuture<String> resultFuture = fileFuture
                // fileData - результат первого, serviceData - результат второго
                .thenCombine(serviceFuture, (fileDate, serviceDate) -> {
                    return fileDate + "\n" + serviceDate;
                })
                // 3. ОБРАБОТКА ОШИБОК: Если чтение файла или запрос упали
                .exceptionally(ex -> {
                    System.out.println("Произошла ошибка: " + ex.getMessage());
                    return "Резервный результат"; // Заглушка, что бы программа не рухнула
                });
        // 4. ВЫВОД: Принимаем финальную склеенную строку и печатаем (ничего не возвращая)
        resultFuture.thenAccept(System.out::println);

        // 5. ПАУЗА В MAIN: Главный поток спит, чтобы фоновые пулы успели доработать до закрытия программы
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// ---

class TaskTwo {
    public static void main(String[] args) {
        // 1. ЗАПУСК ПЕРВОЙ ЗАДАЧИ
        CompletableFuture<Integer> taxFuture = CompletableFuture.supplyAsync(() -> {
            // Имитируем получение числа 100
            System.out.println("Исходная сумма: 100");
            return 100;
            // 2. ПОСЛЕДОВАТЕЛЬНЫЙ ЗАПУСК ВТОРОЙ ЗАДАЧИ (thenCompose)
            // Передаем 100 в переменную amount
        }).thenCompose(amount -> CompletableFuture.supplyAsync(() -> {
            System.out.println("Загрузка налоговой ставки...");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {}
            double taxRate = 0.2;
            return (int) (amount * (1 + taxRate));
            // 3. ПЕРЕХВАТ ОШИБОК для всей цепочки
        })).exceptionally(ex -> {
            System.out.println("Ошибка: "+ex.getMessage());
            return 0;
        });

        // 4. ПОТРЕБЛЕНИЕ РЕЗУЛЬТАТА (без возврата данных)
        taxFuture.thenAccept(System.out::println);

        int parallelism = ForkJoinPool.commonPool().getParallelism();
        System.out.println("Потоков в общем пуле: " + parallelism);
    }
}
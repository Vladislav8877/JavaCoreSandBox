package s06_multithreading.t08_CompletableFuture;

import java.util.concurrent.*;

public class Examples {
    public static void main(String[] args) {

        CompletableFuture<Integer> futureResult = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            return 100;
        }).thenCompose(amount -> CompletableFuture.supplyAsync(() -> {
            // throw new RuntimeException("Mistake...")
            System.out.println("Обработка данных...");
            return amount * 2;
        })).exceptionally(ex -> {
            System.out.println("Ошибка: " + ex.getMessage());
            return 0;
        });

        futureResult.thenAccept(System.out::println);
    }

    // ---

    ExecutorService service = Executors.newCachedThreadPool();
    Future<Integer> futureRes = service.submit(() -> {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
        return 100;
    });

    Future<?> totalRes = service.submit(() -> {
        try {
            Integer result = futureRes.get(3, TimeUnit.SECONDS);
            System.out.println("Total Result: " + result);
        } catch (ExecutionException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            service.shutdown();
        }
    });
}

// ---

class ExampleOne {
    public static void main(String[] args) {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
            }
            return 1000;
        }).thenApply(price -> (int) (price * 0.80)).thenApply(price -> price + 150);

        future.thenAccept(System.out::println).join();
    }
}
//---

class ExampleTwo {
    public static void main(String[] args) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            return "User_777";
        }).thenCompose(userID -> CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
            }
            return "Баланс пользователя: " + userID + ": 500$";
        }));
        future.thenAccept(System.out::println).join();
    }
}
// ---

class ExampleThree {
    public static void main(String[] args) {
        CompletableFuture<Double> futureRate = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
            }
            return 0.92;
        });
        CompletableFuture<Integer> futurePrice = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
            }
            return 100;
        });

        CompletableFuture<Double> result = futurePrice.thenCombine(futureRate, (price, rate) -> price * rate);
        result.thenAccept(System.out::println).join();
    }
}
//---

class ExampleFour {
    public static void main(String[] args) {
        CompletableFuture<String> res = CompletableFuture.supplyAsync(() -> {
            System.out.println("Выполняется проверка...");
            if (true) {
                throw new RuntimeException("Ошибка....");
            }
            return "Успех";
        }).exceptionally(ex -> {
            System.out.println("Лог ошибки: " + ex.getMessage());
            return "Гостевой режим (Guest)";
        });
        res.thenAccept(System.out::println).join();
    }
}
// ---

class ExampleFive {
    public static void main(String[] args) {
        CompletableFuture<String> operation = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {}
            return "Платёж №1001 успешно пройден.";
        }).whenComplete((res, ex) -> {
            if (ex != null) {
                System.out.println("[ЛОГ] Ошибка платежа: "+ex.getMessage());
            } else {
                System.out.println("[ЛОГ] Успешная операция: "+res);
            }
        });
        operation.thenAccept(System.out::println).join();
    }
}
// ---

class ExampleSix {
    public static void main(String[] args) {
        CompletableFuture<String> handle = CompletableFuture.supplyAsync(() -> {
            if (true) {
                throw new RuntimeException("БД не отвечает...");
            }
            return "100";
        }).handle((res, ex) -> {
            if (ex != null) {
                return "HTTP 500 [Internal Error]:" + ex.getMessage();
            } else {
                return "HTTP 200 [OK]: Баланс равен " + res;
            }
        });
        handle.thenAccept(System.out::println).join();
    }
}
// ---

class ExampleSeven {
    public static void main(String[] args) {
        var customPool = Executors.newFixedThreadPool(4);

        CompletableFuture<String> personalPool = CompletableFuture.supplyAsync(() -> {
            System.out.println("Задача выполнятся в потоке: "+Thread.currentThread().getName());
            return "Ура!";
        },customPool);
        customPool.shutdown();
        personalPool.thenAccept(System.out::println).join();
    }
}
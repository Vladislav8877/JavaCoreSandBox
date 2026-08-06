package s06_multithreading.t07_Practice;

import java.util.concurrent.*;

class One {
    public static void main(String[] args) throws InterruptedException {

        CompletableFuture<Double> futurePriceOne = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
            }
            return 2000.0;
        });

        CompletableFuture<Double> futurePriceTwo = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
            }
            return 15.0;
        });

        CompletableFuture<String> totalFutureResult = futurePriceOne
                .thenCombine(futurePriceTwo, (price, discount) -> price * (1 - discount / 100.0))
                .handle((res, ex) -> {
                    if (ex != null) {
                        return "Ошибка при расчете: " + ex.getMessage();
                    } else {
                        return "Итогова цена со скидкой: " + res;
                    }
                });
        totalFutureResult.thenAccept(System.out::println).join();
    }
}
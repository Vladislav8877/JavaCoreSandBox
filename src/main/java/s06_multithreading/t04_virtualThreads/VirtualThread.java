package s06_multithreading.t04_virtualThreads;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class VirtualThread {
    // Есть 3 способа создать виртуальные потоки
    public static void main(String[] args) throws InterruptedException {
        // Способ 1: Прямой запуск (Быстро и просто)
        // Если тебе нужно просто запустить фоновую задачу и забыть про неё, используй метод startVirtualThread.

        Thread vThread = Thread.startVirtualThread(() -> {
            System.out.println("Привет из Весёлого поплавка и виртуального потока: "+Thread.currentThread().getName());
        });

        //join

        try {
            vThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Способ 2: Через билдер (Если нужно настроить имя)
        // Если ты хочешь дать потоку красивое имя (что очень полезно при чтении логов), используй Thread.ofVirtual().

        Thread myVirtualThread = Thread.ofVirtual()
                .name("МойСуперПоток")
                .start(() -> {
                    System.out.println("Работает: "+Thread.currentThread().getName());
                });
        myVirtualThread.join();

        /*Способ 3: Современный Пул Потоков (Лучшая практика)
        В реальных приложениях (например, на серверах) мы не создаем потоки поштучно.
        Мы используем ExecutorService. В Java 21 появился специальный пул, который создает новый виртуальный поток
        для каждой новой задачи */

        // Создаем пул, который будет штамповать виртуальные потоки
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            // Отправляем в пул 10 000 задач!
            for (int i = 1; i < 10; i++) {
                final int taskNumber = i;
                executor.submit(() -> {
                    try {
                        // Имитируем запрос к базе данных (сон 1 секунда)
                        Thread.sleep(1000);
                        System.out.println("Задача " + taskNumber + " выполнена потоком: " + Thread.currentThread());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
            System.out.println("Задачи отправлены. Ждем выполнения...");
        }
    }
}

// ---

class ImageDownloader {

    public static void main(String[] args) {
        int imagesCount = 50;
        System.out.println("Начинаем загрузку " + imagesCount + " картинок...");
        Instant start = Instant.now();

        try(var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 1; i <= imagesCount; i++) {
                final int imageNumber = i;
                executor.submit(() -> {
                    downloadImage(imageNumber);
                });
            }
        }

        Instant finish = Instant.now();
        System.out.println("Все картинки скачаны за " + Duration.between(start, finish).toMillis() + " мс!");
    }

    // Этот метод уже написан за тебя, его менять не нужно
    private static void downloadImage(int imageId) {
        try {
            System.out.println(Thread.currentThread() + " качает картинку #" + imageId);
            // Имитация задержки сети (2 секунды)
            Thread.sleep(2000);
            System.out.println("✅ Картинка #" + imageId + " готова!");
        } catch (InterruptedException e) {
            System.out.println("Загрузка прервана!");
        }
    }
}

// ---

class PriceAggregator {
    public static void main(String[] args) {

        try(var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            System.out.println("Начинаем поиск цен");
            // 1. Отправляем первого разведчика в DNS
            // Заметь: теперь внутри лямбды есть слово RETURN!
            Future<Integer> futureDNS = executor.submit(() -> {
                Thread.sleep(2000);
                return 45000;
            });

            // 2. Отправляем второго разведчика в M.Video
            Future<Integer> futureM = executor.submit(() -> {
                Thread.sleep(1000);
                return 47000;
            });

            System.out.println("Разведчики отправлены! У нас на руках два чека (Future).");
            System.out.println("Главный поток может пока поделать свои дела...\n");

            // 3. Пытаемся получить результат из первой "коробки"
            System.out.println("Ждем ответ от DNS...");
            int priceDNS = futureDNS.get(); // ТУТ ГЛАВНЫЙ ПОТОК ЗАВИСНЕТ, пока ответ не придет
            System.out.println("DNS ответил: " + priceDNS + " руб.");

            // 4. Получаем результат из второй "коробки"
            System.out.println("Ждем ответ от M.Video...");
            int priceM = futureM.get();
            System.out.println("M.Video ответил: " + priceM + " руб.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/*
Callable (Наличие return): Как только ты написал внутри submit() слово return 45000;, менеджер потоков executor понял:
"Ага, этот парень хочет получить данные обратно!".
Future<Integer> (Коробка с обещанием): Метод submit возвращает управление мгновенно.
Главный поток не ждет 2 секунды. Он моментально получает объект Future — пустую коробку,
в которой когда-нибудь в будущем появится число (Integer).
future.get() (Вскрытие коробки): Это самый важный метод.
Когда ты вызываешь get(), ты говоришь: "Мне срочно нужен результат прямо сейчас".
Если виртуальный поток уже принес цену — ты получишь её мгновенно.
Если поток еще "спит" или "качает" — твой главный поток main остановится на этой строчке и будет ждать, пока коробка не заполнится.
 */
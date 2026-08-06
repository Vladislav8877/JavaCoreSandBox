package s01_exception.t01_tryCatch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Trening {
    public static void main(String[] args) {

        try {
            int[] arr = new int[5];
            System.out.println(arr[5]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("ArrayIndexOutOfBoundsException Warning");
        } finally {
            System.out.println("Programming is STOP");
        }
    }
}

class one {
    public static void main(String[] args) {

        try {
           int[] arr = new int[2];
           arr[2] = 10;
           int result = arr[1]/0;
            System.out.println(result);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("ArrayIndexOutOfBoundsException Warning");
        } catch (ArithmeticException e) {
            System.out.println("ArithmeticException Warning");
        }
    }
}

class two {
    public static void main(String[] args) {

        try {
            int[] arr = {10,20};
            int result = arr[2]/0;
            System.out.println(result);
        } catch (ArrayIndexOutOfBoundsException | ArithmeticException e) {
            System.out.println("ArrayIndexOutOfBoundsException | ArithmeticException Warning");
        }
    }
}


class three {
    public static void main(String[] args) {

        String input = "ноль";

        try {
            int number = Integer.parseInt(input);
            int result = 100/number;
            System.out.println("Результат: "+result);
        } catch (NumberFormatException | ArithmeticException e) {
            // "е" - это объект-отчет об ошибки
            System.err.println("Ошибка!");
            //ищем тип ошибки (Тип объекта)
            System.err.println("Тип исключения: "+e.getClass().getSimpleName()); //Эта команда говорит нам, к какому классу относится исключение.
            //Читаем сообщение внутри объекта
            System.err.println("Сообщение от Java: "+ e.getMessage()); //Эта команда выводит краткое текстовое пояснение, что именно не понравилось системе.
            //Выводим маршрутный лист (Стек вызовов)
            System.err.println("Стек вызовов: ");
            e.printStackTrace(); //это целая история перемещений ошибки по твоему коду.
        }
    }
}

class example {

    static void runExample(int mode){
        if(mode == 1){
            int a = 10;
            int b  = a/0;
        }
        if(mode == 2){
            int[] arr = new int [2];
            System.out.println(arr[2]);
        }
        if(mode == 3){
            throw new RuntimeException();
        }
    }
    public static void main(String[] args) {

        try {
            runExample(3);
        } catch (ArithmeticException e) {
            System.out.println("ArithmeticException Warning");
            System.err.println("Тип исключения: "+e.getClass().getSimpleName());
            System.err.println("Читаем внутренние сообщение: "+e.getMessage());
            System.err.println("Вызываем стек вызовов: ");
            e.printStackTrace();
        } catch (RuntimeException e) {
            System.out.println("RuntimeException Warning");
            System.err.println("Тип исключения: "+e.getClass().getSimpleName());
            System.err.println("Читаем внутренние сообщение: "+e.getMessage());
            System.err.println("Вызываем стек вызовов: ");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Exception Warning");
            System.err.println("Тип исключения: "+e.getClass().getSimpleName());
            System.err.println("Читаем внутренние сообщение: "+e.getMessage());
            System.err.println("Вызываем стек вызовов: ");
            e.printStackTrace();
        }
    }
}

class test{
    public static void main(String[] args) {

        String input1 = "10";
        String input2 = "0";

        try {
            int num1 = Integer.parseInt(input1);
            int num2 = Integer.parseInt(input2);
            System.out.println("Результат: " + (num1 / num2));
        } catch (ArithmeticException e) {
            System.out.println("ArithmeticException Warning");
        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException Warning");
        } finally {
            System.out.println("Завершение работы калькулятора");
        }
    }
}

// --------- Обработка Исключений


class exc{
    public static void main(String[] args) {

        // 1. Создаем поток ввода-вывода внутри круглых скобок try(...)
        try (BufferedReader reader = new BufferedReader(new FileReader("file.txt"))) {
            // 2. Читаем первую строку файла
            System.out.println(reader.readLine());
        } catch (IOException e) {
            // 3. Ловим ошибку, если файла нет или диск сбоит
            System.out.println("Ошибка: " + e.getMessage());
        }   // 4. В этот момент Java САМА вызывает reader.close()

        /*
        Try-Catch-Resources - для авт. закрытия ресурсов
        try(..) - это ключевое место. Всё, что объявлено внутри этих скобок, Java считает ресурсом.
        Это значит, она берёт обязательство закрыть это, как только блок try закончится (неважно, успешно или с ошиб.)
        FileReader - умеет читать байты из файла и превращать их в буквы
        BufferedReader - берет эти буквы и складывает в буфер(в память) чтобы читать их большими кусками или
        целыми специальными строками(readLine). Это оптимизация скорости
        reader.readLine() - читает строку до первого переноса
        Самое важное - неявное закрытие. Не нужно писать finally{reader.close();} Компилятор дописывает этот код в байт-коде
         */
    }
}

class MyDataBase implements AutoCloseable {

    public MyDataBase() {
        System.out.println("Подключение к базе данных...");
    }
    public void readData() {
        System.out.println("Читаем данные...");
    }
    @Override
    public void close(){
        System.out.println("Соединение закрыто автоматически");
    }

    public static void main(String[] args) {
        // исп. try-with-resources
        try (MyDataBase db = new MyDataBase()) {
            db.readData();
            //Здесь может вылететь ошибка но close() всё равно сработает
        } catch (Exception e){
            System.out.println("Ошибка!");
        }
    }
    /*
    В примере Java видит, что MyDataBase создан внутри круглых скобок try (...).
    Это сигнал для компилятора: "Как только этот блок закончится (успешно или с ошибкой),
    автоматически вызови метод close() для этого объекта"
     */
}


class LoggingExample {

   static final Logger logger = Logger.getLogger(LoggingExample.class.getName());

    public static void main(String[] args) {

        try {
            int res = 10/0;
            System.out.println(res);
        } catch (ArithmeticException e) {
            logger.log(Level.SEVERE,"ArithmeticException", e);
        }
    }
    /*
    Логирование позволяет разобраться в причинах ошибки
    Logger.getLogger - мы просим Java дать нам "журналиста" привязанного к имени нашего класса
    Это нужно, что бы в логах было видно, откуда пришло сообщение(из класса LoggingExample)
    Level.WARNING - указывает серьёзность. INFO - просто инф. WARNING - предупреждение. ERROR/SEVERE -ошибка
    Передача "е" - в логирование. Он сохраняет StackTrace (весь пути ошибки) Увидим в какой строке метода main случилось
     */
}

//----- Теперь задачи на тему



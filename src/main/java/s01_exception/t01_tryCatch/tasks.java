package s01_exception.t01_tryCatch;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import static s01_exception.t01_tryCatch.LoggingExample.logger;


class FileW {

    private static final Logger logger = Logger.getLogger(FileW.class.getName());

    public void writeToFile(String fileName, String text){

        try (FileWriter writer = new FileWriter(fileName)) {

            writer.write(text);
            System.out.println("успех");

        } catch (IOException e ) {
            logger.log(Level.SEVERE,"IOException"+ fileName, e);
        }
    }
}

class main{
    public static void main(String[] args) {
        FileW fileW = new FileW();
        fileW.writeToFile("test.txt", "Hello - goodLuck my friend");
    }
}

//-------------

class Bank {

    private static final Logger logger = Logger.getLogger(Bank.class.getName());

    public void process(String targetFile, String transactionData){

        try(FileWriter writer = new FileWriter(targetFile)) {
            writer.write(transactionData);
            throw new IOException("сбой связи с банком!");
        } catch (IOException e ) {
            logger.log(Level.SEVERE,"Ошибка поймана, проверяем файл",e);
        }
    }
}
class main2{

    public static void main(String[] args) {

        Bank bank = new Bank();
        bank.process("test.txt", "Hello - goodLuck my friend");
    }
}
//-----
class RobotArm implements AutoCloseable{

    void move(){
        System.out.println("Робот двигается");
    }

    @Override
    public void close(){
        System.out.println("Питание робота отключено - манипулятор заблокирован!");
    }

    private static final Logger logger = Logger.getLogger(RobotArm.class.getName());
}

class main3{
    public static void main(String[] args) {

        try (RobotArm robot = new RobotArm()){
            robot.move();
        } catch (Exception e){
            logger.log(Level.SEVERE,"ошибка... ошибка...",e);
        }

    }
}
//----
class main4 {
    public static void main(String[] args) {

        String file = "file.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(file))){

            String line;
            while((line = reader.readLine()) != null) { //стандартный цикл для чтения
                System.out.println(line); // читает построчно и записывает всё в line пока файл не закончится
            }
        } catch (IOException e) { //этот блок выполниться если например файл не будет найден
            System.err.println("Ошибка: "+e.getMessage());
        }
    }
}
//------
class main5 {
    public static void main(String[] args) {

        String filePatch = "output.txt";

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePatch))){

            writer.write("Первая строка. Записанная в файл");
            writer.newLine();
            writer.write("Вторая строка");
            writer.newLine();
            writer.write("Конец!");
        } catch (IOException e ) { //этот блок выполниться если например у нас нет прав на запись в папку
            System.err.println("Ошибка: "+e.getMessage());
        }
    }
}
//-------
class main6 {

    private static final Logger logger = Logger.getLogger(main6.class.getName());

    public static void main(String[] args) {

        try {
            int a = 10/0;
        } catch (ArithmeticException e) {
            logger.log(Level.SEVERE,"ArithmeticException",e);
        }

    }
}
// ---------------------------------
class main7 {
    public static void main(String[] args) {

        String file = "input.txt";

        try (BufferedReader reader = new BufferedReader((new FileReader(file)))) {

            String line;
            while((line = reader.readLine()) != null){
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("IOException: "+e.getMessage());
        }
    }
}

class main8 {
    public static void main(String[] args) {

        String inputFile = "input.txt";
        String outputFile = "output.txt";
        int counter = 1;
        String line;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile)); BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            while((line = reader.readLine()) !=null){
                writer.write(counter+": "+line);
                writer.newLine();
                counter++;
            }

        } catch (IOException e) {
            System.out.println("Exception: "+e.getMessage());
        }
    }
}

class main9  {

    private static final Logger logger = Logger.getLogger(main9.class.getName());

    static String inputFile = "input.txt";
    static String outputFile = "output.txt";
    static int counter = 1;
    static String line;

    public static void main(String[] args) {

        logger.log(Level.INFO,"Запуск процесса копирования...");

        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile)) ; BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            while((line = reader.readLine()) != null){
                writer.write(counter+": "+line);
                writer.newLine();
                counter++;
                logger.log(Level.FINE,"Добавили новую строчку - "+line);
            }
            logger.log(Level.INFO,"Копирование успешно завершено!");
        } catch (IOException e){
            logger.log(Level.SEVERE,"IOException: "+e);
        }
    }

    // что бы было видно FINE - нужно настроить фильтр сервера с помощью кода в Main или создать файл в корне!
}

package s01_exception.t01_tryCatch;

import java.sql.SQLException;

public class MyCheckedException extends Exception {

    public MyCheckedException() {
        super();
    }
    public MyCheckedException(String message) {
        super(message);
    }
}

class MyUncheckedException extends RuntimeException {

    public MyUncheckedException() {
        super();
    }
    public MyUncheckedException(String message) {
        super(message);
    }
}

//------------------------------------

class NegativeAmountException extends Exception {

    public NegativeAmountException() {
        super();
    }
    public NegativeAmountException(String message) {
        super(message);
    }
}

class BankAccount {

    private double balance = 1000.0;

    public void withdraw(double amount) throws NegativeAmountException {
     if (amount < 0) {
         throw new NegativeAmountException("Ошибка: сумма не может быть отрицательной - "+amount); // создаём объект с детальным сообщением
     }

     balance-=amount;
        System.out.println("Успешное снятие. Баланс: "+balance);
    }

    public static void main(String[] args) {

        BankAccount bankAccount = new BankAccount();

        try {
            bankAccount.withdraw(-100);
        } catch (NegativeAmountException e) {
            System.out.println(e.getMessage());
        }
    }
}

//-------------------------------------

class PaymentException extends Exception {

    public PaymentException() {
        super();
    }
    public PaymentException(String message) {
        super(message);
    }
    public PaymentException(String message, Throwable cause) {
        super(message, cause);
    }

    public static void main(String[] args) throws PaymentException {

        try {
            throw new SQLException("сервер базы данных недоступен!");
        } catch (SQLException e) {
            throw new PaymentException("платёж не прошёл",e);
        }
    }
}

//-----------------

class AccessDeniedException extends Exception {

    public AccessDeniedException(String message) {
        super(message);
    }
}

class BunkerSystem {

    // низкий уровень: просто кидает ошибку
    public void checkSystem(int code) throws AccessDeniedException {
        if(code != 123){
            throw new AccessDeniedException("Критическая ошибка доступа");
        }
    }

    // средний уровень: пробрасывает дальше не исп. try-catch
    public void startBunker(int code) throws AccessDeniedException {
        System.out.println("Запуск системы");
        checkSystem(code);
        System.out.println("Системы запущены успешно");
    }

    /*
    public void startBunker(int cod) {
     try {
     System.out.println("Запуск системы");
     checkSystem(code);
     System.out.println("Системы запущены успешно");
     } catch (AccessDeniedException e) {
     e.getMessage()...
        }
     */

    public static void main(String[] args) {

        BunkerSystem bunkerSystem = new BunkerSystem();

        // высокий уровень: ловим ошибку
        try {
            bunkerSystem.startBunker(13);
        } catch (AccessDeniedException e) {
            System.out.println(e.getMessage());
        }

    }
}

//-------

class MyException extends Exception {
    public MyException(String message, Throwable cause) {
        super(message, cause);
    }

    public static void doSomething() throws MyException {
     try {
         int result = 10/0;
         System.out.println(result);
     } catch (ArithmeticException e) {
         throw new MyException("произошла ошибка в исчислениях",e);
     }
    }

    public static void main(String[] args) {
        try {
            doSomething();
        } catch (MyException e) {
            System.out.println(e.getMessage());
            System.out.println("настоящая причина: "+e.getCause());
        }
    }
}

class MyPersonException extends Exception {

    private final int errorCode;

    public MyPersonException(String message, Throwable cause, int errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    public int getErrorCode() {return errorCode;}
}
class man {
    public void math(int numberOne, int numberTwo) throws MyPersonException {
        try {
            int result = numberOne / numberTwo;
            System.out.println(result);
        } catch (ArithmeticException e) {
            throw new MyPersonException("деление на ноль запрещено!!!", e, 101);
        }
    }

    public static void main(String[] args) {
        man man = new man();

        try {
            man.math(10,0);
        } catch (MyPersonException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getErrorCode());
        }

    }
}



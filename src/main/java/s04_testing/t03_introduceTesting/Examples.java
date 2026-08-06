package s04_testing.t03_introduceTesting;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Calculator {
    public int sum(int a, int b) {
        return a+b;
    }
}
class CalculatorTest {
    @Test
    @DisplayName("Тестовые методы - 1")
    public void testSum() {
        Calculator c = new Calculator();

        assertEquals(10, c.sum(5,5));
    }
}

//---

class CalculatorTwo {
    public int sum(int a, int b) {
        if(a < 0 || b < 0) {
            throw new IllegalArgumentException("NegativeArgument!");
        }
        return a+b;
    }
}

class CalculatorTwoTest {
    @Test
    @DisplayName("Тестовые методы - 2")
    public void sumTwo() {
        CalculatorTwo c = new CalculatorTwo();

        assertEquals(10, c.sum(5,5));
        // assertEquals(1,c.sum(-2,3));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> c.sum(-2,3));
        assertEquals("NegativeArgument!", exception.getMessage());
    }
}

//---

interface BankApi {
    boolean withdrawMoney(int amount);
}

class PizzaService { // зависимость D.
    private BankApi bank;

    public PizzaService(BankApi bank) {
        this.bank = bank;
    }

    public String orderPizza(int amount) {
        boolean isPaid = bank.withdrawMoney(amount);

        if (isPaid) {
            return "Pizza is cooking";
        } else {
            return "Pizza can't be cooked - you don't have enough money";
        }
    }
}

/*class BadTest {
    @Test
    void test() {
        BankApi bank = new RealSberBankApi();
        PizzaService pizzaService = new PizzaService(bank);

        String res = pizzaService.orderPizza(500); //Пытаемся заказать пиццу... и тест падает, потому что нет интернета!
        assertEquals("Pizza is cooking", res);
    }
}
*/

class FakeBank implements BankApi {
    @Override
    public boolean withdrawMoney(int amount) {
        // Он никуда не лезет! Он просто всегда отвечает "ДА, деньги списаны"
        return true;
    }
}

class PizzaServiceTest {
    @Test
    void shouldOrderPizzaWhenPaymentIsSuccessful() {
        // 1. Подготовка: берем нашу пустышку
        BankApi fakeBank = new FakeBank();
        // 2. ИЗОЛЯЦИЯ: подсовываем нашему сервису игрушечный банк вместо настоящего!
        // Сервис об этом не подозревает. Он думает, что работает с настоящим.
        PizzaService pizzaService = new PizzaService(fakeBank);
        // 3. Действие: вызываем публичный интерфейс нашего класса
        String result = pizzaService.orderPizza(500);
        // 4. Проверка: всё сработало как часы!
        assertEquals("Pizza is cooking", result);
    }
}
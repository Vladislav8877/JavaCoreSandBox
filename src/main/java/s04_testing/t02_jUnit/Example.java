package s04_testing.t02_jUnit;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ExampleTest {

    //запуститься один раз перед запуском всех тестов
    @BeforeAll
    static void initAll() {
        System.out.println("Before All Tests");
    }

    //запускается перед каждым тестом
    @BeforeEach
    void init() {
        System.out.println("Before each Test");
    }

    //тестовые методы которые тестируют какую-то логику
    @Test
    void shouldPlusNumbersCorrectly() {
        //Arrange
        int a = 2;
        int b = 3;
        //Act
        int res = a+b;
        //Assert
        assertEquals(5,res,"Сумма 2 и 3 должна быть равна 5");
    }
    @Test
    void shouldMinusNumbersCorrectly() {
        int a = 4;
        int b = 3;
        int res = a-b;
        assertEquals(1,res,"Результат 4 и 3 должен быть равен 1");
    }

    //запускается после каждого теста
    @AfterEach
    void tearDown() {
        System.out.println("After each Test");
    }

    //запуститься только один раз после запуска всех тестов
    @AfterAll
    static void tearDownAll() {
        System.out.println("After All Tests");
    }
}

//---

class DiscountCalculator {

    public double calculate(double amount, double sale) {
        if (sale < 0.0 || sale > 100.0) {
            throw new IllegalArgumentException("Sale value must be between 0.0 and 100");
        } else if (sale == 0.0) {
            return amount;
        } else {
            return amount - (amount * (sale / 100.0));
        }
    }
}

class DiscountCalculatorTest {

    private DiscountCalculator calc;

    @BeforeEach
    void init() {
        calc = new DiscountCalculator();
    }

    @Test
    @DisplayName("Применение скидки в 20%")
    void shouldCalculateDiscount() {
        assertEquals(800.0,calc.calculate(1000.0, 20.0),"Ошибка: логика скидки неверна");
    }

    @Test
    @DisplayName("Применение граничной скидки в 0%")
    void shouldNotChangePriceWhenDiscountZero() {
        assertEquals(1000.0,calc.calculate(1000.0, 0.0),"Ошибка: цена не должна изменяться");
    }

    @Test
    @DisplayName("Применение граничной скидки в 100%")
    void shouldChangePriceWhenDiscountHundred() {
        assertEquals(0.0,calc.calculate(1000.0,100.0),"Ошибка: цена товара должна быть равна 0");
    }

    @Test
    @DisplayName("Применение скидки ниже 0% и выше 100%")
    void shouldThrowExceptionWhenDiscountIsZeroAndAboveThenHundred() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> calc.calculate(1.000,-1.0)),
                () -> assertThrows(IllegalArgumentException.class, () -> calc.calculate(1.000,101.0))
        );
    }


    /* @Test
    @DisplayName("Некорректный ввод данных")
    void shouldThrowsExceptionWhenDiscountIsNegative() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> calc.calculate(1000.0,-1.0));
        assertEquals("Sale value must be between 0.0 and 100",exception.getMessage());
    }
     */
}

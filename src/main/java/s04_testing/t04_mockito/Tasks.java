package s04_testing.t04_mockito;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


interface Thermometer {
    int getTemperature();
}

class SmartConditioner {
    private final Thermometer thermometer;

    public SmartConditioner(Thermometer thermometer) {
        this.thermometer = thermometer;
    }

    public String checkAndTurnOn() {
        int temperature = thermometer.getTemperature();
        return temperature > 30 ? "Включаю охлаждение" : "Температура в норме";
    }
}

class SmartConditionerTest {
    @Test
    void shouldTurnOnWhenHot() {
        Thermometer conditioner = mock(Thermometer.class);
        when(conditioner.getTemperature()).thenReturn(35);

        SmartConditioner smartConditioner = new SmartConditioner(conditioner);
        String result = smartConditioner.checkAndTurnOn();

        assertEquals("Включаю охлаждение", result,"Ошибка: кондиционер должен включиться");
        verify(conditioner,times(1)).getTemperature();
    }
}

//---

interface BankService {
    boolean validatePin(String pin);
}

class ATM {
    private final BankService bankService;

    public ATM(BankService bankService) {
        this.bankService = bankService;
    }

    public String enterPin(String pin) {
        boolean result = bankService.validatePin(pin);

        if(result) {
            return "Доступ разрешен";
        } else {
            return "Доступ запрещен";
        }
    }
}

/*class ATMTest {
    @Test
    void shouldBlockCardWhenPinIsWrong() {
        BankService bankService = mock(BankService.class);
        when(bankService.validatePin("0000")).thenReturn(false);
        when(bankService.validatePin("1234")).thenReturn(true);

        ATM atm = new ATM(bankService);
        String wrongResult = atm.enterPin("0000");
        String correctResult = atm.enterPin("1234");

        assertEquals("Доступ запрещен",wrongResult,"Ошибка: неправильный пин-код должен запрещать доступ к карточке");
        assertEquals("Доступ разрешен",correctResult,"Ошибка: правильный пин-код не должен разрешать доступ к карточке");

        verify(bankService,times(1)).validatePin("0000");
        verify(bankService,times(1)).validatePin("1234");
        verify(bankService,times(2)).validatePin(anyString());
    }
}
 */

class ATMTest {
    // Тест 1: Негативный сценарий
    @Test
    void shouldBlockCardWhenPinIsWrong() {
        // Arrange
        BankService bankService = mock(BankService.class);
        when(bankService.validatePin("0000")).thenReturn(false);
        ATM atm = new ATM(bankService);

        // Act
        String wrongResult = atm.enterPin("0000");

        // Assert
        assertEquals("Доступ запрещен", wrongResult, "Ошибка: неправильный пин-код должен запрещать доступ");
        verify(bankService, times(1)).validatePin("0000");
    }

    // Тест 2: Позитивный сценарий
    @Test
    void shouldAllowAccessWhenPinIsCorrect() {
        // Arrange
        BankService bankService = mock(BankService.class);
        when(bankService.validatePin("1234")).thenReturn(true);
        ATM atm = new ATM(bankService);

        // Act
        String correctResult = atm.enterPin("1234");

        // Assert
        assertEquals("Доступ разрешен", correctResult, "Ошибка: правильный пин-код ДОЛЖЕН разрешать доступ");
        verify(bankService, times(1)).validatePin("1234");
    }
}

//---

interface PromoApi {
    int getDiscountPercent(String promo);
}

class CheckoutService {
    private final PromoApi promoApi;

    public CheckoutService(PromoApi promoApi) {
        this.promoApi = promoApi;
    }

    public double calculateTotal(double price, String promoCode) {
        int discount = promoApi.getDiscountPercent(promoCode);

        if(discount == 0) {
            return price;
        }
        return price - (price * (discount / 100.0));
    }
}

class CheckoutServiceTest {
    @Test
    void shouldApplyDiscountWhenPromoIsValid () {
        PromoApi promoApi = mock(PromoApi.class);
        when(promoApi.getDiscountPercent("summer")).thenReturn(20);
        CheckoutService service = new CheckoutService(promoApi);

        double result = service.calculateTotal(1000.0,"summer");

        assertEquals(800.0,result,"Ошибка: скидка должна снижать стоимость");
        verify(promoApi,times(1)).getDiscountPercent("summer");
    }

    @Test
    void shouldNotChangePriceWhenPromoIsInvalid () {
        PromoApi promoApi = mock(PromoApi.class);
        when(promoApi.getDiscountPercent("fake")).thenReturn(0);
        CheckoutService service = new CheckoutService(promoApi);

        double result = service.calculateTotal(1000.0,"fake");

        assertEquals(1000.0,result,"Ошибка: неверный промокод не должен изменять цену");
        verify(promoApi,times(1)).getDiscountPercent("fake");
    }
}

//---

interface PinValidator {
    boolean isValid(String pin);
}

interface PoliceNotifier {
    void callPolice(String reason);
}

class DoorController {
    public void open() {
        System.out.println("Дверь физически открылась");
    }
}

class SecuritySystem {
    final private PinValidator validator;
    final private PoliceNotifier police;
    final private DoorController door;

    public SecuritySystem(PinValidator validator, PoliceNotifier police, DoorController door) {
        this.validator = validator;
        this.police = police;
        this.door = door;
    }

    public void enterPin(String pin) {
        if(validator.isValid(pin)) {
            door.open();
        } else {
            police.callPolice("Взлом!: введен неверный пин: "+pin);
        }
    }
}

class SecuritySystemTest {
    @Test
    void shouldCallPoliceAndLockDoorWhenPinIsWrong() {
        PinValidator validator = mock(PinValidator.class);
        when(validator.isValid(anyString())).thenReturn(false);

        PoliceNotifier police = mock(PoliceNotifier.class);

        DoorController door = new DoorController();
        DoorController spyDoor = spy(door);
        //---
        SecuritySystem security = new SecuritySystem(validator,police,spyDoor);
        //---
        security.enterPin("6666");
        //---
        verify(police, times(1)).callPolice(anyString());
        verify(spyDoor, never()).open();
    }
}
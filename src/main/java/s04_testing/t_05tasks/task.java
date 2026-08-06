 /* package testing.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static javax.management.Query.times;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static sun.java2d.cmm.ProfileDataVerifier.verify;


class User {
    String name;
    String status;

    public User(String name, String status) {
        this.name = name;
        this.status = status;
    }
}

interface DiscountRepository {
    int getDiscountPercent(User user);
}

class OrderService {
    private final DiscountRepository discountRepo;
    public OrderService(DiscountRepository DiscountRepo) {
        this.discountRepo = DiscountRepo;
    }

    public double calculateDiscount(User user, double amount) {
        int discount = discountRepo.getDiscountPercent(user);
        return amount - (amount *(discount / 100.0));
    }
}


class OrderServiceTest {
    private DiscountRepository discountRepo;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        discountRepo = mock(DiscountRepository.class);
        orderService = new OrderService(discountRepo);
    }

    private User createHelper(String role) {
        return new User("Иван Иванов",role);
    }

    @Test
    void shouldApplyVipDiscount() {
        //Arrange
        User vipUser = createHelper("VIP"); //исп. хелпер
        when(discountRepo.getDiscountPercent(vipUser)).thenReturn(20);

        //Act
        double result = orderService.calculateDiscount(vipUser,1000.0);

        //Assert
        assertEquals(800.0,result,"Ошибка: VIP-клиент должен получить скидку 20%");
        verify(discountRepo,times(1)).getDiscountPercent(vipUser);
    }

    @Test
    void shouldReturnZeroWhenNewClientIsNotVip() {
        User newUser = createHelper("NEW");
        when(discountRepo.getDiscountPercent(newUser)).thenReturn(0);

        double result = orderService.calculateDiscount(newUser,1000.0);

        assertEquals(1000.0,result,"Ошибка: NEW-клиент должен получить скидку 0%");
        verify(discountRepo,times(1)).getDiscountPercent(newUser);
    }
}

//---

class Client {
    String name;
    String status;

    public Client(String name, String status) {
        this.name = name;
        this.status = status;
    }
}

interface CalculateDiscount {
    int getDiscount(Client client);
}

interface PromoCode {
    int getPromoCode(String promo);
}

class DiscountService {
    private final CalculateDiscount calculateDiscount;
    private final PromoCode promoCode;

    public DiscountService(CalculateDiscount calculateDiscount, PromoCode promoCode) {
        this.calculateDiscount =  calculateDiscount;
        this.promoCode = promoCode;
    }

    public double calculateDiscount(Client client, double amount, String promo) {
        int discount = calculateDiscount.getDiscount(client);

        if (promo.equals("summerONE")) {
            discount += promoCode.getPromoCode(promo);
        }
        return amount - (amount*(discount / 100.0));
    }
}

class DiscountServiceTest {
    private CalculateDiscount discount;
    private PromoCode promo;
    private DiscountService service;

    @BeforeEach
    void setUp() {
        discount = mock(CalculateDiscount.class);
        promo = mock(PromoCode.class);
        service = new DiscountService(discount,promo);
    }

    private Client createHelper(String role) {
        return new Client("Ivan Ivanov",role);
    }

    @Test
    @DisplayName("Клиент без статуса покупает продукцию и не получает скидку")
    void simpleClientShouldNotHAveDiscount() {
        Client simpleUser = createHelper("NEW");
        when(discount.getDiscount(simpleUser)).thenReturn(0);

        double result = service.calculateDiscount(simpleUser,1000.0,"summer");

        assertEquals(1000.0,result,"Ошибка: скидка не должна применятся к клиенту без статуса");
        verify(discount, times(1)).getDiscount(simpleUser);
        verify(promo,never()).getPromoCode(anyString());
    }

    @Test
    @DisplayName("Вип-клиент покупает продукцию и получает скидку 20%")
    void vipClientShouldGetDiscount() {
        Client vipClient = createHelper("VIP");
        when(discount.getDiscount(vipClient)).thenReturn(20);

        double result = service.calculateDiscount(vipClient,1000.0,"winter");

        assertEquals(800.0,result,"Ошибка: скидка должна применяться к клиенту со статусом");
        verify(discount,times(1)).getDiscount(vipClient);
        verify(promo,never()).getPromoCode(anyString());
    }

    @Test
    @DisplayName("Клиент без статуса покупает продукт, вводит промокод и получает скидку")
    void simpleClientShouldGetPromoDiscount() {
        Client simpleUser = createHelper("NEW");
        when(discount.getDiscount(simpleUser)).thenReturn(0);
        when(promo.getPromoCode("summerONE")).thenReturn(20);

        double result = service.calculateDiscount(simpleUser,1000.0,"summerONE");

        assertEquals(800.0,result,"Ошибка: промокод должен давать скидку клиенту");
        verify(discount,times(1)).getDiscount(simpleUser);
        verify(promo,times(1)).getPromoCode(anyString());
    }

    @Test
    @DisplayName("Вип-клиент вводит промокод и получает скидку в 40%")
    void vipClientShouldGetPromoDiscount() {
        Client vipClient = createHelper("VIP");
        when(discount.getDiscount(vipClient)).thenReturn(20);
        when(promo.getPromoCode("summerONE")).thenReturn(20);

        double result = service.calculateDiscount(vipClient,1000.0,"summerONE");

        assertEquals(600.0,result,"Ошибка: промокод должен давать скидку VIP-клиенту");
        verify(discount,times(1)).getDiscount(vipClient);
        verify(promo,times(1)).getPromoCode("summerONE");
    }
}
  */
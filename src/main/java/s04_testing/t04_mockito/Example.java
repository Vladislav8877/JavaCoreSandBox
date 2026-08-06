package s04_testing.t04_mockito;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class User {
    private String name;
    private int id;

    public User(String name, int id) {
        this.name = name;
        this.id = id;
    }
    public String getName() {
        return name;
    }
}

interface UserRepository {
    User findUserById(int id);
}

class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getUserName(int id) {
        User user = userRepository.findUserById(id);
        return user != null ? user.getName() : "Unknow";
    }
}

class UserServiceTest {
    @Test
    void shouldReturnUserNameWhenUserExists() {
        // Arrange
        // Mockito создает пустой класс-подделку на основе интерфейса UserRepository.
        UserRepository mockRepository = mock(UserRepository.class);

        // Создаем реального пользователя, которого мы якобы "достанем из базы"
        User dummyUser = new User("Alice", 1);

        // Читается так: "КОГДА (when) у мока вызовут метод findUserById с числом 1,
        // ТО ВЕРНИ (thenReturn) нашего dummyUser".
        when(mockRepository.findUserById(1)).thenReturn(dummyUser);

        // Передаем нашу подделку в реальный сервис. Сервис не замечает подвоха.
        UserService userService = new UserService(mockRepository);


        // 2. ACT (ДЕЙСТВИЕ)
        // Вызываем тестируемый метод. Внутри себя он обратится к нашему моку!
        String userName = userService.getUserName(1);


        // 3. ASSERT (ПРОВЕРКИ)
        // Проверяем логику: сервис должен был достать "Alice" из объекта и вернуть строку.
        assertEquals("Alice", userName);

        // Магия №3: Проверка поведения (Снятие записей с видеокамеры мока)
        // Мы говорим Mockito: "Убедись (verify), что mockRepository ровно 1 раз (times(1))
        // дернули за метод findUserById с параметром 1".
        // Если внутри UserService кто-то случайно стер строчку обращения к базе, этот verify упадет с ошибкой!
        verify(mockRepository, times(1)).findUserById(1);
    }
}

//---

interface BankApi {
    boolean charge(String cardNumber, double amount);
}
interface TicketPrinter {
    void sendTicketToEmail(String email);
}

class MovieTicketService {
    private final BankApi bank;
    private final TicketPrinter printer;

    public MovieTicketService(BankApi bank, TicketPrinter printer) {
        this.bank = bank;
        this.printer = printer;
    }

    public String byTicket(String cardNumber, String email, double price) {
        boolean isPaid = bank.charge(cardNumber, price);

        if(isPaid) {
            printer.sendTicketToEmail(email);
            return "Удачная оплата";
        } else {
            return "Ошибка оплаты";
        }
    }
}

class MovieTicketServiceTest {

    @Test
    void shouldNotSendTicketIfPaymentFails() {
        BankApi bank = mock(BankApi.class);
        TicketPrinter printer = mock(TicketPrinter.class);

        MovieTicketService service = new MovieTicketService(bank, printer);

        when(bank.charge("1234",500.0)).thenReturn(false);
        String result = service.byTicket("1234","vlad@gmail",500.0);

        assertEquals("Ошибка оплаты",result,"Ошибка: ...");

        verify(bank,times(1)).charge("1234",500.0);
        verify(printer, never()).sendTicketToEmail(anyString());
    }
}


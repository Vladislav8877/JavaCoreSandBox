package s04_testing.t01_myOldExamples;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class Hero {
    private String name;
    private int level = 1;
    private int experience = 0;

    public Hero(String name){
        this.name = name;
    }

    public void gainExperience(int amount){
        if (amount < 0) {
            throw new IllegalArgumentException("Experience cannot be negative");
        }
        experience += amount;

        while (experience >= 100) {
            level++;
            experience -= 100;
        }
    }
    public int getLevel() { return level; }
    public int getExperience() { return experience; }
}

class HeroTest {

    private Hero hero;

    @BeforeEach
    void setUp() {
        hero = new Hero("Oleg");
    }

    @Test
    @DisplayName("Уровень должен стать 2, а опыт 0 при получении 100 exp")
    void shouldLevelUpAtHundredExp(){
        hero.gainExperience(100);
        assertEquals(2, hero.getLevel(), "Уровень должен стать 2");
        assertEquals(0, hero.getExperience(), "Опыт должен стать 0");
    }

    //лучший способ записи нескольких проверок по логике в одном методе:
    @Test
    @DisplayName("Уровень должен стать 2, а опыт 0 при получении 100 exp")
    void shouldLevelUpAtHundredExpNew() {
        hero.gainExperience(100);
        assertAll("Проверка состояния героя после Level Up",
                () -> assertEquals(2,hero.getLevel(),"После получения exp - Lvl не стал = 2"),
                () -> assertEquals(0,hero.getExperience(),"После увелечения Lvl = exp не стал = 0")
        );
        //Шикарная запись, в старом способе если на первой проверке произойдет ошибка, вторая проверка
        //не будет произведена что повысит сложность поиска ошибки
    }

    @ParameterizedTest
    @DisplayName("Проверка мульти-повышения уровня при большом опыте")
    @CsvSource({
            "100, 2, 0",
            "250, 3, 50",
            "499, 5, 99",
            "1000, 11, 0"
    })
    void expShouldSaveAndUpMultiLvl(int amount, int expectedLvl, int expectedExp) {
        hero.gainExperience(amount);
        assertEquals(expectedLvl, hero.getLevel(), "Ошибка уровня");
        assertEquals(expectedExp, hero.getExperience(), "Ошибка опыта");
    }

    @Test
    @DisplayName("Опыт свыше 100 должен переходить на следующий уровень")
    void experienceShouldBeNotZero(){
        hero.gainExperience(120);
        assertEquals(20, hero.getExperience(), "Остаток опыта должен сохраниться");
    }

    @Test
    @DisplayName("Негативный сценарий: отрицательный опыт вызывает ошибку")
    void shouldThrowException(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> hero.gainExperience(-50));
        assertEquals("Experience cannot be negative", exception.getMessage());
    }

    //старый способ записи:
    @Test
    @DisplayName("Негативный сценарий: отрицательный опыт вызывает ошибку")
    void shouldThrowExceptionOldMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            int negativeExp = -1;
            hero.gainExperience(negativeExp);
        });
        assertEquals("Experience cannot be negative", exception.getMessage());
    }
}


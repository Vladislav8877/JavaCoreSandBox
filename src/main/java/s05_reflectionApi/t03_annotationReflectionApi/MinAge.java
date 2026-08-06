package s05_reflectionApi.t03_annotationReflectionApi;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MinAge {
    int value();
}

class Account {
    String login;
    @MinAge(18)
    int age;

   public Account(String login, int age) {
        this.login = login;
        this.age = age;
    }
}

class Validator {
    public static void main(String[] args) throws Exception {
        Account userOne = new Account("vlad",15);

        // Ищем поля с ограничением по возрасту
        for (Field field : userOne.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(MinAge.class)) {
                field.setAccessible(true);

                int actualAge = (int) field.get(userOne);
                MinAge rule = field.getAnnotation(MinAge.class);
                int requiredAge = rule.value();

                if (actualAge < requiredAge) {
                    System.out.println("Ошибка: Пользователю " + actualAge + " лет. Доступ разрешен с "+requiredAge);
                } else {
                    System.out.println("Доступ разрешен!");
                }
            }
        }
    }
}


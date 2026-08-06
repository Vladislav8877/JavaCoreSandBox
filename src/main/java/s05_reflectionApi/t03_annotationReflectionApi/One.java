package s05_reflectionApi.t03_annotationReflectionApi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class exp {

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.TYPE})
    public @interface MyAnnotation {
        String value() default "defaultValue";
    }
}

class Example {
    @exp.MyAnnotation(value = "Hello Reflection!")
    public void annotateMethod() {
        System.out.println("This method is annotated with @MyAnnotation");
    }
}

//---

class annotation {
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.TYPE})
    public @interface MyMagicBox {
        //Параметр аннотации - по умолчанию будет unknown
        String label() default "Unknown";

        //можно добавлять числа, массивы, boolean
        boolean isHeavy() default false;
    }
}

class MyService {

    @annotation.MyMagicBox(label = "Запуск сервера", isHeavy = true)
    public void startService() {
        System.out.println("Сервер запущен...");
    }

    public void doSecretStuff() {
        System.out.println("Секретные дела...");
    }

    @annotation.MyMagicBox(label = "Очистка кэша")
    public void cleanCash() {
        System.out.println("Кэш очищен...");
    }
}

class AnnotationReader {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {

        MyService service = new MyService();
        Class<?> clazz = service.getClass();

        for (Method method : clazz.getDeclaredMethods()) {
            //Проверяем - есть ли на методе мои аннотации
            if (method.isAnnotationPresent(annotation.MyMagicBox.class)) {
                annotation.MyMagicBox sticker = method.getAnnotation(annotation.MyMagicBox.class);

                System.out.println("Найден метод: "+method.getName());
                System.out.println("Описание: "+sticker.label());
                System.out.println("Процесс тяжелый?: "+sticker.isHeavy());

                System.out.println("Результат выполнения: ");
                method.invoke(service);
            }
        }
    }
}

//---


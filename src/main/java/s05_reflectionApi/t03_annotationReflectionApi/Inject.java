package s05_reflectionApi.t03_annotationReflectionApi;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Inject {
}

class Battery {
    public void charge() {
        System.out.println("Батарея заряжается");
    }
}

class SmartPhone {
    @Inject
    private Battery battery;

    public void turnOn() {
        if (battery != null) {
            battery.charge();
            System.out.println("Смартфон успешно вкл.");
        } else {
            System.out.println("Батареи нету в телефоне!");
        }
    }
}

class InjectorEngin {
    public static void main(String[] args) throws Exception {
        SmartPhone smartPhone = new SmartPhone();
        Class<?> clazz = smartPhone.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)) {
                field.setAccessible(true);
                Class<?> fieldType = field.getType();

                Object obj = fieldType.getDeclaredConstructor().newInstance();
                field.set(smartPhone,obj);
            }
        }
    }
}
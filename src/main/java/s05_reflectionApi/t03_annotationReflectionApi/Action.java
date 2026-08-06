package s05_reflectionApi.t03_annotationReflectionApi;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Action {
    String description() default "defaultValue";
}

class Service {

    @Action
    public void sendMessage() {
        System.out.println("Sending message...");
    }

    @Action(description = "description!")
    public void takeMessage() {
        System.out.println("Taking message...");
    }

    public void process() {
        System.out.println("Processing...");
    }
}

class mains {
    public static void main(String[] args) throws Exception {
        Service service = new Service();
        Class<?> clazz = Service.class;

        for (Method methods : clazz.getDeclaredMethods()) {
            if (methods.isAnnotationPresent(Action.class)) {
                methods.setAccessible(true);
                Action action = methods.getAnnotation(Action.class);
                System.out.println("Имя метода: "+methods.getName());
                System.out.println("Значение метода: "+action.description());

                methods.invoke(service);
            }
        }
    }
}
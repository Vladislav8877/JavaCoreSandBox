package s05_reflectionApi.t04_lastSubject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashSet;
import java.util.Set;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Component {
    String name() default "";
}

@Component(name = "UserService")
class UserService {
    public void execute() {
        System.out.println("User service is running");
    }
}

@Component(name = "OrderService")
class OrderService {
    public void execute() {
        System.out.println("Order service is running");
    }
}

class ComponentScanner {
    public static void main(String[] args) {
        String packageName = "lastSubject";
        Set<Class<?>> components = new HashSet<>();

        Class<?>[] classes = {UserService.class, OrderService.class};
        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(Component.class)) {
                components.add(clazz);
                Component annotation = clazz.getAnnotation(Component.class);
                System.out.println("Registered component: " + annotation.name());
            }
        }

        for (Class<?> component : components) {
            try {
                Object instance = component.getDeclaredConstructor().newInstance();
                component.getMethod("execute").invoke(instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
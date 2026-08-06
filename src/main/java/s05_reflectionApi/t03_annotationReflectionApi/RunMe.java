package s05_reflectionApi.t03_annotationReflectionApi;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RunMe {
}

class Robot {
    @RunMe
    public void sayHello() {
        System.out.println("Hello World");
    }

    public void cleanRoom() {
        System.out.println("Clean Room");
    }

    @RunMe
    public void sayGoodbye() {
        System.out.println("Goodbye World");
    }
}

class TestRunner {
    public static void main(String[] args) throws Exception {
        Robot robot = new Robot();
        for (Method methods : Robot.class.getDeclaredMethods()) {
            if (methods.isAnnotationPresent(RunMe.class)) {
                methods.setAccessible(true);
                methods.invoke(robot);
            }
        }
    }
}
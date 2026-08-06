package s05_reflectionApi.t03_annotationReflectionApi;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequiresRole {
    String role() default "GUEST";
}

class BankService {
    @RequiresRole(role = "ADMIN")
    public void deleteDateBase() {
        System.out.println("Delete date base");
    }

    @RequiresRole(role = "USER")
    public void transferMoney() {
        System.out.println("Transfer money");
    }

    @RequiresRole()
    public void showInfo() {
        System.out.println("Show info");
    }
}

class SecurityService {
    public static void main(String[] args) throws Exception {

        BankService bankService = new BankService();
        Class<?> clazz = bankService.getClass();
        String currentUserRole = "USER";

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(RequiresRole.class)) {
                method.setAccessible(true);
                RequiresRole annotation = method.getAnnotation(RequiresRole.class);

                if (annotation.role().equals(currentUserRole)) {
                    method.invoke(bankService);
                } else {
                    System.out.println("Отказ: у вас нет прав на метод: "+method.getName());
                }
            }
        }
    }
}
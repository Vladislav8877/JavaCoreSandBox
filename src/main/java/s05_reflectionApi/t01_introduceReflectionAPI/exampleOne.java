package s05_reflectionApi.t01_introduceReflectionAPI;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Scanner;

public class exampleOne {
    public static void main(String[] args) {
        String greeting = "Hello Reflection!";
        Class<?> class1 = greeting.getClass(); //используем когда объект уже есть в памяти
        //Class<String> class1 = String.class;
        System.out.println(class1.getName());
    }
}

class ExampleTwo {
    public static void main(String[] args) {
        Class<?> class1 = String.class;
        Class<?> class2 = int.class;
        System.out.println(class1.getSimpleName());

        //способ первый
        String myString = new String("Hello!"); //сначала тратим память на создание объекта
        Class<?> classMyString = myString.getClass(); //и только потом получаем его метаданные
        //способ второй без создания объекта!
        Class<?> classMyString2 = String.class;
        System.out.println(classMyString == classMyString2);
    }
}


class ExampleThree {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите полное название класса: ");
        String classNameFromUser = scanner.nextLine();

        try {
            Class<?> dynamicClass = Class.forName(classNameFromUser);
            System.out.println("Успешно: получаем класс - "+dynamicClass.getSimpleName());
            System.out.println("Это интерфейс?: "+dynamicClass.isInterface());
        } catch (ClassNotFoundException e) {
            System.err.println("Данного класса не существует!");
        }
    }
}

class ClassAnalyzer {
    public static void main(String[] args) {
        //берем стандартный ArrayList
        Class<?> targetClass = ArrayList.class;

        //1. Имена класса
        System.out.println("Короткое имя класса: " + targetClass.getSimpleName());
        System.out.println("Полное имя класса: " + targetClass.getName());

        //2. Модификаторы доступа
        //getModifiers возвращает число (int). Чтобы перевести его в текст используем класс Modifier
        int mods = targetClass.getModifiers();
        System.out.println("Модификаторы: " + Modifier.toString(mods));

        //3. Информация о наследовании
        System.out.println("Родительский класс: " + targetClass.getSuperclass().getSimpleName());

        //4. Проверка типа
        System.out.println("Это интерфейс?: " + targetClass.isInterface());
        System.out.println("Это массив?: "+targetClass.isArray());

        //5. Какие интерфейсы реализует
        System.out.println("Реализованные интерфейсы: ");
        Class<?>[] interfaces = targetClass.getInterfaces();
        for (Class<?> inter : interfaces) {
            System.out.println(inter.getSimpleName());
        }
    }
}

//----

class User {
    String name;
    int age;

    User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {return name;}
    public int getAge() {return age;}
}

class Reflection {
    public static void main(String[] args) {
        analyzerClass(User.class);

        User user = new User("Vlad",23);
        Class<?> clazzOne = user.getClass();
        analyzerClass(clazzOne);

        String className = "User";  //com.project.User
        try {
            Class<?> classFromString = Class.forName(className);
            analyzerClass(classFromString);
        } catch (ClassNotFoundException e) {
            System.out.println("Exception!");
        }
    }


    public static void analyzerClass(Class<?> clazz) {
        System.out.println("Имя класса: "+clazz.getName());

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            System.out.println("Поля: "+field.getName());
        }

        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println("Методы: "+method.getName());
        }
    }
}
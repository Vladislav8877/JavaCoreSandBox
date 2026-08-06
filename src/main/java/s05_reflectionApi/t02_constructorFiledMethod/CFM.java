package s05_reflectionApi.t02_constructorFiledMethod;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

//Вызов конструктора через newInstance(…)

class One {
    private final String message;


    public One(String message) {
        this.message = message;
    }
    private One() {
        this.message = "default";
    }

    String getMessage() {return message;}

}

class Two {
    public static void main(String[] args) {

        try {
            Constructor<?> constructor = One.class.getConstructor(String.class); //Конструктор который принимает String

            //Создаём объект
            One obj = (One) constructor.newInstance("Hello my friend!");
            System.out.println("Message: "+obj.getMessage());

            //Получаем приватный конструктор
            Constructor<?> privateConstructor = One.class.getDeclaredConstructor();
            privateConstructor.setAccessible(true); //Разрешаем доступ
            One objPrivate = (One) privateConstructor.newInstance(); //Привидение типов - каст
            System.out.println("Message (privateConstr.): "+objPrivate.getMessage());
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

//Обращение (чтение) и изменение полей

class Fields {
    public static void main(String[] args) {
        Field[] fields = One.class.getFields(); //публичные и поля родителя
        Field[] fieldsP = One.class.getDeclaredFields(); //приватные поля класса но без родителя

        Class<?> clazz = One.class;
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            System.out.println("Field name: "+field.getName());
        }
        //---

        try {
            One myClass = new One("He Hello Joj");
            Field field = One.class.getDeclaredField("message");
            field.setAccessible(true); //разрешаем доступ

            //читаем значение
            String oldValue = (String) field.get(myClass);
            System.out.println("Old value: "+oldValue);

            //меняем значение
            field.set(myClass,"Change value right now!");

            String newValue = (String) field.get(myClass);
            System.out.println("New value: "+newValue);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}

class Person {
   public String name;
    private final int age;
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

class Ref {
    public static void main(String[] args) {

        try {
            Person bob = new Person("Bob",30);

            Field field = Person.class.getDeclaredField("age");
            // ВЗЛОМ: Открываем доступ к приватному полю
            field.setAccessible(true);

            // ЧТЕНИЕ: Передаем конкретный объект (bob), из которого хотим достать возраст
            int currentAge = (int) field.get(bob);
            System.out.println("Current age: "+currentAge);

            field.set(bob,40);
            System.out.println("New age: "+field.get(bob));
            //---

            Field stringField = Person.class.getField("name");
            String currentName = (String) stringField.get(bob);
            System.out.println("Current name: "+currentName);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

//работа с методами

//Method method = Worker.class.getDeclaredMethod("doWork",String.class,int.class);

/*
Person charlie = new Person("Charlie");

// 1. Находим приватный метод (без параметров)
Method secretMethod = Person.class.getDeclaredMethod("secretThoughts");

// 2. ВЗЛОМ: Разрешаем его вызов
secretMethod.setAccessible(true);

// 3. Вызываем метод у объекта charlie. (Второй аргумент пустой, т.к. метод не принимает параметров)
secretMethod.invoke(charlie);
*/



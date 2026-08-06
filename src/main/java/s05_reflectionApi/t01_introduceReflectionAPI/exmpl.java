package s05_reflectionApi.t01_introduceReflectionAPI;

import java.lang.reflect.*;

class Pers {
    private String name;
    private int age;

    public Pers(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private void setName(String name) {
        this.name = name;
    }
    private void setAge(int age) {
        this.age = age;
    }

    public String getName() {return name;}
    public int getAge() {return age;}
}

class ReflectionA {
    public static void main(String[] args) {
        analyzer(Pers.class);
    }

    public static void analyzer(Class<?> clazz) {
        System.out.println("Имя класса: "+clazz.getSimpleName());

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

//----


class One {
    public int age;
    private final String message;

    public One(String message) {
        this.message = message;
    }
    private One() {
        this.message = "default message";
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
    private void printMessage() {
        System.out.println("Private message!");
    }
    private static void printMessage(int age) {
        System.out.println("My old is: "+age);
    }

    public String getMessage() {
        return message;
    }
}

class Two {
    public static void main(String[] args) {

        try {
            Constructor<?> constructor = One.class.getConstructor(String.class);
            One obj = (One) constructor.newInstance("Hello my people");
            System.out.println("Message: "+obj.getMessage());

            Constructor<?> privateConstr = One.class.getDeclaredConstructor();
            privateConstr.setAccessible(true);
            One privateObj = (One) privateConstr.newInstance();
            System.out.println("Message: "+privateObj.getMessage());
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

//----

class F {
    public static void main(String[] args) {
        Field[] fields = One.class.getFields();
        for (Field field : fields) {
            System.out.println("field: "+field.getName());
        }

        System.out.println("---");

        Field[] privateFields = One.class.getDeclaredFields();
        for (Field field : privateFields) {
            System.out.println("privateField: "+field.getName());
        }

        System.out.println("---");

        /*try { //нужно сделать поле message - public
            One myClass = new One("Hello BOB!");
            Field field = myClass.getClass().getField("message");

            String oldValueField = (String) field.get(myClass);
            System.out.println("oldValueField: "+oldValueField);

            field.set(myClass,"BOB, goodbye!");

            String newValueField = (String) field.get(myClass);
            System.out.println("newValueField: "+newValueField);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }*/

        try {
            One myClass = new One("Hello BOSS!");
            Field declaredF = myClass.getClass().getDeclaredField("message");
            declaredF.setAccessible(true);

            String oldValueField = (String) declaredF.get(myClass);
            System.out.println("oldValueField: "+oldValueField);

            declaredF.set(myClass,"BOSS, goodbye!");

            String newValueField = (String) declaredF.get(myClass);
            System.out.println("newValueField: "+newValueField);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

class M {
    public static void main(String[] args) {
        Method[] methods = One.class.getMethods();
        for (Method method : methods) {
            System.out.println("method: "+method.getName());
        }

        System.out.println("---");

        Method[] privateMethods = One.class.getDeclaredMethods();
        for (Method method : privateMethods) {
            System.out.println("privateMethod: "+method.getName());
        }

        System.out.println("---");

        try {
            One myClass = new One("Hello BOSS!");
            Method method = myClass.getClass().getMethod("printMessage",String.class);
            method.invoke(myClass,"!!!");
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        System.out.println("---");

        try {
            One myClass = new One("GoodBye BOSS!");
            Method method = myClass.getClass().getDeclaredMethod("printMessage");
            method.setAccessible(true);
            method.invoke(myClass);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        System.out.println("---");

        try {
            Method method = One.class.getDeclaredMethod("printMessage",int.class);
            method.setAccessible(true);
            method.invoke(null,10);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

//------------------------


class Person {
    String name;
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Name: "+name+", Age: "+age;
    }
}

class Reflections {
    public static void main(String[] args) {

        try {
            Class<?> clazz = Person.class;
            Constructor<?> constructor = clazz.getConstructor(String.class, int.class);

            Object person = constructor.newInstance("Vlad",23);
            System.out.println("Created person: "+person.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
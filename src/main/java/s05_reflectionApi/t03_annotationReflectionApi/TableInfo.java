package s05_reflectionApi.t03_annotationReflectionApi;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TableInfo {
    String tableName();
}

@TableInfo(tableName = "user_db_table") // Указываем, как таблица называется в БД
class User {
    String name = "Vlad";
    int age = 23;
}

class sqlGenerator {
    public static void main(String[] args) {
        Class<?> clazz = User.class;
        if (clazz.isAnnotationPresent(TableInfo.class)) {
            TableInfo info = clazz.getAnnotation(TableInfo.class);
            String table = info.tableName();

            System.out.println("Сгенерирован запрос: SELECT * FROM " + table + ";");
        } else {
            System.out.println("Это не таблица базы данных!");
        }
    }
}
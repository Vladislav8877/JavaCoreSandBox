package s01_exception.t02_exceptionGenimi;

import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static WarehouseManager manager = new WarehouseManager();

    private static void printMenu() {
        System.out.println("\nМЕНЮ:");
        System.out.println("(1) Принять товар на склад");
        System.out.println("(2) Продать товар");
        System.out.println("(3) Показать остатки");
        System.out.println("(0) Выход");
        System.out.print("Ваш выбор: ");
    }

    private static void addProductUI() {
        try {
            System.out.print("Введите название товара: ");
            String name = sc.nextLine();

            System.out.print("Введите серийный номер (должен начинаться с SN-): ");
            String serial = sc.nextLine();

            System.out.print("Введите цену: ");
            double price = Double.parseDouble(sc.nextLine());

            System.out.print("Введите количество: ");
            int stock = Integer.parseInt(sc.nextLine());

            Product newProduct = new Product(name,serial,price,stock);
            manager.addProduct(newProduct);
            System.out.println("Товар успешно добавлен!");
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: цена и количество должны быть числами!");
        } catch (InvalidSerialException e) {
            System.out.println("Ошибка серийного номера: "+e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка данных: "+e.getMessage());
        }
    }

    private static void sellProductUI() {
        try {
            System.out.print("Введите серийный номер товара: ");
            String serial = sc.nextLine();

            System.out.print("Какое количество на продажу: ");
            int amount = Integer.parseInt(sc.nextLine());

            manager.sellProduct(serial, amount);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введите целое число для количества!");
        } catch (ProductNotFoundException | InsufficientStuckException e) {
            System.out.println("Ошибка продажи: "+e.getMessage());
        }
    }

    private static void showAllProducts() {
        System.out.println("||||Товары На Складе||||");
        if (manager.getAllProducts().isEmpty()) {
            System.out.println("Склад пуст.");
        } else {
            for (Product p : manager.getAllProducts()) {
                System.out.println(p);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Warehouse Management System");

        while(true) {
            printMenu();
            String choice = sc.nextLine();

            try {
                switch(choice) {
                    case "1": addProductUI(); break;
                    case "2": sellProductUI(); break;
                    case "3": showAllProducts(); break;
                    case "0": System.exit(0); break;
                    default:
                        System.out.println("Ошибка: выберите пункт от (0) до (3)");
                }
            } catch (Exception e) {
                System.out.println("Непредвиденная ошибка: "+e.getMessage());
            }
        }
    }
}

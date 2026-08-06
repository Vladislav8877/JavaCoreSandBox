package s01_exception.t02_exceptionGenimi;

import java.util.ArrayList;
import java.util.List;

public class WarehouseManager {

    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        // метод принимает уже готовый созданный объект например в классе main
        // и просто хранит его в себе! Product принимает объект класса и хранит
        // его в переменной product через которую могу обратиться к методам класса
        products.add(product);
    }

    private Product findBySerial(String serial) { // private - прячем логику (не каждый может войти внутрь и глянуть товар)
        // тип возвращаемого значения Product - возвращает объект целиком
        // метод заходит в список/находит объект/дает адрес объекта
        for (Product p : products) {
            // для каждого объекта Product p из списка сделай следующие
            if(p.getSerialNumber().equalsIgnoreCase(serial)) {
                return p;
            }
        }
        throw new ProductNotFoundException("Товар с серийным номером: "+serial+" не найден на складе!");
    }

    public void sellProduct(String serial, int quantity) {
//        Product (Тип): Это форма «ячейки» или «парковки». Ты говоришь Java:
//        «Я сейчас создаю переменную, в которую можно положить только объект типа Product (ни число, ни строку, а именно товар)».
//        product (Имя переменной): Это просто название твоей «корзины» или «метки».
//        Ты можешь назвать её как угодно, например foundItem или item. Это ярлык, по которому ты будешь обращаться к результату.
        Product product = findBySerial(serial);

        if(product.getStock() < quantity) {
            throw new InsufficientStuckException(String.format("Недостаточно товара: '%s'. Запрошено: %d, в наличии: %d",
                    serial, quantity, product.getStock()));
        }
        product.setStock(product.getStock() - quantity);
        System.out.println("Продано: "+quantity+" шт. товара: "+product.getName());
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products); // возвращаем копию списка для безопасности (что бы не удалили список)
    }
}

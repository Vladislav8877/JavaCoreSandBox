package s01_exception.t02_exceptionGenimi;

public class Product {

    private String name;
    private String serialNumber;
    private double price;
    private int stock;

    public Product(String name, String serialNumber, double price, int stock) throws InvalidSerialException {
        setName(name);
        setSerialNumber(serialNumber);
        setPrice(price);
        setStock(stock);
    }

    public void setName(String name) {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("Название товара не может быть пустым!");
        }
        this.name = name;
    }
    public void setSerialNumber(String serialNumber) throws InvalidSerialException {
        if(serialNumber == null || !serialNumber.startsWith("SN-")) {
            throw new InvalidSerialException("Серийный номер должен начинаться с 'SN-'. Получено: "+serialNumber);
        }
        this.serialNumber = serialNumber;
    }
    public void setPrice(double price) {
        if(price < 0) {
            throw new IllegalArgumentException("Цена товара не может быть отрицательной!");
        }
        this.price = price;
    }
    public void setStock(int stock) {
        if(stock < 0) {
            throw new IllegalArgumentException("Количество товара на складе не может быть меньше 0!");
        }
        this.stock = stock;
    }

    public String getName() {return name;}
    public String getSerialNumber() {return serialNumber;}
    public double getPrice() {return price;}
    public int getStock() {return stock;}

    @Override
    public String toString() {
        return String.format("Товар: %s | SN: %s | Цена: %.3f | В наличии: %d |" , name, serialNumber, price, stock);
    }
}

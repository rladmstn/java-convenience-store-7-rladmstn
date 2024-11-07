package store.domain;

public abstract class Product {
    private final String name;
    private final int price;
    private int stock;

    Product(String name, int price, int stock){
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public void decreaseStock(int amount) {
        this.stock -= amount;
    }

    public String getName(){
        return name;
    }

    public int getPrice(){
        return price;
    }

    public int getStock(){
        return stock;
    }
}

package store.domain;

public abstract class PurchaseResult {
    private final String name;
    protected final int price;
    protected int totalCount;

    public PurchaseResult(String name, int price, int totalCount) {
        this.name = name;
        this.price = price;
        this.totalCount = totalCount;
    }

    public String getName() {
        return name;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getTotalAmount(){
        return totalCount * price;
    }
}

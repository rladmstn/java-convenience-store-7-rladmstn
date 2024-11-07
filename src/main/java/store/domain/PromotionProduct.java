package store.domain;

public class PromotionProduct extends Product{
    private final Promotion promotion;

    public PromotionProduct(String name, int price, int stock, Promotion promotion) {
        super(name, price, stock);
        this.promotion = promotion;
    }

    public Promotion getPromotion(){
        return promotion;
    }
}

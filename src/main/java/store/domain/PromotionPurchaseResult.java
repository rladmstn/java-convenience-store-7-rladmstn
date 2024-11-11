package store.domain;

public class PromotionPurchaseResult extends PurchaseResult {
    private int promotionAppliedCount;
    private int normalPurchaseCount;

    public PromotionPurchaseResult(String name, int price, int totalCount, int promotionAppliedCount,
                                   int normalPurchaseCount) {
        super(name, price, totalCount);
        this.promotionAppliedCount = promotionAppliedCount;
        this.normalPurchaseCount = normalPurchaseCount;
    }

    public int getPromotionAppliedCount() {
        return promotionAppliedCount;
    }

    public int getNormalPurchaseCount() {
        return normalPurchaseCount;
    }

    public int getPromotionAppliedAmount(){
        return promotionAppliedCount * price;
    }

    public int getNormalPurchaseAmount(){
        return normalPurchaseCount * price;
    }

    public void addPromotionProduct() {
        totalCount++;
        promotionAppliedCount++;
        normalPurchaseCount = 0;
    }

    public void removeNormalPurchase() {
        totalCount -= normalPurchaseCount;
        normalPurchaseCount = 0;
    }
}

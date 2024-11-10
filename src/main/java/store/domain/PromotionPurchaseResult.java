package store.domain;

public class PromotionPurchaseResult extends PurchaseResult {
    private int promotionAppliedCount;
    private int originalCount;

    public PromotionPurchaseResult(String name, int price, int totalCount, int promotionAppliedCount,
                                   int originalCount) {
        super(name, price, totalCount);
        this.promotionAppliedCount = promotionAppliedCount;
        this.originalCount = originalCount;
    }

    public int getPromotionAppliedCount() {
        return promotionAppliedCount;
    }

    public int getOriginalCount() {
        return originalCount;
    }

    public int getPromotionDiscountAmount(){
        return price * promotionAppliedCount;
    }

    public void addPromotionProduct(){
        totalCount ++;
        promotionAppliedCount ++;
        originalCount = 0;
    }

    public void removeOriginalPurchase(){
        totalCount -= originalCount;
        originalCount = 0;
    }
}

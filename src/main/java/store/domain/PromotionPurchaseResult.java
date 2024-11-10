package store.domain;

public class PromotionPurchaseResult extends PurchaseResult {
    private final int promotionAppliedCount;
    private final int originalCount;

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
}

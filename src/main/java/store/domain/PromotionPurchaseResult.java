package store.domain;

public class PromotionPurchaseResult extends PurchaseResult {
    private int promotionAppliedCount;
    private int originalCount;
    private int promotionAppliedAmount;

    public PromotionPurchaseResult(String name, int price, int totalCount, int promotionAppliedCount,
                                   int originalCount) {
        super(name, price, totalCount);
        this.promotionAppliedCount = promotionAppliedCount;
        this.originalCount = originalCount;
        this.promotionAppliedAmount = promotionAppliedCount * price;
    }

    public int getPromotionAppliedCount() {
        return promotionAppliedCount;
    }

    public int getOriginalCount() {
        return originalCount;
    }

    public int getPromotionAppliedAmount(){
        return promotionAppliedAmount;
    }

    public void addPromotionProduct(){
        totalCount ++;
        promotionAppliedCount ++;
        originalCount = 0;
        updateAmounts();
    }

    public void removeOriginalPurchase(){
        totalCount -= originalCount;
        originalCount = 0;
        updateAmounts();
    }

    private void updateAmounts(){
        totalAmount = totalCount * price;
        promotionAppliedAmount = promotionAppliedCount * price;
    }
}

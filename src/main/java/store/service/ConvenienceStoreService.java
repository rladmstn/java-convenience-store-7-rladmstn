package store.service;

import java.util.List;
import store.domain.Catalog;
import store.domain.Promotion;
import store.domain.PromotionProduct;
import store.domain.PromotionPurchaseResult;
import store.domain.PurchaseResult;

public class ConvenienceStoreService {
    private final Catalog catalog;
    private static final int MAX_MEMBERSHIP_DISCOUNT = 8000;
    private static final float MEMBERSHIP_DISCOUNT_PERCENT = 0.3F;

    public ConvenienceStoreService(Catalog catalog) {
        this.catalog = catalog;
    }

    public boolean isPromotionActive(String productName) {
        if (catalog.existPromotionProducts(productName)) {
            PromotionProduct product = catalog.getPromotionProducts(productName);
            return product.getPromotion().isPromotionInProgress();
        }
        return false;
    }

    public boolean isPromotionProductAddable(int originalCount, int purchaseCount, Promotion promotion, int stock) {
        int buyCount = promotion.buyCount();
        int giveCount = promotion.giveCount();
        return (originalCount == buyCount) && (stock >= giveCount + purchaseCount);
    }

    public boolean isOriginalPurchaseRequired(int originalCount, int promotionStock, int purchaseCount) {
        return originalCount > 0 && promotionStock <= purchaseCount;
    }

    public PromotionPurchaseResult initPromotionPurchaseResult(int purchaseCount, PromotionProduct promotionProduct) {
        Promotion promotion = promotionProduct.getPromotion();
        int promotionAppliedCount =
                Math.min(purchaseCount, promotionProduct.getStock()) / (promotion.giveCount() + promotion.buyCount());
        int originalCount =
                purchaseCount - promotionAppliedCount * (promotion.giveCount() + promotion.buyCount());
        return new PromotionPurchaseResult(
                promotionProduct.getName(), promotionProduct.getPrice(), purchaseCount,
                promotionAppliedCount, originalCount);
    }

    public int calculateMembershipDiscountAmount(List<PurchaseResult> purchaseResults) {
        int originalPriceTotalAmount = 0;
        for (PurchaseResult result : purchaseResults) {
            if (result instanceof PromotionPurchaseResult) {
                originalPriceTotalAmount += ((PromotionPurchaseResult) result).getOriginalAmount();
                continue;
            }
            originalPriceTotalAmount += result.getTotalAmount();
        }
        return (int) Math.min(MAX_MEMBERSHIP_DISCOUNT, originalPriceTotalAmount * MEMBERSHIP_DISCOUNT_PERCENT);
    }
}

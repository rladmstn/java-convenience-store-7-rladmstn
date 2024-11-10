package store.service;

import store.domain.Catalog;
import store.domain.Promotion;
import store.domain.PromotionProduct;
import store.domain.PromotionPurchaseResult;

public class ConvenienceStoreService {
    private final Catalog catalog;

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

}
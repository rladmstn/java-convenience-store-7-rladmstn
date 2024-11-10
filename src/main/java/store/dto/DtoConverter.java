package store.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import store.constants.CommonConstant;
import store.constants.ReceiptCategory;
import store.domain.Catalog;
import store.domain.NormalProduct;
import store.domain.Product;
import store.domain.PromotionProduct;
import store.domain.PromotionPurchaseResult;
import store.domain.PurchaseResult;
import store.util.NumberFormatUtil;

public final class DtoConverter {
    private static final String EMPTY_STRING = "";
    private static final String MINUS = "-";

    public static List<ProductResponse> toCatalogResponse(Catalog catalog) {
        List<ProductResponse> response = new ArrayList<>();
        Map<String, List<Product>> list = catalog.getCatalog();

        for (String name : list.keySet()) {
            for (Product product : list.get(name)) {
                response.add(generateProductResponse(product));
            }
        }
        return response;
    }

    public static List<PurchaseInputRequest> toPurchaseInputRequest(String input) {
        List<PurchaseInputRequest> requests = new ArrayList<>();
        String[] purchases = input.split(CommonConstant.DELIMITER.get());
        for (String purchase : purchases) {
            String[] tempInput = purchase.substring(1, purchase.length() - 1)
                    .split(CommonConstant.PURCHASE_DELIMITER.get());
            requests.add(new PurchaseInputRequest(tempInput[0], Integer.parseInt(tempInput[1])));
        }
        return requests;
    }

    public static ReceiptResponse toReceiptResponse(List<PurchaseResult> purchaseResults, int membershipDiscount) {
        int finalAmount = getFinalAmount(purchaseResults);
        int finalCount = getFinalCount(purchaseResults);
        int finalPromotionAmount = getFinalPromotionAmount(purchaseResults);
        List<PurchasedProductResponse> allPurchasedResponses = new ArrayList<>();
        List<PromotionProductResponse> promotionsResponses = new ArrayList<>();
        addResultToResponses(purchaseResults, allPurchasedResponses, promotionsResponses);
        TotalPaymentResponse totalPaymentResponse = getTotalPaymentResponse(finalCount, finalAmount);
        List<SummaryResponse> summaryResponse = getSummaryResponses(membershipDiscount, finalPromotionAmount,
                finalAmount);
        return new ReceiptResponse(allPurchasedResponses, promotionsResponses, totalPaymentResponse, summaryResponse);
    }

    private static TotalPaymentResponse getTotalPaymentResponse(int finalCount, int finalAmount) {
        return new TotalPaymentResponse(ReceiptCategory.TOTAL_PURCHASE_AMOUNT_CATEGORY.get(), finalCount, NumberFormatUtil.numberFormat(finalAmount));
    }

    private static int getFinalCount(List<PurchaseResult> results) {
        return results.stream().mapToInt(PurchaseResult::getTotalCount).sum();
    }

    private static int getFinalAmount(List<PurchaseResult> results) {
        return results.stream().mapToInt(PurchaseResult::getTotalAmount).sum();
    }

    private static int getFinalPromotionAmount(List<PurchaseResult> results) {
        return results.stream().filter(result -> result instanceof PromotionPurchaseResult)
                .mapToInt(result -> ((PromotionPurchaseResult) result).getPromotionAppliedAmount())
                .sum();
    }

    private static void addResultToResponses(List<PurchaseResult> purchaseResults,
                                             List<PurchasedProductResponse> purchasedProductResponses,
                                             List<PromotionProductResponse> promotionProductResponses) {
        for (PurchaseResult purchaseResult : purchaseResults) {
            purchasedProductResponses.add(
                    new PurchasedProductResponse(purchaseResult.getName(), purchaseResult.getTotalCount(),
                            NumberFormatUtil.numberFormat(purchaseResult.getTotalAmount())));
            if (purchaseResult instanceof PromotionPurchaseResult) {
                promotionProductResponses.add(new PromotionProductResponse(purchaseResult.getName(),
                        ((PromotionPurchaseResult) purchaseResult).getPromotionAppliedCount()));
            }
        }
    }

    private static List<SummaryResponse> getSummaryResponses(int membershipDiscount, int finalPromotionAmount,
                                                             int finalAmount) {
        SummaryResponse promotionResponse = new SummaryResponse(ReceiptCategory.PROMOTION_CATEGORY.get(),
                MINUS + NumberFormatUtil.numberFormat(finalPromotionAmount));
        SummaryResponse membershipResponse = new SummaryResponse(ReceiptCategory.MEMBERSHIP_CATEGORY.get(),
                MINUS + NumberFormatUtil.numberFormat(membershipDiscount));
        SummaryResponse finalPaymentResponse = new SummaryResponse(ReceiptCategory.FINAL_PAYMENT_CATEGORY.get(),
                NumberFormatUtil.numberFormat(finalAmount - finalPromotionAmount - membershipDiscount));

        return List.of(promotionResponse, membershipResponse, finalPaymentResponse);
    }

    private static ProductResponse generateProductResponse(Product product) {
        if (product instanceof NormalProduct) {
            return new ProductResponse(product.getName(), product.getPrice(), product.getStockToString(), EMPTY_STRING);
        }
        PromotionProduct promotionProduct = (PromotionProduct) product;
        return new ProductResponse(
                promotionProduct.getName(),
                promotionProduct.getPrice(),
                promotionProduct.getStockToString(),
                promotionProduct.getPromotionName());
    }
}

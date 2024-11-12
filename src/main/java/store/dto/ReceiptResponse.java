package store.dto;

import java.util.List;

public record ReceiptResponse(List<PurchasedProductResponse> purchasedProduct,
                              List<PromotionProductResponse> promotionProduct,
                              TotalPaymentResponse totalPayment,
                              List<SummaryResponse> summary) {
}

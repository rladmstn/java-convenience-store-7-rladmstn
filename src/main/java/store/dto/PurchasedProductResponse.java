package store.dto;

public record PurchasedProductResponse(String name,
                                       int count,
                                       int payment) {
}

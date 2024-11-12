package store.dto;

public record TotalPaymentResponse(String category,
                                   int count,
                                   String totalPayment) {
}

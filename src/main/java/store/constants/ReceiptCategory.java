package store.constants;

public enum ReceiptCategory {
    TOTAL_PURCHASE_AMOUNT_CATEGORY("총구매액"),
    PROMOTION_CATEGORY("행사할인"),
    MEMBERSHIP_CATEGORY("멤버십할인"),
    FINAL_PAYMENT_CATEGORY("내실돈");

    private final String value;
    ReceiptCategory(String value) {
        this.value = value;
    }

    public String get(){
        return this.value;
    }
}

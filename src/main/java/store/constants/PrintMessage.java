package store.constants;

public enum PrintMessage {
    // input
    INPUT_PRODUCT_SELECTION("\n구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"),
    INPUT_PROMOTION_NOT_APPLIED("\n현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"),
    INPUT_ADDITIONAL_PROMOTION("\n현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)"),
    INPUT_MEMBERSHIP_DISCOUNT("\n멤버십 할인을 받으시겠습니까? (Y/N)"),
    INPUT_ADDITIONAL_PURCHASE("\n감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)"),

    // output
    PRINT_START("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n"),
    PRINT_PRODUCT("- %s %,d원 %s %s"),

    PRINT_RECEIPT_START("==============W 편의점============"),
    PRINT_CATEGORY("상품명\t\t수량\t금액"),
    PRINT_PURCHASE_PRODUCT("%-10s %-2d %,d"),
    PRINT_RECEIPT_PROMOTION("=============증\t정==============="),
    PRINT_PROMOTION_PRODUCT("%-10s %-2d"),
    PRINT_RECEIPT_END("=================================="),
    PRINT_TOTAL("총구매액\t\t%-2d\t%,d"),
    PRINT_SUMMARY("%-10s %s");

    private final String message;

    PrintMessage(String message) {
        this.message = message;
    }

    public String get() {
        return message;
    }

    public String format(Object... args) {
        return message.formatted(args);
    }
}

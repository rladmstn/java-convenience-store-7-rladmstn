package store.constants;

public enum ErrorMessage {
    PREFIX("[ERROR] "),
    INCORRECT_PURCHASE_FORM("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    NOT_EXIST_PRODUCT("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    OVER_STOCK("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    MIN_PURCHASE_COUNT("최소 구매 수량은 한 개 이상입니다. 다시 입력해 주세요."),
    INVALID_INPUT("잘못된 입력입니다. 다시 입력해 주세요."),

    FAILED_TO_READ_FILE("파일을 읽어오는데 실패했습니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return PREFIX.message + message;
    }
}

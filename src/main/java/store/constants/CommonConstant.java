package store.constants;

public enum CommonConstant {
    DELIMITER(","),
    PURCHASE_DELIMITER("-");

    private final String value;

    CommonConstant(String value) {
        this.value = value;
    }

    public String get(){
        return value;
    }
}

package store.dto;

public record ProductResponse(String name,
                              int price,
                              String stock,
                              String promotion) {
}

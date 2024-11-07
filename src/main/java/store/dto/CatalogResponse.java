package store.dto;

public record CatalogResponse(String name,
                              int price,
                              String stock,
                              String promotion) {
}

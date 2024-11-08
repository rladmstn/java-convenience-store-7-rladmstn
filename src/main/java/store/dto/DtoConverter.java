package store.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import store.constants.CommonConstant;
import store.domain.Catalog;
import store.domain.NormalProduct;
import store.domain.Product;
import store.domain.PromotionProduct;

public final class DtoConverter {
    private static final String EMPTY_STRING = "";

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

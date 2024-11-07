package store.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import store.domain.Catalog;
import store.domain.NormalProduct;
import store.domain.Product;
import store.domain.PromotionProduct;

public final class DtoConverter {
    private static final String EMPTY_STRING = "";

    public static List<ProductResponse> toCatalogResponse(Catalog catalog) {
        List<ProductResponse> response = new ArrayList<>();
        Map<String, List<Product>> list = catalog.getCatalog();

        for(String name : list.keySet()){
            for(Product product : list.get(name)){
                response.add(generateProductResponse(product));
            }
        }
        return response;
    }

    private static ProductResponse generateProductResponse(Product product) {
        if(product instanceof NormalProduct){
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

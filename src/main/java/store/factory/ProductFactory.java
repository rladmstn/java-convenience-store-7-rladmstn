package store.factory;

import java.util.List;
import store.domain.NormalProduct;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.PromotionProduct;

public class ProductFactory {
    private static final String NULL = "null";

    public Product createProduct(String[] input, List<Promotion> promotions) {
        String name = input[0];
        int price = Integer.parseInt(input[1]);
        int stock = Integer.parseInt(input[2]);
        String promotionName = input[3];

        if(promotionName.equals(NULL)){
            return new NormalProduct(name, price, stock);
        }
        Promotion promotion = Promotion.getByPromotionName(promotions, promotionName);
        return new PromotionProduct(name, price, stock, promotion);
    }

    public Product createNormalProduct(String name, int price){
        return new NormalProduct(name, price, 0);
    }
}

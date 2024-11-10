package store.config;

import java.util.ArrayList;
import java.util.List;
import store.constants.FilePath;
import store.domain.Catalog;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.PromotionProduct;
import store.factory.ProductFactory;
import store.factory.PromotionFactory;
import store.util.Reader;

public class ConvenienceStoreInitializer {
    private final String DELIMITER = ",";
    private final ProductFactory productFactory = new ProductFactory();
    private final PromotionFactory promotionFactory = new PromotionFactory();
    private final List<Promotion> promotions;
    private final Catalog catalog;

    public ConvenienceStoreInitializer() {
        promotions = initializePromotions();
        catalog = initializeCatalog();
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    private List<Promotion> initializePromotions(){
        List<String> content = Reader.readFile(FilePath.PROMOTIONS);
        List<Promotion> promotions = new ArrayList<>();

        for(String line : content){
            Promotion promotion = promotionFactory.createPromotion(line.split(DELIMITER));
            promotions.add(promotion);
        }
        return promotions;
    }

    private Catalog initializeCatalog(){
        List<String> content = Reader.readFile(FilePath.PRODUCTS);
        Catalog catalog = new Catalog();

        for(String line : content){
            Product product = productFactory.createProduct(line.split(DELIMITER), promotions);
            catalog.addProduct(product);
        }
        addMissingNormalProduct(catalog);
        return catalog;
    }

    private void addMissingNormalProduct(Catalog catalog) {
        for(String productName : catalog.getCatalog().keySet()){
            List<Product> products = catalog.getProducts(productName);
            if(products.size() == 1 && products.getFirst() instanceof PromotionProduct){
                products.add(productFactory.createNormalProduct(productName, products.getFirst().getPrice()));
            }
        }
    }
}

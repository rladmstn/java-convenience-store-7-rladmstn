package store;

import java.util.List;
import store.controller.ConvenienceStoreController;
import store.domain.Catalog;
import store.domain.Promotion;
import store.config.ConvenienceStoreInitializer;

public class Application {
    public static void main(String[] args) {
        ConvenienceStoreInitializer initializer = new ConvenienceStoreInitializer();
        Catalog catalog = initializer.getCatalog();
        List<Promotion> promotions = initializer.getPromotions();

        ConvenienceStoreController controller = new ConvenienceStoreController(promotions, catalog);
        controller.run();
    }
}

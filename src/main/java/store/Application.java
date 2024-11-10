package store;

import store.controller.ConvenienceStoreController;
import store.domain.Catalog;
import store.config.ConvenienceStoreInitializer;

public class Application {
    public static void main(String[] args) {
        ConvenienceStoreInitializer initializer = new ConvenienceStoreInitializer();
        Catalog catalog = initializer.getCatalog();

        ConvenienceStoreController controller = new ConvenienceStoreController(catalog);
        controller.run();
    }
}

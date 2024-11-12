package store;

import java.io.IOException;
import store.constants.ErrorMessage;
import store.controller.ConvenienceStoreController;
import store.domain.Catalog;
import store.config.ConvenienceStoreInitializer;

public class Application {
    public static void main(String[] args) {
        try {
            ConvenienceStoreInitializer initializer = new ConvenienceStoreInitializer();
            Catalog catalog = initializer.getCatalog();

            ConvenienceStoreController controller = new ConvenienceStoreController(catalog);
            controller.run();
        }catch (IOException e) {
            System.out.println(ErrorMessage.FAILED_TO_READ_FILE.getMessage());
        }
    }
}

package store.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Catalog {
    private final Map<String, List<Product>> catalog = new LinkedHashMap<>();

    public void addProduct(Product product){
        String name = product.getName();

        List<Product> products = catalog.getOrDefault(name, new ArrayList<>());
        products.add(product);
        catalog.put(name, products);
    }

    public Map<String, List<Product>> getCatalog() {
        return catalog;
    }
}

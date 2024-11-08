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

    public boolean hasProduct(String name){
        return catalog.containsKey(name);
    }

    public boolean hasSufficientStock(String name, int quantity){
        List<Product> products = catalog.get(name);
        int totalStock = products.stream().mapToInt(Product::getStock).sum();
        return totalStock >= quantity;
    }
}

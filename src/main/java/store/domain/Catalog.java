package store.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Catalog {
    private final Map<String, List<Product>> catalog = new LinkedHashMap<>();

    public void addProduct(Product product) {
        String name = product.getName();

        List<Product> products = catalog.getOrDefault(name, new ArrayList<>());
        products.add(product);
        catalog.put(name, products);
    }

    public Map<String, List<Product>> getCatalog() {
        return catalog;
    }

    public boolean existProducts(String name) {
        return catalog.containsKey(name);
    }

    public boolean hasSufficientStock(String name, int purchaseCount) {
        List<Product> products = catalog.get(name);
        int totalStock = products.stream().mapToInt(Product::getStock).sum();
        return totalStock >= purchaseCount;
    }

    public PromotionProduct getPromotionProducts(String name) {
        List<Product> products = catalog.get(name);
        for (Product product : products) {
            if (product instanceof PromotionProduct) {
                return (PromotionProduct) product;
            }
        }
        return null;
    }

    public NormalProduct getNormalProducts(String name) {
        List<Product> products = catalog.get(name);
        for (Product product : products) {
            if (product instanceof NormalProduct) {
                return (NormalProduct) product;
            }
        }
        return null;
    }

    public Product getAlternativeProduct(Product product) {
        if (product instanceof PromotionProduct) {
            return getNormalProducts(product.getName());
        }
        return getPromotionProducts(product.getName());
    }

    public List<Product> getProducts(String name) {
        return catalog.get(name);
    }

    public boolean existPromotionProducts(String name) {
        List<Product> products = catalog.get(name);
        return !products.stream()
                .filter(product -> product instanceof PromotionProduct)
                .toList()
                .isEmpty();
    }

    public void decreaseStock(Product product, int totalCount) {
        int productStock = product.getStock();
        product.decreaseStock(Math.min(totalCount, productStock));
        if (totalCount > productStock) {
            Product alternativeProduct = getAlternativeProduct(product);
            alternativeProduct.decreaseStock(totalCount - productStock);
        }
    }
}

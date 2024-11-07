package store.factory;

import java.time.LocalDate;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.NormalProduct;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.PromotionProduct;

class ProductFactoryTest {
    static ProductFactory productFactory;
    static List<Promotion> promotions;

    @BeforeAll
    static void setUp(){
        productFactory = new ProductFactory();
        promotions = List.of(new Promotion("MD추천상품", 2, 1, LocalDate.now(), LocalDate.now().plusDays(30)));
    }

    @Test
    @DisplayName("일반 상품 Product 객체 생성 성공 (NormalProduct)")
    void createNormalProduct() {
        // given
        String[] split = "감자칩,1500,5,null".split(",");
        // when
        Product product = productFactory.createProduct(split, promotions);
        // then
        Assertions.assertThat(product).isInstanceOf(NormalProduct.class);
    }

    @Test
    @DisplayName("행사 상품 Product 객체 생성 성공 (PromotionProduct)")
    void createPromotionProduct() {
        // given
        String[] split = "초코바,1200,5,MD추천상품".split(",");
        // when
        Product product = productFactory.createProduct(split, promotions);
        // then
        Assertions.assertThat(product).isInstanceOf(PromotionProduct.class);
    }

}
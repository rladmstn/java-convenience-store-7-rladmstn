package store.config;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Catalog;
import store.domain.Promotion;

class ConvenienceStoreInitializerTest {
    static ConvenienceStoreInitializer initializer;
    final int PROMOTION_COUNT = 3;
    final int CATALOG_COUNT = 11;

    @BeforeAll
    static void setUp(){
        initializer = new ConvenienceStoreInitializer();
    }

    @Test
    @DisplayName("프로모션 객체 리스트 생성 성공")
    void getPromotions() {
        // when
        List<Promotion> promotions = initializer.getPromotions();
        // then
        Assertions.assertThat(promotions.size()).isEqualTo(PROMOTION_COUNT);
    }

    @Test
    @DisplayName("물품 목록 카탈로그 생성 성공")
    void getCatalog() {
        // when
        Catalog catalog = initializer.getCatalog();
        // then
        Assertions.assertThat(catalog.getCatalog().size()).isEqualTo(CATALOG_COUNT);
    }
}
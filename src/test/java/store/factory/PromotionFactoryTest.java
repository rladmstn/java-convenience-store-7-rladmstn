package store.factory;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Promotion;

class PromotionFactoryTest {
    static PromotionFactory promotionFactory;

    @BeforeAll
    static void setUp() {
        promotionFactory = new PromotionFactory();
    }

    @Test
    @DisplayName("행사 객체 생성 (Promotion)")
    void createPromotion() {
        // given
        String[] input = "반짝할인,1,1,2024-11-01,2024-11-30".split(",");
        // when
        Promotion promotion = promotionFactory.createPromotion(input);
        // then
        Assertions.assertThat(promotion).isInstanceOf(Promotion.class);
    }
}
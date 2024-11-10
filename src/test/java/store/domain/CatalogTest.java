package store.domain;

import static org.assertj.core.api.Assertions.*;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class CatalogTest {
    @Nested
    class staticProductTest {
        static Catalog catalog;
        static PromotionProduct promotionProduct;
        static NormalProduct normalProduct;
        static Promotion promotion;

        @BeforeAll
        static void setUp() {
            catalog = new Catalog();
            promotion = new Promotion("promotion", 2, 1,
                    DateTimes.now().toLocalDate(), DateTimes.now().toLocalDate().plusDays(30));
            promotionProduct = new PromotionProduct("product", 1000, 10, promotion);
            normalProduct = new NormalProduct("product", 1000, 10);
            catalog.addProduct(promotionProduct);
            catalog.addProduct(normalProduct);
            catalog.addProduct(new NormalProduct("normalProduct", 1500, 3));
        }

        @ParameterizedTest
        @CsvSource(value = {
                "product, true",
                "none, false"
        }, delimiter = ',')
        @DisplayName("물품 목록에 해당 이름의 물품이 존재하는지 확인")
        void existProducts(String productName, boolean expected) {
            // when
            boolean result = catalog.existProducts(productName);
            // then
            assertThat(result).isEqualTo(expected);
        }

        @ParameterizedTest
        @CsvSource(value = {
                "15, true",
                "30, false"
        })
        @DisplayName("구매할 수 있을만큼 충분한 재고가 있는지 확인")
        void hasSufficientStock(int purchaseCount, boolean expected) {
            // when
            boolean result = catalog.hasSufficientStock("product", purchaseCount);
            // then
            assertThat(result).isEqualTo(expected);
        }

        @ParameterizedTest
        @MethodSource("getAlternativeProductParameters")
        @DisplayName("해당 물품 재고가 다 떨어졌을 경우, 차감할 대체 물품 객체 리턴")
        void getAlternativeProduct(Product product, Product expected) {
            // when
            Product result = catalog.getAlternativeProduct(product);
            // then
            assertThat(result).isEqualTo(expected);
        }

        @ParameterizedTest
        @CsvSource(value = {
                "product, true",
                "normalProduct, false"
        }, delimiter = ',')
        @DisplayName("프로모션을 진행하는 상품이 존재하는지 확인")
        void existPromotionProducts(String productName, boolean expected) {
            // when
            boolean result = catalog.existPromotionProducts(productName);
            // then
            assertThat(result).isEqualTo(expected);
        }

        private static Stream<Arguments> getAlternativeProductParameters() {
            return Stream.of(
                    Arguments.of(promotionProduct, normalProduct),
                    Arguments.of(normalProduct, promotionProduct)
            );
        }
    }

    @Nested
    class nonStaticProductTest {
        static Promotion promotion = new Promotion("promotion", 2, 1,
                DateTimes.now().toLocalDate(), DateTimes.now().toLocalDate().plusDays(30));
        static final String NORMAL_PRODUCT = "normal";
        static final String PROMOTION_PRODUCT = "promotion";

        @ParameterizedTest
        @MethodSource("getDecreaseProductParameters")
        @DisplayName("재고 차감")
        void decreaseNormalStock(String category, PromotionProduct promotionProduct, NormalProduct normalProduct,
                                 int purchaseCount, int promotionExpected, int normalExpected) {
            // given
            Catalog catalog = new Catalog();
            catalog.addProduct(promotionProduct);
            catalog.addProduct(normalProduct);
            Product target = normalProduct;
            if (category.equals(PROMOTION_PRODUCT)) {
                target = promotionProduct;
            }
            // when
            catalog.decreaseStock(target, purchaseCount);
            // then
            assertThat(promotionProduct.getStock()).isEqualTo(promotionExpected);
            assertThat(normalProduct.getStock()).isEqualTo(normalExpected);
        }

        private static Stream<Arguments> getDecreaseProductParameters() {
            return Stream.of(
                    Arguments.of(NORMAL_PRODUCT, new PromotionProduct("product", 1000, 10, promotion),
                            new NormalProduct("product", 1000, 10), 10, 10, 0),
                    Arguments.of(NORMAL_PRODUCT, new PromotionProduct("product", 1000, 10, promotion),
                            new NormalProduct("product", 1000, 10), 15, 5, 0),
                    Arguments.of(PROMOTION_PRODUCT, new PromotionProduct("product", 1000, 10, promotion),
                            new NormalProduct("product", 1000, 10), 10, 0, 10),
                    Arguments.of(PROMOTION_PRODUCT, new PromotionProduct("product", 1000, 10, promotion),
                            new NormalProduct("product", 1000, 10), 15, 0, 5)
            );
        }
    }
}
package store.service;

import static org.assertj.core.api.Assertions.*;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import store.domain.Catalog;
import store.domain.NormalProduct;
import store.domain.Promotion;
import store.domain.PromotionProduct;
import store.domain.PromotionPurchaseResult;

class ConvenienceStoreServiceTest {
    static Catalog catalog;
    static PromotionProduct promotion1Product, promotion2Product, promotion3Product, promotion4Product;
    static NormalProduct normalProduct;
    static Promotion promotion1, promotion2, promotion3;
    ConvenienceStoreService service = new ConvenienceStoreService(catalog);

    @BeforeAll
    static void setUp() {
        catalog = new Catalog();
        promotion1 = new Promotion("promotion1", 1, 1,
                DateTimes.now().toLocalDate(), DateTimes.now().toLocalDate().plusDays(30));
        promotion2 = new Promotion("promotion2", 2, 1,
                DateTimes.now().toLocalDate().minusDays(30), DateTimes.now().toLocalDate().minusDays(3));
        promotion3 = new Promotion("promotion3", 2, 1,
                DateTimes.now().toLocalDate(), DateTimes.now().toLocalDate().plusDays(30));
        setProducts();
    }

    private static void setProducts() {
        promotion1Product = new PromotionProduct("promotion1Product", 1000, 10, promotion1);
        normalProduct = new NormalProduct("promotion1Product", 1000, 10);
        catalog.addProduct(promotion1Product);
        catalog.addProduct(normalProduct);
        catalog.addProduct(new NormalProduct("normalProduct", 1500, 3));
        promotion2Product = new PromotionProduct("promotion2Product", 2000, 10, promotion2);
        catalog.addProduct(promotion2Product);
        promotion3Product = new PromotionProduct("promotion3Product", 2000, 10, promotion3);
        catalog.addProduct(promotion2Product);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "promotion1Product, true", // 프로모션 진행 중
            "promotion2Product, false", // 프로모션 상품 존재, 진행은 안하는 중
            "normalProduct, false" // 프로모션 상품 없음
    }, delimiter = ',')
    @DisplayName("프로모션 진행 중인 상품인지 여부 판단")
    void isPromotionActive(String productName, boolean expected) {
        // when
        boolean result = service.isPromotionActive(productName);
        // then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2, 5, 10, true", // 추가 증정 가능
            "1, 3, 10, false", // 추가 증정 실패 : 구매 개수 부족
            "2, 5, 5, false" // 추가 증정 실패 : 재고 부족
    }, delimiter = ',')
    @DisplayName("프로모션 추가 증정이 가능한지 여부 판단")
    void isPromotionProductAddable(int originalCount, int purchaseCount, int stock, boolean expected) {
        // when
        boolean result = service.isPromotionProductAddable(originalCount, purchaseCount, promotion2,
                stock);
        // then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2, 5, 5, true", // 2+1 프로모션, 원금 구매 필요
            "1, 5, 5, true", // 1+1 프로모션, 원금 구매 필요
            "2, 5, 6, true", // 2+1 프로모션, 원금 구매 필요
            "0, 4, 4, false", // 1+1 프로모션, 원금 구매 필요 없음
    }, delimiter = ',')
    @DisplayName("원금 구매가 필요한지 여부 판단")
    void isOriginalPurchaseRequired(int originalCount, int stock, int purchaseCount, boolean expected) {
        // when
        boolean result = service.isOriginalPurchaseRequired(originalCount, stock, purchaseCount);
        // then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("getTestParameters")
    @DisplayName("프로모션 구매 결과 초기화")
    void initPromotionPurchaseResult(int purchaseCount, int originalCount, int totalCount, int promotionAppliedCount, PromotionProduct product) {
        // when
        PromotionPurchaseResult result = service.initPromotionPurchaseResult(purchaseCount, product);
        // then
        assertThat(result.getOriginalCount()).isEqualTo(originalCount);
        assertThat(result.getPromotionAppliedCount()).isEqualTo(promotionAppliedCount);
        assertThat(result.getTotalCount()).isEqualTo(totalCount);
    }

    private static Stream<Arguments> getTestParameters(){
        return Stream.of(
                // 구매 개수, 원가 구매 개수, 전체 구매 개수, 프로모션 적용 개수, 프로모션 상품
                Arguments.of(12, 3, 12, 3, promotion3Product),
                Arguments.of(5, 1, 5, 2, promotion1Product),
                Arguments.of(3, 1, 3, 1, promotion1Product),
                Arguments.of(1, 1, 1, 0, promotion3Product)
        );
    }
}
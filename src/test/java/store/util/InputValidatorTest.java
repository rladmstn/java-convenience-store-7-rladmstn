package store.util;

import camp.nextstep.edu.missionutils.DateTimes;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.constants.ErrorMessage;
import store.domain.Catalog;
import store.domain.NormalProduct;
import store.domain.Promotion;
import store.domain.PromotionProduct;

class InputValidatorTest {
    static Catalog catalog;

    @BeforeAll
    static void setUp(){
        Promotion promotion = new Promotion(
                "반짝할인", 2, 1,
                DateTimes.now().toLocalDate(),
                DateTimes.now().toLocalDate().plusDays(30));
        catalog = new Catalog();
        catalog.addProduct(new NormalProduct("콜라",1000,10));
        catalog.addProduct(new PromotionProduct("콜라",1000,10,promotion));
        catalog.addProduct(new NormalProduct("사이다",1500,5));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "[콜라-3][오렌지-4]",
            "[abc-1000000]",
            "[우리집-30,포도:300]",
            "사이다-20],[젤리-1234567890]",
            "[밥-30],"
    })
    @DisplayName("올바른 형식 검증 실패")
    void validatePurchaseForm(String input) {
        Assertions.assertThatThrownBy(() -> InputValidator.validatePurchaseSelection(input,catalog))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INCORRECT_PURCHASE_FORM.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "[콜라-3],[오렌지-2]",
            "[젤리-10]"
    })
    @DisplayName("존재하는 상품 검증 실패")
    void validateProductExists(String input){
        Assertions.assertThatThrownBy(() -> InputValidator.validatePurchaseSelection(input, catalog))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NOT_EXIST_PRODUCT.getMessage());
    }

    @Test
    @DisplayName("재고 이하의 구매 개수 검증 실패")
    void validateSufficientStock(){
        String input = "[콜라-21]";
        Assertions.assertThatThrownBy(() -> InputValidator.validatePurchaseSelection(input,catalog))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.OVER_STOCK.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"[콜라-20]","[콜라-10],[사이다-2]"})
    @DisplayName("정상 구매 검증 성공")
    void validatePurchaseSelection(String input){
        InputValidator.validatePurchaseSelection(input, catalog);
    }
}
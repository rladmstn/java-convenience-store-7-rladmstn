package store.dto;

import static org.assertj.core.api.Assertions.*;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Catalog;
import store.domain.NormalProduct;
import store.domain.NormalPurchaseResult;
import store.domain.Promotion;
import store.domain.PromotionProduct;
import store.domain.PromotionPurchaseResult;
import store.domain.PurchaseResult;
import store.service.ConvenienceStoreService;

class DtoConverterTest {
    @Test
    @DisplayName("구매 관련 정보를 DTO로 변환 성공")
    void toPurchaseInputRequest() {
        // given
        String input = "[콜라-3],[사이다-10]";
        // when
        List<PurchaseInputRequest> requests = DtoConverter.toPurchaseInputRequest(input);
        // then
        assertThat(requests.size()).isEqualTo(2);
        assertThat(requests.getFirst().productName()).isEqualTo("콜라");
        assertThat(requests.getFirst().count()).isEqualTo(3);
    }

    @Test
    @DisplayName("물품 목록 정보를 DTO로 변환 성공")
    void toCatalogResponse(){
        // given
        Catalog catalog = new Catalog();
        catalog.addProduct(new NormalProduct("promotion1Product", 1000, 10));
        catalog.addProduct(new NormalProduct("normalProduct", 1500, 3));
        // when
        List<ProductResponse> responses = DtoConverter.toCatalogResponse(catalog);
        // then
        assertThat(responses.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("구매 결과를 영수증 DTO로 변환 성공")
    void toReceiptResponse(){
        // given
        List<PurchaseResult> results = new ArrayList<>();
        // 2+1 프로모션 구매
        results.add(new PromotionPurchaseResult("product1",1000,3,1,0));
        // 일반 구매
        results.add(new NormalPurchaseResult("product2",2000,3));
        // when
        ReceiptResponse receiptResponse = DtoConverter.toReceiptResponse(results, (int)(6000*0.3));
        // then
        assertThat(receiptResponse.purchasedProduct().size()).isEqualTo(2);
        assertThat(receiptResponse.promotionProduct().getFirst().count()).isEqualTo(1);
        assertThat(receiptResponse.summary().size()).isEqualTo(3);
    }
}
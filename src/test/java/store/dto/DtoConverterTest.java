package store.dto;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DtoConverterTest {
    @Test
    @DisplayName("구매 관련 정보를 DTO로 변환 성공")
    void toPurchaseInputRequest() {
        // given
        String input = "[콜라-3],[사이다-10]";
        // when
        List<PurchaseInputRequest> requests = DtoConverter.toPurchaseInputRequest(input);
        // then
        Assertions.assertThat(requests.size()).isEqualTo(2);
        Assertions.assertThat(requests.getFirst().productName()).isEqualTo("콜라");
        Assertions.assertThat(requests.getFirst().count()).isEqualTo(3);
    }
}
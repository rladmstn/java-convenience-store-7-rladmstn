package store;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static camp.nextstep.edu.missionutils.test.Assertions.assertNowTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

class ApplicationTest extends NsTest {
    @Test
    void 파일에_있는_상품_목록_출력() {
        assertSimpleTest(() -> {
            run("[물-1]", "N", "N");
            assertThat(output()).contains(
                    "- 콜라 1,000원 10개 탄산2+1",
                    "- 콜라 1,000원 10개",
                    "- 사이다 1,000원 8개 탄산2+1",
                    "- 사이다 1,000원 7개",
                    "- 오렌지주스 1,800원 9개 MD추천상품",
                    "- 오렌지주스 1,800원 재고 없음",
                    "- 탄산수 1,200원 5개 탄산2+1",
                    "- 탄산수 1,200원 재고 없음",
                    "- 물 500원 10개",
                    "- 비타민워터 1,500원 6개",
                    "- 감자칩 1,500원 5개 반짝할인",
                    "- 감자칩 1,500원 5개",
                    "- 초코바 1,200원 5개 MD추천상품",
                    "- 초코바 1,200원 5개",
                    "- 에너지바 2,000원 5개",
                    "- 정식도시락 6,400원 8개",
                    "- 컵라면 1,700원 1개 MD추천상품",
                    "- 컵라면 1,700원 10개"
            );
        });
    }

    @Test
    void 여러_개의_일반_상품_구매() {
        assertSimpleTest(() -> {
            run("[비타민워터-3],[물-2],[정식도시락-2]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈18,300");
        });
    }

    @Test
    void 여러_개의_프로모션_상품_구매() {
        assertSimpleTest(() -> {
            run("[콜라-5]", "Y", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈4,000");
        });
    }

    @Test
    void 기간에_해당하지_않는_프로모션_적용() {
        assertNowTest(() -> {
            run("[감자칩-2]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈3,000");
        }, LocalDate.of(2024, 2, 1).atStartOfDay());
    }

    @Test
    void 멤버십_할인_적용() {
        assertSimpleTest(() -> {
            run("[물-2]", "Y", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈700");
        });
    }

    @Test
    void 원가_구매가_필요한_경우_원가_구매() {
        assertSimpleTest(() -> {
            run("[콜라-12]", "Y", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈9,000");
        });
    }

    @Test
    void 원가_구매가_필요한_경우_원가_구매_안함() {
        assertSimpleTest(() -> {
            run("[콜라-12]", "N", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈6,000");
        });
    }

    @ParameterizedTest
    @CsvSource(value = {
            "[콜라-12]/A/N/[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.",
            "[컵라면-12]/N/N/[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.",
            "[abc123]/Y/Y/[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.",
            "[청포도에이드-3]/Y/Y/[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요."
    }, delimiter = '/')
    void 예외_테스트(String purchase, String answer1, String answer2, String expected) {
        assertSimpleTest(() -> {
            runException(purchase, answer1, answer2);
            assertThat(output()).contains(expected);
        });
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}

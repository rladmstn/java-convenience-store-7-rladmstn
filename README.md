# java-convenience-store-precourse
요구사항에 명시 되지 않은 부분은 🔺 표시

## **Input**

---

✅ 상품 목록과 행사 목록을 파일 입출력으로 불러온다.

- `src/main/resources/products.md`과 `src/main/resources/promotions.md` 파일을 이용한다.
- 두 파일 모두 내용의 형식을 유지한다면 값은 수정 가능하다.

✅ 구매할 상품과 수량을 입력 받는다.

```java
[콜라-10], [사이다-3] // 콜라 10개, 사이다 3개
```

✅ 프로모션 적용 가능한 상품을 고객이 해당 수량보다 적게 가져온 경우, 그 수량만큼 추가 여부를 입력받는다.

- Y : 증정 받을 수 있는 상품을 추가한다.
- N : 증정 받을 수 있는 상품을 추가하지 않는다.

✅ 프로모션 재고가 부족해 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제할지 여부를 입력받는다.

- Y : 일부 수량에 대해 정가로 결제한다.
- N : 정가로 결제해야하는 수량만큼 제외한 후 결제를 진행한다.

✅ 멤버십 할인 적용 여부를 입력받는다.

- Y : 멤버십 할인을 적용한다.
- N : 멤버십 할인을 적용하지 않는다.

✅ 추가 구매 여부를 입력 받는다.

- Y : 재고가 업데이트 된 상품 목록을 확인 후 추가로 구매를 진행한다.
- N : 구매를 종료한다.

### [ Input Error Exception ]

✅ 사용자가 잘못된 값을 입력했을 때, “[ERROR]”로 시작하는 오류 메세지와 함께 상황에 맞는 안내를 출력한다.

- 구매할 상품과 수량 형식이 올바르지 않은 경우

    ```
    [ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.
    ```

- 존재하지 않는 상품을 입력한 경우

    ```
    [ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.
    ```

- 구매 수량이 재고 수량을 초과한 경우

    ```
    [ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.
    ```

- 기타 잘못된 입력의 경우

    ```
    [ERROR] 잘못된 입력입니다. 다시 입력해 주세요.
    ```


## **Key Functions**

---

✅ 사용자가 입력한 상품의 가격과 수량을 기반으로 최종 결제 금액을 계산한다.

- 프로모션 및 멤버십 할인 정책을 반영해 최종 결제 금액을 산출한다.

### [ 재고 관리 ]

✅ 각 상품의 재고 수량을 고려해 결제 가능 여부를 확인한다.

✅ 고객이 상품을 구매할 때마다, 결제된 수량만큼 해당 상품의 재고에서 차감한다.

✅ 차감된 재고는 다음 구매에서 최신 상태로서 유지된다.

### [ 프로모션 할인 ]

✅ 오늘 날짜가 프로모션 기간 내일 경우 할인을 적용한다.

✅ 프로모션은 N개 구매 시 1개 무료 증정의 형태이다.

✅ (1+1) 또는 (2+1) 프로모션이 각각 지정된 상품에 적용된다.

- 동일 상품에 여러 프로모션이 적용되지 않는다.

✅ 프로모션 혜택은 재고 내에서만 적용 가능하다.

✅ 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 필요한 수량을 추가로 가져오면 혜택을 받을 수 있다고 안내한다.

✅ 프로모션 재고가 부족해 일부 수량을 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제하게 됨을 안내한다.

### [ 멤버십 할인 ]

✅ 멤버십 회원은 프로모션 미적용 금액의 30%를 할인받는다.

✅ 프로모션 적용 후 남은 원금 구매 금액에 대해 멤버십 할인을 적용한다.

✅ 멤버십 할인의 최대 한도는 8000원이다.

### [ 영수증 출력 ]

✅ 영수증은 고객의 구매 내역과 할인을 요약해 출력한다.

✅ 영수증 항목은 아래와 같다.

- 구매 상품 내역 : 구매 상품명, 수량, 가격
- 증정 상품 내역 : 프로모션 따라 무료로 제공된 증정 상품의 목록
- 금액 정보
    - 총 구매액 : 구매한 상품의 총 수량과 총 금액
    - 행사할인 : 프로모션에 의해 할인된 금액
    - 멤버십 할인 : 멤버십에 의해 추가로 할인된 금액
    - 내실 돈 : 최종 결제 금액

✅ 영수증 구성 요소를 정렬한다.

## **Output**

---

✅ 환영 인사와 상품명, 가격, 프로모션 이름, 재고를 안내한다.

- 재고가 0개면 `재고 없음` 을 출력한다.

```java
안녕하세요. W편의점입니다.
현재 보유하고 있는 상품입니다.

- 콜라 1,000원 10개 탄산2+1
- 콜라 1,000원 10개
- 사이다 1,000원 8개 탄산2+1
- 사이다 1,000원 7개
- 오렌지주스 1,800원 9개 MD추천상품
- 오렌지주스 1,800원 재고 없음
- 탄산수 1,200원 5개 탄산2+1
- 탄산수 1,200원 재고 없음
- 물 500원 10개
- 비타민워터 1,500원 6개
- 감자칩 1,500원 5개 반짝할인
- 감자칩 1,500원 5개
- 초코바 1,200원 5개 MD추천상품
- 초코바 1,200원 5개
- 에너지바 2,000원 5개
- 정식도시락 6,400원 8개
- 컵라면 1,700원 1개 MD추천상품
- 컵라면 1,700원 10개

구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])
```

✅ 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량만큼 가져오지 않았을 경우, 혜택에 대한 안내 메세지를 출력한다.

```java
현재 {상품명}은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)
```

✅ 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제할지 여부에 대한 안내 메세지를 출력한다.

```java
현재 {상품명} {수량}개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)
```

✅ 멤버십 할인 적용 여부를 확인하기 위해 안내 문구를 출력한다.

```java
멤버십 할인을 받으시겠습니까? (Y/N)
```

✅ 구매 상품 내역, 증정 상품 내역, 금액 정보를 출력한다.

```java
==============W 편의점============
상품명		수량	금액
콜라		3 	3,000
에너지바 		5 	10,000
=============증	정===============
콜라		1
================================
총구매액		8	13,000
행사할인			-1,000
멤버십할인			-3,000
내실돈			 9,000
```

✅ 추가 구매 여부를 확인하기 위해 안내 문구를 출력한다.
```java
감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)
```
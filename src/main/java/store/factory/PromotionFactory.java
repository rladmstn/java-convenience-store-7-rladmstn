package store.factory;

import java.time.LocalDate;
import store.domain.Promotion;

public class PromotionFactory {
    public Promotion createPromotion(String[] input) {
        String name = input[0];
        int buyCount = Integer.parseInt(input[1]);
        int giveCount = Integer.parseInt(input[2]);
        LocalDate startDate = LocalDate.parse(input[3]);
        LocalDate endDate = LocalDate.parse(input[4]);

        return new Promotion(name, buyCount, giveCount, startDate, endDate);
    }
}

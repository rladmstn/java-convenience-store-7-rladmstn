package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.util.List;

public record Promotion(String name,
                        int buyCount,
                        int getCount,
                        LocalDate startDate,
                        LocalDate endDate) {

    public static Promotion getByPromotionName(List<Promotion> promotions, String name){
        for(Promotion promotion : promotions){
            if(promotion.name().equals(name))
                return promotion;
        }
        return null;
    }

    public boolean isPromotionInProgress(){
        LocalDate now = DateTimes.now().toLocalDate();
        return !(now.isBefore(startDate) || now.isAfter(endDate));
    }
}

package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.constants.PrintMessage;

public class InputView {
    public String inputProductSelection(){
        System.out.println(PrintMessage.INPUT_PRODUCT_SELECTION.get());
        return Console.readLine();
    }

    public String askNonPromotionPurchase(){
        System.out.println(PrintMessage.INPUT_PROMOTION_NOT_APPLIED.get());
        return Console.readLine();
    }

    public String askAdditionalPromotion(){
        System.out.println(PrintMessage.INPUT_ADDITIONAL_PROMOTION.get());
        return Console.readLine();
    }

    public String askMembershipDiscount(){
        System.out.println(PrintMessage.INPUT_MEMBERSHIP_DISCOUNT.get());
        return Console.readLine();
    }

    public String askAdditionalPurchase(){
        System.out.println(PrintMessage.INPUT_ADDITIONAL_PURCHASE.get());
        return Console.readLine();
    }
}

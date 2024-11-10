package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.constants.PrintMessage;

public class InputView {
    public String inputProductSelection(){
        System.out.println(PrintMessage.INPUT_PRODUCT_SELECTION.get());
        return Console.readLine();
    }

    public String askNonPromotionPurchase(String productName, int count){
        System.out.println(PrintMessage.INPUT_PROMOTION_NOT_APPLIED.format(productName,count));
        return Console.readLine();
    }

    public String askAdditionalPromotion(String productName){
        System.out.println(PrintMessage.INPUT_ADDITIONAL_PROMOTION.format(productName));
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

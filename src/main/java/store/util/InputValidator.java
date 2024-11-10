package store.util;

import java.util.List;
import store.constants.CommonConstant;
import store.constants.ErrorMessage;
import store.domain.Catalog;
import store.dto.DtoConverter;
import store.dto.PurchaseInputRequest;

public class InputValidator {
    private static final String PURCHASE_PATTERN = "^\\[[가-힣]+-\\d+\\](,\\[[가-힣]+-\\d+\\])*$";

    public static void validatePurchaseSelection(String input, Catalog catalog){
        validatePurchaseForm(input);
        validateCanPurchase(input, catalog);
    }

    public static void validateAnswer(String input){
        validateAnswerForm(input);
    }

    private static void validateCanPurchase(String input, Catalog catalog) {
        List<PurchaseInputRequest> requests = DtoConverter.toPurchaseInputRequest(input);
        for(PurchaseInputRequest request : requests){
            String productName = request.productName();
            int count = request.count();
            checkProductExists(catalog, productName);
            checkSufficientStock(catalog, productName, count);
        }
    }

    private static void validatePurchaseForm(String input){
        if(!input.matches(PURCHASE_PATTERN)){
            throw new IllegalArgumentException(ErrorMessage.INCORRECT_PURCHASE_FORM.getMessage());
        }
    }

    private static void checkProductExists(Catalog catalog, String productName) {
        if(!catalog.existProducts(productName)) {
            throw new IllegalArgumentException(ErrorMessage.NOT_EXIST_PRODUCT.getMessage());
        }
    }

    private static void checkSufficientStock(Catalog catalog, String productName, int count) {
        if(!catalog.hasSufficientStock(productName, count)){
            throw new IllegalArgumentException(ErrorMessage.OVER_STOCK.getMessage());
        }
    }

    private static void validateAnswerForm(String input) {
        if(!(input.equals(CommonConstant.YES.get()) || input.equals(CommonConstant.NO.get()))){
            throw new IllegalArgumentException(ErrorMessage.INVALID_INPUT.getMessage());
        }
    }
}

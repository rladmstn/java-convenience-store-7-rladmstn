package store.controller;

import java.util.List;
import store.constants.CommonConstant;
import store.domain.Catalog;
import store.domain.Promotion;
import store.dto.DtoConverter;
import store.dto.PurchaseInputRequest;
import store.service.ConvenienceStoreService;
import store.util.InputValidator;
import store.view.InputView;
import store.view.OutputView;

public class ConvenienceStoreController {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final ConvenienceStoreService service;
    private final Catalog catalog;
    private final List<Promotion> promotions;

    public ConvenienceStoreController(Catalog catalog, List<Promotion> promotions) {
        this.catalog = catalog;
        this.service = new ConvenienceStoreService(catalog);
        this.promotions = promotions;
    }

    public void run() {
    }

    private List<PurchaseInputRequest> getPurchaseSelection() {
        try {
            String input = inputView.inputProductSelection();
            InputValidator.validatePurchaseSelection(input, catalog);
            return DtoConverter.toPurchaseInputRequest(input);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return getPurchaseSelection();
        }
    }

    private boolean wantsToAddPromotionProduct(String productName) {
        try {
            String input = inputView.askAdditionalPromotion(productName);
            InputValidator.validateAnswer(input);
            return input.equals(CommonConstant.YES.get());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return wantsToAddPromotionProduct(productName);
        }
    }

    private boolean wantsToOriginalPurchase(String productName, int count) {
        try {
            String input = inputView.askNonPromotionPurchase(productName, count);
            InputValidator.validateAnswer(input);
            return input.equals(CommonConstant.YES.get());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return wantsToOriginalPurchase(productName, count);
        }
    }

    private boolean wantsToApplyMembershipDiscount() {
        try {
            String input = inputView.askMembershipDiscount();
            InputValidator.validateAnswer(input);
            return input.equals(CommonConstant.YES.get());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return wantsToApplyMembershipDiscount();
        }
    }
}

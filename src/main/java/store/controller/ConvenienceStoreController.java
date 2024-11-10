package store.controller;

import java.util.ArrayList;
import java.util.List;
import store.constants.CommonConstant;
import store.domain.Catalog;
import store.domain.NormalProduct;
import store.domain.NormalPurchaseResult;
import store.domain.Promotion;
import store.domain.PromotionProduct;
import store.domain.PromotionPurchaseResult;
import store.domain.PurchaseResult;
import store.dto.DtoConverter;
import store.dto.PurchaseInputRequest;
import store.dto.ReceiptResponse;
import store.service.ConvenienceStoreService;
import store.util.InputValidator;
import store.view.InputView;
import store.view.OutputView;

public class ConvenienceStoreController {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final ConvenienceStoreService service;
    private final Catalog catalog;

    public ConvenienceStoreController(Catalog catalog) {
        this.catalog = catalog;
        this.service = new ConvenienceStoreService(catalog);
    }

    public void run() {
        while (true) {
            outputView.printCatalog(DtoConverter.toCatalogResponse(catalog));
            List<PurchaseInputRequest> purchases = getPurchaseSelection();
            List<PurchaseResult> purchaseResults = processPurchase(purchases);
            int membershipDiscount = applyMembershipDiscount(purchaseResults);
            ReceiptResponse receipt = DtoConverter.toReceiptResponse(purchaseResults, membershipDiscount);
            outputView.printReceipt(receipt);
            if(!wantsToMorePurchase()) {
                break;
            }
        }
    }

    private int applyMembershipDiscount(List<PurchaseResult> purchaseResults) {
        int membershipDiscount = 0;
        if(wantsToApplyMembershipDiscount()){
            membershipDiscount = service.calculateMembershipDiscountAmount(purchaseResults);
        }
        return membershipDiscount;
    }

    private List<PurchaseResult> processPurchase(List<PurchaseInputRequest> purchases) {
        List<PurchaseResult> results = new ArrayList<>();
        for (PurchaseInputRequest purchase : purchases) {
            if (service.isPromotionActive(purchase.productName())) {
                results.add(purchasePromotionProducts(purchase.productName(), purchase.count()));
                continue;
            }
            results.add(purchaseNormalProducts(purchase.productName(), purchase.count()));
        }
        return results;
    }

    private NormalPurchaseResult purchaseNormalProducts(String productName, int purchaseCount) {
        NormalProduct normalProduct = catalog.getNormalProducts(productName);
        catalog.decreaseStock(normalProduct, purchaseCount);
        return new NormalPurchaseResult(
                productName,
                normalProduct.getPrice(),
                purchaseCount);
    }

    private PromotionPurchaseResult purchasePromotionProducts(String productName, int purchaseCount) {
        PromotionProduct promotionProduct = catalog.getPromotionProducts(productName);
        PromotionPurchaseResult result = service.initPromotionPurchaseResult(purchaseCount, promotionProduct);
        if (canAddPromotionProduct(purchaseCount, result, promotionProduct.getPromotion(), promotionProduct)) {
            result.addPromotionProduct();
        }
        if (shouldPurchaseAtOriginalPrice(productName, purchaseCount, result, promotionProduct)) {
            result.removeOriginalPurchase();
        }
        catalog.decreaseStock(promotionProduct, result.getTotalCount());
        return result;
    }

    private boolean shouldPurchaseAtOriginalPrice(String productName, int purchaseCount, PromotionPurchaseResult result,
                                                  PromotionProduct promotionProduct) {
        return service.isOriginalPurchaseRequired(result.getOriginalCount(), promotionProduct.getStock(), purchaseCount)
                && !wantsToOriginalPurchase(productName, result.getOriginalCount());
    }

    private boolean canAddPromotionProduct(int purchaseCount, PromotionPurchaseResult result,
                                           Promotion promotion, PromotionProduct promotionProduct) {
        return service.isPromotionProductAddable(result.getOriginalCount(), purchaseCount, promotion,
                promotionProduct.getStock())
                && wantsToAddPromotionProduct(promotionProduct.getName());
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

    private boolean wantsToMorePurchase() {
        try {
            String input = inputView.askAdditionalPurchase();
            InputValidator.validateAnswer(input);
            return input.equals(CommonConstant.YES.get());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return wantsToMorePurchase();
        }
    }
}

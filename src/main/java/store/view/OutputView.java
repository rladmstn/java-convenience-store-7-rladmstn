package store.view;

import java.util.List;
import store.constants.PrintMessage;
import store.dto.ProductResponse;
import store.dto.PromotionProductResponse;
import store.dto.PurchasedProductResponse;
import store.dto.ReceiptResponse;
import store.dto.SummaryResponse;
import store.dto.TotalPaymentResponse;

public class OutputView {
    public void printCatalog(List<ProductResponse> catalogs) {
        System.out.println(PrintMessage.PRINT_START.get());
        for (ProductResponse catalog : catalogs) {
            System.out.println(PrintMessage.PRINT_PRODUCT.format(
                    catalog.name(),
                    catalog.price(),
                    catalog.stock(),
                    catalog.promotion()
            ));
        }
    }

    public void printReceipt(ReceiptResponse receipt) {
        printPurchasedProduct(receipt.purchasedProduct());
        printPromotionProduct(receipt.promotionProduct());
        printTotalSummary(receipt.totalPayment(), receipt.summary());
    }

    private void printPurchasedProduct(List<PurchasedProductResponse> response) {
        System.out.println(PrintMessage.PRINT_RECEIPT_START.get());
        System.out.println(PrintMessage.PRINT_CATEGORY.get());
        for (PurchasedProductResponse product : response) {
            System.out.println(PrintMessage.PRINT_PURCHASE_PRODUCT.format(
                    product.name(),
                    product.count(),
                    product.payment()
            ));
        }
    }

    private void printPromotionProduct(List<PromotionProductResponse> response) {
        System.out.println(PrintMessage.PRINT_RECEIPT_PROMOTION.get());
        for (PromotionProductResponse product : response) {
            System.out.println(PrintMessage.PRINT_PROMOTION_PRODUCT.format(
                    product.name(), product.count()
            ));
        }
    }

    private void printTotalSummary(TotalPaymentResponse totalPayment, List<SummaryResponse> summaryResponse) {
        System.out.println(PrintMessage.PRINT_RECEIPT_END.get());
        System.out.println(PrintMessage.PRINT_TOTAL.format(totalPayment.count(), totalPayment.totalPayment()));
        for (SummaryResponse summary : summaryResponse) {
            System.out.println(PrintMessage.PRINT_SUMMARY.format(summary.category(), summary.payment()));
        }
    }
}

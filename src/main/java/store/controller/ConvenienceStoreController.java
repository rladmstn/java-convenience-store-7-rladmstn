package store.controller;

import java.util.List;
import store.domain.Catalog;
import store.domain.Promotion;
import store.view.InputView;
import store.view.OutputView;

public class ConvenienceStoreController {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final List<Promotion> promotions;
    private final Catalog catalog;

    public ConvenienceStoreController(List<Promotion> promotions,Catalog catalog) {
        this.promotions = promotions;
        this.catalog = catalog;
    }

    public void run(){
    }
}

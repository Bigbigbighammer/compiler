package service;

import java.util.ArrayList;
import java.util.List;

public class TaxChain {

    private final List<TaxHandler> taxHandlers = new ArrayList<>();

    public void addLastHandler(TaxHandler taxHandler) {
        this.taxHandlers.add(taxHandler);
    }

    public double calculate(double salary) {
        TaxContext taxContext = new TaxContext(salary);
        for (TaxHandler taxHandler : taxHandlers) {
            taxHandler.calculate(taxContext);
            if (taxContext.shouldStop()) {
                break;
            }
        }
        return taxContext.getFinalTax();
    }

}

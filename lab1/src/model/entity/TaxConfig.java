package model.entity;

public class TaxConfig {

    private double threshold = 1600;

    private TaxTable taxTable;

    public TaxConfig(double threshold, TaxTable taxTable) {
        this.threshold = threshold;
        this.taxTable = taxTable;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public TaxTable getTaxTable() {
        return taxTable;
    }

    public void setTaxTable(TaxTable taxTable) {
        this.taxTable = taxTable;
    }
}

package tax;

/**
 * 税率表规则
 */
public class TaxRule {
    private int grade;
    private double min;
    private double max;
    private double rate;

    public TaxRule(int grade, double min, double max, double rate) {
        this.grade = grade;
        this.min = min;
        this.max = max;
        this.rate = rate;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}

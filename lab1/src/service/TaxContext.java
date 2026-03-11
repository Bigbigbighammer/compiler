package service;

public class TaxContext {

    private double tax = 0;

    private double salary = 0;

    private boolean stop = false;

    public TaxContext(double salary) {
        this.salary = salary;
    }

    public void setSalary(double money) {
        this.salary = money;
    }

    public double getSalary() {
        return this.salary;
    }

    public void stopChain() {
        this.stop = true;
    }

    public boolean shouldStop() {
        return this.stop;
    }

    public void addTax(double money) {
        this.tax += money;
    }

    public double getFinalTax() {
        return this.tax;
    }
}

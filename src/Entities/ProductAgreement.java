package Entities;


public class ProductAgreement {
    private double price;
    private int minAmount;
    private double baseDiscount;
    private double maxDiscount;

    public ProductAgreement(double price, int minAmount, double baseDiscount, double maxDiscount){
        this.price=price;
        this.minAmount=minAmount;
        this.baseDiscount=baseDiscount;
        this.maxDiscount=maxDiscount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount;
    }

    public double getBaseDiscount() {
        return baseDiscount;
    }

    public void setBaseDiscount(double baseDiscount) {
        this.baseDiscount = baseDiscount;
    }

    public double getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(double maxDiscount) {
        this.maxDiscount = maxDiscount;
    }
}

package Suppliers.Entities;


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

    public int getMinAmount() {
        return minAmount;
    }

    public double getBaseDiscount() {
        return baseDiscount;
    }

    public double getMaxDiscount() {
        return maxDiscount;
    }

    @Override
    public String toString(){
        return maxDiscount == 0 ? "\tPrice: "+price+ "\n\t\t\tNo discount agreement." :
                "\tPrice: "+price+
                "\t\tMinimum amount for discount: "+minAmount+
                "\n\t\t\tBase discount: "+baseDiscount+
                "\t\tMaximum discount: "+maxDiscount;
    }
}

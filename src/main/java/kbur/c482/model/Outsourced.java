package kbur.c482.model;

public class Outsourced extends Part {

    private int companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
    }

    public int getCompanyName() {
        return companyName;
    }

    public void setCompanyName(int companyName) {
        this.companyName = companyName;
    }
}

package kbur.c482.model;

public class OutsourcedPart extends Part{

    private String companyName;

    /** Constructor for in house part. */
    public OutsourcedPart(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** Method gets Company name. */
    public String getCompanyName () {
        return companyName;
    }

    /** Method sets Company Name. */
    public void setCompanyName () {
        this.companyName = companyName;
    }
}

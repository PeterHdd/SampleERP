package model;

public class Product {

    private String searchKey,name,currency,productCategory,uom, bpReference;

    public Product(String key,String name,String currency,String productCategory,String uom,String bpReference) {
        this.searchKey=key;
        this.name=name;
        this.currency=currency;
        this.productCategory=productCategory;
        this.uom=uom;
        this.bpReference=bpReference;
    }

    public String getBpReference() {
        return bpReference;
    }

    public String getCurrency() {
        return currency;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public String getUom() {
        return uom;
    }

    public String getName() {
        return name;
    }
}

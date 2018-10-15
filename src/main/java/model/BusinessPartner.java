package model;

public class BusinessPartner {

    private String searchKey,fiscalName,taxId,companyId,currency,location;
    private Boolean isCustomer;

    public BusinessPartner(String searchKey,String fiscalName,String taxId,String companyId, boolean customer,String currency,String location){
        this.searchKey=searchKey;
        this.fiscalName=fiscalName;
        this.taxId=taxId;
        this.companyId=companyId;
        this.isCustomer=customer;
        this.currency=currency;
        this.location=location;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getCurrency() {
        return currency;
    }

    public Boolean getCustomer() {
        return isCustomer;
    }

    public String getFiscalName() {
        return fiscalName;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public String getLocation() {
        return location;
    }

    public String getTaxId() {
        return taxId;
    }
}

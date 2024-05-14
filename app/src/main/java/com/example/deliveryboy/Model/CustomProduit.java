package com.example.deliveryboy.Model;

public class CustomProduit {
    private String productName;
    private String productPrice;

    private String productRef;
    private String productQuantity;
    private String productCondition;

    public CustomProduit(String productName, String productPrice, String productRef, String productQuantity, String productCondition) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productRef = productRef;
        this.productQuantity = productQuantity;
        this.productCondition = productCondition;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductRef() {
        return productRef;
    }

    public void setProductRef(String productRef) {
        this.productRef = productRef;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductCondition() {
        return productCondition;
    }

    public void setProductCondition(String productCondition) {
        this.productCondition = productCondition;
    }
}

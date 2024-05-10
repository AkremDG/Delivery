package com.example.deliveryboy.Model.Responses;

public class LocalPriceAndQuantity {
    private Double price;
    private Double quantity;

    public LocalPriceAndQuantity(Double price, Double quantity) {
        this.price = price;
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}

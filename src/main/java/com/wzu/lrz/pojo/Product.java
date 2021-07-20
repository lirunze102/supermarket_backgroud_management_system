package com.wzu.lrz.pojo;

public class Product {

    private Integer id;
    private String productName;
    private Integer price;
    private Integer num_have;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getNum_have() {
        return num_have;
    }

    public void setNum_have(Integer num_have) {
        this.num_have = num_have;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", num_have=" + num_have +
                '}';
    }
}

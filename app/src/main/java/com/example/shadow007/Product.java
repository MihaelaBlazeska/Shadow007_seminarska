package com.example.shadow007;

public class Product {

    String name;
    String brand;
    int quantity;
    int price;
    int discount;
    String gender;
    String desc;
    String image;

    //long resId = parent.getResources().getIdentifier(arrayOfStrings[position], "drawable", mApplicationContext.getPackageName());
    Product(){

    }
    Product(String name,int quantity,int price,int discount,String gender,String brand,String desc,String image){
        this.name=name;
        this.desc=desc;
        this.brand=brand;
        this.gender=gender;
        this.price=price;
        this.quantity=quantity;
        this.discount=discount;
        this.image=image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}

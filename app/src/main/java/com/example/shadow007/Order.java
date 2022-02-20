package com.example.shadow007;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Order{
    private ArrayList<Product> order=new ArrayList<>();
    private ArrayList<Integer> quantity=new ArrayList<>();
    LatLng delivery_add;
    int id;
    String user;
    String delivery;
    int final_price;
    int processed;
    int delivered;

    public Order(){

    }
    public Order(ArrayList<Product> order,LatLng delivery_add,String user,String delivery,int final_price,int id,ArrayList<Integer> quantity){
        this.order=order;
        this.id=id;
        this.delivery_add=delivery_add;
        this.user=user;
        this.delivery=delivery;
        this.final_price=final_price;
        this.processed=0;
        this.delivered=0;
        this.quantity=quantity;
    }

    public ArrayList<Integer> getQuantity() {
        return quantity;
    }

    public void setQuantity(ArrayList<Integer> quantity) {
        this.quantity = quantity;
    }

    public int getDelivered() {
        return delivered;
    }

    public void setDelivered(int delivered) {
        this.delivered = delivered;
    }

    public int getProcessed() {
        return processed;
    }

    public void setProcessed(int processed) {
        this.processed = processed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Product> getOrder() {
        return order;
    }

    public void setOrder(ArrayList<Product> order) {
        this.order = order;
    }

    public LatLng getDelivery_add() {
        return delivery_add;
    }

    public void setDelivery_add(LatLng delivery_add) {
        this.delivery_add = delivery_add;
    }


    public int getFinal_price() {
        return final_price;
    }

    public void setFinal_price(int final_price) {
        this.final_price = final_price;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }
}

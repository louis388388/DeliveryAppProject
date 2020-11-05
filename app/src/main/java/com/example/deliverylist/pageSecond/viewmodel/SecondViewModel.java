package com.example.deliverylist.pageSecond.viewmodel;

import androidx.lifecycle.ViewModel;

public class SecondViewModel extends ViewModel {

    private int position = -1;
    private String address_start;
    private String address_end;
    private String price;
    private String goods_picture;

    public String getAddress_start() {
        return address_start;
    }

    public void setAddress_start(String address_start) {
        this.address_start = address_start;
    }

    public String getAddress_end() {
        return address_end;
    }

    public void setAddress_end(String address_end) {
        this.address_end = address_end;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGoods_picture() {
        return goods_picture;
    }

    public void setGoods_picture(String goods_picture) {
        this.goods_picture = goods_picture;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}

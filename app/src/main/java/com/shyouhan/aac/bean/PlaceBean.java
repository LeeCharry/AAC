package com.shyouhan.aac.bean;

import com.example.tulib.util.http.IModel;

import java.io.Serializable;

/**
 * Created by lcy on 2018/7/8.
 */

public class PlaceBean implements Serializable,IModel{

    /**
     * id : 1
     * placename : 台湾快递中心
     * traplacename : 台灣快遞中心
     * phone : 076-666666
     * address : 台湾市中心
     * traaddress : 台灣市中心
     */

    private int id;
    private String placename;
    private String traplacename;
    private String phone;
    private String address;
    private String traaddress;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlacename() {
        return placename;
    }

    public void setPlacename(String placename) {
        this.placename = placename;
    }

    public String getTraplacename() {
        return traplacename;
    }

    public void setTraplacename(String traplacename) {
        this.traplacename = traplacename;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTraaddress() {
        return traaddress;
    }

    public void setTraaddress(String traaddress) {
        this.traaddress = traaddress;
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public boolean isAuthError() {
        return false;
    }

    @Override
    public boolean isBizError() {
        return false;
    }
}

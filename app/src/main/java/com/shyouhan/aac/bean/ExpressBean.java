package com.shyouhan.aac.bean;

import com.example.tulib.util.http.IModel;

import java.io.Serializable;

/**
 * Created by lcy on 2018/7/8.
 */

public class ExpressBean implements Serializable,IModel{

    /**
     * id : 1
     * name : 顺丰快递
     * traname : 順豐快遞
     */

    private int id;
    private String name;
    private String traname;
    private Boolean isSelected;

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTraname() {
        return traname;
    }

    public void setTraname(String traname) {
        this.traname = traname;
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

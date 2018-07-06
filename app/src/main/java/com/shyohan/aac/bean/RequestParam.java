package com.shyohan.aac.bean;

import java.io.Serializable;

/**
 * Created by lcy on 2018/7/5.
 */

public class RequestParam implements Serializable {

    /**
     * 登录参数
     * username : qw111
     * password : 111111
     * device : ac2b6e7c2de75237e7c2de75237ac2b6
     */
    private String username;
    private String password;
    private String device;

    /**
     * 中转快递参数
     * token : XurDgO4hto
     * packid : 95574
     * mainlandid : 96366
     */
    private String token;
    private long packid;
    private String mainlandid;

    /**
     * 抵达站所，出货，揽件，签收参数
     * "token": "XOR8YwmZ9K",
     * "  packid": 57963,
     * place : 4Kf4VBLnzQ
     */
    private String place;


    public RequestParam(String username, String password, String device) {
        this.username = username;
        this.password = password;
        this.device = device;
    }

    public RequestParam(String token, long packid) {
        this.token = token;
        this.packid = packid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getPackid() {
        return packid;
    }

    public void setPackid(long packid) {
        this.packid = packid;
    }

    public String getMainlandid() {
        return mainlandid;
    }

    public void setMainlandid(String mainlandid) {
        this.mainlandid = mainlandid;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}

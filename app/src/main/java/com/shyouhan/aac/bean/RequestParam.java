package com.shyouhan.aac.bean;

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
    private String packid;
    private String fakepackid;
    private Integer express;
    private String expressnum;

    /**
     * 抵达站所，出货，揽件，签收参数
     * "token": "XOR8YwmZ9K",
     * "  packid": 57963,
     * place : 4Kf4VBLnzQ
     */
    private String place;

    /**
     * phone : pVlqweieaf
     * content : OdMjQBPZSH
     */

    private String email;
    private String content;

    public String getFakepackid() {
        return fakepackid;
    }

    public void setFakepackid(String fakepackid) {
        this.fakepackid = fakepackid;
    }

    /**
     * packid : 62140
     * express : 84037
     * expressnum : 24544
     */





    public RequestParam(String username, String password, String device) {
        this.username = username;
        this.password = password;
        this.device = device;
    }

    public RequestParam(String username, String token, String email, String content) {
        this.username = username;
//        this.token = token;
        this.email = email;
        this.content = content;
    }
    //多件抵达站点
    public RequestParam(String token, String packid, String fakepackid, Integer express) {
        this.token = token;
        this.packid = packid;
        this.fakepackid = fakepackid;
//        this.express = express;
    }

    public RequestParam() {
    }

    public RequestParam(String token, String packid) {
        this.token = token;
        this.packid = packid;
    }

    public RequestParam(String token, String packid, Integer express, String expressnum) {
        this.token = token;
        this.packid = packid;
        this.express = express;
        this.expressnum = expressnum;
    }

    public RequestParam(String packid) {
        this.packid = packid;
    }


    public String getPlace() {
        return place;
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

    public String getPackid() {
        return packid;
    }

    public void setPackid(String packid) {
        this.packid = packid;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getExpress() {
        return express;
    }

    public void setExpress(Integer express) {
        this.express = express;
    }

    public String getExpressnum() {
        return expressnum;
    }

    public void setExpressnum(String expressnum) {
        this.expressnum = expressnum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

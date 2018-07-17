package com.shyouhan.aac.bean;

import java.io.Serializable;

/**
 * Created by lcy on 2018/7/8.
 */

public class BannerBean implements Serializable {

    /**
     * id : 4
     * title : banner 测试
     * tratitle : banner 測試
     * image : https://i.loli.net/2018/07/08/5b419fed77e47.jpg
     * link : http://aac.shyouhan.com
     */

    private int id;
    private String title;
    private String tratitle;
    private String image;
    private String link;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTratitle() {
        return tratitle;
    }

    public void setTratitle(String tratitle) {
        this.tratitle = tratitle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}

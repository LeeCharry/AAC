package com.shyouhan.aac.bean;

import java.io.Serializable;

/**
 * Created by licy on 2019/1/26 13:56.
 * <p>
 * description：
 */

public class ResultBean implements Serializable {
    private String danhao;
    private String msg;
    private String tramsg;

    public ResultBean(String danhao, String msg, String tramsg) {
        this.danhao = danhao;
        this.msg = msg;
        this.tramsg = tramsg;
    }

    public String getDanhao() {
        return danhao;
    }

    public void setDanhao(String danhao) {
        this.danhao = danhao;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTramsg() {
        return tramsg;
    }

    public void setTramsg(String tramsg) {
        this.tramsg = tramsg;
    }
}

package com.shyouhan.aac.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lcy on 2018/7/8.
 */

public class PackStatusBean<T> implements Serializable {

    /**
     * pack : 6925861571459
     * status : [{"status":"sign","time":null,"desc":"已签收","tradesc":"已簽收"},{"status":"delivery","time":null,"desc":"正在派送包裹","tradesc":"正在派送包裹"},{"status":"arriveplace","time":null,"desc":"已到达 ","tradesc":"已到達 "},{"status":"transfer","time":null,"desc":"正在中转到国内快递","tradesc":"正在中轉到國內快遞"},{"status":"sending","time":1531020344,"desc":"已从台湾发货，正在发往上海","tradesc":"已從台灣發貨，正在發往上海"},{"status":"lanjian","time":1531020337,"desc":"已从台湾分拨中心收件","tradesc":"已從台灣分撥中心發件"}]
     */
    /**
     * {
     "status": 412,
     "msg": "此快递不存在！",
     "tramsg": "此快遞不存在！"
     }
     */

    private String msg;
    private String tramsg;
    private String pack;
    private Integer status;
    private List<PackStatusBean.StatusBean> data;

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<StatusBean> getData() {
        return data;
    }

    public void setData(List<StatusBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PackStatusBean{" +
                "msg='" + msg + '\'' +
                ", tramsg='" + tramsg + '\'' +
                ", pack='" + pack + '\'' +
                ", status=" + status +
                '}';
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

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }


    public static class StatusBean implements Serializable{
        /**
         * status : sign
         * time : null
         * desc : 已签收
         * tradesc : 已簽收
         */

        private String status;
        private Long time;
        private String desc;
        private String tradesc;

        @Override
        public String toString() {
            return "StatusBean{" +
                    "status='" + status + '\'' +
                    ", time=" + time +
                    ", desc='" + desc + '\'' +
                    ", tradesc='" + tradesc + '\'' +
                    '}';
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Long getTime() {
            return time;
        }

        public void setTime(Long time) {
            this.time = time;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getTradesc() {
            return tradesc;
        }

        public void setTradesc(String tradesc) {
            this.tradesc = tradesc;
        }
    }
}

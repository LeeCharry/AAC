package com.shyohan.aac.bean;

import com.example.tulib.util.http.IModel;

import java.io.Serializable;

/**
 * Created by administor on 2017/6/28.
 */

public class BaseObject<T> implements Serializable, IModel {

   /**
    * status : 200
    * msg : 成功
    * tramsg : 成功
    * token : fvgDgLypprn9momyni3Iw3kUAX1+Ny18ycNjaq2lgMRX4AvpEepFOrp9eqvVDXW8OrzIYM2MX9pbXtMlOLgeVcy5TyUUFwXLxh5XK4rWXlt5ni4AeVWbkAuEtOzFljag1HSax+msK/X4VD7ghUxBHAk/aHFBZF57hx5broBTfWA=
    */

   private int status;
   private String msg;
   private String tramsg;
   private String token;
   private T data;

   public T getData() {
      return data;
   }

   public void setData(T data) {
      this.data = data;
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

   public int getStatus() {
      return status;
   }

   public void setStatus(int status) {
      this.status = status;
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

   public String getToken() {
      return token;
   }

   public void setToken(String token) {
      this.token = token;
   }
}

package com.hi.base.data;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class HiOrder {
    private String cpOrderID;			//CP订单号
    private String extra;               //自定义数据， 支付回调的时候原样返回
    private String payNotifyUrl;        //通知发货URL，这里设置了优先使用这里的，这里没设置，使用游戏管理中配置的回调地址

    private Integer price;              //单位 分

    private String currency;            //币种，默认USD

    private String productID;           //商品ID
    private String productName;         //商品名称
    private String productDesc;         //商品描述

    private String roleID;              //角色ID
    private String roleName;            //角色名称
    private String roleLevel;           //角色等级
    private String serverID;            //服务器ID

    public String getCpOrderID() {
        return cpOrderID;
    }

    public void setCpOrderID(String cpOrderID) {
        this.cpOrderID = cpOrderID;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getPayNotifyUrl() {
        return payNotifyUrl;
    }

    public void setPayNotifyUrl(String payNotifyUrl) {
        this.payNotifyUrl = payNotifyUrl;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleLevel() {
        return roleLevel;
    }

    public void setRoleLevel(String roleLevel) {
        this.roleLevel = roleLevel;
    }

    public String getServerID() {
        return serverID;
    }

    public void setServerID(String serverID) {
        this.serverID = serverID;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public HiOrderResult getOrderResult() {
        return orderResult;
    }

    public void setOrderResult(HiOrderResult orderResult) {
        this.orderResult = orderResult;
    }

    private String serverName;          //服务器名称
    private String vip;                 //vip等级
    private HiOrderResult orderResult;

    public JSONObject toJSONObject() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("cpOrderID", TextUtils.isEmpty(cpOrderID) ? "" : cpOrderID);
        params.put("extra", TextUtils.isEmpty(extra) ? "" : extra);
        params.put("payNotifyUrl", TextUtils.isEmpty(payNotifyUrl) ? "" : payNotifyUrl);
        params.put(" price",  price);
        params.put("currency", TextUtils.isEmpty(currency) ? "" : currency);
        params.put("productID", TextUtils.isEmpty(productID) ? "" : productID);
        params.put("productName", TextUtils.isEmpty(productName) ? "" : productName);
        params.put("productDesc", TextUtils.isEmpty(productDesc) ? "" : productDesc);
        params.put("roleID", TextUtils.isEmpty(roleID) ? "" : roleID);
        params.put("roleName", TextUtils.isEmpty(roleName) ? "" : roleName);
        params.put("roleLevel", TextUtils.isEmpty(roleLevel) ? "" : roleLevel);
        params.put("serverID", TextUtils.isEmpty(serverID) ? "" : serverID);
        params.put("serverName", TextUtils.isEmpty(serverName) ? "" : serverName);
        params.put("vip", TextUtils.isEmpty(vip) ? "" : vip);

        if (orderResult != null) {
            params.put("orderResult", orderResult.toJSONObject());
        }

        return params;
    }

    public void fromJSONObject(JSONObject json) {

        this.cpOrderID = json.optString("cpOrderID");
        this.extra = json.optString("extra");
        this.payNotifyUrl = json.optString("payNotifyUrl");
        this.price = json.optInt("price");
        this.currency = json.optString("currency");
        this.productID = json.optString("productID");
        this.productName = json.optString("productName");
        this.productDesc = json.optString("productDesc");
        this.roleID = json.optString("roleID");
        this.roleName = json.optString("roleName");
        this.roleLevel = json.optString("roleLevel");
        this.serverID = json.optString("serverID");
        this.serverName = json.optString("serverName");
        this.vip = json.optString("vip");

        if (json.has("orderResult")) {
            this.orderResult = new HiOrderResult();
            orderResult.fromJSONObject(json.optJSONObject("orderResult"));
        }
    }

}

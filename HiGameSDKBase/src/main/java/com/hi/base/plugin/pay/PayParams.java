package com.hi.base.plugin.pay;

public class PayParams {

    /**
     * 订单ID
     */
    public String orderId;

    /**
     * 订单名称
     */
    public String orderName;

    /**
     * 订单描述
     */
    public String orderDesc;

    /**
     * 订单价格
     */
    public String orderPrice;

    /**
     * 订单货币类型
     */
    public String orderCurrency;

    /**
     * 订单额外信息
     */
    public String orderExtra;

    /**
     * 订单回调地址
     */
    public String orderCallback;

    /**
     * 订单回调参数
     */
    public String orderCallbackParam;

    /**
     * 订单回调类型
     */
    public String orderCallbackType;

    /**
     * 订单回调URL
     */
    public String orderCallbackUrl;

    /**
     * 订单回调参数类型
     */
    public String orderCallbackParamType;

    /**
     * 订单回调参数值
     */
    public String orderCallbackParamValue;

    /**
     * 订单回调参数值类型
     */
    public String orderCallbackParamValueType;

    /**
     * 订单回调参数值类型2
     */
    public String orderCallbackParamValueType2;

    /**
     * 订单回调参数值2
     */
    public String orderCallbackParamValue2;

    /**
     * 本地产品ID
     */
    private String localProductID;

    /**
     * 平台产品ID
     */
    private String platformProductID;

    /**
     * 价格
     */
    private Integer price;

    /**
     * 名称
     */
    private String name;

    /**
     * 区域价格
     */
    private String localePrice;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderCurrency() {
        return orderCurrency;
    }

    public void setOrderCurrency(String orderCurrency) {
        this.orderCurrency = orderCurrency;
    }

    public String getOrderExtra() {
        return orderExtra;
    }

    public void setOrderExtra(String orderExtra) {
        this.orderExtra = orderExtra;
    }

    public String getOrderCallback() {
        return orderCallback;
    }

    public void setOrderCallback(String orderCallback) {
        this.orderCallback = orderCallback;
    }

    public String getOrderCallbackParam() {
        return orderCallbackParam;
    }

    public void setOrderCallbackParam(String orderCallbackParam) {
        this.orderCallbackParam = orderCallbackParam;
    }

    public String getOrderCallbackType() {
        return orderCallbackType;
    }

    public void setOrderCallbackType(String orderCallbackType) {
        this.orderCallbackType = orderCallbackType;
    }

    public String getOrderCallbackUrl() {
        return orderCallbackUrl;
    }

    public void setOrderCallbackUrl(String orderCallbackUrl) {
        this.orderCallbackUrl = orderCallbackUrl;
    }

    public String getOrderCallbackParamType() {
        return orderCallbackParamType;
    }

    public void setOrderCallbackParamType(String orderCallbackParamType) {
        this.orderCallbackParamType = orderCallbackParamType;
    }

    public String getOrderCallbackParamValue() {
        return orderCallbackParamValue;
    }

    public void setOrderCallbackParamValue(String orderCallbackParamValue) {
        this.orderCallbackParamValue = orderCallbackParamValue;
    }

    public String getOrderCallbackParamValueType() {
        return orderCallbackParamValueType;
    }

    public void setOrderCallbackParamValueType(String orderCallbackParamValueType) {
        this.orderCallbackParamValueType = orderCallbackParamValueType;
    }

    public String getOrderCallbackParamValueType2() {
        return orderCallbackParamValueType2;
    }

    public void setOrderCallbackParamValueType2(String orderCallbackParamValueType2) {
        this.orderCallbackParamValueType2 = orderCallbackParamValueType2;
    }

    public String getOrderCallbackParamValue2() {
        return orderCallbackParamValue2;
    }

    public void setOrderCallbackParamValue2(String orderCallbackParamValue2) {
        this.orderCallbackParamValue2 = orderCallbackParamValue2;
    }

    public String getLocalProductID() {
        return localProductID;
    }

    public void setLocalProductID(String localProductID) {
        this.localProductID = localProductID;
    }

    public String getPlatformProductID() {
        return platformProductID;
    }

    public void setPlatformProductID(String platformProductID) {
        this.platformProductID = platformProductID;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocalePrice() {
        return localePrice;
    }

    public void setLocalePrice(String localePrice) {
        this.localePrice = localePrice;
    }
}
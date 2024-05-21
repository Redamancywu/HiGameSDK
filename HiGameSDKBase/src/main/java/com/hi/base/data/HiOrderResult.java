package com.hi.base.data;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class HiOrderResult {
    private String orderID;
    private String productID;
    private String realProductID;
    private String orderResult;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getRealProductID() {
        return realProductID;
    }

    public void setRealProductID(String realProductID) {
        this.realProductID = realProductID;
    }

    public String getOrderResult() {
        return orderResult;
    }

    public void setOrderResult(String orderResult) {
        this.orderResult = orderResult;
    }
    public JSONObject toJSONObject() throws JSONException {

        JSONObject params = new JSONObject();
        params.put("orderID", TextUtils.isEmpty(orderID) ? "" : orderID);
        params.put("productID",TextUtils.isEmpty(productID) ? "" : productID);
        params.put("realProductID",TextUtils.isEmpty(realProductID) ? "" : realProductID);
        params.put("orderResult", TextUtils.isEmpty(orderResult) ? "" : orderResult);

        return params;
    }

    public void fromJSONObject(JSONObject json) {

        this.orderID = json.optString("orderID");
        this.productID = json.optString("productID");
        this.realProductID = json.optString("realProductID");
        this.orderResult = json.optString("orderResult");
    }
}

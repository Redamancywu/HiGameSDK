package com.hi.base.data;
/**
 * 商品信息，包含当前支付地区商品对应的转换汇率后的价格
 */
public class HiProduct {

    private String localProductID;               //游戏内商品ID
    private String platformProductID;           //对应在当前支付平台上面配置的商品ID
    private Integer price;                      //价格， 对应在SDK管理后台中配置的价格，单位 分
    private String name;                        //商品名称

    private String localePrice;                 //当前地区的支付价格，根据汇率转换之后的，比如HK$8.00
    private String localeCurrency;              //当前货币类型，比如HKD, CNY等

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

    public String getLocaleCurrency() {
        return localeCurrency;
    }

    public void setLocaleCurrency(String localeCurrency) {
        this.localeCurrency = localeCurrency;
    }
}

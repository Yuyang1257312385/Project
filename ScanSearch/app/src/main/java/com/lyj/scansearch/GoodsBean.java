package com.lyj.scansearch;

/**
 * Created by yu on 2017/6/14.
 */

public class GoodsBean {


    /**
     * ean : 6924862100033
     * name :
     * titleSrc : http://www.liantu.com/tiaoma/eantitle.php?title=KzRwWk9PUThiNkdJRy9QVldoVWNNRHBoMlRwT3pyci9FTlpydk50cWxjTW94eUtpT3VqNG9wb1ZHZmhKUUx1T2RIeFBlU2ZmbFR0OG5BdDFoMkZDcyttaGR0V0tLUXRPdUR3WmF5STM5VkE1dlJRQ2FtYk9Tdz09
     * guobie : 中国
     * place : 百事
     * price : 2.5
     * supplier : 武汉百事可乐饮料有限公司
     * sort_id : 7
     * faccode : 69248621
     * fac_name : 武汉百事可乐饮料有限公司
     */

    private String ean;//商品条形码
    private String name;//空
    private String titleSrc;//商品名称(返回的是图片url，不是文本,需要特殊处理)
    private String guobie;//商品国别
    private String place;//商品产地
    private double price;//参考价格
    private String supplier;//供应商名称
    private int sort_id;//商品类别
    private String faccode;//生产厂商代码
    private String fac_name;//生产厂商名称
    private String fac_status;//注册状态

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitleSrc() {
        return titleSrc;
    }

    public void setTitleSrc(String titleSrc) {
        this.titleSrc = titleSrc;
    }

    public String getGuobie() {
        return guobie;
    }

    public void setGuobie(String guobie) {
        this.guobie = guobie;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public int getSort_id() {
        return sort_id;
    }

    public void setSort_id(int sort_id) {
        this.sort_id = sort_id;
    }

    public String getFaccode() {
        return faccode;
    }

    public void setFaccode(String faccode) {
        this.faccode = faccode;
    }

    public String getFac_name() {
        return fac_name;
    }

    public void setFac_name(String fac_name) {
        this.fac_name = fac_name;
    }

    public String getFac_status() {
        return fac_status;
    }

    public void setFac_status(String fac_status) {
        this.fac_status = fac_status;
    }
}

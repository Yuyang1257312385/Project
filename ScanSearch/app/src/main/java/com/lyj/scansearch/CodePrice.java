package com.lyj.scansearch;

import org.litepal.crud.DataSupport;

/**
 * Created by yu on 2017/6/14.
 */

public class CodePrice extends DataSupport {
    private String code;
    private String price;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

package com.bjw.ComAssistant.bean.product;

import java.util.ArrayList;

/**
 * Created by Hui on 2016/12/10.
 */

public class ProductListBean {
    private String order_id;
    private String start_date;
    private String end_date;
    private String goods_count;
    private ArrayList<ProductBean> goods_list;

    public ProductListBean() {
    }

    public ProductListBean(String order_id, String start_date,
                           String end_date, String goods_count, ArrayList<ProductBean> goods_list) {
        this.order_id = order_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.goods_count = goods_count;
        this.goods_list = goods_list;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getGoods_count() {
        return goods_count;
    }

    public void setGoods_count(String goods_count) {
        this.goods_count = goods_count;
    }

    public ArrayList<ProductBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(ArrayList<ProductBean> goods_list) {
        this.goods_list = goods_list;
    }

    @Override
    public String toString() {
        return "ProductListBean{" +
                "order_id='" + order_id + '\'' +
                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", goods_count='" + goods_count + '\'' +
                ", goods_list=" + goods_list +
                '}';
    }
}

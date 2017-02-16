package com.bjw.ComAssistant.bean.product;

/**
 * Created by Hui on 2016/12/10.
 */

public class ProductBean {
    private String line_num;//商品列表行号
    private String goods_id;//商品id
    private String goods_name;//商品名称
    private String customer_id;//采购商id
    private String customer_store_name;//采购商店铺名称
    private String unit_id;//商品单位id
    private String quantity;//商品下单量
    private String quantity_unit;//商品下单量单位
    private String quantity_real;//商品分拣实际重量
    private String quantity_real_unit;//商品分拣实际重量单位
    private String remark;//留言

    public ProductBean() {
    }

    public ProductBean(String line_num, String goods_id, String goods_name, String customer_id,
                       String customer_store_name, String unit_id, String quantity, String quantity_unit, String quantity_real, String quantity_real_unit, String remark) {
        this.line_num = line_num;
        this.goods_id = goods_id;
        this.goods_name = goods_name;
        this.customer_id = customer_id;
        this.customer_store_name = customer_store_name;
        this.unit_id = unit_id;
        this.quantity = quantity;
        this.quantity_unit = quantity_unit;
        this.quantity_real = quantity_real;
        this.quantity_real_unit = quantity_real_unit;
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLine_num() {
        return line_num;
    }

    public void setLine_num(String line_num) {
        this.line_num = line_num;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_store_name() {
        return customer_store_name;
    }

    public void setCustomer_store_name(String customer_store_name) {
        this.customer_store_name = customer_store_name;
    }

    public String getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(String unit_id) {
        this.unit_id = unit_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getQuantity_unit() {
        return quantity_unit;
    }

    public void setQuantity_unit(String quantity_unit) {
        this.quantity_unit = quantity_unit;
    }

    public String getQuantity_real() {
        return quantity_real;
    }

    public void setQuantity_real(String quantity_real) {
        this.quantity_real = quantity_real;
    }

    public String getQuantity_real_unit() {
        return quantity_real_unit;
    }

    public void setQuantity_real_unit(String quantity_real_unit) {
        this.quantity_real_unit = quantity_real_unit;
    }

    @Override
    public String toString() {
        return "ProductBean{" +
                "line_num='" + line_num + '\'' +
                ", goods_id='" + goods_id + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", customer_id='" + customer_id + '\'' +
                ", customer_store_name='" + customer_store_name + '\'' +
                ", unit_id='" + unit_id + '\'' +
                ", quantity='" + quantity + '\'' +
                ", quantity_unit='" + quantity_unit + '\'' +
                ", quantity_real='" + quantity_real + '\'' +
                ", quantity_real_unit='" + quantity_real_unit + '\'' +
                ", remark='" + remark + '\'' +
                '}' + "\n";
    }
}

package com.bjw.ComAssistant.bean;

/**
 * Created by Hui on 2016/11/12.
 */

public class DetailBean {
    private String num;
    private String name;
    private String weight;
    private String read = "";

    public DetailBean() {
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    @Override
    public String toString() {
        return "DetailBean{" +
                "num='" + num + '\'' +
                ", name='" + name + '\'' +
                ", weight='" + weight + '\'' +
                ", read='" + read + '\'' +
                '}' + "\n";
    }
}

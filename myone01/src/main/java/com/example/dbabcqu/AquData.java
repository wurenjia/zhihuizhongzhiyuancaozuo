package com.example.dbabcqu;

import org.litepal.crud.LitePalSupport;

public class AquData extends LitePalSupport{
    //定义表格式
    private int id;
    private String time;
    private float lightdata;
    private float soilmstdata;
    private float soiltempdata;
    private float airmstdata;
    private float airtempdata;

    //定义数据获取和存储方法
    //id
    public void setId(int id) {
        this.id = id;
    }

    public int getId(){
        return id;
    }
    //time时间坐标
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    //lightdata光照强度

    public float getLightdata() {
        return lightdata;
    }

    public void setLightdata(float lightdata) {
        this.lightdata = lightdata;
    }

    //soilmstdata土壤湿度

    public void setSoilmstdata(float soilmstdata) {
        this.soilmstdata = soilmstdata;
    }

    public float getSoilmstdata() {
        return soilmstdata;
    }

    //soiltempdata土壤温度

    public float getSoiltempdata() {
        return soiltempdata;
    }

    public void setSoiltempdata(float soiltempdata) {
        this.soiltempdata = soiltempdata;
    }


    //airmstdata空气湿度

    public float getAirmstdata() {
        return airmstdata;
    }

    public void setAirmstdata(float airmstdata) {
        this.airmstdata = airmstdata;
    }

    //airtempdata空气温度

    public float getAirtempdata() {
        return airtempdata;
    }

    public void setAirtempdata(float airtempdata) {
        this.airtempdata = airtempdata;
    }
}


package com.example.h_flory.model;

public class DataModel {

    private String title,detail;
    private int status;
    private int image;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getStatus() {
        return status;
    }

    public DataModel(String title, String detail, int status, int image) {
        this.title = title;
        this.detail = detail;
        this.status = status;
        this.image = image;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataModel(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }

    public DataModel(String title, String detail, int status) {
        this.title = title;
        this.detail = detail;
        this.status = status;
    }
}

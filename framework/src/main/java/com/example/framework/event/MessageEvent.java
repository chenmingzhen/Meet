package com.example.framework.event;

/**
 * FileName:MessageEvent
 * Create Date:2020/2/4 21:07
 * Profile:
 */
public class MessageEvent {
    private int type;

    //文本消息
    private String userId;  //每个消息都应该持有userId
    private String text;

    //图片消息
    private String imgUrl;

    //位置消息
    private double la;
    private double lo;
    private String address;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public double getLa() {
        return la;
    }

    public void setLa(double la) {
        this.la = la;
    }

    public double getLo() {
        return lo;
    }

    public void setLo(double lo) {
        this.lo = lo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public MessageEvent(int type) {
        this.type = type;
    }

}

package com.example.framework.event;

/**
 * FileName:MessageEvent
 * Create Date:2020/2/4 21:07
 * Profile:
 */
public class MessageEvent {
    private int type;

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

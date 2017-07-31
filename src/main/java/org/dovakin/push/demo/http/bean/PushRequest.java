package org.dovakin.push.demo.http.bean;

/**
 * Created by liuhuanchao on 2017/7/25.
 */
public class PushRequest {

    private int eventType;

    private PushMessage message;

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public PushMessage getMessage() {
        return message;
    }

    public void setMessage(PushMessage message) {
        this.message = message;
    }
}

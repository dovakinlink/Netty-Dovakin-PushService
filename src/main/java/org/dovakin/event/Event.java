package org.dovakin.event;

import org.dovakin.message.BaseMessage;

/**
 * Created by liuhuanchao on 2017/7/25.
 */
public class Event<T extends BaseMessage> {

    private int type;

    private T content;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}

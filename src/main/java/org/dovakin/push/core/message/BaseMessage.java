package org.dovakin.push.core.message;


/**
 * Created by liuhuanchao on 2017/7/24.
 */
public class BaseMessage<E extends MessageType, T> {

    private String clientId;
    private E messagetype;
    private T content;

    public BaseMessage(E messagetype, T content){
        setMessagetype(messagetype);
        setContent(content);
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public E getMessagetype() {
        return messagetype;
    }

    public void setMessagetype(E messagetype) {
        this.messagetype = messagetype;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}

package test.demo.http.bean;


import test.demo.message.BaseMessage;
import test.demo.message.MessageType;

/**
 * Created by liuhuanchao on 2017/7/24.
 */
public class PushMessage extends BaseMessage<MessageType, PushContent> {

    public PushMessage(MessageType messagetype, PushContent content) {
        super(messagetype, content);
    }
}

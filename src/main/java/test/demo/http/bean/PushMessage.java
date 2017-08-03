package test.demo.http.bean;

import org.dovakin.push.core.message.BaseMessage;
import org.dovakin.push.core.message.MessageType;

/**
 * Created by liuhuanchao on 2017/7/24.
 */
public class PushMessage extends BaseMessage<MessageType, PushContent> {

    public PushMessage(MessageType messagetype, PushContent content) {
        super(messagetype, content);
    }
}

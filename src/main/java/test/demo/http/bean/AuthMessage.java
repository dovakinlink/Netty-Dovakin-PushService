package test.demo.http.bean;

import org.dovakin.push.core.message.BaseMessage;
import org.dovakin.push.core.message.MessageType;

/**
 * Created by liuhuanchao on 2017/7/24.
 */
public class AuthMessage extends BaseMessage<MessageType, AuthContent> {



    public AuthMessage(MessageType messagetype, AuthContent content) {
        super(messagetype, content);
    }
}

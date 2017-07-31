package org.dovakin.push.demo.http.bean;

import org.dovakin.push.core.message.BaseMessage;

/**
 * Created by liuhuanchao on 2017/7/24.
 */
public class AuthMessage extends BaseMessage<MessageType, AuthContent> {



    public AuthMessage(MessageType messagetype, AuthContent content) {
        super(messagetype, content);
    }
}

package org.dovakin.message;

import org.dovakin.message.bean.AuthContent;

/**
 * Created by liuhuanchao on 2017/7/24.
 */
public class AuthMessage extends BaseMessage<MessageType, AuthContent> {



    public AuthMessage(MessageType messagetype, AuthContent content) {
        super(messagetype, content);
    }
}

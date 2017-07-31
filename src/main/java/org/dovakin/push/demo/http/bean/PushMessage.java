package org.dovakin.push.demo.http.bean;

import org.dovakin.push.core.message.BaseMessage;

/**
 * Created by liuhuanchao on 2017/7/24.
 */
public class PushMessage extends BaseMessage<MessageType, PushContent> {

    public PushMessage(MessageType messagetype, PushContent content) {
        super(messagetype, content);
    }
}

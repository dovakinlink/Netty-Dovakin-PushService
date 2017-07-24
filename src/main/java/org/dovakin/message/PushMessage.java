package org.dovakin.message;

import org.dovakin.message.bean.PushContent;

/**
 * Created by liuhuanchao on 2017/7/24.
 */
public class PushMessage extends BaseMessage<MessageType, PushContent> {

    public PushMessage(MessageType messagetype, PushContent content) {
        super(messagetype, content);
    }
}

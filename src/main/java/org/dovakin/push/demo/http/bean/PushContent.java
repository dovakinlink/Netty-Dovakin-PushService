package org.dovakin.push.demo.http.bean;

/**
 * Created by liuhuanchao on 2017/7/24.
 */
public class PushContent {

    // 未读消息个数
    private int notifyCount;
    // 推送标题
    private String pushTitle;
    // 推送内容
    private String pushMessage;

    public int getNotifyCount() {
        return notifyCount;
    }

    public void setNotifyCount(int notifyCount) {
        this.notifyCount = notifyCount;
    }

    public String getPushTitle() {
        return pushTitle;
    }

    public void setPushTitle(String pushTitle) {
        this.pushTitle = pushTitle;
    }

    public String getPushMessage() {
        return pushMessage;
    }

    public void setPushMessage(String pushMessage) {
        this.pushMessage = pushMessage;
    }

    @Override
    public String toString(){
        return  "{" +
                "\"notifyCount\":" + notifyCount + ","
                + "\"pushTitle\":\"" + pushTitle + "\","
                + "\"pushMessage\":\"" + pushMessage + "\"}";
    }
}

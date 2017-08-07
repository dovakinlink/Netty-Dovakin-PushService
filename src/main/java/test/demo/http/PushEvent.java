package test.demo.http;

/**
 * Created by liuhuanchao on 2017/8/7.
 */
public class PushEvent {
    private int eventType;
    private Message message;


    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public class Message{
        private String clientId;
        private Content content;

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public Content getContent() {
            return content;
        }

        public void setContent(Content content) {
            this.content = content;
        }
    }

    public class Content{
        private int notifyCount;
        private String pushTitle;
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
    }
}

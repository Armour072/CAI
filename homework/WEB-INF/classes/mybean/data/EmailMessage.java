package mybean.data;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class EmailMessage {
    private String sender;
    private String recipient;
    private String topic;
    private String sendTime;
    private String message;
    private int mailSerial;
    private boolean cancelSign;

    public EmailMessage(String sender, String recipient, String topic, Timestamp sendTime, String message, int mailSerial, boolean cancelSign) {
        this.sender = sender;
        this.recipient = recipient;
        this.topic = topic;
        this.sendTime = convertTimestampToString(sendTime);
        this.message = message;
        this.mailSerial = mailSerial;
        this.cancelSign = cancelSign;
    }
       public EmailMessage() {
        // Default constructor
    }
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = convertTimestampToString(sendTime);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMailSerial() {
        return mailSerial;
    }

    public void setMailSerial(int mailSerial) {
        this.mailSerial = mailSerial;
    }

    public boolean isCancelSign() {
        return cancelSign;
    }

    public void setCancelSign(boolean cancelSign) {
        this.cancelSign = cancelSign;
    }

    private String convertTimestampToString(Timestamp timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(timestamp);
    }

    
}

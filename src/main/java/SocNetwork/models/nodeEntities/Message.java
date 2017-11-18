package SocNetwork.models.nodeEntities;

import SocNetwork.models.enums.ContentType;
import SocNetwork.models.enums.MessageStatus;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

import java.util.Date;


@NodeEntity
public class Message implements Comparable<Message>{

    @GraphId
    private Long id;

    @Property
    private Long senderId;

    @Property
    private Long recipientId;

    @Property
    private Date datetime;

    @Property
    private MessageStatus status;

    @Property
    private byte[] data;

    @Property
    private ContentType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public ContentType getType() {
        return type;
    }

    public void setType(ContentType type) {
        this.type = type;
    }

    @Override
    public int compareTo(Message message) {

        return  this.getDatetime().after(message.getDatetime())  ?  1 :
                this.getDatetime().before(message.getDatetime()) ? -1 : 0;
    }
}

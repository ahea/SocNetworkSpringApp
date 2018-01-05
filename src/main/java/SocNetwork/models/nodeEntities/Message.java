package SocNetwork.models.nodeEntities;

import SocNetwork.models.enums.MessageDataType;
import SocNetwork.models.enums.MessageType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

import java.util.Date;


@NodeEntity
@JsonTypeInfo(
        use =       JsonTypeInfo.Id.NAME,
        include =   JsonTypeInfo.As.EXTERNAL_PROPERTY,
        property =  "messageType",
        visible =   true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Message.class, name = "Plain"),
        @JsonSubTypes.Type(value = Comment.class, name = "Comment")
})
public class Message implements Comparable<Message> {

    @GraphId
    private Long id;

    @Property
    private Long senderId;

    @Property
    private Date datetime;

    @Property
    private MessageType messageType;

    @Property
    private MessageDataType messageDataType;

    @Property
    private byte[] data;

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

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public MessageDataType getMessageDataType() {
        return messageDataType;
    }

    public void setMessageDataType(MessageDataType messageDataType) {
        this.messageDataType = messageDataType;
    }

    @Override
    public int compareTo(Message message) {

        return  this.getDatetime().after(message.getDatetime())  ?  1 :
                this.getDatetime().before(message.getDatetime()) ? -1 : 0;
    }
}

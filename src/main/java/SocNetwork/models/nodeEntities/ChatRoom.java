package SocNetwork.models.nodeEntities;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.*;

/**
 * Created by aleksei on 29.04.17.
 */

@NodeEntity
public class ChatRoom {

    @GraphId
    private Long id;

    @Relationship(type = "HAS_MESSAGE", direction = Relationship.OUTGOING)
    private List<Message> messages = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}

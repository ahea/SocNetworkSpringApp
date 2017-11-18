package SocNetwork.models.nodeEntities;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.*;


@NodeEntity
public class ChatRoom {

    @GraphId
    private Long id;

    @Relationship(type = "HAS_MESSAGE", direction = Relationship.OUTGOING)
    private SortedSet<Message> messages = new TreeSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SortedSet<Message> getMessages() {
        return messages;
    }

    public void setMessages(SortedSet<Message> messages) {
        this.messages = messages;
    }
}

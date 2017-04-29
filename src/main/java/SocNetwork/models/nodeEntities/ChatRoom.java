package SocNetwork.models.nodeEntities;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by aleksei on 29.04.17.
 */

@NodeEntity
public class ChatRoom {

    @GraphId
    private Long id;

    @Relationship(type = "HAS_MESSAGE", direction = "OUTGOING")
    private Set<Message> messages = new LinkedHashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

}

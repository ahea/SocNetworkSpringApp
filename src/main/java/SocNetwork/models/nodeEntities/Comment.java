package SocNetwork.models.nodeEntities;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity
public class Comment extends Message {

    @Property
    private Long parentId;

    @Property
    private Integer startPosition;

    @Property
    private Integer endPosition;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Integer startPosition) {
        this.startPosition = startPosition;
    }

    public Integer getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(Integer endPosition) {
        this.endPosition = endPosition;
    }
}

package SocNetwork.models.relationshipEntities;

import SocNetwork.models.nodeEntities.Language;
import SocNetwork.models.nodeEntities.User;
import SocNetwork.models.enums.LanguageLevel;
import org.neo4j.ogm.annotation.*;

/**
 * Created by aleksei on 25.04.17.
 */
@RelationshipEntity(type = "HAS_LANGUAGE")
public class UserHasLanguage {

    @GraphId
    private Long relationshipId;
    @StartNode
    private User user;
    @EndNode
    private Language language;
    @Property
    private LanguageLevel level;

    public long getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(long relationshipId) {
        this.relationshipId = relationshipId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public LanguageLevel getLevel() {
        return level;
    }

    public void setLevel(LanguageLevel level) {
        this.level = level;
    }
}

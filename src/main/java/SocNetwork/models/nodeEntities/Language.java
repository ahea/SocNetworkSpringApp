package SocNetwork.models.nodeEntities;

import SocNetwork.models.enums.LanguageName;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/**
 * Created by aleksei on 24.04.17.
 */

@NodeEntity
public class Language {

    @GraphId
    private Long id;

    @Property
    private LanguageName languageName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LanguageName getLanguageName() {
        return languageName;
    }

    public void setLanguageName(LanguageName languageName) {
        this.languageName = languageName;
    }
}

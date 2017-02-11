package SocNetwork.models;

import SocNetwork.models.enums.Country;
import SocNetwork.models.enums.Gender;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Collection;

/**
 * Created by aleksei on 11.02.17.
 */

@NodeEntity
public class User {

    @GraphId
    private Long id;

    @Property
    private String name;

    @Property
    private String email;

    @Property
    private String password;

    @Property
    private String photoLink;

    @Property
    private Integer age;

    @Property
    private Country country;

    @Property
    private Gender gender;

    @Property
    private String about;

    @Property
    private boolean online;

    @Relationship(type="USER_ROLE", direction=Relationship.UNDIRECTED)
    Collection<Role> roles;

    @Relationship(type="HAS_IN_FRIENDLIST", direction=Relationship.UNDIRECTED)
    private Collection<User> friendList;

    @Relationship(type="HAS_IN_BLACKLIST", direction=Relationship.OUTGOING)
    private Collection<User> blackList;

}

package SocNetwork.models.nodeEntities;

import SocNetwork.models.enums.Country;
import SocNetwork.models.enums.Gender;
import SocNetwork.models.relationshipEntities.UserHasLanguage;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

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

    private Set<UserHasLanguage> hasLanguage = new HashSet<>();

    @Relationship(type="IN_CHATROOM", direction=Relationship.OUTGOING)
    private Set<ChatRoom> chatRooms = new HashSet<>();

    @Relationship(type="HAS_ROLE", direction=Relationship.OUTGOING)
    private Set<Role> roles = new HashSet<>();

    @Relationship(type="HAS_IN_FRIENDLIST", direction=Relationship.OUTGOING)
    private Set<User> friendList = new HashSet<>();

    @Relationship(type="HAS_IN_BLACKLIST", direction=Relationship.OUTGOING)
    private Set<User> blackList = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public Set<UserHasLanguage> getHasLanguage() {
        return hasLanguage;
    }

    public void setHasLanguage(Set<UserHasLanguage> hasLanguage) {
        this.hasLanguage = hasLanguage;
    }

    public Set<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    public void setChatRooms(Set<ChatRoom> chatRooms) {
        this.chatRooms = chatRooms;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<User> getFriendList() {
        return friendList;
    }

    public void setFriendList(Set<User> friendList) {
        this.friendList = friendList;
    }

    public Set<User> getBlackList() {
        return blackList;
    }

    public void setBlackList(Set<User> blackList) {
        this.blackList = blackList;
    }

    public User hideRelationships(){
        this.friendList = null;
        this.blackList = null;
        return this;
    }

    public User hideCredentials(){
        this.email = null;
        this.password = null;
        return this;
    }
}

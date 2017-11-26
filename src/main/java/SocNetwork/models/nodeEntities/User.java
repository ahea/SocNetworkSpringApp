package SocNetwork.models.nodeEntities;

import SocNetwork.models.enums.Country;
import SocNetwork.models.enums.Gender;
import SocNetwork.models.enums.LanguageLevel;
import SocNetwork.models.enums.LanguageName;
import SocNetwork.models.relationshipEntities.UserHasLanguage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


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

    @JsonIgnore
    private Set<UserHasLanguage> hasLanguage = new HashSet<>();

    @Transient
    private Map<LanguageName, LanguageLevel> languages;

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

    @JsonIgnore
    public Set<UserHasLanguage> getHasLanguage() {
        return hasLanguage;
    }

    @JsonIgnore
    public void setHasLanguage(Set<UserHasLanguage> hasLanguage) {
        this.hasLanguage = hasLanguage;
    }

    public Map<LanguageName, LanguageLevel> getLanguages() {
        return languages;
    }

    public void setLanguages(Map<LanguageName, LanguageLevel> languages) {
        this.languages = languages;
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

    public User hideCredentials() {
        this.email = null;
        this.password = null;
        return this;
    }

    public User hideChatRooms() {
        this.chatRooms = null;
        return this;
    }

    public User hideBlackList() {
        this.blackList = null;
        return this;
    }

    @Override
    public String toString() {

        return  "<------- USER INFORMATION ------->" +
                "\n[id]         " +  this.id +
                "\n[name]       " +  this.name +
                "\n[email]      " +  this.email +
                "\n[photoLink]  " +  this.photoLink +
                "\n[age]        " +  this.age +
                "\n[country]    " +  this.country +
                "\n[gender]     " +  this.gender +
                "\n[about]      " +  this.about +
                "\n[online]     " +  this.online +
                "\n[languages]  " +  this.languages +
                "\n<-------------------------------->";
    }

}

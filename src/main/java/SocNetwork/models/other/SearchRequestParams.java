package SocNetwork.models.other;

import SocNetwork.models.enums.Country;
import SocNetwork.models.enums.Gender;
import SocNetwork.models.enums.LanguageLevel;
import SocNetwork.models.enums.LanguageName;

import java.util.HashMap;
import java.util.Map;


public class SearchRequestParams {

    private Map<LanguageName, LanguageLevel> languages = new HashMap<>();
    private Country country;
    private Integer age;
    private Gender gender;
    private boolean hasPhoto;
    private boolean online;

    public Map<LanguageName, LanguageLevel> getLanguages() {
        return languages;
    }

    public void setLanguages(Map<LanguageName, LanguageLevel> languages) {
        this.languages = languages;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean isHasPhoto() {
        return hasPhoto;
    }

    public void setHasPhoto(boolean hasPhoto) {
        this.hasPhoto = hasPhoto;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}

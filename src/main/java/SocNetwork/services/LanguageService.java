package SocNetwork.services;

import SocNetwork.models.enums.LanguageLevel;
import SocNetwork.models.enums.LanguageName;
import SocNetwork.models.nodeEntities.User;

import java.util.Map;

/**
 * Created by aleksei on 05.05.17.
 */

public interface LanguageService {

    void updateLanguages(User user, Map<LanguageName, LanguageLevel> languages);

    Map<LanguageName, LanguageLevel> getLanguages(User user);

}

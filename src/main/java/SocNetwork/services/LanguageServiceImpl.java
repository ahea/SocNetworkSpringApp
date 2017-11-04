package SocNetwork.services;

import SocNetwork.models.enums.LanguageLevel;
import SocNetwork.models.enums.LanguageName;
import SocNetwork.models.nodeEntities.Language;
import SocNetwork.models.nodeEntities.User;
import SocNetwork.models.queryResults.LanguageResult;
import SocNetwork.models.relationshipEntities.UserHasLanguage;
import SocNetwork.repositories.LanguageRepository;
import SocNetwork.repositories.UserHasLanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.template.Neo4jTemplate;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class LanguageServiceImpl implements LanguageService {

    private LanguageRepository languageRepository;
    private UserHasLanguageRepository userHasLanguageRepository;

    @Autowired
    public void setLanguageRepository(LanguageRepository languageRepository){
        this.languageRepository = languageRepository;
    }

    @Autowired
    public void setUserHasLanguageRepository(UserHasLanguageRepository userHasLanguageRepository){
        this.userHasLanguageRepository = userHasLanguageRepository;
    }

    @Override
    public void updateLanguages(User user, Map<LanguageName, LanguageLevel> languages){

        userHasLanguageRepository.deleteByUserId(user.getId());

        for (LanguageName name : languages.keySet()){
            Language lang = languageRepository.findByLanguageName(name);
            UserHasLanguage rel = new UserHasLanguage();
            rel.setLevel(languages.get(name));
            rel.setUser(user);
            rel.setLanguage(lang);
            userHasLanguageRepository.save(rel);
        }

    }

    @Override
    public Map<LanguageName, LanguageLevel> getLanguages(User user){
        Collection<LanguageResult> result =
                userHasLanguageRepository.findLanguagesAndLevels(user.getId());
        HashMap<LanguageName, LanguageLevel> map = new HashMap<>();
        for (LanguageResult element : result){
            map.put(element.name, element.level);
        }
        return map;
    }
}

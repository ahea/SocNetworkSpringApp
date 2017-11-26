package SocNetwork.services;

import SocNetwork.models.enums.LanguageLevel;
import SocNetwork.models.enums.LanguageName;
import SocNetwork.models.nodeEntities.Language;
import SocNetwork.models.nodeEntities.User;
import SocNetwork.models.queryResults.LanguageResult;
import SocNetwork.models.relationshipEntities.UserHasLanguage;
import SocNetwork.repositories.LanguageRepository;
import SocNetwork.repositories.UserHasLanguageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.template.Neo4jTemplate;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class LanguageServiceImpl implements LanguageService {

    private final Logger logger = LoggerFactory.getLogger(LanguageServiceImpl.class);

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

        int y = 2;

        User user2 = user;

        userHasLanguageRepository.deleteByUserId(user.getId());
        logger.info("[updateLanguages] Updating languages: [userId] " + user.getId() +
                " [languages] " + languages);

        for (LanguageName name : languages.keySet()){
            Language lang = languageRepository.findByLanguageName(name);
            UserHasLanguage rel = new UserHasLanguage();
            rel.setLevel(languages.get(name));
            rel.setUser(user);
            rel.setLanguage(lang);
            rel = userHasLanguageRepository.save(rel);
            logger.info("[updateLanguages] New userHasLanguage relationship persisted: " +
                    "[relId] " + rel.getRelationshipId() + " [userId] " + user.getId() +
                    " [languageName] " + lang.getLanguageName());
        }

    }

    @Override
    public Map<LanguageName, LanguageLevel> getLanguagesByUserId(Long id){

        Collection<LanguageResult> languagesAndLevels = userHasLanguageRepository.findLanguagesAndLevels(id);
        HashMap<LanguageName, LanguageLevel> languagesMapping = new HashMap<>();
        for (LanguageResult languageAndLevel : languagesAndLevels) {
            languagesMapping.put(languageAndLevel.name, languageAndLevel.level);
        }
        logger.info("[getLanguagesByUserId] [userId] " + id + " [languagesMapping] " + languagesMapping);
        return languagesMapping;
    }
}

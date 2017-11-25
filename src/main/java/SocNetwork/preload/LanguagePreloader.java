package SocNetwork.preload;

import SocNetwork.models.nodeEntities.Language;
import SocNetwork.models.enums.LanguageName;
import SocNetwork.repositories.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class LanguagePreloader implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private LanguageRepository languageRepository;


    @Autowired
    public void setLanguageRepository(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.info("Preload started: [Languages]");
        for (int i = 0; i < LanguageName.values().length; i++) {
            createLanguage(LanguageName.values()[i]);
        }
        logger.info("Preload finished: [Languages]");
    }

    @Transactional
    private Language createLanguage(LanguageName name) {
        Language lang = languageRepository.findByLanguageName(name);
        if (lang == null){
            lang = new Language();
            lang.setLanguageName(name);
            languageRepository.save(lang);
            logger.info("[Languages] Created: " + name);
        } else {
            logger.info("[Languages] Already exists: " + name);
        }
        return lang;
    }

}

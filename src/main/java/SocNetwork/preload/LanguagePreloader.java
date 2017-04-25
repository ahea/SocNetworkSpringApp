package SocNetwork.preload;

import SocNetwork.models.Language;
import SocNetwork.models.enums.LanguageName;
import SocNetwork.repositories.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by aleksei on 24.04.17.
 */
@Component
public class LanguagePreloader implements ApplicationListener<ContextRefreshedEvent> {

    private LanguageRepository languageRepository;

    @Autowired
    public void setLanguageRepository(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        for (int i = 0; i < LanguageName.values().length; i++)
            createLanguage(LanguageName.values()[i]);
    }

    @Transactional
    private Language createLanguage(LanguageName name) {
        Language lang = languageRepository.findByLanguageName(name);
        if (lang == null){
            lang = new Language();
            lang.setLanguageName(name);
            languageRepository.save(lang);
        }
        return lang;
    }

}

package SocNetwork.services;

import SocNetwork.models.enums.LanguageLevel;
import SocNetwork.models.enums.LanguageName;
import SocNetwork.models.nodeEntities.User;
import SocNetwork.models.other.SearchRequestParams;
import SocNetwork.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by aleksei on 06.05.17.
 */

@Service
public class SearchServiceImpl implements SearchService {

    private UserRepository userRepository;
    private LanguageService languageService;

    @Autowired
    public void setUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Autowired
    public void setLanguageService (LanguageService languageService){
        this.languageService = languageService;
    }

    @Override
    public List<User> searchUsersByParams(SearchRequestParams params, int offset, int count){
        List<User> result = new ArrayList<>();
        Map<LanguageName, LanguageLevel> langParam = params.getLanguages();
        for (User user : userRepository.findAll()){
            Map<LanguageName, LanguageLevel> userLangs = languageService.getLanguages(user);
            user.setLanguages(userLangs);
            boolean hasLangParams = true;
            for (LanguageName langNameParam : langParam.keySet())
                if (!userLangs.keySet().contains(langNameParam) ||
                        !langParam.get(langNameParam).equals(userLangs.get(langNameParam)))
                    hasLangParams = false;
            if ((langParam == null || hasLangParams) &&
                    (params.getCountry() == null || params.getCountry() == user.getCountry()) &&
                    (params.getGender() == null || params.getGender() == user.getGender()) &&
                    (params.getAge() == null || params.getAge() == user.getAge()) &&
                    (user.getPhotoLink() != null || !params.isHasPhoto()) &&
                    (user.isOnline() || !params.isOnline()))
                result.add(user.hideCredentials().hideRelationships());
        }
        if (offset + count > result.size())
            return result.subList(offset, result.size());
        else
            return result.subList(offset, offset + count);
    }

}

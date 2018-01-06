package SocNetwork.services;

import SocNetwork.models.enums.LanguageLevel;
import SocNetwork.models.enums.LanguageName;
import SocNetwork.models.nodeEntities.User;
import SocNetwork.models.other.SearchRequestParams;
import SocNetwork.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


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
    public List<User> searchUsersByParams(SearchRequestParams params, Integer offset, Integer count) {

        //Search certain person
        if (params.getId() != null) {
            User user = userRepository.findOne(params.getId());
            if (user != null) {
                user
                    .hideCredentials()
                    .hideRelationships()
                    .hideBlackList()
                    .hideChatRooms();
            }
            return Arrays.asList(user);
        }
        if (params.getEmail() != null) {
            User user = userRepository.findByEmail(params.getEmail());
            if (user != null) {
                user
                        .hideCredentials()
                        .hideRelationships()
                        .hideBlackList()
                        .hideChatRooms();
            }
            return Arrays.asList(user);
        }
        if (params.getId() != null) {
            List<User> result = userRepository.findByName(params.getName());
            for (User user : result) {
                user
                    .hideCredentials()
                    .hideRelationships()
                    .hideBlackList()
                    .hideChatRooms();
            }
            return result;
        }

        //Search by parameters
        Comparator<User> comparator = new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {

                return u1.isOnline() && !u2.isOnline()  ? -1 :
                        !u1.isOnline() && u2.isOnline() ?  1 :
                        params.getCountry() != null && params.getCountry() == u1.getCountry() && params.getCountry() != u2.getCountry() ? -1 :
                        params.getCountry() != null && params.getCountry() == u2.getCountry() && params.getCountry() != u1.getCountry() ?  1 :
                        params.getAge() != null && (
                                u1.getAge() != null && u2.getAge() == null ||
                                u1.getAge() != null && u2.getAge() != null && Math.abs(params.getAge() - u1.getAge()) < Math.abs(params.getAge() - u2.getAge())) ? -1 :
                        params.getAge() != null && (
                                u2.getAge() != null && u1.getAge() == null ||
                                u1.getAge() != null && u2.getAge() != null && Math.abs(params.getAge() - u1.getAge()) > Math.abs(params.getAge() - u2.getAge())) ?  1 :
                        params.getGender() != null && params.getGender() == u1.getGender() && params.getGender() != u2.getGender()      ? -1 :
                        params.getGender() != null && params.getGender() == u2.getGender() && params.getGender() != u1.getGender()      ?  1 :
                        u1.getPhotoLink() != null && u2.getPhotoLink() == null ? -1 :
                        u2.getPhotoLink() != null && u1.getPhotoLink() == null ?  1 : 0;
            }
        };
        ArrayList<User> result = new ArrayList<>();
        Map<LanguageName, LanguageLevel> langParam = params.getLanguages();

        for (LanguageName langName : langParam.keySet()) {
            for (User speaker : userRepository.findSpeakers(langName.toString(), langParam.get(langName).toString())) {
                Map<LanguageName, LanguageLevel> speakerLangs = languageService.getLanguagesByUserId(speaker.getId());
                speaker.setLanguages(speakerLangs);
                result.add(speaker
                        .hideCredentials()
                        .hideRelationships()
                        .hideBlackList()
                        .hideChatRooms());
            }
        }

        Collections.sort(result, comparator);

        return result.subList(
                Math.min(result.size(), Math.max(0, offset)),
                Math.min(result.size(), offset + count)
        );
    }
}

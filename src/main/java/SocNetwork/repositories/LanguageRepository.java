package SocNetwork.repositories;

import SocNetwork.models.nodeEntities.Language;
import SocNetwork.models.enums.LanguageName;
import org.springframework.data.neo4j.repository.GraphRepository;


public interface LanguageRepository extends GraphRepository<Language> {

    Language findByLanguageName(LanguageName name);

}

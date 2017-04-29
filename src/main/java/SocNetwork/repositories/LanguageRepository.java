package SocNetwork.repositories;

import SocNetwork.models.nodeEntities.Language;
import SocNetwork.models.enums.LanguageName;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by aleksei on 24.04.17.
 */
public interface LanguageRepository extends GraphRepository<Language> {

    Language findByLanguageName(LanguageName name);

}

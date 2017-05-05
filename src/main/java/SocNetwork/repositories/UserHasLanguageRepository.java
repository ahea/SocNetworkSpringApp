package SocNetwork.repositories;

import SocNetwork.models.queryResults.LanguageResult;
import SocNetwork.models.relationshipEntities.UserHasLanguage;
import SocNetwork.services.LanguageServiceImpl;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;


/**
 * Created by aleksei on 05.05.17.
 */
public interface UserHasLanguageRepository extends GraphRepository<UserHasLanguage> {


    @Query("MATCH (a:User)-[r:HAS_LANGUAGE]->(b:Language) " +
            "WHERE id(a) = {id} " +
            "DELETE r")
    void deleteByUserId(@Param("id") Long id);

    @Query("MATCH (a:User)-[r:HAS_LANGUAGE]->(b:Language)\n" +
            "WHERE id(a) = {id}\n" +
            "RETURN r.level as level, b.languageName as name")
    Collection<LanguageResult> findLanguagesAndLevels(@Param("id") Long id);

}

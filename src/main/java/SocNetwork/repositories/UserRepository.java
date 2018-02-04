package SocNetwork.repositories;

import SocNetwork.models.nodeEntities.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;


public interface UserRepository extends GraphRepository<User> {

    User findByEmail(String email);

    @Query("MATCH (a:User)-[r:HAS_IN_FRIENDLIST]->(b:User) " +
            "WHERE id(b) = {userId} " +
            "RETURN a")
    Collection<User> getIncomingFriendsById (@Param("userId") Long id);

    List<User> findByName(String name);

    @Query("MATCH (a:User)-[r:HAS_LANGUAGE]->(b:Language) " +
            "WHERE b.languageName = {languageName} " +
            "AND   (r.level = {languageLevel} OR {languageLevel} = 'NONE') " +
            "RETURN a")
    Collection<User> findSpeakers(@Param("languageName")  String languageName,
                                  @Param("languageLevel") String languageLevel);
}

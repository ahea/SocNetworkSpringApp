package SocNetwork.repositories;

import SocNetwork.models.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

/**
 * Created by aleksei on 11.02.17.
 */
public interface UserRepository extends GraphRepository<User> {

    User findByEmail(String email);

    @Query("MATCH (a:User)-[r:HAS_IN_FRIENDLIST]->(b:User) " +
            "WHERE id(a) = {userId} " +
            "RETURN b")
    Collection<User> getFriendList (@Param("userId") Long id);

    @Query("match (a)-[rel1:HAS_IN_FRIENDLIST]->(b)," +
            " (b)-[rel2:HAS_IN_FRIENDLIST]->(a)" +
            " where id(a) = {userId}" +
            " return b")
    Collection<User> getFriends(@Param("userId") Long id);

}

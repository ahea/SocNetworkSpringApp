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

}

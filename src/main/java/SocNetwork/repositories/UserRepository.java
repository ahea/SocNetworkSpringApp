package SocNetwork.repositories;

import SocNetwork.models.nodeEntities.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;


public interface UserRepository extends GraphRepository<User> {

    User findByEmail(String email);


    @Query("MATCH (a:User)-[r:HAS_IN_FRIENDLIST]->(b:User) " +
            "WHERE id(b) = {userId} " +
            "RETURN a")
    Collection<User> getIncomingFriendsById (@Param("userId") Long id);

}

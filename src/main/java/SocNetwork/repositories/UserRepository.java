package SocNetwork.repositories;

import SocNetwork.models.User;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by aleksei on 11.02.17.
 */
public interface UserRepository extends GraphRepository<User> {

    public User findByEmail(String email);

}

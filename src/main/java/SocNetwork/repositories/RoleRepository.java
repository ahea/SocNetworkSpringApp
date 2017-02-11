package SocNetwork.repositories;

import SocNetwork.models.Role;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by aleksei on 11.02.17.
 */
public interface RoleRepository extends GraphRepository<Role> {

    public Role findByName(String name);

}

package SocNetwork.repositories;

import SocNetwork.models.nodeEntities.Role;
import org.springframework.data.neo4j.repository.GraphRepository;


public interface RoleRepository extends GraphRepository<Role> {

    Role findByName(String name);

}

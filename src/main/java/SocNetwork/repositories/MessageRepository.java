package SocNetwork.repositories;

import SocNetwork.models.nodeEntities.Message;
import org.springframework.data.neo4j.repository.GraphRepository;


public interface MessageRepository extends GraphRepository<Message> {

}

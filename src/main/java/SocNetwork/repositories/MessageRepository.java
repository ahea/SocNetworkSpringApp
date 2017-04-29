package SocNetwork.repositories;

import SocNetwork.models.nodeEntities.Message;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by aleksei on 29.04.17.
 */
public interface MessageRepository extends GraphRepository<Message> {

}

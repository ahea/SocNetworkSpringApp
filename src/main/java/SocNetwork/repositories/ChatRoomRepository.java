package SocNetwork.repositories;

import SocNetwork.models.nodeEntities.ChatRoom;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by aleksei on 29.04.17.
 */
public interface ChatRoomRepository extends GraphRepository<ChatRoom> {

}

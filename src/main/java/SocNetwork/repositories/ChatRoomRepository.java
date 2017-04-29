package SocNetwork.repositories;

import SocNetwork.models.nodeEntities.ChatRoom;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

/**
 * Created by aleksei on 29.04.17.
 */
public interface ChatRoomRepository extends GraphRepository<ChatRoom> {

    @Query("MATCH (a:User)-[r1:IN_CHATROOM]->(b:ChatRoom)<-[r2:IN_CHATROOM]-(c:User) " +
            "WHERE id(a) = {id1} and id(c) = {id2} " +
            "RETURN id(b)")
    Collection<Long> findCommon (@Param("id1") Long id1,
                                     @Param("id2") Long id2);

}

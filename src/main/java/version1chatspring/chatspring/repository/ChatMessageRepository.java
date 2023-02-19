package version1chatspring.chatspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import version1chatspring.chatspring.model.Chat;
import version1chatspring.chatspring.model.ChatMessage;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage,Integer> {

}


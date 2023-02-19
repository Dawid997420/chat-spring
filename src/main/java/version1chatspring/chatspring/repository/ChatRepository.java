package version1chatspring.chatspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import version1chatspring.chatspring.model.Chat;
import version1chatspring.chatspring.model.ChatMessage;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat,Integer> {




}

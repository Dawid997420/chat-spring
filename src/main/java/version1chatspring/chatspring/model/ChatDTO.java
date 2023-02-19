package version1chatspring.chatspring.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;

@Data
public class ChatDTO {

    private String chatName ;

    private Integer id ;

    private List<UserD> users ;

    private List<Integer> messagesId;

    public void addMessage(Integer messageId) {
        messagesId.add(messageId);
    }

    public void addUser(UserD userD) {
        users.add(userD);
    }


}

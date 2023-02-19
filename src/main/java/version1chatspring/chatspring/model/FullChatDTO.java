package version1chatspring.chatspring.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Data
public class FullChatDTO {


    public FullChatDTO(String chatName,Integer id, List<UserD> userDList) {
        this.id=id;
        this.chatName = chatName;
       this.users = userDList;
    }
    private String chatName ;

    private Integer id ;

    private List<UserD> users ;

    private List<ChatMessage> messages;

    public void addMessage(ChatMessage message) {
        messages.add(message);
    }


    public void addUser(UserD userD) {
        users.add(userD);
    }



}

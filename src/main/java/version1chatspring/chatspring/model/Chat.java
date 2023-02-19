package version1chatspring.chatspring.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@NoArgsConstructor
@Table(name = "chat")
@Data
@Entity
public class Chat {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;


    private List<Integer> usersId ;

    @Column(length = 20000)
    private List<Integer> messagesId;

    public void addUserToChat (UserD userD) {

        this.usersId.add(userD.getId());
    }

    public void addMessageToChat (Integer messageId) {

        this.messagesId.add(messageId);
    }

}

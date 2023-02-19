package version1chatspring.chatspring.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;


@NoArgsConstructor
@Entity
@Table(name = "chatMessage")
@Data
public class ChatMessage {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
   private Integer id;

   private Integer chatId;

   private Integer authorId;

   private String text ;

   private Date created;
}

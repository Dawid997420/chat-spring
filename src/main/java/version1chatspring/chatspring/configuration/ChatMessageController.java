package version1chatspring.chatspring.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import version1chatspring.chatspring.repository.ChatMessageRepository;


public class ChatMessageController {


    @Autowired
    private ChatMessageRepository chatMessageRepository;







}

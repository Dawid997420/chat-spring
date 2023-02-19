package version1chatspring.chatspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import version1chatspring.chatspring.model.ChatMessage;
import version1chatspring.chatspring.repository.ChatMessageRepository;

import java.util.List;

@RequestMapping("/message")
@RestController
public class ChatMessageController {

    @Autowired
    private ChatMessageRepository chatMessageRepository;


    @GetMapping
    public ResponseEntity<List<ChatMessage>> getChatMessage() {

        return ResponseEntity.ok(chatMessageRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<ChatMessage> addMessage(@RequestBody ChatMessage chatMessage) {

            chatMessageRepository.save(chatMessage);
            return ResponseEntity.ok(chatMessage);
    }


    @DeleteMapping
    public void deleteAllMessages(){

        chatMessageRepository.deleteAll();

    }


}

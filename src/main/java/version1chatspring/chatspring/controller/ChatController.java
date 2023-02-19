package version1chatspring.chatspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import version1chatspring.chatspring.model.Chat;
import version1chatspring.chatspring.model.ChatDTO;
import version1chatspring.chatspring.model.FullChatDTO;
import version1chatspring.chatspring.model.UserD;
import version1chatspring.chatspring.repository.ChatRepository;
import version1chatspring.chatspring.repository.UserDRepository;
import version1chatspring.chatspring.services.ChatService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@RequestMapping("/chat")
@RestController
public class ChatController {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserDRepository userDRepository;


    @GetMapping
    public ResponseEntity<List<Chat>> getAllChats() {

     return  ResponseEntity.ok(chatRepository.findAll());
    }





    @GetMapping("/user/{id}")
    public ResponseEntity<List<ChatDTO>> getAllChatsWithUserId(@PathVariable Integer id) {

       return ResponseEntity.ok(chatService.getAllUserIdChatsDTO(id));

    }

    @PostMapping
    public ResponseEntity<Chat> addChat(@RequestBody Chat chat ) {

        chatRepository.save(chat);

        return ResponseEntity.ok(chat);
    }



    @DeleteMapping
    public void deleteAllChats() {

        chatRepository.deleteAll();
    }

    @DeleteMapping("/{id}")
    public void deleteChatById(@PathVariable Integer id) {

        chatRepository.deleteById(id);

    }

        /*
    @PostMapping("/elo")
    public ResponseEntity<Chat> addChatIfNoExist(@RequestBody Chat chat, Principal principal) {

        UserD userD = userDRepository.findByUsername(principal.getName());


        if (!chatService.existChatByUsersId(chat.getUsersId())) {

            chat.addUserToChat(userD);
            chat.setMessagesId(new ArrayList<>());
            chatRepository.save(chat);
        }
        return  ResponseEntity.ok(chat);


     }   */

    @GetMapping("/{id}")
    public ResponseEntity<FullChatDTO> getChat(@PathVariable Integer id,Principal principal){

    UserD userD = userDRepository.findByUsername(principal.getName());
      FullChatDTO fullChatDTO=  (chatService.
                convertChatToChatDTO(chatRepository.findById(id).orElseThrow()));

        String chatName = fullChatDTO.getUsers().stream().
             filter( userD1 -> !userD1.getId().equals(userD.getId())).toList().get(0).getUsername();

        fullChatDTO.setChatName(chatName);

        return ResponseEntity.ok
                (fullChatDTO);
    }


    @GetMapping("/simple/{id}")
    public ResponseEntity<Chat> getChat(@PathVariable Integer id) {

        return ResponseEntity.ok(chatRepository.findById(id).orElseThrow());
    }


}

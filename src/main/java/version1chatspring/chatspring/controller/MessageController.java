package version1chatspring.chatspring.controller;

import com.nimbusds.jose.shaded.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import version1chatspring.chatspring.model.Chat;
import version1chatspring.chatspring.model.ChatMessage;
import version1chatspring.chatspring.model.UserD;
import version1chatspring.chatspring.repository.ChatMessageRepository;
import version1chatspring.chatspring.repository.ChatRepository;
import version1chatspring.chatspring.repository.UserDRepository;

import java.time.*;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class MessageController {


    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    ChatRepository chatRepository;


    @Autowired
    UserDRepository userDRepository;

    @Autowired
    ChatMessageRepository chatMessageRepository;

    @MessageMapping("/1message/{id}")
    public ChatMessage send(@DestinationVariable String id, String chatMessage) throws Exception {

        Gson g = new Gson();
         ChatMessage chatMessageToSave = g.fromJson(chatMessage, ChatMessage.class);

        simpMessagingTemplate.convertAndSend("/broker/messages/" + id,chatMessage);


        Chat chat = chatRepository.findById(chatMessageToSave.getChatId()).orElseThrow();

        LocalDateTime localDateTime = LocalDateTime.now().plusHours(1);
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();

        Date dateToSave = Date.from(instant);


        chatMessageToSave.setCreated(dateToSave );

        chatMessageRepository.save(chatMessageToSave);

        if ( chat.getMessagesId().size() < 1 ) {
            chat.setMessagesId(new ArrayList<>());
        }
        chat.addMessageToChat(chatMessageToSave.getId());
        System.out.println(chatMessageToSave.getId());

        chatRepository.save(chat);


        return chatMessageToSave;
    }

    @MessageMapping("/message/{id}")
    public ChatMessage sendTest(@DestinationVariable Integer id, String chatMessage) throws Exception {

        Gson g = new Gson();
        ChatMessage chatMessageToSave = g.fromJson(chatMessage, ChatMessage.class);

        System.out.println(id);

        Chat chatFind= chatRepository.findById(id).orElseThrow();

        for ( int i =0 ; i < chatFind.getUsersId().size();i++ ) {



            UserD userDFound=userDRepository.findById(chatFind.getUsersId().get(i)).orElseThrow();

            System.out.println(userDFound.getId());
            simpMessagingTemplate.convertAndSend("/broker/messages/" + userDFound.getUsername(),chatMessage);

        }



        Chat chat = chatRepository.findById(chatMessageToSave.getChatId()).orElseThrow();

        LocalDateTime localDateTime = LocalDateTime.now().plusHours(1);
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();

        Date dateToSave = Date.from(instant);


        chatMessageToSave.setCreated(dateToSave );

        chatMessageRepository.save(chatMessageToSave);

        if ( chat.getMessagesId().size() < 1 ) {
            chat.setMessagesId(new ArrayList<>());
        }
        chat.addMessageToChat(chatMessageToSave.getId());
        //System.out.println(chatMessageToSave.getId());

        chatRepository.save(chat);


        return chatMessageToSave;
    }



}

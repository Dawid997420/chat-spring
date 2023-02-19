package version1chatspring.chatspring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import version1chatspring.chatspring.model.*;
import version1chatspring.chatspring.repository.ChatMessageRepository;
import version1chatspring.chatspring.repository.ChatRepository;
import version1chatspring.chatspring.repository.UserDRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {


    @Autowired
    private ChatRepository chatRepository ;

    @Autowired
    private UserDRepository userDRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;


    public FullChatDTO convertChatToChatDTO(Chat chat) {

        List<Chat> chatList = new ArrayList<>();
        chatList.add(chat);

        ChatDTO chatDTO = convertChatToChatDTO(chatList).get(0);


        FullChatDTO chatDTOFull= new FullChatDTO(chatDTO.getChatName(),chatDTO.getId(),
                chatDTO.getUsers());

        List<ChatMessage> chatMessageList = chatMessageRepository.findAll();

        chatMessageList = chatMessageList.stream().filter(chatMessage -> {

            for ( int i =0 ; i < chatDTO.getMessagesId().size() ;i++) {

                if ( chatMessage.getId().equals( chatDTO.getMessagesId().get(i)) ) {

                    return true;
                }

            }
            return false;
        }).collect(Collectors.toList());

        chatDTOFull.setMessages(chatMessageList);


            return chatDTOFull;
    }


    public List<Chat> getAllUserIdChats( Integer id) {

        List<Chat> allChats= chatRepository.findAll();

        List<Chat> allUserChats= new ArrayList<>();

        for ( int i =0 ; i < allChats.size(); i++) {

            for( int j=0 ; j< allChats.get(i).getUsersId().size() ; j++) {


                if ( allChats.get(i).getUsersId().get(j).equals( id)) {

                    allUserChats.add(allChats.get(i));


                }
            }
        }


        List<ChatDTO> allUserChatsDTO = new ArrayList<>();




        return  allUserChats;

    }

    public List<ChatDTO> getAllUserIdChatsDTO ( Integer id){

            List<ChatDTO> chatsDTOS = convertChatToChatDTO( getAllUserIdChats(id));


           List<UserD> userDList = userDRepository.findAll().stream()
                   .filter( userD -> !userD.getId().equals(id)).toList();

       chatsDTOS= chatsDTOS.stream().peek(chatDTO -> {

           List<UserD> userDSL = chatDTO.getUsers();

           String chatname = "" ;

           for ( int i = 0 ; i < userDSL.size(); i++) {

               if ( !userDSL.get(i).getId().equals(id) ) {
                    chatname = userDSL.get(i).getUsername();
               }
           }
          // userDSL.stream().filter( userD -> !userD.getId().equals(id) ).toList();

           chatDTO.setChatName(chatname);
        }).collect(Collectors.toList());

        return chatsDTOS;
    }


    public List<ChatDTO> convertChatToChatDTO(List<Chat> chatList) {

        List<ChatDTO> dtoList = new ArrayList<>();

        for ( int i = 0 ; i < chatList.size() ; i++) {


            Chat currentChat = chatList.get(i);

            ChatDTO chatDTO = new ChatDTO();


            chatDTO.setId(currentChat.getId());
            chatDTO.setMessagesId(currentChat.getMessagesId());
            chatDTO.setUsers(new ArrayList<>());

            for ( int j = 0 ; j < currentChat.getUsersId().size() ; j++) {


   

                        UserD userD= userDRepository.findById(currentChat.getUsersId().get(j)).orElseThrow();

                        chatDTO.addUser(userD);




            }


            dtoList.add(chatDTO);

        }

        return dtoList;

    }


    public void deleteAllUserIdChats(Integer userId){

        List<Chat> chatListToDelete = new ArrayList<>();
        List<Chat> allChats = chatRepository.findAll();

        for ( int i =0 ; i < allChats.size() ; i++) {

            for ( int j = 0 ; j < allChats.get(i).getUsersId().size() ;  j++ ) {

                if (userId.equals(allChats.get(i).getUsersId().get(j))) {
                    chatListToDelete.add(allChats.get(i));
                }


            }
        }

        chatRepository.deleteAll(chatListToDelete);



    }

    public void addNewUserChats(UserD userD) {


        List<UserD> userDList = userDRepository.findAll();

        userDList.remove(userD);

        for ( int i = 0 ; i < userDList.size(); i++) {

            Chat chatToAdd = new Chat();
            chatToAdd.setMessagesId(new ArrayList<>());

            List<Integer> usersId = new ArrayList<>();

            usersId.add(userD.getId());

            usersId.add(userDList.get(i).getId());



            chatToAdd.setUsersId(usersId);
            chatRepository.save(chatToAdd);
        }



    }

    public boolean existChatByUsersId(List<Integer> usersId) {

        List<Chat> correctList = chatRepository.findAll();

        for ( int i =0 ;i< usersId.size() ; i++) {


            correctList = returnAllChatsWithUserId(usersId.get(i),correctList);

        }


        return correctList.get(0).getUsersId().size()>1;
    }


    public Chat findChatByUsersId(List<Integer> usersId) {

        List<Chat> correctList = chatRepository.findAll();

        for ( int i =0 ;i< usersId.size() ; i++) {


            correctList = returnAllChatsWithUserId(usersId.get(i),correctList);

        }

        return correctList.get(0);
    }


    private List<Chat> returnAllChatsWithUserId(Integer userId, List<Chat> listToFiltr ) {


        List<Chat> chatList = chatRepository.findAll();

        List<Chat> chatWithUserId = new ArrayList<>();

        for (int i = 0 ; i < chatList.size() ; i++  ) {

            if ( userIdExistsInChat(userId,chatList.get(i))) {

                chatWithUserId.add(chatList.get(i));
            }

        }

        return chatWithUserId;
    }

    private boolean userIdExistsInChat(Integer userId, Chat chat) {

        for ( int i = 0 ; i < chat.getUsersId().size() ; i++) {

           if ( chat.getUsersId().get(i).equals(userId) ) {

               return true;
           }

        }

        return false;
    }


}

package version1chatspring.chatspring.controller;

import org.hibernate.sql.exec.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;
import version1chatspring.chatspring.model.UserD;
import version1chatspring.chatspring.repository.ChatRepository;
import version1chatspring.chatspring.repository.UserDRepository;
import version1chatspring.chatspring.services.ChatService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserDController {

    @Autowired
    private UserDRepository userRepository;

    @Autowired
    private ChatService chatService ;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Boolean> registerUser(@RequestBody UserD userD) {


        userD.setPassword(passwordEncoder.encode(userD.getPassword()));

        if ( userRepository.existsByUsername(userD.getUsername()) ) {

            return ResponseEntity.ok(false);
        } else {
            userRepository.save(userD);

            UserD userD1 = userRepository.findByUsername(userD.getUsername());

            chatService.addNewUserChats(userD1);

        }
        return ResponseEntity.ok(true);


    }

    @GetMapping
    public ResponseEntity<List<UserD>> findAll() {

       return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/chats")
    public ResponseEntity<List<UserD>> findAllWithoutPrincipal(Principal principal) {

        try {

            List<UserD> userDList = userRepository.findAll();

            UserD userD1 = userRepository.findByUsername(principal.getName());

            userDList.remove(userD1);

            return ResponseEntity.ok(userDList);

        } catch (NullPointerException exception) {

            throw new  NullPointerException();

        }

    }





    @GetMapping("/{id}")
    public ResponseEntity<UserD> findUserById(@PathVariable Integer id) {


        return ResponseEntity.ok(userRepository.findById(id).orElseThrow());
    }


    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {

        userRepository.deleteById(id);

    }

    @DeleteMapping
    public void deleteAll(){
        userRepository.deleteAll();
    }

    @RequestMapping("/principal")
    @GetMapping
    public ResponseEntity<UserD> home(Principal principal) {

        UserD userD =  userRepository.findByUsername(principal.getName());

        try {

            return ResponseEntity.ok(userD);
        }   catch (NullPointerException exception) {

            return ResponseEntity.internalServerError().body(userD);
        }

    }



}

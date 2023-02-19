package version1chatspring.chatspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import version1chatspring.chatspring.model.UserDLogin;
import version1chatspring.chatspring.services.TokenService;

import java.security.Principal;
import java.util.logging.Logger;

@RestController
public class AuthController {

    private static final Logger LOG = Logger.getLogger(String.valueOf(AuthController.class));

    @Autowired
    private TokenService tokenService;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping("/login")
    @PostMapping
    private String loginWithCredentials(@RequestBody UserDLogin userDLogin) {


       Authentication authentication =
               authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userDLogin.getUsername(), userDLogin.getPassword()));



        return tokenService.generateToken(authentication);
    }





}

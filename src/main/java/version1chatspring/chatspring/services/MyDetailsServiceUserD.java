package version1chatspring.chatspring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import version1chatspring.chatspring.model.UserD;
import version1chatspring.chatspring.repository.UserDRepository;

import java.nio.file.attribute.UserPrincipal;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class MyDetailsServiceUserD implements UserDetailsService {

    @Autowired
    private UserDRepository userDRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserD userD=userDRepository.findByUsername(username);

        if ( userD == null ) {
            throw new UsernameNotFoundException(username);
        } else {

            Collection<GrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority("USER"));

            return new User(userD.getUsername(),userD.getPassword(), roles);
        }

    }


}

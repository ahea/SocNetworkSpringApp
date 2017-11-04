package SocNetwork.services;

import SocNetwork.models.nodeEntities.Role;
import SocNetwork.models.nodeEntities.User;
import SocNetwork.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;


@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final User user = userRepository.findByEmail(email);

        if (user == null){
            System.out.println("User not found");
            return null;
        }
        for (GrantedAuthority authority : getGrantedAuthorities(user.getRoles()))
            System.out.println(authority);

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(),
                getGrantedAuthorities(user.getRoles())
        );
    }

    private static Collection<GrantedAuthority> getGrantedAuthorities(Collection<Role> roles) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Role role : roles)
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        return grantedAuthorities;
    }

}

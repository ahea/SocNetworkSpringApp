package SocNetwork.services;

import SocNetwork.exceptions.EmailExistsException;
import SocNetwork.models.Role;
import SocNetwork.models.User;
import SocNetwork.repositories.RoleRepository;
import SocNetwork.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Created by aleksei on 11.02.17.
 */
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder encoder;
    private RoleRepository roleRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Autowired
    public void setEncoder(PasswordEncoder encoder){
        this.encoder = encoder;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @Override
    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUser(User user) throws EmailExistsException {
        if (getUserByEmail(user.getEmail()) != null)
            throw new EmailExistsException("This email is already used");
        Role role = roleRepository.findByName("USER");
        user.setRoles(Arrays.asList(role));
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id){
        userRepository.delete(id);
    }

    @Override
    public void deleteUser(User user){
        userRepository.delete(user);
    }

    @Override
    public void deleteAllUsers(){
        userRepository.deleteAll();
    }

}

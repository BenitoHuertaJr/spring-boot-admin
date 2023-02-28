package site.benitohuerta.starter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import site.benitohuerta.starter.entity.Role;
import site.benitohuerta.starter.entity.User;
import site.benitohuerta.starter.enums.RoleEnum;
import site.benitohuerta.starter.repository.RoleRepository;
import site.benitohuerta.starter.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setPasswordConfirm(null);
        user.setActive(user.isActive());

        userRepository.save(user);
    }

    @Override
    public void registration(User user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setPasswordConfirm(null);
        user.setRoles(new HashSet< >(roleRepository.getRoleByName(RoleEnum.USER.getName())));
        user.setActive(true);

        userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String userEmail) {
        return userRepository.getUserByEmail(userEmail);
    }

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    public User findById(Integer id) {
        User user = userRepository.findById(id).orElse(null);
        return user;
    }

    public User update(Integer id, User userDetails) {
        User user = userRepository.findById(id).get();
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setActive(userDetails.isActive());
        user.setRoles(userDetails.getRoles());

        return userRepository.save(user);
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
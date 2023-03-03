package site.benitohuerta.starter.service;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import site.benitohuerta.starter.entity.User;

import java.util.List;

public interface UserService {
    void save(User user);

    void registration(User user);

    User getUserByEmail(String userEmail);

    User getUserByName(String userName);

    List<User> findAll();

    User findById(Integer id);

    User update(Integer id, User userDetails);

    void delete(Integer id);

    User getAuthenticatedUser();
}
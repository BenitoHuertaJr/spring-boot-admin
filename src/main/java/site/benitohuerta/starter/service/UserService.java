package site.benitohuerta.starter.service;


import site.benitohuerta.starter.entity.User;

import java.util.List;

public interface UserService {
    void save(User user);

    void registration(User user);

    User getUserByEmail(String userEmail);

    List<User> findAll();

    User findById(Integer id);

    User update(Integer id, User userDetails);

    void delete(Integer id);
}
package site.benitohuerta.starter.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.benitohuerta.starter.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query(value = "SELECT * FROM users WHERE email = :userEmail", nativeQuery = true)
    public User getUserByEmail(@Param("userEmail") String userEmail);

    @Query(value = "SELECT * FROM users WHERE name = :userName", nativeQuery = true)
    public User getUserByName(@Param("userName") String userName);

}
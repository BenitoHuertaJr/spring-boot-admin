package site.benitohuerta.starter.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.benitohuerta.starter.entity.Role;
import site.benitohuerta.starter.entity.User;

import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

    @Query(value = "SELECT * FROM roles WHERE name = :roleName", nativeQuery = true)
    public List<Role> getRoleByName(@Param("roleName") String roleName);

    @Query(value = "SELECT * FROM roles WHERE id IN(:ids)", nativeQuery = true)
    public List<Role> getRoleByIds(@Param("ids") List<Integer> ids);

}
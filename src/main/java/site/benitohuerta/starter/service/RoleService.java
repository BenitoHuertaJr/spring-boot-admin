package site.benitohuerta.starter.service;


import site.benitohuerta.starter.entity.Role;

import java.util.List;

public interface RoleService {
    void save(Role role);

    List<Role> getRoleByName(String roleName);

    List<Role> findAll();

    Role findById(Integer id);

    Role update(Integer id, Role roleDetails);

    void delete(Integer id);

    public List<Role> getRoleByIds(List<Integer> ids);
}
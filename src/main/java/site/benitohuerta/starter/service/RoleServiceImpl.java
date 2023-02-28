package site.benitohuerta.starter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.benitohuerta.starter.entity.Role;
import site.benitohuerta.starter.repository.RoleRepository;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public List<Role> getRoleByName(String roleName) {
        return roleRepository.getRoleByName(roleName);
    }

    public List<Role> findAll() {
        return (List<Role>) roleRepository.findAll();
    }

    public Role findById(Integer id) {
        Role role = roleRepository.findById(id).orElse(null);
        return role;
    }

    public Role update(Integer id, Role roleDetails) {
        Role role = roleRepository.findById(id).get();
        role.setName(roleDetails.getName());

        return roleRepository.save(role);
    }

    public void delete(Integer id) {
        roleRepository.deleteById(id);
    }

    @Override
    public List<Role> getRoleByIds(List<Integer> ids) {
        return roleRepository.getRoleByIds(ids);
    }
}
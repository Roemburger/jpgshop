package com.iprwc.jpgshop.dao;

import com.iprwc.jpgshop.entity.Role;
import com.iprwc.jpgshop.entity.RoleType;
import org.springframework.stereotype.Component;

@Component
public class RoleDAO {
    RoleRepository roleRepository;

    public RoleDAO(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role setUserRole() {
        return roleRepository.findByType(RoleType.USER);
    }
}

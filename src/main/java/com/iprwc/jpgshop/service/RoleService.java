package com.iprwc.jpgshop.service;

import com.iprwc.jpgshop.dao.RoleDAO;
import com.iprwc.jpgshop.entity.Role;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleDAO roleDAO;
    public RoleService(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }
    public Role setUserRole() {
        return roleDAO.setUserRole();
    }
}

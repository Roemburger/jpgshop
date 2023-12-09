package com.iprwc.jpgshop.dao;

import com.iprwc.jpgshop.entity.Role;
import com.iprwc.jpgshop.entity.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByType(RoleType roleType);
}

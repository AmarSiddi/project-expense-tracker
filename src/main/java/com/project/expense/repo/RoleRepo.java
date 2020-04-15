package com.project.expense.repo;

import java.util.Optional;

import com.project.expense.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.expense.model.RoleName;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

	Optional<Role> findByName(RoleName roleName);
}

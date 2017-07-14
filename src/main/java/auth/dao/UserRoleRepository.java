package auth.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import auth.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
	public UserRole findByName(String name);
	public UserRole findById(Long id);
}

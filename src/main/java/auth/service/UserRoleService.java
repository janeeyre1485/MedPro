package auth.service;

import java.util.List;

import auth.model.UserRole;

public interface UserRoleService {
	public UserRole findByName(String name);

	public UserRole findById(Long id);

	public List<UserRole> findAll();

}

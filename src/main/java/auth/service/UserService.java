package auth.service;

import java.util.List;

import auth.model.User;

public interface UserService {

	public void save(User user);

	public User findUserByEmail(String username);

	public User findUserById(Long id);

	public void addRoleToUser(User user, String role);

	public List<User> findAll();
}

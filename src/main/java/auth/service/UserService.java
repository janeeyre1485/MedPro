package auth.service;

import auth.model.User;

public interface UserService {

	public void save(User user);
	public User findUserByEmail(String username);
}

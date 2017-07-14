package auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auth.dao.UserRepository;
import auth.dao.UserRoleRepository;
import auth.model.User;
import auth.model.UserRole;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void addRoleToUser(User user, String role) {
		List<UserRole> roles = user.getUserRoles();
		roles.add(userRoleRepository.findByName(role));
		user.setUserRoles(roles);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User findUserById(Long id) {
		return userRepository.findById(id);
	}
}

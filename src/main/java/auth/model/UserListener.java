package auth.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PrePersist;

import org.springframework.security.crypto.password.PasswordEncoder;

import auth.dao.UserRoleRepository;
import auth.service.BeanUtil;

public class UserListener {

	private PasswordEncoder passwordEncoder;
	private UserRoleRepository userRoleRepository;

	public PasswordEncoder getPasswordEncoder() {
		return BeanUtil.getBean(PasswordEncoder.class);
	}

	public UserRoleRepository getUserRoleRepository() {
		return BeanUtil.getBean(UserRoleRepository.class);
	}

	@PrePersist
	public void onSave(Object object) {
		List<UserRole> userRoles;
		passwordEncoder = getPasswordEncoder();
		userRoleRepository = getUserRoleRepository();
		User user = (User) object;
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		if (user.getUserRoles().isEmpty()) {
			userRoles = new ArrayList<UserRole>();
			userRoles.add(userRoleRepository.findByName("ROLE_USER"));
			user.setUserRoles(userRoles);
		}
	}
}

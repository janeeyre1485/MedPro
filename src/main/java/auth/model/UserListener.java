package auth.model;

import javax.persistence.PrePersist;

import org.springframework.security.crypto.password.PasswordEncoder;

import auth.service.BeanUtil;

public class UserListener {

	private PasswordEncoder passwordEncoder;

	@PrePersist
	public void onSave(Object object) {

		passwordEncoder = BeanUtil.getBean(PasswordEncoder.class);
		
		User user = (User) object;
		user.setPassword(passwordEncoder.encode(user.getPassword()));
	}
}

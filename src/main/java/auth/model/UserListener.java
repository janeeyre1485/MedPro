package auth.model;

import javax.persistence.PrePersist;

import org.springframework.security.crypto.password.PasswordEncoder;

import auth.service.BeanUtil;

public class UserListener {

	private PasswordEncoder passwordEncoder;
	
	public PasswordEncoder getPasswordEncoder(){
		return BeanUtil.getBean(PasswordEncoder.class);
	
	}

	@PrePersist
	public void onSave(Object object) {
		passwordEncoder = getPasswordEncoder();
		User user = (User) object;
		user.setPassword(passwordEncoder.encode(user.getPassword()));
	}
}

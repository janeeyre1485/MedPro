package auth.validator;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import auth.model.User;
import auth.model.UserRole;

@Component
public class UserRolesValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		List<UserRole> roles = user.getUserRoles();
		if (roles.isEmpty()) {
			errors.rejectValue("userRoles", "NotEmpty");
		}
	}

}

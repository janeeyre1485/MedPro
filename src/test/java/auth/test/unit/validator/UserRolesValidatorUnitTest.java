package auth.test.unit.validator;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import auth.model.User;
import auth.model.UserRole;
import auth.test.TestUtils;
import auth.validator.UserRolesValidator;

@RunWith(SpringRunner.class)
@SpringBootTest

public class UserRolesValidatorUnitTest {

	@Autowired
	private UserRolesValidator userRolesValidator;

	@Test
	public void testValidate_emptyRoles() throws Exception {
		User user = new User(TestUtils.CORRECT_EMAIL, TestUtils.CORRECT_PASSWORD, TestUtils.CORRECT_PASSWORD);
		Errors errors = new BeanPropertyBindingResult(user, "user");

		userRolesValidator.validate(user, errors);

		Assert.assertTrue(errors.hasErrors());

	}
	
	@Test
	public void testValidate_notEmptyRoles() throws Exception {
		User user = new User(TestUtils.CORRECT_EMAIL, TestUtils.CORRECT_PASSWORD, TestUtils.CORRECT_PASSWORD);
		List<UserRole> roles = new ArrayList<UserRole>();
		roles.add(new UserRole());
		user.setUserRoles(roles);
		
		Errors errors = new BeanPropertyBindingResult(user, "user");

		userRolesValidator.validate(user, errors);

		Assert.assertTrue(!errors.hasErrors());

	}
}

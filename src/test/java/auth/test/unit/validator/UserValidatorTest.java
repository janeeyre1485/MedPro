package auth.test.unit.validator;

import static org.mockito.Mockito.when;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import auth.model.User;
import auth.service.UserService;
import auth.test.TestUtils;
import auth.validator.UserValidator;

@RunWith(MockitoJUnitRunner.class)
public class UserValidatorTest {

	@InjectMocks
	private UserValidator userValidator;

	@Mock
	private UserService mockUserService;

	@Test
	public void testValidate_validUser() {
		User user = new User(TestUtils.CORRECT_EMAIL, TestUtils.CORRECT_PASSWORD, TestUtils.CORRECT_PASSWORD);
		Errors errors = new BeanPropertyBindingResult(user, "user");

		when(mockUserService.findUserByEmail(user.getEmail())).thenReturn(null);
		userValidator.validate(user, errors);

		Assert.assertEquals(errors.hasErrors(), false);

	}

	@Test
	public void testValidate_invalidEmail() {
		User user = new User(TestUtils.INCORRECT_EMAIL, TestUtils.CORRECT_PASSWORD, TestUtils.CORRECT_PASSWORD);
		Errors errors = new BeanPropertyBindingResult(user, "user");

		when(mockUserService.findUserByEmail(user.getEmail())).thenReturn(null);
		userValidator.validate(user, errors);

		Assert.assertThat(errors.getFieldError("email").toString(), CoreMatchers.containsString("Email.not.correct"));

	}

	@Test
	public void testValidate_notEqualPasswords() {

		User user = new User(TestUtils.INCORRECT_EMAIL, TestUtils.CORRECT_PASSWORD, TestUtils.INCORRECT_PASSWORD);
		Errors errors = new BeanPropertyBindingResult(user, "user");

		when(mockUserService.findUserByEmail(user.getEmail())).thenReturn(null);
		userValidator.validate(user, errors);

		Assert.assertThat(errors.getFieldError("passwordConfirm").toString(),
				CoreMatchers.containsString("Diff.passwordConfirm"));
	}

	@Test
	public void testValidate_emptyEmail() {

		User user = new User(TestUtils.EMPTY_STRING, TestUtils.CORRECT_PASSWORD, TestUtils.CORRECT_PASSWORD);
		Errors errors = new BeanPropertyBindingResult(user, "user");

		when(mockUserService.findUserByEmail(user.getEmail())).thenReturn(null);
		userValidator.validate(user, errors);

		Assert.assertThat(errors.getFieldError("email").toString(), CoreMatchers.containsString("NotEmpty"));
	}

	@Test
	public void testValidate_emptyPassword() {

		User user = new User(TestUtils.CORRECT_EMAIL, TestUtils.EMPTY_STRING, TestUtils.CORRECT_PASSWORD);
		Errors errors = new BeanPropertyBindingResult(user, "user");

		when(mockUserService.findUserByEmail(user.getEmail())).thenReturn(null);
		userValidator.validate(user, errors);

		Assert.assertThat(errors.getFieldError("password").toString(), CoreMatchers.containsString("NotEmpty"));
	}

	@Test
	public void testValidate_emptyPasswordConfirm() {
		User user = new User(TestUtils.CORRECT_EMAIL, TestUtils.CORRECT_PASSWORD, TestUtils.EMPTY_STRING);
		Errors errors = new BeanPropertyBindingResult(user, "user");

		when(mockUserService.findUserByEmail(user.getEmail())).thenReturn(null);
		userValidator.validate(user, errors);

		Assert.assertThat(errors.getFieldError("passwordConfirm").toString(), CoreMatchers.containsString("NotEmpty"));
	}

	@Test
	public void testValidate_notUniqueEmail() {

		User user = new User(TestUtils.CORRECT_EMAIL, TestUtils.CORRECT_PASSWORD, TestUtils.CORRECT_PASSWORD);
		Errors errors = new BeanPropertyBindingResult(user, "user");

		when(mockUserService.findUserByEmail(user.getEmail())).thenReturn(user);
		userValidator.validate(user, errors);

		Assert.assertThat(errors.getFieldError("email").toString(), CoreMatchers.containsString("Email.not.unique"));
	}
}

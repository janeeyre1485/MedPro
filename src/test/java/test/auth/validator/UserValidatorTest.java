package test.auth.validator;

import static org.mockito.Mockito.when;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import auth.model.User;
import auth.service.UserService;
import auth.validator.UserValidator;

@RunWith(MockitoJUnitRunner.class)
public class UserValidatorTest {

	@InjectMocks
	private UserValidator userValidator;

	private User user;
	private Errors errors;

	@Mock
	private UserService mockUserService;

	@Before
	public void setUp() {
		user = new User();
		errors = new BeanPropertyBindingResult(user, "user");
	}

	@Test
	public void testValidate_validUser() {

		user.setEmail("mail@mail.com");
		user.setPassword("password");
		user.setPasswordConfirm("password");

		when(mockUserService.findUserByEmail(user.getEmail())).thenReturn(null);
		userValidator.validate(user, errors);

		Assert.assertEquals(errors.hasErrors(), false);

	}

	@Test
	public void testValidate_invalidEmail() {

		user.setEmail("mailmail.com");
		user.setPassword("password");
		user.setPasswordConfirm("password");

		when(mockUserService.findUserByEmail(user.getEmail())).thenReturn(null);
		userValidator.validate(user, errors);

		Assert.assertThat(errors.getFieldError("email").toString(), CoreMatchers.containsString("Email.not.correct"));

	}
	
	@Test
	public void testValidate_notEqualPasswords() {

		user.setEmail("mailmail.com");
		user.setPassword("password");
		user.setPasswordConfirm("password1");

		when(mockUserService.findUserByEmail(user.getEmail())).thenReturn(null);
		userValidator.validate(user, errors);

		Assert.assertThat(errors.getFieldError("passwordConfirm").toString(), CoreMatchers.containsString("Diff.passwordConfirm"));
	}
	
	@Test
	public void testValidate_emptyEmail() {

		user.setEmail("");
		user.setPassword("password");
		user.setPasswordConfirm("password");

		when(mockUserService.findUserByEmail(user.getEmail())).thenReturn(null);
		userValidator.validate(user, errors);

		Assert.assertThat(errors.getFieldError("email").toString(), CoreMatchers.containsString("NotEmpty"));
	}
	
	@Test
	public void testValidate_emptyPassword() {

		user.setEmail("mail@mail.com");
		user.setPassword("");
		user.setPasswordConfirm("password");

		when(mockUserService.findUserByEmail(user.getEmail())).thenReturn(null);
		userValidator.validate(user, errors);

		Assert.assertThat(errors.getFieldError("password").toString(), CoreMatchers.containsString("NotEmpty"));
	}
	
	@Test
	public void testValidate_emptyPasswordConfirm() {

		user.setEmail("mail@mail.com");
		user.setPassword("password");
		user.setPasswordConfirm("");

		when(mockUserService.findUserByEmail(user.getEmail())).thenReturn(null);
		userValidator.validate(user, errors);

		Assert.assertThat(errors.getFieldError("passwordConfirm").toString(), CoreMatchers.containsString("NotEmpty"));
	}
	
	@Test
	public void testValidate_notUniqueEmail() {

		user.setEmail("mail@mail.com");
		user.setPassword("password");
		user.setPasswordConfirm("password");

		when(mockUserService.findUserByEmail(user.getEmail())).thenReturn(user);
		userValidator.validate(user, errors);

		Assert.assertThat(errors.getFieldError("email").toString(), CoreMatchers.containsString("Email.not.unique"));
	}
}

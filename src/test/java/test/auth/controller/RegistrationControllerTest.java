package test.auth.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import auth.controller.RegistrationController;
import auth.model.User;
import auth.service.UserService;
import auth.validator.UserValidator;

@RunWith(SpringJUnit4ClassRunner.class)
public class RegistrationControllerTest {

	private static final String USER_EMAIL_VALID = "mail@mail.com";
	private static final String USER_PASSWORD_VALID = "Password1";
	private static final String USER_PASSWORD_CONFIRM_VALID = "Password1";

	private static final String USER_EMAIL_INVALID = "mailmail.com";
	private static final String USER_PASSWORD_INVALID = "";
	private static final String USER_PASSWORD_CONFIRM_INVALID = "";

	@InjectMocks
	private RegistrationController registrationController;

	@Mock
	private UserService mockUserService;

	@Mock
	private UserValidator mockUserValidator;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/jsp/view/");
		viewResolver.setSuffix(".jsp");

		mockMvc = MockMvcBuilders.standaloneSetup(registrationController).setViewResolvers(viewResolver).build();

		when(mockUserValidator.supports(User.class)).thenReturn(true);

	}

	@Test
	public void testCreateAccount() throws Exception {

		mockMvc.perform(get("/registration")).andExpect(status().isOk())
				.andExpect(model().attribute("user", hasProperty("email", nullValue())))
				.andExpect(model().attribute("user", hasProperty("password", nullValue())))
				.andExpect(view().name("registration"));
	}

	@Test
	public void testDoCreateAccount_validAccount() throws Exception {

		mockMvc.perform(post("/registration").param("email", USER_EMAIL_VALID).param("password", USER_PASSWORD_VALID)
				.param("passwordConfirm", USER_PASSWORD_CONFIRM_VALID))
				.andExpect(model().attribute("user", hasProperty("email", equalTo(USER_EMAIL_VALID))))
				.andExpect(model().attribute("user", hasProperty("password", equalTo(USER_PASSWORD_VALID))))
				.andExpect(redirectedUrl("/login"));

	}

	@Test
	public void testDoCreateAccount_invalidAccount() throws Exception {

		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Errors errors = (Errors) invocation.getArguments()[1];
				errors.rejectValue("email", "Email not correct");
				errors.rejectValue("password", "Password not correct");
				errors.rejectValue("passwordConfirm", "Passwords don't match");

				return null;
			}
		}).when(mockUserValidator).validate(any(), any());

		mockMvc.perform(post("/registration").param("email", USER_EMAIL_INVALID)
				.param("password", USER_PASSWORD_INVALID).param("passwordConfirm", USER_PASSWORD_CONFIRM_INVALID))
				.andExpect(view().name("registration")).andExpect(model().attributeHasFieldErrors("user", "email"))
				.andExpect(model().attributeHasFieldErrors("user", "password"))
				.andExpect(model().attributeHasFieldErrors("user", "passwordConfirm"));
	}

}

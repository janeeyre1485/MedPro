package auth.test.unit.controller;

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
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import auth.controller.RegistrationController;
import auth.model.User;
import auth.service.UserService;
import auth.test.TestUtils;
import auth.validator.UserValidator;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationUnitTest {

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

		mockMvc.perform(post("/registration").param("email", TestUtils.CORRECT_EMAIL)
				.param("password", TestUtils.CORRECT_PASSWORD).param("passwordConfirm", TestUtils.CORRECT_PASSWORD))
				.andExpect(model().attribute("user", hasProperty("email", equalTo(TestUtils.CORRECT_EMAIL))))
				.andExpect(model().attribute("user", hasProperty("password", equalTo(TestUtils.CORRECT_PASSWORD))))
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

		mockMvc.perform(post("/registration")
						.param("email", TestUtils.INCORRECT_EMAIL)
						.param("password", TestUtils.INCORRECT_PASSWORD)
						.param("passwordConfirm", TestUtils.CORRECT_PASSWORD))
				.andExpect(view().name("registration"))
				.andExpect(model().attributeHasFieldErrors("user", "email"))
				.andExpect(model().attributeHasFieldErrors("user", "password"))
				.andExpect(model().attributeHasFieldErrors("user", "passwordConfirm"));
	}

}

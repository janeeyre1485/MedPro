package test.auth.controller;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;

import auth.controller.RegistrationController;
import auth.service.UserService;

public class LoginControllerTest {
	@InjectMocks
	private RegistrationController registrationController;

	@Mock
	private UserService userService;

	private MockMvc mockMvc;

	
	@Test
	public void testCreateAccount() throws Exception {

		mockMvc.perform(get("/registration")).andExpect(status().isOk())
				.andExpect(model().attribute("user", hasProperty("email", nullValue())))
				.andExpect(model().attribute("user", hasProperty("password", nullValue())))
				.andExpect(view().name("registration"));
	}
}

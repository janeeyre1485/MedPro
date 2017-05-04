package test.auth.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import auth.config.SecurityConfig;
import auth.controller.LoginController;
import auth.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SecurityConfig.class)
@WebAppConfiguration
public class LoginControllerTest {

	@Autowired
	FilterChainProxy springSecurityFilterChain;

	@InjectMocks
	private LoginController loginController;

	@Mock
	private UserService userService;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/jsp/view/");
		viewResolver.setSuffix(".jsp");

		mockMvc = MockMvcBuilders.standaloneSetup(loginController).setViewResolvers(viewResolver)
				.apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain)).build();

	}

	@Test
	public void testLoginPage() throws Exception {
		mockMvc.perform(get("/login")).andExpect(view().name("login"));
	}

	@Test
	public void testLoginPage_invalidCredentials_errorMessage() throws Exception {
		mockMvc.perform(get("/login").param("error", "Credential error")).andExpect(model().attributeExists("error"));
	}

	@Test
	public void testLoginPage_loggedoutUsed() throws Exception {
		mockMvc.perform(get("/login").param("logout", "User logged out")).andExpect(model().attributeExists("message"));
	}
}

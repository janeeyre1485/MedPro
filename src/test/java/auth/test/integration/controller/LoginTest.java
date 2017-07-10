package auth.test.integration.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import auth.model.User;
import auth.service.UserService;
import auth.test.TestUtils;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class LoginTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private UserService userService;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();

	}

	@Test
	public void loginTest_correctCredentials() throws Exception {
		userService.save(new User(TestUtils.CORRECT_EMAIL, TestUtils.CORRECT_PASSWORD, TestUtils.CORRECT_PASSWORD));

		mockMvc.perform(formLogin("/login").user(TestUtils.CORRECT_EMAIL).password(TestUtils.CORRECT_PASSWORD))
				.andExpect(redirectedUrl("/welcome"));
	}

	@Test
	public void loginTest_incorrectCredentials() throws Exception {
		mockMvc.perform(formLogin("/login").user(TestUtils.INCORRECT_EMAIL).password(TestUtils.INCORRECT_PASSWORD))
				.andExpect(redirectedUrl("/login?error"));
	}

	@Test
	public void logoutTest() throws Exception {

		mockMvc.perform(logout()).andExpect(redirectedUrl("/login?logout"));
	}

}

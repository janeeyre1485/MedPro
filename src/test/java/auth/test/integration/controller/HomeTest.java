package auth.test.integration.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import auth.model.User;
import auth.service.UserService;
import auth.test.TestUtils;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class HomeTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private UserService userService;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	public void welcomeTest_authorizedUser() throws Exception {
		userService.save(new User(TestUtils.CORRECT_EMAIL, TestUtils.CORRECT_PASSWORD,TestUtils.CORRECT_PASSWORD));
		mockMvc.perform(post("/login")
				.param("username", TestUtils.CORRECT_EMAIL)
				.param("password", TestUtils.CORRECT_PASSWORD));
		mockMvc.perform(get("/home")).andExpect(status().isOk());
	}

	@Test
	public void welcomeTest_unauthorizedUser() throws Exception {

		mockMvc.perform(get("/home")).andExpect(status().isOk());
	}
}

package auth.test.integration.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import auth.test.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class WelcomeIntegrationTest {

	@Autowired
	private WebApplicationContext context;


	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	public void welcomeTest_authorizedUser() throws Exception {

		mockMvc.perform(get("/welcome")
				.with(user(TestUtils.CORRECT_EMAIL)
				.password(TestUtils.CORRECT_PASSWORD)))
				.andExpect(status().isOk());
	}

	@Test
	public void welcomeTest_unauthorizedUser() throws Exception {

		mockMvc.perform(get("/welcome")).andExpect(unauthenticated());
	}
}

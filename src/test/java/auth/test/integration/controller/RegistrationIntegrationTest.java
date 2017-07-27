package auth.test.integration.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import org.junit.Assert;
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
public class RegistrationIntegrationTest {

	@Autowired
	private UserService userService;

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {

		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

	}

	@Test
	public void registerTest_validUser() throws Exception {
		mockMvc.perform(post("/registration").param("email", TestUtils.CORRECT_EMAIL)
				.param("password", TestUtils.CORRECT_PASSWORD).param("passwordConfirm", TestUtils.CORRECT_PASSWORD))
				.andExpect(redirectedUrl("/login"));
		Assert.assertNotNull(userService.findUserByEmail(TestUtils.CORRECT_EMAIL));
	}

	@Test
	public void registerTest_emptyFields() throws Exception {
		mockMvc.perform(post("/registration").param("email", TestUtils.EMPTY_STRING)
				.param("password", TestUtils.EMPTY_STRING).param("passwordConfirm", TestUtils.EMPTY_STRING))
				.andExpect(model().attributeHasFieldErrors("user", "email"))
				.andExpect(model().attributeHasFieldErrors("user", "password"))
				.andExpect(model().attributeHasFieldErrors("user", "passwordConfirm"));
	}

	@Test
	public void registerTest_invalidEmail() throws Exception {
		mockMvc.perform(post("/registration").param("email", TestUtils.INCORRECT_EMAIL)
				.param("password", TestUtils.CORRECT_PASSWORD).param("passwordConfirm", TestUtils.CORRECT_PASSWORD))
				.andExpect(model().attributeHasFieldErrors("user", "email"));
	}

	@Test
	public void registerTest_passwordsMismatch() throws Exception {
		mockMvc.perform(post("/registration").param("email", TestUtils.CORRECT_EMAIL)
				.param("password", TestUtils.CORRECT_PASSWORD).param("passwordConfirm", TestUtils.INCORRECT_PASSWORD))
				.andExpect(model().attributeHasFieldErrors("user", "passwordConfirm"));
	}

	@Test
	public void registerTest_emailNotUnique() throws Exception {
		userService.save(new User(TestUtils.CORRECT_EMAIL, TestUtils.CORRECT_PASSWORD, TestUtils.CORRECT_PASSWORD));

		mockMvc.perform(post("/registration").param("email", TestUtils.CORRECT_EMAIL)
				.param("password", TestUtils.CORRECT_PASSWORD).param("passwordConfirm", TestUtils.CORRECT_PASSWORD))
				.andExpect(model().attributeHasFieldErrors("user", "email"));
	}
}

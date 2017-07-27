package auth.test.integration.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import auth.dao.UserRoleRepository;
import auth.model.User;
import auth.model.UserRole;
import auth.service.UserRoleService;
import auth.service.UserService;
import auth.test.TestUtils;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class EditUserRolesIntegrationTest {

	@Autowired
	UserService userService;

	@Autowired
	UserRoleService userRoleService;

	@Autowired
	UserRoleRepository userRoleRep;

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;
	private User user;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		
		user = new User(TestUtils.CORRECT_EMAIL, TestUtils.CORRECT_PASSWORD, TestUtils.CORRECT_PASSWORD);
		userService.addRoleToUser(user, "ROLE_ADMIN");
		userService.save(user);

	}

	@Test
	public void editUserRoles_addUserRole() throws Exception {

		User userWithRole = userService.findUserByEmail(TestUtils.CORRECT_EMAIL);
		userService.addRoleToUser(userWithRole, "ROLE_USER");

		mockMvc.perform(post("/admin/users/{id}/edit-roles", user.getId()).sessionAttr("user", userWithRole))
				.andExpect(redirectedUrl("/admin/users/"));

		Assert.assertEquals(2, userService.findUserByEmail(TestUtils.CORRECT_EMAIL).getUserRoles().size());
	}

	@Test
	public void editUserRoles_addUserNoRole() throws Exception {

		User userWithoutRole = userService.findUserByEmail(TestUtils.CORRECT_EMAIL);
		userWithoutRole.setUserRoles(new ArrayList<UserRole>());

		mockMvc.perform(post("/admin/users/{id}/edit-roles", user.getId()).sessionAttr("user", userWithoutRole))
				.andExpect(model().attributeHasFieldErrors("user", "userRoles"));

	}

}

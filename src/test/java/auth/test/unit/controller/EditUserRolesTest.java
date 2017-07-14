package auth.test.unit.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasProperty;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import auth.controller.EditUserRolesController;
import auth.model.User;
import auth.service.UserRoleService;
import auth.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EditUserRolesTest {

	@InjectMocks
	private EditUserRolesController editUserRolesController;

	@Mock
	UserService userService;

	@Mock
	UserRoleService userRoleService;

	private Long id = new Long(7);
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/jsp/view/");
		viewResolver.setSuffix(".jsp");

		mockMvc = MockMvcBuilders.standaloneSetup(editUserRolesController).setViewResolvers(viewResolver).build();

	}

	@Test
	public void testEditUserRolesPageGet() throws Exception {
		when(userService.findUserById(id)).thenReturn(new User());
		mockMvc.perform(get("/admin/users/{id}/edit-roles", id)).andExpect(status().isOk())
				.andExpect(model().attribute("user", hasProperty("userRoles")))
				.andExpect(model().attribute("user", hasProperty("email")))
				.andExpect(model().attribute("user", hasProperty("password")))
				.andExpect(model().attribute("roles", notNullValue()))
				.andExpect(view().name("edituser"));

	}

	@Test
	public void testEditUserRolesPagePost() throws Exception {
		mockMvc.perform(post("/admin/users/{id}/edit-roles", id).sessionAttr("user", new User()))
				.andExpect(redirectedUrl("/admin/users/"));
	}
}

package auth.test.unit.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
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
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import auth.controller.EditUserRolesController;
import auth.model.User;
import auth.service.UserRoleService;
import auth.service.UserService;
import auth.test.TestUtils;
import auth.validator.UserRolesValidator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EditUserRolesUnitTest {

	@InjectMocks
	private EditUserRolesController editUserRolesController;

	@Mock
	UserService userService;

	@Mock
	UserRoleService userRoleService;

	@Mock
	private UserRolesValidator mockUserValidator;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/jsp/view/");
		viewResolver.setSuffix(".jsp");

		mockMvc = MockMvcBuilders.standaloneSetup(editUserRolesController).setViewResolvers(viewResolver).build();
		when(mockUserValidator.supports(User.class)).thenReturn(true);
	}

	@Test
	public void testEditUserRolesPageGet() throws Exception {
		when(userService.findUserById(TestUtils.USER_ID)).thenReturn(new User());
		mockMvc.perform(get("/admin/users/{id}/edit-roles", TestUtils.USER_ID)).andExpect(status().isOk())
				.andExpect(model().attribute("user", hasProperty("userRoles")))
				.andExpect(model().attribute("roles", notNullValue())).andExpect(view().name("edituser"));

	}

	@Test
	public void testEditUserRolesPagePost_emptyUserRoles() throws Exception {
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				Errors errors = (Errors) invocation.getArguments()[1];
				errors.rejectValue("userRoles", "user roles is empty");
				return null;
			}
		}).when(mockUserValidator).validate(any(), any());

		mockMvc.perform(post("/admin/users/{id}/edit-roles", TestUtils.USER_ID).sessionAttr("user", new User()))
				.andExpect(view().name("edituser"));
	}

	@Test
	public void testEditUserRolesPagePost_validUserRoles() throws Exception {
		
		doNothing().when(userService).save(any(User.class));
		mockMvc.perform(post("/admin/users/{id}/edit-roles", TestUtils.USER_ID)
				.sessionAttr("user", new User()))
				.andExpect(redirectedUrl("/admin/users/"));
	}
}

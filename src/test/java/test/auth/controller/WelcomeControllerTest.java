package test.auth.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import auth.controller.WelcomeController;

@RunWith(MockitoJUnitRunner.class)
public class WelcomeControllerTest {

	@InjectMocks
	private WelcomeController welcomeController;
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/jsp/view/");
		viewResolver.setSuffix(".jsp");

		mockMvc = MockMvcBuilders.standaloneSetup(welcomeController).setViewResolvers(viewResolver).build();
	}
	
	@Test
	public void testWelcomePage() throws Exception{
		mockMvc.perform(get("/welcome"))
		.andExpect(view().name("welcome"));
	}
}

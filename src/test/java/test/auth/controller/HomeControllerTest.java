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

import auth.controller.HomeController;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {

	@InjectMocks
	private HomeController homeController;
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/jsp/view/");
		viewResolver.setSuffix(".jsp");

		mockMvc = MockMvcBuilders.standaloneSetup(homeController).setViewResolvers(viewResolver).build();

	}

	@Test
	public void testHomePage() throws Exception {
		//mockMvc.perform(get("/")).andExpect(view().name("home"));

		mockMvc.perform(get("/home")).andExpect(view().name("home"));
	}
}

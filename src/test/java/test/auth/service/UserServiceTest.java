package test.auth.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import auth.dao.UserRepository;
import auth.model.User;
import auth.service.UserService;
import auth.service.UserServiceImpl;

@RunWith(MockitoJUnitRunner.class)
// @SpringBootTest
// @ContextConfiguration(classes = { AuthAppApplication.class, AppConfig.class
// })
public class UserServiceTest {

	private static final String USER_EMAIL = "mail@mail.com";
	private static final String USER_PASSWORD = "Password1";

	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@Before
	public void setUp() {
		userService = new UserServiceImpl();
		ReflectionTestUtils.setField(userService, "userRepository", userRepository);

	}

	@Test
	public void testCreation() {
		Assert.assertNotNull(userRepository);
		Assert.assertNotNull(userService);

	}

	@Test
	public void save() {
		User user = new User();
		user.setEmail(USER_EMAIL);
		user.setPassword(USER_PASSWORD);

		userService.save(user);

		ArgumentCaptor<User> userAccountArgument = ArgumentCaptor.forClass(User.class);
		Mockito.verify(userRepository, Mockito.times(1)).save(userAccountArgument.capture());
		Mockito.verifyNoMoreInteractions(userRepository);

		User createdUserAccount = userAccountArgument.getValue();

		Assert.assertEquals(createdUserAccount.getEmail(), USER_EMAIL);
		Assert.assertEquals(createdUserAccount.getPassword(), USER_PASSWORD);

	}

	@Test
	public void findByEmail() {
		User user = new User();
		user.setEmail(USER_EMAIL);
		user.setPassword(USER_PASSWORD);

		Mockito.when(userRepository.findByEmail(USER_EMAIL)).thenReturn(user);

		User actual = userService.findUserByEmail(USER_EMAIL);

		Mockito.verify(userRepository, Mockito.times(1)).findByEmail(USER_EMAIL);
		Mockito.verifyNoMoreInteractions(userRepository);

		Assert.assertEquals(user.getEmail(), actual.getEmail());
		Assert.assertEquals(user.getPassword(), actual.getPassword());

	}

}

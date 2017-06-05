package test.auth.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import auth.dao.UserRepository;
import auth.model.User;
import auth.service.UserService;
import auth.service.UserServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	private static final String USER_EMAIL = "mail@mail.com";
	private static final String USER_PASSWORD = "Password1";

	@InjectMocks
	private UserService userService = new UserServiceImpl();

	@Mock
	private UserRepository mockUserRepository;

	@Test
	public void testCreation() {
		Assert.assertNotNull(mockUserRepository);
		Assert.assertNotNull(userService);

	}

	@Test
	public void save() {
		User user = new User();
		user.setEmail(USER_EMAIL);
		user.setPassword(USER_PASSWORD);

		userService.save(user);

		ArgumentCaptor<User> userAccountArgument = ArgumentCaptor.forClass(User.class);
		verify(mockUserRepository, times(1)).save(userAccountArgument.capture());
		verifyNoMoreInteractions(mockUserRepository);

		User createdUserAccount = userAccountArgument.getValue();

		Assert.assertEquals(createdUserAccount.getEmail(), USER_EMAIL);
		Assert.assertEquals(createdUserAccount.getPassword(), USER_PASSWORD);

	}

	@Test
	public void findByEmail() {
		User user = new User();
		user.setEmail(USER_EMAIL);
		user.setPassword(USER_PASSWORD);

		when(mockUserRepository.findByEmail(USER_EMAIL)).thenReturn(user);

		User actual = userService.findUserByEmail(USER_EMAIL);

		verify(mockUserRepository, times(1)).findByEmail(USER_EMAIL);
		verifyNoMoreInteractions(mockUserRepository);

		Assert.assertEquals(user.getEmail(), actual.getEmail());
		Assert.assertEquals(user.getPassword(), actual.getPassword());

	}

}
package auth.test.unit.service;

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
import auth.test.TestUtils;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

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
		User user = new User(TestUtils.CORRECT_EMAIL, TestUtils.CORRECT_PASSWORD, TestUtils.CORRECT_PASSWORD);

		userService.save(user);

		ArgumentCaptor<User> userAccountArgument = ArgumentCaptor.forClass(User.class);
		verify(mockUserRepository, times(1)).save(userAccountArgument.capture());
		verifyNoMoreInteractions(mockUserRepository);

		User createdUserAccount = userAccountArgument.getValue();

		Assert.assertEquals(createdUserAccount.getEmail(), TestUtils.CORRECT_EMAIL);
		Assert.assertEquals(createdUserAccount.getPassword(), TestUtils.CORRECT_PASSWORD);

	}

	@Test
	public void findByEmail() {
		User user = new User(TestUtils.CORRECT_EMAIL, TestUtils.CORRECT_PASSWORD, TestUtils.CORRECT_PASSWORD);

		when(mockUserRepository.findByEmail(TestUtils.CORRECT_EMAIL)).thenReturn(user);

		User actual = userService.findUserByEmail(TestUtils.CORRECT_EMAIL);

		verify(mockUserRepository, times(1)).findByEmail(TestUtils.CORRECT_EMAIL);
		verifyNoMoreInteractions(mockUserRepository);

		Assert.assertEquals(user.getEmail(), actual.getEmail());
		Assert.assertEquals(user.getPassword(), actual.getPassword());

	}

}

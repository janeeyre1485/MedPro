package auth.test.unit.model;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import auth.dao.UserRoleRepository;
import auth.model.User;
import auth.model.UserListener;
import auth.model.UserRole;
import auth.test.TestUtils;

@RunWith(MockitoJUnitRunner.class)
public class UserListenerTest {

	private UserListener userListener;

	@Mock
	private PasswordEncoder mockPasswordEncoder;

	@Mock
	private UserRoleRepository mockUserRoleRepository;

	@Before
	public void setUp() {
		userListener = new UserListener() {
			@Override
			public PasswordEncoder getPasswordEncoder() {
				return mockPasswordEncoder;
			}

			@Override
			public UserRoleRepository getUserRoleRepository() {
				return mockUserRoleRepository;
			}
		};
	}

	@Test
	public void testOnSave() throws Exception {
		User user = new User(TestUtils.CORRECT_EMAIL, TestUtils.CORRECT_PASSWORD, TestUtils.CORRECT_PASSWORD);
		UserRole userRole = new UserRole((long) 1, "ROLE_USER");

		when(mockPasswordEncoder.encode(TestUtils.CORRECT_PASSWORD)).thenReturn(TestUtils.ENCODED_PASSWORD);
		when(mockUserRoleRepository.findByName("ROLE_USER")).thenReturn(userRole);

		userListener.onSave(user);

		Assert.assertEquals("ROLE_USER", user.getUserRoles().get(0).getName());
		Assert.assertEquals(user.getPassword(), TestUtils.ENCODED_PASSWORD);
	}

}

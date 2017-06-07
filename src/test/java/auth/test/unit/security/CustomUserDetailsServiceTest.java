package auth.test.unit.security;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import auth.dao.UserRepository;
import auth.model.User;
import auth.security.CustomUserDetailsService;
import auth.test.TestUtils;

@RunWith(MockitoJUnitRunner.class)
public class CustomUserDetailsServiceTest {

	@Mock
	private UserRepository mockUserRepository;

	@InjectMocks
	private CustomUserDetailsService mockCustomUserDetailsService;

	@Test
	public void testLoadByEmail() throws Exception {
		User user = new User();
		user.setEmail(TestUtils.EMAIL_FOUND);

		when(mockUserRepository.findByEmail(TestUtils.EMAIL_FOUND)).thenReturn(user);
		UserDetails actual = mockCustomUserDetailsService.loadUserByUsername(TestUtils.EMAIL_FOUND);

		Assert.assertEquals(actual.getUsername(), TestUtils.EMAIL_FOUND);
	}

	@Test(expected = UsernameNotFoundException.class)
	public void testLoadByEmail_emailNotFound_throwsException() throws Exception {
		when(mockUserRepository.findByEmail(TestUtils.EMAIL_NOT_FOUND)).thenReturn(null);
		mockCustomUserDetailsService.loadUserByUsername(TestUtils.EMAIL_NOT_FOUND);

	}
}

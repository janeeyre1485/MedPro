package test.auth.security;

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

@RunWith(MockitoJUnitRunner.class)
public class CustomUserDetailsServiceTest {

	private static final String EMAIL_NOT_FOUND = "abc";
	private static final String EMAIL_FOUND = "user@test.com";

	@Mock
	private UserRepository mockUserRepository;

	@InjectMocks
	private CustomUserDetailsService mockCustomUserDetailsService;


	@Test
	public void testLoadByEmail() throws Exception{
		User user = new User();
		user.setEmail(EMAIL_FOUND);
		
		when(mockUserRepository.findByEmail(EMAIL_FOUND)).thenReturn(user);
		UserDetails actual = mockCustomUserDetailsService.loadUserByUsername(EMAIL_FOUND);
		
		Assert.assertEquals(actual.getUsername(), EMAIL_FOUND);
	}

	@Test(expected = UsernameNotFoundException.class)
	public void testLoadByEmail_emailNotFound_throwsException() throws Exception {
		when(mockUserRepository.findByEmail(EMAIL_NOT_FOUND)).thenReturn(null);
		mockCustomUserDetailsService.loadUserByUsername(EMAIL_NOT_FOUND);

	}
}

package test.auth.security;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import auth.dao.UserRepository;
import auth.model.User;
import auth.security.CustomUserDetailsService;

public class CustomUserDetailsServiceTest {

	private static final String EMAIL_NOT_FOUND = "abc";
	private static final String EMAIL_FOUND = "user@test.com";

	@Mock
	private UserRepository mockUserRepository;

	@InjectMocks
	private CustomUserDetailsService mockCustomUserDetailsService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

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

package test.auth.model;


import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import auth.model.User;
import auth.model.UserListener;

@RunWith(MockitoJUnitRunner.class)
public class UserListenerTest {

	private static final String PASSWORD = "Password";
	private static final String ENCODED_PASSWORD = "EncodedPassword";
	
	private UserListener userListener ;

	@Mock
	private PasswordEncoder mockPasswordEncoder;
	
	@Before
	public void setUp(){
		userListener = new UserListener(){
			@Override
			public PasswordEncoder getPasswordEncoder(){
				return mockPasswordEncoder;
			}
		};
	}
	
	@Test
	public void testOnSave() throws Exception {
		User user = new User();
		user.setEmail("mail@mail.com");
		user.setPassword(PASSWORD);
		
		when(mockPasswordEncoder.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);
		
		userListener.onSave(user);
		Assert.assertEquals(user.getPassword(),ENCODED_PASSWORD);
	}

}

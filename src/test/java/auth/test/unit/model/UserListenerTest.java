package auth.test.unit.model;


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
import auth.test.TestUtils;

@RunWith(MockitoJUnitRunner.class)
public class UserListenerTest {


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
		user.setEmail(TestUtils.CORRECT_EMAIL);
		user.setPassword(TestUtils.CORRECT_PASSWORD);
		
		when(mockPasswordEncoder.encode(TestUtils.CORRECT_PASSWORD)).thenReturn(TestUtils.ENCODED_PASSWORD);
		
		userListener.onSave(user);
		
		Assert.assertEquals(user.getPassword(),TestUtils.ENCODED_PASSWORD);
	}

}

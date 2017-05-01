package test.auth.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import auth.AuthAppApplication;
import auth.config.AppConfig;
import auth.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {AuthAppApplication.class, AppConfig.class})
public class UserTest {

	@Test
	public void test(){
		User user = new User();
		user.setEmail("mail@mail.com");
		user.setPassword("pass");
		
		System.out.println(user);
	}
}

package test.auth.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import auth.AuthAppApplication;
import auth.config.AppConfig;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {AuthAppApplication.class, AppConfig.class})
public class UserTest {

	@Test
	public void test(){
		System.out.println("Test method");
	}
}

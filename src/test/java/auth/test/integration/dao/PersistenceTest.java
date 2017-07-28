package auth.test.integration.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import auth.dao.UserRepository;
import auth.dao.UserRoleRepository;
import auth.model.User;
import auth.model.UserRole;
import auth.test.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PersistenceTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Before
	public void setUp() {
		userRoleRepository.save(new UserRole((long) 1, "ROLE_USER"));
		userRoleRepository.save(new UserRole((long) 2, "ROLE_ADMIN"));
	}

	@Test
	public void testSave_validUser() {
		User user = new User(TestUtils.CORRECT_EMAIL, TestUtils.CORRECT_PASSWORD, TestUtils.CORRECT_PASSWORD);
		userRepository.save(user);

		User user1 = userRepository.findByEmail(TestUtils.CORRECT_EMAIL);

		Assert.assertNotNull(user1);

	}

	@Test
	public void testSave_noRoles_userDefault() {
		User user = new User(TestUtils.CORRECT_EMAIL, TestUtils.CORRECT_PASSWORD, TestUtils.CORRECT_PASSWORD);
		userRepository.save(user);

		User user1 = userRepository.findByEmail(TestUtils.CORRECT_EMAIL);

		Assert.assertEquals(user1.getUserRoles().size(), 1);
		Assert.assertEquals(user1.getUserRoles().get(0).getName(), "ROLE_USER");
	}
	
	@Test
	public void testSave_adminRole() {
		User user = new User(TestUtils.CORRECT_EMAIL, TestUtils.CORRECT_PASSWORD, TestUtils.CORRECT_PASSWORD);
		
		List<UserRole> userRoles = new ArrayList<UserRole>();
		userRoles.add(userRoleRepository.findByName("ROLE_ADMIN"));
		user.setUserRoles(userRoles);
		
		userRepository.save(user);

		User user1 = userRepository.findByEmail(TestUtils.CORRECT_EMAIL);

		Assert.assertEquals(user1.getUserRoles().size(), 1);
		Assert.assertEquals(user1.getUserRoles().get(0).getName(), "ROLE_ADMIN");
	}

	@Test
	public void testEncodedPassword() {
		User user = new User(TestUtils.CORRECT_EMAIL, TestUtils.CORRECT_PASSWORD, TestUtils.CORRECT_PASSWORD);
		userRepository.save(user);

		User user1 = userRepository.findByEmail(TestUtils.CORRECT_EMAIL);
		Assert.assertEquals(passwordEncoder.matches(TestUtils.CORRECT_PASSWORD, user1.getPassword()), true);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void testSave_emailNotUnique() {
		User user = new User(TestUtils.CORRECT_EMAIL, TestUtils.CORRECT_PASSWORD, TestUtils.CORRECT_PASSWORD);
		userRepository.save(user);

		User user1 = new User(TestUtils.CORRECT_EMAIL, TestUtils.CORRECT_PASSWORD, TestUtils.CORRECT_PASSWORD);
		userRepository.save(user1);

	}
}

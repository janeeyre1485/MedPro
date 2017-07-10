package auth.test.integration.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import auth.dao.UserRepository;
import auth.model.User;
import auth.test.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PersistenceTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	public void testSave_validUser() {

		User user = new User(TestUtils.CORRECT_EMAIL, TestUtils.CORRECT_PASSWORD, TestUtils.CORRECT_PASSWORD);

		userRepository.save(user);

		User user1 = userRepository.findByEmail(TestUtils.CORRECT_EMAIL);

		Assert.assertNotNull(user1);

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

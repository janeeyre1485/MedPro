package auth.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import auth.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
}

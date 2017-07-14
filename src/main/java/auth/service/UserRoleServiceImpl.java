package auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auth.dao.UserRoleRepository;
import auth.model.UserRole;

@Service
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public UserRole findByName(String name) {
		return userRoleRepository.findByName(name);
	}

	@Override
	public List<UserRole> findAll() {
		return userRoleRepository.findAll();
	}

	@Override
	public UserRole findById(Long id) {
		return userRoleRepository.findById(id);
	}

}

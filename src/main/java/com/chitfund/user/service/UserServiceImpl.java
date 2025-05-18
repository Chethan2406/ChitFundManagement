package com.chitfund.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.chitfund.user.model.User;
import com.chitfund.user.model.UserRequest;
import com.chitfund.user.repository.UserRepository;
import com.chitfund.util.UserException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final ObjectMapper objectMapper;

	public UserServiceImpl(UserRepository userRepository, ObjectMapper objectMapper) {
		this.userRepository = userRepository;
		this.objectMapper = objectMapper;
	}

	@Override
	public User getUserById(Long id) {
		try {
			return userRepository.findById(id)
					.orElseThrow(() -> new UserException("User not found with id: " + id, null));
		} catch (Exception e) {
			log.error("CFM_USI_001 - Error fetching user by ID", e);
			throw new UserException("Unable to fetch user with id: " + id, e);
		}
	}

	@Override
	public List<User> getAllUser() {
		try {
			List<User> users = userRepository.findAll();
			if (users.isEmpty()) {
				throw new UserException("No users found", null);
			}
			return users;
		} catch (Exception e) {
			log.error("CFM_USI_002 - Error fetching all users", e);
			throw new UserException("Unable to fetch all users", e);
		}
	}

	@Override
	public User saveUser(User user) {
		try {
			return userRepository.save(user);
		} catch (Exception e) {
			log.error("CFM_USI_004 - Error saving user", e);
			throw new UserException("Unable to save user", e);
		}
	}

	@Override
	public User deleteUser(Long id) {
		try {
			User user = getUserById(id);
			user.setDeleteFlag(true);
			return userRepository.save(user);
		} catch (Exception e) {
			log.error("CFM_USI_005 - Error deleting user", e);
			throw new UserException("Unable to delete user with id: " + id, e);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if (user != null) {
			List<GrantedAuthority> authorities = new ArrayList<>();
			return buildUserForAuthentication(user, authorities);
		}
		throw new UsernameNotFoundException("User with '" + email + "' does not exist!");
	}

	private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
		log.info("EPG_US_008 - UserService : buildUserForAuthentication()");
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}

	@Override
	public User saveUser(UserRequest request) {
		try {
			User user = objectMapper.convertValue(request, User.class);
			user = userRepository.save(user);
			return user;
		} catch (DataIntegrityViolationException e) {
			log.error("EPG_US_009 - Data integrity violation while saving user", e);

			String rootCause = Optional.ofNullable(e.getRootCause())
					.map(Throwable::getMessage)
					.orElse("");

			if (rootCause.contains("uk_") || rootCause.contains("Duplicate")) {
				throw new UserException("A user with the same email or phone already exists.");
			}

			throw new UserException("Unable to save user due to data integrity violation.");
		}

		catch (Exception e) {
			log.error("EPG_US_009 - Error saving user", e);
			throw new UserException("Unable to save user", e);
		}
	}

}

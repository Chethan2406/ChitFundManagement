package com.chitfund.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.chitfund.security.JwtTokenProvider;
import com.chitfund.user.model.LoginRequest;
import com.chitfund.user.model.User;
import com.chitfund.user.model.UserRequest;
import com.chitfund.user.repository.UserRepository;
import com.chitfund.user.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Profile("dev")
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	private final BCryptPasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final JwtTokenProvider tokenProvider;
	private final AuthenticationManager authenticationManager;

	public UserController(UserService userService, BCryptPasswordEncoder passwordEncoder, UserRepository userRepository,
			JwtTokenProvider tokenProvider, AuthenticationManager authenticationManager) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.tokenProvider = tokenProvider;
		this.authenticationManager = authenticationManager;
	}

	@PostMapping
	public ResponseEntity<Map<String,Object>> saveUser(@Valid @RequestBody UserRequest request, BindingResult result) {
		log.info("CFM_USR_001 - Saving new UserRequest: {}", request);
			Map<String,Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> err.getField() + ": " + err.getDefaultMessage())
					.collect(Collectors.toList());
			log.warn("CFM_USR_001 - Validation errors: {}", errors);
			response.put("errors", errors);
			response.put("statusCode", 400);

			return ResponseEntity.badRequest().body(response);
		}
		User savedUser = userService.saveUser(request);
		response.put("user", savedUser);
		response.put("statusCode", 200);
		log.info("CFM_USR_001 - User saved successfully with ID: {}", savedUser.getUserId());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Long id) {
		log.info("CFM_UC_002 - getUserById: {}", id);
		User user = userService.getUserById(id);
		return ResponseEntity.ok(user);
	}

	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		log.info("CFM_UC_003 - getAllUsers");
		return ResponseEntity.ok(userService.getAllUser());
	}

	@PutMapping
	public ResponseEntity<?> updateUser(@Valid @RequestBody User user, BindingResult result) {
		log.info("CFM_UC_005 - updateUser: {}", user);

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> err.getField() + ": " + err.getDefaultMessage())
					.collect(Collectors.toList());
			log.warn("CFM_UC_005 - Validation errors: {}", errors);
			return ResponseEntity.badRequest().body(errors);
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User updatedUser = userService.saveUser(user);
		return ResponseEntity.ok(updatedUser);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable long id) {
		log.info("CFM_UC_006 - deleteUser: {}", id);
		User deletedUser = userService.deleteUser(id);
		return ResponseEntity.ok(deletedUser);
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> loginLCO(@RequestBody LoginRequest request) {
		log.debug("CFM_UC_007 - LoginController - login()");

		Map<String, Object> response = new HashMap<>();

		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

			log.info("CFM_UC_007 - Authenticated user: {}", authentication);

			final UserDetails userDetails = userService.loadUserByUsername(request.getEmail());
			User user = userRepository.findByEmail(request.getEmail());

			if (user.getStatus() == 0) {
				response.put("error", "User is inactive");
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
			}

			String jwt = tokenProvider.createToken(userDetails.getUsername());

			response.put("token", jwt);
			response.put("user", user);

			return ResponseEntity.ok(response);

		} catch (AuthenticationException e) {
			log.error("Unexpected error during login", e.getMessage());
			response.put("error", "Invalid username or password");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		} catch (Exception e) {
			log.error("Unexpected error during login", e.getMessage());
			response.put("error", "Internal server error");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
}

package com.project.expense.controller;

import java.net.URI;
import java.util.Collections;

import javax.validation.Valid;

import com.project.expense.model.ReCaptchaResponse;
import com.project.expense.model.Role;
import com.project.expense.model.RoleName;
import com.project.expense.model.User;
import com.project.expense.repo.RoleRepo;
import com.project.expense.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.expense.exception.AppException;
import com.project.expense.repo.UserRepo;

import payloads.ApiResponse;
import payloads.JwtAuthenticationResponse;
import payloads.LoginRequest;
import payloads.SignUpRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private String message;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepo userRepository;

	@Autowired
    RoleRepo roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
    JwtTokenProvider tokenProvider;

	@Autowired
	private RestTemplate restTemplate;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, @RequestParam (name="g-recaptcha-response") String captchaResponse) {
		String url="https://www.google.com/recaptcha/api/siteverify";
		String params = "?secret=6LckUuwUAAAAADzulhLmqzXvEjCtNwj3Hm7tRYyp&response="+captchaResponse;

		ReCaptchaResponse reCaptchaResponse = restTemplate.exchange(url+params, HttpMethod.POST,null,ReCaptchaResponse.class).getBody();

		if(reCaptchaResponse.isSuccess()){
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			String jwt = tokenProvider.generateToken(authentication);
			Long userIDJWT = tokenProvider.getUserIdFromJWT(jwt);

			return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, userIDJWT));

		}else {

			return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		}

	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity(new ApiResponse(false, "Username is already taken!"), HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
		}

		// Creating user's account
		User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
				signUpRequest.getPassword());

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
				.orElseThrow(() -> new AppException("User Role not set."));

		user.setRoles(Collections.singleton(userRole));

		User result = userRepository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/{username}")
				.buildAndExpand(result.getUsername()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
	}
}

package com.codingdojo.loginreg.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.codingdojo.loginreg.models.Role;
import com.codingdojo.loginreg.models.User;
import com.codingdojo.loginreg.repositories.RoleRepo;
import com.codingdojo.loginreg.repositories.UserRepo;

@Service
public class UserService  implements ApplicationListener<AuthenticationSuccessEvent> {

	private UserRepo ur;
	private RoleRepo rr;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public UserService(UserRepo ur,
			RoleRepo rr,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.ur = ur;
		this.rr = rr;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	public List<User> findAllUsers() {
		return ur.findAllUsers();
	}
	
	public Role findRoleByName(String name) {
		if (rr.findByName(name).size() == 0) {
			return null;
		} else {
			return rr.findByName(name).get(0);
		}
	}
	
	public User findUserById(Long id) {
		Optional<User> user = ur.findById(id);
		if (user != null) {
			return user.get();
		}
		return null;
	}
	
	public User findUserByUsername(String username) {
		return ur.findByUsername(username);
	}
	
	public void deleteUserById(Long id) {
		ur.deleteUserById(id);
	}
	
	public void saveWithUserRole(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles(rr.findByName("ROLE_USER"));
		ur.save(user);
	}
	
	public void saveUserWithAdminRole(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles(rr.findByName("ROLE_ADMIN"));
		ur.save(user);
	}

	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		UserDetails user = (UserDetails) event.getAuthentication().getPrincipal();
		User u = ur.findByUsername(user.getUsername());
		u.setLastSignIn(new Date());
		ur.save(u);
	}
	
}

package com.codingdojo.loginreg.services;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codingdojo.loginreg.models.Role;
import com.codingdojo.loginreg.models.User;
import com.codingdojo.loginreg.repositories.UserRepo;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {
	
	@Autowired
	private UserRepo ur;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = ur.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("No user with that email");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user));
	}
	
	private List<GrantedAuthority> getAuthorities(User user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (Role role : user.getRoles()) {
			GrantedAuthority ga = new SimpleGrantedAuthority(role.getName());
			authorities.add(ga);
		}
		return authorities;
	}

}

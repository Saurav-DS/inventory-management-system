package com.saurav.ims.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.saurav.ims.model.Role;
import com.saurav.ims.model.User;

public class CustomUserDetails implements UserDetails{
	
	private final User user;
	
	public CustomUserDetails(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Role> roles = user.getRoles();
		return roles.stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toSet());
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}
	
	@Override
	public boolean isAccountNonExpired() {
	    return user.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
	    return user.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
	    return user.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
	    return user.isEnabled();
	}
}

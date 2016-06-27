package com.psib.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.psib.model.Staff;
import com.psib.service.impl.RoleManager;
import com.psib.service.impl.StaffManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

	private static final Logger LOG = Logger.getLogger(MyUserDetailsService.class);

	@Autowired
	private IStaffManager staffManager;

	@Autowired
	private IRoleManager roleManager;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		LOG.info(new StringBuilder("[loadUserByUsername] Start: username = ").append(username));
		Staff staff = staffManager.getStaffByUsername(username);

		List<GrantedAuthority> authorities = buildUserAuthority(staff.getRoleId());

		LOG.info("[loadUserByUsername] End");
		return buildUserForAuthentication(staff, authorities);
	}

	private User buildUserForAuthentication(Staff staff, List<GrantedAuthority> authorities) {
		LOG.info(new StringBuilder("[buildUserForAuthentication] Start: username = ").append(staff.getUsername()));
		LOG.info("[buildUserForAuthentication] End");
		return new User(staff.getUsername(), staff.getPassword(), true, true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(int userRoles) {
		LOG.info(new StringBuilder("[buildUserAuthority] Start: userRoles = ").append(userRoles));
		Set<GrantedAuthority> setAuths = new HashSet<>();

		// Build user's authorities
		String role = roleManager.getRoleById(userRoles).getName();
		setAuths.add(new SimpleGrantedAuthority(role));

		List<GrantedAuthority> Result = new ArrayList<>(setAuths);

		LOG.info("[buildUserAuthority] End");
		return Result;
	}
}

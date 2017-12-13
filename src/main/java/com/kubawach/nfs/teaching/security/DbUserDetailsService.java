package com.kubawach.nfs.teaching.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.kubawach.nfs.teaching.dao.UserDao;
import com.kubawach.nfs.teaching.model.User;

public class DbUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(DbUserDetailsService.class);
    
    @Autowired private UserDao userRepository;
    
	@Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
		
		String lowercaseLogin = login.toLowerCase().trim();
        User user = userRepository.findOne(lowercaseLogin);
        if (user == null) {
            log.error("User {} not found!", login);
            throw new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database");
        }
        return new org.springframework.security.core.userdetails.User(lowercaseLogin, user.getPasswordHash(), SecurityUtils.toAuthorityList(user.getRoles()));
    }
}

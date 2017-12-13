package com.kubawach.nfs.teaching.service;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kubawach.nfs.teaching.dao.UserDao;
import com.kubawach.nfs.teaching.model.User;
import com.kubawach.nfs.teaching.web.UserFactory;

@Service
public class UserService {

    private static final Logger log = Logger.getLogger(UserService.class);
    
    @Autowired private UserDao dao;
    @Autowired private SecurityService securityService;
    
    public User saveNewUserIfNotPresent(String userLogin) {
        
        User user = dao.findOne(userLogin);
        if (user == null) {
            user = UserFactory.newUser(userLogin);
            dao.save(user);
        }
        return user;
    }
    
    public void update(User user) {
        User original = dao.findOne(user.getLogin());
        if (original == null) {
            throw new IllegalArgumentException("User with login: "+user.getLogin()+" does not exist.");
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            user.setPasswordHash(original.getPasswordHash());
        } else {
            user.setPasswordHash(securityService.hashPassword(user.getPassword()));
        }
        dao.save(user);
    }
    
    public User userLoggedIn(String userLogin) {
        
        // credentials ok - save or retrieve
        User user = saveNewUserIfNotPresent(userLogin);
        log.info("Logged in: '"+userLogin+"'");
        return user;
    }
}

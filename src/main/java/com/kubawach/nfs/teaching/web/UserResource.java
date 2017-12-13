package com.kubawach.nfs.teaching.web;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kubawach.nfs.teaching.dao.UserDao;
import com.kubawach.nfs.teaching.model.Role;
import com.kubawach.nfs.teaching.model.User;
import com.kubawach.nfs.teaching.security.SecurityContext;
import com.kubawach.nfs.teaching.service.SamlService;
import com.kubawach.nfs.teaching.service.UserService;

@Controller
@RequestMapping("users")
@Transactional
public class UserResource {
    
    @Autowired private UserDao dao;
    @Autowired private SamlService samlService;
    @Autowired private UserService userService;
    
    @RequestMapping(method=RequestMethod.GET)
    @ResponseBody
    @Secured({Role.ASSISTANT, Role.ADMIN})
    public Iterable<User> findAllUsers() throws Exception {
        return dao.findAll();
    }
        
    @RequestMapping(method=RequestMethod.GET, value="current")
    @ResponseBody
    public User getCurrent() {
        return dao.findOne(SecurityContext.currentUser());
    }
    
    @RequestMapping(method=RequestMethod.GET, value="token")
    @ResponseBody
    public User loginWithToken(@RequestParam String token) throws Exception {
        
        Validate.notEmpty(token);
        
        String login = samlService.validateToken(token);
        if (login == null) {
            throw new RuntimeException("Could not verify user token!");
        }
        return userService.userLoggedIn(login);
    }
    
    @RequestMapping(method=RequestMethod.POST)
    @ResponseBody
    @Secured({Role.ASSISTANT, Role.ADMIN})
    public User saveNewStudent(@RequestBody String login) {
        
        Validate.notEmpty(login);        
        return userService.saveNewUserIfNotPresent(login);
    }
    
    @RequestMapping(method=RequestMethod.PUT)
    @ResponseBody
    @Secured({Role.ADMIN})
    public void update(@RequestBody User user) {

        Validate.notNull(user);
        userService.update(user);
    }
    
    @RequestMapping(method=RequestMethod.DELETE)
    @ResponseBody
    @Secured({Role.ADMIN})
    public void remove(@RequestBody String login) {
        
        Validate.notEmpty(login);        
        dao.delete(login);
    }
}

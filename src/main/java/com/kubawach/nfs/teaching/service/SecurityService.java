package com.kubawach.nfs.teaching.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kubawach.nfs.teaching.model.HasOwner;
import com.kubawach.nfs.teaching.security.SecurityContext;

@Service
public class SecurityService {

    @Autowired private PasswordEncoder passwordEncoder;
    
    public void ownershipCheck(HasOwner hasOwner) {
        String login = SecurityContext.currentUser();
        if (!login.equals(hasOwner.getUser().getLogin())) {
            throw new RuntimeException("User "+login+" is not owner of the object.");
        }
    }
    
    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }
    
    public static void main(String[] args) {
        System.out.println(new StandardPasswordEncoder().encode(args[0]));
    }
}

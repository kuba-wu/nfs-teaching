package com.kubawach.nfs.teaching.security;

import java.util.Collections;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

public class SecurityContext {

    public static String currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth == null ? "anonymous" : auth.getName());
    }
    
    public static Set<String> currentUserAuthorities() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth == null ? Collections.<String>emptySet() : FluentIterable.from(auth.getAuthorities()).transform(new Function<GrantedAuthority, String>() {
            @Override public String apply(GrantedAuthority input) {
                return input.getAuthority();
            }
        }).toSet());
    }
}

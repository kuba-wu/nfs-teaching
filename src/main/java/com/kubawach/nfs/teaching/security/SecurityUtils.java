package com.kubawach.nfs.teaching.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

public class SecurityUtils {

    public static List<SimpleGrantedAuthority> toAuthorityList(List<String> roles) {
        return FluentIterable.from(roles).transform(new Function<String, SimpleGrantedAuthority>() {
            @Override public SimpleGrantedAuthority apply(String input) {
                return new SimpleGrantedAuthority(input);
            }
        }).toList();
    }

}

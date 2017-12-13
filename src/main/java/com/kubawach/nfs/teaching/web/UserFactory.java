package com.kubawach.nfs.teaching.web;

import com.google.common.collect.Lists;
import com.kubawach.nfs.teaching.model.Role;
import com.kubawach.nfs.teaching.model.User;

public class UserFactory {

    public static User newUser(String login) {
        return new User(login, null, Lists.newArrayList(Role.STUDENT), null);
    }
}

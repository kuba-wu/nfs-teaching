package com.kubawach.nfs.teaching.dao;

import org.springframework.data.repository.CrudRepository;

import com.kubawach.nfs.teaching.model.User;

public interface UserDao extends CrudRepository<User, String> {

}

package com.kubawach.nfs.teaching.model;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="USER")
public class User {

    @Id @Column(name="LOGIN") private String login;
    @Column(name="PASSWORD_HASH") @JsonIgnore private String passwordHash;
    @ElementCollection(fetch=FetchType.EAGER) @CollectionTable(name="USER_ROLE", joinColumns=@JoinColumn(name="USER_ID")) private List<String> roles;
    @Transient private String password;
}

package com.kubawach.nfs.teaching.model.task;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kubawach.nfs.core.model.system.SerializedSystem;
import com.kubawach.nfs.core.model.system.System;
import com.kubawach.nfs.teaching.model.HasOwner;
import com.kubawach.nfs.teaching.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"objectMapper"})
@Entity 
@Table(name="SOLUTION")
public class Solution implements HasOwner {

    @Transient @Autowired private ObjectMapper objectMapper;
    
    @Id @GeneratedValue(generator="system-uuid") @GenericGenerator(name="system-uuid", strategy = "uuid") private String id;
    @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="SYSTEM_ID") private SerializedSystem system;
    @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="USER_ID") private User user;
    @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="EXERCISE_ID") private Exercise exercise;
    @Column private Date savedOn;
    @Column private String comment;
    
    @JsonIgnore
    public SerializedSystem getSerializedSystem() {
        return system;
    }
    
    public void setSystem(System system) {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        this.system = system.toSerialized(objectMapper);
    }
    
    public System getSystem() {
        return system.toSystem(objectMapper);
    }
    
    @PostLoad
    public void initialize() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
}

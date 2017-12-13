package com.kubawach.nfs.teaching.model.task;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kubawach.nfs.core.model.task.Task;
import com.kubawach.nfs.teaching.model.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="EXERCISE")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Exercise {

    @Id @GeneratedValue(generator="system-uuid") @GenericGenerator(name="system-uuid", strategy = "uuid") private String id;
    
    @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="TASK_ID") private Task task;
    @Column private Date start;
    @Column private Date end;
    
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            name="EXERCISE_ASSIGNEE",
            joinColumns={@JoinColumn(name="EXERCISE_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="USER_LOGIN", referencedColumnName="login")})
    private List<User> assignees;
    
    @JsonIgnore
    public boolean isActive() {
        Date now = new Date();
        return (end.after(now) && start.before(now));
    }
}

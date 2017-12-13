package com.kubawach.nfs.teaching.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kubawach.nfs.teaching.model.task.Exercise;

public interface ExerciseDao extends JpaRepository<Exercise, String>{

    @Query("select ex from Exercise ex inner join ex.assignees assign where ex.start <= CURRENT_DATE and ex.end >= CURRENT_DATE and assign.login = ?1")
    public List<Exercise> findActiveForUser(String login);
    
    @Query("select ex from Exercise ex inner join ex.assignees assign where ex.end < CURRENT_DATE and assign.login = ?1")
    public List<Exercise> findPastForUser(String login);
    
    @Query("select ex from Exercise ex where (ex.task.id = ?1 or ?1 is null) and ex.start >= ?2 and ex.end <= ?3")
    public List<Exercise> findByFilter(String taskId, Date start, Date end);
    
    @Query("select ex from Exercise ex where (ex.task.id = ?1)")
    public List<Exercise> findForTask(String taskId);
}

package com.kubawach.nfs.teaching.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.kubawach.nfs.teaching.model.task.Solution;

public interface SolutionDao extends JpaRepository<Solution, String>{

    @Query("select ex from Solution ex where ex.exercise.id = ?1 and ex.user.login = ?2")
    public Solution findForExerciseAndUser(String exerciseId, String login);
    
    @Query("select ex from Solution ex where ex.exercise.id = ?1")
    public List<Solution> findForExercise(String exerciseId);
    
    @Query("delete from Solution so where so.exercise.id = ?1")
    @Modifying
    public void deleteForExercise(String exerciseId);
}

package com.kubawach.nfs.teaching.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kubawach.nfs.core.dao.system.SerializedSystemDao;
import com.kubawach.nfs.core.model.system.SerializedSystem;
import com.kubawach.nfs.teaching.dao.ExerciseDao;
import com.kubawach.nfs.teaching.dao.SolutionDao;
import com.kubawach.nfs.teaching.dao.UserDao;
import com.kubawach.nfs.teaching.model.User;
import com.kubawach.nfs.teaching.model.task.Exercise;
import com.kubawach.nfs.teaching.model.task.Solution;

@Service
@Transactional
public class SolutionService { 

    @Autowired private SolutionDao solutionDao;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private ExerciseDao exerciseDao;
    @Autowired private UserDao userDao;
    @Autowired private SerializedSystemDao systemDao;

    private Solution findById(String id) {
        Solution original = solutionDao.getOne(id);
        if (original == null) {
            throw new RuntimeException("Solution with id: "+id+" not found.");
        }
        return original;
    }
    
    private void checkActive(Solution solution) {
        if (!solution.getExercise().isActive()) {
            throw new RuntimeException("Exercise not active.");
        }
    }
    
    public Solution reset(String solutionId) {
        Solution solution = findById(solutionId);
        checkActive(solution);
        solution.getSerializedSystem().setValue(solution.getExercise().getTask().getSerializedSystem().getValue());
        solutionDao.save(solution);
        return solution;
    }
    
    public void save(Solution solution) {
        String id = solution.getId();

        Solution original = findById(id);
        checkActive(original);
        original.setComment(solution.getComment());
        original.setSavedOn(new Date());
        original.getSerializedSystem().setValue(solution.getSerializedSystem().getValue());
        solutionDao.save(original);
    }
    
    public Solution getOrCreateFor(String exerciseId, String login) {
        
        Solution solution = solutionDao.findForExerciseAndUser(exerciseId, login);
        if (solution == null) {
            solution = createFor(exerciseId, login);
            solutionDao.save(solution);
        }
        return solution;
    }
    
    private Solution createFor(String exerciseId, String login) {
        
        Exercise exercise = exerciseDao.getOne(exerciseId);
        User user = userDao.findOne(login);
        SerializedSystem system = exercise.getTask().getSerializedSystem();
        SerializedSystem newSystem = new SerializedSystem(null, system.getId(), system.getValue());
        systemDao.save(newSystem);
        
        return new Solution(objectMapper, null, newSystem, user, exercise, new Date(), "");
    }
}

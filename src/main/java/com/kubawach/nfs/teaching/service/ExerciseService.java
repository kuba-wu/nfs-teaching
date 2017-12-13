package com.kubawach.nfs.teaching.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kubawach.nfs.teaching.dao.ExerciseDao;
import com.kubawach.nfs.teaching.dao.SolutionDao;
import com.kubawach.nfs.teaching.model.task.Exercise;

@Service
public class ExerciseService {

    private static final Logger log = Logger.getLogger(ExerciseService.class);
    
    @Autowired private ExerciseDao dao;
    @Autowired private SolutionDao solutionDao;
    
    public List<Exercise> findByFilter(String taskId, Date startParam, Date endParam) {
        
        log.info("Searching for exercises: taskId="+taskId+", start="+startParam+", end="+endParam);
        Date start = (startParam == null ? new Date(0) : startParam);
        Date end = (endParam == null ? new Date(Long.MAX_VALUE) : endParam);
        return dao.findByFilter(taskId, start, end);
    }
    
    public void remove(String id) {
        dao.delete(id);
        solutionDao.deleteForExercise(id);
    }
}
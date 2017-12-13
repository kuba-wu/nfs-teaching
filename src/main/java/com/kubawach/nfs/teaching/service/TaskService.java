package com.kubawach.nfs.teaching.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kubawach.nfs.core.dao.system.SerializedSystemDao;
import com.kubawach.nfs.core.dao.task.TaskDao;
import com.kubawach.nfs.core.model.system.SerializedSystem;
import com.kubawach.nfs.core.model.task.Task;
import com.kubawach.nfs.teaching.dao.ExerciseDao;
import com.kubawach.nfs.teaching.model.task.Exercise;

@Service
public class TaskService {

    @Autowired private TaskDao taskDao;
    @Autowired private ExerciseDao exerciseDao;
    @Autowired private SerializedSystemDao serializedSystemDao;
    @Autowired private ExerciseService exerciseService;
    
    public void remove(String id) {
        
        List<Exercise> exercises = exerciseDao.findForTask(id);
        for (Exercise exercise : exercises) {
            exerciseService.remove(exercise.getId());
        }
        taskDao.delete(id);
    }
    
    public void save(Task task) {
        
        SerializedSystem system = serializedSystemDao.save(task.getSerializedSystem());
        task.setSerializedSystem(system);
        taskDao.save(task);
    }
}

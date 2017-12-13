package com.kubawach.nfs.teaching.web;

import static com.kubawach.nfs.teaching.security.SecurityContext.currentUser;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kubawach.nfs.teaching.dao.ExerciseDao;
import com.kubawach.nfs.teaching.dto.ExerciseFilterDto;
import com.kubawach.nfs.teaching.model.Role;
import com.kubawach.nfs.teaching.model.task.Exercise;
import com.kubawach.nfs.teaching.service.ExerciseService;

@Controller
@RequestMapping("exercises")
@Transactional
public class ExerciseResource {

    @Autowired private ExerciseDao exerciseDao;
    @Autowired private ExerciseService exerciseService;
    
    
    @RequestMapping(method=RequestMethod.GET, value="active")
    @ResponseBody
    public List<Exercise> findActiveForUser() {
        return exerciseDao.findActiveForUser(currentUser());
    }
    
    @RequestMapping(method=RequestMethod.GET, value="past")
    @ResponseBody
    public List<Exercise> findPastForUser() {
        return exerciseDao.findPastForUser(currentUser());
    }
    
    @RequestMapping(method=RequestMethod.GET, value="")
    @ResponseBody
    @Secured({Role.ASSISTANT})
    public List<Exercise> findAll() {
        
        return exerciseDao.findAll();
    }
    
    @RequestMapping(method=RequestMethod.POST, value="/filter")
    @ResponseBody
    @Secured({Role.ASSISTANT})
    public List<Exercise> findByFilter(@RequestBody ExerciseFilterDto filter) {
        
        Validate.notNull(filter);
        return exerciseService.findByFilter(filter.getTaskId(), filter.getStart(), filter.getEnd());
    }
    
    @RequestMapping(method=RequestMethod.POST)
    @ResponseBody
    @Secured({Role.ASSISTANT})
    public void save(@RequestBody Exercise exercise) {

        Validate.notNull(exercise);
        exerciseDao.save(exercise);
    }
    
    @RequestMapping(method=RequestMethod.DELETE, value="/{id}")
    @ResponseBody
    @Secured({Role.ASSISTANT})
    public void delete(@PathVariable String id) {
        
        Validate.notNull(id);
        exerciseService.remove(id);
    }
}

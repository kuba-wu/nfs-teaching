package com.kubawach.nfs.teaching.web;

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

import com.kubawach.nfs.core.dao.task.TaskDao;
import com.kubawach.nfs.core.model.task.Task;
import com.kubawach.nfs.teaching.model.Role;
import com.kubawach.nfs.teaching.service.TaskService;

@Controller
@RequestMapping("task")
@Transactional
public class AssignedTaskResource {

    @Autowired private TaskDao taskDao;
    @Autowired private TaskService taskService;
    
    @RequestMapping(method=RequestMethod.GET)
    @ResponseBody
    @Secured({Role.ASSISTANT})
    public Iterable<Task> findAll() {
        return taskDao.findAll();
    }

    
    @RequestMapping(method=RequestMethod.DELETE, value="/{id}")
    @ResponseBody
    @Secured({Role.ASSISTANT})
    public void delete(@PathVariable String id) {
        
        Validate.notEmpty(id);
        taskService.remove(id);
    }
    
    @RequestMapping(method=RequestMethod.POST)
    @ResponseBody
    @Secured({Role.ASSISTANT})
    public void save(@RequestBody Task task) {
        
        Validate.notNull(task);
        taskService.save(task);
    }
}

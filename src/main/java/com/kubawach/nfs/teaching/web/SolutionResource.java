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

import com.kubawach.nfs.teaching.dao.SolutionDao;
import com.kubawach.nfs.teaching.model.Role;
import com.kubawach.nfs.teaching.model.task.Solution;
import com.kubawach.nfs.teaching.service.SecurityService;
import com.kubawach.nfs.teaching.service.SolutionService;

@Controller
@RequestMapping("solution")
@Transactional
public class SolutionResource {

    @Autowired private SecurityService securityService;
    @Autowired private SolutionService solutionService;
    @Autowired private SolutionDao solutionDao;

    @RequestMapping(method=RequestMethod.GET, value="/user/any/exercise/{exercise}")
    @ResponseBody
    @Secured({Role.ASSISTANT})
    public List<Solution> findForExercise(@PathVariable String exercise) {
        
        Validate.notEmpty(exercise);
        return solutionDao.findForExercise(exercise);
    }
    
    @RequestMapping(method=RequestMethod.GET, value="/user/current/exercise/{exercise}")
    @ResponseBody
    public Solution findForExerciseAndCurrentUser(@PathVariable String exercise) {
        
        Validate.notEmpty(exercise);
        return solutionService.getOrCreateFor(exercise, currentUser());
    }
    
    @RequestMapping(value="", method=RequestMethod.POST)
    @ResponseBody
    public void saveSolution(@RequestBody final Solution solution) {
        
        Validate.notNull(solution);
        securityService.ownershipCheck(solutionDao.findOne(solution.getId()));
        solutionService.save(solution);
    }
    
    @RequestMapping(value="/{solutionId}/reset", method=RequestMethod.GET)
    @ResponseBody
    public Solution resetSolution(@PathVariable String solutionId) {

        Validate.notEmpty(solutionId);
        securityService.ownershipCheck(solutionDao.findOne(solutionId));
        return solutionService.reset(solutionId);
    }
}

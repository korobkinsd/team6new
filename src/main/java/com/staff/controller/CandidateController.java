package com.staff.controller;

import com.staff.dao.CandidateDao;
import com.staff.model.Candidate;
import com.staff.model.Candidate_;
import com.staff.util.filtering.CandidateFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
public class CandidateController {
    @Autowired
    private CandidateDao candidateDao;

    public void setCandidateDao(CandidateDao candidateDao){
        this.candidateDao = candidateDao;
    }

//    public CandidateDao getCandidateDao(){
//        return this.candidateDao;
//    }


    private final Logger logger = LoggerFactory.getLogger(CandidateController.class);

    @PostMapping("/candidate")
    public ResponseEntity<?> save(@RequestBody Candidate candidate) {
        long id = candidateDao.save(candidate);
        return ResponseEntity.ok().body("New candidate has been saved with ID:" + id);
    }

    @GetMapping("/candidate/{id}")
    public ResponseEntity<Candidate> get(@PathVariable("id") long id) {
        Candidate candidate = candidateDao.get(id);
        return ResponseEntity.ok().body(candidate);
    }
    @GetMapping("/candidate")
    public ResponseEntity<List<Candidate>> list(
                                           @RequestParam(value = "name", defaultValue = "")String name,
                                           @RequestParam(value = "surname", defaultValue = "")String surname,
                                           @RequestParam(value = "birthdayFrom", defaultValue = "")String birthdayFrom,
                                           @RequestParam(value = "birthdayTo", defaultValue = "")String birthdayTo,
                                           @RequestParam(value = "salaryFrom", defaultValue = "")String salaryFrom,
                                           @RequestParam(value = "salaryTo", defaultValue = "")String salaryTo,
                                           @RequestParam(value = "listCandidateStates", defaultValue = "")List<Candidate.CandidateState> listCandidateStates,
                                           @RequestParam(value = "page", defaultValue = "1")String page,
                                           @RequestParam(value = "pageSize", defaultValue = "10")String pageSize,
                                           @RequestParam(value = "sortColumnName", defaultValue = Candidate_.ID)String sortColumnName,
                                           @RequestParam(value = "order", defaultValue = "ASC")String order
    ) {
        CandidateFilter candidateFilter = new CandidateFilter();
        candidateFilter.setSalaryFrom(salaryFrom);
        candidateFilter.setSalaryTo(salaryTo);
        candidateFilter.setBirthdayFrom(birthdayFrom);
        candidateFilter.setBirthdayTo(birthdayTo);
        candidateFilter.setName(name);
        candidateFilter.setSurname(surname);
        candidateFilter.setPage(page != null && !"".equals(page) ? Integer.parseInt(page) : 1);
        candidateFilter.setPagesize(pageSize != null && !"".equals(pageSize) ? Integer.parseInt(pageSize) : 10);
        candidateFilter.setSortColumnName(sortColumnName);
        candidateFilter.setOrder(order);
        candidateFilter.setCandidateStates(listCandidateStates);
        List<Candidate> candidates = candidateDao.list(candidateFilter);
        logger.debug("done");
        return ResponseEntity.ok().body(candidates);
    }

    @PutMapping("/candidate/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Candidate candidate) {
        candidateDao.update(id, candidate);
        return ResponseEntity.ok().body("Candidate has been updated successfully.");
    }

    @DeleteMapping("/candidate/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        candidateDao.delete(id);
        return ResponseEntity.ok().body("Candidate has been deleted successfully.");
    }
}

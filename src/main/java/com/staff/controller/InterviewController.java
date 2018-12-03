package com.staff.controller;

import com.staff.dao.InterviewDao;
import com.staff.metamodel.Interview_;
import com.staff.model.Interview;
import com.staff.modelDto.InterviewDto;
import com.staff.util.filtering.InterviewUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class InterviewController {

    @Autowired
    private InterviewDao interviewDao;

    public void setInterviewDao(InterviewDao interviewDao){
        this.interviewDao = interviewDao;
    }

    @PostMapping("/interview")
    public ResponseEntity<?> save(@RequestBody Interview interview) {
        long id = interviewDao.save(interview);
        return ResponseEntity.ok().body("New Interview has been saved with ID:" + id);
    }

    @GetMapping("/interview/{id}")
    public ResponseEntity<InterviewDto> get(@PathVariable("id") long id) {
        Interview interview = interviewDao.get(id);
        InterviewDto interviewDto = this.generateInterviewDto(interview);
        return ResponseEntity.ok().body(interviewDto);
    }

    @GetMapping("/interview")
    public ResponseEntity<List<InterviewDto>> list(@RequestParam(value = "vacancy_id", defaultValue = "")Long vacancy_id,
                                                   @RequestParam(value = "candidate_id", defaultValue = "")Long candidate_id,
                                                   @RequestParam(value = "plan_date", defaultValue = "") Date plan_date,
                                                   @RequestParam(value = "fact_date", defaultValue = "") Date fact_date,
                                                   @RequestParam(value = "sortColumnName", defaultValue = Interview_.CANDIDATE_ID)String sortColumnName,
                                                   @RequestParam(value = "order", defaultValue = "ASC")String order,
                                                   @RequestParam(value = "page", defaultValue = "1")Integer page,
                                                   @RequestParam(value = "pagesize", defaultValue = "10")Integer pagesize
    ) {
        InterviewUtil interviewUtil = new InterviewUtil();

        interviewUtil.vacancy_id = vacancy_id;
        interviewUtil.candidate_id = candidate_id;
        interviewUtil.plan_date = plan_date;
        interviewUtil.fact_date = fact_date;
        interviewUtil.sortColumnName = sortColumnName;
        interviewUtil.order = order;
        interviewUtil.page = page;
        interviewUtil.pagesize = pagesize;

        List<Interview> interviews = interviewDao.list(interviewUtil);

        List<InterviewDto> interviewDtos = new ArrayList<InterviewDto>();
        for (Interview interview: interviews) {
            interviewDtos.add(this.generateInterviewDto(interview));
        }

        return ResponseEntity.ok().body(interviewDtos);
    }

    private InterviewDto generateInterviewDto(Interview interview){
        InterviewDto interviewDto = new InterviewDto();
        interviewDto.id = interview.getId();
        interviewDto.candidate_id = interview.getCandidateId();
        interviewDto.vacancy_id = interview.getVacancyId();
        interviewDto.plan_date = interview.getPlanDate();
        interviewDto.fact_Date = interview.getFactDate();

        return interviewDto;
    }

    @PutMapping("/interview/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Interview interview) {
        interviewDao.update(id, interview);
        return ResponseEntity.ok().body("Interview has been updated successfully.");
    }

    @DeleteMapping("/interview/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        interviewDao.delete(id);
        return ResponseEntity.ok().body("Interview has been deleted successfully.");
    }
}

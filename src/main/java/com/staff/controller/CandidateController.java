package com.staff.controller;

import com.staff.dao.CandidateDao;
import com.staff.model.Candidate;
import com.staff.model.Candidate_;
import com.staff.modelDto.CandidateDto;
import com.staff.util.filtering.CandidateFilter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CandidateController {
    @Autowired
    private CandidateDao candidateDao;

    @Autowired
    private ModelMapper modelMapper;

    public void setCandidateDao(CandidateDao candidateDao){
        this.candidateDao = candidateDao;
    }

    private final Logger logger = LoggerFactory.getLogger(CandidateController.class);

    @PostMapping(value = "/candidate", consumes="application/json;charset=UTF-8" )
    public ResponseEntity<CandidateDto> save(@RequestBody CandidateDto candidateDto)  throws ParseException {
        Long id = candidateDao.save( convertToEntity(candidateDto) );
        Candidate candidate = candidateDao.get(id);
        if(candidate == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } else {
            return ResponseEntity.ok().body(convertToDto(candidate));
        }
    }

    @GetMapping(value = "/candidate/{id}", produces="application/json;charset=UTF-8")
    public ResponseEntity<CandidateDto> get(@PathVariable("id") Long id) {
        Candidate candidate = candidateDao.get(id);
        if(candidate == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.ok().body(convertToDto(candidate));
        }
    }

    @GetMapping("/candidate")
    public List<CandidateDto> list(
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
        return candidates.stream()
                .map(candidate -> convertToDto(candidate))
                .collect(Collectors.toList());
    }

    @PutMapping(value = "/candidate/{id}", consumes="application/json;charset=UTF-8")
    public ResponseEntity<CandidateDto> update(@PathVariable("id") Long id, @RequestBody CandidateDto candidateDto) throws ParseException {
        Candidate candidate = candidateDao.get(id);
        if(candidate == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            candidateDao.update(id, convertToEntity(candidateDto));
            candidate = candidateDao.get(id);
            return ResponseEntity.ok().body(convertToDto(candidate));
        }
    }

    @DeleteMapping("/candidate/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Candidate candidate = candidateDao.get(id);
        if(candidate == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            candidateDao.delete(id);
            return ResponseEntity.ok().body(null);
        }
    }

    private CandidateDto convertToDto(Candidate candidate) {
        CandidateDto candidateDto = modelMapper.map(candidate, CandidateDto.class);
        candidateDto.setCandidateStateAsString(candidate.getCandidateState().getDescription());
        return candidateDto;
    }

    private Candidate convertToEntity(CandidateDto candidateDto) {
        Candidate candidate = modelMapper.map(candidateDto, Candidate.class);
        candidate.setCandidateState(candidateDto.getCandidateStateAsString());
        return candidate;
    }
}

package com.staff.controller;

import com.staff.dao.CandidateDao;
import com.staff.model.Candidate;
import com.staff.model.Candidate_;
import com.staff.model.ContactDetails;
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
import java.util.ArrayList;
import java.util.Collection;
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
    public void save(@RequestBody CandidateDto candidateDto)  throws ParseException {

        Candidate candidate = new Candidate();
        Candidate candidateIn = convertToEntity(candidateDto);
        candidate.setCandidateState( candidateIn.getCandidateState());
        candidate.setName( candidateIn.getName());
        candidate.setSurname( candidateIn.getSurname());
        candidate.setSalary( candidateIn.getSalary());
        candidate.setBirthday( candidateIn.getBirthday());
        //candidate.setContactDetailsList(contactDetailsEmpty);
        Long id = candidateDao.save( candidate );
        logger.debug("saved: id = "  + id.toString());
        List<ContactDetails> contactDetails = convertToEntity(candidateDto).getContactDetailsList();
        List<ContactDetails> contactDetailsEmpty = new ArrayList<>();
        ContactDetails contactDetailsLinked;
        for ( ContactDetails contactDetailsOne : contactDetails) {
            contactDetailsLinked = contactDetailsOne;
            contactDetailsLinked.setCandidate(candidate);
            contactDetailsEmpty.add(contactDetailsLinked);
        }
        candidate.setContactDetailsList(contactDetailsEmpty);
        candidateDao.update( id, candidate );
        logger.debug("updated: " + id.toString() + " size: " + contactDetailsEmpty.size());
        //logger.debug("done. candidate: " + candidate.toString());
        //return ResponseEntity.ok().body("New candidate has been saved with ID:" + id);
    }

    @GetMapping(value = "/candidate/{id}", produces="application/json;charset=UTF-8")
    public ResponseEntity<CandidateDto> get(@PathVariable("id") Long id) {
        Candidate candidate = candidateDao.get(id);
        if(candidate == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }else {
            return ResponseEntity.ok().body(convertToDto(candidate));
        }
        //return convertToDto(candidate);
        //return ResponseEntity.ok().body(candidate);
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
        logger.debug("done, recs: " + candidates.size());

        return candidates.stream()
                .map(candidate -> convertToDto(candidate))
                .collect(Collectors.toList());
        //return candidates;
        //return ResponseEntity.ok().body(candidates);
    }

    @PutMapping(value = "/candidate/{id}", consumes="application/json;charset=UTF-8")
    public void update(@PathVariable("id") Long id, @RequestBody CandidateDto candidate) throws ParseException {
        candidateDao.update(id, convertToEntity(candidate));
        //return ResponseEntity.ok().body("Candidate [id=" + id.toString() + "] has been updated successfully.");
    }

    @DeleteMapping("/candidate/{id}")
    public void delete(@PathVariable("id") Long id) {
        candidateDao.delete(id);
        //return ResponseEntity.ok().body("Candidate [id=" + id.toString() + "] has been deleted successfully.");
    }

    private CandidateDto convertToDto(Candidate candidate) {
        //CandidateDto candidateDto = new CandidateDto();
        //if (candidate != null) {
        CandidateDto candidateDto = modelMapper.map(candidate, CandidateDto.class);
        //}
        /*for ( ContactDetails cd : candidate.getContactDetailsList()) {
            //candidateDto.getContactDetailsList().add(cd);
            logger.debug("convertToDto, cd: " + cd.getContactDetails()+" " +cd.getContactType());
        }*/
        /*postDto.setSubmissionDate(post.getSubmissionDate(),
        userService.getCurrentUser().getPreference().getTimezone());*/
        return candidateDto;
    }

    private Candidate convertToEntity(CandidateDto candidateDto) throws ParseException {
        //Candidate candidate = new Candidate();
        //if (candidateDto != null) {
            Candidate candidate = modelMapper.map(candidateDto, Candidate.class);
        //}
        /*post.setSubmissionDate(postDto.getSubmissionDateConverted(
                userService.getCurrentUser().getPreference().getTimezone()));*/
        return candidate;
    }
}

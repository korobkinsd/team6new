package com.staff.controller;

import com.staff.dao.CandidateDao;
import com.staff.model.Candidate;
import com.staff.model.Candidate_;
import com.staff.model.ContactDetails;
import com.staff.modelDto.CandidateDto;
import com.staff.util.filtering.CandidateFilter;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.ResponseEntity;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

//import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CandidateControllerTest {

    private CandidateController controller;

    public ModelMapper modelMapper;

    //private final Logger logger = LoggerFactory.getLogger(CandidateControllerTest.class);

    @Before
    public void init() {
        controller = new CandidateController();
    }

    @Test
    public void showAll()  throws ParseException {
        CandidateDao candidateDao = mock(CandidateDao.class);
        List<Candidate> listOfCandidate = new ArrayList<Candidate>();
        Candidate testCandidate = generateCandidate();
        listOfCandidate.add(testCandidate);
        CandidateFilter filter = generateFilter();
        when(candidateDao.list( any(CandidateFilter.class) ))
                .thenReturn(listOfCandidate);

        controller.setCandidateDao(candidateDao);

        //ResponseEntity<List<Candidate>>
        List<CandidateDto> listCandidates = controller.list(filter.getName(), filter.getSurname(), filter.getBirthdayFromAsString(),
            filter.getBirthdayToAsString(), filter.getSalaryFrom().toString(), filter.getSalaryTo().toString(), filter.getCandidateStates(),
            filter.getPage().toString(), filter.getPagesize().toString(),
            filter.getSortColumnName(), filter.getOrder());
        /*ResponseEntity<List<Candidate>> response = controller.list( any(String.class), any(String.class), any(String.class), any(String.class), any(String.class),
                any(String.class), anyListOf(Candidate.CandidateState.class), any(String.class), any(String.class), any(String.class), any(String.class));*/

        assertEquals(1, listCandidates.size());  // we must have only one user
        //assertEquals(testCandidate, convertToEntity(listCandidates.get(0))); // we must have two equals candidates
    }

    /*@Test
    public void filterAll() {
        CandidateDao candidateDao = mock(CandidateDao.class);
        List<Candidate> listCandidate = new ArrayList<Candidate>();
        Candidate testCandidate = generateCandidate();
        listCandidate.add(testCandidate);  // 1
        listCandidate.add(testCandidate);  // 2
        testCandidate.setSalary(10000.00);
        listCandidate.add(testCandidate);  // 3

        CandidateFilter filter = generateFilter();

        when(candidateDao.list( any(CandidateFilter.class) ))
                .thenReturn(listCandidate);

        controller.setCandidateDao(candidateDao);

        List<Candidate> listCandidates = controller.list(filter.getName(), filter.getSurname(), filter.getBirthdayFromAsString(),
                filter.getBirthdayToAsString(), filter.getSalaryFrom().toString(), filter.getSalaryTo().toString(), filter.getCandidateStates(),
                filter.getPage().toString(), filter.getPagesize().toString(),
                filter.getSortColumnName(), filter.getOrder());

        assertEquals(2, listCandidates.size());  // we must have 2 candidates : (1) and (2), no (3) (salary > 9999.99)
        assertNotEquals(testCandidate, listCandidates.get(0)); // we mustn't have two equals candidates!!
    }*/


    @Test
    public void get() {
        CandidateDao candidateDao = mock(CandidateDao.class);
        Candidate testCandidate = generateCandidate();
        when(candidateDao.get( anyLong() )).thenReturn( testCandidate );
        controller.setCandidateDao(candidateDao);
        Candidate candidate = modelMapper.map(controller.get( 1L ).getBody(), Candidate.class);
        assertEquals(testCandidate, candidate); // we must have two equals candidates
    }

    @Test
    public void save() throws ParseException{
        CandidateDao candidateDao = mock(CandidateDao.class);
        Candidate testCandidate = generateCandidate();

        when(candidateDao.save( any(Candidate.class) ))
                .thenReturn(testCandidate.getId());
        controller.setCandidateDao(candidateDao);
        CandidateDto candidateDto = controller.save( modelMapper.map(testCandidate, CandidateDto.class)).getBody();
        //controller.save( convertToDto(testCandidate );
        assertEquals(testCandidate, modelMapper.map(candidateDto,Candidate.class));
    }

    @Test
    public void update() throws ParseException {
        CandidateDao candidateDao = mock(CandidateDao.class);
        Candidate testCandidate = generateCandidate();
        /*when(candidateDao.update( anyLong(), any(Candidate.class) ))
                .thenReturn(testCandidate.getId());*/
        controller.setCandidateDao(candidateDao);
        controller.update( 1L, modelMapper.map(testCandidate, CandidateDto.class) );
        assertEquals(0, 0);
    }

    @Test
    public void delete() {
        CandidateDao candidateDao = mock(CandidateDao.class);
        controller.setCandidateDao(candidateDao);
        controller.delete( 1L );
        assertEquals( 0, 0);
    }

    private CandidateFilter generateFilter() {
        CandidateFilter filter = new CandidateFilter();
        filter.setName("Random");
        filter.setSurname("Candidate");
        filter.setSalaryFrom(0.01);
        filter.setSalaryTo(9999.99);
        filter.setBirthdayFrom("1990-01-01");
        filter.setBirthdayTo("2100-01-01");
        List<Candidate.CandidateState> filterCandidateStates = new ArrayList<Candidate.CandidateState>();
        filterCandidateStates.add(Candidate.CandidateState.ACTIVE);
        filterCandidateStates.add(Candidate.CandidateState.ARCHIVE);
        filter.setCandidateStates(filterCandidateStates);
        filter.setOrder("ASC");
        filter.setPagesize(10);
        filter.setPage(1);
        filter.setOrder(Candidate_.ID);
        return filter;
    }

    private Candidate generateCandidate() {
        Candidate candidate = new Candidate();
        candidate.setId(1L);
        candidate.setName("Random");
        candidate.setSurname("Candidate");
        candidate.setSalary(550.12);
        candidate.setBirthdayAsString("2000-01-01");
        candidate.setCandidateState(Candidate.CandidateState.ACTIVE);
        ContactDetails contactDetailsOne = new ContactDetails();
        //contactDetailsOne.setCandidate(candidate);
        contactDetailsOne.setContactDetails("Some address");
        contactDetailsOne.setContactType("Address");
        List<ContactDetails> contactDetails = new ArrayList<ContactDetails>();
        contactDetails.add(contactDetailsOne);
        contactDetailsOne.setContactDetails("Some e-mail");
        contactDetailsOne.setContactType("E-mail");
        contactDetails.add(contactDetailsOne);
        candidate.setContactDetailsList(contactDetails);
        return candidate;
    }

}

package com.staff.controller;

import com.staff.dao.CandidateDao;
import com.staff.model.Candidate;
import com.staff.model.Candidate_;
import com.staff.util.filtering.CandidateFilter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CandidateControllerTest {

    private CandidateController controller;

    private final Logger logger = LoggerFactory.getLogger(CandidateControllerTest.class);

    @Before
    public void setUp() {
        controller = new CandidateController();
    }

    @Test
    public void showAllUsers() {
        CandidateDao candidateDao = mock(CandidateDao.class);
        List<Candidate> listCandidate = new ArrayList<Candidate>();
        Candidate testCandidate = generateCandidate();
        listCandidate.add(testCandidate);
        //Answer<String> answer = "Ok";
        CandidateFilter filter = generateFilter();

        when(candidateDao.list( any(CandidateFilter.class) ))
                .thenReturn(listCandidate);

        /*
        when(candidateDao.save( any(Candidate.class) ))
                .thenReturn(void);

        when(candidateDao.update( anyLong(), any(Candidate.class) ))
                .then(void);


        when(candidateDao.delete( anyLong() ))
                .thenReturn("Deleted");*/


        controller.setCandidateDao(candidateDao);

        //ResponseEntity<List<Candidate>>
        List<Candidate> listCandidates = controller.list(filter.getName(), filter.getSurname(), filter.getBirthdayFromAsString(),
            filter.getBirthdayToAsString(), filter.getSalaryFrom().toString(), filter.getSalaryTo().toString(), filter.getCandidateStates(),
            filter.getPage().toString(), filter.getPagesize().toString(),
            filter.getSortColumnName(), filter.getOrder());
        /*ResponseEntity<List<Candidate>> response = controller.list( any(String.class), any(String.class), any(String.class), any(String.class), any(String.class),
                any(String.class), anyListOf(Candidate.CandidateState.class), any(String.class), any(String.class), any(String.class), any(String.class));*/

        assertEquals(1, listCandidates.size());  // we must have only one user
        assertEquals(testCandidate, listCandidates.get(0)); // we must have two equals candidates

        /*ResponseEntity<Candidate> oneCandidate = controller.get( 100500L );
        assertEquals( testCandidate, oneCandidate.getBody());

        ResponseEntity<String> msg = controller.save(testCandidate);
        assertEquals( "Saved", msg.toString());

        msg = controller.update(100500L, testCandidate);
        assertEquals( "Updated", msg.toString());

        msg = controller.delete(100500L );
        assertEquals( "Deleted", msg.toString());*/


        /*logger.debug("save() -> " + response2.getBody());
        String st = response2.getBody().replaceAll("[^0-9]+","");
        logger.debug("st: " + st);
        Long id;
        if ( st != null && !"".equals(st)) {
            id = Long.parseLong(st);
        } else {
            id = -1L;
        }
        assertNotEquals( -1L, id);  // we must get id!=-1*/
    }

    @Test
    public void getUser() {
        CandidateDao candidateDao = mock(CandidateDao.class);
        Candidate testCandidate = generateCandidate();
        when(candidateDao.get( anyLong() ))
                .thenReturn(testCandidate);
        controller.setCandidateDao(candidateDao);
        Candidate candidate = controller.get( 1L );
        assertEquals(testCandidate, candidate); // we must have two equals candidates
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
        candidate.setId(100500L);
        candidate.setName("Random");
        candidate.setSurname("Candidate");
        candidate.setSalary(550.12);
        candidate.setBirthday("2000-01-01");
        candidate.setCandidateState(Candidate.CandidateState.ACTIVE);
        return candidate;
    }

}

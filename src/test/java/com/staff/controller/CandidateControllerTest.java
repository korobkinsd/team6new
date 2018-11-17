package com.staff.controller;

import com.staff.dao.CandidateDao;
import com.staff.model.Candidate;
import com.staff.model.Candidate_;
import com.staff.util.filtering.CandidateFilter;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CandidateControllerTest {

    private CandidateController controller;

    @Before
    public void setUp() {
        controller = new CandidateController();
    }

    @Test
    public void get() {
        CandidateDao candidateDao = mock(CandidateDao.class);
        Candidate candidate = generateCandidate();
        when(candidateDao.get(anyLong())).thenReturn(candidate);

        controller.setCandidateDao(candidateDao);

        ResponseEntity<Candidate> candidateGet = controller.get(101);
        assertEquals(candidateGet.getBody(), candidate);
    }
    
    @Test
    public void showAllCandidates() {
        CandidateDao candidateDao = mock(CandidateDao.class);
        List<Candidate> listCandidate = new ArrayList<Candidate>();
        Candidate testCandidate = generateCandidate();
        listCandidate.add(testCandidate);
        CandidateFilter filter = generateFilter();
        when(candidateDao.list( any(CandidateFilter.class) ))
                .thenReturn(listCandidate);

        controller.setCandidateDao(candidateDao);

        ResponseEntity<List<Candidate>> response = controller.list(filter.getName(), filter.getSurname(), filter.getBirthdayFromAsString(),
            filter.getBirthdayToAsString(), filter.getSalaryFrom().toString(), filter.getSalaryTo().toString(), filter.getCandidateStates(),
            filter.getPage().toString(), filter.getPagesize().toString(),
            filter.getSortColumnName(), filter.getOrder());
        /*ResponseEntity<List<Candidate>> response = controller.list( any(String.class), any(String.class), any(String.class), any(String.class), any(String.class),
                any(String.class), anyListOf(Candidate.CandidateState.class), any(String.class), any(String.class), any(String.class), any(String.class));*/

        assertEquals(1, response.getBody().size());  // we must have only one candidate
        assertEquals(testCandidate, response.getBody().get(0)); // we must have two equals candidates
    }

    @Test
    public void update() {
        CandidateDao candidateDao = mock(CandidateDao.class);
        Candidate candidate = generateCandidate();
        candidateDao.update(anyLong(), any(Candidate.class));

        controller.setCandidateDao(candidateDao);

        controller.update((long) 101, candidate);

        assertEquals("", "");
    }

    @Test
    public void save() {
        CandidateDao candidateDao = mock(CandidateDao.class);
        Candidate candidate = generateCandidate();

        candidateDao.save( any(Candidate.class));

        controller.setCandidateDao(candidateDao);

        controller.save(candidate);

        assertEquals("", "");
    }

    @Test
    public void delete() {
        CandidateDao candidateDao = mock(CandidateDao.class);
        Candidate candidate = generateCandidate();
        candidateDao.delete(anyLong());

        controller.setCandidateDao(candidateDao);

        controller.delete((long) 101);
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
        candidate.setName("Random");
        candidate.setSurname("Candidate");
        candidate.setSalary(550.12);
        candidate.setBirthday("2000-01-01");
        candidate.setCandidateState(Candidate.CandidateState.ACTIVE);
        return candidate;
    }

}

package com.staff.dao;

import com.staff.model.Candidate;
import com.staff.util.filtering.CandidateFilter;

import java.util.List;

public interface CandidateDao {

        long save(Candidate candidate);

        Candidate get(long id);

        List<Candidate> list(CandidateFilter filter);

        void update(long id, Candidate candidate);

        void delete(long id);

}



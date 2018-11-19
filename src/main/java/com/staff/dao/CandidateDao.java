package com.staff.dao;

import com.staff.model.Candidate;
import com.staff.util.filtering.CandidateFilter;

import java.util.List;

public interface CandidateDao {

        Long save(Candidate candidate);

        Candidate get(Long id);

        List<Candidate> list(CandidateFilter filter);

        void update(Long id, Candidate candidate);

        void delete(Long id);

}



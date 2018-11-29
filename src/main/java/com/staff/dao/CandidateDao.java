package com.staff.dao;

import com.staff.model.Candidate;
import com.staff.util.filtering.CandidateFilter;

import java.util.List;

public interface CandidateDao {

        Candidate get(Long id);

        List<Candidate> list(CandidateFilter filter);

        Long saveOrUpdate(Candidate candidate);

        void delete(Long id);

}



package com.staff.dao;

import com.staff.model.Interview;
import com.staff.util.filtering.InterviewUtil;

import java.util.List;

public interface InterviewDao {

    long save (Interview interview);

    Interview get(long id);

    List<Interview> list(InterviewUtil interviewUtil);

    void update (long id, Interview interview);

    void delete (long id);
}

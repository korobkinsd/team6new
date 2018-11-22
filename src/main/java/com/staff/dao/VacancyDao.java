package com.staff.dao;

import com.staff.model.Vacancy;
import com.staff.util.filtering.SortPagining;

import java.util.List;

public interface VacancyDao {

   long save(Vacancy vacancy);

   Vacancy get(long id);

   List<Vacancy> list(SortPagining sortPagining);

   void update(long id, Vacancy vacancy);

   void delete(long id);

}

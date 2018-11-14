package com.staff.dao;

import com.staff.model.Vacancy;

import java.util.List;

public interface VacancyDao {

   long save(Vacancy vacancy);

   Vacancy get(long id);

   List<Vacancy> list(Vacancy vacancy, int pageNumber, int pageSize);

   void update(long id, Vacancy vacancy);

   void delete(long id);

}

package com.staff.service;

import com.staff.model.Vacancy;

import java.util.List;

public interface VacancyService {

   long save(Vacancy vacancy);
   Vacancy get(long id);
   List<Vacancy> list(Vacancy vacancy);
   void update(long id, Vacancy vacancy);
   void delete(long id);
}

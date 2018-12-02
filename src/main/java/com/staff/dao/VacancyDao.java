package com.staff.dao;

import com.staff.model.Vacancy;
import com.staff.modelDto.VacancyChangeDto;
import com.staff.modelDto.VacancyDto;
import com.staff.util.filtering.SortPagining;

import java.util.List;

public interface VacancyDao {

   long save(VacancyChangeDto vacancy);

   VacancyDto get(long id);

   List<VacancyDto> list(SortPagining sortPagining);

   void update(long id, VacancyChangeDto vacancy);

   void delete(long id);

}

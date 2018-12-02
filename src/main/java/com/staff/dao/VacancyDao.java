package com.staff.dao;

import com.staff.modelDto.VacancyChangeDto;
import com.staff.modelDto.VacancyDto;
import com.staff.util.filtering.VacancyFilter;

import java.util.List;

public interface VacancyDao {

   long save(VacancyChangeDto vacancy);

   VacancyDto get(long id);

   List<VacancyDto> list(VacancyFilter vacancyFilter);

   void update(long id, VacancyChangeDto vacancy);

   void delete(long id);

}

package com.staff.service;

import com.staff.dao.VacancyDao;
import com.staff.model.Vacancy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class VacancyServiceImp implements VacancyService {

   @Autowired
   private VacancyDao vacancyDao;

   @Transactional
   @Override
   public long save(Vacancy vacancy) {
      return vacancyDao.save(vacancy);
   }

   @Override
   public Vacancy get(long id) {
      return vacancyDao.get(id);
   }

   @Override
   public List<Vacancy> list(Vacancy vacancy) {
      return vacancyDao.list(vacancy);
   }

   @Transactional
   @Override
   public void update(long id, Vacancy vacancy) {
      vacancyDao.update(id, vacancy);
   }

   @Transactional
   @Override
   public void delete(long id) {
      vacancyDao.delete(id);
   }

}

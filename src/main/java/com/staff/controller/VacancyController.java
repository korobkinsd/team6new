package com.staff.controller;

import com.staff.dao.VacancyDao;
import com.staff.model.Vacancy;

import com.staff.modelDto.VacancyDto;
import com.staff.util.filtering.SortPagining;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
public class VacancyController {

   @Autowired
   protected VacancyDao vacancyDao;

   public void setVacancyDao(VacancyDao vacancyDao){
      this.vacancyDao = vacancyDao;
   }

   /*---Add new vacancy---*/
   @PutMapping ("/vacancy")
   public ResponseEntity<?> save(@RequestBody Vacancy vacancy) {
      long id = vacancyDao.save(vacancy);
      return ResponseEntity.ok().body("New Vacancy has been saved with ID:" + id);
   }

   /*---Get a vacancy by id---*/
   @GetMapping("/vacancy/{id}")
   public ResponseEntity<Vacancy> get(@PathVariable("id") long id) {
      Vacancy vacancy = vacancyDao.get(id);
      return ResponseEntity.ok().body(vacancy);
   }

   /*---get all vacancys---*/
   @GetMapping("/vacancy")
   public List<VacancyDto> list(@RequestParam(value = "id", defaultValue = "")Long id,
                                             @RequestParam(value = "position", defaultValue = "")String position,
                                             @RequestParam(value = "salaryfrom", defaultValue = "0")Double salaryfrom,
                                             @RequestParam(value = "salaryto", defaultValue = "0")Double salaryto,
                                             @RequestParam(value = "iddeveloper", defaultValue = "0")Long iddeveloper,
                                             @RequestParam(value = "state", defaultValue = "")String state,
                                             @RequestParam(value = "experienceyearsrequire", defaultValue = "0")Double experienceyearsrequire,
                                             @RequestParam(value = "offset", defaultValue = "1")int pageNumber,
                                             @RequestParam(value = "limit", defaultValue = "10")int pageSize,
                                             @RequestParam(value = "columnname", defaultValue = "id")String columnName,
                                             @RequestParam(value = "order", defaultValue = "ASC")String order
   ) {

      Vacancy vacancyFilter =new Vacancy();
       vacancyFilter.setId(id);
     //  vacancyFilter.setIdDeveloper(iddeveloper);
       vacancyFilter.setPosition(position);
       vacancyFilter.setSalaryFrom(salaryfrom);
       vacancyFilter.setSalaryTo(salaryto);
       vacancyFilter.setState(state);
       vacancyFilter.setExperienceYearsRequire(experienceyearsrequire);
       SortPagining sortPagining = new SortPagining( vacancyFilter ,columnName,order,pageNumber,pageSize);
       List<Vacancy> vacancys = vacancyDao.list(sortPagining);

       List<VacancyDto> vacancyDtoList = new ArrayList<VacancyDto>();


           for (Vacancy vacancy: vacancys){
               VacancyDto vacancyDto =new VacancyDto(vacancy);
               vacancyDtoList.add(vacancyDto);
           }

return vacancyDtoList;

     // return ResponseEntity.ok().body(vacancyDtoList);
   }

   /*---Update a vacancy by id---*/
   @PostMapping ("/vacancy/{id}")
   public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Vacancy vacancy) {
      vacancyDao.update(id, vacancy);
      return ResponseEntity.ok().body("Vacancy has been updated successfully.");
   }

   /*---Delete a vacancy by id---*/
   @DeleteMapping("/vacancy/{id}")
   public ResponseEntity<?> delete(@PathVariable("id") long id) {
      vacancyDao.delete(id);
      return ResponseEntity.ok().body("Vacancy has been deleted successfully.");
   }
}
package com.staff.controller;

import com.staff.dao.VacancyDao;
import com.staff.model.Vacancy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class VacancyController {

   @Autowired
   protected VacancyDao vacancyDao;

   /*---Add new vacancy---*/
   @PostMapping("/vacancy")
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
   public ResponseEntity<List<Vacancy>> list(@RequestParam(value = "id", defaultValue = "")Long id,
                                             @RequestParam(value = "position", defaultValue = "")String position,
                                             @RequestParam(value = "salaryfrom", defaultValue = "0")Double salaryfrom,
                                             @RequestParam(value = "salaryto", defaultValue = "0")Double salaryto,
                                             @RequestParam(value = "iddeveloper", defaultValue = "0")Long iddeveloper,
                                             @RequestParam(value = "state", defaultValue = "")String state,
                                             @RequestParam(value = "experienceyearsrequire", defaultValue = "0")Double experienceyearsrequire,
                                             @RequestParam(value = "offset", defaultValue = "1")int pageNumber,
                                             @RequestParam(value = "limit", defaultValue = "10")int pageSize
   ) {

      Vacancy vacancyfilet =new Vacancy();
       vacancyfilet.setId(id);
       vacancyfilet.setIdDeveloper(iddeveloper);
       vacancyfilet.setPosition(position);
       vacancyfilet.setSalaryFrom(salaryfrom);
       vacancyfilet.setSalaryTo(salaryto);
       vacancyfilet.setState(state);
       vacancyfilet.setExperienceYearsRequire(experienceyearsrequire);
      List<Vacancy> vacancys = vacancyDao.list(vacancyfilet,pageNumber,pageSize);
      return ResponseEntity.ok().body(vacancys);
   }

   /*---Update a vacancy by id---*/
   @PutMapping("/vacancy/{id}")
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
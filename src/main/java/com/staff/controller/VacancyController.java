package com.staff.controller;

import com.staff.model.Vacancy;
import com.staff.service.VacancyService;
import com.staff.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VacancyController {

   @Autowired
   private VacancyService vacancyService;

   /*---Add new vacancy---*/
   @PostMapping("/vacancy")
   public ResponseEntity<?> save(@RequestBody Vacancy vacancy) {
      long id = vacancyService.save(vacancy);
      return ResponseEntity.ok().body("New Vacancy has been saved with ID:" + id);
   }

   /*---Get a vacancy by id---*/
   @GetMapping("/vacancy/{id}")
   public ResponseEntity<Vacancy> get(@PathVariable("id") long id) {
      Vacancy vacancy = vacancyService.get(id);
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
                                             @RequestParam(value = "experienceyearsrequire", defaultValue = "0")Double experienceyearsrequire
   ) {
      Vacancy vacancyfilet =new Vacancy();
       vacancyfilet.setId(id);
       vacancyfilet.setIdDeveloper(iddeveloper);
       vacancyfilet.setPosition(position);
       vacancyfilet.setSalaryFrom(salaryfrom);
       vacancyfilet.setSalaryTo(salaryto);
       vacancyfilet.setState(state);
       vacancyfilet.setExperienceYearsRequire(experienceyearsrequire);
      List<Vacancy> vacancys = vacancyService.list(vacancyfilet);
      return ResponseEntity.ok().body(vacancys);
   }

   /*---Update a vacancy by id---*/
   @PutMapping("/vacancy/{id}")
   public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Vacancy vacancy) {
      vacancyService.update(id, vacancy);
      return ResponseEntity.ok().body("Vacancy has been updated successfully.");
   }

   /*---Delete a vacancy by id---*/
   @DeleteMapping("/vacancy/{id}")
   public ResponseEntity<?> delete(@PathVariable("id") long id) {
      vacancyService.delete(id);
      return ResponseEntity.ok().body("Vacancy has been deleted successfully.");
   }
}
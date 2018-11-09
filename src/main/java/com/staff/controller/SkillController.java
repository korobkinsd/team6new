package com.staff.controller;

import com.staff.model.Skill;
import com.staff.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SkillController {

   @Autowired
   private SkillService skillService;

   /*---Add new skill---*/
   @PostMapping("/skill")
   public ResponseEntity<?> save(@RequestBody Skill skill) {
      long id = skillService.save(skill);
      return ResponseEntity.ok().body("New Skill has been saved with ID:" + id);
   }

   /*---Get a skill by id---*/
   @GetMapping("/skill/{id}")
   public ResponseEntity<Skill> get(@PathVariable("id") long id) {
      Skill skill = skillService.get(id);
      return ResponseEntity.ok().body(skill);
   }

   /*---get all skills---*/
   @GetMapping("/skill")
   public ResponseEntity<List<Skill>> list() {
      List<Skill> skills = skillService.list();
      return ResponseEntity.ok().body(skills);
   }

   /*---Update a skill by id---*/
   @PutMapping("/skill/{id}")
   public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Skill skill) {
      skillService.update(id, skill);
      return ResponseEntity.ok().body("Skill has been updated successfully.");
   }

   /*---Delete a skill by id---*/
   @DeleteMapping("/skill/{id}")
   public ResponseEntity<?> delete(@PathVariable("id") long id) {
      skillService.delete(id);
      return ResponseEntity.ok().body("Skill has been deleted successfully.");
   }
}
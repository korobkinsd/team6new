package com.staff.controller;

import com.staff.dao.SkillDao;
import com.staff.model.Skill;
import com.staff.model.Skill_;
import com.staff.util.filtering.TrashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SkillController {
   
   @Autowired
   private SkillDao skillDao;

   public void setSkillDao(SkillDao skillDao){
      this.skillDao = skillDao;
   }

   public SkillDao getSkillDao(){
      return this.skillDao;
   }

   @PostMapping("/skill")
   public ResponseEntity<?> save(@RequestBody Skill skill) {
      String name = skillDao.save(skill);
      return ResponseEntity.ok().body("New Skill has been saved:" + name);
   }

   @GetMapping("/skill/{name}")
   public ResponseEntity<Skill> get(@PathVariable("name") String name) {
      Skill skill = skillDao.get(name);
      return ResponseEntity.ok().body(skill);
   }
   @GetMapping("/skill")
   public ResponseEntity<List<Skill>> list(@RequestParam(value = "name", defaultValue = "")String name,
                                          @RequestParam(value = "sortColumnName", defaultValue = Skill_.NAME)String sortColumnName,
                                          @RequestParam(value = "order", defaultValue = "ASC")String order,
                                          @RequestParam(value = "page", defaultValue = "1")Integer page,
                                          @RequestParam(value = "pagesize", defaultValue = "5")Integer pagesize
   ) {
      TrashUtil trashUtil =new TrashUtil();
      trashUtil.name = name;
      trashUtil.sortColumnName = sortColumnName;
      trashUtil.order = order;
      trashUtil.page = page;
      trashUtil.pagesize = pagesize;

      List<Skill> skills = skillDao.list(trashUtil);
      return ResponseEntity.ok().body(skills);
   }

   @PutMapping("/skill/{name}")
   public ResponseEntity<?> update(@PathVariable("name") String name, @RequestBody Skill skill) {
      skillDao.update(name, skill);
      return ResponseEntity.ok().body("Skill has been updated successfully.");
   }

   @DeleteMapping("/skill/{name}")
   public ResponseEntity<?> delete(@PathVariable("name") String name) {
      skillDao.delete(name);
      return ResponseEntity.ok().body("Skill has been deleted successfully.");
   }
}
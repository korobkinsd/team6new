package com.staff.controller;

import com.staff.dao.RequirementDao;
import com.staff.metamodel.Requirement_;
import com.staff.model.Requirement;
import com.staff.util.filtering.TrashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RequirementController {
   
   @Autowired
   private RequirementDao requirementDao;

   public void setRequirementDao(RequirementDao requirementDao){
      this.requirementDao = requirementDao;
   }

   @PostMapping("/requirement")
   public ResponseEntity<?> save(@RequestBody Requirement requirement) {
      String name = requirementDao.save(requirement);
      return ResponseEntity.ok().body("New Requirement has been saved:" + name);
   }

   @GetMapping("/requirement/{name}")
   public ResponseEntity<Requirement> get(@PathVariable("name") String name) {
      Requirement requirement = requirementDao.get(name);
      return ResponseEntity.ok().body(requirement);
   }
   @GetMapping("/requirement")
   public ResponseEntity<List<Requirement>> list(@RequestParam(value = "name", defaultValue = "")String name,
                                          @RequestParam(value = "sortColumnName", defaultValue = Requirement_.NAME)String sortColumnName,
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

      List<Requirement> requirements = requirementDao.list(trashUtil);
      return ResponseEntity.ok().body(requirements);
   }

   @PutMapping("/requirement/{name}")
   public ResponseEntity<?> update(@PathVariable("name") String name, @RequestBody Requirement requirement) {
      requirementDao.update(name, requirement);
      return ResponseEntity.ok().body("Requirement has been updated successfully.");
   }

   @DeleteMapping("/requirement/{name}")
   public ResponseEntity<?> delete(@PathVariable("name") String name) {
      requirementDao.delete(name);
      return ResponseEntity.ok().body("Requirement has been deleted successfully.");
   }
}
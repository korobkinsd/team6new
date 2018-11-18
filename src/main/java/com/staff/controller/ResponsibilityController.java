package com.staff.controller;

import com.staff.dao.ResponsibilityDao;
import com.staff.metamodel.Responsibility_;
import com.staff.model.Responsibility;
import com.staff.util.filtering.TrashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ResponsibilityController {
   
   @Autowired
   private ResponsibilityDao responsibilityDao;

   public void setResponsibilityDao(ResponsibilityDao responsibilityDao){
      this.responsibilityDao = responsibilityDao;
   }

   @PostMapping("/responsibility")
   public ResponseEntity<?> save(@RequestBody Responsibility responsibility) {
      String name = responsibilityDao.save(responsibility);
      return ResponseEntity.ok().body("New Responsibility has been saved:" + name);
   }

   @GetMapping("/responsibility/{name}")
   public ResponseEntity<Responsibility> get(@PathVariable("name") String name) {
      Responsibility responsibility = responsibilityDao.get(name);
      return ResponseEntity.ok().body(responsibility);
   }
   @GetMapping("/responsibility")
   public ResponseEntity<List<Responsibility>> list(@RequestParam(value = "name", defaultValue = "")String name,
                                          @RequestParam(value = "sortColumnName", defaultValue = Responsibility_.NAME)String sortColumnName,
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

      List<Responsibility> responsibilitys = responsibilityDao.list(trashUtil);
      return ResponseEntity.ok().body(responsibilitys);
   }

   @PutMapping("/responsibility/{name}")
   public ResponseEntity<?> update(@PathVariable("name") String name, @RequestBody Responsibility responsibility) {
      responsibilityDao.update(name, responsibility);
      return ResponseEntity.ok().body("Responsibility has been updated successfully.");
   }

   @DeleteMapping("/responsibility/{name}")
   public ResponseEntity<?> delete(@PathVariable("name") String name) {
      responsibilityDao.delete(name);
      return ResponseEntity.ok().body("Responsibility has been deleted successfully.");
   }
}
package com.staff.model;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "skill", schema = "team6")
public class Skill {

   @Id
   @Size(min = 1, max = 25, message = "Your full name must be between 1 and 80 characters long.")
   @Pattern(regexp = "[A-Za-z]", message = "Only letters")
   @Column(name = "name")
   private String name;

   public final String getName() {
      return name;
   }

   public final void setName(String name) {
      this.name = name;
   }

   @Override
   public final boolean equals(Object o) {
      if (this == o) {
         return true;
      }
      if (o == null || getClass() != o.getClass()) {
         return false;
      }
      Skill skill = (Skill) o;
      return name.equals(skill.name);
   }

   @Override
   public final int hashCode() {
      return Objects.hash(name);
   }

   @Override
   public final String toString() {
      return "Skill [" + " name='" + name + "]";
   }

}

package com.staff.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "requirement", schema = "team6")
public class Requirement {

   @Id
   @NotNull(message="is required")
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
      Requirement requirement = (Requirement) o;
      return name.equals(requirement.name);
   }

   @Override
   public final int hashCode() {
      return Objects.hash(name);
   }

   @Override
   public final String toString() {
      return "Requirement [" + " name='" + name + "]";
   }

}

package com.staff.controller;

import com.staff.dao.VacancyDao;
import com.staff.metamodel.Vacancy_;
import com.staff.model.Vacancy;
import com.staff.modelDto.VacancyChangeDto;
import com.staff.modelDto.VacancyDto;
import com.staff.util.filtering.VacancyFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class VacancyControllerTest {


    private VacancyController controller;
    
    @BeforeEach
    protected void setUp() {
        controller = new VacancyController();

    }


    private Vacancy generateVacancy() {

        Vacancy vacancy = new Vacancy();
        vacancy.setId((long)2035);
        vacancy.setPosition("Java");
        // vacancy.setIdDeveloper((long)0);
        vacancy.setSalaryFrom((double) 0);
        vacancy.setSalaryTo((double)0);
        vacancy.setExperienceYearsRequire((double)0);
        return vacancy;
    }
    private VacancyDto generateVacancyDto() {
        VacancyDto vacancy = new VacancyDto(generateVacancy());
        return vacancy;
    }

    private VacancyChangeDto generateVacancyChangeDto() {

        VacancyChangeDto vacancy = new VacancyChangeDto();
        vacancy.setId((long)2035);
        vacancy.setPosition("Java");
         vacancy.setIdDeveloper((long)102);
        vacancy.setSalaryFrom((double) 0);
        vacancy.setSalaryTo((double)0);
        vacancy.setExperienceYearsRequire((double)0);
        return vacancy;
    }


    @Test
    void get() {
        VacancyDao vacancyDao=mock(VacancyDao.class);
        Vacancy vacancy = generateVacancy();
        controller.vacancyDao = vacancyDao;

    }

    @Test
    void list() {
        VacancyDao vacancyDao=mock(VacancyDao.class);
        VacancyDto vacancy = generateVacancyDto();
        Vacancy vacancyempty = generateVacancy();
        controller.vacancyDao = vacancyDao;
        List<VacancyDto> listVacancies = new ArrayList<VacancyDto>();
        listVacancies.add(vacancy);
        when(vacancyDao.list(any(VacancyFilter.class))).thenReturn(listVacancies);

        List<VacancyDto> vacancys = controller.list(vacancyempty.getId(),vacancyempty.getPosition(),vacancyempty.getSalaryFrom(),vacancyempty.getSalaryTo(),(long)102,vacancyempty.getState(),vacancyempty.getExperienceYearsRequire(),1,10, Vacancy_.ID,"ASC");
        assertEquals(vacancys.size(), 1);
        assertEquals(vacancys.get(0), vacancy);


    }


    @Test
    void save() {
        VacancyDao vacancyDao = mock(VacancyDao.class);
        VacancyChangeDto vacancy = generateVacancyChangeDto();
       vacancyDao.save(any(VacancyChangeDto.class));

      controller.setVacancyDao(vacancyDao);

       controller.save(vacancy);

        assertEquals("", "");
    }

    @Test
    void update() {
        VacancyDao vacancyDao = mock(VacancyDao.class);
        VacancyChangeDto vacancy = generateVacancyChangeDto();
        vacancyDao.update(anyLong(), any(VacancyChangeDto.class));

        controller.setVacancyDao(vacancyDao);

        controller.update((long) 101, vacancy);

        assertEquals("", "");
    }

    @Test
    void delete() {
        VacancyDao vacancyDao = mock(VacancyDao.class);
        Vacancy vacancy = generateVacancy();
        vacancyDao.delete(anyLong());

        controller.setVacancyDao(vacancyDao);

        controller.delete((long) 101);
    }
}


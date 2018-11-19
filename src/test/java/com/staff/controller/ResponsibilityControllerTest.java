package com.staff.controller;

import com.staff.dao.ResponsibilityDao;
import com.staff.model.Responsibility;
import com.staff.util.filtering.TrashUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ResponsibilityControllerTest {

    private ResponsibilityController controller;

    @BeforeEach
    void setUp() { controller = new ResponsibilityController();
    }

    @Test
    public void get() {
        ResponsibilityDao responsibilityDao = mock(ResponsibilityDao.class);
        Responsibility responsibility = generateResponsibility();
        when(responsibilityDao.get(anyString())).thenReturn(responsibility);

        controller.setResponsibilityDao(responsibilityDao);

        ResponseEntity<Responsibility> responsibilityGet = controller.get("name");
        assertEquals(responsibilityGet.getBody(), responsibility);
    }
    @Test
    void list() {
        ResponsibilityDao responsibilityDao = mock(ResponsibilityDao.class);
        TrashUtil responsibilityUtil = generateResponsibilityUtil();
        Responsibility responsibility = generateResponsibility();
        List<Responsibility> listResponsibilitys = new ArrayList<Responsibility>();
        listResponsibilitys.add(responsibility);
        when(responsibilityDao.list(any(TrashUtil.class)))
                .thenReturn(listResponsibilitys);

        controller.setResponsibilityDao(responsibilityDao);

        ResponseEntity<List<Responsibility>> responsibilitys = controller.list(
                responsibilityUtil.name,
                responsibilityUtil.sortColumnName,
                responsibilityUtil.order,
                responsibilityUtil.page,
                responsibilityUtil.pagesize);

        assertEquals(responsibilitys.getBody().size(), 1);
        assertEquals(responsibilitys.getBody().get(0), responsibility);

    }

    @Test
    public void update() {
        ResponsibilityDao responsibilityDao = mock(ResponsibilityDao.class);
        Responsibility responsibility = generateResponsibility();
        responsibilityDao.update(anyString(), any(Responsibility.class));

        controller.setResponsibilityDao(responsibilityDao);

        controller.update("name",responsibility);

        assertEquals("", "");
    }

    @Test
    public void save() {
        ResponsibilityDao responsibilityDao = mock(ResponsibilityDao.class);
        Responsibility responsibility = generateResponsibility();

        responsibilityDao.save(any(Responsibility.class));

        controller.setResponsibilityDao(responsibilityDao);

        controller.save(responsibility);

        assertEquals("", "");
    }

    @Test
    public void delete() {
        ResponsibilityDao responsibilityDao = mock(ResponsibilityDao.class);
        Responsibility responsibility = generateResponsibility();
        responsibilityDao.delete(anyString());

        controller.setResponsibilityDao(responsibilityDao);

        controller.delete("name");
    }

    private TrashUtil generateResponsibilityUtil() {
        TrashUtil trashUtil = new TrashUtil();
        trashUtil.name = "name";
        trashUtil.sortColumnName = "name";
        trashUtil.order = "ASC";
        trashUtil.page = 1;
        trashUtil.pagesize = 5;

        return trashUtil;
    }

    private Responsibility generateResponsibility(){
        Responsibility responsibility = new Responsibility();
        responsibility.setName("name");

        return responsibility;
    }
}
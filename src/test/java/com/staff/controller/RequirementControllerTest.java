package com.staff.controller;

import com.staff.dao.RequirementDao;
import com.staff.model.Requirement;
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

class RequirementControllerTest {

    private RequirementController controller;

    @BeforeEach
    void setUp() { controller = new RequirementController();
    }

    @Test
    public void get() {
        RequirementDao requirementDao = mock(RequirementDao.class);
        Requirement requirement = generateRequirement();
        when(requirementDao.get(anyString())).thenReturn(requirement);

        controller.setRequirementDao(requirementDao);

        ResponseEntity<Requirement> requirementGet = controller.get("name");
        assertEquals(requirementGet.getBody(), requirement);
    }
    @Test
    void list() {
        RequirementDao requirementDao = mock(RequirementDao.class);
        TrashUtil requirementUtil = generateRequirementUtil();
        Requirement requirement = generateRequirement();
        List<Requirement> listRequirements = new ArrayList<Requirement>();
        listRequirements.add(requirement);
        when(requirementDao.list(any(TrashUtil.class)))
                .thenReturn(listRequirements);

        controller.setRequirementDao(requirementDao);

        ResponseEntity<List<Requirement>> requirements = controller.list(
                requirementUtil.name,
                requirementUtil.sortColumnName,
                requirementUtil.order,
                requirementUtil.page,
                requirementUtil.pagesize);

        assertEquals(requirements.getBody().size(), 1);
        assertEquals(requirements.getBody().get(0), requirement);

    }

    @Test
    public void update() {
        RequirementDao requirementDao = mock(RequirementDao.class);
        Requirement requirement = generateRequirement();
        requirementDao.update(anyString(), any(Requirement.class));

        controller.setRequirementDao(requirementDao);

        controller.update("name",requirement);

        assertEquals("", "");
    }

    @Test
    public void save() {
        RequirementDao requirementDao = mock(RequirementDao.class);
        Requirement requirement = generateRequirement();

        requirementDao.save(any(Requirement.class));

        controller.setRequirementDao(requirementDao);

        controller.save(requirement);

        assertEquals("", "");
    }

    @Test
    public void delete() {
        RequirementDao requirementDao = mock(RequirementDao.class);
        Requirement requirement = generateRequirement();
        requirementDao.delete(anyString());

        controller.setRequirementDao(requirementDao);

        controller.delete("name");
    }

    private TrashUtil generateRequirementUtil() {
        TrashUtil trashUtil = new TrashUtil();
        trashUtil.name = "name";
        trashUtil.sortColumnName = "name";
        trashUtil.order = "ASC";
        trashUtil.page = 1;
        trashUtil.pagesize = 5;

        return trashUtil;
    }

    private Requirement generateRequirement(){
        Requirement requirement = new Requirement();
        requirement.setName("name");

        return requirement;
    }
}
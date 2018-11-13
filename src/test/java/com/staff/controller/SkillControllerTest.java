package com.staff.controller;

import com.staff.dao.SkillDao;
import com.staff.model.Skill;
import com.staff.util.filtering.TrashUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SkillControllerTest {

    private SkillController skillController;

    @BeforeEach
    void setUp() { skillController = new SkillController();
    }

    @Test
    void list() {
        SkillDao skillDao=mock(SkillDao.class);
        TrashUtil trashUtil = generateSkill();
        Skill skill = new Skill();
        when(skillDao.list(trashUtil))
                .thenReturn(List.of(skill));

        skillController.setSkillDao(skillDao);

        ResponseEntity<List<Skill>> skills = skillController.list(trashUtil.name, trashUtil.sortColumnName, trashUtil.order, trashUtil.page, trashUtil.pagesize);

        assertEquals(skills.getBody().size(), 10);
        assertEquals(skills.getBody().get(0), skill);
    }

    private TrashUtil generateSkill() {
        TrashUtil trashUtil = new TrashUtil();
        trashUtil.name = "name";
        trashUtil.sortColumnName = "name";
        trashUtil.order = "ASC";
        trashUtil.page = 1;
        trashUtil.pagesize = 5;

        return trashUtil;
    }

}
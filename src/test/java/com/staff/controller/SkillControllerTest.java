package com.staff.controller;

import com.staff.dao.SkillDao;
import com.staff.model.Skill;
import com.staff.util.filtering.TrashUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SkillControllerTest {

    private SkillController controller;

    @BeforeEach
    void setUp() { controller = new SkillController();
    }

    @Test
    public void get() {
        SkillDao skillDao = mock(SkillDao.class);
        Skill skill = generateSkill();
        when(skillDao.get(anyString())).thenReturn(skill);

        controller.setSkillDao(skillDao);

        ResponseEntity<Skill> skillGet = controller.get("name");
        assertEquals(skillGet.getBody(), skill);
    }
    @Test
    void list() {
        SkillDao skillDao = mock(SkillDao.class);
        TrashUtil skillUtil = generateSkillUtil();
        Skill skill = generateSkill();
        List<Skill> listSkills = new ArrayList<Skill>();
        listSkills.add(skill);
        when(skillDao.list(any(TrashUtil.class)))
                .thenReturn(listSkills);

        controller.setSkillDao(skillDao);

        ResponseEntity<List<Skill>> skills = controller.list(
                skillUtil.name,
                skillUtil.sortColumnName,
                skillUtil.order,
                skillUtil.page,
                skillUtil.pagesize);

        assertEquals(skills.getBody().size(), 1);
        assertEquals(skills.getBody().get(0), skill);

    }

    @Test
    public void update() {
        SkillDao skillDao = mock(SkillDao.class);
        Skill skill = generateSkill();
        skillDao.update(anyString(), any(Skill.class));

        controller.setSkillDao(skillDao);

        controller.update("name",skill);

        assertEquals("", "");
    }

    @Test
    public void save() {
        SkillDao skillDao = mock(SkillDao.class);
        Skill skill = generateSkill();

        skillDao.save(any(Skill.class));

        controller.setSkillDao(skillDao);

        controller.save(skill);

        assertEquals("", "");
    }

    @Test
    public void delete() {
        SkillDao skillDao = mock(SkillDao.class);
        Skill skill = generateSkill();
        skillDao.delete(anyString());

        controller.setSkillDao(skillDao);

        controller.delete("name");
    }

    private TrashUtil generateSkillUtil() {
        TrashUtil trashUtil = new TrashUtil();
        trashUtil.name = "name";
        trashUtil.sortColumnName = "name";
        trashUtil.order = "ASC";
        trashUtil.page = 1;
        trashUtil.pagesize = 5;

        return trashUtil;
    }

    private Skill generateSkill(){
        Skill skill = new Skill();
        skill.setName("name");

        return skill;
    }
}
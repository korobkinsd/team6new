package com.staff.controller;

import com.staff.dao.SkillDao;
import com.staff.model.Skill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SkillControllerTest2 {

    SkillController skillController;
    @BeforeEach
    void setUp() { skillController = new SkillController();
    }

    @Test
    void list() {
        SkillDao skillDao=mock(SkillDao.class);
        Skill skill = generateSkill();
        when(skillDao.get((Long) any()));
    }

    private Skill generateSkill() {
        Skill skill = new Skill();
        skill.setName("Alex");
        skill.setId((long) 1);

        return skill;
    }

}
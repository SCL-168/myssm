package com.scl.crm.workbench.service.impl;

import com.scl.crm.workbench.domain.ClueActivityRelation;
import com.scl.crm.workbench.mapper.ClueActivityRelationMapper;
import com.scl.crm.workbench.service.ClueActivityRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("clueActivityRelationService")
public class ClueActivityRelationServiceImpl implements ClueActivityRelationService {


    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;


    @Override
    public int saveCreateClueActivityRelationByList(List<ClueActivityRelation> list) {
        return clueActivityRelationMapper.insertClueActivityRelationByList(list);
    }

    //根据clueId和activityId删除线索和市场活动关联关系
    @Override
    public int deleteClueActivityRelationByClueIdActivityId(ClueActivityRelation relation) {
        return clueActivityRelationMapper.deleteClueIdActivityRelationByClueIdActivityId(relation);
    }
}

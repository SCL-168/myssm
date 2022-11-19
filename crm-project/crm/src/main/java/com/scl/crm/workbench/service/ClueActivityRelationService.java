package com.scl.crm.workbench.service;

import com.scl.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationService {


    int saveCreateClueActivityRelationByList(List<ClueActivityRelation>list);

    //根据clueId和activityId删除线索和市场活动关联关系
    int deleteClueActivityRelationByClueIdActivityId(ClueActivityRelation relation);

}

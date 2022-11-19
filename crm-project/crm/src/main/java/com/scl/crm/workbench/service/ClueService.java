package com.scl.crm.workbench.service;

import com.scl.crm.workbench.domain.Clue;

import java.util.Map;

public interface ClueService {

    //保存创建线索表
    int saveCreateClue(Clue clue);

    //跟据ID查询线索明细
    Clue queryClueForDetailById(String id);

    //保存创建的线索
    void saveConvertClue(Map<String,Object> map);
    
}

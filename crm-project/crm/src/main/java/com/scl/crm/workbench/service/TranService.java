package com.scl.crm.workbench.service;


import com.scl.crm.workbench.domain.FunnelVO;
import com.scl.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;


/*交易*/

public interface TranService {
    void saveCreateTran(Map<String,Object>map);

    Tran queryTranForDetailById(String id);

    /*查询交易阶段的数据量*/
    List<FunnelVO>queryCountOfTranGroupByStage();
}

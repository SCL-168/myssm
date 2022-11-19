package com.scl.crm.settings.service;

import com.scl.crm.settings.domain.DicValue;

import java.util.List;

public interface DicValueService {
    //根据typeCode查询字典值
    List<DicValue>queryDivValueByTypeCode(String typeCode);
}

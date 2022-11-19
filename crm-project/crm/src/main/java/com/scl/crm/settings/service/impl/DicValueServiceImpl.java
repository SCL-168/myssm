package com.scl.crm.settings.service.impl;

import com.scl.crm.settings.domain.DicValue;
import com.scl.crm.settings.mapper.DicValueMapper;
import com.scl.crm.settings.service.DicValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("dicValueService")
public class DicValueServiceImpl implements DicValueService {


    @Autowired
    private DicValueMapper dicValueMapper;

    //根据typeCode查询字典值
    @Override
    public List<DicValue> queryDivValueByTypeCode(String typeCode) {
        return dicValueMapper.selectDivValueByTypeCode(typeCode);
    }
}

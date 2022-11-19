package com.scl.crm.workbench.service;

import com.scl.crm.workbench.domain.TranRemark;

import java.util.List;

/*交易备注*/
public interface TranRemarkService {
    List<TranRemark>queryTranRemarkForDetailByTranId(String tranId);
}

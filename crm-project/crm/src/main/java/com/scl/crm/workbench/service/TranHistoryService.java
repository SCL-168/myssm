package com.scl.crm.workbench.service;

import com.scl.crm.workbench.domain.TranHistory;

import java.util.List;

/*
* 交易历史
* */
public interface TranHistoryService {
    List<TranHistory>queryTranHistoryForDetailByTranId(String tranId);
}

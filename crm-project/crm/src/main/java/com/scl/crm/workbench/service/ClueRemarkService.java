package com.scl.crm.workbench.service;

import com.scl.crm.workbench.domain.ClueRemark;

import java.util.List;

/*
* 线索备注
* */
public interface ClueRemarkService {

    List<ClueRemark>queryClueRemarkForDetailByClueId(String clueId);
}

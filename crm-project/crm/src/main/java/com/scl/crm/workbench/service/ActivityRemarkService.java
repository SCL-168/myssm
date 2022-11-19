package com.scl.crm.workbench.service;

import com.scl.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkService {

     //根据activity查询市场活动下所有备注明细
    List<ActivityRemark>queryActivityRemarkForDetailByActivityId(String activityId);

    //保存创建的市场活动备注
    int saveCreateActivityRemark(ActivityRemark remark);

    //根据ID删除备注
    int deleteActivityRemarkById(String id);

    //修改备注数据
    int saveEditActivityRemark(ActivityRemark remark);

}

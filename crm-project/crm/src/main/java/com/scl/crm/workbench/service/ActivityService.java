package com.scl.crm.workbench.service;

import com.scl.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityService {

    int saveCreateActivity(Activity activity);

    //分页查询
    List<Activity>queryActivityByConditionForPage(Map<String,Object>map);

    //查询总条数
    int queryCountOfActivityByCondition(Map<String,Object>map);

    int deleteActivityByIds(String[] ids);

    Activity queryActivityById(String id);

    //修改市场活动
    int saveEditActivity(Activity activity);

    //查询所有市场活动列表，批量导出
    List<Activity>queryAllActivitys();

    //保存批量导入市场活动列表
    int saveCreateActivityByList(List<Activity>activityList);

    //根据ID查询市场活动明细
    Activity queryActivityForDetailById(String id);

    //线索所用，根据clueId查询该线索下所有备注
    List<Activity>queryActivityForDetailByClueId(String clueId);

    //线索所用,根据name模糊查询市场活动，并且把已经跟clueId关联的排除掉
    List<Activity>queryActivityForDetailByNameClueId(Map<String,Object>map);

    //线索所用,根据IDS查询市场活动的明细信息
    List<Activity>queryActivityForDetailByIds(String[] ids);

    //线索所用,根据name模糊查询市场活动列表，且已经与clueId关联的线索市场活动列表
    List<Activity>queryActivityForConvertByNameClueId(Map<String,Object>map);

}

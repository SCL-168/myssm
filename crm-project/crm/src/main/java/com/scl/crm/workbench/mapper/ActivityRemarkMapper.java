package com.scl.crm.workbench.mapper;

import com.scl.crm.workbench.domain.Activity;
import com.scl.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Fri Oct 14 16:48:02 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Fri Oct 14 16:48:02 CST 2022
     */
    int insert(ActivityRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Fri Oct 14 16:48:02 CST 2022
     */
    int insertSelective(ActivityRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Fri Oct 14 16:48:02 CST 2022
     */
    ActivityRemark selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Fri Oct 14 16:48:02 CST 2022
     */
    int updateByPrimaryKeySelective(ActivityRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Fri Oct 14 16:48:02 CST 2022
     */
    int updateByPrimaryKey(ActivityRemark record);

    /*
    * 根据activityId查询市场活动下所有备注明细
    * */
    List<ActivityRemark>selectActivityRemarkForDetailByActivityId(String activityId);

    /*
    * 保存创建的市场活动备注
    * */
    int insertActivityRemark(ActivityRemark remark);

    /*
    * 根据ID删除备注
    * */
    int deleteActivityRemarkById(String id);

    /*
    * 修改备注数据
    * */
    int updateActivityRemark(ActivityRemark activityRemark);
}
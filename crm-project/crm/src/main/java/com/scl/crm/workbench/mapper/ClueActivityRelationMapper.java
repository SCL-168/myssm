package com.scl.crm.workbench.mapper;

import com.scl.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_activity_relation
     *
     * @mbggenerated Fri Oct 28 19:21:26 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_activity_relation
     *
     * @mbggenerated Fri Oct 28 19:21:26 CST 2022
     */
    int insert(ClueActivityRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_activity_relation
     *
     * @mbggenerated Fri Oct 28 19:21:26 CST 2022
     */
    int insertSelective(ClueActivityRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_activity_relation
     *
     * @mbggenerated Fri Oct 28 19:21:26 CST 2022
     */
    ClueActivityRelation selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_activity_relation
     *
     * @mbggenerated Fri Oct 28 19:21:26 CST 2022
     */
    int updateByPrimaryKeySelective(ClueActivityRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_activity_relation
     *
     * @mbggenerated Fri Oct 28 19:21:26 CST 2022
     */
    int updateByPrimaryKey(ClueActivityRelation record);

    /*
    * 批量保存创建的线索和市场活动的关联关系
    * */
    int insertClueActivityRelationByList(List<ClueActivityRelation>list);

    //根据clueId和activityId删除线索和市场活动的关联关系
    int deleteClueIdActivityRelationByClueIdActivityId(ClueActivityRelation relation);

    //根据clueID查询市场活动和线索的关联关系
    List<ClueActivityRelation>selectClueActivityRemarkByClueId(String clueId);

    //根据clueID删除市场活动和线索的关联关系
    int deleteClueActivityRemarkByClueId(String clueId);

}
package com.scl.crm.workbench.mapper;

import com.scl.crm.workbench.domain.ContactsActivityRelation;

import java.util.List;

/*
* 联系人和市场活动关联关系
* */
public interface ContactsActivityRelationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts_activity_relation
     *
     * @mbggenerated Mon Oct 31 10:45:59 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts_activity_relation
     *
     * @mbggenerated Mon Oct 31 10:45:59 CST 2022
     */
    int insert(ContactsActivityRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts_activity_relation
     *
     * @mbggenerated Mon Oct 31 10:45:59 CST 2022
     */
    int insertSelective(ContactsActivityRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts_activity_relation
     *
     * @mbggenerated Mon Oct 31 10:45:59 CST 2022
     */
    ContactsActivityRelation selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts_activity_relation
     *
     * @mbggenerated Mon Oct 31 10:45:59 CST 2022
     */
    int updateByPrimaryKeySelective(ContactsActivityRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts_activity_relation
     *
     * @mbggenerated Mon Oct 31 10:45:59 CST 2022
     */
    int updateByPrimaryKey(ContactsActivityRelation record);

    /*
    * 批量保存创建者联系人和市场活动的关联关系
    * */
    int insertContactsActivityRelationByList(List<ContactsActivityRelation>list);
}
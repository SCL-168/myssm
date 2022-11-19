package com.scl.crm.workbench.mapper;

import com.scl.crm.workbench.domain.CustomerRemark;

import java.util.List;

/*
* 客户备注表
* */
public interface CustomerRemarkMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer_remark
     *
     * @mbggenerated Sun Oct 30 21:57:30 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer_remark
     *
     * @mbggenerated Sun Oct 30 21:57:30 CST 2022
     */
    int insert(CustomerRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer_remark
     *
     * @mbggenerated Sun Oct 30 21:57:30 CST 2022
     */
    int insertSelective(CustomerRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer_remark
     *
     * @mbggenerated Sun Oct 30 21:57:30 CST 2022
     */
    CustomerRemark selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer_remark
     *
     * @mbggenerated Sun Oct 30 21:57:30 CST 2022
     */
    int updateByPrimaryKeySelective(CustomerRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer_remark
     *
     * @mbggenerated Sun Oct 30 21:57:30 CST 2022
     */
    int updateByPrimaryKey(CustomerRemark record);

    //保存客户备注信息
    int insertCustomerRemarkByList(List<CustomerRemark>list);

}
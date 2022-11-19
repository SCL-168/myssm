package com.scl.crm.workbench.service.impl;

import com.scl.crm.commons.contants.Contants;
import com.scl.crm.commons.utils.DateUtils;
import com.scl.crm.commons.utils.UUIDUtils;
import com.scl.crm.settings.domain.User;
import com.scl.crm.workbench.domain.*;
import com.scl.crm.workbench.mapper.*;
import com.scl.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("clueService")
public class ClueServiceImpl implements ClueService {

    @Autowired
    private ClueMapper clueMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private ContactsMapper contactsMapper;

    @Autowired
    private ClueRemarkMapper clueRemarkMapper;

    @Autowired
    private CustomerRemarkMapper customerRemarkMapper;

    @Autowired
    private ContactsRemarkMapper contactsRemarkMapper;

    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;

    @Autowired
    private ContactsActivityRelationMapper contactsActivityRelationMapper;

    @Autowired
    private TranMapper tranMapper;

    @Autowired
    private TranRemarkMapper tranRemarkMapper;

    @Override
    public int saveCreateClue(Clue clue) {

        return clueMapper.insertClue(clue);
    }

    //跟据ID查询线索明细
    @Override
    public Clue queryClueForDetailById(String id) {
        return clueMapper.selectClueForDetailById(id);
    }

    @Override
    public void saveConvertClue(Map<String, Object> map) {
        String clueId= (String) map.get("clueId");
        //获取当前用户
        User user=(User) map.get(Contants.SESSION_USER);
        //根据ID查询线索信息
        Clue clue=clueMapper.selectClueById(clueId);
        String isCreateTran=(String)map.get("isCreateTran");
        //把线索中有关公司的信息转换到客户表中
        Customer c=new Customer();
        c.setAddress(clue.getAddress());
        c.setContactSummary(clue.getContactSummary());
        c.setCreateBy(user.getId());
        c.setCreateTime(DateUtils.formateDateTime(new Date()));
        c.setDescription(clue.getDescription());
        c.setId(UUIDUtils.getUUID());
        c.setName(clue.getCompany());
        c.setNextContactTime(clue.getNextContactTime());
        c.setOwner(user.getId());
        c.setPhone(clue.getPhone());
        c.setWebsite(clue.getWebsite());
        //调用保存客户的service
        customerMapper.insertCustomer(c);
        //把该线索中有关个人信息转换为联系人表中
        Contacts co=new Contacts();
        co.setAddress(clue.getAddress());
        co.setAppellation(clue.getAppellation());
        co.setContactSummary(clue.getContactSummary());
        co.setCreateBy(user.getId());
        co.setCreateTime(DateUtils.formateDateTime(new Date()));
        co.setCustomerId(c.getId());
        co.setDescription(clue.getDescription());
        co.setEmail(clue.getEmail());
        co.setFullname(clue.getFullname());
        co.setId(UUIDUtils.getUUID());
        co.setJob(clue.getJob());
        co.setMphone(clue.getMphone());
        co.setNextContactTime(clue.getNextContactTime());
        co.setOwner(user.getId());
        co.setSource(clue.getSource());
        //调用保存联系人的service
        contactsMapper.insertContacts(co);

        //根据clueID查询所有备注
        List<ClueRemark> crList = clueRemarkMapper.selectClueRemarkByClueId(clueId);
        //如果该线索下有备注，则把备注保存到客户备注表中,且把备注转换到所有联系人备注表中
        if(crList!=null&&crList.size()>0){
            //遍历List
            CustomerRemark cur=null;
            List<CustomerRemark>curList=new ArrayList<>();
            ContactsRemark cor=null;
            List<ContactsRemark>corList=new ArrayList<>();
            for (ClueRemark cr:crList){
                cur=new CustomerRemark();
                cur.setCreateBy(cr.getCreateBy());
                cur.setCreateTime(cr.getCreateTime());
                cur.setCustomerId(cr.getId());
                cur.setEditBy(cr.getEditBy());
                cur.setEditFlag(cr.getEditFlag());
                cur.setEditTime(cr.getEditTime());
                cur.setId(UUIDUtils.getUUID());
                cur.setNoteContent(cr.getNoteContent());
                curList.add(cur);

                //客户备注
                cor=new ContactsRemark();
                cor.setContactsId(co.getId());
                cor.setCreateBy(cr.getCreateBy());
                cor.setCreateTime(cr.getCreateTime());
                cor.setEditBy(cr.getEditBy());
                cor.setEditFlag(cr.getEditFlag());
                cor.setEditTime(cr.getEditTime());
                cor.setId(UUIDUtils.getUUID());
                cor.setNoteContent(cr.getNoteContent());
                corList.add(cor);
            }
             //调用客户备注service
            customerRemarkMapper.insertCustomerRemarkByList(curList);
             //调用联系人service
            contactsRemarkMapper.insertContactsRemarkByList(corList);
        }
        //调用service根据clueID查询市场活动和线索关联关系
        List<ClueActivityRelation> carList = clueActivityRelationMapper.selectClueActivityRemarkByClueId(clueId);

        //把该线索和市场活动的关联关系转换到联系人和市场活动的关联关系表中
        ContactsActivityRelation coar=null;
        List<ContactsActivityRelation>coarList=new ArrayList<>();
        if(carList!=null&&carList.size()>0){
            for(ClueActivityRelation car:carList){
                coar=new ContactsActivityRelation();
                coar.setActivityId(car.getActivityId());
                coar.setContactsId(co.getId());
                coar.setId(UUIDUtils.getUUID());
                //保存到list中
                coarList.add(coar);
            }
            //调用联系人和市场活动管来关系的service
            contactsActivityRelationMapper.insertContactsActivityRelationByList(coarList);
        }
        //如果需要创建交易，则往交易表中添加记录,且需要把该线索下的备注转到交易备注表中
        if("true".equals(isCreateTran)){
           Tran tran=new Tran();
             String mp=(String) map.get("activityId");
             tran.setActivityId(mp);
             tran.setContactsId(co.getId());
             tran.setCreateBy(user.getId());
             tran.setCreateTime(DateUtils.formateDateTime(new Date()));
             tran.setCustomerId(c.getId());
             tran.setExpectedDate((String)map.get("expectedDate"));
             tran.setId(UUIDUtils.getUUID());
             tran.setMoney((String) map.get("money"));
             tran.setName((String) map.get("name"));
             tran.setOwner(user.getId());
             tran.setStage((String) map.get("stage"));
             //调用Tran的service
             tranMapper.insertTran(tran);
             //添加备注

            if(crList!=null&&crList.size()>0){
                TranRemark tr=null;
                List<TranRemark>trList=new ArrayList<>();
                for(ClueRemark cr:crList){
                    tr=new TranRemark();
                    tr.setCreateBy(cr.getCreateBy());
                    tr.setCreateTime(cr.getCreateTime());
                    tr.setEditBy(cr.getEditBy());
                    tr.setEditFlag(cr.getEditFlag());
                    tr.setEditTime(cr.getEditTime());
                    tr.setId(UUIDUtils.getUUID());
                    tr.setNoteContent(cr.getNoteContent());
                    tr.setTranId(tran.getId());
                    //封装
                    trList.add(tr);
                }
                //调用service转换
                tranRemarkMapper.insertTranRemarkByList(trList);
            }
        }

        //调用线索备注mapper
        clueRemarkMapper.deleteCLueRemarkByClueId(clueId);
        //调用线索和市场活动关联关系service
        clueActivityRelationMapper.deleteClueActivityRemarkByClueId(clueId);
        //调用线索service
        clueMapper.deleteClueById(clueId);
    }
}

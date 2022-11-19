package com.scl.crm.workbench.web.controller;

import com.scl.crm.commons.contants.Contants;
import com.scl.crm.commons.domain.ReturnObject;
import com.scl.crm.commons.utils.DateUtils;
import com.scl.crm.commons.utils.UUIDUtils;
import com.scl.crm.settings.domain.User;
import com.scl.crm.workbench.domain.ActivityRemark;
import com.scl.crm.workbench.service.ActivityRemarkService;
import com.sun.org.apache.bcel.internal.generic.D2F;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class ActivityRemarkController {

    @Autowired
    private ActivityRemarkService activityRemarkService;


    @RequestMapping("/workbench/activity/saveCreateActivityRemark.do")
    @ResponseBody
    //备注的添加
    public Object saveCreateActivityRemark(ActivityRemark remark, HttpSession session) {

        User user = (User) session.getAttribute(Contants.SESSION_USER);
        //封装参数
        remark.setId(UUIDUtils.getUUID());
        remark.setCreateTime(DateUtils.formateDateTime(new Date()));
        remark.setCreateBy(user.getId());
        remark.setEditFlag(Contants.REMARK_EDIT_FLAG_NO_EDITED);

        ReturnObject returnObject = new ReturnObject();
        try {
            //调用service
            int ret = activityRemarkService.saveCreateActivityRemark(remark);
            //判断是否保存成功
            if (ret > 0) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setRetData(remark);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FALSE);
                returnObject.setMessage("系统繁忙，请稍后重试！");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FALSE);
            returnObject.setMessage("系统繁忙，请稍后重试！");
        }
        return returnObject;
    }

    //根据ID删除备注
    @RequestMapping("/workbench/activity/deleteActivityRemarkById.do")
    @ResponseBody
    public Object deleteActivityRemarkById(String id){

        ReturnObject returnObject=new ReturnObject();
        try{
            //调用service，获取选择ID
            int ret = activityRemarkService.deleteActivityRemarkById(id);
            if(ret>0){
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            }else{
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FALSE);
                returnObject.setMessage("系统繁忙，请稍后重试！");
            }
        }catch (Exception ex){
              ex.printStackTrace();
              returnObject.setCode(Contants.RETURN_OBJECT_CODE_FALSE);
              returnObject.setMessage("系统繁忙，请稍后重试！");
        }
        return returnObject;
    }

    //修改备注数据
    @RequestMapping("/workbench/activity/saveEditActivityRemark.do")
    @ResponseBody
    public Object saveEditActivityRemark(ActivityRemark activityRemark,HttpSession session){
        User user = (User) session.getAttribute(Contants.SESSION_USER);
        //封装参数
        activityRemark.setEditTime(DateUtils.formateDateTime(new Date()));
        activityRemark.setEditBy(user.getId());
        activityRemark.setEditFlag(Contants.REMARK_EDIT_FLAG_YES_EDITED);

        ReturnObject returnObject=new ReturnObject();
        try {
            //调用service
            int ret = activityRemarkService.saveEditActivityRemark(activityRemark);
            if(ret>0){
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setRetData(activityRemark);
            }else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FALSE);
                returnObject.setMessage("系统繁忙，请稍后重试！");
            }
        }catch (Exception exception){
                exception.printStackTrace();
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FALSE);
                returnObject.setMessage("系统繁忙，请稍后重试！");
        }
        return returnObject;
    }
}

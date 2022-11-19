package com.scl.crm.workbench.web.controller;


import com.scl.crm.commons.contants.Contants;
import com.scl.crm.commons.domain.ReturnObject;
import com.scl.crm.commons.utils.DateUtils;
import com.scl.crm.commons.utils.UUIDUtils;
import com.scl.crm.settings.domain.DicValue;
import com.scl.crm.settings.domain.User;
import com.scl.crm.settings.service.DicValueService;
import com.scl.crm.settings.service.UserService;
import com.scl.crm.workbench.domain.Activity;
import com.scl.crm.workbench.domain.Clue;
import com.scl.crm.workbench.domain.ClueActivityRelation;
import com.scl.crm.workbench.domain.ClueRemark;
import com.scl.crm.workbench.service.ActivityService;
import com.scl.crm.workbench.service.ClueActivityRelationService;
import com.scl.crm.workbench.service.ClueRemarkService;
import com.scl.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class ClueController {

     @Autowired
     private UserService userService;

     @Autowired
     private DicValueService divValueService;

     @Autowired
     private ClueService clueService;

     @Autowired
     private ClueRemarkService clueRemarkService;//线索列表备注

     @Autowired
     private ActivityService activityService;
     
     @Autowired
     private ClueActivityRelationService clueActivityRelationService;


     //跳转到线索页面
    @RequestMapping("/workbench/clue/index.do")
     public String index(HttpServletRequest request){
          //调用service
        List<User>userList=userService.queryAllUsers();
        List<DicValue>appellationList=divValueService.queryDivValueByTypeCode("appellation");//appellation称呼的code值
        List<DicValue> clueStateList = divValueService.queryDivValueByTypeCode("clueState");//线索的状态
        List<DicValue> sourceList = divValueService.queryDivValueByTypeCode("source");//线索来源
        //保存到作用域中
        request.setAttribute("userList",userList );
        request.setAttribute("clueStateList", clueStateList);
        request.setAttribute("appellationList", appellationList);
        request.setAttribute("sourceList", sourceList);
        //请求转发
        return "workbench/clue/index";
     }

     //保存新创建线索表
    @RequestMapping("/workbench/clue/saveCreateClue.do")
    @ResponseBody
    public Object saveCreateClue(Clue clue,HttpSession session){
        User user=(User) session.getAttribute(Contants.SESSION_USER);
        //封装参数
        clue.setId(UUIDUtils.getUUID());
        clue.setCreateTime(DateUtils.formateDateTime(new Date()));
        clue.setCreateBy(user.getId());

        ReturnObject returnObject=new ReturnObject();
        try{
            //调用service
            int ret = clueService.saveCreateClue(clue);
            if (ret>0){
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            }else {
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

    //查看线索明细
    @RequestMapping("/workbench/clue/detailClue.do")
    public String detailClue(String id,HttpServletRequest request){
        //调用各service层
        Clue clue = clueService.queryClueForDetailById(id);
        List<ClueRemark> remarkList = clueRemarkService.queryClueRemarkForDetailByClueId(id);
        List<Activity> activityList = activityService.queryActivityForDetailByClueId(id);
        //把数据保存到request中
        request.setAttribute("clue", clue);
        request.setAttribute("remarkList", remarkList);
        request.setAttribute("activityList", activityList);
        //请求转发
        return "workbench/clue/detail";
    }

    //根据名称模糊查询
    @RequestMapping("/workbench/clue/queryActivityForDetailByNameClueId.do")
    @ResponseBody
    public Object queryActivityForDetailByNameClueId(String activityName,String clueId){
        //封装参数
        Map<String,Object>map=new HashMap<>();
        map.put("activityName", activityName);
        map.put("clueId", clueId);
        //调用service
        List<Activity> activityList = activityService.queryActivityForDetailByNameClueId(map);
        //根据查询结果，返回响应信息
        return activityList;
    }

    //保存关联市场活动
    @RequestMapping("/workbench/clue/saveBund.do")
    @ResponseBody
    public Object saveBund(String[] activityId,String clueId) {
        //封装参数
        ClueActivityRelation car = null;
        List<ClueActivityRelation> relationList = new ArrayList<>();

        for (String ai : activityId) {
            car = new ClueActivityRelation();
            car.setActivityId(ai);
            car.setClueId(clueId);
            car.setId(UUIDUtils.getUUID());
            //封装在list中
            relationList.add(car);
        }
            ReturnObject returnObject = new ReturnObject();
            try {
                //调用service
                int ret = clueActivityRelationService.saveCreateClueActivityRelationByList(relationList);
                if (ret > 0) {
                    returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                    List<Activity> activityList = activityService.queryActivityForDetailByIds(activityId);
                    returnObject.setRetData(activityList);
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

    //根据clueId和activityId删除线索和市场活动关联关系
    @RequestMapping("/workbench/clue/saveUnbund.do")
    @ResponseBody
    public Object saveUnbund(ClueActivityRelation relation){
        ReturnObject returnObject=new ReturnObject();
        try{
            //调用service
            int ret = clueActivityRelationService.deleteClueActivityRelationByClueIdActivityId(relation);
            if(ret>0){
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            }else {
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

    //转
    @RequestMapping("/workbench/clue/toConvert.do")
    public String toConvert(String id,HttpServletRequest request){
        //调用service
        Clue clue=clueService.queryClueForDetailById(id);
        List<DicValue> stageList = divValueService.queryDivValueByTypeCode("stage");
        //把数据存入request
        request.setAttribute("clue", clue);
        request.setAttribute("stageList", stageList);
        //请求转发
        return "workbench/clue/convert";
    }

    //根据name模糊查询与clueId关联的线索市场活动列表
    @RequestMapping("/workbench/clue/queryActivityForConvertByNameClueId.do")
    @ResponseBody
    public Object queryActivityForConvertByNameClueId(String activityName,String clueId){
         //封装参数
        Map<String,Object>map=new HashMap<>();
        map.put("activityName",activityName);
        map.put("clueId", clueId);
        //调用service
        List<Activity> activityList = activityService.queryActivityForConvertByNameClueId(map);
        //根据查询结果返回响应信息
        return activityList;
    }


    //转换保存客户信息
    @RequestMapping("/workbench/clue/convertClue.do")
    @ResponseBody
    public Object convertClue(String clueId,String money,String name,
                              String expectedDate,String stage,String activityId,
                              String isCreateTran,HttpSession session){

        session.getAttribute(Contants.SESSION_USER);
        //封装参数
        Map<String,Object>map=new HashMap<>();
        map.put("clueId",clueId);
        map.put("money",money);
        map.put("name",name);
        map.put("expectedDate",expectedDate);
        map.put("stage",stage);
        map.put("activityId",activityId);
        map.put("isCreateTran", isCreateTran);
        map.put(Contants.SESSION_USER, session.getAttribute(Contants.SESSION_USER));

        ReturnObject returnObject=new ReturnObject();
        try{
            //调用service
            clueService.saveConvertClue(map);
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        }catch (Exception ex){
            ex.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FALSE);
            returnObject.setMessage("系统繁忙，请稍后重试！");
        }
        return returnObject;
    }
}

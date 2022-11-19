package com.scl.crm.workbench.web.controller;


import com.scl.crm.commons.contants.Contants;
import com.scl.crm.commons.domain.ReturnObject;
import com.scl.crm.settings.domain.DicValue;
import com.scl.crm.settings.domain.User;
import com.scl.crm.settings.service.DicValueService;
import com.scl.crm.settings.service.UserService;
import com.scl.crm.workbench.domain.Tran;
import com.scl.crm.workbench.domain.TranHistory;
import com.scl.crm.workbench.domain.TranRemark;
import com.scl.crm.workbench.service.CustomerService;
import com.scl.crm.workbench.service.TranHistoryService;
import com.scl.crm.workbench.service.TranRemarkService;
import com.scl.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


@Controller
public class TranController {

    @Autowired
    private DicValueService dicValueService;

    @Autowired
    private UserService userService;

   @Autowired
   private CustomerService customerService;

   @Autowired
   private TranService tranService;
   
   @Autowired
   private TranRemarkService tranRemarkService;

   @Autowired
   private TranHistoryService tranHistoryService;


    @RequestMapping("/workbench/transaction/index.do")
    public String index(HttpServletRequest request){
        //调用service
        List<DicValue> stageList = dicValueService.queryDivValueByTypeCode("stage");
        List<DicValue> transactionTypeList = dicValueService.queryDivValueByTypeCode("transactionType");
        List<DicValue> sourceList = dicValueService.queryDivValueByTypeCode("source");
        //保存到作用域
        request.setAttribute("stageList",stageList);
        request.setAttribute("transactionTypeList", transactionTypeList);
        request.setAttribute("sourceList",sourceList);
        //请求转发
        return "workbench/transaction/index";
    }

    @RequestMapping("/workbench/transaction/toSave.do")
    public String toSave(HttpServletRequest request){
        //调用service，查询所有用户
        List<User> userList = userService.queryAllUsers();
        List<DicValue> stageList = dicValueService.queryDivValueByTypeCode("stage");
        List<DicValue> transactionTypeList = dicValueService.queryDivValueByTypeCode("transactionType");
        List<DicValue> sourceList = dicValueService.queryDivValueByTypeCode("source");
        //保存到作用域中
        request.setAttribute("userList", userList);
        request.setAttribute("stageList",stageList);
        request.setAttribute("transactionTypeList", transactionTypeList);
        request.setAttribute("sourceList",sourceList);
        //请求转发
        return "workbench/transaction/save";
    }


    @RequestMapping("/workbench/transaction/getPossibilityByStage.do")
    @ResponseBody
    public Object getPossibilityByStage(String stageValue ){
        //获取解析文件
        ResourceBundle bundle = ResourceBundle.getBundle("possibility");
        String possibility = bundle.getString(stageValue);
        return possibility;
    }

    @RequestMapping("/workbench/transaction/queryCustomerNameByName.do")
    @ResponseBody
    public Object queryCustomerNameByName(String customerName){
        //调用service
        List<String> customerNameList=customerService.queryCustomerNameByName(customerName);
        //根据处理结果返回响应信息
        return customerNameList;
    }

    @RequestMapping("/workbench/transaction/saveCreateTran.do")
    @ResponseBody
    public Object saveCreateTran(@RequestParam Map<String,Object> map, HttpSession session){
        //接收参数
        map.put(Contants.SESSION_USER, session.getAttribute(Contants.SESSION_USER));

        ReturnObject returnObject=new ReturnObject();
        try {
            //调用service
            tranService.saveCreateTran(map);
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FALSE);
            returnObject.setMessage("系统繁忙，请稍后重试！");
        }

        return returnObject;
    }


    @RequestMapping("/workbench/transaction/detailTran.do")
    public String detailTran(String id,HttpServletRequest request){
        //调用service
        Tran tran = tranService.queryTranForDetailById(id);
        List<TranRemark> remarkList = tranRemarkService.queryTranRemarkForDetailByTranId(id);
        List<TranHistory> historyList = tranHistoryService.queryTranHistoryForDetailByTranId(id);
        //根据交易阶段名称查询交易阶段状态
        ResourceBundle bundle = ResourceBundle.getBundle("possibility");
        String possibility = bundle.getString(tran.getStage());
        tran.setPossibility(possibility);//扩展属性，见Tran类里面
        //把数据保存到作用域
        request.setAttribute("tran",tran);
        request.setAttribute("remarkList", remarkList);
        request.setAttribute("historyList",historyList );
        //调用交易数据字典值service
        List<DicValue> stageList = dicValueService.queryDivValueByTypeCode("stage");
        request.setAttribute("stageList",stageList);
        //请求转发
        return "workbench/transaction/detail";


    }
}

package com.scl.crm.workbench.web.controller;


import com.scl.crm.workbench.domain.FunnelVO;
import com.scl.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ChartController {

    @Autowired
    private TranService tranService;


    @RequestMapping("/workbench/chart/transaction/index.do")
    public String index(){
        return "workbench/chart/transaction/index";
    }

    @RequestMapping("/workbench/chart/transaction/queryCountOfTranGroupByStage.do")
    @ResponseBody
    public Object queryCountOfTranGroupByStage(){
        //调用交易service
        List<FunnelVO> funnelVOList = tranService.queryCountOfTranGroupByStage();
        //返回响应信息
        return funnelVOList;
    }
}

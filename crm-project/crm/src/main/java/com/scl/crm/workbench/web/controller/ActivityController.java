package com.scl.crm.workbench.web.controller;

import com.scl.crm.commons.contants.Contants;
import com.scl.crm.commons.domain.ReturnObject;
import com.scl.crm.commons.utils.DateUtils;
import com.scl.crm.commons.utils.HSSFUtils;
import com.scl.crm.commons.utils.UUIDUtils;
import com.scl.crm.settings.domain.User;
import com.scl.crm.settings.service.UserService;
import com.scl.crm.workbench.domain.Activity;
import com.scl.crm.workbench.domain.ActivityRemark;
import com.scl.crm.workbench.service.ActivityRemarkService;
import com.scl.crm.workbench.service.ActivityService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

//import static com.scl.crm.commons.contants.Contants.*;

@Controller
public class ActivityController {

    //调用service
    @Autowired
    private UserService userService;

    //调用市场service
    @Autowired
    private ActivityService activityService;

    @Autowired
    //调用备注service
    private ActivityRemarkService activityRemarkService;

    @RequestMapping("/workbench/activity/index.do")
    public String index(HttpServletRequest request) {
        //调用service查询所有用户
        List<User> userList = userService.queryAllUsers();
        //把查询结果放到作用域中
        request.setAttribute("userList", userList);
        //请求转发
        return "workbench/activity/index";
    }


    //创建市场活动
    @RequestMapping("/workbench/activity/saverCreateActivity.do")
    @ResponseBody
    public Object saverCreateActivity(Activity activity, HttpSession session) {
        User user = (User) session.getAttribute(Contants.SESSION_USER);//获取当前用户
        //封装参数,使用UUID生成唯一ID
        activity.setId(UUIDUtils.getUUID());//市场活动表ID
        activity.setCreateTime(DateUtils.formateDateTime(new Date()));//市场活动创建时间
        activity.setCreateBy(user.getId());//市场活动由谁创建

        ReturnObject returnObject = new ReturnObject();
        //调用市场service保存
        try {
            int ret = activityService.saveCreateActivity(activity);
            if (ret > 0) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);//保存成功
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FALSE);//保存失败
                returnObject.setMessage("系统繁忙，请稍后重试！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FALSE);//保存失败
            returnObject.setMessage("系统繁忙，请稍后重试！");
        }
        return returnObject;
    }


    //查询市场活动表
    @RequestMapping("/workbench/activity/queryActivityByConditionForPage.do")
    @ResponseBody
    public Object queryActivityByConditionForPage(String name, String owner, String startDate, String endDate, int pageNo, int pageSize) {
        //封装参数
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("owner", owner);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("beginNo", (pageNo - 1) * pageSize);
        map.put("pageSize", pageSize);
        //调用service,查询市场活动列表
        List<Activity> activityList = activityService.queryActivityByConditionForPage(map);
        //查询总条数
        int totalRows = activityService.queryCountOfActivityByCondition(map);
        //根据查询结果，生成响应信息
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("activityList", activityList);
        retMap.put("totalRows", totalRows);
        return retMap;
    }

    //根据ID删除指定的市场活动
    @RequestMapping("/workbench/activity/deleteActivityIds.do")
    @ResponseBody
    public Object deleteActivityIds(String[] id) {
        ReturnObject returnObject = new ReturnObject();
        //调用service
        try {
            int ret = activityService.deleteActivityByIds(id);
            //判断是否删除成功
            if (ret > 0) {
                //删除成功
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FALSE);
                returnObject.setMessage("系统繁忙，请稍后重试！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FALSE);
            returnObject.setMessage("系统繁忙，请稍后重试！");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/queryActivityById.do")
    @ResponseBody
    public Object queryActivityById(String id) {
        //调用service查询
        Activity activity = activityService.queryActivityById(id);
        return activity;
    }

    //修改市场活动列表信息
    @RequestMapping("/workbench/activity/saveEditActivity.do")
    @ResponseBody
    public Object saveEditActivity(Activity activity, HttpSession session) {
        User user = (User) session.getAttribute(Contants.SESSION_USER);
        //封装参数
        activity.setEditTime(DateUtils.formateDateTime(new Date()));
        activity.setEditBy(user.getId());
        //调用service
        ReturnObject returnObject = new ReturnObject();
        try {
            int ret = activityService.saveEditActivity(activity);
            if (ret > 0) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FALSE);
                returnObject.setMessage("系统繁忙，请稍后重试！");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FALSE);
            returnObject.setMessage("系统繁忙，请稍后重试！");
        }
        return returnObject;
    }

    //批量导出市场活动列表信息
    @RequestMapping("/workbench/activity/exportAllActivitys.do")
    public void exportAllActivitys(HttpServletResponse response) throws IOException {
        //调用service层
        List<Activity> activities = activityService.queryAllActivitys();
        //创建Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("市场活动列表");//页
        HSSFRow row = sheet.createRow(0);//行
        HSSFCell cell = row.createCell(0);//列
        cell.setCellValue("id");
        cell = row.createCell(1);
        cell.setCellValue("所有者");
        cell = row.createCell(2);
        cell.setCellValue("名称");
        cell = row.createCell(3);
        cell.setCellValue("开始日期");
        cell = row.createCell(4);
        cell.setCellValue("结束日期");
        cell = row.createCell(5);
        cell.setCellValue("成本");
        cell = row.createCell(6);
        cell.setCellValue("描述");
        cell = row.createCell(7);
        cell.setCellValue("创建时间");
        cell = row.createCell(8);
        cell.setCellValue("创建者");
        cell = row.createCell(9);
        cell.setCellValue("修改时间");
        cell = row.createCell(10);
        cell.setCellValue("修改者");

        //遍历集合
        if (activities != null && activities.size() > 0) {
            Activity activity = null;
            for (int i = 0; i < activities.size(); i++) {
                activity = activities.get(i);
                //每遍历出一个activity,生成一行写入
                row = sheet.createRow(i + 1);
                //每一行创建11列
                cell = row.createCell(0);
                cell.setCellValue(activity.getId());
                cell = row.createCell(1);
                cell.setCellValue(activity.getOwner());
                cell = row.createCell(2);
                cell.setCellValue(activity.getName());
                cell = row.createCell(3);
                cell.setCellValue(activity.getStartDate());
                cell = row.createCell(4);
                cell.setCellValue(activity.getEndDate());
                cell = row.createCell(5);
                cell.setCellValue(activity.getCost());
                cell = row.createCell(6);
                cell.setCellValue(activity.getDescription());
                cell = row.createCell(7);
                cell.setCellValue(activity.getCreateTime());
                cell = row.createCell(8);
                cell.setCellValue(activity.getCreateBy());
                cell = row.createCell(9);
                cell.setCellValue(activity.getEditTime());
                cell = row.createCell(10);
                cell.setCellValue(activity.getEditBy());
            }
        }
//        //根据wb对象生成Excel文件
//        OutputStream ot = new FileOutputStream("D:\\Test folder\\activityAll.xls");
//        wb.write(ot);
//
//        //关闭资源
//        ot.close();
//        wb.close();

        //把生成的Excel文件下载到客户端
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=activityAll.xls");
        OutputStream out = response.getOutputStream();
      /*  InputStream fs = new FileInputStream("D:\\Test folder\\activityAll.xls");
        byte[] buff=new byte[256];
        int len=0;
        while ((len= fs.read(buff))!=-1){
            out.write(buff,0,len);
        }
        fs.close();*/
        wb.write(out);
        wb.close();
        out.flush();
    }

    //保存批量导入市场活动列表
    @RequestMapping("/workbench/activity/importActivity.do")
    @ResponseBody
    public Object importActivity(MultipartFile activityFile,HttpSession session){
        User user=(User) session.getAttribute(Contants.SESSION_USER);
        ReturnObject returnObject=new ReturnObject();
        try {

            InputStream is=activityFile.getInputStream();
            HSSFWorkbook wb=new HSSFWorkbook(is);
            //根据wb获取HSSFSheet对象，封装了一页的所有信息
            HSSFSheet sheet=wb.getSheetAt(0);//页的下标，下标从0开始，依次增加
            //根据sheet获取HSSFRow对象，封装了一行的所有信息
            HSSFRow row=null;
            HSSFCell cell=null;
            Activity activity=null;
            List<Activity>activityList=new ArrayList<>();
            for(int i=1;i<=sheet.getLastRowNum();i++) {//sheet.getLastRowNum()：最后一行的下标
                row=sheet.getRow(i);//行的下标，下标从0开始，依次增加
                activity=new Activity();
                activity.setId(UUIDUtils.getUUID());
                activity.setOwner(user.getId());
                activity.setCreateTime(DateUtils.formateDateTime(new Date()));
                activity.setCreateBy(user.getId());

                for(int j=0;j<row.getLastCellNum();j++) {//row.getLastCellNum():最后一列的下标+1
                    //根据row获取HSSFCell对象，封装了一列的所有信息
                    cell=row.getCell(j);//列的下标，下标从0开始，依次增加

                    //获取列中的数据
                    String cellValue=HSSFUtils.getCellValueForStr(cell);
                    if(j==0){
                        activity.setName(cellValue);
                    }else if(j==1){
                        activity.setStartDate(cellValue);
                    }else if(j==2){
                        activity.setEndDate(cellValue);
                    }else if(j==3){
                        activity.setCost(cellValue);
                    }else if(j==4){
                        activity.setDescription(cellValue);
                    }
                }

                //每一行中所有列都封装完成之后，把activity保存到list中
                activityList.add(activity);
            }

            //调用service层方法，保存市场活动
            int ret=activityService.saveCreateActivityByList(activityList);

            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            returnObject.setRetData(ret);
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FALSE);
            returnObject.setMessage("系统忙，请稍后重试....");
        }

        return returnObject;
    }

    //根据activity查询市场活动下所有备注明细
    @RequestMapping("/workbench/activity/detailActivity.do")
    public String detailActivity(String id,HttpServletRequest request){
        //调用service
        Activity activity = activityService.queryActivityForDetailById(id);
        List<ActivityRemark> remarkList = activityRemarkService.queryActivityRemarkForDetailByActivityId(id);
       //把数据保存到作用域
        request.setAttribute("activity", activity);
        request.setAttribute("remarkList", remarkList);
       //请求转发
        return "workbench/activity/detail";
    }


}

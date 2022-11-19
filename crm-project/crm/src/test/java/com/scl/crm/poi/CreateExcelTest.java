package com.scl.crm.poi;



import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/*测试文档POI用法*/
public class CreateExcelTest {
    public static void main(String[] args) throws IOException {
        //生成Excel文件
        HSSFWorkbook wb= new HSSFWorkbook();
        HSSFSheet sheet=wb.createSheet("学生列表");//生成页
        HSSFRow row=sheet.createRow(0);//生成行，以0开始递增
        HSSFCell cell=row.createCell(0);//生成列，以0开始递增
        cell.setCellValue("学号");
        cell=row.createCell(1);
        cell.setCellValue("姓名");
        cell=row.createCell(2);
        cell.setCellValue("年龄");
        //生成HSSFCellStyle对象
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        //使用sheet创建10个HSSFRow对象，对应sheet中的10行
        for (int i=1;i<=10;i++){
            row=sheet.createRow(i);
            cell=row.createCell(0);
            cell.setCellStyle(style);
            cell.setCellValue(100+i);
            cell=row.createCell(1);
            cell.setCellStyle(style);
            cell.setCellValue("A"+i);
            cell=row.createCell(2);
            cell.setCellStyle(style);
            cell.setCellValue(20+i);
        }
        //调用工具函数生成Excel文件
        OutputStream os=new FileOutputStream("D:\\Test folder\\student.xls");
        wb.write(os);

        //关闭资源
        os.close();
        wb.close();
        System.out.println("==========ok==========");
    }
}

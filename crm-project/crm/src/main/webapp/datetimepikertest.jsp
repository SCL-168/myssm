<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String basePath=request.getScheme()+"://"+
            request.getServerName()+":"+
            request.getServerPort()+
            request.getContextPath()+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <%--引入jQuery--%>
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <%--引入bootstrap框架--%>
    <link rel="stylesheet" href="jquery/bootstrap_3.3.0/css/bootstrap.min.css">
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <%--引入bootstrap_dateimepicker--%>
    <link rel="stylesheet" href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css">
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
    <title>演示bs_datatimepicker日历 插件</title>

    <script>
        $(function (){
           $("#myDate").datetimepicker({
               language:"zh-CN",//语言
               format:"yyyy-MM-dd",//格式
               minView:"month",//可以选择的最小范围
               initialDate:new Date(),//初始化显示时间
               autoclose:true,//设置选择完日期或是时间之后，日历是否自动关闭
               todayBtn:true,//设置是否显示今天时间按钮
               clearBtn:"清空",//设置清空按钮
           });
        });
    </script>

</head>
<body>
<input type="text" id="myDate" readonly>
</body>
</html>
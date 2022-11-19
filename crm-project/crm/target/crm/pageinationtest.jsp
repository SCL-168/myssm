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
    <link rel="stylesheet" type="text/css" href="jquery/bootstrap_3.3.0/css/bootstrap.min.css">
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <%--引入bootstrap_dateimepicker--%>
    <link rel="stylesheet"  type="text/css" href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css">
    <script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination-master/localization/en.js"></script>
    <title>演示pagination分页插件</title>

    <script type="text/javascript">
    $(function (){
        $("#demo_pag1").bs_pagination({
            currentPage:2,//当前页号，相当于pageNo
            rowsPerPage:20,//每页显示的条数，相当于pageSize
            totalRows:1000,//总条数
            totalPages:50,//总页数，必填参数
            visiblePageLinks: 10,//最多可显示的卡片数
            showGoToPage: true,//是否显示”跳转到“部分，默认true--显示
            showRowsPerPage: true,//是否显示”每页显示条数“部分，默认true--显示
            showRowsInfo: true,//是否显示记录的信息，默认TRUE--显示
            //用户每次切换页号，都自动触发本函数
            //每次用户切换页号之后的pageNo和pageSize
            onChangePage:function(event,pageObj){
                //JS代码
                alert(pageObj.currentPage);
                alert(pageObj.rowsPerPage);
            }
        });
    });
    </script>

</head>
<body>
<div id="demo_pag1"></div>
</body>
</html>
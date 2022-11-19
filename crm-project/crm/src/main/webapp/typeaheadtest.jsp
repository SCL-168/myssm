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

    <script type="text/javascript" src="jquery/bs_typeahead/bootstrap3-typeahead.min.js"></script>
    <title>演示自动补全</title>

    <script type="text/javascript">
        $(function (){
            $("#customerName").typeahead({
                //source:['京东商城','阿里巴巴','百度网络科技公司','字节跳动','SCL科技技术公司','华为通讯技术公司'];//静态
                //动态
                source:function (jquery,process){
                    //这里的jQuery相当于：var customerName=$("#customerName").val();即获取用户输入的关键字
                    $.ajax({
                        url:"workbench/transaction/queryAllCustomerName.do",
                        data:{
                          customerName:jquery//获取关键字用于模糊查询
                        },
                        type:"post",
                        dataType:"json",
                        success:function (data){
                             process(data);
                        }
                    });
                }
            });
        });
    </script>
</head>
<body>
 <input type="text" id="customerName">
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String basePath = request.getScheme() + "://" +
            request.getServerName() + ":" +
            request.getServerPort() +
            request.getContextPath() + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $(window).keydown(function(e){
               //判断是否为指定键位
                if(e.keyCode==13){
                    $("#loginBtn").click();
                }
            });
            //登录按钮添加事件
            $("#loginBtn").click(function () {
                //获取参数
                var loginAct = $.trim($("#loginAct").val());//trim:去除空格
                var loginPwd = $.trim($("#loginPwd").val());
                var idRemPwd = $.trim($("#isRemPwd").prop("checked"));//获取免登录框状态
                //判断其登录信息是否可行
                if (loginAct == "") {
                    alert("用户名不能为空！");
                    return;
                }
                if(loginPwd == ""){
                  alert("密码不能为空！");
                    return;
                }
                //有正确用户名和密码输入
                //$("#msg").text("正在验证，请稍后......");
                $.ajax({
                    url: "settings/qx/user/login.do",//请求地址
                    data: {
                        loginAct: loginAct,
                        loginPwd: loginPwd,
                        idRemPwd: idRemPwd,
                    },
					type: "post",
                    dataType: "json",
                    success: function (data) {
                        if (data.code == "1") {
                            //登录成功
                            window.location.href = "workbench/index.do";
                        } else {
                            //登录失败
                            $("#msg").text(data.message);
                        }
                    },
                    beforeSend:function (){
                        $("#msg").text("正在验证，请稍后......");
                    },

                });
            });
        });
    </script>
</head>
<body>
<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
    <img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
</div>
<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
    <div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">
        CRM &nbsp;<span style="font-size: 12px;">&copy;2019&nbsp;动力节点</span></div>
</div>

<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
    <div style="position: absolute; top: 0px; right: 60px;">
        <div class="page-header">
            <h1>登录</h1>
        </div>
        <form action="workbench/index.html" class="form-horizontal" role="form">
            <div class="form-group form-group-lg">
                <div style="width: 350px;">
                    <input class="form-control" type="text" id="loginAct"  value="${cookie.loginAct.value}" placeholder="用户名">
                </div>
                <div style="width: 350px; position: relative;top: 20px;">
                    <input class="form-control" type="password" id="loginPwd" value="${cookie.loginPwd.value}" placeholder="密码">
                </div>
                <div class="checkbox" style="position: relative;top: 30px; left: 10px;">
                    <label>
                        <c:if test="${not empty cookie.loginAct and not empty cookie.loginPwd}">
                            <input type="checkbox" id="isRemPwd" checked>
                        </c:if>
                       <c:if test="${empty cookie.loginAct or empty cookie.loginPwd}">
                           <input type="checkbox" id="isRemPwd">
                       </c:if>
                        十天免登陆
                    </label>
                    &nbsp;&nbsp;
                    <span id="msg" style="color: red"></span>
                </div>
                <button type="button" class="btn btn-primary btn-lg btn-block" id="loginBtn"
                        style="width: 350px; position: relative;top: 45px;">登录
                </button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
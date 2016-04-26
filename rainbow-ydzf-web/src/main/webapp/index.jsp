<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <title>移动执法登录</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/js/bootstrap/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/fonts/iconfont.css"/>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/global.css"/>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/login.css"/>
</head>
<body>
<div class="container">
    <form action="<%=request.getContextPath()%>/login" method="post" onsubmit="return false" id="userLogin">
    <div class="row">
        <div class="login">
            <div class="head">
                <img src="<%=request.getContextPath()%>/static/img/law_icon.png" alt="" />
                <span>食药监管移动执法系统</span>
                <img src="<%=request.getContextPath()%>/static/img/logo2.png" alt="" class="fr"/>
            </div>
            <div class="login_wrap clearfix">
                <div class="left"></div>
                <div class="right">
                    <div class="loginbox">
                        <p class="wel">欢迎您登录</p>
                        <div class="login_item">
                            <label for="username" class="name_label"></label>
                            <input type="text" class="username" id="userName" name="loginName" placeholder="请输入账号"/>
                        </div>
                        <div class="login_item">
                            <label for="password" class="pwd_label"></label>
                            <input type="password" class="password" id="password" name="pwd" placeholder="请输入密码"/>
                        </div>
                        <p class="clearfix">
                            <a href="#">忘记密码？</a>
                            <a href="#">免费注册</a>
                        </p>
                        <button class="btn btn-login" onclick="login()">登录</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="login_foot">
            <ul>
                <li><a href="#"><i class="iconfont icon-yijianfankui"></i>意见反馈</a></li>
                <li><a href="#"><i class="iconfont icon-wenhao"></i>帮助中心</a></li>
                <li><a href="#">北京亚信数据有限公司  版权所有©2015</a></li>
            </ul>
        </div>
    </div>
    </form>
</div>
</body>
<!-- Placed at the end of the document so the pages load faster -->
<script src="<%=request.getContextPath()%>/static/js/jquery-1.11.1.js"></script>
<script src="<%=request.getContextPath()%>/static/js/bootstrap/js/bootstrap.min.js"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<%--<script src="$request.getContextPath()/static/js/ie10-viewport-bug-workaround.js"></script>--%>
<script>
    $(function(){
        $('.login_item').click(function(){
            $(this).addClass('login_focus').siblings().removeClass('login_focus');
        });
    });
    function keyDown(e,obj){
        var ev= window.event||e;
        if (ev.keyCode == 9) {
            $("#pwdpiv").addClass('login_focus');
            $("#namepiv").removeClass('login_focus');
        }else if(ev.keyCode == 13){
            login();
        }
    }
    function login(){
        var ret = true;
        if($("#userName").val().length<=0){
            $(".ErrorClew").text("用户名不能为空").css("visibility","visible");
            ret = false;
            return false;
        }
        if($("#password").val().length<=0){
            $(".ErrorClew").text("密码不能为空").css("visibility","visible");
            ret = false;
            return false;
        }
        if(ret){
            $.ajax({
                type: "POST",
                async: true,
                url: $("#userLogin").attr('action'),
                data: $("#userLogin").serialize(),
                success: function(msg) {
                    if (msg.success) {
                        window.location.href = '<%=request.getContextPath()%>/sys/main';
                    } else {
                        $(".ErrorClew").text(msg.message).css("visibility","visible");
                    }
                }
            });
        }
    }
</script>
</html>

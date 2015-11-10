<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%
String contextPath = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + contextPath + "/";
String errorMsg = (String)request.getAttribute("errorMessage");
String focus = (String)request.getAttribute("focus");
if (errorMsg == null) {
   errorMsg = "";
}
if (focus == null) {
   focus = "";
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>规则平台</title>
<script src="<%=contextPath%>/js/jquery-1.10.1.min.js" type="text/javascript"></script>
<script src="<%=contextPath%>/js/jquery.cookie.js" type="text/javascript"></script>
<style type="text/css">
body{
	margin: 0;
}

.head{
	background-color: white;
	height: 100px;
}
.content{
	background-image: url("../images/tkbg.jpg");
	height: 571px;
}
.content_bg{
	background-image: url("../images/tklogin.jpg");
	background-repeat:no-repeat;
	background-position:center;
	text-align:center;
	margin:0 auto;
	height: 571px;
	width: 1024px;
}
.formDiv{
	position: relative;
	left: 580px;
	top: 270px;
}
.formDiv .user_img{
	background-image: url("../images/user.png") ;
	height: 42px;
	width: 44px;

}
.formDiv .pwd_img{
	background-image: url("../images/password.png");
	height: 42px;
	width: 44px;
	margin-top: 5px;
}

.inputon{
	height: 38px;
	line-height: 38px;
	width: 200px;
	margin:0 0 0 48px;
	border: 1px solid #c9c9c9;
}
.inputerro{
	height: 38px;
	line-height: 38px;
	width: 200px;
	margin:0 0 0 48px;
	border:solid 1px #f00;
}
.login_btn1 {
	margin-bottom: 16px;
	margin-left:-740px;
	margin-top:10px;
	cursor: pointer;
	width:75px;
	height:25px;
}
.erroword{
	width:90px;
	color: #f00;
	height:22px;
	float:left;
	margin-left:50px;
	margin-top:10px;
}
.foot{
	height: 60px;
	margin-top:15px;
}
.copyright{
	margin:0 auto;
	vertical-align: middle;
	width: 200px;
}
.copyright span{
	margin:0 auto;
	font-size: 8pt;
}
</style>
</head> 
<body onload="body_onload()">
<div class="head"></div>
<div class="content">
<div class="content_bg">
	<div class="formDiv">
		<form name="dataform" action="<%=basePath%>LoginPage/login"  checkType="blur" target="_self" method="post">
			<input type="hidden" name="action" id="action" value="login">
			<div class="user_img"><input class="inputon" maxlength="30" name="userid" id="userId" type="text" onfocus="this.select();" onKeyPress="userIdOnkeyPress()" class="text1"/></div>
			<div class="pwd_img"><input class="inputon" type="password" maxlength="30" name="password" id="password" onfocus="this.select();"  onKeyPress="passwordOnkeyPress()" class="text2"/></div>
			<div class="erroword" id="erroword"></div>
			<img src="../images/login_btn.png" class="login_btn1"  onclick="login();"/>
		</form>
	</div>
</div>
</div>
<div class="foot">
	<div class="copyright"><span>Copyright©2015  方正国际 版权所有 </span></div>
</div>
</body>
<script language="JavaScript" >
try {
   if (window != top) { // 在顶层窗口打开登陆页面
      top.location.href = location.href;
   }
}
catch (err) {}

function body_onload() {
 
   if (focus == "userId" || focus == "") {
      document.getElementById("userId").focus();
   }
   else if (focus == "password") {
      document.getElementById("password").focus();
   }
}

function userIdOnkeyPress() {
   var keycode = event.keyCode;
   if (keycode == 13) {
      document.getElementById("password").focus();
   }
}

function passwordOnkeyPress() {
   var keycode = event.keyCode;
   if (keycode == 13) {
      login();
   }
}

function login(){
    var userIdValue = document.getElementById("userId").value;
    userIdValue = userIdValue.trim(); 
    if(null == userIdValue || "" == userIdValue ){
      document.getElementById("erroword").innerHTML="请输入用户名！";
      document.getElementById("userId").focus();
      document.getElementById("userId").className="inputerro";
      return;
    }
    $.cookie("lastUser",userIdValue,{expires:365});
    document.getElementById("userId").value = userIdValue;
    document.forms["dataform"].submit();
}

function reset(){
  document.getElementById("userId").value="";
  document.getElementById("password").value="";
}

String.prototype.trim = function() { 
   return this.replace(/(^\s*)|(\s*$)/g, ""); 
} 
errMsg();
function errMsg(){
   var errors = "${errors}";
   var focus = "${focus}";
   if (errors != "") {
      document.getElementById("erroword").innerHTML=errors;
   }
}

function setUser(obj){
  obj.value="";
}
function setPsd(obj){
 obj.value="";
}
$(document).ready(function(){
	$("#userId").val($.cookie("lastUser"));
});
</script>
</html>

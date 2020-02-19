<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>用户注册</title>
	<link rel="stylesheet" href="login/css/bootstrap.min.css">
	<link rel="stylesheet" href="login/css/animate.css">
	<link rel="stylesheet" href="login/css/style.css">
	<!-- Modernizr JS -->
	<script src="login/js/modernizr-2.6.2.min.js"></script>
	<script src="login/js/respond.min.js"></script>
	<script src="login/js/jquery.min.js"></script>
	<script src="login/js/bootstrap.min.js"></script>
	<script src="login/js/jquery.placeholder.min.js"></script>
	<script src="login/js/jquery.waypoints.min.js"></script>
	<script src="login/js/main.js"></script>

	<script>
		function save(){
			var formArray =  $("#formobj").serializeArray();
			var item = $(":radio:checked");
			var len = item.length;
			if(len==0){
				//alert("yes--选中的值为:"+$(":radio:checked").val());
				showErrorMsg("版本类型必填");
				return false;
			}

			if($("#cusName").val()==""){
				showErrorMsg("企业名称必填");
				return false;
			}
			if($("#realName").val()==""){
				showErrorMsg("联系人必填");
				return false;
			}
			if($("#telphone").val()==""){
				showErrorMsg("手机号必填");
				return false;
			}
			var myreg = /^(((13[0-9]{1})|(14[0-9]{1})|(17[0]{1})|(15[0-3]{1})|(15[5-9]{1})|(18[0-9]{1}))+\d{8})$/;
			if (!myreg.test($("#telphone").val())) {
				showErrorMsg("手机号格式不正确");
				return false;
			}
			if($("#verifCode").val()==""){
				showErrorMsg("验证码必填");
				return false;
			}
			if($("#userName").val()==""){
				showErrorMsg("用户账号必填");
				return false;
			}
			if($("#password").val()==""){
				showErrorMsg("密码必填");
				return false;
			}
			if($("#twopassword").val()==""){
				showErrorMsg("重复密码必填");
				return false;
			}
			if($("#smsverifCode").val()!=$("#verifCode").val()){
				showErrorMsg("验证码错误");
				return false;
			}
			if($("#password").val()!=$("#twopassword").val()){
				showErrorMsg("两次输入的密码不一致");
				return false;
			}

			$.ajax({
				url : "userController.do?saveUserForReg",
				type : 'post',
				cache : false,
				data: formArray,
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						window.location.href='${webRoot}/loginController.do?login';
					}else{
						showErrorMsg(d.msg);
						return false;
					}
				}
			});

		}

		/**
		 * 倒计时
		 * e 代表发送按钮对象
		 */
		var flag = 0;
		function resetTime(){
			if(flag >5){
				showErrorMsg("短信发送次数超出限制");
				return false;
			}
			if($("#telphone").val()==""){
				showErrorMsg("手机号必填");
				return false;
			}
			var myreg = /^(((13[0-9]{1})|(14[0-9]{1})|(17[0]{1})|(15[0-3]{1})|(15[5-9]{1})|(18[0-9]{1}))+\d{8})$/;
			if (!myreg.test($("#telphone").val())) {
				showErrorMsg("手机号格式不正确");
				return false;
			}
			var f = 0;
			$.ajax({
				url : "eSmsRecordController.do?sendSms&telphone="+$("#telphone").val(),
				type : 'post',
				cache : false,
				data: null,
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						$("#smsverifCode").val(d.obj);
					}else{
						showErrorMsg(d.msg);
						return false;
					}
				}
			});
			var second = 60;
			var timer = null;
			timer = setInterval(function(){
				second -= 1;
				if(second >0){
					$("#checkBtn").attr('disabled',true);
					$("#checkBtn").val(second + "秒后获取验证码");
				}else{
					clearInterval(timer);
					$("#checkBtn").attr('disabled',false);
					$("#checkBtn").val("发送短信验证码");
				}
			},1000);

			flag++;
		}

	</script>
</head>
<body class="style-2">
<div class="container" style="margin-top: -20px">
	<div class="row">
		<div class="col-md-4">
			<!-- Start Sign In Form -->
			<form id="formobj" name="formobj" action="#" class="fh5co-form animate-box" data-animate-effect="fadeIn">
				<h2>用户注册</h2>
				<div id="errMsgContiner" class="form-group2">
					<div class="alert alert-success" role="alert"> <div id="showErrMsg"></div></div>
				</div>
				<div class="form-group2">
					<input name="version" type="radio"  value="1.0">
					V1.0&nbsp;&nbsp;&nbsp;&nbsp;
					<input name="version" type="radio"  value="1.1">
					V1.1&nbsp;&nbsp;&nbsp;&nbsp;
				</div>
				<div class="form-group2">
					<label for="cusName" class="sr-only">企业名称</label>
					<input type="text" class="form-control" id="cusName" name="cusName" placeholder="企业名称" autocomplete="off">
				</div>
				<div class="form-group2">
					<label for="cusName" class="sr-only">企业地址</label>
					<input type="text" class="form-control" id="address" name="address" placeholder="企业地址" autocomplete="off">
				</div>
				<div class="form-group2">
					<label for="cusName" class="sr-only">联系人</label>
					<input type="text" class="form-control" id="realName" name="realName" placeholder="联系人" autocomplete="off">
				</div>
				<div class="form-group2" style="margin-bottom:-10px;">
					<label for="telphone" class="sr-only">手机号</label>
					<input type="text" class="form-control" id="telphone" name="telphone" placeholder="手机号" autocomplete="off">
					<input type="button" value="发送短信验证码" class="btn btn-primary" id="checkBtn" onclick="resetTime();" style="margin-top: -90px;margin-left: 160px;">
				</div>
				<div class="form-group2">
					<label for="userName" class="sr-only">验证码</label>
					<input type="hidden"  id="smsverifCode" name="smsverifCode" />
					<input type="text" class="form-control" id="verifCode" name="verifCode" placeholder="验证码" autocomplete="off">
				</div>
				<div class="form-group2">
					<label for="userName" class="sr-only">用户账号</label>
					<input type="text" class="form-control" id="userName" name="userName" placeholder="用户账号" autocomplete="off">
				</div>
				<div class="form-group2">
					<label for="password" class="sr-only">密码</label>
					<input type="password" class="form-control" id="password" name="password" name="password" placeholder="密码" autocomplete="off">
				</div>
				<div class="form-group2">
					<label for="twopassword" class="sr-only">重复密码</label>
					<input type="password" class="form-control" id="twopassword" name="twopassword" name="twopassword" placeholder="重复密码" autocomplete="off">
				</div>
				<div class="form-group2">
					<input type="button" value="注册" onclick="save()" class="btn btn-primary">&nbsp;&nbsp;&nbsp;
					<input type="button" onclick="home();" value="返回" class="btn btn-primary">
				</div>
			</form>
		</div>
	</div>
</div>
</body>
<script type="text/javascript">
	$(function(){
		optErrMsg();
	});
	$("#errMsgContiner").hide();
	function optErrMsg(){
		$("#showErrMsg").html('');
		$("#errMsgContiner").hide();
	}
	//登录提示消息显示
	function showErrorMsg(msg){
		$("#errMsgContiner").show();
		$("#showErrMsg").html(msg);
		window.setTimeout(optErrMsg,3000);
	}
	function home(){
		window.location.href = "loginController.do?login";
	}
</script>
<script src = "webpage/com/service/custom/ymkCustom.js"></script>

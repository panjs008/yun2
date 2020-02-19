<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>邮箱数据</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  $(document).ready(function(){
	 	layer.msg("${msg}");
  });
  </script>
 </head>
 <body>
 ${msg}
 </body>
  <script src = "webpage/com/emk/email/esendht/eSendHt.js"></script>

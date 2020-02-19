<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>样品通知单</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<script type="text/javascript">
		//编写自定义JS代码
		$(function(){
			$("#sampleState").val("${emkSamplePage.sampleState}");
		});
	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkYptzdController.do?doUpdateState" tiptype="1">
	<input id="id" name="id" type="hidden" value="${emkSamplePage.id }"/>
	<table style="width:100%;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">
					样品状态:
				</label>
			</td>
			<td class="value">
				<select id="sampleState" name="sampleState">
					<option value="0">资料</option>
					<option value="1">材料</option>
					<option value="2">织布</option>
					<option value="3">染色</option>
					<option value="4">缝制</option>
					<option value="5">烫标</option>
					<option value="6">整烫</option>
					<option value="7">检验</option>
					<option value="8">包装</option>
					<option value="9">完成</option>
					<%--<option value="10">寄出</option>--%>
				</select>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">客户状态</label>
			</td>
		</tr>
	</table>
</t:formvalid>

</body>

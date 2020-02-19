<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>人员信息表</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<%@include file="/context/selectDept.jsp"%>

	<script type="text/javascript">
		//编写自定义JS代码

	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="hmStaffController.do?doApproval" tiptype="1">
	<input id="id" name="id" type="hidden" value="${param.id }"/>
	<input id="lygState" name="lygState" type="hidden" value="${param.lygState }"/>

	<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right" width="20%">
				<label class="Validform_label">
					是否通过:
				</label>
			</td>
			<td class="value" colspan="3">
				<input name="isPass" type="radio" datatype="*" value="0">
				是
				<input name="isPass" type="radio" datatype="*" value="1">
				否
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">是否通过</label>
			</td>
		</tr>

	</table>
</t:formvalid>
</body>
<script src = "webpage/com/hm/rsgl/staff/hmStaff.js"></script>

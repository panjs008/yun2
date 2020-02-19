<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>入库单</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>

	<script type="text/javascript">
		//编写自定义JS代码

		$(document).ready(function(){
		});


	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkMInStorageController.do?doRecevie" tiptype="1">
	<input id="id" name="id" type="hidden" value="${param.id }"/>
	<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">
					收货状态:
				</label>
			</td>
			<td class="value">
				<select id="recevieState" name="recevieState" datatype="*"  >
					<option value='0'>未收货</option>
					<option value='1'>已收货</option>
				</select>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">收货状态</label>
			</td>

		</tr>

	</table>

</t:formvalid>
</body>
</html>

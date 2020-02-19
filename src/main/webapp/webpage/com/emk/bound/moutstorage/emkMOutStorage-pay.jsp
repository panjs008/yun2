<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>销售开单收款</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>

	<script type="text/javascript">
		//编写自定义JS代码

		$(document).ready(function(){
			$("#payState").val('${emkMOutStoragePage.payState }');
		});


	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkMOutStorageController.do?doPay" tiptype="1">
	<input id="id" name="id" type="hidden" value="${param.id }"/>
	<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">
					收款状态:
				</label>
			</td>
			<td class="value">
				<select id="payState" name="payState" datatype="*"  >
					<option value='0'>未收款</option>
					<option value='1'>已收款</option>
				</select>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">收款状态</label>
			</td>

		</tr>

	</table>

</t:formvalid>
</body>
</html>

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
			$("#payState").val('${emkProOrderPage.payState }');
		});


	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkProOrderController.do?doPay" tiptype="1">
	<input id="id" name="id" type="hidden" value="${param.id }"/>
	<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">
					付款状态:
				</label>
			</td>
			<td class="value">
				<select id="payState" name="payState" datatype="*"  >
					<option value='0'>未付款</option>
					<option value='1'>已付款</option>
				</select>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">付款状态</label>
			</td>

		</tr>

	</table>

</t:formvalid>
</body>
</html>

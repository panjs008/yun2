<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>付款信息表</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<%@include file="/context/header2.jsp"%>
	<script src="${webRoot}/context/gys.js"></script>

	<script type="text/javascript">
		//编写自定义JS代码
		$(document).ready(function(){
			$("#paydetail").load("emkPayController.do?payList&proOrderId=${emkPayPage.orderId }&orderNo=${emkPayPage.orderNo }");
			$("#matraildetail").load("emkPayController.do?formaterialMxList&type=1&orderNo=${emkPayPage.orderNo }");
			$("#detail").load("emkPayController.do?orderMxList&type=1&orderNo=${emkPayPage.orderNo }");
			$("#otherdetail").load("emkPayController.do?otherMxList&type=1&orderNo=${emkPayPage.orderNo }");

		});
		function previewArea(){
			window.print();
		}
	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkPayController.do?doUpdate" tiptype="1">
	<input id="id" name="id" type="hidden" value="${emkPayPage.id }"/>
	<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right" width="20%">
				<label class="Validform_label">
					订单号:
				</label>
			</td>
			<td class="value" >
				<input type="text" id="orderNoV" class="inputxt" style="width: 150px" value="${emkPayPage.orderNo }"/>

				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">订单号</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					工厂:
				</label>
			</td>
			<td class="value">
				<select class="form-control select2" id="gysId"  datatype="*"  >
					<option value=''>请选择</option>
				</select>
				<input id="gysCode" name="gysCode" type="hidden" value="${emkPayPage.gysCode }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<input id="gys" name="gys" type="hidden"  value="${emkPayPage.gys }"  style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">工厂</label>
			</td>
		</tr>

	</table>
	<div id="paydetail" style="overflow-x:hidden;overflow-y: hidden"></div>
	<div id="detail" style="overflow-x:hidden;overflow-y: hidden"></div>
	<div id="matraildetail" style="overflow-x:hidden;overflow-y: hidden"></div>
	<div id="otherdetail" style="overflow-x:hidden;overflow-y: hidden"></div>

</t:formvalid>
</body>

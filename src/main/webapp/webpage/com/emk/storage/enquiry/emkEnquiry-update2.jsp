<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>报单</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<%@include file="/context/header2.jsp"%>
	<%--<script src="${webRoot}/context/gys.js"></script>--%>

	<script type="text/javascript">
		//编写自定义JS代码
		$(document).ready(function(){
			$("#detail").load("emkEnquiryController.do?orderMxList&state=${emkEnquiryPage.state}&proOrderId=${emkEnquiryPage.id }");
		});
	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkEnquiryController.do?doUpdate" tiptype="1">
	<input id="id" name="id" type="hidden" value="${emkEnquiryPage.id }"/>
	<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">
					订单号:
				</label>
			</td>
			<td class="value" >
				<input id="enquiryNo" name="enquiryNo" value="${emkEnquiryPage.enquiryNo }"  readonly type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">订单号</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					下单日期:
				</label>
			</td>
			<td class="value">
				<input id="kdDate" name="kdDate" readonly value="${emkEnquiryPage.kdDate }" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  type="text" style="width: 150px" class="Wdate"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">下单日期</label>
			</td>
		</tr>

		<tr>
			<td align="right">
				<label class="Validform_label">
					工厂:
				</label>
			</td>
			<td class="value">
				<%--<select class="form-control select2" id="gysId"  datatype="*"  style="width: 158px" >
					<option value=''>请选择</option>
				</select>--%>
				<input id="gysCode" name="gysCode" type="hidden" value="${emkEnquiryPage.gysCode }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<input id="gys" name="gys" type="text"  value="${emkEnquiryPage.gys }"  style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">工厂</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					最晚交货日期:
				</label>
			</td>
			<td class="value">
				<input id="ysDate" name="ysDate" readonly datatype="*" value="${emkEnquiryPage.ysDate }" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'kdDate\');}',onpicked:setEndTime})"  type="text" style="width: 150px" class="Wdate"  />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">最晚交货日期</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					距交期剩余天数:
				</label>
			</td>
			<td class="value">
				<input id="levelDays" name="levelDays" readonly type="text" value="${emkEnquiryPage.levelDays }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">距交期剩余天数</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					款号:
				</label>
			</td>
			<td class="value">
				<input id="sampleNo" name="sampleNo" type="text" value="${emkEnquiryPage.sampleNo }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">款号</label>
			</td>
		</tr>
	</table>

	<div id="detail" style="overflow-x:hidden;overflow-y: hidden"></div>
</t:formvalid>
</body>

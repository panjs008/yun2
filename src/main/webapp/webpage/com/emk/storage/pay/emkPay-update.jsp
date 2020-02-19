<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>付款信息表</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<script src="${webRoot}/context/gys.js"></script>
	<%@include file="/context/header2.jsp"%>
	<script type="text/javascript" src="js/ajaxfileupload.js"></script>

	<script type="text/javascript">
		//编写自定义JS代码
		$(document).ready(function(){
			$("#paydetail").load("emkPayController.do?payList&proOrderId=${emkPayPage.orderId }&orderNo=${emkPayPage.orderNo }");
			$("#matraildetail").load("emkPayController.do?formaterialMxList&type=1&orderNo=${emkPayPage.orderNo }");
			$("#detail").load("emkPayController.do?orderMxList&type=1&orderNo=${emkPayPage.orderNo }");
			$("#otherdetail").load("emkPayController.do?otherMxList&type=1&orderNo=${emkPayPage.orderNo }");

		});



	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkPayController.do?doUpdate" tiptype="1">
	<input id="id" name="id" type="hidden" value="${emkPayPage.id }"/>
	<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
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
		<tr>
			<td align="right" width="20%">
				<label class="Validform_label">
					订单号:
				</label>
			</td>
			<td class="value" >
				<input id="orderNo" name="orderNo" type="text"  value="${emkPayPage.orderNo }"  style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">订单号</label>
			</td>
		</tr>


	</table>
	<div id="paydetail" style="overflow-x:hidden;overflow-y: hidden"></div>
	<div id="detail" style="overflow-x:hidden;overflow-y: hidden"></div>
	<div id="matraildetail" style="overflow-x:hidden;overflow-y: hidden"></div>
	<div id="otherdetail" style="overflow-x:hidden;overflow-y: hidden"></div>

	<table style="width: 100%;margin-top: 5px;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right" width="20%">
				<label class="Validform_label">
					日期:
				</label>
			</td>
			<td class="value">
				<input id="payDate" name="payDate" type="text" style="width: 150px" datatype="*" readonly value="${emkPayPage.payDate }" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"  />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">日期</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					金额:
				</label>
			</td>
			<td class="value">
				<input id="money" name="money" type="text" datatype="*" style="width: 150px" value="${emkPayPage.money }" class="inputxt"  datatype="/^(-?\d+)(\.\d+)?$/"   value='${emkPayPage.money}'/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">金额</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					方式:
				</label>
			</td>
			<td class="value">
				<t:dictSelect id="type" field="type" typeGroupCode="paytype" datatype="*" defaultVal="${emkPayPage.type}" hasLabel="false" title="方式"></t:dictSelect>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">方式</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					备注:
				</label>
			</td>
			<td class="value">
				<input id="remark" name="remark" type="text" style="width: 150px" value="${emkPayPage.remark }" class="inputxt"  ignore="ignore"  value='${emkPayPage.remark}'/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">备注</label>
			</td>
		</tr>

		<tr>
			<td align="right">
				<label class="Validform_label">
					图片凭证:
				</label>
			</td>
			<td class="value">
				<input type="hidden" id="saveFileName" name="saveFileName" value="${emkPayPage.saveFileName }">
				<input type="hidden" id="fileNameUrl" name="fileNameUrl" value="${emkPayPage.fileNameUrl }">
				<input name="files" id="outuploadFile" onchange="saveFile2('uploadControl.do?upload&fileDir=outorder','outuploadFile','fileName','formobj','saveFileName','fileNameUrl','outorder');"  style="width:300px;display: none" type="file" accept=".png,.gif,.jpg,.jpeg"  />
				<input id="fileName" name="fileName" value="${emkPayPage.fileName }" type="text" style="width: 150px;" readonly onclick="uploadClick('outuploadFile')">
				<span id="outuploadFileId">
					<c:if test="${emkPayPage.fileNameUrl ne '' && emkPayPage.fileNameUrl ne null}">
						<input class="btn" type="button" value="查看" onclick="findDetail('${webRoot}/imp/outorder/${emkPayPage.saveFileName }')" style="background:#18a689 none repeat scroll 0 0;height:28px;width:50px !important;border-radius:5px;color: #fff;" >
					</c:if>
				</span>
			</td>
		</tr>
	</table>
</t:formvalid>
</body>

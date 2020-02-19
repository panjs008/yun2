<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>付款信息表</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<%@include file="/context/header2.jsp"%>
	<script src="${webRoot}/context/gys.js"></script>
	<script src="${webRoot}/context/orderSelect.js"></script>
	<script type="text/javascript" src="js/ajaxfileupload.js"></script>

	<script type="text/javascript">
		//编写自定义JS代码
		$(document).ready(function(){
			//$("#detailId").load("emkEnquiryController.do?orderMxList");
		});



	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkPayController.do?doAdd" tiptype="1">
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
				<select class="form-control select2" id="orderNo" onchange="finishOrder();"  name="orderNo" datatype="*"  >
					<option value=''>请选择</option>
				</select>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">订单号</label>
			</td>
			</tr>


	</table>
	<t:tabs id="enquiryDetail" iframe="false"  tabPosition="top" fit="false">
		<t:tab href="emkPayController.do?orderMxList" icon="icon-search" title="已发货明细（扣除退货）" id="detail"></t:tab>
	</t:tabs>
	<t:tabs id="matrialDetail" iframe="false"  tabPosition="top" fit="false">
		<t:tab href="emkPayController.do?formaterialMxList" icon="icon-search" title="叫布费用明细合计" id="mdetail"></t:tab>
	</t:tabs>
	<t:tabs id="matrialDetail" iframe="false"  tabPosition="top" fit="false">
		<t:tab href="emkPayController.do?otherMxList" icon="icon-search" title="其他物料明细合计" id="odetail"></t:tab>
	</t:tabs>
	<table style="width: 100%;margin-top: 5px;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right" width="20%">
				<label class="Validform_label">
					日期:
				</label>
			</td>
			<td class="value">
				<input id="payDate" name="payDate" type="text" style="width: 150px" datatype="*" readonly value="${kdDate}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"  />
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
				<input id="money" name="money" type="text" datatype="*" style="width: 150px" class="inputxt"  datatype="/^(-?\d+)(\.\d+)?$/"   value='${emkPayPage.money}'/>
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
				<input id="remark" name="remark" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkPayPage.remark}'/>
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
				<input type="hidden" id="fileNameUrl" name="fileNameUrl">
				<input name="files" id="outuploadFile" onchange="saveFile2('uploadControl.do?upload&fileDir=ourorder','outuploadFile','fileName','formobj','saveFileName','fileNameUrl','ourorder');"  style="width:300px;display: none" type="file" accept=".png,.gif,.jpg,.jpeg"  />
				<input id="fileName" name="fileName" type="text" style="width: 150px;" readonly onclick="uploadClick('outuploadFile')">
				<span id="outuploadFileId"></span>
			</td>
		</tr>
	</table>
</t:formvalid>
</body>

<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>叫布表</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<%@include file="/context/header2.jsp"%>
	<script src="${webRoot}/context/orderSelect.js"></script>
	<script type="text/javascript" src="js/ajaxfileupload.js"></script>

	<script type="text/javascript">
		//编写自定义JS代码
	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkFormaterialController.do?doUpdate" tiptype="1">
	<input id="id" name="id" type="hidden" value="${emkFormaterialPage.id }"/>
	<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">
					布行名称:
				</label>
			</td>
			<td class="value">
				<input id="bhmc" name="bhmc" type="text" datatype="*" value="${emkFormaterialPage.bhmc }" style="width: 150px" class="inputxt" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">布行名称</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					开单日期:
				</label>
			</td>
			<td class="value">
				<input id="kdDate" name="kdDate" readonly value="${emkFormaterialPage.kdDate }" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  type="text" style="width: 150px" class="Wdate"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">开单日期</label>
			</td>
		</tr>

		<tr>
			<td align="right">
				<label class="Validform_label">
					单号:
				</label>
			</td>
			<td class="value" colspan="3">
				<input type="hidden" id="orderNoV" value="${emkFormaterialPage.orderNo }"/>
				<select class="form-control select2" id="orderNo" value="${emkFormaterialPage.orderNo }" name="orderNo" datatype="*"  >
					<option value=''>请选择</option>
				</select>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">单号</label>
			</td>

		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					图片:
				</label>
			</td>
			<td class="value" colspan="3">
				<input type="hidden" id="fileName" name="fileName" value="${emkFormaterialPage.fileName }">
				<input type="hidden" id="saveFileName" name="saveFileName" value="${emkFormaterialPage.saveFileName }">
				<input type="hidden" id="fileNameUrl" name="fileNameUrl" value="${emkFormaterialPage.fileNameUrl }">
				<input name="files" id="jbuploadFile" onchange="saveFile('uploadControl.do?upload&fileDir=material','jbuploadFile','newFile','formobj','fileName','fileNameUrl');" style="width:300px;display: none" type="file" accept=".png,.gif,.jpg,.jpeg"  />
				<input id="newFile" type="text" style="width: 150px;" value="${emkFormaterialPage.fileName }" readonly onclick="uploadClick('jbuploadFile')">
				<span id="jbuploadFileId">
					<c:if test="${emkFormaterialPage.fileNameUrl ne '' && emkFormaterialPage.fileNameUrl ne null}">
						<input class="btn" type="button" value="查看" onclick="findDetail('${webRoot}/imp/material/${emkFormaterialPage.saveFileName }')" style="background:#18a689 none repeat scroll 0 0;height:28px;width:50px !important;border-radius:5px;color: #fff;" >
					</c:if>
				</span>

			</td>
		</tr>



	</table>
	<t:tabs id="detail" iframe="false"  tabPosition="top" fit="false">
		<t:tab href="emkFormaterialController.do?orderMxList&state=0&formaterialId=${emkFormaterialPage.id }" icon="icon-search" title="明细" id="detail"></t:tab>
	</t:tabs>
</t:formvalid>
</body>
<script src = "webpage/com/emk/storage/formaterial/emkFormaterial.js"></script>

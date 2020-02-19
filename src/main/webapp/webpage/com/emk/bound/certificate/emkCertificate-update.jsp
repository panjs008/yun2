<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>证件表</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<%@include file="/context/header2.jsp"%>
	<script type="text/javascript" src="js/ajaxfileupload.js"></script>

	<script type="text/javascript">
		//编写自定义JS代码
	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkCertificateController.do?doUpdate" tiptype="1">
	<input id="id" name="id" type="hidden" value="${emkCertificatePage.id }"/>
	<input id="gysId" name="gysId" type="hidden" value="${emkCertificatePage.gysId}"/>
	<input id="webRoot" type="hidden" value="${webRoot}"/>

	<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">
					证件名称:
				</label>
			</td>
			<td class="value">
				<input id="certName" name="certName" type="text" value="${emkCertificatePage.certName }" style="width: 150px" class="inputxt"  datatype="*"/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">证件名称</label>
			</td>
			<td class="value" rowspan="6" align="center">
				<img id="uploadimg" src="${emkCertificatePage.certImageUrl eq null || emkCertificatePage.certImageUrl eq ''? 'images/certlogo.png':emkCertificatePage.certImageUrl}" style="border-radius:12px;" width="360" height="240">
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					证件号码:
				</label>
			</td>
			<td class="value">
				<input id="certNo" name="certNo"  type="text" value="${emkCertificatePage.certNo }" style="width: 150px" class="inputxt"  datatype="*"/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">证件号码</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					发证机关:
				</label>
			</td>
			<td class="value">
				<input id="certSignDept" name="certSignDept" type="text" value="${emkCertificatePage.certSignDept }"  style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">发证机关</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					发证日期:
				</label>
			</td>
			<td class="value">
				<input id="certSignDate" name="certSignDate" readonly  value="${emkCertificatePage.certSignDate }" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  type="text" style="width: 150px" class="Wdate"  datatype="*"/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">发证日期</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					到期日期:
				</label>
			</td>
			<td class="value">
				<input id="certLimitDate" name="certLimitDate" type="text"  value="${emkCertificatePage.certLimitDate }" readonly onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'certSignDate\');}'})" style="width: 150px" class="Wdate"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">到期日期</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					备注:
				</label>
			</td>
			<td class="value">
				<input id="remark" name="remark" type="text" style="width: 150px" value="${emkCertificatePage.remark }"  class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">备注</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					证件图片:
				</label>
			</td>
			<td class="value" colspan="3">
				<input type="hidden" id="certImage" name="certImage" value="${emkCertificatePage.certImage }" datatype="*">
				<input type="hidden" id="saveFileName" name="saveFileName"  value="${emkCertificatePage.certImage }" >
				<input type="hidden" id="certImageUrl" name="certImageUrl" datatype="*"  value="${emkCertificatePage.certImageUrl }" >
				<input name="files" id="certuploadFile" onchange="saveFile('uploadControl.do?upload&fileDir=cert','certuploadFile','newFile','formobj','certImage','certImageUrl');" style="width:300px;display: none" type="file" accept=".png,.gif,.jpg,.jpeg"  />
				<input id="newFile" type="text" style="width: 150px;" readonly onclick="uploadClick('certuploadFile')">
				<span id="certuploadFileId"></span>
				<label class="Validform_label" style="display: none;">证件图片</label>
					<%--[<a href="javascript:findDetail('${emkEnquiryPage.certImageUrl }')">${emkEnquiryPage.customSample }</a>]--%>
			</td>
		</tr>



	</table>
</t:formvalid>
</body>
<script src = "webpage/com/emk/bound/certificate/emkCertificate.js"></script>

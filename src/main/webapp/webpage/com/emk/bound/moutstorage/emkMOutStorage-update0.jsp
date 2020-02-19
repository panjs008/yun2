<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>采购出库表</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<%@include file="/context/header2.jsp"%>
	<script src="${webRoot}/context/storage.js"></script>

	<script type="text/javascript">
		//编写自定义JS代码
		$(document).ready(function(){
			$("#type").val("${emkMOutStoragePage.type }");
			$("#detailId").load("emkMOutStorageController.do?orderMxList&type=0&inStorageId=${emkMOutStoragePage.id}");
		});

	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkMOutStorageController.do?doUpdate" tiptype="1">
	<input id="emkMOutStorageId" name="emkMOutStorageId" type="hidden" value="${emkMOutStoragePage.id }"/>
	<input id="storageName" name="storageName" type="hidden" value="${emkMOutStoragePage.storageName }"/>
	<input id="appler" name="appler" type="hidden" value="${emkMOutStoragePage.appler }"/>
	<input id="applerId" name="applerId" type="hidden" value="${emkMOutStoragePage.applerId }"/>

	<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right" >
				<label class="Validform_label">
					申请人:
				</label>
			</td>
			<td class="value">
				<input id="realName" name="realName" value="${emkMOutStoragePage.appler}"  type="text" readonly style="width: 30%" class="inputxt" >
				<input name="userName"   type="hidden" value="${emkMOutStoragePage.applerId}"   id="userName" type="text"  />
				<t:choose  hiddenName="userName"  hiddenid="userName" url="userController.do?userSelect0" name="userList0" width="700px" height="500px"
						   icon="icon-search" title="选择处理人" textname="realName" isclear="true" isInit="true"></t:choose>
			</td>
			<td align="right">
				<label class="Validform_label">
					单号:
				</label>
			</td>
			<td class="value">
				<input id="ckNo" name="ckNo" readonly value="${emkMOutStoragePage.ckNo}"  style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">单号</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					出库类型:
				</label>
			</td>
			<td class="value">
				<select id="type" name="type" datatype="*" style="width: 150px">
					<option value=''>请选择</option>
					<option value='3'>正常出库</option>
					<option value='1'>临时出库</option>
				</select>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">出库类型</label>
			</td>
		</tr>

		<tr>
			<td align="right">
				<label class="Validform_label">
					仓库名称:
				</label>
			</td>
			<td class="value">
				<select class="form-control select2" id="storageNameId" datatype="*">
					<option value=''>请选择</option>
				</select>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">仓库名称</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					出库日期:
				</label>
			</td>
			<td class="value" >
				<input id="outDate" name="outDate" readonly value="${emkMOutStoragePage.outDate }" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  type="text" style="width: 150px" class="Wdate"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">出库日期</label>
			</td>

			<td align="right">
				<label class="Validform_label">
					发货日期:
				</label>
			</td>
			<td class="value" >
				<input id="fhDate" name="fhDate" readonly  value="${emkMOutStoragePage.fhDate }" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'outDate\');}'})"  type="text" style="width: 150px" class="Wdate"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">发货日期</label>
			</td>
		</tr>
		<tr>
			<td align="right" >
				<label class="Validform_label">
					客户名称:
				</label>
			</td>
			<td class="value"  colspan="5">
				<input id="cusNum" name="cusNum" value="${emkMOutStoragePage.cusNum }"  type="hidden" />
				<input id="cusName" name="cusName" readonly type="text" value="${emkMOutStoragePage.cusName }" style="width:150px" class="inputxt"  datatype="*"/>
				<t:choose  hiddenName="cusNum"  hiddenid="cusNum" url="ymkCustomController.do?select" name="ymkCustomList" width="700px" height="500px"
						   icon="icon-search" title="选择客户" textname="cusName,bankName,bankAccount,zlxr,workphone,address" isclear="true" isInit="true"></t:choose>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">客户名称</label>
			</td>
		</tr>
	</table>
	<div id="detailId" style="overflow-x:hidden;overflow-y: hidden"></div>


</t:formvalid>
</body>
</html>

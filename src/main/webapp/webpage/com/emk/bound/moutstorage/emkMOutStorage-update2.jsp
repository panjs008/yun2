<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>采购出库表</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<%@include file="/context/header2.jsp"%>
	<script type="text/javascript">
		//编写自定义JS代码
		$(document).ready(function(){
			$("#detailId").load("emkMOutStorageController.do?orderMxList2&type=0&inStorageId=${emkMOutStoragePage.id}&node=${param.node}");
		});


	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkMOutStorageController.do?doUpdate" tiptype="1">
	<input id="emkMOutStorageId" name="emkMOutStorageId" type="hidden" value="${emkMOutStoragePage.id }"/>
	<input id="type" name="type" type="hidden" value="${param.type }"/>
	<input id="appler" name="appler" type="hidden" value="${emkMOutStoragePage.appler }"/>
	<input id="applerId" name="applerId" type="hidden" value="${param.applerId }"/>

	<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">

		<tr>
			<td align="right" >
				<label class="Validform_label">
					申请人:
				</label>
			</td>
			<td class="value" colspan="3">

				<input id="realName" name="realName" value="${emkMOutStoragePage.appler}"  type="text" readonly style="width: 35%" class="inputxt" >
				<input name="userName"   type="hidden" value="${emkMOutStoragePage.applerId}"   id="userName" type="text"  />

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
		</tr>

		<tr>
			<td align="right" >
				<label class="Validform_label">
					客户名称:
				</label>
			</td>
			<td class="value"  colspan="3">
				<input id="cusNum" name="cusNum" value="${emkMOutStoragePage.cusNum }"  type="hidden" />
				<input id="cusName" name="cusName" readonly type="text" value="${emkMOutStoragePage.cusName }" style="width:  35%" class="inputxt"  datatype="*"/>

				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">客户名称</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					联系人:
				</label>
			</td>
			<td class="value">
				<input id="zlxr" name="zlxr" type="text" style="width: 150px" value="${emkMOutStoragePage.zlxr }" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">联系人</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					企业注册地址:
				</label>
			</td>
			<td class="value" colspan="5">
				<input id="address" name="address" type="hidden"  class="inputxt" ignore="ignore" />
				<input id="qyzcAddress" name="qyzcAddress" type="text" style="width: 60%" value="${emkMOutStoragePage.qyzcAddress }" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">企业注册地址</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					收票收件地址:
				</label>
			</td>
			<td class="value" colspan="5">
				<input id="spAddress" name="spAddress" type="text" style="width: 60%" value="${emkMOutStoragePage.spAddress }" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">收票收件地址</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					开户行:
				</label>
			</td>
			<td class="value">
				<input id="bankName" name="bankName" type="text" style="width: 150px" value="${emkMOutStoragePage.bankName }" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">开户行</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					开户账号:
				</label>
			</td>
			<td class="value" >
				<input id="bankAccount" name="bankAccount" type="text" value="${emkMOutStoragePage.bankName }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">开户账号</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					联系人电话:
				</label>
			</td>
			<td class="value">
				<input id="workphone" name="workphone" type="text" style="width: 150px" value="${emkMOutStoragePage.workphone }" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">联系人电话</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					税号:
				</label>
			</td>
			<td class="value" >
				<input id="suiNum" name="suiNum" type="text" value="${emkMOutStoragePage.suiNum }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">税号</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					销售日期:
				</label>
			</td>
			<td class="value" >
				<input id="outDate" name="outDate" readonly value="${emkMOutStoragePage.outDate }" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  type="text" style="width: 150px" class="Wdate"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">销售日期</label>
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
	</table>
	<div id="detailId" style="overflow-x:hidden;overflow-y: hidden"></div>


</t:formvalid>
</body>
</html>

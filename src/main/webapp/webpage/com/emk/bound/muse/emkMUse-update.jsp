<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>使用登记表</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<%@include file="/context/header2.jsp"%>
	<script type="text/javascript">
		//编写自定义JS代码
		$(document).ready(function(){
			$("#detailId").load("emkMUseController.do?orderMxList&useId=${emkMUsePage.id}");
		});
		function returnToSelect(){
			$.ajax({
				url : "emkMUseController.do?setCusSession&cusNum="+$("#cusNum").val(),
				type : 'post',
				cache : false,
				success : function(data) {
				}
			});
		}
	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkMUseController.do?doUpdate" tiptype="1">
	<input id="emkMUseId" name="emkMUseId" type="hidden" value="${emkMUsePage.id }"/>
	<input id="appler" name="appler" type="hidden" value="${emkMUsePage.appler }"/>
	<input id="applerId" name="applerId" type="hidden" value="${emkMUsePage.applerId }"/>

	<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right" >
				<label class="Validform_label">
					登记人:
				</label>
			</td>
			<td class="value" colspan="3" >
				<input id="realName" name="realName" value="${emkMUsePage.appler}"  type="text" readonly style="width: 150px" class="inputxt" >
				<input name="userName"   type="hidden" value="${emkMUsePage.applerId}"   id="userName" type="text"  />
				<t:choose  hiddenName="userName"  hiddenid="userName" url="userController.do?userSelect0" name="userList0" width="700px" height="500px"
						   icon="icon-search" title="选择登记人" textname="realName" isclear="true" isInit="true"></t:choose>
			</td>
			<td align="right">
				<label class="Validform_label">
					登记单号:
				</label>
			</td>
			<td class="value" colspan="3">
				<input id="ckNo" name="ckNo" readonly style="width: 150px" class="inputxt"  ignore="ignore" />
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
				<input id="cusNum" name="cusNum" value="${emkMUsePage.cusNum }"  type="hidden" />
				<input id="cusName" name="cusName" readonly type="text" value="${emkMUsePage.cusName }" style="width: 60%" class="inputxt"  datatype="*"/>
				<t:choose  hiddenName="cusNum"  hiddenid="cusNum" url="ymkCustomController.do?select" name="ymkCustomList" width="700px" height="500px"
						   icon="icon-search" title="选择客户" textname="cusName,bankName,bankAccount,zlxr,workphone,address,suiNum" isclear="true" isInit="true"></t:choose>
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
			<td align="right">
				<label class="Validform_label">
					联系人电话:
				</label>
			</td>
			<td class="value">
				<input id="workphone" name="workphone" type="text" style="width: 150px" value="${emkMUsePage.workphone }" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">联系人电话</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					企业注册地址:
				</label>
			</td>
			<td class="value" colspan="3">
				<input id="address" name="address" type="hidden"  class="inputxt"  ignore="ignore" />
				<input id="qyzcAddress" name="qyzcAddress" type="text" style="width: 60%" value="${emkMUsePage.qyzcAddress }" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">企业注册地址</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					收票收件地址:
				</label>
			</td>
			<td class="value" colspan="3">
				<input id="spAddress" name="spAddress" type="text" style="width: 60%" value="${emkMUsePage.spAddress }" class="inputxt"  ignore="ignore" />
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
				<input id="bankName" name="bankName" type="text" style="width: 150px" value="${emkMUsePage.bankName }" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">开户行</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					开户账号:
				</label>
			</td>
			<td class="value" >
				<input id="bankAccount" name="bankAccount" type="text" value="${emkMUsePage.bankAccount }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">开户账号</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					税号:
				</label>
			</td>
			<td class="value" colspan="3">
				<input id="suiNum" name="suiNum" type="text" value="${emkMUsePage.suiNum }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">税号</label>
			</td>

		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					医院名称:
				</label>
			</td>
			<td class="value">
				<t:dictSelect id="hospitalCode" field="hospitalCode" typeGroupCode="yymc" datatype="*" defaultVal="${emkMUsePage.hospitalCode}" hasLabel="false" title="工艺类型"></t:dictSelect>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">医院名称</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					病人姓名:
				</label>
			</td>
			<td class="value">
				<input id="patient" name="patient" type="text" value="${emkMUsePage.patient }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">病人姓名</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					手术时间:
				</label>
			</td>
			<td class="value">
				<input id="operationDate" name="operationDate" value="${emkMUsePage.operationDate }" readonly onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  type="text" style="width: 150px" class="Wdate"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">手术时间</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					手术医生:
				</label>
			</td>
			<td class="value">
				<input id="operationDc" name="operationDc" value="${emkMUsePage.operationDc }" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">手术医生</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					发票号码:
				</label>
			</td>
			<td class="value">
				<input id="kpNum" name="kpNum" type="text" value="${emkMUsePage.kpNum }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">发票号码</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					开票单位:
				</label>
			</td>
			<td class="value">
				<input id="kpUnit" name="kpUnit" type="text" value="${emkMUsePage.kpUnit }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">开票单位</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					开票金额:
				</label>
			</td>
			<td class="value">
				<input id="kpMoney" name="kpMoney" type="text" value="${emkMUsePage.kpMoney }" datatype="d" style="width: 150px" class="inputxt" ignore="ignore"/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">开票金额</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					厂家回款时间:
				</label>
			</td>
			<td class="value">
				<input id="cjhkMoney" name="cjhkMoney" type="text" value="${emkMUsePage.cjhkMoney }" readonly style="width: 150px" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  class="Wdate"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">厂家回款时间</label>
			</td>
		</tr>
	</table>
	<div id="detailId" style="overflow-x:hidden;overflow-y: hidden"></div>

</t:formvalid>
</body>
<script src = "webpage/com/emk/bound/muse/emkMUse.js"></script>

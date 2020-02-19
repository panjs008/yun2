<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>工单处理</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>


</head>
<body>
<%--<div id="timeDiv" title="流程时间轴" style="width:100%;height:360px;overflow:hidden" >
	<iframe scrolling="no" id="processFrm" frameborder="0"  src="emkMaterialContractController.do?goTime&id=${param.id}" width="100%" height="100%"></iframe>
	</div>--%>
<div id="orderDiv" title="流程图" style="width:100%;height:280px;overflow:hidden" >
	<iframe scrolling="no" id="processFrm" frameborder="0" style="margin-top: -6px;margin-left:60px;"  src="flowController.do?showProcess&id=${param.id}&tableName=emk_material_contract" width="100%" height="100%"></iframe>
</div>
<div id="timeDiv" title="流程时间轴" style="width:100%;height:300px;overflow:hidden" >
	<iframe scrolling="no" id="timeFrm" frameborder="0"  src="emkMaterialContractController.do?goTime&id=${param.id}" width="100%" height="100%"></iframe>
</div>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkMaterialContractController.do?doSubmit"  tiptype="1" >
	<input id="id" name="id" type="hidden" value="${emkMaterialContract.id }"/>
	<table id="adviceTb" style="width: 100%;margin-top:2px;margin-bottom: 4px" cellpadding="0" cellspacing="1" class="formtable">
		<c:if test="${(emkMaterialContract.state eq 1 || emkMaterialContract.state eq 21) && ROLE.rolecode eq 'ywy'
		 				|| (emkMaterialContract.state eq 35 || emkMaterialContract.state eq 4) && ROLE.rolecode eq 'ywjl'
		 				|| (emkMaterialContract.state eq 3 || emkMaterialContract.state eq 23) && ROLE.rolecode eq 'jsy'
		 				|| (emkMaterialContract.state eq 22 || emkMaterialContract.state eq 36) && ROLE.rolecode eq 'cgy'
		 				|| (emkMaterialContract.state eq 24 || emkMaterialContract.state eq 16) && ROLE.rolecode eq 'cgjl'
		 				|| (emkMaterialContract.state eq 41 || emkMaterialContract.state eq 27 || emkMaterialContract.state eq 48 ) && ROLE.rolecode eq 'cw'
		 				|| emkMaterialContract.state eq 25 && ROLE.rolecode eq 'cwjl'
		 				|| (emkMaterialContract.state eq 26 || emkMaterialContract.state eq 47) && ROLE.rolecode eq 'zjl' && param.node eq 'zjlpfTask'}">
			<tr>
				<td align="right">
					<label class="Validform_label">
						是否同意:
					</label>
				</td>
				<td class="value" colspan="3">
					<input name="isPass" type="radio" datatype="*" value="0" onclick="setReruire(0);">
					是
					<input name="isPass" type="radio" datatype="*" value="1" onclick="setReruire(1);">
					否
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">是否同意</label>
				</td>
			</tr>
			<c:if test="${(emkMaterialContract.state eq 24 || emkMaterialContract.state eq 16) && ROLE.rolecode eq 'cgjl'}">
				<tr id="chooseUser">
					<td align="right" width="150px" valign="middle">
						<label class="Validform_label">
							指定财务操作人:
						</label>
					</td>

					<td class="value" colspan="3">
						<input id="realName" name="realName" type="text" datatype="*" readonly style="width: 150px" class="inputxt" >
						<input name="userName"   type="hidden"  id="userName" datatype="*"  type="text"  />
						<t:choose  hiddenName="userName"  hiddenid="userName" url="userController.do?userSelectUserKey&userKey=财务" name="userList1" width="700px" height="500px"
								   icon="icon-search" title="选择处理人" textname="realName" isclear="true" isInit="true"></t:choose>
					</td>
				</tr>
			</c:if>
			<tr>
				<td align="right" width="150px" valign="middle">
					<label class="Validform_label">
						处理意见:
					</label>
				</td>
				<td class="value" colspan="3"><textarea datatype="*" id="advice" style="width:95%;height:80px" class="inputxt" rows="5" name="advice"></textarea>
				</td>
			</tr>
		</c:if>
</table>
</t:formvalid>

</body>
</html>
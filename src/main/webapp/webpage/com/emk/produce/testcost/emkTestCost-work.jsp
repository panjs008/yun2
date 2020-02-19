<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>工单处理</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>


</head>
<body>
<div id="orderDiv" title="流程图" style="width:100%;height:280px;overflow:hidden" >
	<iframe scrolling="no" id="processFrm" frameborder="0" style="margin-top: -8px;margin-left:0px;"  src="flowController.do?showProcess&id=${param.id}&tableName=emk_test_cost" width="100%" height="100%"></iframe>
</div>
<div id="timeDiv" title="流程时间轴" style="width:100%;height:300px;overflow:hidden" >
	<iframe scrolling="no" id="timeFrm" frameborder="0"  src="emkTestCostController.do?goTime&id=${param.id}" width="100%" height="100%"></iframe>
</div>

<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkTestCostController.do?doSubmit"  tiptype="1" >
	<input id="id" name="id" type="hidden" value="${param.id }"/>
	<input id="yfId" name="yfId" type="hidden" value="${param.yfId }"/>

	<table id="adviceTb" style="width: 100%;margin-top:2px;margin-bottom: 4px" cellpadding="0" cellspacing="1" class="formtable">
		<c:if test="${(emkTestCostPage.state eq 1 or emkTestCostPage.state eq 64) && ROLE.rolecode eq 'ywgdy'
		 				|| (emkTestCostPage.state eq 61 or emkTestCostPage.state eq 4) && ROLE.rolecode eq 'ywjl'
		 				|| (emkTestCostPage.state eq 3 or emkTestCostPage.state eq 27 or emkTestCostPage.state eq 48) && ROLE.rolecode eq 'cw'
		 				|| emkTestCostPage.state eq 25 && ROLE.rolecode eq 'cwjl'
		 				|| emkTestCostPage.state eq 47 && ROLE.rolecode eq 'zjl' && param.node eq 'zjlpfTask'}">
			<tr>
				<td align="right">
					<label class="Validform_label">
						是否同意:
					</label>
				</td>
				<td class="value" colspan="3">
					<input name="isPass" type="radio" datatype="*"  value="0">
					是
					<input name="isPass" type="radio" datatype="*"  value="1">
					否
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">是否同意</label>
				</td>
			</tr>
			<c:if test="${emkTestCostPage.state eq 61 && ROLE.rolecode eq 'scgdy'  && param.node eq 'csTask'}">
				<tr id="chooseUser">
					<td align="right" width="150px" valign="middle">
						<label class="Validform_label">
							指定测试员:
						</label>
					</td>

					<td class="value" colspan="3">
						<input id="realName" name="realName" type="text" datatype="*" readonly style="width: 150px" class="inputxt" >
						<input name="userName"   type="hidden"  id="userName" datatype="*"  type="text"  />
						<t:choose  hiddenName="userName"  hiddenid="userName" url="userController.do?userSelectUserKey&userKey=测试员" name="userList1" width="700px" height="500px"
								   icon="icon-search" title="选择处理人" textname="realName" isclear="true" isInit="true"></t:choose>
					</td>
				</tr>
			</c:if>
			<c:if test="${emkTestCostPage.state eq 62 && ROLE.rolecode eq 'tester' && param.node eq 'bgTask'}">
				<tr>
					<td align="right">
						<label class="Validform_label">
							测试报告:
						</label>
					</td>
					<td class="value" colspan="3">
						<input id="fileName" name="fileName" datatype="*" type="hidden" />
						<input id="fileNameUrl" name="fileNameUrl"   type="hidden" />
						<t:upload name="instruction0" id="instruction0" dialog="false" extend="*.jpg;*.png;*.doc;*.txt;*.xls" buttonText="添加文件" queueID="instructionfile" view="false" auto="true" uploader="systemController.do?saveFiles"  onUploadSuccess="uploadSuccess0" >
						</t:upload><span id="fileNameId"></span>
					</td>
				</tr>
				<tr>
					<td colspan="4" id="instructionfile" class="value">
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
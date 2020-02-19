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
	<iframe scrolling="no" id="processFrm" frameborder="0" style="margin-top: -8px;"  src="flowController.do?showProcess&id=${param.id}&tableName=emk_enquiry" width="100%" height="100%"></iframe>
</div>
<div id="timeDiv" title="流程时间轴" style="width:100%;height:320px;overflow:hidden" >
	<iframe scrolling="no" id="timeFrm" frameborder="0"  src="emkEnquiryController.do?goTime&id=${param.id}" width="100%" height="100%"></iframe>
</div>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkEnquiryController.do?doSubmit"  tiptype="1" >
	<input id="id" name="id" type="hidden" value="${param.id }"/>
	<table id="adviceTb" style="width: 100%;margin-top:2px;margin-bottom: 4px" cellpadding="0" cellspacing="1" class="formtable">
		<c:if test="${((emkEnquiry.state eq 3 || emkEnquiry.state eq 4 || emkEnquiry.state eq 6 || emkEnquiry.state eq 12  || (emkEnquiry.state eq 15  && param.node eq 'fkgcqrTask') || emkEnquiry.state eq 17) && (ROLE.rolecode eq 'gc' || ROLE.rolecode eq 'ptyh' || ROLE.rolecode eq 'admin'))
		 				|| ( emkEnquiry.state eq 9 && (ROLE.rolecode eq 'ptyh' || ROLE.rolecode eq 'admin'))}">
			<%--<c:set value="业务经理" var="userKey" scope="session"></c:set>--%>
			<tr>
				<td align="right">
					<label class="Validform_label">
						是否<c:if test="${emkEnquiry.state ne 17}">同意</c:if><c:if test="${emkEnquiry.state eq 17}">结算完成</c:if>:
					</label>
				</td>
				<td class="value" colspan="3">
					<input name="isPass" type="radio" datatype="*" value="0">
					是
					<input name="isPass" type="radio" datatype="*" value="1">
					否
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">是否同意</label>
				</td>
			</tr>
			<c:if test="${emkEnquiry.state eq 9}">
				<tr>
					<td align="right">
						<label class="Validform_label">
							是否退货:
						</label>
					</td>
					<td class="value" colspan="3">
						<input name="isBack" type="radio" datatype="*" value="0">
						是
						<input name="isBack" type="radio" datatype="*" value="1">
						否
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">是否退货</label>
					</td>
				</tr>
			</c:if>
			<tr>
				<td align="right" width="150px" valign="middle">
					<label class="Validform_label">
						处理意见:
					</label>
				</td>
				<td class="value" colspan="3"><textarea datatype="*" id="advice" style="width:90%;height:60px" class="inputxt" rows="5" name="advice"></textarea>
				</td>
			</tr>

		</c:if>


</table>
</t:formvalid>

</body>
</html>
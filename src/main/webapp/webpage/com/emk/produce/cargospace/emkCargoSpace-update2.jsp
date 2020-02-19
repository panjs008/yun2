<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>出货通知单</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<%@include file="/context/header2.jsp"%>
	<script src="${webRoot}/context/gys.js"></script>
	<script type="text/javascript">


	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkCargoSpaceController.do?doUpdate" tiptype="1">
	<input id="cargoId" name="cargoId" type="hidden" value="${emkCargoSpacePage.id }"/>
	<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
		<c:if test="${param.node eq 'dctzdTask' }">
			<tr>
				<td align="right" >
					<label class="Validform_label">
						操作类型:
					</label>
				</td>
				<td class="value"  colspan="3">
					<input  name="isPass" type="radio" datatype="*" value="0">
					保存数据&nbsp;&nbsp;&nbsp;&nbsp;
					<input  name="isPass" type="radio" datatype="*"  value="1">
					提交审核
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">操作类型</label>
				</td>
			</tr>
		</c:if>
		<tr>
			<td align="right">
				<label class="Validform_label">
					订舱通知单号:
				</label>
			</td>
			<td class="value">
				<input id="cargoNo" name="cargoNo"  value="${emkCargoSpacePage.cargoNo }" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">订舱通知单号</label>
			</td>
			<td class="value" colspan="2"></td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					提交订舱日期:
				</label>
			</td>
			<td class="value">
				<input id="kdDate" name="kdDate" readonly value="${emkCargoSpacePage.kdDate }" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  type="text" style="width: 150px" class="Wdate"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">提交订舱日期</label>
			</td>
			<td class="value" colspan="2"></td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					出货通知单号:
				</label>
			</td>
			<td class="value">
				<input id="outForumNo" name="outForumNo" type="text" value="${emkCargoSpacePage.outForumNo }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">出货通知单号</label>
			</td>
			<td class="value" colspan="2"></td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					离厂放行条号:
				</label>
			</td>
			<td class="value">
				<input id="levealFactoryNo" name="levealFactoryNo" type="text" value="${emkCargoSpacePage.levealFactoryNo }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">离厂放行条号</label>
			</td>
			<td class="value" colspan="2"></td>
		</tr>

		<tr>
			<td align="right" >
				<label class="Validform_label">
					业务部门:
				</label>
			</td>
			<td class="value" >
				<input id="businesseDeptName" name="businesseDeptName" value="${emkCargoSpacePage.businesseDeptName }" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<input id="businesseDeptId" name="businesseDeptId"  value="${emkCargoSpacePage.businesseDeptId }" type="hidden"  />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">业务部门</label>
			</td>
			<td class="value" colspan="2"></td>

		</tr>
		<tr>
			<td align="right" >
				<label class="Validform_label">
					业务员:
				</label>
			</td>
			<td class="value" >
				<select id="businesser"  name="businesser" datatype="*">
					<option value=''>请选择</option>
					<c:forEach items="${ywyList}" var="ywy">
						<option value='${ywy.userName}' ${ywy.userName eq emkCargoSpacePage.businesser ? 'selected':''}>${ywy.realName}</option>
					</c:forEach>
				</select>
				<%--<select class="form-control select2" id="businesserId" datatype="*" >
					<option value=''>请选择</option>
				</select>
				<input id="businesser" name="businesser" readonly value="${emkCargoSpacePage.businesser }" type="hidden" style="width: 150px" class="inputxt"  ignore="ignore" />
				<input id="businesserName" name="businesserName"  value="${emkCargoSpacePage.businesserName }" type="hidden"  />--%>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">业务员</label>
			</td>
			<td class="value" colspan="2"></td>

		</tr>
		<tr>
			<td align="right" >
				<label class="Validform_label">
					业务跟单员:
				</label>
			</td>
			<td class="value" >
				<select id="tracer"  name="tracer" datatype="*">
					<option value=''>请选择</option>
					<c:forEach items="${ywgdyList}" var="ywgdy">
						<option value='${ywgdy.userName}' ${ywgdy.userName eq emkCargoSpacePage.tracer ? 'selected':''}>${ywgdy.realName}</option>
					</c:forEach>
				</select>
				<%--<select class="form-control select2" id="tracerId"  >
					<option value=''>请选择</option>
				</select>
				<input id="tracer" name="tracer" readonly type="hidden" value="${emkCargoSpacePage.tracer }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<input id="tracerName" name="tracerName"  type="hidden" value="${emkCargoSpacePage.tracerName }" />--%>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">业务员</label>
			</td>
			<td class="value" colspan="2"></td>

		</tr>
		<tr>
			<td align="right" >
				<label class="Validform_label">
					生产跟单员:
				</label>
			</td>
			<td class="value">
				<select id="developer"  name="developer" datatype="*">
					<option value=''>请选择</option>
					<c:forEach items="${scgdyList}" var="scgdy">
						<option value='${scgdy.userName}'  ${scgdy.userName eq emkCargoSpacePage.developer ? 'selected':''}>${scgdy.realName}</option>
					</c:forEach>
				</select>
				<%--<select class="form-control select2" id="developerId"  >
					<option value=''>请选择</option>
				</select>
				<input id="developer" name="developer" readonly value="${emkCargoSpacePage.developer }" type="hidden" style="width: 150px" class="inputxt"  ignore="ignore" />
				<input id="developerName" name="developerName" value="${emkCargoSpacePage.developerName }" type="hidden"  />--%>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">业务员</label>
			</td>
			<td class="value" colspan="2"></td>

		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					船务员:
				</label>
			</td>
			<td class="value">
				<input id="cwyer" name="cwyer" type="text" value="${emkCargoSpacePage.cwyer }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">船务员</label>
			</td>
			<td class="value" colspan="2"></td>

		</tr>
		<tr>
			<td align="right" ></td>
			<td align="right" class="value">
				<label class="Validform_label">
					收货人:
				</label>
			</td>
			<td class="value">
				<input id="shrer" name="shrer" type="text" value="${emkCargoSpacePage.shrer }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">船务员</label>
			</td>
			<td class="value"></td>
		</tr>
		<tr>
			<td align="right"></td>
			<td align="right" class="value">
				<label class="Validform_label">
					电话:
				</label>
			</td>
			<td class="value">
				<input id="telphone" name="telphone" type="text" value="${emkCargoSpacePage.telphone }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">电话</label>
			</td>
			<td class="value"></td>
		</tr>
		<tr>
			<td align="right"></td>
			<td class="value"></td>
			<td align="right" class="value">
				<label class="Validform_label">
					到港时间:
				</label>
			</td>
			<td class="value">
				<input id="arrvieDate" name="arrvieDate" readonly value="${emkCargoSpacePage.arrvieDate }" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  type="text" style="width: 150px" class="Wdate"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">到港时间</label>
			</td>
		</tr>
	</table>
	<t:tabs id="detailDetail" iframe="false"  tabPosition="top" fit="false">
		<t:tab href="emkCargoSpaceController.do?boxMxList&cargoId=${emkCargoSpacePage.id}" icon="icon-search" title="明细" id="detail"></t:tab>
	</t:tabs>
</t:formvalid>
</body>

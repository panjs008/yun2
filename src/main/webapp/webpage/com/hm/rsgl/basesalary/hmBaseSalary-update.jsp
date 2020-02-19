<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>薪酬预设管理</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<script type="text/javascript">
		//编写自定义JS代码
		function returnToStaff(){

		}
	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="hmBaseSalaryController.do?doUpdate" tiptype="1">
	<input id="bid" name="bid" type="hidden" value="${hmBaseSalaryPage.id }"/>
	<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">
					姓名:
				</label>
			</td>
			<td class="value" colspan="3">
				<input id="workNo" name="workNo" value="${hmBaseSalaryPage.workNo }"  type="hidden" style="width: 150px" class="inputxt"  ignore="ignore" />

				<input id="realName" name="realName" datatype="*" value="${hmBaseSalaryPage.realName }" readonly type="text" style="width: 150px" class="inputxt" />
				<t:choose  hiddenName="staffId"  hiddenid="id" url="hmStaffController.do?select" name="hmStaffList" width="800px" height="500px"
						   icon="icon-search" title="选择人员" textname="id,workNo,realName,deptCode,deptName,workCode,workName" isclear="true" isInit="true"></t:choose>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">姓名</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					月份:
				</label>
			</td>
			<td class="value" colspan="3">
				<input id="month" name="month" datatype="*" readonly type="text"  value="${hmBaseSalaryPage.month }" onClick="WdatePicker({dateFmt:'yyyy-MM'})" style="width: 150px" class="Wdate"  />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">月份</label>
			</td>
		</tr>
		<%--<tr>
			<td align="right">
				<label class="Validform_label">
					工号:
				</label>
			</td>
			<td class="value" colspan="3">
				<input id="workNo" name="workNo" value="${hmBaseSalaryPage.workNo }" readonly type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">工号</label>
			</td>
		</tr>--%>
		<tr>
			<td align="right">
				<label class="Validform_label">
					部门:
				</label>
			</td>
			<td class="value">
				<input id="deptCode" name="deptCode" value="${hmBaseSalaryPage.deptCode }" type="hidden" style="width: 150px" class="inputxt"  ignore="ignore" />
				<input id="deptName" name="deptName" value="${hmBaseSalaryPage.deptName }" readonly type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">部门</label>
			</td>

			<td align="right">
				<label class="Validform_label">
					车间:
				</label>
			</td>
			<td class="value">
				<input id="workCode" name="workCode" value="${hmBaseSalaryPage.workCode }" type="hidden" style="width: 150px" class="inputxt"  ignore="ignore" />
				<input id="workName" name="workName" value="${hmBaseSalaryPage.workName }" readonly type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">车间</label>
			</td>
		</tr>
		<c:forEach var="list" items="${categoryEntities}" varStatus="status">
			<c:if test="${status.index % 2 ==0}">
				<tr>
					<td align="right" width="150px">
						<label class="Validform_label">
								${list.name}:
						</label>
					</td>
					<td class="value" colspan="${fn:length(categoryEntities)-1 eq status.index && fn:length(categoryEntities) % 2 ==1   ? 3:'1'}">
						<c:if test="${list.required eq '0'}">
							<c:choose>
								<c:when test="${(list.column_type eq '0' || list.column_type eq '2') || list.column_type eq '1'}">
									<input id="${list.code}" style="width: 150px"  name="${list.code}" value="${hmBaseSalaryPage[list.code]}" type="text"  datatype="*"   class="inputxt" >
								</c:when>
								<c:when test="${list.column_type eq '2'}">
									<input id="${list.code}" style="width: 150px"  name="${list.code}" value="${hmBaseSalaryPage[list.code]}" value="${hmBaseSalaryPage[list.code]}" type="text" datatype="n"   class="inputxt" >
								</c:when>
								<c:when test="${list.column_type eq '3'}">
									<input id="${list.code}" name="${list.code}" value="${hmBaseSalaryPage[list.code]}" type="text" datatype="*" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width: 150px"    class="inputxt" >
								</c:when>
								<c:when test="${list.column_type eq '4'}">
									<textarea id="${list.code}" name="${list.code}"  style="width:500px;height:60px"  datatype="*"  class="inputxt">${hmBaseSalaryPage[list.code]}</textarea>
								</c:when>
								<c:when test="${list.column_type eq '6' || list.column_type eq '7'}">
									<input id="${list.code}" name="${list.code}" type="text" datatype="d"  value="${hmBaseSalaryPage[list.code]}"  class="inputxt"  style="width: 150px">
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
						</c:if>
						<c:if test="${list.required ne '0'}">
							<c:choose>
								<c:when test="${(list.column_type eq '0' || list.column_type eq '2') || list.column_type eq '1'}">
									<input id="${list.code}" style="width: 150px"  name="${list.code}" ignore="ignore" type="text"  value="${hmBaseSalaryPage[list.code]}"  class="inputxt" >
								</c:when>
								<c:when test="${list.column_type eq '2'}">
									<input id="${list.code}" style="width: 150px"  name="${list.code}" ignore="ignore" type="text"  value="${hmBaseSalaryPage[list.code]}"  class="inputxt" >
								</c:when>
								<c:when test="${list.column_type eq '3'}">
									<input id="${list.code}"  name="${list.code}" type="text" value="${hmBaseSalaryPage[list.code]}" ignore="ignore" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width: 150px"    class="inputxt" >
								</c:when>
								<c:when test="${list.column_type eq '4'}">
									<textarea id="${list.code}" name="${list.code}" ignore="ignore" style="width:500px;height:60px"  class="inputxt">${hmBaseSalaryPage[list.code]}</textarea>
								</c:when>
								<c:when test="${list.column_type eq '6' || list.column_type eq '7'}">
									<input id="${list.code}" name="${list.code}" type="text" datatype="d" ignore="ignore"  value="${hmBaseSalaryPage[list.code]}"  class="inputxt"  style="width: 150px">
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
						</c:if>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">${list.name}</label>
					</td>
				<c:if test="${fn:length(categoryEntities)-1 eq status.index && fn:length(categoryEntities) % 2 ==1}">
					</tr>
				</c:if>
			</c:if>
			<c:if test="${status.index % 2 ==1}">
					<td align="right" width="150px">
						<label class="Validform_label">
								${list.name}:
						</label>
					</td>
					<td class="value">
						<c:if test="${list.required eq '0'}">
							<c:choose>
								<c:when test="${(list.column_type eq '0' || list.column_type eq '2') || list.column_type eq '1'}">
									<input id="${list.code}" style="width: 150px"  name="${list.code}" value="${hmBaseSalaryPage[list.code]}" type="text"  datatype="*"   class="inputxt" >
								</c:when>
								<c:when test="${list.column_type eq '2'}">
									<input id="${list.code}" style="width: 150px"  name="${list.code}" value="${hmBaseSalaryPage[list.code]}" value="${hmBaseSalaryPage[list.code]}" type="text" datatype="n"   class="inputxt" >
								</c:when>
								<c:when test="${list.column_type eq '3'}">
									<input id="${list.code}" name="${list.code}" value="${hmBaseSalaryPage[list.code]}" type="text" datatype="*" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width: 150px"    class="inputxt" >
								</c:when>
								<c:when test="${list.column_type eq '4'}">
									<textarea id="${list.code}" name="${list.code}"  style="width:500px;height:60px"  datatype="*"  class="inputxt">${hmBaseSalaryPage[list.code]}</textarea>
								</c:when>
								<c:when test="${list.column_type eq '6' || list.column_type eq '7'}">
									<input id="${list.code}" name="${list.code}" type="text" datatype="d"  value="${hmBaseSalaryPage[list.code]}"  class="inputxt"  style="width: 150px">
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
						</c:if>
						<c:if test="${list.required ne '0'}">
							<c:choose>
								<c:when test="${(list.column_type eq '0' || list.column_type eq '2') || list.column_type eq '1'}">
									<input id="${list.code}" style="width: 150px" ignore="ignore"  name="${list.code}" type="text"  value="${hmBaseSalaryPage[list.code]}"  class="inputxt" >
								</c:when>
								<c:when test="${list.column_type eq '2'}">
									<input id="${list.code}" style="width: 150px"  ignore="ignore" name="${list.code}" type="text"  value="${hmBaseSalaryPage[list.code]}"  class="inputxt" >
								</c:when>
								<c:when test="${list.column_type eq '3'}">
									<input id="${list.code}"  name="${list.code}" type="text" ignore="ignore" value="${hmBaseSalaryPage[list.code]}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width: 150px"    class="inputxt" >
								</c:when>
								<c:when test="${list.column_type eq '4'}">
									<textarea id="${list.code}" name="${list.code}" ignore="ignore" style="width:500px;height:60px"  class="inputxt">${hmBaseSalaryPage[list.code]}</textarea>
								</c:when>
								<c:when test="${list.column_type eq '6' || list.column_type eq '7'}">
									<input id="${list.code}" name="${list.code}" type="text" datatype="d"  ignore="ignore" value="${hmBaseSalaryPage[list.code]}"  class="inputxt"  style="width: 150px">
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
						</c:if>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">${list.name}</label>
					</td>
				</tr>
			</c:if>

		</c:forEach>

	</table>
</t:formvalid>
</body>
<script src = "webpage/com/hm/rsgl/basesalary/hmBaseSalary.js"></script>

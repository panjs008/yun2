<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>薪酬预设管理</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<%@include file="/context/header2.jsp"%>
	<script type="text/javascript" src="js/ajaxfileupload.js"></script>

	<script type="text/javascript">
		//编写自定义JS代码
		function returnToStaff(){

		}
	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="hmBaseSalaryController.do?doUpdateChange" tiptype="1">
	<input id="id" name="id" type="hidden" value="${hmBaseSalaryPage.id }"/>
	<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">
					部门:
				</label>
			</td>
			<td class="value">
				<input id="deptCode" name="deptCode" readonly  value="${hmBaseSalaryPage.deptCode }" type="hidden" style="width: 150px" class="inputxt"  ignore="ignore" />
				<input id="deptName" name="deptName" readonly  value="${hmBaseSalaryPage.deptName }" readonly type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">部门</label>
			</td>

			<td align="right">
				<label class="Validform_label">
					车间:
				</label>
			</td>
			<td class="value">
				<input id="workCode" name="workCode" readonly  value="${hmBaseSalaryPage.workCode }" type="hidden" style="width: 150px" class="inputxt"  ignore="ignore" />
				<input id="workName" name="workName" readonly  value="${hmBaseSalaryPage.workName }" readonly type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">车间</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					姓名:
				</label>
			</td>
			<td class="value">
				<input id="workNo" name="workNo" readonly  value="${hmBaseSalaryPage.workNo }"  type="hidden" style="width: 150px" class="inputxt"  ignore="ignore" />
				<input id="realName" name="realName" readonly  value="${hmBaseSalaryPage.realName }" readonly type="text" style="width: 150px" class="inputxt"  ignore="ignore" />

				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">姓名</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					实施时间:
				</label>
			</td>
			<td class="value" colspan="3">
				<input id="excetuDate" value="${hmBaseSalaryPage.excetuDate }" readonly  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly name="excetuDate" type="text" style="width: 150px" class="Wdate"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">实施时间</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					调薪摘要:
				</label>
			</td>
			<td class="value" colspan="3">
				<textarea  id="reason"  style="width:95%;height:30px" class="inputxt" rows="3" name="reason">${hmBaseSalaryPage.reason }</textarea>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">调薪摘要</label>
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
		<tr>
			<td align="right">
				<label class="Validform_label">
					图片:
				</label>
			</td>
			<td class="value" colspan="3">
				<input type="hidden" id="saveFileName" name="saveFileName" value="${hmBaseSalaryPage.saveFileName }">
				<input type="hidden" id="fileNameUrl" name="fileNameUrl" value="${hmBaseSalaryPage.fileNameUrl }">
				<input name="files" id="salaryFile" onchange="saveFile2('uploadControl.do?upload&fileDir=salary','salaryFile','fileName','formobj','saveFileName','fileNameUrl','salary');"  style="width:300px;display: none" type="file" accept=".png,.gif,.jpg,.jpeg"  />
				<input id="fileName" name="fileName" value="${hmBaseSalaryPage.fileName }" type="text" style="width: 150px;" readonly onclick="uploadClick('salaryFile')">
				<span id="salaryFileId">
					<c:if test="${hmBaseSalaryPage.fileNameUrl ne '' && hmBaseSalaryPage.fileNameUrl ne null}">
						<input class="btn" type="button" value="查看" onclick="findDetail('${webRoot}/imp/salary/${hmBaseSalaryPage.saveFileName }')" style="background:#18a689 none repeat scroll 0 0;height:28px;width:50px !important;border-radius:5px;color: #fff;" >
					</c:if>
				</span>
			</td>
		</tr>
	</table>
</t:formvalid>
</body>
<script src = "webpage/com/hm/rsgl/basesalary/hmBaseSalary.js"></script>

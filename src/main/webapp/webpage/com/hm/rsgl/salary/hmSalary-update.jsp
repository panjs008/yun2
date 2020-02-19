<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>工资表管理</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<script type="text/javascript">
		//编写自定义JS代码
	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="hmSalaryController.do?doUpdate" tiptype="1">
	<input id="id" name="id" type="hidden" value="${hmSalaryPage.id }"/>
	<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">
					姓名:
				</label>
			</td>
			<td class="value">
				<input id="realName" name="realName" readonly value="${hmSalaryPage.realName }" readonly type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">姓名</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					实发工资:
				</label>
			</td>
			<td class="value">
				<input id="money" name="money"  datatype="*" readonly value="${hmSalaryPage.money }"  type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">实发工资</label>
			</td>
		</tr>

		<tr>
			<td align="right">
				<label class="Validform_label">
					部门:
				</label>
			</td>
			<td class="value">
				<input id="deptCode" name="deptCode" value="${hmSalaryPage.deptCode }" type="hidden" style="width: 150px" class="inputxt"  ignore="ignore" />
				<input id="deptName" name="deptName" readonly value="${hmSalaryPage.deptName }" readonly type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">部门</label>
			</td>

			<td align="right">
				<label class="Validform_label">
					车间:
				</label>
			</td>
			<td class="value">
				<input id="workCode" name="workCode" value="${hmSalaryPage.workCode }" type="hidden" style="width: 150px" class="inputxt"  ignore="ignore" />
				<input id="workName" name="workName" readonly value="${hmSalaryPage.workName }" readonly type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
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
									<input id="${list.code}" style="width: 150px"  name="${list.code}" readonly value="${hmSalaryPage[list.code]}" type="text"  datatype="*"   class="inputxt" >
								</c:when>
								<c:when test="${list.column_type eq '2'}">
									<input id="${list.code}" style="width: 150px"  name="${list.code}" readonly value="${hmSalaryPage[list.code]}" value="${hmSalaryPage[list.code]}" type="text" datatype="n"   class="inputxt" >
								</c:when>
								<c:when test="${list.column_type eq '3'}">
									<input id="${list.code}" name="${list.code}" value="${hmSalaryPage[list.code]}" readonly type="text" datatype="*" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width: 150px"    class="inputxt" >
								</c:when>
								<c:when test="${list.column_type eq '4'}">
									<textarea id="${list.code}" name="${list.code}"  style="width:500px;height:60px"  readonly datatype="*"  class="inputxt">${hmSalaryPage[list.code]}</textarea>
								</c:when>
								<c:when test="${list.column_type eq '6' || list.column_type eq '7'}">
									<input id="${list.code}" name="${list.code}" type="text" datatype="d"  readonly value="${hmSalaryPage[list.code]}"  class="inputxt"  style="width: 150px">
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
						</c:if>
						<c:if test="${list.required ne '0'}">
							<c:choose>
								<c:when test="${(list.column_type eq '0' || list.column_type eq '2') || list.column_type eq '1'}">
									<input id="${list.code}" style="width: 150px"  name="${list.code}" ignore="ignore" readonly type="text"  value="${hmSalaryPage[list.code]}"  class="inputxt" >
								</c:when>
								<c:when test="${list.column_type eq '2'}">
									<input id="${list.code}" style="width: 150px"  name="${list.code}" ignore="ignore" readonly type="text"  value="${hmSalaryPage[list.code]}"  class="inputxt" >
								</c:when>
								<c:when test="${list.column_type eq '3'}">
									<input id="${list.code}"  name="${list.code}" type="text" value="${hmSalaryPage[list.code]}"readonly  ignore="ignore" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width: 150px"    class="inputxt" >
								</c:when>
								<c:when test="${list.column_type eq '4'}">
									<textarea id="${list.code}" name="${list.code}" ignore="ignore" style="width:500px;height:60px"readonly  class="inputxt">${hmSalaryPage[list.code]}</textarea>
								</c:when>
								<c:when test="${list.column_type eq '6' || list.column_type eq '7'}">
									<input id="${list.code}" name="${list.code}" type="text" datatype="d" ignore="ignore" readonly  value="${hmSalaryPage[list.code]}"  class="inputxt"  style="width: 150px">
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
									<input id="${list.code}" style="width: 150px"  name="${list.code}" value="${hmSalaryPage[list.code]}" readonly  type="text"  datatype="*"   class="inputxt" >
								</c:when>
								<c:when test="${list.column_type eq '2'}">
									<input id="${list.code}" style="width: 150px"  name="${list.code}" value="${hmSalaryPage[list.code]}" readonly  value="${hmSalaryPage[list.code]}" type="text" datatype="n"   class="inputxt" >
								</c:when>
								<c:when test="${list.column_type eq '3'}">
									<input id="${list.code}" name="${list.code}" value="${hmSalaryPage[list.code]}" type="text" datatype="*"  readonly  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width: 150px"    class="inputxt" >
								</c:when>
								<c:when test="${list.column_type eq '4'}">
									<textarea id="${list.code}" name="${list.code}"  style="width:500px;height:60px"  datatype="*"  readonly class="inputxt">${hmSalaryPage[list.code]}</textarea>
								</c:when>
								<c:when test="${list.column_type eq '6' || list.column_type eq '7'}">
									<input id="${list.code}" name="${list.code}" type="text" datatype="d"  value="${hmSalaryPage[list.code]}" readonly  class="inputxt"  style="width: 150px">
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
						</c:if>
						<c:if test="${list.required ne '0'}">
							<c:choose>
								<c:when test="${(list.column_type eq '0' || list.column_type eq '2') || list.column_type eq '1'}">
									<input id="${list.code}" style="width: 150px" ignore="ignore"  name="${list.code}" type="text" readonly  value="${hmSalaryPage[list.code]}"  class="inputxt" >
								</c:when>
								<c:when test="${list.column_type eq '2'}">
									<input id="${list.code}" style="width: 150px"  ignore="ignore" name="${list.code}" type="text" readonly  value="${hmSalaryPage[list.code]}"  class="inputxt" >
								</c:when>
								<c:when test="${list.column_type eq '3'}">
									<input id="${list.code}"  name="${list.code}" type="text" ignore="ignore" value="${hmSalaryPage[list.code]}"readonly  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width: 150px"    class="inputxt" >
								</c:when>
								<c:when test="${list.column_type eq '4'}">
									<textarea id="${list.code}" name="${list.code}" ignore="ignore" style="width:500px;height:60px" readonly  class="inputxt">${hmSalaryPage[list.code]}</textarea>
								</c:when>
								<c:when test="${list.column_type eq '6' || list.column_type eq '7'}">
									<input id="${list.code}" name="${list.code}" type="text" datatype="d"  ignore="ignore" readonly value="${hmSalaryPage[list.code]}"  class="inputxt"  style="width: 150px">
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
			<td class="value" align="left" colspan="4">
				<label class="Validform_label">
					基本部分
				</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					其他补贴:
				</label>
			</td>
			<td class="value">
				<input id="otherBt" name="otherBt" datatype="d" value="${hmSalaryPage.otherBt }"  type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">其他补贴</label>
			</td>

			<td align="right">
				<label class="Validform_label">
					合计:
				</label>
			</td>
			<td class="value">
				<input id="baseHj" name="baseHj" readonly datatype="d" value="${hmSalaryPage.baseHj }"  type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">合计</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					摘要:
				</label>
			</td>
			<td class="value" colspan="3">
				<textarea  id="zhaiy" style="width:95%;height:25px" class="inputxt" rows="3" name="zhaiy">${hmSalaryPage.zhaiy}</textarea>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">摘要</label>
			</td>

		</tr>
		<tr>
			<td class="value" align="left" colspan="4">
				<label class="Validform_label">
					应扣部分
				</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					劳保:
				</label>
			</td>
			<td  class="value">
				<input id="lb" name="lb"  datatype="d" value="${hmSalaryPage.lb }"  type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">劳保</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					奖罚:
				</label>
			</td>
			<td class="value">
				<input id="jf" name="jf"   value="${hmSalaryPage.jf }"  type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">奖罚</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					出勤天数:
				</label>
			</td>
			<td class="value">
				<input id="cqDay" name="cqDay" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${hmSalaryPage.cqDay}'/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">出勤天数</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					请假:
				</label>
			</td>
			<td class="value">
				<input id="bsj" name="bsj" type="hidden" style="width: 150px" class="inputxt"  ignore="ignore"  value='${hmSalaryPage.bsj}'/>
				<input  readonly type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${hmSalaryPage.hourSalary}${hmSalaryPage.hourSalary ne null ? '*':''}${hmSalaryPage.hours}${hmSalaryPage.hours ne null ? '=':''}${hmSalaryPage.bsj}'/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">请假</label>
			</td>
			<%--<td align="right">
				<label class="Validform_label">
					出勤奖:
				</label>
			</td>
			<td class="value">
				<input id="fullHourMoney" name="fullHourMoney" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${hmSalaryPage.fullHourMoney}'/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">出勤奖</label>
			</td>--%>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					迟到:
				</label>
			</td>
			<td class="value">
				<input id="cdzt" name="cdzt" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${hmSalaryPage.cdzt}'/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">迟到</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					旷工:
				</label>
			</td>
			<td class="value">
				<input id="kgwdk" name="kgwdk" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${hmSalaryPage.kgwdk}'/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">旷工</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					其他扣回:
				</label>
			</td>
			<td  class="value">
				<input id="qtkh" name="qtkh" datatype="d" value="${hmSalaryPage.qtkh }"  type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">其他扣回</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					应扣合计:
				</label>
			</td>
			<td class="value">
				<input id="ykHj" name="ykHj" readonly datatype="d" value="${hmSalaryPage.ykHj }"  type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">应扣合计</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					摘要:
				</label>
			</td>
			<td class="value" colspan="3">
				<textarea  id="remark" style="width:95%;height:25px" class="inputxt" rows="3" name="remark">${hmSalaryPage.remark}</textarea>
				<%--<input id="remark" name="remark" value="${hmSalaryPage.remark}" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />--%>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">摘要</label>
			</td>

		</tr>
	</table>
</t:formvalid>
</body>

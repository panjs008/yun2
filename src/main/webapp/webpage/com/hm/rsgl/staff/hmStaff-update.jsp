<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>人员信息表</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<%@include file="/context/header2.jsp"%>
	<script src="${webRoot}/context/pstorage.js"></script>

	<script type="text/javascript">
		//编写自定义JS代码
		$(function() {
			$("#state").val(${hmStaffPage.state });
			$("#sex").val('${hmStaffPage.sex }');
			$("#isSale").val('${hmStaffPage.isSale }');

			$('#cc').combotree({
				url : 'departController.do?setPFunction&selfId=${depart.id}',
				width: 155,
				/* onSelect : function(node) {
				 //                alert(node.text);
				 changeOrgType();
				 }*/
				onClick: function (node) {
					$("#orgIds").val(node.id);
				}
			});
		});
	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="hmStaffController.do?doUpdate" tiptype="1">
	<input id="id" name="id" type="hidden" value="${hmStaffPage.id }"/>
	<input id="deptCode" name="deptCode" type="hidden" value="${hmStaffPage.deptCode }"/>
	<input id="deptName" name="deptName" type="hidden" value="${hmStaffPage.deptName }"/>
	<input id="storageId" name="storageId" type="hidden" value="${hmStaffPage.storageId }"/>
	<input id="storageName" name="storageName" type="hidden" value="${hmStaffPage.storageName }"/>
	<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">
					工号:
				</label>
			</td>
			<td class="value">
				<input id="workNo" name="workNo" type="text" readonly style="width: 150px" class="inputxt"  ignore="ignore"  value="${hmStaffPage.workNo }"/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">工号</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					姓名:
				</label>
			</td>
			<td class="value">
				<input id="realName" name="realName" type="text" datatype="*" style="width: 150px" class="inputxt"  value='${hmStaffPage.realName}'/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">姓名</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					部门:
				</label>
			</td>
			<td class="value">
				<input id="orgIds" name="orgIds" datatype="*"  type="hidden" value="${orgIds}"/>
				<input id="cc" value="${hmStaffPage.deptName}">
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">部门</label>
			</td>
		</tr>


		<tr>
			<td align="right">
				<label class="Validform_label">
					仓库:
				</label>
			</td>
			<td class="value">
				<select class="form-control select2" id="storageNameId" datatype="*">
					<option value=''>请选择</option>
				</select>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">仓库</label>
			</td>

			<td align="right">
				<label class="Validform_label">
					助记码:
				</label>
			</td>
			<td class="value">
				<input id="zjm" name="zjm" type="text" readonly style="width: 150px" class="inputxt"  ignore="ignore"  value='${hmStaffPage.zjm}'/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">助记码</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					性别:
				</label>
			</td>
			<td class="value">
				<select id="sex" name="sex" datatype="*">
					<option value="男">男</option>
					<option value="女">女</option>
				</select>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">性别</label>
			</td>
		</tr>

		<tr>
			<td align="right">
				<label class="Validform_label">
					联系电话:
				</label>
			</td>
			<td class="value">
				<input id="telphone" name="telphone" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${hmStaffPage.telphone}'/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">联系电话</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					入职日期:
				</label>
			</td>
			<td class="value">
				<input id="rzDate" name="rzDate" type="text"   onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width: 150px" class="Wdate"   value='${hmStaffPage.rzDate}'/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">入职日期</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					转正日期:
				</label>
			</td>
			<td class="value">
				<input id="zzDate" name="zzDate" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width: 150px" class="Wdate"  ignore="ignore"  value='${hmStaffPage.zzDate}'/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">转正日期</label>
			</td>
		</tr>

		<tr>
			<td align="right">
				<label class="Validform_label">
					身份证号码:
				</label>
			</td>
			<td class="value">
				<input id="idCard" name="idCard" onkeyup="getBirthdayFromIdCard()"  onafterpaste="getBirthdayFromIdCard()" type="text" datatype="idcard" style="width:150px" class="inputxt"  ignore="ignore"  value='${hmStaffPage.idCard}'/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">身份证号码</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					家庭地址:
				</label>
			</td>
			<td class="value">
				<input id="address" name="address" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${hmStaffPage.address}'/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">家庭地址</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					民族:
				</label>
			</td>
			<td class="value">
				<input id="mz" name="mz" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${hmStaffPage.mz}'/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">民族</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					出生日期:
				</label>
			</td>
			<td class="value">
				<input id="birthDay" name="birthDay"  type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width: 150px" class="Wdate"  ignore="ignore"  value='${hmStaffPage.birthDay}'/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">出生日期</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					工龄:
				</label>
			</td>
			<td class="value">
				<input id="workYear" name="workYear" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${hmStaffPage.workYear}'/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">工龄</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					月休天数:
				</label>
			</td>
			<td class="value">
				<input id="sleepDays" name="sleepDays" datatype="n"   type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${hmStaffPage.sleepDays}'/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">月休天数</label>
			</td>
		</tr>


		<tr>
			<td align="right">
				<label class="Validform_label">
					邮箱:
				</label>
			</td>
			<td class="value">
				<input id="email" name="email" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${hmStaffPage.email}'/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">邮箱</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					在职状态:
				</label>
			</td>
			<td class="value">
				<select id="state" name="state" datatype="*">
					<option value="0">在职</option>
					<option value="1">离职</option>
				</select>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">在职状态</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					离职日期:
				</label>
			</td>
			<td class="value">
				<input id="lzDate" name="lzDate"  type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width: 150px" class="Wdate"  ignore="ignore"  value='${hmStaffPage.lzDate}'/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">离职日期</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					基本工资:
				</label>
			</td>
			<td class="value">
				<input id="salary" name="salary" type="text" datatype="d" style="width: 150px" class="inputxt"  ignore="ignore"  value='${hmStaffPage.salary}'/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">基本工资</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					备注:
				</label>
			</td>
			<td class="value" colspan="3">
				<input id="remark" name="remark" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${hmStaffPage.remark}'/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">备注</label>
			</td>
		</tr>
	</table>
</t:formvalid>
</body>
<script src = "webpage/com/hm/rsgl/staff/hmStaff.js"></script>

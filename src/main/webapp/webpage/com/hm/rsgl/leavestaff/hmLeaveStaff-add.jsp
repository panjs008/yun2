<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>离职人员信息表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="hmLeaveStaffController.do?doAdd" tiptype="1">
					<input id="id" name="id" type="hidden" value="${hmLeaveStaffPage.id }"/>
	  <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
		  <tr>
			  <td align="right">
				  <label class="Validform_label">
					  姓名:
				  </label>
			  </td>
			  <td class="value" colspan="3">
				  <input name="realName"   type="text"  style="width: 150px"  id="realName"/>
				  <input name="workNo"   type="hidden"  style="width: 150px"  id="workNo"/>
				  <t:choose  hiddenName="workNo"  hiddenid="workNo" url="hmStaffController.do?forLeave" name="hmStaffList" width="800px" height="500px"
							 icon="icon-search" title="选择人员"
							 textname="workNo,realName,deptCode,deptName,workCode,workName,groupCode,groupName,job,jb,xclb,sex,yglb,taker,workType,rzDate,zzDate,telphone,idCard,birthDay,workYear,sleepDays,mz,xueli,housePhone,jjlxr,zzDays,zzmm,isNs,hjszd,email"
							 isclear="true" isInit="true"></t:choose>
				  <span class="Validform_checktip"></span>
				  <label class="Validform_label" style="display: none;">人员</label>
			  </td>
			  <td align="right">
				  <label class="Validform_label">
					  部门:
				  </label>
			  </td>
			  <td class="value">
				  <input name="deptCode"   type="hidden"  id="deptCode"/>
				  <input name="deptName"   type="text" readonly  style="width: 150px"  id="deptName"/>
				  <span class="Validform_checktip"></span>
				  <label class="Validform_label" style="display: none;">部门</label>
			  </td>
		  </tr>

		  <tr>
			  <td align="right">
				  <label class="Validform_label">
					  车间:
				  </label>
			  </td>
			  <td class="value">
				  <input name="workCode"   type="hidden"  id="workCode"/>
				  <input name="workName"  style="width: 150px"  readonly type="text"  id="workName"/>
				  <span class="Validform_checktip"></span>
				  <label class="Validform_label" style="display: none;">车间</label>
			  </td>
			  <td align="right">
				  <label class="Validform_label">
					  班组:
				  </label>
			  </td>
			  <td class="value">
				  <input name="groupCode"   type="hidden"  id="groupCode"/>
				  <input name="groupName" style="width: 150px"   readonly  type="text"  id="groupName"/>
				  <span class="Validform_checktip"></span>
				  <label class="Validform_label" style="display: none;">班组</label>
			  </td>
			  <td align="right">
				  <label class="Validform_label">
					  职务:
				  </label>
			  </td>
			  <td class="value">
				  <t:dictSelect id="job" field="job"  typeGroupCode="job"   defaultVal="default" hasLabel="false" title="职务"></t:dictSelect>
				  <span class="Validform_checktip"></span>
				  <label class="Validform_label" style="display: none;">职务</label>
			  </td>
		  </tr>
		  <tr>
			  <td align="right">
				  <label class="Validform_label">
					  级别:
				  </label>
			  </td>
			  <td class="value">
				  <t:dictSelect id="jb" field="jb" typeGroupCode="jb"   defaultVal="default" hasLabel="false" title="级别"></t:dictSelect>
				  <span class="Validform_checktip"></span>
				  <label class="Validform_label" style="display: none;">级别</label>
			  </td>
			  <td align="right">
				  <label class="Validform_label">
					  薪酬类别:
				  </label>
			  </td>
			  <td class="value">
				  <t:dictSelect id="xclb" field="xclb" typeGroupCode="xclb"   defaultVal="default" hasLabel="false" title="薪酬类别"></t:dictSelect>
				  <span class="Validform_checktip"></span>
				  <label class="Validform_label" style="display: none;">薪酬类别</label>
			  </td>
			  <td align="right">
				  <label class="Validform_label">
					  性别:
				  </label>
			  </td>
			  <td class="value">
				  <select id="sex" name="sex">
					  <option value="男">男</option>
					  <option value="女">女</option>
				  </select>
				  <%--<t:dictSelect id="sex" field="sex" typeGroupCode="sex"   defaultVal="default" hasLabel="false" title="性别"></t:dictSelect>--%>
				  <span class="Validform_checktip"></span>
				  <label class="Validform_label" style="display: none;">性别</label>
			  </td>

		  </tr>

		  <tr>
			  <td align="right">
				  <label class="Validform_label">
					  员工类别:
				  </label>
			  </td>
			  <td class="value">
				  <t:dictSelect id="yglb" field="yglb" typeGroupCode="yglb"   defaultVal="default" hasLabel="false" title="员工类别"></t:dictSelect>
				  <span class="Validform_checktip"></span>
				  <label class="Validform_label" style="display: none;">员工类别</label>
			  </td>
			  <td align="right">
				  <label class="Validform_label">
					  带工:
				  </label>
			  </td>
			  <td class="value">
				  <t:dictSelect id="taker" field="taker" typeGroupCode="dg"  defaultVal="default" hasLabel="false" title="带工"></t:dictSelect>
				  <span class="Validform_checktip"></span>
				  <label class="Validform_label" style="display: none;">带工</label>
			  </td>
			  <td align="right">
				  <label class="Validform_label">
					  固定/临时:
				  </label>
			  </td>
			  <td class="value">
				  <t:dictSelect id="workType" field="workType" typeGroupCode="gdls"  defaultVal="default" hasLabel="false" title="性别"></t:dictSelect>
				  <span class="Validform_checktip"></span>
				  <label class="Validform_label" style="display: none;">固定临时</label>
			  </td>

		  </tr>
		  <tr>
			  <td align="right">
				  <label class="Validform_label">
					  入职日期:
				  </label>
			  </td>
			  <td class="value">
				  <input id="rzDate" name="rzDate" type="text"  readonly  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width: 150px" class="Wdate"  ignore="ignore"  value='${hmLeaveStaffPage.rzDate}'/>
				  <span class="Validform_checktip"></span>
				  <label class="Validform_label" style="display: none;">入职日期</label>
			  </td>
			  <td align="right">
				  <label class="Validform_label">
					  转正日期:
				  </label>
			  </td>
			  <td class="value">
				  <input id="zzDate" name="zzDate" type="text"  readonly  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width: 150px" class="Wdate"  ignore="ignore"  value='${hmLeaveStaffPage.zzDate}'/>
				  <span class="Validform_checktip"></span>
				  <label class="Validform_label" style="display: none;">转正日期</label>
			  </td>
			  <td align="right">
				  <label class="Validform_label">
					  手机号:
				  </label>
			  </td>
			  <td class="value">
				  <input id="telphone" name="telphone" type="text" readonly  style="width: 150px" class="inputxt"  ignore="ignore"  value='${hmLeaveStaffPage.telphone}'/>
				  <span class="Validform_checktip"></span>
				  <label class="Validform_label" style="display: none;">手机号</label>
			  </td>
		  </tr>

		  <tr>
			  <td align="right">
				  <label class="Validform_label">
					  身份证号码:
				  </label>
			  </td>
			  <td class="value" colspan="5">
				  <input id="idCard" name="idCard" readonly  onafterpaste="getBirthdayFromIdCard()" type="text"  style="width:150px" class="inputxt"  ignore="ignore"  value='${hmLeaveStaffPage.idCard}'/>
				  <span class="Validform_checktip"></span>
				  <label class="Validform_label" style="display: none;">身份证号码</label>
			  </td>
		  </tr>
		  <tr>
			  <td align="right">
				  <label class="Validform_label">
					  出生日期:
				  </label>
			  </td>
			  <td class="value">
				  <input id="birthDay" name="birthDay"  readonly  type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width: 150px" class="Wdate"  ignore="ignore"  value='${hmLeaveStaffPage.birthDay}'/>
				  <span class="Validform_checktip"></span>
				  <label class="Validform_label" style="display: none;">出生日期</label>
			  </td>
			  <td align="right">
				  <label class="Validform_label">
					  工龄:
				  </label>
			  </td>
			  <td class="value">
				  <input id="workYear" name="workYear" readonly  type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${hmLeaveStaffPage.workYear}'/>
				  <span class="Validform_checktip"></span>
				  <label class="Validform_label" style="display: none;">工龄</label>
			  </td>
			  <td align="right">
				  <label class="Validform_label">
					  月休天数:
				  </label>
			  </td>
			  <td class="value">
				  <input id="sleepDays" name="sleepDays" readonly  type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${hmLeaveStaffPage.sleepDays}'/>
				  <span class="Validform_checktip"></span>
				  <label class="Validform_label" style="display: none;">月休天数</label>
			  </td>
		  </tr>
		  <tr>
			  <td align="right">
				  <label class="Validform_label">
					  民族:
				  </label>
			  </td>
			  <td class="value">
				  <input id="mz" name="mz" type="text" readonly  style="width: 150px" class="inputxt"  ignore="ignore"  value='${hmLeaveStaffPage.mz}'/>
				  <span class="Validform_checktip"></span>
				  <label class="Validform_label" style="display: none;">民族</label>
			  </td>
			  <td align="right">
				  <label class="Validform_label">
					  学历:
				  </label>
			  </td>
			  <td class="value">
				  <t:dictSelect id="xueli" field="xueli"   typeGroupCode="xueli"  defaultVal="default" hasLabel="false" title="学历"></t:dictSelect>
				  <span class="Validform_checktip"></span>
				  <label class="Validform_label" style="display: none;">学历</label>
			  </td>
			  <td align="right">
				  <label class="Validform_label">
					  家庭电话:
				  </label>
			  </td>
			  <td class="value">
				  <input id="housePhone" name="housePhone"  readonly type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${hmLeaveStaffPage.housePhone}'/>
				  <span class="Validform_checktip"></span>
				  <label class="Validform_label" style="display: none;">家庭电话</label>
			  </td>
		  </tr>

		  <tr>
			  <td align="right">
				  <label class="Validform_label">
					  联系人电话:
				  </label>
			  </td>
			  <td class="value">
				  <input id="jjlxr" name="jjlxr" type="text" readonly  style="width: 150px" class="inputxt"  ignore="ignore"  value='${hmLeaveStaffPage.jjlxr}'/>
				  <span class="Validform_checktip"></span>
				  <label class="Validform_label" style="display: none;">紧急联系人电话</label>
			  </td>
			  <td align="right">
				  <label class="Validform_label">
					  转正提醒:
				  </label>
			  </td>
			  <td class="value">
				  <input id="zzDays" name="zzDays" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${hmLeaveStaffPage.zzDays}'/>
				  <span class="Validform_checktip"></span>
				  <label class="Validform_label" style="display: none;">转正提醒</label>
			  </td>
			  <td align="right">
				  <label class="Validform_label">
					  政治面貌:
				  </label>
			  </td>
			  <td class="value">
				  <t:dictSelect id="zzmm" field="zzmm" typeGroupCode="zzmm" defaultVal="default" hasLabel="false" title="政治面貌"></t:dictSelect>
				  <span class="Validform_checktip"></span>
				  <label class="Validform_label" style="display: none;">政治面貌</label>
			  </td>
		  </tr>

		  <tr>
			  <td align="right">
				  <label class="Validform_label">
					  是否内宿:
				  </label>
			  </td>
			  <td class="value">
				  <t:dictSelect id="isNs" field="isNs" typeGroupCode="isNs" defaultVal="default" hasLabel="false" title="政治面貌"></t:dictSelect>
				  <span class="Validform_checktip"></span>
				  <label class="Validform_label" style="display: none;">是否内宿</label>
			  </td>
			  <td align="right">
				  <label class="Validform_label">
					  户籍所在地:
				  </label>
			  </td>
			  <td class="value">
				  <input id="hjszd" name="hjszd" type="text" readonly  style="width: 150px" class="inputxt"  ignore="ignore"  value='${hmLeaveStaffPage.hjszd}'/>
				  <span class="Validform_checktip"></span>
				  <label class="Validform_label" style="display: none;">户籍所在地</label>
			  </td>
			  <td align="right">
				  <label class="Validform_label">
					  电子邮件:
				  </label>
			  </td>
			  <td class="value">
				  <input id="email" name="email" type="text"  readonly style="width: 150px" class="inputxt"  ignore="ignore"  value='${hmLeaveStaffPage.email}'/>
				  <span class="Validform_checktip"></span>
				  <label class="Validform_label" style="display: none;">电子邮件</label>
			  </td>

		  </tr>
		  <tr>
			  <td align="right">
				  <label class="Validform_label">
					  申请时间:
				  </label>
			  </td>
			  <td class="value">
				  <input id="applyDate" name="applyDate"  type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width: 150px" class="Wdate"  ignore="ignore"  value='${applyDate}'/>
				  <span class="Validform_checktip"></span>
				  <label class="Validform_label" style="display: none;">申请时间</label>
			  </td>
			  <td align="right">
				  <label class="Validform_label">
					  离职日期:
				  </label>
			  </td>
			  <td class="value">
				  <input id="leaveDate" name="leaveDate"  type="text" datatype="*" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'applyDate\');}'})" style="width: 150px" class="Wdate"  ignore="ignore"  value='${hmLeaveStaffPage.leaveDate}'/>
				  <span class="Validform_checktip"></span>
				  <label class="Validform_label" style="display: none;">离职日期</label>
			  </td>
			  <td align="right">
				  <label class="Validform_label">
					  离职类别:
				  </label>
			  </td>
			  <td class="value">
				  <select id="leaveType" name="leaveType" >
					  <option value="0">正常离职</option>
					  <option value="1">自离</option>
				  </select>
				  <span class="Validform_checktip"></span>
				  <label class="Validform_label" style="display: none;">离职类别</label>
			  </td>
		  </tr>
	  </table>

  </t:formvalid>
 </body>
  <script src = "webpage/com/hm/rsgl/leavestaff/hmLeaveStaff.js"></script>		

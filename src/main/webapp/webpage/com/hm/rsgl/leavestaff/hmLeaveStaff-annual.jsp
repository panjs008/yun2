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
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="hmLeaveStaffController.do?doAnnual" tiptype="1">
					<input id="id" name="id" type="hidden" value="${param.id }"/>
	  <input id="applyDate" name="applyDate" type="hidden" value="${hmLeaveStaffPage.applyDate }"/>
	  <input id="monthDay" name="monthDay" type="hidden" value="${monthDay}"/>

	  <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
		  <tr>
			  <td align="right">
				  <label class="Validform_label">
					  结算日期:
				  </label>
			  </td>
			  <td class="value">
				  <input id="annualDate" name="annualDate" readonly  type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'applyDate\');}',maxDate:'#F{$dp.$D(\'monthDay\');}'})"  style="width: 150px" class="Wdate"  ignore="ignore"  value='${(hmLeaveStaffPage.annualDate ne null && hmLeaveStaffPage.annualDate ne '') ? hmLeaveStaffPage.annualDate:annualDate}'/>
				  <span class="Validform_checktip"></span>
				  <label class="Validform_label" style="display: none;">结算日期</label>
			  </td>
		  </tr>
	  </table>

  </t:formvalid>
 </body>
  <script src = "webpage/com/hm/rsgl/leavestaff/hmLeaveStaff.js"></script>		

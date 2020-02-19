<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="hmStaffList" checkbox="true" pagination="true" sortName="deptCode,workCode" sortOrder="asc" pageSize="20" fitColumns="true" title="" actionUrl="hmStaffController.do?datagrid&state=0" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="工号"  query="true" field="workNo"  queryMode="single"  width="90"></t:dgCol>
      <t:dgCol title="姓名"  query="true" field="realName"  queryMode="single"  width="100"></t:dgCol>
      <t:dgCol title="部门"  query="true" field="deptName"  queryMode="single"  width="170"></t:dgCol>
      <t:dgCol title="部门代码"  hidden="true" field="deptCode"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="车间" query="true" field="workName"  queryMode="single"  width="170"></t:dgCol>
      <t:dgCol title="车间代码" hidden="true"  field="workCode"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="班组" query="true" field="groupName"  queryMode="single"  width="170"></t:dgCol>
      <t:dgCol title="班组代码"  hidden="true"  field="groupCode"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="职务"  field="job"  dictionary="job" queryMode="single"  width="130"></t:dgCol>
      <t:dgCol title="级别"  field="jb"  dictionary="jb" queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="薪酬类别"  field="xclb"  dictionary="xclb" queryMode="single"  width="100"></t:dgCol>
      <t:dgCol title="性别"  field="sex" dictionary="sex"  queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="员工类别"  field="yglb" query="true" dictionary="yglb"  queryMode="single"  width="110"></t:dgCol>
      <t:dgCol title="带工"  field="taker" query="true" dictionary="dg"  queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="固定/临时"  field="workType" dictionary="gdls"  queryMode="single"  width="110"></t:dgCol>
      <t:dgCol title="入职日期"  field="rzDate"  queryMode="single"  width="130"></t:dgCol>
   <t:dgCol title="转正日期"  field="zzDate"  queryMode="single"  width="130"></t:dgCol>
   <t:dgCol title="手机号"  field="telphone"  queryMode="single"  width="140"></t:dgCol>
   <%--<t:dgCol title="身份证号码"  field="idCard"  queryMode="single"  width="120"></t:dgCol>--%>
   <%--<t:dgCol title="出生日期"  field="birthDay"  queryMode="single"  width="130"></t:dgCol>--%>
      <t:dgCol title="月休"  field="sleepDays"  queryMode="single"  width="70"></t:dgCol>

       <t:dgToolBar title="查看" icon="fa fa-search" url="hmStaffController.do?goUpdate" funname="detail" width="1200" height="600"></t:dgToolBar>
      <t:dgToolBar title="导出" icon="fa fa-arrow-circle-right" funname="ExportXls"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/hm/rsgl/staff/hmStaffList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });

 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'hmStaffController.do?upload', "hmStaffList");
}

//导出
function ExportXls() {
	JeecgExcelExport("hmStaffController.do?exportXls","hmStaffList");
}

//模板下载
function ExportXlsByT() {
    window.open("${webRoot}/context/人员信息表.xls");
//	JeecgExcelExport("hmStaffController.do?exportXlsByT","hmStaffList");
}

 </script>
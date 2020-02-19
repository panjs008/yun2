<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="hmStaffList" checkbox="true" pagination="true"  sortOrder="asc" pageSize="20" fitColumns="true" title="" actionUrl="hmStaffController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="工号"  query="true" field="workNo"  queryMode="single"  width="90"></t:dgCol>
      <t:dgCol title="姓名"  query="true" field="realName"  queryMode="single"  width="100"></t:dgCol>
      <t:dgCol title="助记码"  query="true" field="zjm"  queryMode="single"  width="100"></t:dgCol>

      <t:dgCol title="部门"  query="true" field="deptName"  queryMode="single"  width="170"></t:dgCol>
      <t:dgCol title="仓库"   field="storageName"  queryMode="single"  width="170"></t:dgCol>

      <t:dgCol title="部门代码"  hidden="true" field="deptCode"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="性别"  field="sex" dictionary="sex"  queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="入职日期"  field="rzDate"  queryMode="single"  width="130"></t:dgCol>
   <t:dgCol title="转正日期"  field="zzDate"  queryMode="single"  width="130"></t:dgCol>
      <t:dgCol title="离职日期"  field="lzDate"  queryMode="single"  width="130"></t:dgCol>

      <t:dgCol title="联系电话"  field="telphone"  queryMode="single"  width="140"></t:dgCol>

      <t:dgCol title="月休"  field="sleepDays"  queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="基本工资"  field="salary"  queryMode="single"  width="130"></t:dgCol>

      <t:dgCol title="状态"  field="state" formatterjs="setColor" queryMode="single"  width="70"></t:dgCol>

       <t:dgToolBar title="录入" icon="fa fa-plus" url="hmStaffController.do?goAdd&winTitle=录入员工信息" funname="add" width="1200" height="600"></t:dgToolBar>
       <t:dgToolBar title="编辑" icon="fa fa-edit" url="hmStaffController.do?goUpdate&winTitle=编辑员工信息" funname="update" width="1200" height="600"></t:dgToolBar>
       <t:dgToolBar title="删除"  icon="fa fa-remove" url="hmStaffController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
       <t:dgToolBar title="查看" icon="fa fa-search" url="hmStaffController.do?goUpdate" funname="detail" width="1200" height="600"></t:dgToolBar>
      <t:dgToolBar title="导入" icon="fa fa-arrow-circle-left"  funname="add" url="hmStaffController.do?uploadVi" width="500" height="200"></t:dgToolBar>

      <t:dgToolBar title="导出" icon="fa fa-arrow-circle-right" funname="ExportXls"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/hm/rsgl/staff/hmStaffList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });

 function setColor(val,row,index){
     if(val == '0'){
         return '<font color="blue">在职</font>';
     }else if(val == '1'){
         return '<font color="red">离职</font>';
     }
 }
 
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
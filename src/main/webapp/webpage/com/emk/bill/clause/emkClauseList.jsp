<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="emkClauseList" checkbox="false" pagination="true" fitColumns="true" title="" actionUrl="emkClauseController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="条款编号"  field="clauseNum"  queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="内容描述"  field="clauseContent"   queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="备注"  field="remark"  queryMode="single"  width="150"></t:dgCol>
       <t:dgToolBar title="录入" icon="fa fa-plus" operationCode="add" url="emkClauseController.do?goAdd" funname="add" width="1000" height="500"></t:dgToolBar>
       <t:dgToolBar title="编辑" icon="fa fa-edit" operationCode="edit" url="emkClauseController.do?goUpdate" funname="update" width="1000" height="500"></t:dgToolBar>
       <t:dgToolBar title="删除" operationCode="delete" operationCode="delete"  icon="fa fa-remove" url="emkClauseController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
       <t:dgToolBar title="查看" icon="fa fa-search" url="emkClauseController.do?goUpdate" funname="detail"></t:dgToolBar>
       <t:dgToolBar title="导入" icon="fa fa-arrow-circle-left" funname="ImportXls"></t:dgToolBar>
       <t:dgToolBar title="导出" operationCode="exp" icon="fa fa-arrow-circle-right" funname="ExportXls"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/emk/bill/clause/emkClauseList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'emkClauseController.do?upload', "emkClauseList");
}

//导出
function ExportXls() {
	JeecgExcelExport("emkClauseController.do?exportXls","emkClauseList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("emkClauseController.do?exportXlsByT","emkClauseList");
}

 </script>
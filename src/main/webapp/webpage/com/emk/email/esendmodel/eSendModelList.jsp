<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="eSendModelList" checkbox="false" nowrap="false" pagination="true" fitColumns="false" title="" actionUrl="eSendModelController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="模板编号"  field="mbbh" query="true"  queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="标题"  field="title" query="true"  queryMode="single"  width="200"></t:dgCol>
      <t:dgCol title="正文"  field="content"  queryMode="single"  width="700"></t:dgCol>
       <t:dgToolBar title="录入" icon="fa fa-plus" url="eSendModelController.do?goAdd" funname="add"></t:dgToolBar>
       <t:dgToolBar title="编辑" icon="fa fa-edit" url="eSendModelController.do?goUpdate" funname="update"></t:dgToolBar>
       <t:dgToolBar title="删除"  icon="fa fa-remove" url="eSendModelController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
       <t:dgToolBar title="查看" icon="fa fa-search" url="eSendModelController.do?goUpdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/emk/email/esendmodel/eSendModelList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'eSendModelController.do?upload', "eSendModelList");
}

//导出
function ExportXls() {
	JeecgExcelExport("eSendModelController.do?exportXls","eSendModelList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("eSendModelController.do?exportXlsByT","eSendModelList");
}

 </script>
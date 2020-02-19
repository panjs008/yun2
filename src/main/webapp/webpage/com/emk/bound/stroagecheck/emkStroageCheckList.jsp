<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="emkStroageCheckList" checkbox="false" pagination="true" fitColumns="true" title="盘点表" actionUrl="emkStroageCheckController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="盘点单号"  field="checkNo"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="盘点日期"  field="checkDate"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="部门ID"  field="departId"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="部门代码"  field="orgCode"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="备注"  field="remark"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="库存ID"  field="storageId"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="库存名称"  field="storageName"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="金额"  field="money"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="emkStroageCheckController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
       <t:dgToolBar title="录入" icon="fa fa-plus" url="emkStroageCheckController.do?goAdd" funname="add"></t:dgToolBar>
       <t:dgToolBar title="编辑" icon="fa fa-edit" url="emkStroageCheckController.do?goUpdate" funname="update"></t:dgToolBar>
       <t:dgToolBar title="批量删除"  icon="fa fa-remove" url="emkStroageCheckController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
       <t:dgToolBar title="查看" icon="fa fa-search" url="emkStroageCheckController.do?goUpdate" funname="detail"></t:dgToolBar>
       <t:dgToolBar title="导入" icon="fa fa-arrow-circle-left" funname="ImportXls"></t:dgToolBar>
       <t:dgToolBar title="导出" icon="fa fa-arrow-circle-right" funname="ExportXls"></t:dgToolBar>
       <t:dgToolBar title="模板下载" icon="fa fa-arrow-circle-o-down" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/emk/bound/stroagecheck/emkStroageCheckList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'emkStroageCheckController.do?upload', "emkStroageCheckList");
}

//导出
function ExportXls() {
	JeecgExcelExport("emkStroageCheckController.do?exportXls","emkStroageCheckList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("emkStroageCheckController.do?exportXlsByT","emkStroageCheckList");
}

 </script>
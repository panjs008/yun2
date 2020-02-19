<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="emkStorageConnactList" checkbox="false" pagination="true" fitColumns="true" title="" actionUrl="emkStorageConnactController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="部门ID"  field="departId" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="部门代码"  field="orgCode" hidden="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="入库仓库" hidden="true" field="inStorageId"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="入库仓库" query="true"  field="inStorageName"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="出货仓库" hidden="true"  field="outStorageId"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="出货仓库"  query="true" field="outStorageName"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="类型"  field="type" query="true" replace="组合_0,拆卸_1"  queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="单号"  field="connactNo"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="日期"  field="makeDate"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="商品名称"  field="proZnNameB"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="商品ID" hidden="true"  field="proId"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="规格型号"  field="brandB"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="数量"  field="total"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="单价"  field="price"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="备注"  field="remark"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作员ID" hidden="true" field="userId"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作员"  field="realName"  queryMode="single"  width="120"></t:dgCol>
       <t:dgToolBar title="组合" icon="fa fa-plus" url="emkStorageConnactController.do?goAdd&winTitle=录入库存组合" funname="add"></t:dgToolBar>
      <t:dgToolBar title="拆卸" icon="fa fa-cut" url="emkStorageConnactController.do?goAdd2&winTitle=录入库存拆卸" funname="add"></t:dgToolBar>

      <t:dgToolBar title="删除"  icon="fa fa-remove" url="emkStorageConnactController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
       <t:dgToolBar title="查看" icon="fa fa-search" url="emkStorageConnactController.do?goUpdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/emk/bound/storageconnact/emkStorageConnactList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'emkStorageConnactController.do?upload', "emkStorageConnactList");
}

//导出
function ExportXls() {
	JeecgExcelExport("emkStorageConnactController.do?exportXls","emkStorageConnactList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("emkStorageConnactController.do?exportXlsByT","emkStorageConnactList");
}

 </script>
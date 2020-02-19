<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="emkProductPriceList" checkbox="true" singleSelect="true" pagination="true" pageSize="20" sortName="priceNo" sortOrder="asc" fitColumns="true" title="零售价表" actionUrl="emkProductPriceController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="编号"  field="priceNo"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="价格名称"  field="priceName" dictionary="jgmc"  queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="价格"  field="price"  queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="商品ID" hidden="true"  field="productId"  queryMode="single"  width="120"></t:dgCol>
       <t:dgToolBar title="录入"  icon="fa fa-plus" url="emkProductPriceController.do?goAdd" funname="add"></t:dgToolBar>
       <t:dgToolBar title="编辑"  icon="fa fa-edit" url="emkProductPriceController.do?goUpdate" funname="update"></t:dgToolBar>
       <t:dgToolBar title="删除"  icon="fa fa-remove" url="emkProductPriceController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
       <t:dgToolBar title="查看" icon="fa fa-search" url="emkProductPriceController.do?goUpdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/emk/product/productprice/emkProductPriceList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'emkProductPriceController.do?upload', "emkProductPriceList");
}

//导出
function ExportXls() {
	JeecgExcelExport("emkProductPriceController.do?exportXls","emkProductPriceList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("emkProductPriceController.do?exportXlsByT","emkProductPriceList");
}

 </script>
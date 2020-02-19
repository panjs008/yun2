<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="emkPoColorList" checkbox="true" pagination="true" pageSize="20" fitColumns="false" title="" actionUrl="emkPoColorController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="款号"  field="poNumber"  query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="颜色"  field="color" query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="内长"  field="ncSize" query="true" queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="单价"  field="price" queryMode="single"  width="120"></t:dgCol>

      <t:dgToolBar title="录入" icon="fa fa-plus" url="emkPoColorController.do?goAdd&winTitle=录入款号" funname="add"></t:dgToolBar>
       <t:dgToolBar title="编辑" icon="fa fa-edit" url="emkPoColorController.do?goUpdate&winTitle=编辑款号" funname="update"></t:dgToolBar>
       <t:dgToolBar title="删除"  icon="fa fa-remove" url="emkPoColorController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
       <t:dgToolBar title="查看" icon="fa fa-search" url="emkPoColorController.do?goUpdate" funname="detail"></t:dgToolBar>
       <t:dgToolBar title="导入" icon="fa fa-arrow-circle-left" funname="ImportXls"></t:dgToolBar>
       <t:dgToolBar title="导出" icon="fa fa-arrow-circle-right" funname="ExportXls"></t:dgToolBar>
       <t:dgToolBar title="模板下载" icon="fa fa-arrow-circle-o-down" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/emk/product/pocolor/emkPoColorList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 

 //导入
 function ImportXls() {
     $.dialog({
         content: 'url:emkPoColorController.do?upload',
         zIndex: getzIndex(),
         title : '导入款号',
         cache:false,
         lock : true,
         width: 600,
         height: 300
     });

 }

//导出
function ExportXls() {
	JeecgExcelExport("emkPoColorController.do?exportXls","emkPoColorList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("emkPoColorController.do?exportXlsByT","emkPoColorList");
}

 </script>
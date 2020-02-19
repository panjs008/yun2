<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="emkSysParamList" checkbox="false" pageSize="20" pagination="true" fitColumns="false" title="参数查询" actionUrl="emkSysParamController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="参数名称"  field="paramName" query="true" queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="参数值"  field="paramValue"  queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="备注"  field="remark"  queryMode="single"  width="200"></t:dgCol>
   <%--<t:dgCol title="省份"  field="shengFen"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="城市"  field="chengShi"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="片区"  field="pianQu"  queryMode="single"  width="120"></t:dgCol>--%>
       <t:dgToolBar title="录入" icon="fa fa-plus" url="emkSysParamController.do?goAdd&winTitle=录入参数" funname="add"></t:dgToolBar>
       <t:dgToolBar title="编辑" icon="fa fa-edit" url="emkSysParamController.do?goUpdate&winTitle=编辑参数" funname="update"></t:dgToolBar>
       <t:dgToolBar title="删除"  icon="fa fa-remove" url="emkSysParamController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
       <t:dgToolBar title="查看" icon="fa fa-search" url="emkSysParamController.do?goUpdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/emk/system/sysparam/emkSysParamList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'emkSysParamController.do?upload', "emkSysParamList");
}

//导出
function ExportXls() {
	JeecgExcelExport("emkSysParamController.do?exportXls","emkSysParamList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("emkSysParamController.do?exportXlsByT","emkSysParamList");
}

 </script>
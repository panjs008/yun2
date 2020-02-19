<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="emkFileList" checkbox="false" pagination="true" fitColumns="true" title="" actionUrl="emkFileController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="供应商名称"  field="gys"  queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="供应商代码" query="true"  field="gysCode"  queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="上传日期"  field="uploadDate"  queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="文件名称"  field="fileName" query="true"  queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="保存文件名称" hidden="true" field="saveFileName"  queryMode="single"  width="120"></t:dgCol>
   <%--<t:dgCol title="URL"  field="fileNameUrl"  queryMode="single"  width="120"></t:dgCol>--%>
   <t:dgCol title="备注"  field="remark"  queryMode="single"  width="150"></t:dgCol>
       <t:dgToolBar title="录入" icon="fa fa-plus" url="emkFileController.do?goAdd" funname="add"></t:dgToolBar>
       <t:dgToolBar title="编辑" icon="fa fa-edit" url="emkFileController.do?goUpdate" funname="update"></t:dgToolBar>
       <t:dgToolBar title="删除"  icon="fa fa-remove" url="emkFileController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
       <t:dgToolBar title="查看" icon="fa fa-search" url="emkFileController.do?goUpdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/emk/storage/file/emkFileList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'emkFileController.do?upload', "emkFileList");
}

//导出
function ExportXls() {
	JeecgExcelExport("emkFileController.do?exportXls","emkFileList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("emkFileController.do?exportXlsByT","emkFileList");
}

 </script>
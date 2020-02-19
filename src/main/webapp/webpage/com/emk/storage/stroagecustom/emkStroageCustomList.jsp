<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="tools"></t:base>

<t:datagrid name="emkStroageCustomList" checkbox="true" pagination="true" sortName="cusNum" sortOrder="asc" fitColumns="true" title="设置客户" actionUrl="emkStroageCustomController.do?datagrid&stroageId=${param.storageId}" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <%--<t:dgCol title="仓库ID"  field="stroageId"  queryMode="single"  width="120"></t:dgCol>--%>
    <t:dgCol title="客户代码"  field="cusNum"  queryMode="single"  width="80"></t:dgCol>
    <t:dgCol title="客户名称"  field="cusName"  queryMode="single"  width="200"></t:dgCol>
    <t:dgCol title="备注"  field="remark"  queryMode="single"  width="100"></t:dgCol>
    <t:dgToolBar title="录入" icon="fa fa-plus" url="emkStroageCustomController.do?goAdd&storageId=${param.storageId}" funname="add"></t:dgToolBar>
    <t:dgToolBar title="编辑" icon="fa fa-edit" url="emkStroageCustomController.do?goUpdate" funname="update"></t:dgToolBar>
    <t:dgToolBar title="删除"  icon="fa fa-remove" url="emkStroageCustomController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
    <t:dgToolBar title="查看" icon="fa fa-search" url="emkStroageCustomController.do?goUpdate" funname="detail"></t:dgToolBar>
</t:datagrid>
 <script src = "webpage/com/emk/storage/stroagecustom/emkStroageCustomList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'emkStroageCustomController.do?upload', "emkStroageCustomList");
}

//导出
function ExportXls() {
	JeecgExcelExport("emkStroageCustomController.do?exportXls","emkStroageCustomList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("emkStroageCustomController.do?exportXlsByT","emkStroageCustomList");
}

 </script>
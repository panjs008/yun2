<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="emkStorageList" checkbox="false" pagination="true" pageSize="20" fitColumns="false" title="库存查询" actionUrl="emkStorageController.do?khkcdatagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <%--<t:dgCol title="仓库"  field="storageName" query="true" queryMode="single"  width="100"></t:dgCol>--%>
      <%--<t:dgCol title="库位"  field="positionName"  queryMode="single"  width="90"></t:dgCol>--%>
      <t:dgCol title="客户名称"  field="cusName"  query="true" queryMode="single"  width="160"></t:dgCol>

      <t:dgCol title="产品名称"  field="proZnName" formatterjs="formatTotal"  query="true" queryMode="single"  width="130"></t:dgCol>
      <t:dgCol title="产品编号"  field="proNum" query="true"  queryMode="single"  width="140"></t:dgCol>
      <t:dgCol title="规格"  field="standards"  query="true" queryMode="single"  width="90"></t:dgCol>
      <t:dgCol title="型号"  field="brand" query="true"  queryMode="single"  width="140"></t:dgCol>
      <t:dgCol title="单位"  field="unit"  queryMode="single"  width="45"></t:dgCol>

      <t:dgCol title="生产企业"  field="scqy"  queryMode="single"  width="190"></t:dgCol>
      <t:dgCol title="许可证号"  field="lincese"  queryMode="single"  width="140"></t:dgCol>
      <t:dgCol title="注册证号"  field="zcNum"  queryMode="single"  width="140"></t:dgCol>
      <t:dgCol title="生产日期"  field="scDate"  queryMode="single"  width="80"></t:dgCol>
      <%--<t:dgCol title="有效日期"  field="limitDate"  queryMode="single"  width="80"></t:dgCol>--%>
      <%--<t:dgCol title="生产批号"  field="betch"  queryMode="single"  width="110"></t:dgCol>--%>
      <%--<t:dgCol title="储运条件"  field="cytj"  queryMode="single"  width="70"></t:dgCol>--%>
       <%--<t:dgToolBar title="录入" icon="fa fa-plus" operationCode="add" url="emkStorageController.do?goAdd" funname="add"></t:dgToolBar>--%>
       <%--<t:dgToolBar title="编辑" icon="fa fa-edit" operationCode="edit" url="emkStorageController.do?goUpdate" funname="update"></t:dgToolBar>--%>
       <%--<t:dgToolBar title="删除"  icon="fa fa-remove" url="emkStorageController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>--%>
       <t:dgToolBar title="查看" icon="fa fa-search" url="emkStorageController.do?goUpdate" funname="detail" width="800" height="400"></t:dgToolBar>
       <%--<t:dgToolBar title="导入" icon="fa fa-arrow-circle-left" funname="ImportXls"></t:dgToolBar>--%>
       <t:dgToolBar title="导出" icon="fa fa-arrow-circle-right" funname="ExportXls"></t:dgToolBar>
      <%--<t:dgToolBar title="导出" operationCode="exp" icon="fa fa-arrow-circle-right" funname="ExportXls"></t:dgToolBar>--%>

  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/emk/storage/storage/emkStorageList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });

 function formatTotal(val,row,index){
     var s = '';
     if(row.cusName=="合计"){
         return '<span style="color:	#0000FF;">'+row.proZnName+'</span>';
     }else{
         s = row.proZnName;
     }
     return s;
 }
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'emkStorageController.do?upload', "emkStorageList");
}

//导出
function ExportXls() {
	JeecgExcelExport("emkStorageController.do?exportCusXls","emkStorageList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("emkStorageController.do?exportXlsByT","emkStorageList");
}

 </script>
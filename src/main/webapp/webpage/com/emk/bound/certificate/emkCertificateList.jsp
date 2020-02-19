<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="emkCertificateList" checkbox="false" nowrap="true" pagination="true" pageSize="20" fitColumns="true" title="" actionUrl="emkCertificateController.do?datagrid&gysId=${param.gysId}" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="证件名称"  field="certName"  queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="证件号码"  field="certNo"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="发证机关"  field="certSignDept"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="照片"  field="certImageUrl"  image="true" imageSize="60,50" queryMode="single"  width="60"></t:dgCol>

      <t:dgCol title="发证日期"  field="certSignDate"  queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="到期日期"  field="certLimitDate"  queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="备注"  field="remark"  queryMode="single"  width="120"></t:dgCol>
   <%--<t:dgCol title="照片URL"  field="certImageUrl"  queryMode="single"  width="120"></t:dgCol>--%>
   <%--<t:dgCol title="供应商ID"  field="gysId"  queryMode="single"  width="120"></t:dgCol>--%>
       <t:dgToolBar title="录入" icon="fa fa-plus" url="emkCertificateController.do?goAdd&gysId=${param.gysId}&winTitle=录入证件" funname="add"></t:dgToolBar>
       <t:dgToolBar title="编辑" icon="fa fa-edit" url="emkCertificateController.do?goUpdate&winTitle=编辑证件" funname="update"></t:dgToolBar>
       <t:dgToolBar title="删除"  icon="fa fa-remove" url="emkCertificateController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
       <t:dgToolBar title="查看" icon="fa fa-search" url="emkCertificateController.do?goUpdate" funname="detail"></t:dgToolBar>
       <t:dgToolBar title="导入" icon="fa fa-arrow-circle-left" funname="ImportXls"></t:dgToolBar>
       <t:dgToolBar title="导出" icon="fa fa-arrow-circle-right" funname="ExportXls"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/emk/bound/certificate/emkCertificateList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'emkCertificateController.do?upload', "emkCertificateList");
}

//导出
function ExportXls() {
	JeecgExcelExport("emkCertificateController.do?exportXls","emkCertificateList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("emkCertificateController.do?exportXlsByT","emkCertificateList");
}

 </script>
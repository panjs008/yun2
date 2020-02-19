<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="emkYsPayList" checkbox="false" pagination="true" fitColumns="true" title="查询" actionUrl="emkYsPayController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
      <t:dgCol title="主键"  hidden="true" field="id"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="创建人名称"  hidden="true" field="createName"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="创建人登录名称"  hidden="true" field="createBy"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="创建日期"  hidden="true" field="createDate"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="所属部门"  hidden="true" field="sysOrgCode"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="付款单号"  field="yfCheckNo" query="true"  queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="付款日期"  field="yftzDate"  query="true"  queryMode="group"  width="70"></t:dgCol>

      <t:dgCol title="客户名称"  field="cusName"   queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="付款金额"  field="money"   queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="付款方式"  field="paytype"   queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="付款账号"  field="bankAccount"   queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="抹零"  field="clearWs"   queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="经办人"  field="marker"   queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="付款科目"  field="outItem"   queryMode="single"  width="70"></t:dgCol>


      <t:dgToolBar title="录入" icon="fa fa-plus" operationCode="add" url="emkYsPayController.do?goAdd&winTitle=录入收款" funname="add" height="600" width="1350"></t:dgToolBar>
      <t:dgToolBar title="编辑" icon="fa fa-edit" operationCode="edit" url="emkYsPayController.do?goUpdate&winTitle=编辑收款" funname="update" height="600" width="1350"></t:dgToolBar>

      <t:dgToolBar title="删除" operationCode="delete"  icon="fa fa-remove" url="emkYsPayController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
      <t:dgToolBar title="导出" operationCode="exp" icon="fa fa-arrow-circle-right" funname="ExportXls"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/emk/caiwu/yspay/emkYsPayList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
     $("#emkFinanceYfCheckListtb").find("input[name='yftzDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
     $("#emkFinanceYfCheckListtb").find("input[name='yftzDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'emkYsPayController.do?upload', "emkYsPayList");
}

//导出
function ExportXls() {
	JeecgExcelExport("emkYsPayController.do?exportXls","emkYsPayList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("emkYsPayController.do?exportXlsByT","emkYsPayList");
}

 </script>
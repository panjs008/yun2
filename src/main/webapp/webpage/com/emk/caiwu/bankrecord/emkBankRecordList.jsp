<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="emkBankRecordList" checkbox="false" pageSize="20" sortOrder="desc" sortName="dealDate" pagination="true" fitColumns="false" title="现金银行" actionUrl="emkBankRecordController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="交易日期"  field="dealDate" query="true" queryMode="group"  width="140"></t:dgCol>
   <t:dgCol title="仓库ID" hidden="true" field="storageId"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="仓库名称"  field="storageName" query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="账户"  field="bankAccount"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="摘要"  field="remark" query="true"  queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="收入"  field="income"  queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="支出"  field="outcome"  queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="余额"  field="balance"  queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="转账方"  field="transfer"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="转账人ID" hidden="true" field="transferId"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="备注"  field="beizhu"  queryMode="single"  width="90"></t:dgCol>
      <t:dgToolBar title="编辑" icon="fa fa-edit" operationCode="edit" url="emkBankRecordController.do?goUpdate&winTitle=编辑" funname="update" ></t:dgToolBar>
      <t:dgToolBar title="删除"  icon="fa fa-remove" url="emkBankRecordController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
       <t:dgToolBar title="查看" icon="fa fa-search" url="emkBankRecordController.do?goUpdate" funname="detail"></t:dgToolBar>
       <t:dgToolBar title="导入" icon="fa fa-arrow-circle-left" funname="ImportXls"></t:dgToolBar>
       <t:dgToolBar title="导出" icon="fa fa-arrow-circle-right" funname="ExportXls"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/emk/caiwu/bankrecord/emkBankRecordList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
   $("#emkBankRecordListtb").find("input[name='dealDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
   $("#emkBankRecordListtb").find("input[name='dealDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});

 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'emkBankRecordController.do?upload', "emkBankRecordList");
}

//导出
function ExportXls() {
	JeecgExcelExport("emkBankRecordController.do?exportXls","emkBankRecordList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("emkBankRecordController.do?exportXlsByT","emkBankRecordList");
}

 </script>
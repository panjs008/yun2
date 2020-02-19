<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="emkFinanceYfCheckList" checkbox="false" pagination="true" fitColumns="true" title="查询" actionUrl="emkFinanceYfCheckController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
      <t:dgCol title="主键"  hidden="true" field="id"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="创建人名称"  hidden="true" field="createName"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="创建人登录名称"  hidden="true" field="createBy"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="创建日期"  hidden="true" field="createDate"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="所属部门"  hidden="true" field="sysOrgCode"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="付款单号"  field="yfCheckNo" query="true"  queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="付款日期"  field="yftzDate"  query="true"  queryMode="group"  width="70"></t:dgCol>

      <t:dgCol title="供应商"  field="gys"   queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="付款金额"  field="money"   queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="付款方式"  field="paytype"   queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="付款账号"  field="bankAccount"   queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="抹零"  field="clearWs"   queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="经办人"  field="marker"   queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="付款科目"  field="outItem"   queryMode="single"  width="70"></t:dgCol>


      <t:dgToolBar title="录入" icon="fa fa-plus" operationCode="add" url="emkFinanceYfCheckController.do?goAdd&winTitle=录入收款" funname="add" height="600" width="1350"></t:dgToolBar>
      <t:dgToolBar title="编辑" icon="fa fa-edit" operationCode="edit" url="emkFinanceYfCheckController.do?goUpdate&winTitle=编辑收款" funname="update" height="600" width="1350"></t:dgToolBar>

      <t:dgToolBar title="删除" operationCode="delete"  icon="fa fa-remove" url="emkFinanceYfCheckController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
      <t:dgToolBar title="导出" operationCode="exp" icon="fa fa-arrow-circle-right" funname="ExportXls"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/emk/caiwu/yfcheck/emkFinanceYfCheckList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
     $("#emkFinanceYfCheckListtb").find("input[name='yftzDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
     $("#emkFinanceYfCheckListtb").find("input[name='yftzDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});

 });

 function formatColor(val,row){
     if(row.state=="1"){
         return '<span style="color:	#0000FF;">已提交</span>';
     }else if(row.state=="2"){
         return '<span style="color:	#00FF00;">完成</span>';
     }else{
         return '创建';
     }
 }

 function doSubmitV() {
     var rowsData = $('#emkFinanceYfCheckList').datagrid('getSelections');
     var ids = [];
     if (!rowsData || rowsData.length == 0) {
         tip('请选择需要提交的收款');
         return;
     }
     for ( var i = 0; i < rowsData.length; i++) {
         ids.push(rowsData[i].id);
     }
     $.dialog.confirm('您是否确定提交收款?', function(r) {
         if (r) {
             $.ajax({
                 url : "emkFinanceYfCheckController.do?doSubmit&ids="+ids,
                 type : 'post',
                 cache : false,
                 data: null,
                 success : function(data) {
                     var d = $.parseJSON(data);
                     tip(d.msg);
                     if (d.success) {
                         $('#emkFinanceYfCheckList').datagrid('reload');
                     }
                 }
             });
         }
     });
 }
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'emkFinanceYfCheckController.do?upload', "emkFinanceYfCheckList");
}

//导出
function ExportXls() {
	JeecgExcelExport("emkFinanceYfCheckController.do?exportXls","emkFinanceYfCheckList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("emkFinanceYfCheckController.do?exportXlsByT","emkFinanceYfCheckList");
}

 </script>
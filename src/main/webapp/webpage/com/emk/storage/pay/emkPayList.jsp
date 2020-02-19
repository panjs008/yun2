<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="emkPayList" checkbox="false" pagination="true" sortName="payDate" sortOrder="desc" fitColumns="false" title="" actionUrl="emkPayController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="订单ID"  field="orderId" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="订单号"  field="orderNo"  query="true" queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="日期"  field="payDate"  query="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="金额"  field="money"  queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="方式"  field="type"  dictionary="paytype" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="备注"  field="remark"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="状态"  field="state" formatterjs="formatColor"  queryMode="single"  width="120"></t:dgCol>

      <t:dgToolBar title="录入" operationCode="add" icon="fa fa-plus" url="emkPayController.do?goAdd&winTitle=录入付款信息" funname="add" width="1200"></t:dgToolBar>
       <t:dgToolBar title="编辑" operationCode="edit" icon="fa fa-edit" url="emkPayController.do?goUpdate&winTitle=编辑付款信息" funname="update" width="1200"></t:dgToolBar>
       <t:dgToolBar title="删除" operationCode="delete"  icon="fa fa-remove" url="emkPayController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
       <t:dgToolBar title="查看" operationCode="look" icon="fa fa-search" url="emkPayController.do?goUpdate" funname="detail" width="1200"></t:dgToolBar>
      <t:dgToolBar title="提交"   icon="fa fa-arrow-circle-up"  operationCode="submit" funname="doSubmitV"></t:dgToolBar>
      <t:dgToolBar title="打印" icon="fa fa-print" url="emkEnquiryController.do?goUpdate" funname="printPreview"></t:dgToolBar>
      <%--<t:dgToolBar title="导出" icon="fa fa-arrow-circle-right" funname="ExportXls"></t:dgToolBar>--%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/emk/storage/pay/emkPayList.js"></script>		
 <script type="text/javascript">

 $(document).ready(function(){
     $("#emkPayListtb").find("input[name='payDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
     $("#emkPayListtb").find("input[name='payDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 function formatColor(val,row){
     if(row.state=="1"){
         return '<span style="color:	#0000FF;">已提交</span>';
     }else if(row.state=="2"){
         return '<span style="color:	#00FF00;">完成</span>';
     }else if(row.state=="3"){
         return '<span style="color:	#FF0000;">退回</span>';
     }else if(row.state=="4"){
         return '<span style="color:	#0000FF;">工厂确认</span>';
     }else{
         return '创建';
     }
 }

 function printPreview(){
     width = 1200;
     height =window.top.document.body.offsetHeight-100;
     var month =  $("#emkPayListtb").find("input[name='month']").val();
     /*var deptName = $("#hmSalaryListtb").find("input[name='deptName']").val();
      var workName = $("#hmSalaryListtb").find("input[name='workName']").val();
      var groupName = $("#hmSalaryListtb").find("input[name='groupName']").val();
      var realName = $("#hmSalaryListtb").find("input[name='realName']").val();
      var taker = $("#hmSalaryListtb").find("option:selected").val();*/
     var rowsData = $('#emkPayList').datagrid('getSelections');
     var ids = [];
     if (!rowsData || rowsData.length == 0) {
         tip('请选择需要的报单');
         return;
     }
     openwindow("打印预览",'emkEnquiryController.do?jump&r=qtPrintSheetFrm&url=emkPayController.do?printPreview&id='+rowsData[0].id,"emkPayList",width,height);
 }

 function doSubmitV() {
     var rowsData = $('#emkPayList').datagrid('getSelections');
     var ids = [];
     if (!rowsData || rowsData.length == 0) {
         layer.msg('请选择需要提交的付款信息');
         return;
     }
     for ( var i = 0; i < rowsData.length; i++) {
         ids.push(rowsData[i].id);
     }
     $.dialog.confirm('您是否确定提交付款信息?', function(r) {
         if (r) {
             var index = layer.load(1, {
                 skin:"layui-layer-sys1",
                 shade: [0.1,'#fff'] //0.1透明度的白色背景
             });
             $.ajax({
                 url : "emkPayController.do?doSubmit&ids="+ids,
                 type : 'post',
                 cache : false,
                 data: null,
                 success : function(data) {
                     var d = $.parseJSON(data);
                     layer.msg(d.msg);
                     if (d.success) {
                         $('#emkPayList').datagrid('reload');
                     }
                     layer.close(index);
                 }
             });
         }
     });
 }
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'emkPayController.do?upload', "emkPayList");
}

//导出
function ExportXls() {
	JeecgExcelExport("emkPayController.do?exportXls","emkPayList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("emkPayController.do?exportXlsByT","emkPayList");
}

 </script>
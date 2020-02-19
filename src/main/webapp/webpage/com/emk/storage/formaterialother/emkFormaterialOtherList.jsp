<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="emkFormaterialOtherList" pageSize="20" checkbox="false" pagination="true" fitColumns="false" title="" actionUrl="emkFormaterialOtherController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"   queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy" hidden="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="订单号"  field="orderNo" query="true"  queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="工厂"  field="gys"  queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="发放日期"  field="ffDate"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="合计"  field="hj"  queryMode="single"  width="100"></t:dgCol>
      <t:dgCol title="状态" formatterjs="formatColor"  field="state"  queryMode="single"  width="90"></t:dgCol>

      <t:dgToolBar title="录入" icon="fa fa-plus" operationCode="add" url="emkFormaterialOtherController.do?goAdd&winTitle=录入其他物料发放" funname="add"></t:dgToolBar>
       <t:dgToolBar title="编辑" icon="fa fa-edit" operationCode="edit" url="emkFormaterialOtherController.do?goUpdate&winTitle=编辑其他物料发放" funname="update"></t:dgToolBar>
       <t:dgToolBar title="删除"  icon="fa fa-remove" operationCode="delete" url="emkFormaterialOtherController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
       <t:dgToolBar title="查看" icon="fa fa-search" url="emkFormaterialOtherController.do?goUpdate" funname="detail"></t:dgToolBar>
      <t:dgToolBar title="提交" operationCode="submit" icon="fa fa-arrow-circle-up"  funname="doSubmitV"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/emk/storage/formaterialother/emkFormaterialOtherList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
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
 function doSubmitV() {
     var rowsData = $('#emkFormaterialOtherList').datagrid('getSelections');
     var ids = [];
     if (!rowsData || rowsData.length == 0) {
         layer.msg('请选择需要提交的其他物料发放');
         return;
     }
     for ( var i = 0; i < rowsData.length; i++) {
         ids.push(rowsData[i].id);
     }
     $.dialog.confirm('您是否确定提交其他物料发放?', function(r) {
         if (r) {
             $.ajax({
                 url : "emkFormaterialOtherController.do?doSubmit&ids="+ids,
                 type : 'post',
                 cache : false,
                 data: null,
                 success : function(data) {
                     var d = $.parseJSON(data);
                     layer.msg(d.msg);
                     if (d.success) {
                         $('#emkFormaterialOtherList').datagrid('reload');
                     }
                 }
             });
         }
     });
 }
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'emkFormaterialOtherController.do?upload', "emkFormaterialOtherList");
}

//导出
function ExportXls() {
	JeecgExcelExport("emkFormaterialOtherController.do?exportXls","emkFormaterialOtherList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("emkFormaterialOtherController.do?exportXlsByT","emkFormaterialOtherList");
}

 </script>
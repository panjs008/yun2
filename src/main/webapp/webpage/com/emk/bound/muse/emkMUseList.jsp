<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="emkMUseList" checkbox="true" pagination="true" fitColumns="true" title="登记表查询" actionUrl="emkMUseController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="登记单号"  field="ckNo" query="true" queryMode="single"  width="90"></t:dgCol>
      <t:dgCol title="客户名称"  query="true" field="cusName"  queryMode="single"  width="160"></t:dgCol>
      <t:dgCol title="客户编号" field="cusNum"  queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="登记人"  field="appler"  queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="医院名称"  query="true" field="hospitalName"  queryMode="single"  width="180"></t:dgCol>
        <t:dgCol title="病人姓名"  field="patient"  queryMode="single"  width="70"></t:dgCol>
        <t:dgCol title="手术时间"  field="operationDate"  query="true" queryMode="group"  width="80"></t:dgCol>
      <t:dgCol title="产品编号" query="true"  hidden="true"   field="proNum" queryMode="single" width="100"></t:dgCol>
      <t:dgCol title="手术医生"  field="operationDc"  queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="发票号码"  field="kpNum"  queryMode="single"  width="90"></t:dgCol>
      <t:dgCol title="开票单位"  field="kpUnit"  queryMode="single"  width="65"></t:dgCol>
      <t:dgCol title="开票金额"  field="kpMoney"  queryMode="single"  width="65"></t:dgCol>
      <t:dgCol title="厂家回款时间"  field="cjhkMoney"  queryMode="single"  width="85"></t:dgCol>

      <t:dgCol title="状态"  field="state" formatterjs="formatColor"  queryMode="single"  width="60"></t:dgCol>

      <t:dgCol title="登记人ID"  hidden="true" field="applerId"  queryMode="single"  width="120"></t:dgCol>
       <t:dgToolBar title="录入" icon="fa fa-plus" url="emkMUseController.do?goAdd&winTitle=添加使用登记表" funname="add"></t:dgToolBar>
       <t:dgToolBar title="编辑" icon="fa fa-edit" url="emkMUseController.do?goUpdate&winTitle=编辑使用登记表" funname="update"></t:dgToolBar>
      <t:dgToolBar title="提交"  icon="fa fa-arrow-circle-up" funname="doSubmitV"></t:dgToolBar>
      <t:dgToolBar title="删除"  icon="fa fa-remove" url="emkMUseController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
       <t:dgToolBar title="查看" icon="fa fa-search" url="emkMUseController.do?goUpdate" funname="detail"></t:dgToolBar>
      <t:dgToolBar title="导出" operationCode="exp" icon="fa fa-arrow-circle-right" funname="ExportXls"></t:dgToolBar>
      <%--<t:dgToolBar title="导入" icon="fa fa-arrow-circle-left" funname="ImportXls"></t:dgToolBar>--%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/emk/bound/muse/emkMUseList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
     $("#emkMUseListtb").find("input[name='operationDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
     $("#emkMUseListtb").find("input[name='operationDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });


 function formatColor(val,row){
     if(row.state=="1"){
         return '<span style="color:	#0000FF;">已提交</span>';
     }else{
         return '创建';
     }
 }
 function doSubmitV() {
     var rowsData = $('#emkMUseList').datagrid('getSelections');
     var ids = [];
     if (!rowsData || rowsData.length == 0) {
         tip('请选择需要提交的使用登记表');
         return;
     }
     for ( var i = 0; i < rowsData.length; i++) {
         ids.push(rowsData[i].id);
     }

     $.dialog.confirm('您是否确定提交使用登记表?', function(r) {
         if (r) {
             var index = layer.load(1, {
                 skin:"layui-layer-sys1",
                 shade: [0.1,'#fff'] //0.1透明度的白色背景
             });
             $.ajax({
                 url : "emkMUseController.do?doSubmit&ids="+ids,
                 type : 'post',
                 cache : false,
                 data: null,
                 success : function(data) {
                     layer.close(index);
                     var d = $.parseJSON(data);
                     if (d.success) {
                         layer.msg(d.msg);
                         $('#emkMUseList').datagrid('reload');
                     }else{
                         layer.alert(d.msg);
                     }

                 }
             });
         }
     });
 }


 //导入
function ImportXls() {
	openuploadwin('Excel导入', 'emkMUseController.do?upload', "emkMUseList");
}

//导出
function ExportXls() {
	JeecgExcelExport("emkMUseController.do?exportXls","emkMUseList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("emkMUseController.do?exportXlsByT","emkMUseList");
}

 </script>
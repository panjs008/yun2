<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="main_list" class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="emkMaterialPactList" checkbox="true" pagination="true" fitColumns="false" title="" actionUrl="emkMaterialPactController.do?datagrid&type=2&flag=0" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <%--<t:dgCol title="操作" field="opt" frozenColumn="true"  width="100"></t:dgCol>--%>
    <%--  <t:dgCol title="采购合同编号"  field="materialNo" queryMode="single" query="true"  width="125"></t:dgCol>
      <t:dgCol title="合同编号"  field="htNum" queryMode="single" width="120"></t:dgCol>--%>
      <t:dgCol title="订单号"  field="orderNum" queryMode="single" width="105"></t:dgCol>
      <t:dgCol title="提交日期"  field="kdDate"  queryMode="single"  width="80"></t:dgCol>
      <%--<t:dgCol title="交货日期"  field="dhjqDate"  queryMode="single"  width="80"></t:dgCol>--%>
      <%--<t:dgCol title="跟单员"  field="developer"  queryMode="single"  width="70"></t:dgCol>--%>
      <t:dgCol title="客户代码" query="true" field="cusNum"  queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="客户名称" query="true" field="cusName"  queryMode="single"  width="220"></t:dgCol>
      <t:dgCol title="业务部门"  field="businesseDeptName"  queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="业务员" query="true" field="businesserName"  queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="业务跟单员" query="true" field="tracerName"  queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="生产跟单员" query="true" field="developerName"  queryMode="single"  width="80"></t:dgCol>

      <t:dgCol title="款号"  field="sampleNo"  queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="工艺种类"  field="gyzl"  dictionary="gylx" queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="款式大类"  field="proTypeName"  queryMode="single"  width="70"></t:dgCol>

      <%--<t:dgFunOpt funname="queryDetail2(id,materialNo)" title="包装辅料" urlclass="ace_button" urlfont="fa-list-alt"></t:dgFunOpt>--%>
      <t:dgCol title="状态"  field="state" formatterjs="formatColor"  queryMode="single"  width="70"></t:dgCol>
      <%--<t:dgFunOpt funname="goToProcess(id,createBy,createName)" title="流程进度" operationCode="process" urlclass="ace_button"  urlStyle="background-color:#ec4758;" urlfont="fa-tasks"></t:dgFunOpt>--%>
      <t:dgToolBar title="录入" icon="fa fa-plus" operationCode="add" url="emkMaterialPactController.do?goAdd&type=2&winTitle=录入包装预采购合同单" funname="add" height="600" width="1350"></t:dgToolBar>
      <t:dgToolBar title="查看" icon="fa fa-search" operationCode="look" url="emkMaterialPactController.do?goUpdate&type=2&winTitle=查看包装预采购合同单" funname="detail" height="600" width="1350"></t:dgToolBar>
      <t:dgToolBar title="编辑" icon="fa fa-edit" operationCode="edit" url="emkMaterialPactController.do?goUpdate&type=2&winTitle=编辑包装预采购合同单" funname="update" height="600" width="1350"></t:dgToolBar>
      <%--<t:dgToolBar title="提交" operationCode="submit" icon="fa fa-arrow-circle-right" funname="doSubmitV"></t:dgToolBar>--%>
      <t:dgToolBar title="生成合同" operationCode="submit" icon="fa fa-arrow-circle-right" funname="doGenerateV"></t:dgToolBar>

      <%--<t:dgToolBar title="录入" icon="fa fa-plus" operationCode="add" url="emkMaterialPactController.do?goAdd&type=0&winTitle=录入原料预采购合同单" funname="add" height="600" width="1000"></t:dgToolBar>--%>
   <t:dgToolBar title="删除" operationCode="delete"  icon="fa fa-remove" url="emkMaterialPactController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
      <t:dgToolBar title="导出" operationCode="exp" icon="fa fa-arrow-circle-right" funname="ExportXls"></t:dgToolBar>
      <t:dgToolBar title="导出PDF" icon="fa fa-arrow-circle-right"  funname="doPrintPDF"></t:dgToolBar>

  </t:datagrid>
  </div>
 </div>

 <script src = "webpage/com/emk/bill/materialpact/emkMaterialPactList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });

 function queryDetail2(id,proName){
      var rowsData = $('#emkMaterialPactList').datagrid('getSelections');

      var title = "包装辅料清单："+proName+","+rowsData[0].cusName;
      if(li_east == 0 || $('#main_list').layout('panel','east').panel('options').title != title){
       $('#main_list').layout('expand','east');
      }
      $('#main_list').layout('panel','east').panel('setTitle', title);
      $('#main_list').layout('panel','east').panel('resize', {width: 600});

      $('#proDetialListpanel').panel("refresh", "emkSampleDetailController.do?list&sampleType=order&sampleId=" + id);
 }
 function formatColor(val,row){
     if(row.state=="1"){
         return '<span style="color:	#FF0000;">处理中</span>';
     }else if(row.state=="2"){
         return '<span style="color:	#0000FF;">完成</span>';
     }else{
         return '创建';
     }
 }

 function goToProcess(id,createBy,createName){
     var height =window.top.document.body.offsetHeight*0.85;

     $.ajax({
         url: "flowController.do?getCurrentProcess&tableName=emk_material_pact&title=包装预采购合同单&id=" + id,
         type: 'post',
         cache: false,
         data: null,
         success: function (data) {
             var d = $.parseJSON(data);
             if (d.success) {
                 var msg = d.msg;
                 if(createBy == "${CUR_USER.userName}"){
                     createdetailwindow("流程进度--当前环节：" + msg, "flowController.do?goProcess&processUrl=com/emk/bill/materialpact/emkMaterialPact-process&id=" + id, 1200, height);
                 }else{
                     if (msg == "完成") {
                         createdetailwindow('流程进度--当前环节：' + msg, 'flowController.do?goProcess&processUrl=com/emk/bill/materialpact/emkMaterialPact-process&id=' + id, 1200, height);
                     } else {
                         if(createName == "领导审核" && "${ROLE.rolecode}" == "ywjl") {
                             createwindow("流程进度--当前环节：" + msg, "flowController.do?goProcess&processUrl=com/emk/bill/materialpact/emkMaterialPact-process&id=" + id, 1200, height);
                         }else{
                             createdetailwindow("流程进度--当前环节：" + msg, "flowController.do?goProcess&processUrl=com/emk/bill/materialpact/emkMaterialPact-process&id=" + id, 1200, height);
                         }
                     }
                 }

             }
         }
     });
 }
 function doPrintPDF() {
     var rowsData = $('#emkMaterialPactList').datagrid('getSelections');
     var ids = [];
     if (!rowsData || rowsData.length == 0) {
         tip('请选择需要导出PDF的包装预采购合同单生成合同单');
         return;
     }
     for ( var i = 0; i < rowsData.length; i++) {
         ids.push(rowsData[i].id);
     }
     $.dialog.confirm('您是否确定导出PDF的包装预采购合同单生成合同单?', function(r) {
         if (r) {
             window.location.href = "emkMaterialPactController.do?doPrintPDF&ids="+ids;
         }
     });
 }
 function doSubmitV() {
     var rowsData = $('#emkMaterialPactList').datagrid('getSelections');
     var ids = [];
     if (!rowsData || rowsData.length == 0) {
         tip('请选择需要提交的包装预采购合同单');
         return;
     }
     for ( var i = 0; i < rowsData.length; i++) {
         ids.push(rowsData[i].id);
     }
     $.dialog.confirm('您是否确定提交包装预采购合同单?', function(r) {
         if (r) {
             $.ajax({
                 url : "emkMaterialPactController.do?doSubmit&ids="+ids,
                 type : 'post',
                 cache : false,
                 data: null,
                 success : function(data) {
                     var d = $.parseJSON(data);
                     tip(d.msg);
                     if (d.success) {
                         $('#emkMaterialPactList').datagrid('reload');
                     }
                 }
             });
         }
     });
     function doGenerateV() {
         var rowsData = $('#emkMaterialPactList').datagrid('getSelections');
         var ids = [];
         if (!rowsData || rowsData.length == 0) {
             tip('请选择需要原料预采购合同单生成合同单');
             return;
         }
         for (var i = 0; i < rowsData.length; i++) {
             ids.push(rowsData[i].id);
         }
         $.dialog.confirm('您是否确定提交原料预采购合同单?', function (r) {
             if (r) {
                 $.ajax({
                     url: "emkMaterialPactController.do?doGenerate&type=2&ids=" + ids,
                     type: 'post',
                     cache: false,
                     data: null,
                     success: function (data) {
                         var d = $.parseJSON(data);
                         tip(d.msg);
                         if (d.success) {
                             $('#emkMaterialPactList').datagrid('reload');
                         }
                     }
                 });
             }
         });
     }
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'emkMaterialPactController.do?upload', "emkMaterialPactList");
}

//导出
function ExportXls() {
	JeecgExcelExport("emkMaterialPactController.do?exportXls","emkMaterialPactList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("emkMaterialPactController.do?exportXlsByT","emkMaterialPactList");
}

 </script>
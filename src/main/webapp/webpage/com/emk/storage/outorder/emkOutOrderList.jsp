<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="emkOutOrderList" checkbox="false" pageSize="20" sortOrder="desc" sortName="outDate" pagination="true" fitColumns="false" title="" actionUrl="emkOutOrderController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="订单号" query="true"   field="orderNo"  queryMode="single"  width="200"></t:dgCol>
      <t:dgCol title="供应商名称" query="true"  field="gys"  queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="出货日期"  field="outDate" query="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="出货批次"  field="betch"  queryMode="single"  width="120"></t:dgCol>

      <t:dgCol title="状态" formatterjs="formatColor"  field="state"  queryMode="single"  width="90"></t:dgCol>
        <c:if test="${ROLE.rolecode eq 'gc'}">
          <t:dgToolBar title="录入" icon="fa fa-plus" operationCode="add" url="emkOutOrderController.do?goAdd&winTitle=录入出货" funname="add" width="1200"></t:dgToolBar>
           <t:dgToolBar title="编辑" icon="fa fa-edit" operationCode="edit" url="emkOutOrderController.do?goUpdate&winTitle=编辑出货" funname="update" width="1200"></t:dgToolBar>
           <t:dgToolBar title="删除"  icon="fa fa-remove" operationCode="delete" url="emkOutOrderController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
        </c:if>
       <t:dgToolBar title="查看" icon="fa fa-search" operationCode="look" url="emkOutOrderController.do?goUpdate" funname="detail" width="1200"></t:dgToolBar>
      <c:if test="${ROLE.rolecode eq 'gc'}">
          <t:dgToolBar title="导入" icon="fa fa-arrow-circle-left" operationCode="imp" funname="ImportXls"></t:dgToolBar>
          <t:dgToolBar title="提交"  icon="fa fa-arrow-circle-up"  operationCode="submit" funname="doSubmitV"></t:dgToolBar>
          <t:dgToolBar title="追回"  icon="fa fa-arrow-circle-down"  operationCode="submit" funname="doBack"></t:dgToolBar>
      </c:if>
      <t:dgToolBar title="打印" icon="fa fa-print" url="emkOutOrderController.do?goUpdate" funname="printPreview"></t:dgToolBar>
      <t:dgToolBar title="模板下载" icon="fa fa-arrow-circle-o-down" funname="ExportXlsByT"></t:dgToolBar>

  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/emk/storage/outorder/emkOutOrderList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
     $("#emkOutOrderListtb").find("input[name='outDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
     $("#emkOutOrderListtb").find("input[name='outDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });

 //导入
 function ImportXls() {
     $.dialog({
         content: 'url:emkOutOrderController.do?upload',
         zIndex: getzIndex(),
         title : '导入出货',
         cache:false,
         lock : true,
         width: 800,
         height: 400
     });

 }

 function doBack() {
     var rowsData = $('#emkOutOrderList').datagrid('getSelections');
     var ids = [];
     if (!rowsData || rowsData.length == 0) {
         layer.msg('请选择需要追回的出货');
         return;
     }
     for ( var i = 0; i < rowsData.length; i++) {
         ids.push(rowsData[i].id);
     }
     $.dialog.confirm('您是否确定追回出货?', function(r) {
         if (r) {
             var index = layer.load(1, {
                 skin:"layui-layer-sys1",
                 shade: [0.1,'#fff'] //0.1透明度的白色背景
             });
             $.ajax({
                 url : "emkOutOrderController.do?doBack&id="+ids,
                 type : 'post',
                 cache : false,
                 data: null,
                 success : function(data) {
                     var d = $.parseJSON(data);
                     layer.msg(d.msg);
                     if (d.success) {
                         $('#emkOutOrderList').datagrid('reload');
                     }
                     layer.close(index);
                 }
             });
         }
     });
 }

 function doSubmitV() {
     var rowsData = $('#emkOutOrderList').datagrid('getSelections');
     var ids = [];
     if (!rowsData || rowsData.length == 0) {
         layer.msg('请选择需要提交的出货');
         return;
     }
     for ( var i = 0; i < rowsData.length; i++) {
         ids.push(rowsData[i].id);
     }
     $.dialog.confirm('您是否确定提交出货?', function(r) {
         if (r) {
             var index = layer.load(1, {
                 skin:"layui-layer-sys1",
                 shade: [0.1,'#fff'] //0.1透明度的白色背景
             });
             $.ajax({
                 url : "emkOutOrderController.do?doSubmit&ids="+ids,
                 type : 'post',
                 cache : false,
                 data: null,
                 success : function(data) {
                     var d = $.parseJSON(data);
                     layer.msg(d.msg);
                     if (d.success) {
                         $('#emkOutOrderList').datagrid('reload');
                     }
                     layer.close(index);
                 }
             });
         }
     });
 }
 function formatColor(val,row){
     if(row.state=="1"){
         return '<span style="color:	#0000FF;">已提交</span>';
     }else if(row.state=="2"){
         return '<span style="color:	#00FF00;">完成</span>';
     }else if(row.state=="3"){
         return '<span style="color:	#FF0000;">退回</span>';
     }else if(row.state=="4"){
         return '<span style="color:	#0000FF;">验货通过</span>';
     }else{
         return '创建';
     }
 }
 function printPreview(){
     width = 1200;
     height =window.top.document.body.offsetHeight-100;

     var rowsData = $('#emkOutOrderList').datagrid('getSelections');
     var ids = [];
     if (!rowsData || rowsData.length == 0) {
         tip('请选择需要的出货');
         return;
     }
     openwindow("打印预览",'emkEnquiryController.do?jump&r=qtPrintSheetFrm&url=emkOutOrderController.do?printPreview&id='+rowsData[0].id,"emkOutOrderList",width,height);
 }
//导出
function ExportXls() {
	JeecgExcelExport("emkOutOrderController.do?exportXls","emkOutOrderList");
}

 //模板下载
 function ExportXlsByT() {
     window.open("${webRoot}/context/出货模板.xls");
 }

 </script>
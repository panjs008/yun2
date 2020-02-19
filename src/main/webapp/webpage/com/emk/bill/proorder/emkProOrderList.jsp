<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="main_list" class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="emkProOrderList" checkbox="false" singleSelect="true" pagination="true" fitColumns="false" sortName="a01a05a01" sortOrder="desc" title="查询" actionUrl="emkProOrderController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>

      <c:forEach var="list" items="${headcategoryEntities}" varStatus="status">
          <c:if test="${list.code ne 'a01a05a02' && list.code ne 'a01a05a04' }">
              <t:dgCol title="${list.name}"  field="${list.code}" queryMode="single" dictionary="${list.code eq 'a01a05a06' ? 'paytype':''}" query="${list.queryed eq 0 ? 'true':'false'}"  width="${fn:contains(list.name,'单号') ? '120':( list.column_type eq '4' ? '210':(list.column_type eq '0' || list.column_type eq '2') ? '50':'100')}"></t:dgCol>
          </c:if>
          <c:if test="${list.code eq 'a01a05a02'}">
              <t:dgCol title="客户名称"  field="cusName"   query="true" queryMode="single"  width="180"></t:dgCol>
          </c:if>
          <c:if test="${list.code eq 'a01a05a04' }">
              <t:dgCol title="出货仓库"  field="storageName"  query="true" queryMode="single"  width="120"></t:dgCol>
          </c:if>
      </c:forEach>
      <t:dgCol title="商品数量"  field="total"  queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="金额"  field="money"  queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="状态"  field="state" formatterjs="formatColor"  queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="付款状态"  field="payState" formatterjs="formatColor2"  queryMode="single"  width="80"></t:dgCol>

      <t:dgToolBar title="录入" icon="fa fa-plus" operationCode="add" url="emkProOrderController.do?goAdd&winTitle=录入订单" funname="add" height="600" width="1000"></t:dgToolBar>
      <t:dgToolBar title="编辑" icon="fa fa-edit" operationCode="edit" url="emkProOrderController.do?goUpdate&winTitle=编辑订单" funname="update" height="600" width="1000"></t:dgToolBar>

      <%--<t:dgToolBar title="流程进度" operationCode="process" icon="fa fa-plus" funname="goToProcess"></t:dgToolBar>--%>
      <t:dgToolBar title="收款"  icon="fa fa-rmb" operationCode="paysubmit" url="emkProOrderController.do?goPay&winTitle=订单付款" funname="update" height="200" width="400"></t:dgToolBar>
      <%--<t:dgToolBar title="审核" icon="fa fa-plus" funname="doSubmitV" height="600" width="1000"></t:dgToolBar>--%>
      <t:dgToolBar title="删除" operationCode="delete"  icon="fa fa-remove" url="emkProOrderController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
      <t:dgToolBar title="表头列"  icon="fa fa-gear" funname="setHeader"></t:dgToolBar>
      <t:dgToolBar title="列配置"  icon="fa fa-gear" funname="setCommon"></t:dgToolBar>
      <%--<t:dgToolBar title="明细列"  icon="fa fa-gear" funname="setDetail"></t:dgToolBar>--%>


      <%--<t:dgToolBar title="导出" operationCode="exp" icon="fa fa-arrow-circle-right" funname="ExportXls"></t:dgToolBar>
      <t:dgToolBar title="导出PDF" icon="fa fa-arrow-circle-right"  funname="doPrintPDF"></t:dgToolBar>
--%>

  </t:datagrid>
  </div>
 </div>

 <script src = "webpage/com/emk/bill/proorder/emkProOrderList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 function setHeader(){
     $.dialog({
         content: 'url:hmCategoryController.do?for&forColumn&parentCode=A01A05',
         zIndex: getzIndex(),
         title : '表头列配置',
         cache:false,
         lock : true,
         width: 500,
         height: 580,
         cancelVal: '关闭',
         cancel: function(){
             location.reload();
         },
     });
 }
 function setCommon(){
     $.dialog({
         content: 'url:hmCategoryController.do?for&forColumn&parentCode=A01A10',
         zIndex: getzIndex(),
         title : '明细列配置',
         cache:false,
         lock : true,
         width: 500,
         height: 580,
         cancelVal: '关闭',
         cancel: function(){
             location.reload();
         },
     });
 }
 function setDetail(){
     $.dialog({
         content: 'url:hmCategoryController.do?for&forColumn&parentCode=A01A06',
         zIndex: getzIndex(),
         title : '明细列配置',
         cache:false,
         lock : true,
         width: 500,
         height: 580,
         cancelVal: '关闭',
         cancel: function(){
             location.reload();
         },
     });
 }
 function formatColor(val,row){
     if(row.state=="2"){
         return '<span style="color:	#0000FF;">已完成</span>';
     }else if(row.state=="1"){
         return '<span style="color:	#FF0000;">采购处理中</span>';
     }else if(row.state=="0"){
         return '创建';
     }else{
         return '';
     }
 }
 function formatColor2(val,row){
     if(row.payState=="1"){
         return '<span style="color:	#0000FF;">已付款</span>';
     }else if(row.payState=="0"){
         return '<span style="color:	#FF0000;">未付款</span>';
     }
 }
 function doPrintPDF() {
     var rowsData = $('#emkProOrderList').datagrid('getSelections');
     var ids = [];
     if (!rowsData || rowsData.length == 0) {
         tip('请选择需要导出PDF的订单表');
         return;
     }
     for ( var i = 0; i < rowsData.length; i++) {
         ids.push(rowsData[i].id);
     }
     $.dialog.confirm('您是否确定导出PDF的订单表?', function(r) {
         if (r) {
             window.location.href = "emkProOrderController.do?doPrintPDF&ids="+ids;
         }
     });
 }

 function goToProcess(id,createBy,processName,state){
     var height =window.top.document.body.offsetHeight*0.85;
     var processNameVal='',node='';
     if(processName != null){
         if(processName.indexOf('-') > 0){
             processNameVal = processName.substring(0,processName.indexOf('-'));
             node = processName.substring(processName.indexOf('-')+1);
         }
     }
     /*if(createBy == "${CUR_USER.userName}" || ${CUR_USER.userName eq 'admin'}){

     }*/
     if(state == '0'){
         createwindow("流程进度--当前环节：原料面料需求开发申请单", "flowController.do?goProcess&state="+state+"&node="+node+"&processUrl=com/emk/bill/proorder/emkProOrder-process&state="+state+"&id=" + id, 1300, height);
     }else {
         createdetailwindow("流程进度--当前环节：" + processNameVal, "flowController.do?goProcess&state="+state+"&node="+node+"&processUrl=com/emk/bill/proorder/emkProOrder-process&state="+state+"&id=" + id, 1300, height);
     }
 }

 function doSubmitV() {
     var rowsData = $('#emkProOrderList').datagrid('getSelections');
     var ids = [];
     if (!rowsData || rowsData.length == 0) {
         tip('请选择需要提交的订单');
         return;
     }
     for ( var i = 0; i < rowsData.length; i++) {
         ids.push(rowsData[i].id);
     }
     $.dialog.confirm('您是否确定提交订单?', function(r) {
         if (r) {
             $.ajax({
                 url : "emkProOrderController.do?doSubmit&ids="+ids,
                 type : 'post',
                 cache : false,
                 data: null,
                 success : function(data) {
                     var d = $.parseJSON(data);
                     tip(d.msg);
                     if (d.success) {
                         $('#emkProOrderList').datagrid('reload');
                     }
                 }
             });
         }
     });
 }
 function doScProduce() {
     var rowsData = $('#emkProOrderList').datagrid('getSelections');
     var ids = [];
     if (!rowsData || rowsData.length == 0) {
         tip('请选择需要生成的采购生产进度表的订单');
         return;
     }
     for ( var i = 0; i < rowsData.length; i++) {
         ids.push(rowsData[i].id);
     }
     $.dialog.confirm('您是否确定提交生成采购生产进度表?', function(r) {
         if (r) {
             $.ajax({
                 url : "emkProOrderController.do?doScProduce&ids="+ids,
                 type : 'post',
                 cache : false,
                 data: null,
                 success : function(data) {
                     var d = $.parseJSON(data);
                     tip(d.msg);
                     if (d.success) {
                         $('#emkProOrderList').datagrid('reload');
                     }
                 }
             });
         }
     });
 }

 function queryDetail1(id,eNo,state){
     var rowsData = $('#emkProOrderList').datagrid('getSelections');
     var title = "订单明细："+eNo+","+rowsData[0].cusName;
     if(li_east == 0 || $('#main_list').layout('panel','east').panel('options').title != title){
         $('#main_list').layout('expand','east');
     }
     $('#main_list').layout('panel','east').panel('setTitle', title);
     $('#main_list').layout('panel','east').panel('resize', {width: 600});

     $('#proDetialListpanel').panel("refresh", "emkEnquiryDetailController.do?list&state="+state+"&enquiryId=" + id);
 }
 function queryDetail2(id,proName,state){
     var rowsData = $('#emkProOrderList').datagrid('getSelections');

     var title = "主辅料清单："+proName+","+rowsData[0].cusName;
     if(li_east == 0 || $('#main_list').layout('panel','east').panel('options').title != title){
         $('#main_list').layout('expand','east');
     }
     $('#main_list').layout('panel','east').panel('setTitle', title);
     $('#main_list').layout('panel','east').panel('resize', {width: 600});

     $('#proDetialListpanel').panel("refresh", "emkSampleDetailController.do?list&sampleType=order&state="+state+"&sampleId=" + id);
 }
 function queryDetail3(id,proName,state){
     var rowsData = $('#emkProOrderList').datagrid('getSelections');
     var title = "条码："+proName+","+rowsData[0].cusName;
     if(li_east == 0 || $('#main_list').layout('panel','east').panel('options').title != title){
         $('#main_list').layout('expand','east');
     }
     $('#main_list').layout('panel','east').panel('setTitle', title);
     $('#main_list').layout('panel','east').panel('resize', {width: 600});

     $('#proDetialListpanel').panel("refresh", "emkProOrderBarcodeController.do?list&state="+state+"&orderId=" + id);
 }

 function queryDetail4(id,proName,state){
     var rowsData = $('#emkProOrderList').datagrid('getSelections');
     var title = "包装："+proName+","+rowsData[0].cusName;
     if(li_east == 0 || $('#main_list').layout('panel','east').panel('options').title != title){
         $('#main_list').layout('expand','east');
     }
     $('#main_list').layout('panel','east').panel('setTitle', title);
     $('#main_list').layout('panel','east').panel('resize', {width: 600});

     $('#proDetialListpanel').panel("refresh", "emkProOrderPackageController.do?list&state="+state+"&orderId=" + id);
 }

 function queryDetail5(id,proName,state){
     var rowsData = $('#emkProOrderList').datagrid('getSelections');
     var title = "纸箱："+proName +","+rowsData[0].cusName;
     if(li_east == 0 || $('#main_list').layout('panel','east').panel('options').title != title){
         $('#main_list').layout('expand','east');
     }
     $('#main_list').layout('panel','east').panel('setTitle', title);
     $('#main_list').layout('panel','east').panel('resize', {width: 750});

     $('#proDetialListpanel').panel("refresh", "emkProOrderBoxController.do?list&state="+state+"&orderId=" + id);
 }
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'emkProOrderController.do?upload', "emkProOrderList");
}

//导出
function ExportXls() {
	JeecgExcelExport("emkProOrderController.do?exportXls","emkProOrderList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("emkProOrderController.do?exportXlsByT","emkProOrderList");
}

 </script>
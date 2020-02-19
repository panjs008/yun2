<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="main_list" class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="emkEnquiryList" checkbox="false"  pagination="true" sortOrder="desc" sortName="kdDate" pageSize="20" fitColumns="false" title="" actionUrl="emkEnquiryController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="当前流程节点"  field="processName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>

      <t:dgCol title="操作" field="opt" width="100" frozenColumn="true"></t:dgCol>
      <t:dgCol title="报单单号"  field="enquiryNo" query="true" queryMode="single"  width="200"></t:dgCol>

      <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="供应商代码" query="true" field="gysCode"  queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="供应商名称" query="true" field="gys"  queryMode="single"  width="150"></t:dgCol>
     <%-- <t:dgCol title="业务部门"  field="businesseDeptName"  queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="业务员" query="true" field="businesserName"  queryMode="single"  width="70"></t:dgCol>--%>
      <t:dgCol title="款号"  field="sampleNo"  queryMode="single"  width="70"></t:dgCol>
   <%--   <t:dgCol title="描述"  field="sampleNoDesc"  queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="图片"  field="customSampleUrl" imageSize="30,30"  image="true"  queryMode="single"  width="50"></t:dgCol>
--%>
      <t:dgCol title="提交日期" query="true" field="kdDate"  queryMode="group"  width="90"></t:dgCol>
      <t:dgCol title="最晚交货日期"  field="ysDate"  queryMode="single"  width="120"></t:dgCol>
      <%--<t:dgCol title="原样"  field="oldImageUrl" imageSize="30,30"  image="true"  queryMode="single"  width="50"></t:dgCol>--%>

      <%--<t:dgCol title="业务跟单员"  field="tracer"  queryMode="single"  width="90"></t:dgCol>--%>

      <%--<t:dgCol title="工艺种类"  field="gyzl"  dictionary="gylx" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="款式大类"  field="proTypeName"  queryMode="single"  width="70"></t:dgCol>--%>
   <t:dgCol title="数量"  field="sumTotal"  queryMode="single"  width="60"></t:dgCol>
   <%--<t:dgCol title="币种"  field="bz"  queryMode="single"  width="50"></t:dgCol>
   <t:dgCol title="总金额"  field="sumMoney"  queryMode="single"  width="60"></t:dgCol>--%>
      <t:dgCol title="订单状态"  field="state" formatterjs="formatColor"  queryMode="single"  width="120"></t:dgCol>

      <%--<t:dgFunOpt funname="queryDetail1(id,enquiryNo,state)" title="明细" urlclass="ace_button" urlfont="fa-list-alt"></t:dgFunOpt>--%>
      <t:dgFunOpt funname="goToProcess(id,createBy,processName,state,enquiryNo)" title="流程进度" operationCode="process" urlclass="ace_button"  urlStyle="background-color:#ec4758;" urlfont="fa-tasks"></t:dgFunOpt>

      <t:dgToolBar title="录入" icon="fa fa-plus" operationCode="add"  url="emkEnquiryController.do?goAdd&winTitle=录入报单" funname="add" height="650" width="1200"></t:dgToolBar>
       <t:dgToolBar title="编辑" icon="fa fa-edit" operationCode="edit" url="emkEnquiryController.do?goUpdate&winTitle=编辑报单" funname="update" height="650" width="1200"></t:dgToolBar>
      <t:dgToolBar title="查看" icon="fa fa-search" operationCode="look" url="emkEnquiryController.do?goUpdate&winTitle=查看报单" funname="detail" height="650" width="1200"></t:dgToolBar>
      <t:dgToolBar title="提交" operationCode="submit" icon="fa fa-arrow-circle-up"  funname="doSubmitV"></t:dgToolBar>
      <%--<t:dgToolBar title="流程进度" operationCode="process" icon="fa fa-plus"  funname="goToProcess"></t:dgToolBar>--%>

      <t:dgToolBar title="删除" operationCode="delete"  icon="fa fa-remove"  url="emkEnquiryController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
      <t:dgToolBar title="导入" operationCode="imp" icon="fa fa-arrow-circle-left" funname="ImportXls"></t:dgToolBar>
      <t:dgToolBar title="导出" operationCode="exp" icon="fa fa-arrow-circle-right"  funname="ExportXls"></t:dgToolBar>
      <t:dgToolBar title="打印" icon="fa fa-print" url="emkEnquiryController.do?goUpdate" funname="printPreview"></t:dgToolBar>
      <%--<t:dgToolBar title="导出PDF" icon="fa fa-arrow-circle-right"  funname="doPrintPDF"></t:dgToolBar>--%>
      <t:dgToolBar title="模板下载" icon="fa fa-arrow-circle-o-down" funname="ExportXlsByT"></t:dgToolBar>

  </t:datagrid>
  </div>
 </div>
<%--<div data-options="region:'east',
	title:'清单明细',
	collapsed:true,
	split:true,
	border:false,
	onExpand : function(){
		li_east = 1;
	},
	onCollapse : function() {
	    li_east = 0;
	}"
     style="width: 500px; overflow: hidden;" id="eastPanel">
    <div class="easyui-panel" style="padding:0px;border:0px" fit="true" border="false" id="proDetialListpanel"></div>
</div>--%>
 <script src = "webpage/com/emk/storage/enquiry/emkEnquiryList.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
     $("#emkEnquiryListtb").find("input[name='kdDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
     $("#emkEnquiryListtb").find("input[name='kdDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 //导入
 function ImportXls() {
     $.dialog({
         content: 'url:emkEnquiryController.do?upload',
         zIndex: getzIndex(),
         title : '导入报单',
         cache:false,
         lock : true,
         width: 800,
         height: 400
     });

 }
 function formatColor(val,row){
     if(row.state=="1"){
         return '<span style="color:	#0000FF;">已提交</span>';
     }else if(row.state=="2"){
         return '<span style="color:	#00FF00;">完成</span>';
     }else if(row.state=="3"){
         return '<span style="color:	#0000FF;">提交叫布</span>';
     }else if(row.state=="4"){
         return '<span style="color:	#0000FF;">核实通过</span>';
     }else if(row.state=="5"){
         return '<span style="color:	#FF0000;">回退叫布</span>';
     }else if(row.state=="6"){
         return '<span style="color:	#0000FF;">提交其他物料发放</span>';
     }else if(row.state=="7"){
         return '<span style="color:	#0000FF;">工厂确认通过</span>';
     }else if(row.state=="8"){
         return '<span style="color:	#FF0000;">回退其他物料发放</span>';
     }else if(row.state=="9"){
         return '<span style="color:	#0000FF;">提交发货</span>';
     }else if(row.state=="10"){
         return '<span style="color:	#FF0000;">回退发货</span>';
     }else if(row.state=="11"){
         return '<span style="color:	#0000FF;">出货通过退货</span>';
     }else if(row.state=="12"){
         return '<span style="color:	#0000FF;">提交退货</span>';
     }else if(row.state=="13"){
         return '<span style="color:	#FF0000;">回退退货</span>';
     }else if(row.state=="14"){
         return '<span style="color:	#0000FF;">退货通过</span>';
     }else if(row.state=="15"){
         return '<span style="color:	#0000FF;">提交付款</span>';
     }else if(row.state=="16"){
         return '<span style="color:	#FF0000;">回退付款</span>';
     }else if(row.state=="17"){
         return '<span style="color:	#0000FF;">工厂确认付款</span>';
     }else if(row.state=="19"){
         return '<span style="color:	#FF0000;">财务回退付款</span>';
     }else if(row.state=="18"){
         return '<span style="color:	#0000FF;">出货通过付款</span>';
     }else{
         return '创建';
     }
 }

 function goToProcess(id,createBy,processName,state,enquiryNo){
     var height =window.top.document.body.offsetHeight*0.85;
     var processNameVal='',node='';
     if(processName != null){
         if(processName.indexOf('-') > 0){
             processNameVal = processName.substring(0,processName.indexOf('-'));
             node = processName.substring(processName.indexOf('-')+1);
         }
     }
     if(state == '0'){
         createwindow(enquiryNo+"--流程进度--当前环节：", "flowController.do?goProcess&state="+state+"&node="+node+"&processUrl=com/emk/storage/enquiry/emkEnquiry-process&id=" + id, 1320, height);
     }else if(state == '2'){
         createdetailwindow(enquiryNo+"--流程进度--当前环节：完成" , "flowController.do?goProcess&state="+state+"&node="+node+"&processUrl=com/emk/storage/enquiry/emkEnquiry-process&id=" + id, 1320, height);
     }else {
         createdetailwindow(enquiryNo+"--流程进度--当前环节：" + processNameVal, "flowController.do?goProcess&state="+state+"&node="+node+"&processUrl=com/emk/storage/enquiry/emkEnquiry-process&id=" + id, 1320, height);
     }
 }
 function queryDetail1(id,eNo,state){
     $('#emkEnquiryList').datagrid('unselectAll');
     var title = "报单明细："+eNo;
     if(li_east == 0 || $('#main_list').layout('panel','east').panel('options').title != title){
         $('#main_list').layout('expand','east');
     }
     $('#main_list').layout('panel','east').panel('setTitle', title);
     $('#main_list').layout('panel','east').panel('resize', {width: 500});

     $('#proDetialListpanel').panel("refresh", "emkEnquiryDetailController.do?list&state="+state+"&enquiryId=" + id);
 }

 function doSubmitV() {
     var rowsData = $('#emkEnquiryList').datagrid('getSelections');
     var ids = [];
     if (!rowsData || rowsData.length == 0) {
         layer.msg('请选择需要提交的报单');
         return;
     }
     for ( var i = 0; i < rowsData.length; i++) {
         ids.push(rowsData[i].id);
     }
     $.dialog.confirm('您是否确定提交报单?', function(r) {
         if (r) {
             $.ajax({
                 url : "emkEnquiryController.do?doSubmit&ids="+ids,
                 type : 'post',
                 cache : false,
                 data: null,
                 success : function(data) {
                     var d = $.parseJSON(data);
                     layer.msg(d.msg);
                     if (d.success) {
                         $('#emkEnquiryList').datagrid('reload');
                     }
                 }
             });
         }
     });
 }
 function printPreview(){
     width = 1200;
     height =window.top.document.body.offsetHeight-100;
     var month =  $("#emkEnquiryListtb").find("input[name='month']").val();
     /*var deptName = $("#hmSalaryListtb").find("input[name='deptName']").val();
     var workName = $("#hmSalaryListtb").find("input[name='workName']").val();
     var groupName = $("#hmSalaryListtb").find("input[name='groupName']").val();
     var realName = $("#hmSalaryListtb").find("input[name='realName']").val();
     var taker = $("#hmSalaryListtb").find("option:selected").val();*/
     var rowsData = $('#emkEnquiryList').datagrid('getSelections');
     var ids = [];
     if (!rowsData || rowsData.length == 0) {
         tip('请选择需要的报单');
         return;
     }
     openwindow("打印预览",'emkEnquiryController.do?jump&r=qtPrintSheetFrm&url=emkEnquiryController.do?printPreview&id='+rowsData[0].id,"emkEnquiryList",width,height);
 }
 function doPrintPDF() {
     var rowsData = $('#emkEnquiryList').datagrid('getSelections');
     var ids = [];
     if (!rowsData || rowsData.length == 0) {
         tip('请选择需要导出PDF的报单');
         return;
     }
     for ( var i = 0; i < rowsData.length; i++) {
         ids.push(rowsData[i].id);
     }
     $.dialog.confirm('您是否确定导出PDF的报单?', function(r) {
         if (r) {
             window.location.href = "emkEnquiryController.do?doPrintPDF&ids="+ids;
         }
     });
 }

//导出
function ExportXls() {
	JeecgExcelExport("emkEnquiryController.do?exportXls","emkEnquiryList");
}

//模板下载
function ExportXlsByT() {
    window.open("${webRoot}/context/订单明细.xls");
}
 </script>
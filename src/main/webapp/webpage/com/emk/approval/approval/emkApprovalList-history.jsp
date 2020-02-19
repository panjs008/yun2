<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="emkApprovalList" checkbox="false"  pagination="true" fitColumns="false" pageSize="20" title="历史审批业务表" actionUrl="emkApprovalController.do?listAllByJdbc" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="操作" field="opt" width="100" ></t:dgCol>
      <t:dgCol title="单号"  field="workNum"  query="true" queryMode="single"  width="150"></t:dgCol>
      <t:dgCol title="工单类型"  field="type" replace="订单_0,入库申请单_5,销售单_6,出库单_7" queryMode="single"  width="100"></t:dgCol>
      <t:dgCol title="提交人"  field="createName"    queryMode="single"  width="90"></t:dgCol>
      <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>

      <t:dgCol title="申请人ID"  field="commitId" hidden="true" queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="提交时间"  field="commitTime"  queryMode="single"  width="140"></t:dgCol>
      <t:dgCol title="当前节点名称"  field="processName" formatterjs="formatProcessName" queryMode="single"  width="100"></t:dgCol>
      <t:dgCol title="当前节点代码"  field="processNode"  hidden="true" queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="formId"  field="formId"  hidden="true" queryMode="single"  width="120"></t:dgCol>

      <t:dgCol title="节点状态"  field="status" formatterjs="formatColor"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="审核人"  field="bpmSher"  queryMode="single"  width="90"></t:dgCol>
      <t:dgCol title="审核状态"  field="bpmStatus" formatterjs="formatBpmStateColor" queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="审核人ID"  field="bpmSherId"  hidden="true" queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="审核时间"  field="bpmDate"  query="true" queryMode="group"  width="140"></t:dgCol>

      <t:dgFunOpt funname="goToProcess(workNum,formId,commitId,processNode,status,type)" title="流程进度"  urlclass="ace_button"  urlStyle="background-color:#ec4758;" urlfont="fa-tasks"></t:dgFunOpt>
      <%--<t:dgToolBar title="录入" icon="fa fa-plus" url="emkApprovalController.do?goAdd" funname="add"></t:dgToolBar>
      <t:dgToolBar title="编辑" icon="fa fa-edit" url="emkApprovalController.do?goUpdate" funname="update"></t:dgToolBar>
      <t:dgToolBar title="查看" icon="fa fa-search" url="emkApprovalController.do?goUpdate" funname="detail"></t:dgToolBar>
      <t:dgToolBar title="导入" icon="fa fa-arrow-circle-left" funname="ImportXls"></t:dgToolBar>
      <t:dgToolBar title="导出" icon="fa fa-arrow-circle-right" funname="ExportXls"></t:dgToolBar>--%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/emk/approval/approval/emkApprovalList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
     $("#emkApprovalListtb").find("input[name='bpmDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
     $("#emkApprovalListtb").find("input[name='bpmDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });

 function formatColor(val,row){
     if(row.status=="1"){
         return '<span style="color:	#0000FF;">已提交</span>';
     }else if(row.status=="2"){
         return '<span style="color:	#00FF00;">完成</span>';
     }else if(row.status=="24"){
         return '<span style="color:	#0000FF;">采购员通过</span>';
     }else if(row.status == '25'){
         return '<span style="color:	#0000FF;">拣货核实</span>';
     }else if(row.status=="40"){
         return '<span style="color:	#0000FF;">采购员质检通过</span>';
     }else{
         return '创建';
     }
 }
 function formatBpmStateColor(val,row){
     if(row.bpmStatus=="1"){
         return '<span style="color:	#FF0000;">驳回</span>';
     }else{
         return '<span style="color:	#0000FF;">通过</span>';
     }
 }
function formatProcessName(val,row){
    if(row.processNode != null){
        if(row.processNode.indexOf('-') > 0){
            var processNameVal = row.processNode.substring(0,row.processNode.indexOf('-'));
            return processNameVal;
        }
    }
}
 function goToProcess(workNum,id,createBy,processName,state,type){
     var height =window.top.document.body.offsetHeight*0.85;
     var processNameVal='',node='',w=1280;
     var processUrl,initProcessName;
     if(type == '0'){
         processUrl = 'com/emk/storage/enquiry/emkEnquiry-process';
         initProcessName = '业务部审核';
         w = 1320;
     }
     if(type == '5'){
         processUrl = 'com/emk/bound/minstorage/emkMInStorage-process';
         initProcessName = '采购员审核';
         w = 1300;
     }
     if(type == '6' || type == '7'){
         processUrl = 'com/emk/bound/moutstorage/emkMOutStorage-process';
         initProcessName = '采购员审核';
         w = 1300;
     }
     if(processName != null){
         if(processName.indexOf('-') > 0){
             processNameVal = processName.substring(0,processName.indexOf('-'));
             node = processName.substring(processName.indexOf('-')+1);
             initProcessName = processNameVal;
         }
     }
     createdetailwindow(workNum+"--流程进度--当前环节："+initProcessName, "flowController.do?goProcess&node="+node+"&state="+state+"&processUrl="+processUrl+"&id=" + id, w, height);

 }
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'emkApprovalController.do?upload', "emkApprovalList");
}

//导出
function ExportXls() {
	JeecgExcelExport("emkApprovalController.do?exportXls","emkApprovalList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("emkApprovalController.do?exportXlsByT","emkApprovalList");
}

 </script>
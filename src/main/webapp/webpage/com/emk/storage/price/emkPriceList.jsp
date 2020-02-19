<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="main_list" class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="emkPriceList" checkbox="false" pagination="true" sortOrder="desc" sortName="kdDate" fitColumns="false" title="" actionUrl="emkPriceController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
      <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="当前流程节点"  field="processName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>

      <%--<t:dgCol title="操作" field="opt" frozenColumn="true"  width="245"></t:dgCol>--%>
      <%--<t:dgCol title="有效期"  field="limitDate"  queryMode="single"  width="80"></t:dgCol>--%>
      <%--<t:dgCol title="原样"  field="oldImageUrl" imageSize="30,30"  image="true"  queryMode="single"  width="50"></t:dgCol>--%>
      <t:dgCol title="操作" field="opt" width="100" frozenColumn="true"></t:dgCol>
      <t:dgCol title="报价单号"  field="pirceNo" query="true" frozenColumn="true" queryMode="single"  width="105"></t:dgCol>

      <t:dgCol title="客户代码" query="true" field="cusNum"  queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="客户名称" query="true" field="cusName"  queryMode="single"  width="200"></t:dgCol>
      <t:dgCol title="业务部门"  field="businesseDeptName"  queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="业务员"  field="businesserName"  query="true" queryMode="single"  width="60"></t:dgCol>
      <t:dgCol title="款号" query="true"  field="sampleNo"  queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="描述"  field="sampleNoDesc"  queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="图片"  field="customSampleUrl" imageSize="30,30"  image="true"  queryMode="single"  width="50"></t:dgCol>
      <t:dgCol title="成分"  field="weight"  queryMode="single"  width="80"></t:dgCol>
      <%--<t:dgCol title="价格"  field="targetPrice"  queryMode="single"  width="80"></t:dgCol>--%>
      <t:dgCol title="尺码范围"  field="sizeFawei"  queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="最终双方确认价"  field="confirmPrice"  queryMode="single"  width="110"></t:dgCol>
      <t:dgCol title="报价日期"  field="kdDate"  queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="报价有效期"  field="limitDate"  queryMode="single"  width="80"></t:dgCol>

      <t:dgCol title="报价状态"  field="state" formatterjs="formatColor"  queryMode="single"  width="90"></t:dgCol>


      <t:dgCol title="来源"  field="formType" replace="手工创建_0,询盘单生成_1" queryMode="single"  width="80"></t:dgCol>

      <%--<t:dgCol title="工艺种类"  field="gyzl"  dictionary="gylx" queryMode="single"  width="70"></t:dgCol>--%>
      <%--<t:dgCol title="款式大类"  field="proTypeName"  queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="总计"  field="sumMoney"  queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="毛利润率"  field="maoRate"  queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="税收成本"  field="tax"  queryMode="single"  width="70"></t:dgCol>--%>
      <%--<t:dgFunOpt funname="queryDetail1(id,pirceNo,state)" title="主辅料" urlclass="ace_button" urlfont="fa-list-alt"></t:dgFunOpt>
      <t:dgFunOpt funname="queryDetail2(id,pirceNo,state)" title="染色" urlclass="ace_button" urlfont="fa-file-photo-o"></t:dgFunOpt>
      <t:dgFunOpt funname="queryDetail3(id,pirceNo,state)" title="印花" urlclass="ace_button" urlfont="fa-asterisk"></t:dgFunOpt>
      <t:dgFunOpt funname="queryDetail4(id,pirceNo,state)" title="工序" urlclass="ace_button" urlfont="fa-cog"></t:dgFunOpt>--%>
      <t:dgFunOpt funname="goToProcess(id,createBy,processName,state)" title="流程进度" operationCode="process" urlclass="ace_button"  urlStyle="background-color:#ec4758;" urlfont="fa-tasks"></t:dgFunOpt>


      <t:dgToolBar title="录入" operationCode="add" icon="fa fa-plus" url="emkPriceController.do?goAdd&winTitle=录入报价单" funname="add" height="600" width="1210"></t:dgToolBar>
      <t:dgToolBar title="编辑" operationCode="edit" icon="fa fa-edit" url="emkPriceController.do?goUpdate&winTitle=编辑报价单" funname="update" height="600" width="1210"></t:dgToolBar>
      <t:dgToolBar title="查看" icon="fa fa-search" operationCode="look" url="emkPriceController.do?goUpdate&winTitle=查看报价单" funname="detail" height="600" width="1210"></t:dgToolBar>

      <t:dgToolBar title="提交" operationCode="submit" icon="fa fa-arrow-circle-up"  funname="doSubmitV"></t:dgToolBar>
      <t:dgToolBar title="流程进度" operationCode="process" icon="fa fa-plus" funname="goToProcess"></t:dgToolBar>
      <t:dgToolBar title="删除" operationCode="delete" icon="fa fa-remove" url="emkPriceController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
      <t:dgToolBar title="导出" operationCode="exp"  icon="fa fa-arrow-circle-right" funname="ExportXls"></t:dgToolBar>
      <t:dgToolBar title="导出PDF" icon="fa fa-arrow-circle-right"  funname="doPrintPDF"></t:dgToolBar>

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
 <script src = "webpage/com/emk/storage/price/emkPriceList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });


 function formatColor(val,row){
     if(row.state=="1"){
         return '<span style="color:	#0000FF;">已提交</span>';
     }else if(row.state=="21"){
         return '<span style="color:	#0000FF;">回退业务员</span>';
     }else if(row.state=="2"){
         return '<span style="color:	#00FF00;">完成</span>';
     }else if(row.state=="22"){
         return '<span style="color:	#0000FF;">技术员通过</span>';
     }else if(row.state=="23"){
         return '<span style="color:	#0000FF;">回退技术员</span>';
     }else if(row.state=="24"){
         return '<span style="color:	#0000FF;">采购员通过</span>';
     }else if(row.state=="25"){
         return '<span style="color:	#0000FF;">财务通过</span>';
     }else if(row.state=="26"){
         return '<span style="color:	#0000FF;">财务经理通过</span>';
     }else if(row.state=="27"){
         return '<span style="color:	#0000FF;">回退财务</span>';
     }else{
         return '创建';
     }
 }

 function doSubmitV() {
     var rowsData = $('#emkPriceList').datagrid('getSelections');
     var ids = [];
     if (!rowsData || rowsData.length == 0) {
         tip('请选择需要提交的报价单');
         return;
     }
     for ( var i = 0; i < rowsData.length; i++) {
         ids.push(rowsData[i].id);
     }
     $.dialog.confirm('您是否确定提交报价单?', function(r) {
         if (r) {
             $.ajax({
                 url : "emkPriceController.do?doSubmit&ids="+ids,
                 type : 'post',
                 cache : false,
                 data: null,
                 success : function(data) {
                     var d = $.parseJSON(data);
                     tip(d.msg);
                     if (d.success) {
                         $('#emkPriceList').datagrid('reload');
                     }
                 }
             });
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
     if(createBy == "${CUR_USER.userName}"){
         if(state == '0' || state == '21'){
             createwindow("流程进度--当前环节：报价单【业务员】", "flowController.do?goProcess&node="+node+"&processUrl=com/emk/storage/price/emkPrice-process&id=" + id, 1230, height);
         }else {
             createdetailwindow("流程进度--当前环节：" + processNameVal, "flowController.do?goProcess&node="+node+"&processUrl=com/emk/storage/price/emkPrice-process&id=" + id, 1230, height);
         }
     }else{
         if(state == '2'){
             createdetailwindow('流程进度--当前环节：完成' , 'flowController.do?goProcess&processUrl=com/emk/storage/price/emkPrice-process&id=' + id, 1230, height);
         }else{
             if((state == '1' || state == '23') && "${ROLE.rolecode}" == "jsy" || state == '22' && "${ROLE.rolecode}" == "cgy" || (state == '24' || state == '27') && "${ROLE.rolecode}" == "cw" || state == '25'&& "${ROLE.rolecode}" == "cwjl" || state == '26' && "${ROLE.rolecode}" == "zjl"){
                 createwindow("流程进度--当前环节：" + processNameVal, "flowController.do?goProcess&node="+node+"&processUrl=com/emk/storage/price/emkPrice-process&id=" + id, 1230, height);
             }else{
                 createdetailwindow('流程进度--当前环节：' + processNameVal , 'flowController.do?goProcess&processUrl=com/emk/storage/price/emkPrice-process&id=' + id, 1230, height);
             }
         }
     }

 }


 /*function queryDetail1(id,proName,state){
     var title = "主辅料清单："+proName ;
     if(li_east == 0 || $('#main_list').layout('panel','east').panel('options').title != title){
         $('#main_list').layout('expand','east');
     }
     $('#main_list').layout('panel','east').panel('setTitle', title);
     $('#main_list').layout('panel','east').panel('resize', {width: 600});

     $('#proDetialListpanel').panel("refresh", "emkSampleDetailController.do?list&sampleType=price&state="+state+"&sampleId=" + id);
 }

 function queryDetail2(id,proName,state){
     var title = "样品染色："+proName ;
     if(li_east == 0 || $('#main_list').layout('panel','east').panel('options').title != title){
         $('#main_list').layout('expand','east');
     }
     $('#main_list').layout('panel','east').panel('setTitle', title);
     $('#main_list').layout('panel','east').panel('resize', {width: 600});

     $('#proDetialListpanel').panel("refresh", "emkSampleRanController.do?list&type=price&state="+state+"&sampleId=" + id);
 }

 function queryDetail3(id,proName,state){
     var title = "印花染色："+proName ;
     if(li_east == 0 || $('#main_list').layout('panel','east').panel('options').title != title){
         $('#main_list').layout('expand','east');
     }
     $('#main_list').layout('panel','east').panel('setTitle', title);
     $('#main_list').layout('panel','east').panel('resize', {width: 600});

     $('#proDetialListpanel').panel("refresh", "emkSampleYinController.do?list&state="+state+"&sampleId=" + id);
 }

 function queryDetail4(id,proName,state){
     var title = "样品工序："+proName ;
     if(li_east == 0 || $('#main_list').layout('panel','east').panel('options').title != title){
         $('#main_list').layout('expand','east');
     }
     $('#main_list').layout('panel','east').panel('setTitle', title);
     $('#main_list').layout('panel','east').panel('resize', {width: 600});

     $('#proDetialListpanel').panel("refresh", "emkSampleGxController.do?list&state="+state+"&sampleId=" + id);
 }*/
 function doPrintPDF() {
     var rowsData = $('#emkPriceList').datagrid('getSelections');
     var ids = [];
     if (!rowsData || rowsData.length == 0) {
         tip('请选择需要导出PDF的报价单');
         return;
     }
     for ( var i = 0; i < rowsData.length; i++) {
         ids.push(rowsData[i].id);
     }
     $.dialog.confirm('您是否确定导出PDF的报价单?', function(r) {
         if (r) {
             window.location.href = "emkPriceController.do?doPrintPDF&ids="+ids;
         }
     });
 }
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'emkPriceController.do?upload', "emkPriceList");
}

//导出
function ExportXls() {
	JeecgExcelExport("emkPriceController.do?exportXls","emkPriceList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("emkPriceController.do?exportXlsByT","emkPriceList");
}

 </script>
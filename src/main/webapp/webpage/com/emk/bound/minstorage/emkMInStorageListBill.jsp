<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="emkMInStorageList"  singleSelect="true" pagination="true" pageSize="20" fitColumns="false" title="查询条件" actionUrl="emkMInStorageController.do?listAllByJdbc" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
      <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <%--  <t:dgCol title="操作" field="opt"   width="100"></t:dgCol>
      <t:dgCol title="当前流程节点"  field="processName"  hidden="true"  queryMode="single"  width="110"></t:dgCol>--%>
      <%--<t:dgCol title="预采购合同号"  field="yhtNum" queryMode="single" width="105"></t:dgCol>--%>
      <%--<t:dgCol title="采购合同号"  query="true"  field="htNum" queryMode="single" width="105"></t:dgCol>--%>
      <%--<t:dgCol title="采购入库单号" query="true"   field="rkNo" queryMode="single" width="110"></t:dgCol>
      <t:dgCol title="送货日期"  field="sendDate" query="true" queryMode="group"  width="80"></t:dgCol>
      <t:dgCol title="供应商"  field="gys"  query="true" queryMode="single"  width="200"></t:dgCol>
      <t:dgCol title="进货仓库"  field="storageName"  query="true" queryMode="single"  width="150"></t:dgCol>
      <t:dgCol title="经办人"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="开单日期"  field="kdDate" query="true" queryMode="group"  width="80"></t:dgCol>--%>

      <%--<t:dgCol title="备注"  field="remark"  queryMode="single"  width="80"></t:dgCol>--%>
      <t:dgCol title="日期"  field="kdDate" query="true" align="center" queryMode="group"  width="80"></t:dgCol>
      <t:dgCol title="单号"  field="workNo"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="供应商"  field="gys"  queryMode="single"  width="180"></t:dgCol>
      <t:dgCol title="商品名称"  field="proName"  queryMode="single"  width="160"></t:dgCol>
      <t:dgCol title="规格"  field="guig"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="型号"  field="brand"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="单位"  field="unitName"  queryMode="single"  width="50"></t:dgCol>
      <t:dgCol title="商品数量"  field="total"  queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="金额"  field="money"  queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="备注"  field="remark"  queryMode="single"  width="80"></t:dgCol>

      <%--<t:dgFunOpt funname="goToProcess(id,createBy,processName,state)" title="流程进度" urlclass="ace_button"  urlStyle="background-color:#ec4758;" urlfont="fa-tasks"></t:dgFunOpt>--%>

      <t:dgToolBar title="导出"  icon="fa fa-arrow-circle-right" operationCode="exp" funname="ExportXls"></t:dgToolBar>

      <t:dgToolBar title="打印" icon="fa fa-print" url="emkMInStorageController.do?goUpdate" funname="printPreview"></t:dgToolBar>

  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/emk/bound/minstorage/emkMInStorageList.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
     $("#emkMInStorageListtb").find("input[name='kdDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
     $("#emkMInStorageListtb").find("input[name='kdDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
function setHeader(){
    $.dialog({
        content: 'url:hmCategoryController.do?for&forColumn&parentCode=A01A03',
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
         content: 'url:hmCategoryController.do?for&forColumn&parentCode=A01A09',
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
 function printPreview(){
     width = window.top.document.body.offsetWidth*0.85;
     height =window.top.document.body.offsetHeight-100;
     var rowsData = $('#emkMInStorageList').datagrid('getSelections');
     var ids = [];
     if (!rowsData || rowsData.length == 0) {
         tip('请选择需要打印的采购入库单');
         return;
     }
     openwindow("打印预览",'emkMInStorageController.do?jump&r=qtPrintSheetFrm&url=emkMInStorageController.do?printPreview&rkNo='+rowsData[0].rkNo,"hmSalaryList",width,height);
 }

 function formatColor(val,row){
     if(row.state=="1"){
         return '<span style="color:	#0000FF;">已审核</span>';
     }else if(row.state=="0"){
         return '<span style="color:	#FF0000;">未审核</span>';
     }
 }
 function formatColor2(val,row){
     if(row.payState=="1"){
         return '<span style="color:	#0000FF;">已付款</span>';
     }else if(row.payState=="0"){
         return '<span style="color:	#FF0000;">未付款</span>';
     }
 }
 function formatColor3(val,row){
     if(row.recevieState=="1"){
         return '<span style="color:	#0000FF;">已收货</span>';
     }else if(row.recevieState=="0"){
         return '<span style="color:	#FF0000;">未收货</span>';
     }
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
     if(state == '0'){
         processNameVal = '采购入库单';
     }else if(state == '1'){
         processNameVal = '采购员审核';
     }else if(state == '2'){
         processNameVal = '完成';
     }
     createdetailwindow("流程进度--当前环节：" + processNameVal, "flowController.do?goProcess&state="+state+"&node="+node+"&processUrl=com/emk/bound/minstorage/emkMInStorage-process&id=" + id, 1300, height);

 }

 function doPrintPDF() {
     var rowsData = $('#emkMInStorageList').datagrid('getSelections');
     var ids = [];
     if (!rowsData || rowsData.length == 0) {
         tip('请选择需要导出PDF的采购入库单');
         return;
     }
     for ( var i = 0; i < rowsData.length; i++) {
         ids.push(rowsData[i].id);
     }
     $.dialog.confirm('您是否确定导出PDF的采购入库单?', function(r) {
         if (r) {
             window.location.href = "emkMInStorageController.do?doPrintPDF&ids="+ids;
         }
     });
 }
 function doSubmitV() {
     var rowsData = $('#emkMInStorageList').datagrid('getSelections');
     var ids = [];
     if (!rowsData || rowsData.length == 0) {
         tip('请选择需采购入库单');
         return;
     }
     for ( var i = 0; i < rowsData.length; i++) {
         ids.push(rowsData[i].id);
     }

     $.dialog.confirm('您是否确定提交采购入库单?', function(r) {
         var index = layer.load(1, {
             skin:"layui-layer-sys1",
             shade: [0.1,'#fff'] //0.1透明度的白色背景
         });
         if (r) {
             $.ajax({
                 url : "emkMInStorageController.do?doApproval&ids="+ids,
                 type : 'post',
                 cache : false,
                 data: null,
                 success : function(data) {
                     layer.close(index);
                     var d = $.parseJSON(data);
                     layer.msg(d.msg);
                     if (d.success) {
                         $('#emkMInStorageList').datagrid('reload');
                     }

                 }
             });
         }
     });
 }
 function doCancelApproval() {
     var rowsData = $('#emkMInStorageList').datagrid('getSelections');
     var ids = [];
     if (!rowsData || rowsData.length == 0) {
         tip('请选择需采购入库单');
         return;
     }
     for ( var i = 0; i < rowsData.length; i++) {
         ids.push(rowsData[i].id);
     }

     $.dialog.confirm('您是否确定反审核采购入库单?', function(r) {
         var index = layer.load(1, {
             skin:"layui-layer-sys1",
             shade: [0.1,'#fff'] //0.1透明度的白色背景
         });
         if (r) {
             $.ajax({
                 url : "emkMInStorageController.do?doCancelApproval&ids="+ids,
                 type : 'post',
                 cache : false,
                 data: null,
                 success : function(data) {
                     layer.close(index);
                     var d = $.parseJSON(data);
                     layer.msg(d.msg);
                     if (d.success) {
                         $('#emkMInStorageList').datagrid('reload');
                     }

                 }
             });
         }
     });
 }
 function doPay() {
     var rowsData = $('#emkMInStorageList').datagrid('getSelections');
     var ids = [];
     if (!rowsData || rowsData.length == 0) {
         tip('请选择需采购入库单');
         return;
     }
     for ( var i = 0; i < rowsData.length; i++) {
         ids.push(rowsData[i].id);
     }

     $.dialog.confirm('您是否确定付款采购入库单?', function(r) {
         var index = layer.load(1, {
             skin:"layui-layer-sys1",
             shade: [0.1,'#fff'] //0.1透明度的白色背景
         });
         if (r) {
             $.ajax({
                 url : "emkMInStorageController.do?doPay&ids="+ids,
                 type : 'post',
                 cache : false,
                 data: null,
                 success : function(data) {
                     layer.close(index);
                     var d = $.parseJSON(data);
                     layer.msg(d.msg);
                     if (d.success) {
                         $('#emkMInStorageList').datagrid('reload');
                     }

                 }
             });
         }
     });
 }

//导入
function ImportXls() {
	openuploadwin('Excel导入', 'emkMInStorageController.do?upload', "emkMInStorageList");
}

//导出
function ExportXls() {
	JeecgExcelExport("emkMInStorageController.do?exportXls","emkMInStorageList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("emkMInStorageController.do?exportXlsByT","emkMInStorageList");
}

 </script>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="emkMOutStorageList" checkbox="true" pagination="true" pageSize="20" fitColumns="false"  title="" actionUrl="emkMOutStorageController.do?listAllByJdbc&type=2" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
      <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="销售单号" query="true"  frozenColumn="true" field="ckNo" queryMode="single" width="110"></t:dgCol>
      <t:dgCol title="申请人" query="true"   field="appler" queryMode="single" width="60"></t:dgCol>
      <t:dgCol title="发货日期"  field="fhDate"  queryMode="single"  width="90"></t:dgCol>
      <t:dgCol title="客户名称" field="cusName" query="true" queryMode="single"  width="160"></t:dgCol>
      <t:dgCol title="销售日期"  field="outDate"  query="true" queryMode="group"  width="90"></t:dgCol>
      <t:dgCol title="联系人"  field="zlxr"  queryMode="single"  width="60"></t:dgCol>
      <t:dgCol title="产品名称"  field="proZnName"  query="true" queryMode="single"  width="130"></t:dgCol>
      <t:dgCol title="产品编号"  field="proNum" query="true"  queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="规格"  field="standards"  query="true" queryMode="single"  width="90"></t:dgCol>
      <t:dgCol title="型号"  field="brand" query="true"  queryMode="single"  width="140"></t:dgCol>
      <t:dgCol title="单位"  field="unit"  queryMode="single"  width="45"></t:dgCol>
      <t:dgCol title="生产企业"  field="scqy"  queryMode="single"  width="190"></t:dgCol>
      <t:dgCol title="许可证号"  field="lincese"  queryMode="single"  width="140"></t:dgCol>
      <t:dgCol title="注册证号"  field="zcNum"  queryMode="single"  width="140"></t:dgCol>
      <t:dgCol title="生产日期"  field="scDate"  queryMode="single"  width="80"></t:dgCol>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/emk/bound/moutstorage/emkMOutStorageList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
     $("#emkMOutStorageListtb").find("input[name='outDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
     $("#emkMOutStorageListtb").find("input[name='outDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });

 function formatColor(val,row){
     if(row.state=="1"){
         return '<span style="color:	#0000FF;">已提交</span>';
     }else if(row.state=="2"){
         return '<span style="color:	#00FF00;">完成</span>';
     }else if(row.state=="24"){
         return '<span style="color:	#0000FF;">采购员通过</span>';
     }else{
         return '创建';
     }
 }

 function doPrintPDF() {
     var rowsData = $('#emkMOutStorageList').datagrid('getSelections');
     var ids = [];
     if (!rowsData || rowsData.length == 0) {
         tip('请选择需要导出PDF的原料面料销售申请单');
         return;
     }
     for ( var i = 0; i < rowsData.length; i++) {
         ids.push(rowsData[i].id);
     }
     $.dialog.confirm('您是否确定导出PDF的原料面料销售申请单?', function(r) {
         if (r) {
             window.location.href = "emkMOutStorageController.do?doPrintPDF&ids="+ids;
         }
     });
 }
 function printPreview(){
     width = window.top.document.body.offsetWidth*0.85;
     height =window.top.document.body.offsetHeight-100;
     var rowsData = $('#emkMOutStorageList').datagrid('getSelections');
     var ids = [];
     if (!rowsData || rowsData.length == 0) {
         tip('请选择需要打印的销售申请单');
         return;
     }
     openwindow("打印预览",'emkMOutStorageController.do?jump&r=qtPrintSheetFrm&url=emkMOutStorageController.do?printPreview&ckNo='+rowsData[0].ckNo,"hmSalaryList",width,height);
 }
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'emkMOutStorageController.do?upload', "emkMOutStorageList");
}

//导出
function ExportXls() {
	JeecgExcelExport("emkMOutStorageController.do?exportXls","emkMOutStorageList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("emkMOutStorageController.do?exportXlsByT","emkMOutStorageList");
}

 </script>
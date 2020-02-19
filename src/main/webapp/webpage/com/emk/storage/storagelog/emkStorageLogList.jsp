<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="emkStorageLogList" checkbox="false" sortName="markDate" sortOrder="desc" pageSize="20" pagination="true" fitColumns="false" title="查询" actionUrl="emkStorageLogController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" hidden="true"  formatter="yyyy-MM-dd"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="markDate" query="true"  queryMode="group"  width="140"></t:dgCol>
      <t:dgCol title="单号"  field="rkNo" query="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="仓库"  field="storageName"  query="true" queryMode="single"  width="100"></t:dgCol>
      <t:dgCol title="类型"  field="type" query="true" replace="采购入库_0,采购订单入库_1,销售开单出库_2,录入期初库存_3,退货单_4,盘点_5,库存组合单_6,库存拆卸单_7,删除库存组合单_8,删除库存拆卸单_9"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="商品编号"  field="proNum" query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="商品名称"  field="proZnName" query="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="摘要"  field="remark"   queryMode="single"  width="150"></t:dgCol>
      <t:dgCol title="变动前数量"  field="preTotal"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="变动数量"  field="total"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="变动后数量"  field="nextTotal"  queryMode="single"  width="120"></t:dgCol>

  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/emk/storage/storagelog/emkStorageLogList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
     $("#emkStorageLogListtb").find("input[name='markDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
     $("#emkStorageLogListtb").find("input[name='markDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});

 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'emkStorageLogController.do?upload', "emkStorageLogList");
}

//导出
function ExportXls() {
	JeecgExcelExport("emkStorageLogController.do?exportXls","emkStorageLogList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("emkStorageLogController.do?exportXlsByT","emkStorageLogList");
}

 </script>
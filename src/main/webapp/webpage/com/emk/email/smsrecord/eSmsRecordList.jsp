<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="eSmsRecordList" checkbox="false" pagination="true" pageSize="30" fitColumns="false" title="" actionUrl="eSmsRecordController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="姓名"  field="realName"  query="true" queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="电话"  field="telphone" query="true" queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="发送时间"  field="sendTime"  queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="发送内容"  field="content"  queryMode="single"  width="500"></t:dgCol>
   <t:dgCol title="状态"  field="state" replace="已发送_0,发送失败_1"  queryMode="single"  width="90"></t:dgCol>

  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/emk/email/smsrecord/eSmsRecordList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'eSmsRecordController.do?upload', "eSmsRecordList");
}

//导出
function ExportXls() {
	JeecgExcelExport("eSmsRecordController.do?exportXls","eSmsRecordList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("eSmsRecordController.do?exportXlsByT","eSmsRecordList");
}

 </script>
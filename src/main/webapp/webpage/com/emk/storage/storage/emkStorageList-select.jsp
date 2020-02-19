<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="emkStorageList" checkbox="true" pagination="true" pageSize="20" fitColumns="false" title="" actionUrl="emkStorageController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
      <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <c:if test="${ROLE.rolecode eq 'admin'}">
          <t:dgCol title="公司"  field="orgCode" frozenColumn="true" dictionary="t_s_depart,org_code,departname" queryMode="single" query="true"  width="120"></t:dgCol>
      </c:if>
      <t:dgCol title="仓库"  field="storageName" frozenColumn="true" query="true" queryMode="single"  width="100"></t:dgCol>

      <c:forEach var="list" items="${categoryEntities}" varStatus="status">
          <c:if test="${list.code eq 'a01a09a05'}">
              <t:dgCol title="${list.name}"  field="${list.code}" dictionary="emk_storage_set_position,id,position_name" queryMode="single" query="${list.queryed eq 0 ? 'true':'false'}"  width="70"></t:dgCol>
          </c:if>
          <c:if test="${list.code ne 'a01a09a05'}">
              <t:dgCol title="${list.name}"  field="${list.code}" queryMode="single" query="${list.queryed eq 0 ? 'true':'false'}"  width="${list.column_type eq '4' ? '210':(list.column_type eq '0' || list.column_type eq '2' || list.column_type eq '6') ? '70':'100'}"></t:dgCol>
          </c:if>
      </c:forEach>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/emk/storage/storage/emkStorageList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });

 function formatTotal(val,row,index){
     var s = '';
     if(row.brand=="合计"){
         s = '<strong><font color="red">'+row.total+'</font></strong>';
     }else{
         s = row.total;

     }
     return s;
 }
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'emkStorageController.do?upload', "emkStorageList");
}

//导出
function ExportXls() {
	JeecgExcelExport("emkStorageController.do?exportXls","emkStorageList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("emkStorageController.do?exportXlsByT","emkStorageList");
}

 </script>
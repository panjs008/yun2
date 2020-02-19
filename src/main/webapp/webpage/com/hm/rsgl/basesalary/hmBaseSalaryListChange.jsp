<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="hmBaseSalaryList" checkbox="true" pagination="true" pageSize="20" fitColumns="false" title="" actionUrl="hmBaseSalaryController.do?datagridChange&realName=${param.realName}" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <%--<t:dgCol title="工号"   query="true"  field="workNo"  queryMode="single"  width="60"></t:dgCol>--%>
      <t:dgCol title="姓名"   query="true" field="realName"  queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="部门"   field="deptName"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="车间"   field="workName"  queryMode="single"  width="100"></t:dgCol>
      <t:dgCol title="实施日期"   field="excetuDate"  queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="调薪摘要"   field="reason"  queryMode="single"  width="120"></t:dgCol>

      <c:forEach var="list" items="${categoryEntities}" varStatus="status">
          <t:dgCol title="${list.name}"  field="${list.code}"  queryMode="single" query="${list.queryed eq 0 ? 'true':'false'}"  width="${list.column_type eq '4' ? '120':(list.column_type eq '0' || list.column_type eq '2') ? '50':'80'}"></t:dgCol>
      </c:forEach>
       <t:dgToolBar title="录入" icon="fa fa-plus" url="hmBaseSalaryController.do?goAddChange&realName=${param.realName}&month=${param.month}&winTitle=录入调薪" funname="add"></t:dgToolBar>
       <t:dgToolBar title="编辑" icon="fa fa-edit" url="hmBaseSalaryController.do?goUpdateChange&winTitle=编辑调薪" funname="update"></t:dgToolBar>
       <t:dgToolBar title="删除"  icon="fa fa-remove" url="hmBaseSalaryController.do?doBatchDelChange" funname="deleteALLSelect"></t:dgToolBar>
       <t:dgToolBar title="查看" icon="fa fa-search" url="hmBaseSalaryController.do?goUpdateChange&winTitle=编辑调薪" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/hm/rsgl/basesalary/hmBaseSalaryList.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
 });


 </script>

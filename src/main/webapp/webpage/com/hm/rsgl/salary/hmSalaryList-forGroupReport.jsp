<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="tools"></t:base>
<t:datagrid name="hmWorkDayList"  pagination="true" pageSize="50" fitColumns="false" title="" actionUrl="hmSalaryController.do?listAllGroupByJdbc&workCode=${param.workCode}&month=${param.month}" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="月份" field="month"  queryMode="single"  width="70"></t:dgCol>
    <t:dgCol title="部门"  field="deptName"  queryMode="single"  width="100"></t:dgCol>
    <t:dgCol title="车间"  field="workName"  queryMode="single"  width="100"></t:dgCol>
    <t:dgCol title="班组" field="groupName"  queryMode="single"  width="100"></t:dgCol>
    <t:dgCol title="工资" field="gz"  queryMode="single"  width="80"></t:dgCol>
</t:datagrid>

 <script type="text/javascript">


 </script>
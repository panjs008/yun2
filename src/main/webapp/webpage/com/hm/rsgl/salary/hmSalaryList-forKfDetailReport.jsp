<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="tools"></t:base>

<t:datagrid name="hmSalaryDetailList" checkbox="false" pagination="true" fitColumns="false" pageSize="20" title="" actionUrl="hmSalaryController.do?wsfdatagrid&month=${param.month}&dg=${param.taker}&c=${param.c}" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="月份" field="month"  queryMode="single"  width="70"></t:dgCol>
    <t:dgCol title="姓名"  query="true" field="realName"  queryMode="single"  width="70"></t:dgCol>
    <t:dgCol title="部门"  query="true"   field="deptName"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="车间"  query="true"   field="workName"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="班组"  query="true"  field="groupName"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="费用"   field="${param.c}"  queryMode="single"  width="90"></t:dgCol>
    <t:dgCol title="workno"  hidden="true" field="workNo"  queryMode="single"  width="90"></t:dgCol>

</t:datagrid>

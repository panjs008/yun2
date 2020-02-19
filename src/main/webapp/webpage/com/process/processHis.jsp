<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:datagrid name="instorageList" checkbox="false"  pageSize="15"  fitColumns="false" title="" actionUrl="flowController.do?hisProcessDatagrid&sqlType=${param.sqlType}&id=${param.id}" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
    <t:dgCol title="流程节点"  field="bpm_name"  queryMode="single"  width="160"></t:dgCol>
    <t:dgCol title="操作人"  field="create_name"  queryMode="single"  width="80"></t:dgCol>
    <t:dgCol title="开始时间"  field="startTime"  queryMode="single"  width="160"></t:dgCol>
    <t:dgCol title="结束时间"  field="approve_date"  queryMode="single"  width="180"></t:dgCol>
    <t:dgCol title="审核意见"  field="approve_advice"  queryMode="single"  width="300"></t:dgCol>
    <t:dgCol title="审核状态"  field="approve_status"  queryMode="single"  formatterjs="formatColor" width="150"></t:dgCol>
</t:datagrid>
<script>
    function formatColor(val,row){
        if(row.approve_status=="0"){
            return '<span style="color:	#0000FF;">通过</span>';
        }else if(row.approve_status=="1"){
            return '<span style="color:	#FF0000;">驳回</span>';
        }else{
            return '';
        }
    }
</script>
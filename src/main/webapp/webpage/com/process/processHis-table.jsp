<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%--
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:datagrid name="instorageList" checkbox="false"  pageSize="15"  fitColumns="false" title="" actionUrl="flowController.do?hisProcessDatagrid&sqlType=${param.sqlType}&id=${param.id}" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
    <t:dgCol title="流程节点"  field="bpm_name"  queryMode="single"  width="150"></t:dgCol>
    <t:dgCol title="操作人"  field="create_name"  queryMode="single"  width="80"></t:dgCol>
    <t:dgCol title="开始时间"  field="startTime"  queryMode="single"  width="160"></t:dgCol>
    <t:dgCol title="结束时间"  field="approve_date"  queryMode="single"  width="160"></t:dgCol>
    <t:dgCol title="审核意见"  field="approve_advice"  queryMode="single"  width="150"></t:dgCol>
    <t:dgCol title="审核状态"  field="approve_status" formatterjs="formatColor"  queryMode="single"  width="120"></t:dgCol>
</t:datagrid>
<script>

    function formatColor(val,row){
        if(row.approve_status=="0"){
            return '<span style="color:	#0000FF;">通过</span>';
        }else if(row.state=="1"){
            return '<span style="color:	#FF0000;">驳回</span>';
        }else{
            return '';
        }
    }
</script>--%>
<style>
    .table-c table{border-right:1px solid #ddd;border-bottom:1px solid #ddd}
    .table-c table td{border-left:1px solid #ddd;border-top:1px solid #ddd;height: 36px;  font-size: 12px;
        font-family: Tahoma,Verdana,微软雅黑,新宋体, Helvetica, Arial, sans-serif;
    }

</style>
<div class="table-c" style="margin-top:5px;">
    <table id="mxtb" width="98%" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#F8F8F8" style="height: 32px;" >
            <td align="center" width="40" >序号</td>
            <td align="center"  width="140" >流程节点</td>
            <td align="center"  width="60" >操作人</td>
            <td align="center"  width="90" >开始时间</td>
            <td align="center"  width="90" >结束时间</td>
            <td align="center"  width="120">审核意见</td>
            <td align="center"  width="90">审核状态</td>
        </tr>
        <tbody id="add_jeecgOrderProduct_table">
            <c:forEach var="his" items="${processHisList}" varStatus="status">
                <tr>
                    <td align="center" width="40">${status.index+1}</td>
                    <td align="center" width="140">${his.bpm_name}</td>
                    <td align="center" width="60">${his.create_name}</td>
                    <td align="center" width="90">${his.startTime}</td>
                    <td align="center" width="90">${his.approve_date}</td>
                    <td align="center" width="120">${his.approve_advice}</td>
                    <td align="center" width="90">
                        <c:if test="${his.approve_status eq '0'}"><span style="color:	#0000FF;">通过</span></c:if>
                        <c:if test="${his.approve_status eq '1'}"><span style="color:	#FF0000;">驳回</span></c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    </div>
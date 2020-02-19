<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script>

</script>
<div id="main_list" class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
    <t:datagrid name="hmStaffList" checkbox="false" pagination="true" sortName="deptCode,workCode" sortOrder="asc" pageSize="20" fitColumns="false" title="" actionUrl="hmStaffController.do?listAllAnsilyByJdbc&deptCode=${param.deptCode}&workCode=${param.workCode}" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="部门"  field="deptName"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="车间"  field="workName"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="班组" field="groupName"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="性别" field="sex"  queryMode="single"  width="50"></t:dgCol>
            <c:forEach var="list" items="${typeList}" varStatus="status">
                <t:dgCol title="${list.typename}" formatterjs="setPoint"  field="A${list.typecode}" queryMode="single"  width="45" ></t:dgCol>
            </c:forEach>
            <t:dgCol title="合计"  formatterjs="setTotal" field="createName"  queryMode="single"  width="45"></t:dgCol>

        </t:datagrid>
    </div>
</div>

<script src = "webpage/com/hm/rsgl/staff/hmStaffList.js"></script>
<script type="text/javascript">
    $(document).ready(function(){

    });
    function setPoint(val,row,index){
        if(val != null && val != ''  && val != '0'){
            return parseInt(val);
        }else{
            return '';
        }
    }

    function setTotal(val,row,index){
        var total = parseInt(row.A01)+parseInt(row.A02)+parseInt(row.A03)+parseInt(row.A04)+parseInt(row.A05)+parseInt(row.A06)+parseInt(row.A07);
        return '<font color="blue">'+total+'</font>';
    }
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="hmStaffList" checkbox="false" pagination="true" sortName="workNo" sortOrder="asc" pageSize="20" fitColumns="true" title="" actionUrl="hmStaffController.do?datagridOld&monthV=0" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <%--<t:dgCol title="标准" field="month" hidden="true"  query="true" dictionary="dg"   queryMode="single"  width="90"></t:dgCol>--%>
            <t:dgCol title="工号"  query="true" field="workNo"  queryMode="single"  width="90"></t:dgCol>
            <t:dgCol title="姓名"  query="true" field="realName"  queryMode="single"  width="100"></t:dgCol>
            <t:dgCol title="部门"  query="true" field="deptName"  queryMode="single"  width="150"></t:dgCol>
            <t:dgCol title="部门代码"  hidden="true" field="deptCode"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="车间" query="true" field="workName"  queryMode="single"  width="150"></t:dgCol>
            <t:dgCol title="车间代码" hidden="true"  field="workCode"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="班组" query="true" field="groupName"  queryMode="single"  width="150"></t:dgCol>
            <t:dgCol title="班组代码"  hidden="true"  field="groupCode"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="职务"  field="job"  dictionary="job" queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="级别"  field="jb"  dictionary="jb" queryMode="single"  width="80"></t:dgCol>
            <t:dgCol title="薪酬类别"  field="xclb"  dictionary="xclb" queryMode="single"  width="100"></t:dgCol>
            <t:dgCol title="性别"  field="sex" dictionary="sex"  queryMode="single"  width="60"></t:dgCol>
            <t:dgCol title="员工类别"  field="yglb" query="true" dictionary="yglb"  queryMode="single"  width="80"></t:dgCol>
            <t:dgCol title="入职日期"  field="rzDate"  queryMode="single"  width="110"></t:dgCol>
            <t:dgCol title="工龄"  field="workYearV"  queryMode="single"  width="60"></t:dgCol>
            <t:dgCol title="状态"  field="state" formatterjs="setColor" queryMode="single"  width="70"></t:dgCol>
            <t:dgCol title="上月工龄奖"  field="lastMonth" queryMode="single"  width="110"></t:dgCol>
            <t:dgCol title="本月工龄奖"  field="createBy" formatterjs="setGlj1" queryMode="single"  width="110"></t:dgCol>
            <t:dgToolBar title="编辑" icon="fa fa-edit" funname="doSubmitV" width="1200" height="600"></t:dgToolBar>
            <t:dgToolBar title="导出" icon="fa fa-arrow-circle-right" funname="ExportXls"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script src = "webpage/com/hm/rsgl/staff/hmStaffList.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
        /*$("#hmStaffListtb").find("select[name='month']").empty();
        var html = '<option value="0">一年以上三年以下</option>';
        html += '<option value="1">三年以上至5年以下</option>';
        html += '<option value="2">5年以上</option>';
        $("#hmStaffListtb").find("select[name='month']").append(html);*/
    });

    function setColor(val,row,index){
        if(val == '0'){
            return '<font color="blue">在职</font>';
        }else if(val == '1'){
            return '<font color="red">离职</font>';
        }
    }
    function setGlj0(val,row,index){
        var m = $("#hmStaffListtb").find("select[name='month']").val();
        if(m== '0'){
            return 0;
        }else if(m== '1'){
            return 100;
        }else if(m== '2'){
            return 200;
        }
    }
    function setGlj1(val,row,index){
       /* var m = $("#hmStaffListtb").find("select[name='month']").val();
        if(m== '0'){
            return 100;
        }else if(m== '1'){
            return 200;
        }else if(m== '2'){
            return 300;
        }*/

        if(row.workYearV>=1 && row.workYearV<3){
            return 100;
        }else if(row.workYearV>=3 && row.workYearV<5){
            return 200;
        }else if(row.workYearV>=5){
            return 300;
        }
    }

    function doSubmitV() {
        var rowsData = $('#hmStaffList').datagrid('getSelections');
        var ids = [];
        if (!rowsData || rowsData.length == 0) {
            tip('请选择人员');
            return;
        }
        /*for ( var i = 0; i < rowsData.length; i++) {
            ids.push(rowsData[i].id);
        }*/
        createwindow("审批", "hmStaffController.do?goApproval&id=" + rowsData[0].id+"&lygState="+rowsData[0].workYearV, 300, 150);

    }

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'hmStaffController.do?upload', "hmStaffList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("hmStaffController.do?exportXls","hmStaffList");
    }

    //模板下载
    function ExportXlsByT() {
        window.open("${webRoot}/context/人员信息表.xls");
//	JeecgExcelExport("hmStaffController.do?exportXlsByT","hmStaffList");
    }

</script>
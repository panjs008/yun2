<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="emkTestCostList" checkbox="false" pagination="true" fitColumns="true" title=""
                    actionUrl="emkTestCostController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"
                    queryMode="group">
            <t:dgCol title="主键" field="id" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="操作" field="opt" frozenColumn="true"  width="100"></t:dgCol>
            <t:dgCol title="当前流程节点"  field="processName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>

            <t:dgCol title="创建人名称" field="createName" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称" field="createBy" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="创建日期" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="single"
                     width="120"></t:dgCol>
            <t:dgCol title="所属部门" field="sysOrgCode" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="费用付款申请单号"  field="costNo"  query="true" queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="测试申请单号"  field="testNo"   queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="提交日期" field="kdDate" queryMode="single" width="80"></t:dgCol>
            <t:dgCol title="生产合同号" field="produceNum" query="true" queryMode="single" width="90"></t:dgCol>
            <t:dgCol title="订单号" field="orderNo" query="true" queryMode="single" width="80"></t:dgCol>
            <t:dgCol title="业务部门" field="businesseDeptName" queryMode="single" width="80"></t:dgCol>
            <t:dgCol title="业务员" field="businesserName" queryMode="single" width="60"></t:dgCol>
            <t:dgCol title="客户代码" query="true" field="cusNum" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="客户名称" query="true" field="cusName" queryMode="single" width="145"></t:dgCol>
            <t:dgCol title="款号" field="sampleNo" queryMode="single" width="80"></t:dgCol>
            <t:dgCol title="状态"  field="state" formatterjs="formatColor"  queryMode="single"  width="100"></t:dgCol>
            <t:dgCol title="来源"  field="formType" replace="手工创建_0,询盘单生成_1,采购生产进度生成_2" queryMode="single"  width="100"></t:dgCol>

            <t:dgFunOpt funname="goToProcess(id,createBy,processName,state)" title="流程进度" operationCode="process" urlclass="ace_button"  urlStyle="background-color:#ec4758;" urlfont="fa-tasks"></t:dgFunOpt>
            <t:dgToolBar title="查看" icon="fa fa-search" operationCode="look" url="emkTestCostController.do?goUpdate&winTitle=查看测试费用付款申请" funname="detail" height="580" width="1000"></t:dgToolBar>
            <t:dgToolBar title="录入" icon="fa fa-plus" operationCode="add" url="emkTestCostController.do?goAdd&winTitle=录入测试费用付款申请"
                         funname="add" height="600" width="1000"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="fa fa-edit" operationCode="edit" url="emkTestCostController.do?goUpdate&winTitle=编辑测试费用付款申请"
                         funname="update" height="600" width="1000"></t:dgToolBar>

            <t:dgToolBar title="提交" operationCode="submit" icon="fa fa-arrow-circle-up" funname="doSubmitV"></t:dgToolBar>
            <t:dgToolBar title="删除" operationCode="delete"  icon="fa fa-remove" url="emkTestCostController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
            <t:dgToolBar title="导出" operationCode="exp" icon="fa fa-arrow-circle-right" funname="ExportXls"></t:dgToolBar>

        </t:datagrid>
    </div>
</div>
<script src="webpage/com/emk/produce/testcost/emkTestCostList.js"></script>
<script type="text/javascript">
    $(document).ready(function () {

    });

    function formatColor(val,row){
        if(row.state=="1"){
            return '<span style="color:	#0000FF;">提交</span>';
        }else if(row.state=="61"){
            return '<span style="color:	#0000FF;">业务跟单员通过</span>';
        }else if(row.state=="62"){
            return '<span style="color:	#0000FF;">安排测试员</span>';
        }else if(row.state=="63"){
            return '<span style="color:	#0000FF;">测试报告通过</span>';
        }else{
            return '创建';
        }
    }

    function doSubmitV() {
        var rowsData = $('#emkTestCostList').datagrid('getSelections');
        var ids = [];
        if (!rowsData || rowsData.length == 0) {
            tip('请选择需要提交的测试费用付款申请单');
            return;
        }
        for (var i = 0; i < rowsData.length; i++) {
            ids.push(rowsData[i].id);
        }
        $.dialog.confirm('您是否确定提交测试费用付款申请单?', function (r) {
            if (r) {
                $.ajax({
                    url: "emkTestCostController.do?doSubmit&ids=" + ids,
                    type: 'post',
                    cache: false,
                    data: null,
                    success: function (data) {
                        var d = $.parseJSON(data);
                        tip(d.msg);
                        if (d.success) {
                            $('#emkTestCostList').datagrid('reload');
                        }
                    }
                });
            }
        });
    }

    function goToProcess(id,createBy,processName,state){
        var height =window.top.document.body.offsetHeight*0.85;
        var processNameVal='',node='';
        if(processName != null){
            if(processName.indexOf('-') > 0){
                processNameVal = processName.substring(0,processName.indexOf('-'));
                node = processName.substring(processName.indexOf('-')+1);
            }
        }
        if(state == '0'){
            createwindow("流程进度--当前环节：测试费用付款申请单", "flowController.do?goProcess&state="+state+"&node="+node+"&processUrl=com/emk/produce/testcost/emkTestCost-process&state="+state+"&id=" + id, 1250, height);
        }else {
            createdetailwindow("流程进度--当前环节：" + processNameVal, "flowController.do?goProcess&state="+state+"&node="+node+"&processUrl=com/emk/produce/testcost/emkTestCost-process&state="+state+"&id=" + id, 1250, height);
        }
    }


    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'emkTestCostController.do?upload', "emkTestCostList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("emkTestCostController.do?exportXls", "emkTestCostList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("emkTestCostController.do?exportXlsByT", "emkTestCostList");
    }

</script>
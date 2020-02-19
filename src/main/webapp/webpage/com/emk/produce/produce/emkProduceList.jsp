<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="emkProduceList" checkbox="false" pagination="true" fitColumns="true" title=""
                    actionUrl="emkProduceController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"
                    queryMode="group">
            <t:dgCol title="主键" field="id" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="进度更新日期" field="kdDate" queryMode="single" width="80"></t:dgCol>
            <t:dgCol title="更新操作员" field="createBy" queryMode="single" width="80"></t:dgCol>
            <t:dgCol title="所属部门" field="sysOrgCode" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="当前流程节点"  field="processName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="操作" field="opt" frozenColumn="true"  width="100"></t:dgCol>
            <%--<t:dgCol title="提交日期"  field="kdDate"  queryMode="single"  width="60"></t:dgCol>--%>
            <%--<t:dgCol title="出货日期"  field="outDate"  queryMode="single"  width="60"></t:dgCol>--%>
            <%--<t:dgCol title="生产合同号"  field="produceHtNum"  query="true" queryMode="single"  width="85"></t:dgCol>--%>
            <%--<t:dgCol title="订单号"  field="orderNo" query="true"  queryMode="single"  width="80"></t:dgCol>--%>
            <t:dgCol title="业务部门" field="businesseDeptName" queryMode="single" width="80"></t:dgCol>
            <t:dgCol title="业务员" field="businesserName" queryMode="single" width="60"></t:dgCol>
            <t:dgCol title="业务跟单员" field="tracerName" queryMode="single" width="60"></t:dgCol>
            <t:dgCol title="生产跟单员" field="developerName" queryMode="single" width="60"></t:dgCol>

            <t:dgCol title="客户代码" query="true" field="cusNum" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="客户名称" query="true" field="cusName" queryMode="single" width="140"></t:dgCol>
            <%--<t:dgCol title="原样"  field="oldImageUrl" imageSize="30,30"  image="true"  queryMode="single"  width="50"></t:dgCol>--%>

            <%--<t:dgCol title="款号"  field="sampleNo"  queryMode="single"  width="80"></t:dgCol>--%>
            <%--<t:dgCol title="工艺种类"  field="gyzl"  dictionary="gylx" queryMode="single"  width="70"></t:dgCol>--%>
            <%--<t:dgCol title="款式大类"  field="proTypeName"  queryMode="single"  width="70"></t:dgCol>--%>
            <t:dgCol title="状态"  field="state" formatterjs="formatColor"  queryMode="single"  width="100"></t:dgCol>
            <t:dgFunOpt funname="goToProcess(id,createBy,processName,state)" title="流程进度" operationCode="process" urlclass="ace_button"  urlStyle="background-color:#ec4758;" urlfont="fa-tasks"></t:dgFunOpt>
            <t:dgToolBar title="录入" icon="fa fa-plus" operationCode="add"
                         url="emkProduceController.do?goAdd&winTitle=录入采购生产进度" funname="add" height="600"
                         width="1150"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="fa fa-edit" operationCode="edit"
                         url="emkProduceController.do?goUpdate&winTitle=编辑采购生产进度" funname="update" height="600"
                         width="1150"></t:dgToolBar>
            <t:dgToolBar title="提交" operationCode="submit" icon="fa fa-arrow-circle-up" funname="doSubmitV"></t:dgToolBar>
            <t:dgToolBar title="删除" operationCode="delete"  icon="fa fa-remove" url="emkProduceController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>

            <t:dgToolBar title="导出" operationCode="exp" icon="fa fa-arrow-circle-right"
                         funname="ExportXls"></t:dgToolBar>

        </t:datagrid>
    </div>
</div>
<script src="webpage/com/emk/produce/produce/emkProduceList.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
    });

    function formatColor(val, row) {
        if (row.state == "1") {
            return '<span style="color:	#0000FF;">已提交</span>';
        } else if (row.state == "2") {
            return '<span style="color:	#00FF00;">完成</span>';
        } else if (row.state == "61") {
            return '<span style="color:	#0000FF;">业务跟单员通过</span>';
        } else if(row.state=="64"){
            return '<span style="color:	#0000FF;">回退业务跟单员</span>';
        } else if(row.state=="70"){
            return '<span style="color:	#0000FF;">完成采购</span>';
        } else if(row.state=="71"){
            return '<span style="color:	#0000FF;">染色</span>';
        } else if(row.state=="72"){
            return '<span style="color:	#0000FF;">完成染色</span>';
        } else if(row.state=="73"){
            return '<span style="color:	#0000FF;">完成裁剪</span>';
        } else if(row.state=="74"){
            return '<span style="color:	#0000FF;">完成缝制</span>';
        } else if(row.state=="75"){
            return '<span style="color:	#0000FF;">完成包装</span>';
        } else if(row.state=="76"){
            return '<span style="color:	#0000FF;">完成船样</span>';
        } else if(row.state=="77"){
            return '<span style="color:	#0000FF;">订舱通知单</span>';
        } else if(row.state=="78"){
            return '<span style="color:	#0000FF;">回退订舱通知单</span>';
        } else if(row.state=="79"){
            return '<span style="color:	#0000FF;">订舱</span>';
        } else if(row.state=="80"){
            return '<span style="color:	#0000FF;">验货申请</span>';
        } else if(row.state=="81"){
            return '<span style="color:	#0000FF;">完成原料布料</span>';
        } else if(row.state=="82"){
            return '<span style="color:	#0000FF;">出口通知单</span>';
        } else if(row.state=="83"){
            return '<span style="color:	#0000FF;">回退出口通知单</span>';
        } else if(row.state=="84"){
            return '<span style="color:	#0000FF;">离厂通知单</span>';
        } else if(row.state=="85"){
            return '<span style="color:	#0000FF;">仓库装货放行</span>';
        } else if(row.state=="86"){
            return '<span style="color:	#0000FF;">财务在复核</span>';
        } else {
            return '创建';
        }
    }


    function goToProcess(id, createBy, processName, state) {
        var height = window.top.document.body.offsetHeight * 0.85;
        var processNameVal = '', node = '';
        if (processName != null) {
            if (processName.indexOf('-') > 0) {
                processNameVal = processName.substring(0, processName.indexOf('-'));
                node = processName.substring(processName.indexOf('-') + 1);
            }
        }
       /* if (createBy == "${CUR_USER.userName}" || ${CUR_USER.userName eq 'admin'}) {

        }*/
        if (state == '0') {
            createwindow("流程进度--当前环节：【生产跟单员】采购生产进度表", "flowController.do?goProcess&state="+state+"&node=" + node + "&processUrl=com/emk/produce/produce/emkProduce-process&id=" + id, 1410, height);
        } else {
            createdetailwindow("流程进度--当前环节：" + processNameVal, "flowController.do?goProcess&state="+state+"&node=" + node + "&processUrl=com/emk/produce/produce/emkProduce-process&id=" + id, 1410, height);
        }
    }

    function doSubmitV() {
        var rowsData = $('#emkProduceList').datagrid('getSelections');
        var ids = [];
        if (!rowsData || rowsData.length == 0) {
            tip('请选择需要提交的采购生产进度表');
            return;
        }
        for (var i = 0; i < rowsData.length; i++) {
            ids.push(rowsData[i].id);
        }
        $.dialog.confirm('您是否确定提交采购生产进度表?', function (r) {
            if (r) {
                $.ajax({
                    url: "emkProduceController.do?doSubmit&ids=" + ids,
                    type: 'post',
                    cache: false,
                    data: null,
                    success: function (data) {
                        var d = $.parseJSON(data);
                        tip(d.msg);
                        if (d.success) {
                            $('#emkProduceList').datagrid('reload');
                        }
                    }
                });
            }
        });
    }

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'emkProduceController.do?upload', "emkProduceList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("emkProduceController.do?exportXls", "emkProduceList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("emkProduceController.do?exportXlsByT", "emkProduceList");
    }

</script>
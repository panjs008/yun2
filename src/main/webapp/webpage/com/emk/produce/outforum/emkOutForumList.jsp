<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="emkOutForumList" checkbox="false" pagination="true" fitColumns="true" title="" actionUrl="emkOutForumController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
            <t:dgCol title="主键"  hidden="true" field="id"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="创建人名称"  hidden="true" field="createName"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称"  hidden="true" field="createBy"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="创建日期"  hidden="true" field="createDate"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="所属部门"  hidden="true" field="sysOrgCode"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="出货通知单号"  field="outForumNo" query="true"  queryMode="single"  width="80"></t:dgCol>
            <%--<t:dgCol title="生产合同号"  field="produceNum" query="true"  queryMode="single"  width="80"></t:dgCol>--%>
            <%--<t:dgCol title="订单号"  field="orderNo"  query="true" queryMode="single"  width="80"></t:dgCol>--%>
            <t:dgCol title="更新时间"  field="kdDate"   queryMode="single"  width="70"></t:dgCol>

            <t:dgCol title="业务部门"  field="businesseDeptName"  queryMode="single"  width="70"></t:dgCol>
            <t:dgCol title="业务员"  field="businesserName"  queryMode="single"  width="50"></t:dgCol>
            <t:dgCol title="客户代码"  field="cusNum"  queryMode="single"  width="60"></t:dgCol>
            <t:dgCol title="客户名称"  field="cusName"  queryMode="single"  width="145"></t:dgCol>
            <t:dgCol title="款号"  field="sampleNo"  queryMode="single"  width="80"></t:dgCol>
            <t:dgCol title="工艺种类"  field="gyzl"  dictionary="gylx" queryMode="single"  width="60"></t:dgCol>
            <t:dgCol title="款式大类"  field="proTypeName"  queryMode="single"  width="70"></t:dgCol>
            <t:dgCol title="状态"  field="state" formatterjs="formatColor"  queryMode="single"  width="90"></t:dgCol>

            <t:dgToolBar title="录入" icon="fa fa-plus" operationCode="add" url="emkOutForumController.do?goAdd&winTitle=录入出货通知单" funname="add" height="600" width="1100"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="fa fa-edit" operationCode="edit" url="emkOutForumController.do?goUpdate&winTitle=编辑出货通知单" funname="update" height="600" width="1100"></t:dgToolBar>
            <%--<t:dgToolBar title="提交" operationCode="submit" icon="fa fa-arrow-circle-up" funname="doSubmitV"></t:dgToolBar>--%>
            <t:dgToolBar title="删除" operationCode="delete"  icon="fa fa-remove" url="emkOutForumController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script src = "webpage/com/emk/produce/outforum/emkOutForumList.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
    });


    function formatColor(val,row){
        if(row.state=="1"){
            return '<span style="color:	#0000FF;">提交</span>';
        }else if(row.state=="2"){
            return '<span style="color:	#00FF00;">完成</span>';
        }else{
            return '创建';
        }
    }

    function doSubmitV() {
        var rowsData = $('#emkOutForumList').datagrid('getSelections');
        var ids = [];
        if (!rowsData || rowsData.length == 0) {
            tip('请选择需要提交的出货通知单');
            return;
        }
        for (var i = 0; i < rowsData.length; i++) {
            ids.push(rowsData[i].id);
        }
        $.dialog.confirm('您是否确定提交出货通知单?', function (r) {
            if (r) {
                $.ajax({
                    url: "emkOutForumController.do?doSubmit&ids=" + ids,
                    type: 'post',
                    cache: false,
                    data: null,
                    success: function (data) {
                        var d = $.parseJSON(data);
                        tip(d.msg);
                        if (d.success) {
                            $('#emkOutForumList').datagrid('reload');
                        }
                    }
                });
            }
        });
    }

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'emkOutForumController.do?upload', "emkOutForumList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("emkOutForumController.do?exportXls","emkOutForumList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("emkOutForumController.do?exportXlsByT","emkOutForumList");
    }

</script>
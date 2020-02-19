<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="emkMOutStorageList" checkbox="true" pagination="true" fitColumns="false" sortOrder="desc" sortName="outDate,ckNo"  title="出库单查询" actionUrl="emkMOutStorageController.do?datagrid&flag=1" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="操作" field="opt" frozenColumn="true"  width="100"></t:dgCol>
            <%--<t:dgCol title="预采购合同号"  field="yhtNum" queryMode="single" width="105"></t:dgCol>--%>
            <%--<t:dgCol title="采购合同号"  query="true"  field="htNum" queryMode="single" width="105"></t:dgCol>--%>
            <t:dgCol title="出库单号" query="true"   field="ckNo" queryMode="single" width="130"></t:dgCol>
            <t:dgCol title="仓库名称" query="true"   field="storageName" queryMode="single" width="130"></t:dgCol>
            <t:dgCol title="客户名称" field="cusName"  queryMode="single"  width="160"></t:dgCol>
            <t:dgCol title="出库类型" query="true"   field="type" replace="临时出库_1,正常出库_3" formatterjs="formatType" queryMode="single" width="90"></t:dgCol>

            <t:dgCol title="申请人" query="true"   field="appler" queryMode="single" width="80"></t:dgCol>
            <t:dgCol title="产品编号" query="true"  hidden="true"   field="proNum" queryMode="single" width="100"></t:dgCol>
            <t:dgCol title="销售日期"  field="outDate" query="true" queryMode="group"  width="90"></t:dgCol>
            <t:dgCol title="发货日期"  field="fhDate"  queryMode="single"  width="90"></t:dgCol>
            <t:dgCol title="状态"  field="state" formatterjs="formatColor"  queryMode="single"  width="90"></t:dgCol>

            <%--<t:dgCol title="客户代码" field="cusNum"  queryMode="single"  width="60"></t:dgCol>--%>
           <%--
            <t:dgCol title="联系人"  field="zlxr"  queryMode="single"  width="50"></t:dgCol>
            <t:dgCol title="当前流程节点"  field="processName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="开户行"  field="bankName" queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="开户账号"  field="bankAccount" queryMode="single"  width="80"></t:dgCol>
            <t:dgCol title="企业注册地址"  field="qyzcAddress" queryMode="single"  width="140"></t:dgCol>--%>
            <t:dgFunOpt funname="goToProcess(id,createBy,processName,state)" title="流程进度" operationCode="process" urlclass="ace_button"  urlStyle="background-color:#ec4758;" urlfont="fa-tasks"></t:dgFunOpt>

            <t:dgToolBar title="录入" icon="fa fa-plus" operationCode="add" url="emkMOutStorageController.do?goAdd0&type=1&winTitle=添加出库申请单" funname="add" height="600" width="1200"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="fa fa-edit" operationCode="edit" url="emkMOutStorageController.do?goUpdate0&type=1&winTitle=编辑出库申请单" funname="update" height="600" width="1200"></t:dgToolBar>
            <t:dgToolBar title="查看" icon="fa fa-search" operationCode="look" url="emkMOutStorageController.do?goUpdate0&type=1&winTitle=查看出库申请单" funname="detail" height="600" width="1200"></t:dgToolBar>

            <t:dgToolBar title="提交" operationCode="submit" icon="fa fa-arrow-circle-up" funname="doSubmitV"></t:dgToolBar>
            <t:dgToolBar title="删除" operationCode="delete"  icon="fa fa-remove" url="emkMOutStorageController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
            <t:dgToolBar title="导出" operationCode="exp" icon="fa fa-arrow-circle-right" funname="ExportXls"></t:dgToolBar>
            <t:dgToolBar title="导出PDF" icon="fa fa-arrow-circle-right"  funname="doPrintPDF"></t:dgToolBar>
            <t:dgToolBar title="打印" icon="fa fa-print" url="emkMInStorageController.do?goUpdate" funname="printPreview"></t:dgToolBar>

        </t:datagrid>
    </div>
</div>
<script src = "webpage/com/emk/bound/moutstorage/emkMOutStorageList.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
        $("#emkMOutStorageListtb").find("input[name='outDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
        $("#emkMOutStorageListtb").find("input[name='outDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
    });

    function printPreview(){
        width = window.top.document.body.offsetWidth*0.85;
        height =window.top.document.body.offsetHeight-100;
        var rowsData = $('#emkMOutStorageList').datagrid('getSelections');
        var ids = [];
        if (!rowsData || rowsData.length == 0) {
            tip('请选择需要打印的出库申请单');
            return;
        }
        openwindow("打印预览",'emkMOutStorageController.do?jump&r=qtPrintSheetFrm&url=emkMOutStorageController.do?printPreview&ckNo='+rowsData[0].ckNo,"hmSalaryList",width,height);
    }

    function formatType(val,row){
        if(row.type=="3"){
            return '<span style="color:	#0000FF;">正常出库</span>';
        }else if(row.type=="1"){
            return '<span style="color:	#FF0000;">临时出库</span>';
        }
    }
    function formatColor(val,row){
        if(row.state=="1"){
            return '<span style="color:	#0000FF;">已提交</span>';
        }else if(row.state=="2"){
            return '<span style="color:	#00FF00;">完成</span>';
        }else if(row.state=="24"){
            return '<span style="color:	#0000FF;">采购员通过</span>';
        }else{
            return '创建';
        }
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
        console.log(state);
        if(state == '0'){
            processNameVal = '出库申请单';
        }else if(state == '1'){
            processNameVal = '采购员审核';
        }else if(state == '24'){
            processNameVal = '核减';
        }else if(state == '2'){
            processNameVal = '完成';
        }
        createdetailwindow("流程进度--当前环节：" + processNameVal, "flowController.do?goProcess&state="+state+"&node="+node+"&processUrl=com/emk/bound/moutstorage/emkMOutStorage-process&id=" + id, 1230, height);

    }
    function doPrintPDF() {
        var rowsData = $('#emkMOutStorageList').datagrid('getSelections');
        var ids = [];
        if (!rowsData || rowsData.length == 0) {
            tip('请选择需要导出PDF的原料面料销售申请单');
            return;
        }
        for ( var i = 0; i < rowsData.length; i++) {
            ids.push(rowsData[i].id);
        }
        $.dialog.confirm('您是否确定导出PDF的原料面料销售申请单?', function(r) {
            if (r) {
                window.location.href = "emkMOutStorageController.do?doPrintPDFForOut&ids="+ids;
            }
        });
    }
    function doSubmitV() {
        var rowsData = $('#emkMOutStorageList').datagrid('getSelections');
        var ids = [];
        if (!rowsData || rowsData.length == 0) {
            tip('请选择需要提交的销售申请单');
            return;
        }
        for ( var i = 0; i < rowsData.length; i++) {
            ids.push(rowsData[i].id);
        }

        $.dialog.confirm('您是否确定提交销售申请单?', function(r) {
            if (r) {
                var index = layer.load(1, {
                    skin:"layui-layer-sys1",
                    shade: [0.1,'#fff'] //0.1透明度的白色背景
                });
                $.ajax({
                    url : "emkMOutStorageController.do?doSubmitForOut&ids="+ids,
                    type : 'post',
                    cache : false,
                    data: null,
                    success : function(data) {
                        layer.close(index);
                        var d = $.parseJSON(data);
                        if (d.success) {
                            layer.msg(d.msg);
                            $('#emkMOutStorageList').datagrid('reload');
                        }else{
                            layer.alert(d.msg);
                        }
                    }
                });
            }
        });
    }

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'emkMOutStorageController.do?upload', "emkMOutStorageList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("emkMOutStorageController.do?exportXls&type=1,3","emkMOutStorageList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("emkMOutStorageController.do?exportXlsByT","emkMOutStorageList");
    }

</script>
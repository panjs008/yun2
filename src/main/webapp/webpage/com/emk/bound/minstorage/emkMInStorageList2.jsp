<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="emkMInStorageList" checkbox="true" singleSelect="true" pagination="true" pageSize="20" fitColumns="true" sortOrder="desc" sortName="a01a03a07" title="查询条件" actionUrl="emkMInStorageController.do?datagrid&flag=1" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>

            <c:forEach var="list" items="${headcategoryEntities}" varStatus="status">
                <c:if test="${list.code ne 'a01a03a06' && list.code ne 'a01a03a11' && list.code ne 'a01a03a08' }">
                    <t:dgCol title="${list.name}"  field="${list.code}" queryMode="single" dictionary="${list.code eq 'a01a03a09' ? 'paytype':''}" query="${list.queryed eq 0 ? 'true':'false'}"  width="${fn:contains(list.name,'单号') ? '120':( list.column_type eq '4' ? '210':(list.column_type eq '0' || list.column_type eq '2') ? '50':'100')}"></t:dgCol>
                </c:if>
                <c:if test="${list.code eq 'a01a03a06'}">
                    <t:dgCol title="供应商"  field="gys"   query="true" queryMode="single"  width="180"></t:dgCol>
                    <t:dgCol title="业务员"  field="businesser"   queryMode="single"  width="70"></t:dgCol>

                </c:if>
                <c:if test="${list.code eq 'a01a03a11' }">
                    <t:dgCol title="进货仓库"  field="storageName"  query="true" queryMode="single"  width="120"></t:dgCol>
                </c:if>
            </c:forEach>
            <t:dgCol title="产品编号" query="true"  hidden="true"   field="proNum" queryMode="single" width="100"></t:dgCol>
            <t:dgCol title="商品数量"  field="total"  queryMode="single"  width="80"></t:dgCol>
            <t:dgCol title="金额"  field="money"  queryMode="single"  width="80"></t:dgCol>

            <%--<t:dgCol title="出货日期"  field="outDate"  queryMode="single"  width="80"></t:dgCol>--%>
            <t:dgCol title="状态"  field="state" formatterjs="formatColor"  queryMode="single"  width="80"></t:dgCol>
            <t:dgCol title="付款状态"  field="payState" formatterjs="formatColor2"  queryMode="single"  width="80"></t:dgCol>
            <%--<t:dgCol title="收货状态"  field="recevieState" formatterjs="formatColor3"  queryMode="single"  width="80"></t:dgCol>--%>

            <t:dgFunOpt funname="goToProcess(id,createBy,processName,state)" title="流程进度" urlclass="ace_button"  urlStyle="background-color:#ec4758;" urlfont="fa-tasks"></t:dgFunOpt>

            <t:dgToolBar title="录入" icon="fa fa-plus" operationCode="add"  url="emkMInStorageController.do?goAdd2&winTitle=添加采购订单" funname="add" height="600" width="1200"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="fa fa-edit" operationCode="edit" url="emkMInStorageController.do?goUpdate2&winTitle=编辑采购订单" funname="update" height="600" width="1200"></t:dgToolBar>
            <t:dgToolBar title="查看" icon="fa fa-search" operationCode="look"  url="emkMInStorageController.do?goUpdate2&winTitle=查看采购订单" funname="detail" height="600" width="1200"></t:dgToolBar>

            <t:dgToolBar title="完成"  icon="fa fa-check" operationCode="submit" funname="doFinish"></t:dgToolBar>
            <t:dgToolBar title="付款"  icon="fa fa-rmb" operationCode="paysubmit" url="emkMInStorageController.do?goPay&winTitle=采购入库单付款" funname="update" height="200" width="400"></t:dgToolBar>

            <t:dgToolBar title="删除"   icon="fa fa-remove" operationCode="delete" url="emkMInStorageController.do?doBatchDelForOrder" funname="deleteALLSelect"></t:dgToolBar>
            <t:dgToolBar title="导出"  icon="fa fa-arrow-circle-right" operationCode="exp" funname="ExportXls"></t:dgToolBar>
            <%--<t:dgToolBar title="导出PDF" icon="fa fa-arrow-circle-right"  funname="doPrintPDF"></t:dgToolBar>--%>
            <t:dgToolBar title="表头列"  icon="fa fa-gear" funname="setHeader"></t:dgToolBar>
            <t:dgToolBar title="列配置"  icon="fa fa-gear" funname="setCommon"></t:dgToolBar>

            <t:dgToolBar title="打印" icon="fa fa-print" url="emkMInStorageController.do?goUpdate" funname="printPreview"></t:dgToolBar>

        </t:datagrid>
    </div>
</div>
<script src = "webpage/com/emk/bound/minstorage/emkMInStorageList.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
        $("#emkMInStorageListtb").find("input[name='sendDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
        $("#emkMInStorageListtb").find("input[name='sendDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});

        $("#emkMInStorageListtb").find("input[name='kdDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
        $("#emkMInStorageListtb").find("input[name='kdDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
    });
    function setHeader(){
        $.dialog({
            content: 'url:hmCategoryController.do?for&forColumn&parentCode=A01A03',
            zIndex: getzIndex(),
            title : '表头列配置',
            cache:false,
            lock : true,
            width: 500,
            height: 580,
            cancelVal: '关闭',
            cancel: function(){
                location.reload();
            },
        });
    }
    function setCommon(){
        $.dialog({
            content: 'url:hmCategoryController.do?for&forColumn&parentCode=A01A14',
            zIndex: getzIndex(),
            title : '明细列配置',
            cache:false,
            lock : true,
            width: 500,
            height: 580,
            cancelVal: '关闭',
            cancel: function(){
                location.reload();
            },
        });
    }
    function printPreview(){
        width = window.top.document.body.offsetWidth*0.85;
        height =window.top.document.body.offsetHeight-100;
        var rowsData = $('#emkMInStorageList').datagrid('getSelections');
        var ids = [];
        if (!rowsData || rowsData.length == 0) {
            tip('请选择需要打印的采购订单');
            return;
        }
        openwindow("打印预览",'emkMInStorageController.do?jump&r=qtPrintSheetFrm&url=emkMInStorageController.do?printPreview&rkNo='+rowsData[0].rkNo,"hmSalaryList",width,height);
    }

    function formatColor(val,row){
        if(row.state=="2"){
            return '<span style="color:	#0000FF;">已完成</span>';
        }else if(row.state=="0"){
            return '<span style="color:	#FF0000;">创建</span>';
        }
    }
    function formatColor2(val,row){
        if(row.payState=="1"){
            return '<span style="color:	#0000FF;">已付款</span>';
        }else if(row.payState=="0"){
            return '<span style="color:	#FF0000;">未付款</span>';
        }
    }
    function formatColor3(val,row){
        if(row.recevieState=="1"){
            return '<span style="color:	#0000FF;">已收货</span>';
        }else if(row.recevieState=="0"){
            return '<span style="color:	#FF0000;">未收货</span>';
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
        if(state == '0'){
            processNameVal = '采购订单';
        }else if(state == '1'){
            processNameVal = '采购员审核';
        }else if(state == '2'){
            processNameVal = '完成';
        }
        createdetailwindow("流程进度--当前环节：" + processNameVal, "flowController.do?goProcess&state="+state+"&node="+node+"&processUrl=com/emk/bound/minstorage/emkMInStorage-process&id=" + id, 1300, height);

    }

    function doPrintPDF() {
        var rowsData = $('#emkMInStorageList').datagrid('getSelections');
        var ids = [];
        if (!rowsData || rowsData.length == 0) {
            tip('请选择需要导出PDF的采购订单');
            return;
        }
        for ( var i = 0; i < rowsData.length; i++) {
            ids.push(rowsData[i].id);
        }
        $.dialog.confirm('您是否确定导出PDF的采购订单?', function(r) {
            if (r) {
                window.location.href = "emkMInStorageController.do?doPrintPDF&ids="+ids;
            }
        });
    }
    function doFinish() {
        var rowsData = $('#emkMInStorageList').datagrid('getSelections');
        var ids = [];
        if (!rowsData || rowsData.length == 0) {
            tip('请选择需采购订单');
            return;
        }

        $.dialog.confirm('您是否确定完成采购订单?', function(r) {
            var index = layer.load(1, {
                skin:"layui-layer-sys1",
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            if (r) {
                $.ajax({
                    url : "emkMInStorageController.do?doFinish&id="+rowsData[0].id,
                    type : 'post',
                    cache : false,
                    data: null,
                    success : function(data) {
                        layer.close(index);
                        var d = $.parseJSON(data);
                        layer.msg(d.msg);
                        if (d.success) {
                            $('#emkMInStorageList').datagrid('reload');
                        }

                    }
                });
            }
        });
    }
    function doCancelApproval() {
        var rowsData = $('#emkMInStorageList').datagrid('getSelections');
        var ids = [];
        if (!rowsData || rowsData.length == 0) {
            tip('请选择需采购订单');
            return;
        }
        for ( var i = 0; i < rowsData.length; i++) {
            ids.push(rowsData[i].id);
        }

        $.dialog.confirm('您是否确定反审核采购订单?', function(r) {
            var index = layer.load(1, {
                skin:"layui-layer-sys1",
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            if (r) {
                $.ajax({
                    url : "emkMInStorageController.do?doCancelApproval&ids="+ids,
                    type : 'post',
                    cache : false,
                    data: null,
                    success : function(data) {
                        layer.close(index);
                        var d = $.parseJSON(data);
                        layer.msg(d.msg);
                        if (d.success) {
                            $('#emkMInStorageList').datagrid('reload');
                        }

                    }
                });
            }
        });
    }
    function doPay() {
        var rowsData = $('#emkMInStorageList').datagrid('getSelections');
        var ids = [];
        if (!rowsData || rowsData.length == 0) {
            tip('请选择需采购订单');
            return;
        }
        for ( var i = 0; i < rowsData.length; i++) {
            ids.push(rowsData[i].id);
        }

        $.dialog.confirm('您是否确定付款采购订单?', function(r) {
            var index = layer.load(1, {
                skin:"layui-layer-sys1",
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            if (r) {
                $.ajax({
                    url : "emkMInStorageController.do?doPay&ids="+ids,
                    type : 'post',
                    cache : false,
                    data: null,
                    success : function(data) {
                        layer.close(index);
                        var d = $.parseJSON(data);
                        layer.msg(d.msg);
                        if (d.success) {
                            $('#emkMInStorageList').datagrid('reload');
                        }

                    }
                });
            }
        });
    }

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'emkMInStorageController.do?upload', "emkMInStorageList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("emkMInStorageController.do?exportXls","emkMInStorageList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("emkMInStorageController.do?exportXlsByT","emkMInStorageList");
    }

</script>
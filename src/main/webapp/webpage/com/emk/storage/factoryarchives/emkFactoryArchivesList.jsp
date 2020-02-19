<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="emkFactoryArchivesList" checkbox="false" pageSize="20" pagination="true" fitColumns="false" title="供应商查询"
                    actionUrl="emkFactoryArchivesController.do?datagrid" sortName="orgCode,archivesNo" sortOrder="asc" idField="id" fit="true" btnCls="bootstrap"
                    queryMode="group">
            <t:dgCol title="主键" field="id" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="创建人名称" field="createName" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称" field="createBy" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="创建日期" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="single"
                     width="120"></t:dgCol>
            <t:dgCol title="所属部门" field="sysOrgCode" hidden="true" queryMode="single" width="150"></t:dgCol>
          <%--  <t:dgCol title="档案编号" query="true" field="archivesNo" queryMode="single"
                     width="90"></t:dgCol>--%>
            <t:dgCol title="供应商代码" query="true" field="companyCode" queryMode="single" width="90"></t:dgCol>
            <t:dgCol title="供应商名称" query="true" field="companyNameZn" queryMode="single" width="200"></t:dgCol>
            <t:dgCol title="助记码" field="zjm" queryMode="single" width="80"></t:dgCol>
            <t:dgCol title="部门代码" field="orgCode" hidden="true" queryMode="single" width="80"></t:dgCol>

            <t:dgCol title="省份" field="shengFen" dictionary="t_s_category,code,name" queryMode="single"
                     width="70"></t:dgCol>
            <t:dgCol title="城市" field="chengShi" dictionary="t_s_category,code,name" queryMode="single"
                     width="70"></t:dgCol>
            <t:dgCol title="片区" field="pianQu" dictionary="t_s_category,code,name" queryMode="single"
                     width="70"></t:dgCol>
            <t:dgCol title="地址" field="addressZn" queryMode="single" width="150"></t:dgCol>
            <t:dgCol title="联系人" field="primaryContact" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="手机号码" field="primaryContactTel" queryMode="single" width="110"></t:dgCol>
            <t:dgCol title="固定电话" field="gdhm" queryMode="single" width="110"></t:dgCol>

            <t:dgCol title="邮箱" field="primaryContactEmail" queryMode="single" width="110"></t:dgCol>
            <t:dgCol title="期初应付" field="bcqkMoney" queryMode="single" width="80"></t:dgCol>
            <%--<t:dgCol title="累计欠款" field="ljqkMoney" queryMode="single" width="80"></t:dgCol>--%>

            <t:dgToolBar title="录入" icon="fa fa-plus" url="emkFactoryArchivesController.do?goAdd&winTitle=录入供应商"
                         funname="add" width="1100" height="580"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="fa fa-edit" url="emkFactoryArchivesController.do?goUpdate&winTitle=编辑供应商"
                         funname="update" width="1100" height="580"></t:dgToolBar>
            <%--<t:dgToolBar title="提交" operationCode="submit" icon="fa fa-arrow-circle-up"  funname="doSubmitV"></t:dgToolBar>--%>
            <t:dgToolBar title="删除" icon="fa fa-remove" url="emkFactoryArchivesController.do?doBatchDel"
                         funname="deleteALLSelect"></t:dgToolBar>
            <t:dgToolBar title="证件管理" icon="fa fa-arrow-circle-up" url="" funname="cert"></t:dgToolBar>

            <t:dgToolBar title="查看" icon="fa fa-search" url="emkFactoryArchivesController.do?goUpdate" funname="detail"
                         width="1100" height="580"></t:dgToolBar>
            <t:dgToolBar title="导入" icon="fa fa-arrow-circle-left" funname="ImportXls"></t:dgToolBar>
            <t:dgToolBar title="导出" icon="fa fa-arrow-circle-right" funname="ExportXls"></t:dgToolBar>
            <%--<t:dgToolBar title="导出PDF" icon="fa fa-arrow-circle-right" funname="doPrintPDF"></t:dgToolBar>--%>

        </t:datagrid>
    </div>
</div>
<script src="webpage/com/emk/storage/factoryarchives/emkFactoryArchivesList.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
    });

    //证件
    function cert() {
        var rowsData = $('#emkFactoryArchivesList').datagrid('getSelections');
        var ids = [];
        if (!rowsData || rowsData.length == 0) {
            layer.msg('请选择数据');
            return;
        }
        if (rowsData.length >1) {
            layer.msg('只能选择一条数据');
            return;
        }
        $.dialog({
            content: 'url:emkCertificateController.do?list&gysId='+rowsData[0].id,
            zIndex: getzIndex(),
            title : '证件管理',
            cache:false,
            lock : true,
            width: 1200,
            height: 600
        });
    }

    function doPrintPDF() {
        var rowsData = $('#emkFactoryArchivesList').datagrid('getSelections');
        var ids = [];
        if (!rowsData || rowsData.length == 0) {
            tip('请选择需要导出PDF的供应商');
            return;
        }
        for (var i = 0; i < rowsData.length; i++) {
            ids.push(rowsData[i].id);
        }
        $.dialog.confirm('您是否确定导出PDF的供应商?', function (r) {
            if (r) {
                window.location.href = "emkFactoryArchivesController.do?doPrintPDF&ids=" + ids;
            }
        });
    }

    function formatColor(val, row) {
        if (row.state == "1") {
            return '<span style="color:	#0000FF;">已提交</span>';
        }else if (row.state == "2") {
            return '<span style="color:	#00FF00;">完成</span>';
        }else if (row.state == "3") {
            return '<span style="color:	#0000FF;">业务经理通过</span>';
        }else if(row.state=="4"){
            return '<span style="color:	#0000FF;">回退业务经理</span>';
        }else if(row.state=="21"){
            return '<span style="color:	#0000FF;">回退业务员</span>';
        }else if(row.state=="48"){
            return '<span style="color:	#0000FF;">总经理通过</span>';
        }else if (row.state == "51") {
            return '<span style="color:	#0000FF;">验厂经理通过</span>';
        }else if (row.state == "52") {
            return '<span style="color:	#0000FF;">执行验厂</span>';
        }else if(row.state=="53"){
            return '<span style="color:	#0000FF;">验厂报告</span>';
        }else {
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
        if (createBy == "${CUR_USER.userName}" || ${CUR_USER.userName eq 'admin'}) {
            if (state == '0') {
                createwindow("流程进度--当前环节：工厂信息表", "flowController.do?goProcess&node=" + node + "&processUrl=com/emk/storage/factoryarchives/emkFactoryArchives-process&id=" + id, 1300, height);
            } else {
                createdetailwindow("流程进度--当前环节：" + processNameVal, "flowController.do?goProcess&node=" + node + "&state="+state+"&processUrl=com/emk/storage/factoryarchives/emkFactoryArchives-process&id=" + id, 1300, height);
            }
        }
    }

    function doSubmitV() {
        var rowsData = $('#emkFactoryArchivesList').datagrid('getSelections');
        var ids = [];
        if (!rowsData || rowsData.length == 0) {
            tip('请选择需要提交的供应商');
            return;
        }
        for (var i = 0; i < rowsData.length; i++) {
            ids.push(rowsData[i].id);
        }
        $.dialog.confirm('您是否确定提交供应商?', function (r) {
            if (r) {
                $.ajax({
                    url: "emkFactoryArchivesController.do?doSubmit&ids=" + ids,
                    type: 'post',
                    cache: false,
                    data: null,
                    success: function (data) {
                        var d = $.parseJSON(data);
                        tip(d.msg);
                        if (d.success) {
                            $('#emkFactoryArchivesList').datagrid('reload');
                        }
                    }
                });
            }
        });
    }
    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'emkFactoryArchivesController.do?upload', "emkFactoryArchivesList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("emkFactoryArchivesController.do?exportXls", "emkFactoryArchivesList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("emkFactoryArchivesController.do?exportXlsByT", "emkFactoryArchivesList");
    }

</script>
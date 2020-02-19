<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>报修单</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script>
        $(document).ready(function(){
            var height =window.top.document.body.offsetHeight*0.79;
            //$("#proFrm").css("height",height);
            $("#orderFrm").css("height",height);

        });

    </script>

</head>
<body>
<t:tabs id="repairTabId" iframe="false" heigth="600px" tabPosition="top" fit="true" >
    <t:tab title="任务处理" id="orderFrm"  heigth="600px"  width="100%" href="emkMaterialPactController.do?goWork2&id=${param.id}" icon="fa fa-crosshairs"></t:tab>
    <t:tab title="基本信息" id="orderFrm"  heigth="600px"  width="100%" iframe="emkMaterialPactController.do?goUpdate2&id=${param.id}" icon="fa fa-calendar"></t:tab>
    <%--<t:tab title="任务处理" id="workFrm"  heigth="480px"  width="100%" icon="" href="uRepairController.do?goWork&id=${param.id}"></t:tab>--%>
    <t:tab title="流程图" id="proFrm"  heigth="600px" width="100%" icon="fa fa-sitemap" iframe="flowController.do?process&processUrl=com/emk/bill/materialpact/process&id=${param.id}"></t:tab>
</t:tabs>

</body>


<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>报修单</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script>
        $(document).ready(function(){
            var height =window.top.document.body.offsetHeight-140;
            $("#orderFrm").css("height",height);
            $("#baseFrm").css("height",height-10);
            $("#processFrm").css("height",height-10);
        });
    </script>

</head>
<body>
<t:tabs id="inStorageTabId" iframe="false" heigth="${param.hVal}" tabPosition="top" fit="true" >
    <t:tab title="任务处理" id="orderFrm"  heigth="600px"  width="100%" href="emkMInStorageController.do?goWork&node=${param.node}&id=${param.id}" icon="fa fa-crosshairs"></t:tab>
    <t:tab title="基本信息" id="baseFrm"  heigth="600px"  width="100%" iframe="emkMInStorageController.do?goUpdate2&id=${param.id}" icon="fa fa-calendar"></t:tab>
    <t:tab title="流程图" id="processFrm"  heigth="600px" width="100%" icon="fa fa-sitemap" iframe="flowController.do?process&processUrl=com/emk/bound/minstorage/process&id=${param.id}"></t:tab>
</t:tabs>

</body>


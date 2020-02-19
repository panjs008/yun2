<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>出货流程</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script>
        $(document).ready(function(){
            var height =window.top.document.body.offsetHeight*0.79;
            //$("#proFrm").css("height",height);
            $("#cgscjdbFrm").css("height",height);
            $("#dczzdFrm").css("height",height);
            $("#yhsqdFrm").css("height",height);
            $("#cktzdFrm").css("height",height);
            $("#lctzdFrm").css("height",height);
            $("#testFrm").css("height",height);
        });
        function uploadSuccess0(d,file,response){
            var src = d.attributes.url;
            $("#fileNameUrl").val(d.attributes.url);
            $("#fileName").val(d.attributes.name);
            $("#fileNameId").html("[<a href=\"javascript:downloadFile('"+d.attributes.url+"')\">"+d.attributes.name+"</a>]");
        }
        function downloadFile(url) {
            window.open("${webRoot}/"+url);
        }
        function findDetail(photoUrl) {
            $.dialog({
                content: 'url:emkEnquiryController.do?photo&photoUrl='+photoUrl,
                zIndex: getzIndex(),
                title : "查看",
                lock : true,
                width:900,
                height: 500,
                opacity : 0.3,
                cache:false,
                lock : true,
                cache:false,
                max: true,
                min: true,
                drag: true,
                resize: false
            });
        }
    </script>

</head>
<body>
<t:tabs id="mcTabId" iframe="false" heigth="${param.hVal}" tabPosition="top" fit="true" >
    <c:if test="${param.node eq 'cgscjdbTask' || param.node eq 'cgTask' || param.node eq 'ranTask' || param.node eq 'caiTask' || param.node eq 'ylblTask' || param.node eq 'fengTask' || param.node eq 'bzTask'}">
        <t:tab title="任务处理" id="orderFrm"  heigth="600px"  width="100%" iframe="emkProduceController.do?goWork&node=${param.node}&id=${param.id}" icon="fa fa-crosshairs"></t:tab>
        <t:tab title="采购生产进度表" id="cgscjdbFrm"  heigth="${param.hVal}"  width="100%" href="emkProduceController.do?goUpdate2&node=${param.node}&hVal2=${param.hVal2}&hVal=${param.hVal}&id=${param.id}" icon="fa fa-calendar"></t:tab>
    </c:if>
    <c:if test="${param.node ne 'cgscjdbTask' && param.node ne 'cgTask' && param.node ne 'ranTask' && param.node ne 'caiTask'  && param.node ne 'ylblTask'  && param.node ne 'fengTask' && param.node ne 'bzTask' && param.node ne 'dctzdTask' }">
        <t:tab title="任务处理" id="orderFrm"  heigth="600px"  width="100%" href="emkProduceController.do?goWork&node=${param.node}&id=${param.id}" icon="fa fa-crosshairs"></t:tab>
        <t:tab title="采购生产进度表" id="cgscjdbFrm"  heigth="${param.hVal}"  width="100%" iframe="emkProduceController.do?goUpdate2&node=${param.node}&hVal2=${param.hVal2}&hVal=${param.hVal}&id=${param.id}" icon="fa fa-calendar"></t:tab>
    </c:if>
    <c:if test="${param.node eq 'dctzdTask' && (param.state eq '76' or param.state eq '78')}">
        <t:tab title="任务处理" id="orderFrm"  heigth="600px"  width="100%" iframe="emkProduceController.do?goWork&node=${param.node}&id=${param.id}" icon="fa fa-crosshairs"></t:tab>
        <t:tab title="采购生产进度表" id="cgscjdbFrm"  heigth="${param.hVal}"  width="100%" iframe="emkProduceController.do?goUpdate2&node=${param.node}&hVal2=${param.hVal2}&hVal=${param.hVal}&id=${param.id}" icon="fa fa-calendar"></t:tab>
        <t:tab title="测试单" id="testFrm"  heigth="${param.hVal}"  width="100%" iframe="emkTestController.do?goUpdate3&node=${param.node}&hVal2=${param.hVal2}&hVal=${param.hVal}&id=${param.id}" icon="fa fa-calendar"></t:tab>
        <t:tab title="订舱通知单" id="dczzdFrm"  heigth="${param.hVal}"  width="100%" href="emkCargoSpaceController.do?goUpdate2&node=${param.node}&hVal2=${param.hVal2}&hVal=${param.hVal}&id=${param.id}" icon="fa fa-calendar"></t:tab>
    </c:if>
    <c:if test="${param.state eq '2' || param.node eq 'csTask' || param.node eq 'fengTask' || param.node eq 'bzTask'  || param.node eq 'cyTask'}">
        <t:tab title="测试单" id="testFrm"  heigth="${param.hVal}"  width="100%" iframe="emkTestController.do?goUpdate3&node=${param.node}&hVal2=${param.hVal2}&hVal=${param.hVal}&id=${param.id}" icon="fa fa-calendar"></t:tab>
    </c:if>
    <c:if test="${param.state eq '2' || param.node eq 'lctzdTask' || param.node eq 'ywgdshTask' || param.node eq 'cwyTask' || param.node eq 'yhsqbTask' || param.node eq 'ywjlzfhTask'  || param.node eq 'cwzfhTask' || param.node eq 'zjlpzTask' || param.node eq 'cwzfhTask2' || param.node eq 'ckzhfxTask' || param.node eq 'ysyfTask'}">
        <t:tab title="测试单" id="testFrm"  heigth="${param.hVal}"  width="100%" iframe="emkTestController.do?goUpdate3&node=${param.node}&hVal2=${param.hVal2}&hVal=${param.hVal}&id=${param.id}" icon="fa fa-calendar"></t:tab>
        <t:tab title="订舱通知单" id="dczzdFrm"  heigth="${param.hVal}"  width="100%" iframe="emkCargoSpaceController.do?goUpdate2&node=${param.node}&hVal2=${param.hVal2}&hVal=${param.hVal}&id=${param.id}" icon="fa fa-calendar"></t:tab>
    </c:if>
    <c:if test="${param.state eq '2' || param.node eq 'lctzdTask'|| param.node eq 'yhsqbTask' || param.node eq 'cktzdTask' || param.node eq 'ywgdshTask' || param.node eq 'ywjlzfhTask'  || param.node eq 'cwzfhTask' || param.node eq 'zjlpzTask' || param.node eq 'cwzfhTask2' || param.node eq 'ckzhfxTask' || param.node eq 'ysyfTask'}">
        <t:tab title="验货申请单" id="yhsqdFrm"  heigth="${param.hVal}"  width="100%" iframe="emkCheckController.do?goUpdate3&node=${param.node}&hVal2=${param.hVal2}&hVal=${param.hVal}&id=${param.id}" icon="fa fa-calendar"></t:tab>
    </c:if>
    <c:if test="${param.state eq '2' || param.node eq 'lctzdTask'|| param.node eq 'cktzdTask' || param.node eq 'ywgdshTask' || param.node eq 'ywjlzfhTask'  || param.node eq 'cwzfhTask' || param.node eq 'zjlpzTask' || param.node eq 'cwzfhTask2' || param.node eq 'ckzhfxTask' || param.node eq 'ysyfTask'}">
        <t:tab title="出口通知单" id="cktzdFrm"  heigth="${param.hVal}"  width="100%" iframe="emkOutForumController.do?goUpdate2&node=${param.node}&hVal2=${param.hVal2}&hVal=${param.hVal}&id=${param.id}" icon="fa fa-calendar"></t:tab>
    </c:if>
    <c:if test="${param.state eq '2' || param.node eq 'cwzfhTask2' || param.node eq 'ckzhfxTask' || param.node eq 'ysyfTask'}">
        <t:tab title="离厂通知单" id="lctzdFrm"  heigth="${param.hVal}"  width="100%" iframe="emkLeaveFactoryController.do?goUpdate2&node=${param.node}&hVal2=${param.hVal2}&hVal=${param.hVal}&id=${param.id}" icon="fa fa-calendar"></t:tab>
    </c:if>
    <t:tab title="流程图" id="proFrm"  heigth="600px" width="100%" icon="fa fa-sitemap" iframe="flowController.do?process&processUrl=com/emk/produce/produce/process&id=${param.id}"></t:tab>
</t:tabs>
</body>


<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>报单</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script>
        $(document).ready(function(){
            var height =window.top.document.body.offsetHeight-140;
            $("#workFrm").css("height",height);
            $("#baseFrm").css("height",height);
            $("#jbFrm").css("height",height);
            $("#otherFrm").css("height",height);
            $("#fhFrm").css("height",height);
            $("#thFrm").css("height",height);
            $("#fkFrm").css("height",height);
            $("#processFrm").css("height",height);
        });

    </script>

</head>
<body>
<t:tabs id="repairTabId" iframe="false" heigth="${param.hVal}"  tabPosition="top" fit="true" >
    <t:tab title="任务处理" id="workFrm"  heigth="600px"  width="100%" href="emkEnquiryController.do?goWork&node=${param.node}&id=${param.id}" icon="fa fa-crosshairs"></t:tab>
    <t:tab title="报单信息" id="baseFrm"  heigth="600px"  width="100%" iframe="emkEnquiryController.do?goUpdate2&id=${param.id}" icon="fa fa-calendar"></t:tab>
    <c:if test="${param.state > 1 || param.state eq 2}">
        <t:tab title="叫布信息" id="jbFrm"  heigth="600px"  width="100%" iframe="emkFormaterialController.do?goUpdate2&id=${param.id}" icon="fa fa-calendar"></t:tab>
    </c:if>
    <c:if test="${param.state > 5 || param.state eq 2}">
        <t:tab title="其他物料信息" id="otherFrm"  heigth="600px"  width="100%" iframe="emkFormaterialOtherController.do?goUpdate2&id=${param.id}" icon="fa fa-calendar"></t:tab>
    </c:if>
    <c:if test="${param.state > 8 || param.state eq 2}">
        <t:tab title="发货信息" id="fhFrm"  heigth="600px"  width="100%" iframe="emkOutOrderController.do?goUpdate2&id=${param.id}" icon="fa fa-calendar"></t:tab>
    </c:if>
    <c:if test="${(param.state > 11 && param.state != 18) || param.state eq 2}">
        <t:tab title="退货信息" id="thFrm"  heigth="600px"  width="100%" iframe="emkCancelOrderController.do?goUpdate2&id=${param.id}" icon="fa fa-calendar"></t:tab>
    </c:if>
    <c:if test="${(param.state > 14 && param.state != 18) || param.state eq 2}">
        <t:tab title="付款信息" id="fkFrm"  heigth="600px"  width="100%" iframe="emkPayController.do?goUpdate2&id=${param.id}" icon="fa fa-calendar"></t:tab>
    </c:if>
    <t:tab title="流程图" id="processFrm"  heigth="600px" width="100%" icon="fa fa-sitemap" iframe="flowController.do?process&processUrl=com/emk/storage/enquiry/process&id=${param.id}"></t:tab>
</t:tabs>

</body>


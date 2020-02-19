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
            $("#testcostFrm").css("height",height);
            $("#yfhzdFrm").css("height",height);
        });

    </script>

</head>
<body>
<%--<t:tabs id="repairTabId" iframe="false" heigth="600px" tabPosition="top" fit="true" >
    <t:tab title="任务处理" id="orderFrm"  heigth="600px"  width="100%" href="emkTestCostController.do?goWork&id=${param.id}" icon="fa fa-crosshairs"></t:tab>
    <t:tab title="基本信息" id="orderFrm"  heigth="600px"  width="100%" iframe="emkTestCostController.do?goUpdate2&id=${param.id}" icon="fa fa-calendar"></t:tab>
    &lt;%&ndash;<t:tab title="任务处理" id="workFrm"  heigth="480px"  width="100%" icon="" href="uRepairController.do?goWork&id=${param.id}"></t:tab>&ndash;%&gt;
    <t:tab title="流程图" id="proFrm"  heigth="600px" width="100%" icon="fa fa-sitemap" iframe="flowController.do?process&processUrl=com/emk/produce/testcost/process&id=${param.id}"></t:tab>
</t:tabs>--%>
<t:tabs id="mcTabId" iframe="false" heigth="${param.hVal}" tabPosition="top" fit="true" >
    <t:tab title="任务处理" id="orderFrm"  heigth="600px"  width="100%" href="emkTestCostController.do?goWork&node=${param.node}&id=${param.id}" icon="fa fa-crosshairs"></t:tab>
    <t:tab title="费用付款申请单" id="testcostFrm"  heigth="${param.hVal}"  width="100%" iframe="emkTestCostController.do?goUpdate2&hVal2=${param.hVal2}&hVal=${param.hVal}&id=${param.id}" icon="fa fa-calendar"></t:tab>
    <c:if test="${param.node eq 'fkhzdTask' || param.node eq 'zjlpfTask' || param.node eq 'cwzzTask' || param.state eq '2'}">
        <t:tab title="应付核准单" id="yfhzdFrm"  heigth="${param.hVal}"  width="100%" iframe="emkFinanceYfCheckController.do?goUpdate3&type=testCost&id=${param.id}" icon="fa fa-calendar"></t:tab>
    </c:if>
    <t:tab title="流程图" id="proFrm"  heigth="600px" width="100%" icon="fa fa-sitemap" iframe="flowController.do?process&processUrl=com/emk/produce/test/process&id=${param.id}"></t:tab>
</t:tabs>
</body>


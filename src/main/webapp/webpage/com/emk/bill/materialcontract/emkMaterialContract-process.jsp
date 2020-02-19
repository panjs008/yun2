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
            $("#fksqdFrm").css("height",height);
            $("#yfhzdFrm").css("height",height);

        });
        function setReruire(v){
            if(v == 0){
                $("#realName").attr("datatype","*");
                $("#userName").attr("datatype","*");
            }
            if(v == 1){
                $("#realName").removeAttr("datatype");
                $("#userName").removeAttr("datatype");
            }
        }


    </script>

</head>
<body>
<t:tabs id="mcTabId" iframe="false" heigth="${param.hVal}" tabPosition="top" fit="true" >
    <t:tab title="任务处理" id="orderFrm"  heigth="600px"  width="100%" href="emkMaterialContractController.do?goWork&node=${param.node}&id=${param.id}" icon="fa fa-crosshairs"></t:tab>
    <t:tab title="付款申请单" id="fksqdFrm"  heigth="${param.hVal}"  width="100%" iframe="emkMaterialContractController.do?goUpdate2&hVal2=${param.hVal2}&hVal=${param.hVal}&id=${param.id}" icon="fa fa-calendar"></t:tab>
    <c:if test="${param.node eq 'fkhzdTask' || param.node eq 'zjlpfTask' || param.node eq 'cwzzTask' || param.state eq '2'}">
        <t:tab title="应付核准单" id="yfhzdFrm"  heigth="${param.hVal}"  width="100%" iframe="emkFinanceYfCheckController.do?goUpdate3&type=materialContract&id=${param.id}" icon="fa fa-calendar"></t:tab>
    </c:if>
    <t:tab title="流程图" id="proFrm"  heigth="600px" width="100%" icon="fa fa-sitemap" iframe="flowController.do?process&processUrl=com/emk/bill/materialcontract/process&id=${param.id}"></t:tab>
</t:tabs>

</body>


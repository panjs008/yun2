<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>采购需求单</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script>
        $(document).ready(function(){
            var height =window.top.document.body.offsetHeight*0.79;
            $("#caigouFrm").css("height",height);
            $("#zsgxhtFrm").css("height",height);
            $("#fksqdFrm").css("height",height);
            $("#rksqdFrm").css("height",height);
            $("#cksqdFrm").css("height",height);

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
<t:tabs id="sampleRequiredTabId" iframe="false" heigth="${param.hVal}" tabPosition="top" fit="true" >
    <c:if test="${param.node ne 'htTask'}">
        <c:if test="${param.node ne 'fksqdTask'}">
            <t:tab title="任务处理" id="orderFrm"  heigth="600px"  width="100%" href="emkMaterialRequiredController.do?goWork&node=${param.node}&id=${param.id}" icon="fa fa-crosshairs"></t:tab>
        </c:if>
        <c:if test="${param.node eq 'fksqdTask'}">
            <t:tab title="任务处理" id="orderFrm"  heigth="600px"  width="100%" iframe="emkMaterialRequiredController.do?goWork&node=${param.node}&id=${param.id}" icon="fa fa-crosshairs"></t:tab>
        </c:if>
        <c:if test="${param.node eq 'orderTask' || param.node eq 'checkTask' || param.node eq 'cgyjsTask' || param.node eq 'ycghtTask' }">
            <t:tab title="采购需求单" id="caigouFrm"  heigth="${param.hVal}"  width="100%" iframe="emkMaterialRequiredController.do?goUpdate2&hVal2=${param.hVal2}&hVal=${param.hVal}&id=${param.id}" icon="fa fa-calendar"></t:tab>
        </c:if>
        <c:if test="${param.node eq 'cgbjlshTask' || param.node eq 'cgyzxTask'}">
            <t:tab title="采购需求单" id="caigouFrm"  heigth="${param.hVal}"  width="100%" iframe="emkMaterialRequiredController.do?goUpdate2&hVal2=${param.hVal2}&hVal=${param.hVal}&id=${param.id}" icon="fa fa-calendar"></t:tab>
            <t:tab title="正式购销合同" id="zsgxhtFrm"  heigth="${param.hVal}"  width="100%" href="emkMaterialPactController.do?goUpdate3&node=${param.node}&hVal2=${param.hVal2}&hVal=${param.hVal}&id=${param.id}" icon="fa fa-calendar"></t:tab>
        </c:if>
        <c:if test="${param.node eq 'fksqdTask' || param.node eq 'cgbjlshTask2' || param.node eq 'cwbshTask' || param.node eq 'zjlpfTask'
                            || param.node eq 'tzfhTask' || param.node eq 'ckyrkTask' || param.node eq 'cgyshTask' || param.node eq 'jsrkTask'
                            || param.node eq 'cksqdTask' || param.node eq 'ywjlfhTask' || param.node eq 'cgyzfhTask' || param.node eq 'ckTask' || param.state eq '2'}">
            <t:tab title="采购需求单" id="caigouFrm"  heigth="${param.hVal}"  width="100%" iframe="emkMaterialRequiredController.do?goUpdate2&id=${param.id}" icon="fa fa-calendar"></t:tab>
            <t:tab title="正式购销合同" id="zsgxhtFrm"  heigth="${param.hVal}"  width="100%" iframe="emkMaterialPactController.do?goUpdate3&id=${param.id}" icon="fa fa-calendar"></t:tab>
            <t:tab title="付款申请单" id="fksqdFrm"  heigth="${param.hVal}"  width="100%" iframe="emkMaterialContractController.do?goUpdate3&node=${param.node}&hVal2=${param.hVal2}&hVal=${param.hVal}&id=${param.id}" icon="fa fa-calendar"></t:tab>
        </c:if>
        <c:if test="${param.node eq 'cgyshTask' || param.node eq 'jsrkTask' || pparam.node eq 'cksqdTask' || param.node eq 'ywjlfhTask' || param.node eq 'cgyzfhTask' || param.node eq 'ckTask' || param.state eq '2'}">
            <t:tab title="入库申请单" id="rksqdFrm"  heigth="${param.hVal}"  width="100%" iframe="emkMInStorageController.do?goUpdate3&hVal3=${param.hVal2}&hVal=${param.hVal}&id=${param.id}" icon="fa fa-calendar"></t:tab>
        </c:if>
        <c:if test="${pparam.node eq 'cksqdTask' || param.node eq 'ywjlfhTask' || param.node eq 'cgyzfhTask' || param.node eq 'ckTask' || param.state eq '2'}">
            <t:tab title="出库申请单" id="cksqdFrm"  heigth="${param.hVal}"  width="100%" iframe="emkMOutStorageController.do?goUpdate3&hVal3=${param.hVal2}&hVal=${param.hVal}&id=${param.id}" icon="fa fa-calendar"></t:tab>
        </c:if>
    </c:if>
    <c:if test="${param.node eq 'htTask'}">
        <t:tab title="任务处理" id="orderFrm"  heigth="600px"  width="100%" iframe="emkMaterialRequiredController.do?goWork&node=${param.node}&id=${param.id}" icon="fa fa-crosshairs"></t:tab>
        <t:tab title="采购需求单" id="caigouFrm"  heigth="${param.hVal}"  width="100%" iframe="emkMaterialRequiredController.do?goUpdate2&hVal2=${param.hVal2}&hVal=${param.hVal}&id=${param.id}" icon="fa fa-calendar"></t:tab>
        <t:tab title="正式购销合同" id="caigouFrm"  heigth="${param.hVal}"  width="100%" href="emkMaterialPactController.do?goUpdate3&node=${param.node}&hVal2=${param.hVal2}&hVal=${param.hVal}&id=${param.id}" icon="fa fa-calendar"></t:tab>
    </c:if>
    <%--<t:tab title="任务处理" id="workFrm"  heigth="480px"  width="100%" icon="" href="uRepairController.do?goWork&id=${param.id}"></t:tab>--%>
    <t:tab title="流程图" id="proFrm"  heigth="600px" width="100%" icon="fa fa-sitemap" iframe="flowController.do?process&processUrl=com/emk/bill/materialrequired/process&id=${param.id}"></t:tab>
</t:tabs>

</body>


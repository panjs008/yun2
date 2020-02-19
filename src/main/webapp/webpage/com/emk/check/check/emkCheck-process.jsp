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
            $("#checkFrm").css("height",height);

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
    </script>

</head>
<body>
<t:tabs id="mcTabId" iframe="false" heigth="${param.hVal}" tabPosition="top" fit="true" >
    <t:tab title="任务处理" id="orderFrm"  heigth="600px"  width="100%" href="emkCheckController.do?goWork&node=${param.node}&id=${param.id}" icon="fa fa-crosshairs"></t:tab>
    <t:tab title="验货申请表" id="checkFrm"  heigth="${param.hVal}"  width="100%" iframe="emkCheckController.do?goUpdate2&hVal2=${param.hVal2}&hVal=${param.hVal}&id=${param.id}" icon="fa fa-calendar"></t:tab>
    <t:tab title="流程图" id="proFrm"  heigth="600px" width="100%" icon="fa fa-sitemap" iframe="flowController.do?process&processUrl=com/emk/check/check/process&id=${param.id}"></t:tab>
</t:tabs>
</body>


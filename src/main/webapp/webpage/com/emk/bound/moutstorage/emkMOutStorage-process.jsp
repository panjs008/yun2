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
    /*    function setchooseUser(flag){
            if(flag==1){
                $("#chooseUser").css("display","none");
               $("#userName").val("fwtadmin");
                $("#realName").val("fwtadmin");
            }else {
                $("#chooseUser").css("display","");
            }
        }
        function setchooseService(flag){
            if(flag==1){
                $("#spanId1").css("display","");
                $("#spanId2").css("display","none");
            }else {
                $("#spanId1").css("display","none");
                $("#spanId2").css("display","");
            }
        }
        function setchooseServiceYg(flag){
            if(flag==0){
                $("#spanId1").css("display","");
                $("#spanId2").css("display","none");
                $("#spanId3").css("display","none");
            }else if(flag==1){
                $("#spanId1").css("display","none");
                $("#spanId2").css("display","");
                $("#spanId3").css("display","none");
            }else {
                $("#spanId1").css("display","none");
                $("#spanId2").css("display","none");
                $("#spanId3").css("display","");
            }
        }*/
    </script>

</head>
<body>
<t:tabs id="outStorageTabId" iframe="false" heigth="${param.hVal}" tabPosition="top" fit="true" >
    <t:tab title="任务处理" id="orderFrm"  heigth="600px"  width="100%" href="emkMOutStorageController.do?goWork&node=${param.node}&id=${param.id}" icon="fa fa-crosshairs"></t:tab>
    <t:tab title="基本信息" id="baseFrm"  heigth="600px"  width="100%" iframe="emkMOutStorageController.do?goUpdate2&node=${param.node}&id=${param.id}" icon="fa fa-calendar"></t:tab>
    <t:tab title="流程图" id="processFrm"  heigth="600px" width="100%" icon="fa fa-sitemap" iframe="flowController.do?process&processUrl=com/emk/bound/moutstorage/process&id=${param.id}"></t:tab>
</t:tabs>
</body>


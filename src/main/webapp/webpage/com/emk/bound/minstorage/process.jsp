<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script>

</script>

<div id="orderDiv" title="流程图" style="width:100%;height:43%;align:center;overflow:hidden" >
    <iframe scrolling="no" id="processFrm" frameborder="0" style="margin-left: 70px;margin-top:-15px;"  src="flowController.do?showProcess&id=${param.id}&tableName=emk_m_in_storage" width="100%" height="100%"></iframe>
</div>
<div id="hisProcessDiv" title="" style="width:99%;height:57%;overflow:hidden;overflow-x: hidden;overflow-y: hidden" >
    <iframe scrolling="no" id="hisProcessFrm" frameborder="0" style="overflow-x: hidden;overflow-y: hidden"  src="flowController.do?processHis&sqlType=inStorage&id=${param.id}" width="100%" height="100%"></iframe>
</div>

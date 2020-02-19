<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script>
    $(document).ready(function() {
        var height = window.top.document.body.offsetHeight * 0.79;
        //$("#processFrm").css("height",height);
        //$("#processHisFrm").css("height",height*0.3);

    });
</script>

<div id="orderDiv" title="流程图" style="width:100%;height:43%;;overflow:hidden" >
    <iframe scrolling="no" id="processFrm" frameborder="0" style="margin-top: -10px;margin-left:70px;"  src="flowController.do?showProcess&id=${param.id}&tableName=emk_m_out_storage" width="100%" height="100%"></iframe>
</div>
<div id="hisProcessDiv" title="" style="width:99%;height:57%;overflow:hidden;overflow-x: hidden;overflow-y: hidden" >
    <iframe scrolling="no" id="hisProcessFrm" frameborder="0" style="overflow-x: hidden;overflow-y: hidden"  src="flowController.do?processHis&sqlType=outStorage&id=${param.id}" width="100%" height="100%"></iframe>
</div>

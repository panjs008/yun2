<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<div style="width:100%;height:95%" >
	<iframe id="printfrm" src="${webRoot}/${url}" width="100%" height="95%"
			style="border: 0px; line-height: 21px; background: #fff; " frameborder="no" border="0" scrolling="yes">
	</iframe>
</div>

<div class="ui_main" align="center">
	<div class="ui_buttons">
		<input class="ui_state_highlight" onClick="document.getElementById('printfrm').contentWindow.previewArea('');" value="打印" type="button">
		<input value="关闭" type="button" onclick="frameElement.api.close();">
	</div>
</div>

<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>导入运单列表</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<script type="text/javascript">
        //编写自定义JS代码

        function uploadSuccess(d,file,response){
            var src = d.attributes.url;
            $("#filesavename").val(d.attributes.url);
            $("#filename").val(d.attributes.name);
        }
        function uploadCallback(callback,inputId){
            var url = $("#fileUrl").val();
            var name= $("#fileName").val();
            callback(url,name,inputId);

        }

	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="hmStaffController.do?importExcel" tiptype="1" >
	<input id="filesavename" name="filesavename" type="hidden" value="">

	<table style="width: 100%" cellpadding="0" cellspacing="1" class="formtable">

		<tr>
			<td align="right" style="width: 20px">
				<label class="Validform_label">
					文件:
				</label>
			</td>
			<td class="value" style="width: 300px" >
				<input id="filename" name="filename" type="text" style="width: 250px" class="inputxt" readonly="readonly" >
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">文件</label>
			</td>
			<td class="value">
				<t:upload name="instruction" id="instruction" dialog="false" extend="*.xls;*.xlsx;" buttonText="上传文件" queueID="instructionfile" view="false" auto="true" uploader="systemController.do?saveFiles"  onUploadSuccess="uploadSuccess" >
				</t:upload>
			</td>
		</tr>
		<tr>
			<td colspan="3" id="instructionfile" class="value">
			</td>
		</tr>

	</table>


</t:formvalid>

</body>


<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>通用Excel导入${controller_name}</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<%@include file="/context/header2.jsp"%>
	<script src="${webRoot}/context/gys.js"></script>
	<script type="text/javascript" src="js/ajaxfileupload.js"></script>
	<script type="text/javascript">

		function importExcel(){
			if($("#fileNameUrl").val() == null || $("#fileNameUrl").val()==''){
				layer.msg('请上传文件');
				return false;
			}
			var index = layer.load(1, {
				skin:"layui-layer-sys1",
				shade: [0.1,'#fff'] //0.1透明度的白色背景
			});
			$.ajax({
				url:'emkFormaterialController.do?importExcel',
				type : 'post',
				cache : false,
				data:$("#formobj").serialize(),
				success : function(data) {
					var d = $.parseJSON(data);
					tip(d.msg);
					if (d.success) {
						W.document.location.reload(true);
					}
					layer.close(index);
				}
			});
		}
	</script>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj"  dialog="true" usePlugin="password" layout="table" action="" tiptype="1" >
	<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">
					布行名称:
				</label>
			</td>
			<td class="value">
				<input id="bhmc" name="bhmc" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">布行名称</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					开单日期:
				</label>
			</td>
			<td class="value">
				<input id="kdDate" name="kdDate" readonly value="${kdDate}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  type="text" style="width: 150px" class="Wdate"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">开单日期</label>
			</td>
		</tr>

		<tr>
			<td align="right">
				<label class="Validform_label">
					单号:
				</label>
			</td>
			<td class="value" colspan="3">
				<select class="form-control select2" id="orderNo" name="orderNo" datatype="*"  >
					<option value=''>请选择</option>
				</select>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">单号</label>
			</td>
		</tr>

		<tr>
			<td align="right">
				<label class="Validform_label">
					文件路径:
				</label>
			</td>
			<td class="value" colspan="3">
				<input type="hidden" id="saveFileName" name="fileName">
				<input type="hidden" id="fileNameUrl" name="fileNameUrl">
				<input name="files" id="uploadFile" onchange="saveFile('uploadControl.do?upload&fileDir=material','uploadFile','newFile','formobj','fileName','fileNameUrl');" style="width:300px;display: none" type="file" accept=".xls"  />
				<input id="newFile" type="text" style="width:50%;" readonly onclick="uploadClick('uploadFile')">
			</td>
		</tr>

	</table>
	<div style="float: right;margin-top:200px;">
		<input class="btn" type="button" value="确定" onclick="importExcel()" style="background:#18a689 none repeat scroll 0 0;height:30px;width:60px !important;border-radius:5px;color: #fff;" >
	</div>
</t:formvalid>
</body>
</html>

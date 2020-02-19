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
				url:'emkEnquiryController.do?importExcel',
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
					订单号:
				</label>
			</td>
			<td class="value" >
				<input id="enquiryNo" name="enquiryNo" value=""  readonly type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">订单号</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					下单日期:
				</label>
			</td>
			<td class="value">
				<input id="kdDate" name="kdDate" readonly value="${kdDate}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  type="text" style="width: 150px" class="Wdate"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">下单日期</label>
			</td>
		</tr>

		<tr>
			<td align="right">
				<label class="Validform_label">
					供应商:
				</label>
			</td>
			<td class="value">
				<select class="form-control select2" id="gysId"  datatype="*"  >
					<option value=''>请选择</option>
				</select>
				<input id="gysCode" name="gysCode" type="hidden" value="${emkProOrderPage.gysCode }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<input id="gys" name="gys" type="hidden"  value="${emkProOrderPage.gys }"  style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">供应商</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					最晚交货日期:
				</label>
			</td>
			<td class="value">
				<input id="ysDate" name="ysDate" readonly datatype="*" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'kdDate\');}',onpicked:setEndTime})"  type="text" style="width: 150px" class="Wdate"  />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">最晚交货日期</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					距交期剩余天数:
				</label>
			</td>
			<td class="value">
				<input id="levelDays" name="levelDays" readonly type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">距交期剩余天数</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					款号:
				</label>
			</td>
			<td class="value">
				<input id="sampleNo" name="sampleNo" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">款号</label>
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
				<input name="files" id="uploadFile" onchange="saveFile('uploadControl.do?upload&fileDir=order','uploadFile','newFile','formobj','fileName','fileNameUrl');" style="width:300px;display: none" type="file" accept=".xls"  />
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

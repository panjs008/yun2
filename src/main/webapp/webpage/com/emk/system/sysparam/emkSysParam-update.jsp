<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>参数表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  $(function(){

	  $.ajax({
		  url : "ymkCustomController.do?getCity&code=A01A05",
		  type : 'post',
		  cache : false,
		  data: null,
		  success : function(data) {
			  var d = $.parseJSON(data);
			  if (d.success) {
				  var msg = d.msg;
				  var dataItems = new Array(); //定义一数组
				  dataItems = d.obj.split(";"); //字符分割
				  //W.document.location.reload(true);
				  $('#shengFen').empty();
				  var option1 = '<option value="">--选择--</option>';
				  var firstJgmc;
				  for (i=0;i<dataItems.length ;i++ ) {
					  var dataitem = new Array(); //定义一数组
					  dataitem = dataItems[i].split(","); //字符分割
					  if(dataitem[0]!="") {
						  if(dataitem[0] == '${emkSysParamPage.shengFen}'){
							  option1 += '<option value='+dataitem[0]+' selected>'+dataitem[1]+'</option>';
						  }else{
							  option1 += '<option value='+dataitem[0]+'>'+dataitem[1]+'</option>';
						  }
					  }
				  }
				  $("#shengFen").append(option1);
				  if(${emkSysParamPage.chengShi ne '' && emkSysParamPage.chengShi ne null}){
					  $.ajax({
						  url : "ymkCustomController.do?getCity&code=${emkSysParamPage.shengFen}",
						  type : 'post',
						  cache : false,
						  data: null,
						  success : function(data) {
							  var d = $.parseJSON(data);
							  if (d.success) {
								  var msg = d.msg;
								  var dataItems = new Array(); //定义一数组
								  dataItems = d.obj.split(";"); //字符分割
								  //W.document.location.reload(true);
								  $('#chengShi').empty();
								  var option1='';
								  for (i=0;i<dataItems.length ;i++ ) {
									  var dataitem = new Array(); //定义一数组
									  dataitem = dataItems[i].split(","); //字符分割
									  if(dataitem[0] == '${emkSysParamPage.chengShi}'){
										  option1 += '<option value='+dataitem[0]+' selected>'+dataitem[1]+'</option>';
									  }else{
										  option1 += '<option value='+dataitem[0]+'>'+dataitem[1]+'</option>';
									  }
								  }
								  $("#chengShi").append(option1);

								  $.ajax({
									  url : "ymkCustomController.do?getCity&code=${emkSysParamPage.chengShi}",
									  type : 'post',
									  cache : false,
									  data: null,
									  success : function(data) {
										  var d = $.parseJSON(data);
										  if (d.success) {
											  var msg = d.msg;
											  var dataItems = new Array(); //定义一数组
											  dataItems = d.obj.split(";"); //字符分割
											  //W.document.location.reload(true);
											  $('#pianQu').empty();
											  var option1='';
											  for (i=0;i<dataItems.length ;i++ ) {
												  var dataitem = new Array(); //定义一数组
												  dataitem = dataItems[i].split(","); //字符分割
												  if(dataitem[0] == '${emkSysParamPage.pianQu}'){
													  option1 += '<option value='+dataitem[0]+' selected>'+dataitem[1]+'</option>';
												  }else{
													  option1 += '<option value='+dataitem[0]+'>'+dataitem[1]+'</option>';
												  }
											  }
											  $("#pianQu").append(option1);
										  }
									  }
								  });
							  }
						  }
					  });
				  }

			  }
		  }
	  });

	  $("#shengFen").change(function(){
		  $.ajax({
			  url : "ymkCustomController.do?getCity&code="+$("#shengFen").val(),
			  type : 'post',
			  cache : false,
			  data: null,
			  success : function(data) {
				  var d = $.parseJSON(data);
				  if (d.success) {
					  var msg = d.msg;
					  console.log("chengshi:"+ d.obj);
					  var dataItems = new Array(); //定义一数组
					  dataItems = d.obj.split(";"); //字符分割
					  //W.document.location.reload(true);
					  $('#chengShi').empty();
					  $('#pianQu').empty();

					  var option3='';
					  var firstJgmc;

					  for (i=0;i<dataItems.length ;i++ ) {
						  var dataitem = new Array(); //定义一数组
						  dataitem = dataItems[i].split(","); //字符分割
						  if(i == 0){
							  firstJgmc = dataitem[0];
						  }
						  if(dataitem[0]!="") {
							  option3 += '<option value='+dataitem[0]+'>'+dataitem[1]+'</option>';
						  }
					  }
					  $("#chengShi").append(option3);

					  $.ajax({
						  url : "ymkCustomController.do?getCity&code="+firstJgmc,
						  type : 'post',
						  cache : false,
						  data: null,
						  success : function(data) {
							  var d = $.parseJSON(data);
							  if (d.success) {
								  console.log("pianqu:"+ d.obj);

								  var msg = d.msg;
								  var dataItems = new Array(); //定义一数组
								  dataItems = d.obj.split(";"); //字符分割
								  //W.document.location.reload(true);
								  $('#pianQu').empty();
								  var option1='';
								  for (i=0;i<dataItems.length ;i++ ) {
									  var dataitem = new Array(); //定义一数组
									  dataitem = dataItems[i].split(","); //字符分割
									  if(dataitem[0]!="") {
										  option1 += '<option value='+dataitem[0]+'>'+dataitem[1]+'</option>';
									  }
								  }
								  $("#pianQu").append(option1);
							  }
						  }
					  });
				  }
			  }
		  });
	  });

	  $("#chengShi").change(function(){
		  $.ajax({
			  url : "ymkCustomController.do?getCity&code="+$("#chengShi").val(),
			  type : 'post',
			  cache : false,
			  data: null,
			  success : function(data) {
				  var d = $.parseJSON(data);
				  if (d.success) {
					  var msg = d.msg;
					  var dataItems = new Array(); //定义一数组
					  dataItems = d.obj.split(";"); //字符分割
					  //W.document.location.reload(true);
					  $('#pianQu').empty();
					  var option3='';

					  for (i=0;i<dataItems.length ;i++ ) {
						  var dataitem = new Array(); //定义一数组
						  dataitem = dataItems[i].split(","); //字符分割
						  if(dataitem[0]!="") {
							  option3 += '<option value='+dataitem[0]+'>'+dataitem[1]+'</option>';
						  }
					  }
					  $("#pianQu").append(option3);
				  }
			  }
		  });
	  });
  });
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkSysParamController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${emkSysParamPage.id }"/>
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								参数名称:
							</label>
						</td>
						<td class="value">
							<input id="paramName" name="paramName" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkSysParamPage.paramName}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">参数名称</label>
						</td>
					</tr>
					<c:if test="${!fn:contains(emkSysParamPage.paramName, '公司地址')}">
						<tr>
							<td align="right">
								<label class="Validform_label">
									参数值:
								</label>
							</td>
							<td class="value">
								<input id="paramValue" name="paramValue" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkSysParamPage.paramValue}'/>
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">参数值</label>
							</td>
						</tr>

					</c:if>

					<c:if test="${emkSysParamPage.paramName eq '公司地址（省）' || emkSysParamPage.paramName eq '公司地址（市）' || emkSysParamPage.paramName eq '公司地址（区、县）'}">
						<tr>
							<td align="right">
								<label class="Validform_label">
									公司地址（省）:
								</label>
							</td>
							<td class="value">
								<select id="shengFen" name="shengFen"  style="width:155px;">
									<option>--省--</option>
								</select>
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">省份</label>
							</td>
						</tr>
						<tr>
							<td align="right">
								<label class="Validform_label">
									公司地址（市）:
								</label>
							</td>
							<td class="value">
								<select id="chengShi" name="chengShi" style="width:155px;">
									<option>--市--</option>
								</select>
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">城市</label>
							</td>
						</tr>
						<tr>
							<td align="right">
								<label class="Validform_label">
									公司地址（区、县）:
								</label>
							</td>
							<td class="value">
								<select id="pianQu" name="pianQu" style="width:155px;">
									<option>--区、县--</option>
								</select>
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">片区</label>
							</td>
						</tr>
					</c:if>
					<tr>
						<td align="right">
							<label class="Validform_label">
								备注:
							</label>
						</td>
						<td class="value">
						    <input id="remark" name="remark" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkSysParamPage.remark}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
					</tr>


			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/emk/system/sysparam/emkSysParam.js"></script>

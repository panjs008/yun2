<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>邮箱数据</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  	$("#sendState").val("${eSendHtPage.sendState }");
  	$("#state").val("${eSendHtPage.state }");

	  function save(){
		  if($("#telphone").val() == ''){
			  layer.msg("电话必须填写");
			  return false;
		  }
		  if($("#telphone").val().length !=11){
			  layer.msg("请填写正确的电话");
			  return false;
		  }
		 /* if (!myreg.exec($("#telphone").val())){
			  layer.msg('请输入正确的手机号');
			  return false;
		  }*/
		  <c:if test="${param.mbbh eq '1.2'}">
			  if($("#zywh").val() == ''){
				  layer.msg("职业危害必须填写");
				  return false;
			  }
			  if($("#sgxx").val() == ''){
				  layer.msg("上岗信息必须填写");
				  return false;
			  }
		  </c:if>


		  $.ajax({
			  url : "eSendHtController.do?doUpdate2",
			  type : 'post',
			  cache : false,
			  data: $("#formobj").serializeArray(),
			  dataType : 'json',
			  success : function(data) {
				  console.log(data);
				  layer.msg(data.msg);
			  }
		  });

	  }
  </script>
	 <style>
		 .datagrid-toolbar {
			 height: auto;
			 padding: 2px 2px 2px 2px;
			 border-width: 0 0 1px 0;
			 border-style: solid;
		 }
	 </style>
 </head>
 <body>

		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="eSendHtController.do?doUpdate2" tiptype="1">
			<input id="id" name="id" type="hidden" value="${eSendHtPage.id }"/>
			<input id="state" name="state" type="hidden" value="${param.state }"/>
			<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								同事编号:
							</label>
						</td>
						<td class="value">
						    <input id="workNo" name="workNo" readonly type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${eSendHtPage.workNo}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">同事编号</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								同事姓名:
							</label>
						</td>
						<td class="value">
						    <input id="userName" name="userName" readonly type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${eSendHtPage.userName}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">同事姓名</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								续签期限:
							</label>
						</td>
						<td class="value">
							<%--<select id="xqqx" name="xqqx" disabled>
								<option value="0">固定期</option>
								<option value="1">无固定</option>
							</select>--%>
								<input id="xqqx" name="xqqx" type="text" readonly style="width: 150px" class="inputxt"   value='${eSendHtPage.xqqx}'/>

								<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">续签期限</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								经理姓名:
							</label>
						</td>
						<td class="value">
							<input id="manger" name="manger" readonly type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${eSendHtPage.manger}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">经理姓名</label>
						</td>
					</tr>


					<tr>
						<td align="right">
							<label class="Validform_label">
								同事电话:
							</label>
						</td>
						<td class="value" colspan="3">
							<input id="telphone" name="telphone" datatype="n"  type="text" style="width: 150px" class="inputxt"    value='${eSendHtPage.telphone}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">同事电话</label>
						</td>
					</tr>
					<c:if test="${param.mbbh eq '1.2'}">
						<tr>
							<td align="right">
								<label class="Validform_label">
									职业危害:
								</label>
							</td>
							<td class="value">
								<input id="zywh" name="zywh" datatype="*"  type="text" style="width: 150px" class="inputxt"    value='${eSendHtPage.zywh}'/>
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">职业危害</label>
							</td>

							<td align="right">
								<label class="Validform_label">
									上岗证:
								</label>
							</td>
							<td class="value">
								<input id="sgxx" name="sgxx" datatype="*"  type="text" style="width: 150px" class="inputxt"    value='${eSendHtPage.sgxx}'/>
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">上岗证</label>
							</td>
						</tr>
					</c:if>
					<%--<tr>
						<td align="right">
							<label class="Validform_label">
								地址:
							</label>
						</td>
						<td class="value" colspan="3"><textarea id="adress" datatype="*" style="width:90%;height:60px" class="inputxt" rows="5" name="adress">${eSendHtPage.adress}</textarea>
						</td>
					</tr>--%>
					<tr>
						<td colspan="4" align="center">
							<div style="padding: 3px; height: 35px;width:auto;border-color: #ddd" class="datagrid-toolbar" align="center">
								<input class="btn" type="button" value="提交" onclick="save()" style="background:#18a689 none repeat scroll 0 0;height:30px;width:90px !important;border-radius:5px;color: #fff;" onclick="save();">
							</div>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/emk/email/esendht/eSendHt.js"></script>

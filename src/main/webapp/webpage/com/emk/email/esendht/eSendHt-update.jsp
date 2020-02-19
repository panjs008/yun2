<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>邮箱数据</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  $(document).ready(function(){
	  $("#sendState").val("${eSendHtPage.sendState }");
	  $("#state").val("${eSendHtPage.state }");
  });

  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="eSendHtController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${eSendHtPage.id }"/>
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								同事编号:
							</label>
						</td>
						<td class="value">
						    <input id="workNo" name="workNo" type="text" style="width: 150px" class="inputxt"    value='${eSendHtPage.workNo}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">同事编号</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								同事姓名:
							</label>
						</td>
						<td class="value">
						    <input id="userName" name="userName" type="text" style="width: 150px" class="inputxt"    value='${eSendHtPage.userName}'/>
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
							<input id="xqqx" name="xqqx" type="text" style="width: 150px" class="inputxt"   value='${eSendHtPage.xqqx}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">续签期限</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								模板编号:
							</label>
						</td>
						<td class="value">
						    <input id="mbbh" name="mbbh" type="text" style="width: 150px" class="inputxt"    value='${eSendHtPage.mbbh}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">模板编号</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								经理姓名:
							</label>
						</td>
						<td class="value">
						    <input id="manger" name="manger" type="text" style="width: 150px" class="inputxt"    value='${eSendHtPage.manger}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">经理姓名</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								经理邮箱:
							</label>
						</td>
						<td class="value">
						    <input id="email" name="email" type="text" style="width: 150px" class="inputxt"    value='${eSendHtPage.email}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">经理邮箱</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								经理电话:
							</label>
						</td>
						<td class="value" colspan="3">
							<input id="mangerTelphone" name="mangerTelphone" value='${eSendHtPage.mangerTelphone}' datatype="*" type="text" style="width: 150px" class="inputxt"  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">经理电话</label>
						</td>

					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								发送时间:
							</label>
						</td>
						<td class="value">
							<input id="sendTime" name="sendTime" type="text" style="width: 150px" class="Wdate"  ignore="ignore"  value='${eSendHtPage.sendTime}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">发送时间</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								发送状态:
							</label>
						</td>
						<td class="value">
							<select id="sendState" name="sendState" readonly="readonly">
								<option value="0">创建</option>
								<option value="1">已发送</option>
							</select>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">发送状态</label>
						</td>

					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								回复时间:
							</label>
						</td>
						<td class="value">
							<input id="replyTime" name="replyTime" readonly="readonly" type="text" style="width: 150px" class="Wdate"  ignore="ignore"  value='${eSendHtPage.replyTime}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">回复时间</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								回复状态:
							</label>
						</td>
						<td class="value">
							<select id="state" name="state" readonly="readonly">
								<option value="0">创建</option>
								<option value="1">已确认</option>
								<option value="2">拒绝</option>
							</select>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">回复状态</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								电话:
							</label>
						</td>
						<td class="value">
							<input id="telphone" name="telphone" readonly type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${eSendHtPage.telphone}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">电话</label>
						</td>

						<td align="right">
							<label class="Validform_label">
								备注:
							</label>
						</td>
						<td class="value">
							<input id="remark" name="remark" type="text" style="width: 150px" class="inputxt"    value='${eSendHtPage.remark}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
					</tr>
					<c:if test="${eSendHtPage.mbbh eq '1.2'}">
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

			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/emk/email/esendht/eSendHt.js"></script>		

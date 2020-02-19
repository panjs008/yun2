<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>邮箱数据</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="eSendHtController.do?doAdd" tiptype="1">
					<input id="id" name="id" type="hidden" value="${eSendHtPage.id }"/>
		<table style="width: 100%" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							同事编号:
						</label>
					</td>
					<td class="value">
					     	 <input id="workNo" name="workNo" type="text" style="width: 150px" class="inputxt"   />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">同事编号</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							同事姓名:
						</label>
					</td>
					<td class="value">
					     	 <input id="userName" name="userName" type="text" style="width: 150px" class="inputxt"   />
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
						<%--<select id="xqqx" name="xqqx">
							<option value="0">固定期</option>
							<option value="1">无固定</option>
						</select>--%>
							<input id="xqqx" name="xqqx" type="text" style="width: 150px" class="inputxt"   />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">续签期限</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							模板编号:
						</label>
					</td>
					<td class="value">
					     	 <input id="mbbh" name="mbbh" type="text" style="width: 150px" class="inputxt"   />
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
					     	 <input id="manger" name="manger" type="text" style="width: 150px" class="inputxt"   />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">经理姓名</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							经理邮箱:
						</label>
					</td>
					<td class="value">
					     	 <input id="email" name="email" type="text" style="width: 150px" class="inputxt"   />
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
							<input id="mangerTelphone" name="mangerTelphone" datatype="*" type="text" style="width: 150px" class="inputxt"   />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">经理电话</label>
						</td>

					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/emk/email/esendht/eSendHt.js"></script>		

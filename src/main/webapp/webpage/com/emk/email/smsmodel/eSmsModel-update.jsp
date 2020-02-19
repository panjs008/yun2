<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>短信模板</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="eSmsModelController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${eSmsModelPage.id }"/>
			<table style="width: 100%" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							短信类型:
						</label>
					</td>
					<td class="value">
						<t:dictSelect id="type" field="type" typeGroupCode="smsType"  defaultVal="${eSmsModelPage.type }" hasLabel="false" title="短信类型" datatype="*"></t:dictSelect>

						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">短信类型</label>
					</td>
				</tr>
				<tr>
					<td align="right" style="width: 25%">
						<label class="Validform_label">
							短信内容:
						</label>
					</td>
					<td class="value" colspan="3"><textarea datatype="*" id="content" style="width:95%;height:100px" class="inputxt" rows="5" name="content">${eSmsModelPage.content }</textarea>
					</td>
				</tr>

			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/emk/email/smsmodel/eSmsModel.js"></script>		

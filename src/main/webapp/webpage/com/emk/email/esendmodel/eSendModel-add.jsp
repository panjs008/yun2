<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>模板表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="eSendModelController.do?doAdd" tiptype="1">
					<input id="id" name="id" type="hidden" value="${eSendModelPage.id }"/>
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right" style="width:20%">
						<label class="Validform_label">
							模板编号:
						</label>
					</td>
					<td class="value">
					     	 <input id="mbbh" name="mbbh" datatype="*" type="text" style="width: 150px" class="inputxt"   />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">模板编号</label>
						</td>
				</tr>
				<tr>
					<td align="right" style="width:20%">
						<label class="Validform_label">
							标题:
						</label>
					</td>
					<td class="value">
						<input id="title" name="title" value="劳动合同续签确认" type="text" datatype="*" style="width: 150px" class="inputxt"   />
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">标题</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							正文:
						</label>
					</td>
					<td class="value" colspan="3"><textarea datatype="*" id="content" style="width:95%;height:250px" class="inputxt" rows="5" name="content"></textarea>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/emk/email/esendmodel/eSendModel.js"></script>		

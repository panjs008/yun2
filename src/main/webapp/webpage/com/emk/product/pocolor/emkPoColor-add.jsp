<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>款号颜色表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkPoColorController.do?doAdd" tiptype="1">
					<input id="id" name="id" type="hidden" value="${emkPoColorPage.id }"/>
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							款号:
						</label>
					</td>
					<td class="value">
					     	 <input id="poNumber" name="poNumber" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">款号</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							颜色:
						</label>
					</td>
					<td class="value">
					     	 <input id="color" name="color" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">颜色</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							内长:
						</label>
					</td>
					<td class="value">
					     	 <input id="nSize" name="nSize" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">内长</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							单价:
						</label>
					</td>
					<td class="value">
						<input id="price" name="price" type="text" datatype="d" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkPoColorPage.price}'/>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">单价</label>
					</td>
				</tr>
				
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/emk/product/pocolor/emkPoColor.js"></script>		

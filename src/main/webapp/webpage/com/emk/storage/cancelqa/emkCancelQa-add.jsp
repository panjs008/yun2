<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>问题反馈表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkCancelQaController.do?doAdd" >
					<input id="id" name="id" type="hidden" value="${emkCancelQaPage.id }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							退货ID:
						</label>
					</td>
					<td class="value">
					     	 <input id="cId" name="cId" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">退货ID</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							问题描述:
						</label>
					</td>
					<td class="value">
					     	 <input id="question" name="question" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">问题描述</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							图片:
						</label>
					</td>
					<td class="value">
					     	 <input id="image" name="image" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">图片</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							图片URL:
						</label>
					</td>
					<td class="value">
					     	 <input id="imageUrl" name="imageUrl" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">图片URL</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							数量统计:
						</label>
					</td>
					<td class="value">
					     	 <input id="total" name="total" type="text" style="width: 150px" class="inputxt"  datatype="n"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">数量统计</label>
						</td>
				</tr>
				
				
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/emk/storage/cancelqa/emkCancelQa.js"></script>		

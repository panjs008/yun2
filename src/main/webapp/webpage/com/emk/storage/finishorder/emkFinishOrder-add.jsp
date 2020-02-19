<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>已出货表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkFinishOrderController.do?doAdd" >
					<input id="id" name="id" type="hidden" value="${emkFinishOrderPage.id }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							供应商名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="gys" name="gys" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">供应商名称</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							供应商代码:
						</label>
					</td>
					<td class="value">
					     	 <input id="gysCode" name="gysCode" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">供应商代码</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							订单号:
						</label>
					</td>
					<td class="value">
					     	 <input id="orderNo" name="orderNo" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">订单号</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							出货日期:
						</label>
					</td>
					<td class="value">
					     	 <input id="outDate" name="outDate" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">出货日期</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							出货批次:
						</label>
					</td>
					<td class="value">
					     	 <input id="betch" name="betch" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">出货批次</label>
						</td>
				</tr>
				
				
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/emk/storage/finishorder/emkFinishOrder.js"></script>		

<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>盘点表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkStroageCheckController.do?doUpdate" >
					<input id="id" name="id" type="hidden" value="${emkStroageCheckPage.id }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								盘点单号:
							</label>
						</td>
						<td class="value">
						    <input id="checkNo" name="checkNo" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkStroageCheckPage.checkNo}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">盘点单号</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								盘点日期:
							</label>
						</td>
						<td class="value">
						    <input id="checkDate" name="checkDate" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkStroageCheckPage.checkDate}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">盘点日期</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								部门ID:
							</label>
						</td>
						<td class="value">
						    <input id="departId" name="departId" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkStroageCheckPage.departId}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">部门ID</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								部门代码:
							</label>
						</td>
						<td class="value">
						    <input id="orgCode" name="orgCode" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkStroageCheckPage.orgCode}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">部门代码</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								备注:
							</label>
						</td>
						<td class="value">
						    <input id="remark" name="remark" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkStroageCheckPage.remark}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								库存ID:
							</label>
						</td>
						<td class="value">
						    <input id="storageId" name="storageId" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkStroageCheckPage.storageId}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">库存ID</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								库存名称:
							</label>
						</td>
						<td class="value">
						    <input id="storageName" name="storageName" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkStroageCheckPage.storageName}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">库存名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								金额:
							</label>
						</td>
						<td class="value">
						    <input id="money" name="money" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkStroageCheckPage.money}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">金额</label>
						</td>
					</tr>
				
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/emk/bound/stroagecheck/emkStroageCheck.js"></script>		

<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>盘点明细</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkStroageCheckDetailController.do?doUpdate" >
					<input id="id" name="id" type="hidden" value="${emkStroageCheckDetailPage.id }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								部门ID:
							</label>
						</td>
						<td class="value">
						    <input id="departId" name="departId" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkStroageCheckDetailPage.departId}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">部门ID</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								部门代码:
							</label>
						</td>
						<td class="value">
						    <input id="orgCode" name="orgCode" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkStroageCheckDetailPage.orgCode}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">部门代码</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								盘点表ID:
							</label>
						</td>
						<td class="value">
						    <input id="checkId" name="checkId" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkStroageCheckDetailPage.checkId}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">盘点表ID</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								仓库ID:
							</label>
						</td>
						<td class="value">
						    <input id="storageId" name="storageId" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkStroageCheckDetailPage.storageId}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">仓库ID</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								仓库名称:
							</label>
						</td>
						<td class="value">
						    <input id="storageName" name="storageName" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkStroageCheckDetailPage.storageName}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">仓库名称</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								产品编号:
							</label>
						</td>
						<td class="value">
						    <input id="proNum" name="proNum" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkStroageCheckDetailPage.proNum}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">产品编号</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								产品名称:
							</label>
						</td>
						<td class="value">
						    <input id="proName" name="proName" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkStroageCheckDetailPage.proName}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">产品名称</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								规格:
							</label>
						</td>
						<td class="value">
						    <input id="guig" name="guig" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkStroageCheckDetailPage.guig}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">规格</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								型号:
							</label>
						</td>
						<td class="value">
						    <input id="brand" name="brand" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkStroageCheckDetailPage.brand}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">型号</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								颜色:
							</label>
						</td>
						<td class="value">
						    <input id="color" name="color" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkStroageCheckDetailPage.color}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">颜色</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								尺码:
							</label>
						</td>
						<td class="value">
						    <input id="size" name="size" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkStroageCheckDetailPage.size}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">尺码</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								单位:
							</label>
						</td>
						<td class="value">
						    <input id="unit" name="unit" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkStroageCheckDetailPage.unit}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">单位</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								账面数量:
							</label>
						</td>
						<td class="value">
						    <input id="billTotal" name="billTotal" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkStroageCheckDetailPage.billTotal}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">账面数量</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								盘点数量:
							</label>
						</td>
						<td class="value">
						    <input id="checkTotal" name="checkTotal" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkStroageCheckDetailPage.checkTotal}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">盘点数量</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								盈亏数量:
							</label>
						</td>
						<td class="value">
						    <input id="winTotal" name="winTotal" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkStroageCheckDetailPage.winTotal}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">盈亏数量</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								成本价:
							</label>
						</td>
						<td class="value">
						    <input id="costPrice" name="costPrice" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkStroageCheckDetailPage.costPrice}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">成本价</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								金额:
							</label>
						</td>
						<td class="value">
						    <input id="money" name="money" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkStroageCheckDetailPage.money}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">金额</label>
						</td>
				<td align="right">
					<label class="Validform_label">
					</label>
				</td>
				<td class="value">
				</td>
					</tr>
				
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/emk/bound/stroagecheckdetail/emkStroageCheckDetail.js"></script>		

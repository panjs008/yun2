<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>库存表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkStorageController.do?doUpdate" tiptype="1" >
					<input id="id" name="id" type="hidden" value="${emkStoragePage.id }"/>
			<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right" width="150px;">
						<label class="Validform_label">
							仓库:
						</label>
					</td>
					<td class="value"  >
						<input id="storageName" name="storageName" type="text"  value="${emkStoragePage.storageName}" class="inputxt"  style="width: 150px">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">仓库</label>
					</td>
					<td align="right" width="150px;">
						<label class="Validform_label">
							条码编号:
						</label>
					</td>
					<td class="value"  >
						<input id="barCode" name="barCode" type="text"  value="${emkStoragePage.barCode}" class="inputxt"  style="width: 150px">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">条码编号</label>
					</td>
					<td align="right" width="150px;">
						<label class="Validform_label">
							助记码:
						</label>
					</td>
					<td class="value"  >
						<input id="proZjm" name="proZjm" type="text"  value="${emkStoragePage.proZjm}" class="inputxt"  style="width: 150px">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">助记码</label>
					</td>
				</tr>
				<c:forEach var="headlist" items="${headcategoryEntities}" varStatus="status">
					<c:if test="${status.index % 3 ==0}">
						<tr>
						<td align="right" width="150px;">
							<label class="Validform_label">
									${headlist.name}:
							</label>
						</td>
						<td class="value"  colspan="${fn:length(headcategoryEntities)-1 eq status.index && fn:length(headcategoryEntities) % 3 !=0 ? 5:1}" >
							<input id="${headlist.code}" name="${headlist.code}" type="text" ${(headlist.code eq 'a01a09a07' ||headlist.code eq 'a01a09a08' ||headlist.code eq 'a01a09a05' ||
								headlist.code eq 'a01a09a02' || headlist.code eq 'a01a09a10' || headlist.code eq 'a01a09a11' || headlist.code eq 'a01a09a06' || headlist.code eq 'a01a09a13'
									|| headlist.code eq 'a01a09a04') ? 'readonly':''}  value="${emkStoragePage[headlist.code]}" class="inputxt"  style="width: 150px">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">${headlist.name}</label>
						</td>
						<c:if test="${fn:length(headcategoryEntities)-1 eq status.index && fn:length(headcategoryEntities) % 3 ==2}">
							</tr>
						</c:if>
					</c:if>
					<c:if test="${status.index % 3 !=0}">
						<td align="right" width="150px;">
							<label class="Validform_label">
									${headlist.name}:
							</label>
						</td>
						<td class="value">
							<input id="${headlist.code}" name="${headlist.code}" ${(headlist.code eq 'a01a09a07' ||headlist.code eq 'a01a09a08' ||headlist.code eq 'a01a09a05' ||
									headlist.code eq 'a01a09a02' || headlist.code eq 'a01a09a10' || headlist.code eq 'a01a09a11' || headlist.code eq 'a01a09a06' || headlist.code eq 'a01a09a13'
									|| headlist.code eq 'a01a09a04') ? 'readonly':''} type="text"  value="${emkStoragePage[headlist.code]}" style="width: 150px"  class="inputxt" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">${headlist.name}</label>
						</td>
						<c:if test="${fn:length(headcategoryEntities)-1 eq status.index && status.index % 3 ==1}">
							<td colspan="2" class="value"></td>
						</c:if>

						<c:if test="${status.index % 3 ==2}">
							</tr>
						</c:if>
					</c:if>

				</c:forEach>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/emk/storage/storage/emkStorage.js"></script>		

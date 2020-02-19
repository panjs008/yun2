<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>仓库客户表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
	 <%@include file="/context/header2.jsp"%>
	 <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkStroageCustomController.do?doAdd" tiptype="1">
					<input id="id" name="id" type="hidden" value="${emkStroageCustomPage.id }"/>
	  <input id="stroageId" name="stroageId" type="hidden" value="${param.storageId }" />

	  <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
			  <tr>
				  <td align="right" >
					  <label class="Validform_label">
						  客户名称:
					  </label>
				  </td>
				  <td class="value" >
					  <input id="cusName" name="cusName" readonly type="text" value="${emkStroageCustomPage.cusName }" style="width:  30%" class="inputxt"  datatype="*"/>
					  <t:choose  hiddenName="cusNum"  hiddenid="cusNum" url="ymkCustomController.do?select" name="ymkCustomList" width="700px" height="500px"
								 icon="icon-search" title="选择客户" textname="cusName,businesseDeptName,businesseDeptId" isclear="true" isInit="true"></t:choose>
					  <span class="Validform_checktip"></span>
					  <label class="Validform_label" style="display: none;">客户名称</label>
				  </td>
			  </tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							客户代码:
						</label>
					</td>
					<td class="value">
					     	 <input id="cusNum" name="cusNum" readonly type="text" style="width: 150px" class="inputxt"  datatype="*"/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">客户代码</label>
						</td>
				</tr>

				<tr>
					<td align="right">
						<label class="Validform_label">
							备注:
						</label>
					</td>
					<td class="value">
					     	 <input id="remark" name="remark" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
				</tr>
				
				
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/emk/storage/stroagecustom/emkStroageCustom.js"></script>		

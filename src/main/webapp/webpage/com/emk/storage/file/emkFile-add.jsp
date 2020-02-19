<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>文件管理</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
	 <%@include file="/context/header2.jsp"%>
	 <script src="${webRoot}/context/gys2.js"></script>
	 <script type="text/javascript" src="js/ajaxfileupload.js"></script>

	 <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkFileController.do?doAdd" tiptype="1" >
					<input id="id" name="id" type="hidden" value="${emkFilePage.id }"/>
	  <input id="orderNoV" name="orderNoV" type="hidden" value=""/>

		<table style="width: 100%" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							工厂:
						</label>
					</td>
					<td class="value">
						<select class="form-control select2" id="gysId"  datatype="*"  ${ROLE.rolecode eq 'admin' ? '':'disabled'}>
							<option value=''>请选择</option>
						</select>
						<input id="gysCode" name="gysCode" type="hidden" value="${factoryArchivesEntity.companyCode }" style="width: 150px" class="inputxt"  ignore="ignore" />
						<input id="gys" name="gys" type="hidden"  value="${factoryArchivesEntity.companyNameZn }"  style="width: 150px" class="inputxt"  ignore="ignore" />
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">工厂</label>
					</td>
					<td align="right">
						<label class="Validform_label">
							上传日期:
						</label>
					</td>
					<td class="value">
						<input id="uploadDate" name="uploadDate" readonly value="${kdDate}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" datatype="*"  type="text" style="width: 150px" class="Wdate"  ignore="ignore" />
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">上传日期</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							文件:
						</label>
					</td>
					<td class="value" colspan="3">
						<input type="hidden" id="fileName" name="fileName" value="${emkFilePage.fileName }">
						<input type="hidden" id="saveFileName" name="saveFileName" value="${emkFilePage.saveFileName }">
						<input type="hidden" id="fileNameUrl" name="fileNameUrl">
						<input name="files" id="uploadFile" onchange="saveFile('uploadControl.do?upload&fileDir=file','uploadFile','newFile','formobj','fileName','fileNameUrl');" style="width:300px;display: none" type="file" accept=".png,.gif,.jpg,.jpeg"  />
						<input id="newFile" type="text" style="width: 150px;" readonly onclick="uploadClick('uploadFile')">
						<span id="uploadFileId"></span>
							<%--[<a href="javascript:findDetail('${emkEnquiryPage.customSampleUrl }')">${emkEnquiryPage.customSample }</a>]--%>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							备注:
						</label>
					</td>
					<td class="value" colspan="3">
						<textarea id="remark" style="width:90%;height:60px" class="inputxt" rows="5" name="remark"></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
				</tr>
				
				
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/emk/storage/file/emkFile.js"></script>		

<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>商品表</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<%@include file="/context/header2.jsp"%>

	<script type="text/javascript" src="js/ajaxfileupload.js"></script>

	<script type="text/javascript">
		//编写自定义JS代码
		$(function() {
			$('#proTypeTree').combotree({
				url : 'emkProductTypeController.do?setPOfficeInfo&selfId=${emkProductTypePage.id}',
				panelHeight: 200,
				width: 157,
				onClick: function(node){
					$("#proType").val(node.id);
					$("#proTypeName").val(node.text);

				}
			});
			$("#priceId").load("emkProductController.do?priceMxList&productId=${emkProductPage.id}");
			$("#a01a01a08").val("${emkProductPage.a01a01a08 }");
			$("#a01a01a10").val("${emkProductPage.a01a01a10 }");

		});

	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" tiptype="1" layout="table" action="emkProductController.do?doAdd" >
	<input id="id" name="id" type="hidden" value="${emkProductPage.id }"/>
	<input id="webRoot" type="hidden" value="${webRoot}"/>
	<input id="oProName" name="oProName" type="hidden" value="${emkProductPage.proZnName }"/>
	<input id="oBrand" name="oBrand" type="hidden" value="${emkProductPage.brand }"/>
	<input id="oa01a01a01" name="oa01a01a01" type="hidden" value="${emkProductPage.a01a01a01 }"/>
	<input id="oa01a01a02" name="oa01a01a02" type="hidden" value="${emkProductPage.a01a01a02 }"/>

	<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right" >
				<label class="Validform_label">
					商品类别:
				</label>
			</td>
			<td class="value">
				<input id="proType" name="proType" type="hidden" value="${emkProductPage.proType }" datatype="*"/>
				<input id="proTypeName" name="proTypeName" type="hidden" value="${emkProductPage.proTypeName }"/>
				<input id="proTypeTree" value="${emkProductPage.proTypeName}" >
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">商品类别</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					商品名称:
				</label>
			</td>
			<td class="value" >
				<input id="proZnName" name="proZnName" type="text" value="${emkProductPage.proZnName }" style="width: 150px" class="inputxt"  datatype="*"/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">商品名称
				</label>
			</td>
			<td class="value" rowspan="5" align="center" colspan="2">
				<img id="uploadimg" src="${emkProductPage.proImageUrl eq null || emkProductPage.proImageUrl eq ''? 'images/certlogo.png':emkProductPage.proImageUrl}" style="border-radius:12px;" width="330" height="200">
			</td>
		</tr>
		<tr>
			<td align="right" >
				<label class="Validform_label">
					产品编号:
				</label>
			</td>
			<td class="value" >
				<input id="proNum" name="proNum" type="text" value="${proNum }" style="width: 150px" class="inputxt" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">产品编号</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					助记码:
				</label>
			</td>
			<td class="value">
				<input id="proZjm" name="proZjm" readonly value="${emkProductPage.proZjm }" type="text" style="width: 150px" class="inputxt"  ignore="ignore"/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">助记码</label>
			</td>
		</tr>

		<tr>
			<td align="right">
				<label class="Validform_label">
					规格:
				</label>
			</td>
			<td class="value">
				<input id="standards" name="standards" type="text" value="${emkProductPage.standards }" style="width: 150px" class="inputxt"  />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">规格</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					型号:
				</label>
			</td>
			<td class="value">
				<input id="brand" name="brand" type="text" value="${emkProductPage.brand }"  style="width: 150px" class="inputxt"  />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">型号</label>
			</td>

		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					单位:
				</label>
			</td>
			<td class="value">
				<t:dictSelect id="unit" field="unit" typeGroupCode="units"  defaultVal="${emkProductPage.unit }" hasLabel="false" title="单位"></t:dictSelect>

				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">单位</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					单位重量:
				</label>
			</td>
			<td class="value">
				<input id="unitWight" name="unitWight" type="text" datatype="d" value="${emkProductPage.unitWight }"  style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">单位重量</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					成本价:
				</label>
			</td>
			<td class="value">
				<input id="costPrice" name="costPrice" type="text" value="${emkProductPage.costPrice }"  style="width: 150px" class="inputxt"  datatype="d" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">成本价</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					零售价:
				</label>
			</td>
			<td class="value">
				<input id="sellPrice" name="sellPrice" type="text" value="${emkProductPage.sellPrice }"  datatype="d" style="width: 150px" class="inputxt"   />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">零售价</label>
			</td>

		</tr>

		<tr>
			<td align="right" >
				<label class="Validform_label">
					条码:
				</label>
			</td>
			<td class="value" >
				<input id="barCode" name="barCode" type="text" value="${emkProductPage.barCode }" style="width: 150px" class="inputxt" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">条码</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					预警天数:
				</label>
			</td>
			<td class="value">
				<input id="warnDays" name="warnDays" value="${emkProductPage.warnDays }"  type="text" style="width: 150px" class="inputxt"  datatype="n" ignore="ignore"/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">预警天数</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					商品图片:
				</label>
			</td>
			<td class="value">
				<input type="hidden" id="proImage" name="proImage" value="${emkProductPage.proImage }" >
				<input type="hidden" id="saveFileName" name="saveFileName" >
				<input type="hidden" id="proImageUrl" name="proImageUrl" value="${emkProductPage.proImageUrl }" >
				<input name="files" id="productuploadFile" onchange="saveFile('uploadControl.do?upload&fileDir=product','productuploadFile','newFile','formobj','proImage','proImageUrl','product');" style="width:300px;display: none" type="file" accept=".png,.gif,.jpg,.jpeg"  />
				<input id="newFile" type="text" value="${emkProductPage.proImage }" style="width: 150px;" readonly onclick="uploadClick('productuploadFile')">
				<span id="productuploadFileId">
					<c:if test="${emkProductPage.proImageUrl ne '' && emkProductPage.proImageUrl ne null}">
						<input class="btn" type="button" value="查看" onclick="findDetail('${emkProductPage.proImageUrl }')" style="background:#18a689 none repeat scroll 0 0;height:28px;width:50px !important;border-radius:5px;color: #fff;" >
					</c:if>
				</span>
				<label class="Validform_label" style="display: none;">商品图片</label>
			</td>
		</tr>

		<c:forEach var="list" items="${categoryEntities}" varStatus="status">
			<c:if test="${status.index % 3 ==0}">
				<tr>
				<td align="right" width="150px;">
					<label class="Validform_label">
							${list.name}:
					</label>
				</td>
				<td class="value"  colspan="${fn:length(categoryEntities)-1 eq status.index && fn:length(categoryEntities) % 3 !=0 ? 5:1}" >
					<c:if test="${list.required eq '0'}">
						<c:choose>
							<c:when test="${list.column_type eq '0'  || list.column_type eq '1'}">
								<input id="${list.code}" name="${list.code}" type="text" value="${emkProductPage[list.code]}" datatype="*"  style="width: 150px"  class="inputxt" >
							</c:when>
							<c:when test="${list.column_type eq '2'}">
								<input id="${list.code}" name="${list.code}" type="text" value="${emkProductPage[list.code]}" datatype="n"   class="inputxt"  style="width: 150px">
							</c:when>
							<c:when test="${list.column_type eq '6'}">
								<input id="${list.code}" name="${list.code}" type="text" value="${emkProductPage[list.code]}" datatype="d"   class="inputxt"  style="width: 150px">
							</c:when>
							<c:when test="${list.column_type eq '3'}">
								<input id="${list.code}" name="${list.code}" type="text" value="${emkProductPage[list.code]}" datatype="*"  style="width: 150px" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width: 150px;"    class="inputxt" >
							</c:when>
							<c:when test="${list.column_type eq '4'}">
								<textarea id="${list.code}" name="${list.code}" style="width:500px;height:60px"  datatype="*"  class="inputxt"></textarea>
							</c:when>
							<c:when test="${list.column_type eq '5' && list.code eq 'a01a01a08'}">
								<select id="${list.code}" name="${list.code}" style="width: 155px">
									<option value="0">不积分</option>
									<option value="1">按金额积分</option>
									<option value="2">按数量积分</option>
								</select>
							</c:when>
							<c:when test="${list.column_type eq '5' && list.code eq 'a01a01a10'}">
								<select id="${list.code}" name="${list.code}" style="width: 155px">
									<option value="0">无提成</option>
									<option value="1">按销售额提成</option>
									<option value="2">按固定金额提成</option>
									<option value="3">按利润提成</option>
								</select>
							</c:when>
							<c:otherwise>
							</c:otherwise>
						</c:choose>
					</c:if>
					<c:if test="${list.required ne '0'}">
						<c:choose>
							<c:when test="${list.column_type eq '0'  || list.column_type eq '1'}">
								<input id="${list.code}" name="${list.code}" type="text"  value="${emkProductPage[list.code]}" ignore="ignore" style="width: 150px"  class="inputxt" >
							</c:when>
							<c:when test="${list.column_type eq '2'}">
								<input id="${list.code}" name="${list.code}" type="text" value="${emkProductPage[list.code]}" datatype="n" ignore="ignore" style="width: 150px"  class="inputxt" >
							</c:when>
							<c:when test="${list.column_type eq '6'}">
								<input id="${list.code}" name="${list.code}" type="text" value="${emkProductPage[list.code]}" datatype="d"   class="inputxt"  style="width: 150px">
							</c:when>
							<c:when test="${list.column_type eq '3'}">
								<input id="${list.code}" name="${list.code}" type="text"  value="${emkProductPage[list.code]}" ignore="ignore" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width: 150px;"    class="inputxt" >
							</c:when>
							<c:when test="${list.column_type eq '4'}">
								<textarea id="${list.code}" name="${list.code}" ignore="ignore" style="width:500px;height:60px"  class="inputxt">${emkProductPage[list.code]}</textarea>
							</c:when>
							<c:when test="${list.column_type eq '5' && list.code eq 'a01a01a08'}">
								<select id="${list.code}" name="${list.code}" style="width: 155px">
									<option value="0">不积分</option>
									<option value="1">按金额积分</option>
									<option value="2">按数量积分</option>
								</select>
							</c:when>
							<c:when test="${list.column_type eq '5' && list.code eq 'a01a01a10'}">
								<select id="${list.code}" name="${list.code}" style="width: 155px">
									<option value="0">无提成</option>
									<option value="1">按销售额提成</option>
									<option value="2">按固定金额提成</option>
									<option value="3">按利润提成</option>
								</select>
							</c:when>

							<c:otherwise>
							</c:otherwise>
						</c:choose>
					</c:if>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">${list.name}</label>
				</td>
				<c:if test="${fn:length(categoryEntities)-1 eq status.index && fn:length(categoryEntities) % 3 ==2}">
					</tr>
				</c:if>
			</c:if>
			<c:if test="${status.index % 3 !=0}">
				<td align="right" width="150px;">
					<label class="Validform_label">
							${list.name}:
					</label>
				</td>
				<td class="value">
					<c:if test="${list.required eq '0'}">
						<c:choose>
							<c:when test="${list.column_type eq '0'  || list.column_type eq '1'}">
								<input id="${list.code}" name="${list.code}" type="text" value="${emkProductPage[list.code]}" datatype="*"   style="width: 150px"  class="inputxt" >
							</c:when>
							<c:when test="${list.column_type eq '2'}">
								<input id="${list.code}" name="${list.code}" type="text" value="${emkProductPage[list.code]}" datatype="n"   class="inputxt"  style="width: 150px">
							</c:when>
							<c:when test="${list.column_type eq '6'}">
								<input id="${list.code}" name="${list.code}" type="text" value="${emkProductPage[list.code]}" datatype="d"   class="inputxt"  style="width: 150px">
							</c:when>
							<c:when test="${list.column_type eq '3'}">
								<input id="${list.code}" name="${list.code}" type="text" value="${emkProductPage[list.code]}" datatype="*"   style="width: 150px" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width: 150px;"    class="inputxt" >
							</c:when>
							<c:when test="${list.column_type eq '4'}">
								<textarea id="${list.code}" name="${list.code}" style="width:500px;height:60px"  datatype="*"  class="inputxt">${emkProductPage[list.code]}</textarea>
							</c:when>
							<c:when test="${list.column_type eq '5' && list.code eq 'a01a01a08'}">
								<select id="${list.code}" name="${list.code}" style="width: 155px">
									<option value="0">不积分</option>
									<option value="1">按金额积分</option>
									<option value="2">按数量积分</option>
								</select>
							</c:when>
							<c:when test="${list.column_type eq '5' && list.code eq 'a01a01a10'}">
								<select id="${list.code}" name="${list.code}" style="width: 155px">
									<option value="0">无提成</option>
									<option value="1">按销售额提成</option>
									<option value="2">按固定金额提成</option>
									<option value="3">按利润提成</option>
								</select>
							</c:when>

							<c:otherwise>
							</c:otherwise>
						</c:choose>
					</c:if>
					<c:if test="${list.required ne '0'}">
						<c:choose>
							<c:when test="${list.column_type eq '0'  || list.column_type eq '1'}">
								<input id="${list.code}" name="${list.code}" type="text"  value="${emkProductPage[list.code]}" style="width: 150px" ignore="ignore"  class="inputxt"  >
							</c:when>
							<c:when test="${list.column_type eq '2'}">
								<input id="${list.code}" name="${list.code}" type="text" value="${emkProductPage[list.code]}" datatype="n" style="width: 150px"  ignore="ignore" class="inputxt"  >
							</c:when>
							<c:when test="${list.column_type eq '6' || list.column_type eq '7'}">
								<input id="${list.code}" name="${list.code}" type="text" value="${emkProductPage[list.code]}" datatype="d"   class="inputxt"  style="width: 150px">
							</c:when>
							<c:when test="${list.column_type eq '3'}">
								<input id="${list.code}" name="${list.code}" type="text"  value="${emkProductPage[list.code]}" ignore="ignore" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"   style="width: 150px;"    class="inputxt" >
							</c:when>
							<c:when test="${list.column_type eq '4'}">
								<textarea id="${list.code}" name="${list.code}" ignore="ignore" style="width:500px;height:60px"  class="inputxt" >${emkProductPage[list.code]}</textarea>
							</c:when>
							<c:when test="${list.column_type eq '5' && list.code eq 'a01a01a08'}">
								<select id="${list.code}" name="${list.code}" style="width: 155px">
									<option value="0">不积分</option>
									<option value="1">按金额积分</option>
									<option value="2">按数量积分</option>
								</select>
							</c:when>
							<c:when test="${list.column_type eq '5' && list.code eq 'a01a01a10'}">
								<select id="${list.code}" name="${list.code}" style="width: 155px">
									<option value="0">无提成</option>
									<option value="1">按销售额提成</option>
									<option value="2">按固定金额提成</option>
									<option value="3">按利润提成</option>
								</select>
							</c:when>

							<c:otherwise>
							</c:otherwise>
						</c:choose>
					</c:if>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">${list.name}</label>
				</td>
				<c:if test="${fn:length(categoryEntities)-1 eq status.index && status.index % 3 ==1}">
					<td colspan="2" class="value"></td>
				</c:if>

				<c:if test="${status.index % 3 ==2}">
					</tr>
				</c:if>
			</c:if>

		</c:forEach>
		<tr>
			<td align="right">
				<label class="Validform_label">
					备注:
				</label>
			</td>
			<td class="value" colspan="5"><textarea id="remark" style="width:95%;height:50px" class="inputxt" rows="4" name="remark">${emkProductPage.remark }</textarea>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">备注</label>
			</td>
		</tr>

	</table>

	<div id="priceId" style="overflow-x:hidden;"></div>
</t:formvalid>
</body>
<script src = "webpage/com/emk/product/product/emkProduct.js"></script>

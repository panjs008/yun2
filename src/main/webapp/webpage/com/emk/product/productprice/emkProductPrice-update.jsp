<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>零售价表</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<script type="text/javascript">
		//编写自定义JS代码
	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkProductPriceController.do?doUpdate" tiptype="1">
	<input id="id" name="id" type="hidden" value="${emkProductPricePage.id }"/>
	<input id="productId" name="productId" type="hidden" value="${emkProductPricePage.productId }"  style="width: 150px" class="inputxt"  ignore="ignore" />

	<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">
					序号:
				</label>
			</td>
			<td class="value">
				<input id="priceNo" name="priceNo"  value="${emkProductPricePage.priceNo }" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">序号</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					价格名称:
				</label>
			</td>
			<td class="value">
				<t:dictSelect id="priceName" field="priceName" typeGroupCode="jgmc"  defaultVal="${emkProductPricePage.priceName }" hasLabel="false" title="价格名称"></t:dictSelect>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">价格名称</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					价格:
				</label>
			</td>
			<td class="value">
				<input id="price" name="price" datatype="d" type="text" value="${emkProductPricePage.price }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">价格</label>
			</td>
		</tr>

	</table>
</t:formvalid>
</body>
<script src = "webpage/com/emk/product/productprice/emkProductPrice.js"></script>

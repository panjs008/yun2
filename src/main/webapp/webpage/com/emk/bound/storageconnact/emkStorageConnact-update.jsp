<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>库存组装表</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<%@include file="/context/header2ForCol.jsp"%>
	<script src="${webRoot}/context/storageForIn.js"></script>
	<script src="${webRoot}/context/storageForOut.js"></script>

	<script type="text/javascript">
		//编写自定义JS代码
		function productLookForOne(){
			$("#chkInfoForOne").click();
		}
		function returnToOneVal(){

		}
	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkStorageConnactController.do?doAdd" tiptype="1">
	<input id="emkMInStorageId" name="emkMInStorageId" type="hidden" value="${emkStorageConnactPage.id }"/>
	<input id="inStorageId" name="inStorageId" type="hidden" value="${emkStorageConnactPage.inStorageId }"/>
	<input id="inStorageName" name="inStorageName" type="hidden" value="${emkStorageConnactPage.inStorageName }"/>
	<input id="outStorageId" name="outStorageId" type="hidden" value="${emkStorageConnactPage.outStorageId }"/>
	<input id="outStorageName" name="outStorageName" type="hidden" value="${emkStorageConnactPage.outStorageName }"/>
	<input id="proNumB" name="proNumB" type="hidden"/>

	<input id="flag" name="flag" type="hidden" value="0"/>
	<input id="money" name="money" type="hidden" value="${emkStorageConnactPage.money }"/>
	<input id="proId" name="proId" type="hidden" style="width: 150px" class="inputxt"  ignore="ignore" />
	<input id="type" name="type" type="hidden" style="width: 150px" class="inputxt"  ignore="ignore"  value='0'/>

	<div style="display:none">
		<t:choose  hiddenName="id"  hiddenid="id" url="emkProductController.do?proForOne&selectType=0" name="emkProductList" width="900px" height="500px"
				   icon="icon-search" title="选择产品" textname="id,proNumB,proZnNameB,brandB,unitB,a01a01a01B" isclear="true" isInit="true"></t:choose>
	</div>

	<table id="headTb" style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">
					出货仓库:
				</label>
			</td>
			<td class="value">
				<select class="form-control select2" id="outstorageNameId" datatype="*">
					<option value=''>请选择</option>
				</select>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">出货仓库</label>
			</td>

			<td align="right">
				<label class="Validform_label">
					入库仓库:
				</label>
			</td>
			<td class="value">
				<select class="form-control select2" id="instorageNameId" datatype="*">
					<option value=''>请选择</option>
				</select>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">入库仓库</label>
			</td>

			<td align="right">
				<label class="Validform_label">
					单号:
				</label>
			</td>
			<td class="value">
				<input id="connactNo" name="connactNo" readonly type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkStorageConnactPage.connactNo}'/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">单号</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					日期:
				</label>
			</td>
			<td class="value">
				<input id="makeDate" name="makeDate" type="text" value="${emkStorageConnactPage.makeDate }" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width: 150px" class="Wdate"  ignore="ignore"  value='${emkStorageConnactPage.makeDate}'/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">日期</label>
			</td>
		</tr>

		<tr>
			<td align="right">
				<label class="Validform_label">
					商品名称:
				</label>
			</td>
			<td class="value">
				<input id="proZnNameB" name="proZnNameB" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkStorageConnactPage.proZnNameB}'/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">商品名称</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					规格型号:
				</label>
			</td>
			<td class="value">
				<input id="brandB" name="brandB" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkStorageConnactPage.brandB}'/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">规格型号</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					单位:
				</label>
			</td>
			<td class="value">
				<input id="unitB" name="unitB" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkStorageConnactPage.unitB}'/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">单位</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					颜色:
				</label>
			</td>
			<td class="value">
				<input id="a01a01a01B" name="a01a01a01B" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkStorageConnactPage.a01a01a01B}'/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">颜色</label>
			</td>

		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					数量:
				</label>
			</td>
			<td class="value">
				<input id="total" name="total" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkStorageConnactPage.total}'/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">数量</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					单价:
				</label>
			</td>
			<td class="value">
				<input id="price" name="price" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkStorageConnactPage.price}'/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">单价</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					操作员:
				</label>
			</td>
			<td class="value">
				<input id="realName" name="realName" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${CUR_USER.realName}'/>
				<input id="userId" name="userId" type="hidden"  value='${CUR_USER.id}'/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">操作员</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					备注:
				</label>
			</td>
			<td class="value">
				<input id="remark" name="remark" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${emkStorageConnactPage.remark}'/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">备注</label>
			</td>

		</tr>

	</table>
	<div id="detailId" style="overflow-x:hidden;overflow-y:hidden"></div>
</t:formvalid>
</body>
<script>
	$(document).ready(function(){
		$("#detailId").load("emkStorageConnactController.do?orderMxList&inStorageId=${emkStorageConnactPage.id}");
	});

</script>
</html>

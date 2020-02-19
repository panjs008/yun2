<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>采购出库表</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<%@include file="/context/header3ForCol.jsp"%>
	<script src="${webRoot}/context/storageForCol3.js"></script>
	<script src="${webRoot}/context/cusForCol3Upt.js"></script>

	<script type="text/javascript">
		//编写自定义JS代码
		var height;
		var headTb;
		var footTb;
		$(document).ready(function(){
			height = window.top.document.body.offsetHeight;
			headTb = $("#headTb").height();
			footTb = $("#footTb").height();
			<c:if test="${headTb ne null && headTb ne ''}">
				headTb = '${headTb}';
				footTb = '${footTb}';
			</c:if>
			$("#detailId").load("emkMOutStorageController.do?orderMxList&outStorageId=${emkMOutStoragePage.id}");
			$("#detailId").css("height",height-headTb-footTb);
		});
		function setPay(){
			var yfMoneyVal = $("#yfMoney").val();
			var yhMoneyVal = $("#yhMoney").val();
			var shMoneyVal = $("#shMoney").val();
			var bcqkMoneyVal = $("#bcqkMoney").val();
			var qcqkMoneyVal = $("#qcqkMoney").val();
			var ljqkMoneyVal = $("#ljqkMoney").val();
			var bcqk;
			if($("#yfMoney").val()) {
				yfMoneyVal = parseFloat($("#yfMoney").val());
			}
			if($("#yhMoney").val()) {
				yhMoneyVal = parseFloat($("#yhMoney").val());
			}
			if($("#shMoney").val()) {
				shMoneyVal = parseFloat($("#shMoney").val());
			}
			if($("#bcqkMoney").val()) {
				bcqkMoneyVal = parseFloat($("#bcqkMoney").val());
			}
			if($("#qcqkMoney").val()) {
				qcqkMoneyVal = parseFloat($("#qcqkMoney").val());
			}
			if($("#ljqkMoney").val()) {
				ljqkMoneyVal = parseFloat($("#ljqkMoney").val());
			}
			bcqk = yfMoneyVal-yhMoneyVal-shMoneyVal;
			ljqkMoneyVal = bcqk+qcqkMoneyVal;
			if(!isNaN(bcqk)) {
				$("#bcqkMoney").val(toDecimal(bcqk));
				$("#ljqkMoney").val(toDecimal(ljqkMoneyVal));
			}
		}
	</script>

</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkMOutStorageController.do?doUpdate" tiptype="1">
	<input id="emkMOutStorageId" name="emkMOutStorageId" type="hidden" value="${emkMOutStoragePage.id }"/>
	<input id="storageId" name="storageId" type="hidden" value="${emkMOutStoragePage.storageId }"/>
	<input id="flag" name="flag" type="hidden" value="0"/>
	<input id="total" name="total" type="hidden" value="${emkMOutStoragePage.total }"/>
	<input id="money" name="money" type="hidden" value="${emkMOutStoragePage.money }"/>

	<table id="headTb" style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
		<c:forEach var="headlist" items="${headcategoryEntities}" varStatus="status">
			<c:if test="${status.index % 3 ==0}">
				<tr>
				<td align="right" width="150px;">
					<label class="Validform_label">
							${headlist.name}:
					</label>
				</td>
				<td class="value"  colspan="${fn:length(headcategoryEntities)-1 eq status.index && fn:length(headcategoryEntities) % 3 !=0 ? 5:1}" >

					<c:if test="${headlist.required eq '0'}">
						<c:choose>
							<c:when test="${headlist.column_type eq '0'  || headlist.column_type eq '1'}">
								<input id="${headlist.code}" name="${headlist.code}" type="text" datatype="*" value="${emkMOutStoragePage[headlist.code]}" style="width: 150px"  class="inputxt" >
							</c:when>
							<c:when test="${headlist.column_type eq '2'}">
								<input id="${headlist.code}" name="${headlist.code}" type="text" datatype="n"  value="${emkMOutStoragePage[headlist.code]}" class="inputxt"  style="width: 150px">
							</c:when>
							<c:when test="${headlist.column_type eq '6'}">
								<input id="${headlist.code}" name="${headlist.code}" type="text" datatype="d"  value="${emkMOutStoragePage[headlist.code]}" class="inputxt"  style="width: 150px">
							</c:when>
							<c:when test="${headlist.column_type eq '3'}">
								<input id="${headlist.code}" name="${headlist.code}" type="text" datatype="*" value="${emkMOutStoragePage[headlist.code]}" style="width: 150px" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width: 150px;"    class="Wdate" >
							</c:when>
							<c:when test="${headlist.column_type eq '4'}">
								<textarea id="${headlist.code}" name="${headlist.code}" style="width:500px;height:60px"  datatype="*"  class="inputxt">${emkMOutStoragePage[headlist.code]}</textarea>
							</c:when>
							<c:when test="${headlist.column_type eq '5' && headlist.code eq 'a01a07a02'}">
								<select class="form-control select2" id="cusId"  datatype="*"  >
									<option value=''>请选择</option>
								</select>
								<input id="${headlist.code}" name="${headlist.code}"  value="${emkMOutStoragePage[headlist.code]}" type="hidden" style="width: 150px" class="inputxt"  ignore="ignore" />
								<input id="cusName" name="cusName" type="hidden"  value="${emkMOutStoragePage.cusName }"  style="width: 150px" class="inputxt"  ignore="ignore" />
							</c:when>
							<c:when test="${headlist.column_type eq '5' && headlist.code eq 'a01a07a04'}">
								<select class="form-control select2" id="storageNameId" datatype="*">
									<option value=''>请选择</option>
								</select>

								<input id="${headlist.code}" name="${headlist.code}" type="hidden" value="${emkMOutStoragePage[headlist.code]}" style="width: 150px" class="inputxt"  ignore="ignore" />
								<input id="storageName" name="storageName" type="hidden" value="${emkMOutStoragePage.storageName}"/>

							</c:when>
							<c:when test="${headlist.column_type eq '5' && headlist.code eq 'a01a07a05'}">
								<select class="form-control select2" id="businesserId"  datatype="*">
									<option value=''>请选择</option>
								</select>
								<input id="${headlist.code}" name="${headlist.code}" value="${emkMOutStoragePage[headlist.code]}" readonly type="hidden"  style="width: 150px" class="inputxt"  ignore="ignore" />
								<input id="businesser" name="businesser"  type="hidden" value="${emkMOutStoragePage.realName }" />

							</c:when>
							<c:when test="${headlist.column_type eq '7' && headlist.code eq 'a01a07a06'}">
								<t:dictSelect id="${headlist.code}" field="${headlist.code}" typeGroupCode="paytype"  defaultVal="${emkMOutStoragePage[headlist.code]}" hasLabel="false" title="付款方式"></t:dictSelect>
							</c:when>
							<c:otherwise>
							</c:otherwise>
						</c:choose>
					</c:if>
					<c:if test="${headlist.required ne '0'}">
						<c:choose>
							<c:when test="${headlist.column_type eq '0'  || headlist.column_type eq '1'}">
								<input id="${headlist.code}" name="${headlist.code}" type="text" <c:if test="${headlist.code eq 'a01a07a01'}">readonly</c:if> ignore="ignore" value="${emkMOutStoragePage[headlist.code]}" style="width: 150px"  class="inputxt" >
							</c:when>
							<c:when test="${headlist.column_type eq '2'}">
								<input id="${headlist.code}" name="${headlist.code}" type="text" datatype="n" value="${emkMOutStoragePage[headlist.code]}" ignore="ignore" style="width: 150px"  class="inputxt" >
							</c:when>
							<c:when test="${headlist.column_type eq '6'}">
								<input id="${headlist.code}" name="${headlist.code}" type="text" datatype="d"  value="${emkMOutStoragePage[headlist.code]}" class="inputxt"  style="width: 150px">
							</c:when>
							<c:when test="${headlist.column_type eq '3'}">
								<input id="${headlist.code}" name="${headlist.code}" type="text"  ignore="ignore" value="${emkMOutStoragePage[headlist.code]}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width: 150px;"    class="Wdate" >
							</c:when>
							<c:when test="${headlist.column_type eq '4'}">
								<textarea id="${headlist.code}" name="${headlist.code}" ignore="ignore" value="${emkMOutStoragePage[headlist.code]}" style="width:500px;height:60px"  class="inputxt"></textarea>
							</c:when>
							<c:when test="${headlist.column_type eq '5' && headlist.code eq 'a01a07a02'}">
								<select class="form-control select2" id="cusId"  datatype="*"  >
									<option value=''>请选择</option>
								</select>
								<input id="${headlist.code}" name="${headlist.code}" value="${emkMOutStoragePage[headlist.code]}" type="hidden" style="width: 150px" class="inputxt"  ignore="ignore" />
								<input id="cusName" name="cusName" type="hidden"  value="${emkMOutStoragePage.cusName }"  style="width: 150px" class="inputxt"  ignore="ignore" />
							</c:when>
							<c:when test="${headlist.column_type eq '5' && headlist.code eq 'a01a07a04'}">
								<select class="form-control select2" id="storageNameId" datatype="*">
									<option value=''>请选择</option>
								</select>
								<input id="${headlist.code}" name="${headlist.code}" type="hidden" value="${emkMOutStoragePage[headlist.code]}" style="width: 150px" class="inputxt"  ignore="ignore" />
								<input id="storageName" name="storageName" type="hidden" value="${emkMOutStoragePage.storageName}"/>

							</c:when>
							<c:when test="${headlist.column_type eq '5' && headlist.code eq 'a01a07a05'}">
								<select class="form-control select2" id="businesserId"  datatype="*">
									<option value=''>请选择</option>
								</select>
								<input id="${headlist.code}" name="${headlist.code}" value="${emkMOutStoragePage[headlist.code]}" readonly type="hidden"  style="width: 150px" class="inputxt"  ignore="ignore" />
								<input id="businesser" name="businesser"  type="hidden" value="${emkMOutStoragePage.businesser }" />

							</c:when>
							<c:when test="${headlist.column_type eq '7' && headlist.code eq 'a01a07a06'}">
								<t:dictSelect id="${headlist.code}" field="${headlist.code}" typeGroupCode="paytype"  defaultVal="${emkMOutStoragePage[headlist.code]}" hasLabel="false" title="付款方式"></t:dictSelect>
							</c:when>

							<c:otherwise>
							</c:otherwise>
						</c:choose>
					</c:if>
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
					<c:if test="${headlist.required eq '0'}">
						<c:choose>
							<c:when test="${headlist.column_type eq '0'  || headlist.column_type eq '1'}">
								<input id="${headlist.code}" name="${headlist.code}" type="text" datatype="*"  value="${emkMOutStoragePage[headlist.code]}" style="width: 150px"  class="inputxt" >
							</c:when>
							<c:when test="${headlist.column_type eq '2'}">
								<input id="${headlist.code}" name="${headlist.code}" type="text" datatype="n"  value="${emkMOutStoragePage[headlist.code]}" class="inputxt"  style="width: 150px">
							</c:when>
							<c:when test="${headlist.column_type eq '6'}">
								<input id="${headlist.code}" name="${headlist.code}" type="text" datatype="d"  value="${emkMOutStoragePage[headlist.code]}" class="inputxt"  style="width: 150px">
							</c:when>
							<c:when test="${headlist.column_type eq '3'}">
								<input id="${headlist.code}" name="${headlist.code}" type="text" datatype="*" value="${emkMOutStoragePage[headlist.code]}"  style="width: 150px" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width: 150px;"    class="Wdate" >
							</c:when>
							<c:when test="${headlist.column_type eq '4'}">
								<textarea id="${headlist.code}" name="${headlist.code}" style="width:500px;height:60px"  datatype="*"  class="inputxt">${emkMOutStoragePage[headlist.code]}</textarea>
							</c:when>
							<c:when test="${headlist.column_type eq '5' && headlist.code eq 'a01a07a02'}">
								<select class="form-control select2" id="cusId"  datatype="*"  >
									<option value=''>请选择</option>
								</select>
								<input id="${headlist.code}" name="${headlist.code}" value="${emkMOutStoragePage[headlist.code]}" type="hidden" style="width: 150px" class="inputxt"  ignore="ignore" />
								<input id="cusName" name="cusName" type="hidden"  value="${emkMOutStoragePage.cusName }"  style="width: 150px" class="inputxt"  ignore="ignore" />
							</c:when>
							<c:when test="${headlist.column_type eq '5' && headlist.code eq 'a01a07a04'}">
								<select class="form-control select2" id="storageNameId" datatype="*">
									<option value=''>请选择</option>
								</select>
								<input id="${headlist.code}" name="${headlist.code}" type="hidden" value="${emkMOutStoragePage[headlist.code]}" style="width: 150px" class="inputxt"  ignore="ignore" />
								<input id="storageName" name="storageName" type="hidden" value="${emkMOutStoragePage.storageName}"/>

							</c:when>
							<c:when test="${headlist.column_type eq '5' && headlist.code eq 'a01a07a05'}">
								<select class="form-control select2" id="businesserId"  datatype="*">
									<option value=''>请选择</option>
								</select>
								<input id="${headlist.code}" name="${headlist.code}" value="${emkMOutStoragePage[headlist.code]}" readonly type="hidden"  style="width: 150px" class="inputxt"  ignore="ignore" />
								<input id="businesser" name="businesser"  type="hidden" value="${emkMOutStoragePage.businesser }" />

							</c:when>
							<c:when test="${headlist.column_type eq '7' && headlist.code eq 'a01a07a06'}">
								<t:dictSelect id="${headlist.code}" field="${headlist.code}" typeGroupCode="paytype"  defaultVal="${emkMOutStoragePage[headlist.code]}" hasLabel="false" title="付款方式"></t:dictSelect>
							</c:when>

							<c:otherwise>
							</c:otherwise>
						</c:choose>
					</c:if>
					<c:if test="${headlist.required ne '0'}">
						<c:choose>
							<c:when test="${headlist.column_type eq '0'  || headlist.column_type eq '1'}">
								<input id="${headlist.code}" name="${headlist.code}" type="text"  <c:if test="${headlist.code eq 'a01a07a01'}">readonly</c:if> style="width: 150px" value="${emkMOutStoragePage[headlist.code]}" ignore="ignore"  class="inputxt"  >
							</c:when>
							<c:when test="${headlist.column_type eq '2'}">
								<input id="${headlist.code}" name="${headlist.code}" type="text" datatype="n" value="${emkMOutStoragePage[headlist.code]}" style="width: 150px"  ignore="ignore" class="inputxt"  >
							</c:when>
							<c:when test="${headlist.column_type eq '6' }">
								<input id="${headlist.code}" name="${headlist.code}" type="text" datatype="d"  value="${emkMOutStoragePage[headlist.code]}" class="inputxt"  style="width: 150px">
							</c:when>
							<c:when test="${headlist.column_type eq '3'}">
								<input id="${headlist.code}" name="${headlist.code}" type="text"  ignore="ignore" value="${emkMOutStoragePage[headlist.code]}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"   style="width: 150px;"    class="Wdate" >
							</c:when>
							<c:when test="${headlist.column_type eq '4'}">
								<textarea id="${headlist.code}" name="${headlist.code}" ignore="ignore" value="${emkMOutStoragePage[headlist.code]}" style="width:500px;height:60px"  class="inputxt" ></textarea>
							</c:when>
							<c:when test="${headlist.column_type eq '5' && headlist.code eq 'a01a07a02'}">
								<select class="form-control select2" id="cusId"  datatype="*"  >
									<option value=''>请选择</option>
								</select>
								<input id="${headlist.code}" name="${headlist.code}" value="${emkMOutStoragePage[headlist.code]}" type="hidden" style="width: 150px" class="inputxt"  ignore="ignore" />
								<input id="cusName" name="cusName" type="hidden"  value="${emkMOutStoragePage.cusName }"  style="width: 150px" class="inputxt"  ignore="ignore" />
							</c:when>
							<c:when test="${headlist.column_type eq '5' && headlist.code eq 'a01a07a04'}">
								<select class="form-control select2" id="storageNameId" datatype="*">
									<option value=''>请选择</option>
								</select>
								<input id="${headlist.code}" name="${headlist.code}" type="hidden" value="${emkMOutStoragePage[headlist.code]}" style="width: 150px" class="inputxt"  ignore="ignore" />
								<input id="storageName" name="storageName" type="hidden" value="${emkMOutStoragePage.storageName}"/>

							</c:when>
							<c:when test="${headlist.column_type eq '5' && headlist.code eq 'a01a07a05'}">
								<select class="form-control select2" id="businesserId"  datatype="*">
									<option value=''>请选择</option>
								</select>
								<input id="${headlist.code}" name="${headlist.code}" value="${emkMOutStoragePage[headlist.code]}" readonly type="hidden"  style="width: 150px" class="inputxt"  ignore="ignore" />
								<input id="businesser" name="businesser"  type="hidden" value="${emkMOutStoragePage.businesser }" />

							</c:when>
							<c:when test="${headlist.column_type eq '7' && headlist.code eq 'a01a07a06'}">
								<t:dictSelect id="${headlist.code}" field="${headlist.code}" typeGroupCode="paytype"  defaultVal="${emkMOutStoragePage[headlist.code]}" hasLabel="false" title="付款方式"></t:dictSelect>
							</c:when>

							<c:otherwise>
							</c:otherwise>
						</c:choose>
					</c:if>
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
	<div id="detailId" style="overflow-x:hidden;height:100px;"></div>
	<table id="footTb" style="width: 100%;position:fixed; left:0; bottom:0;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">
					应付金额:
				</label>
			</td>
			<td class="value">
				<input id="yfMoney" name="yfMoney" datatype="d" onkeyup="setPay();"  value="${emkMOutStoragePage.yfMoney}"  style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">应付金额</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					账号:
				</label>
			</td>
			<td class="value">
				<t:dictSelect id="bankMoney" field="bankMoney" typeGroupCode="zjzh"  defaultVal="${emkMOutStoragePage.bankMoney }" hasLabel="false" title="账号"></t:dictSelect>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">账号</label>
			</td>
			<td align="right" >
				<label class="Validform_label">
					优惠金额:
				</label>
			</td>
			<td class="value" >
				<input id="yhMoney" name="yhMoney" onkeyup="setPay();"  datatype="d" value="${emkMOutStoragePage.yhMoney}"  style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">优惠金额</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					实付金额:
				</label>
			</td>
			<td class="value">
				<input id="shMoney" name="shMoney"  onkeyup="setPay();" datatype="d" value="${emkMOutStoragePage.shMoney}"  style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">实付金额</label>
			</td>
		</tr>
		<tr>

			<td align="right" >
				<label class="Validform_label">
					本次欠款:
				</label>
			</td>
			<td class="value" >
				<input id="bcqkMoney" name="bcqkMoney"  datatype="d" value="${emkMOutStoragePage.bcqkMoney}"  style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">本次欠款</label>
			</td>
			<td align="right" >
				<label class="Validform_label">
					前次欠款:
				</label>
			</td>
			<td class="value" >
				<input id="qcqkMoney" name="qcqkMoney"  datatype="d" value="${emkMOutStoragePage.qcqkMoney}"  style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">本次欠款</label>
			</td>
			<td align="right" >
				<label class="Validform_label">
					累欠货款:
				</label>
			</td>
			<td class="value" colspan="3">
				<input id="ljqkMoney" name="ljqkMoney"  datatype="d" value="${emkMOutStoragePage.ljqkMoney}"  style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">累欠货款</label>
			</td>
		</tr>
	</table>
</t:formvalid>
</body>
<script>

</script>
</html>

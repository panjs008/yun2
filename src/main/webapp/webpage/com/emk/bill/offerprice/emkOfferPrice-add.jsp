<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>报价单</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<%@include file="/context/headerForOfferPrice.jsp"%>
	<%--<script src="${webRoot}/context/positionSelect.js"></script>--%>
	<script src="${webRoot}/context/storageForOfferPrice.js"></script>
	<script src="${webRoot}/context/cusForOfferPrice.js"></script>

	<script type="text/javascript">
		//编写自定义JS代码
		var height;
		var headTb;
		var footTb;

	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkOfferPriceController.do?doAdd" tiptype="1">
	<input id="proOrderId" name="proOrderId" type="hidden" value="${emkOfferPricePage.id }"/>
	<input id="total" name="total" type="hidden" value="${emkOfferPricePage.total }"/>
	<input id="money" name="money" type="hidden" value="${emkOfferPricePage.money }"/>

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
								<input id="${headlist.code}" name="${headlist.code}" type="text" value="${emkOfferPricePage[headlist.code]}" <c:if test="${headlist.code ne 'a01a13a01'}">datatype="*"</c:if> <c:if test="${headlist.code eq 'a01a13a01'}">readonly</c:if> style="width: 150px"  class="inputxt" >
							</c:when>
							<c:when test="${headlist.column_type eq '2'}">
								<input id="${headlist.code}" name="${headlist.code}" type="text" value="${emkOfferPricePage[headlist.code]}" datatype="n"   class="inputxt"  style="width: 150px">
							</c:when>
							<c:when test="${headlist.column_type eq '6'}">
								<input id="${headlist.code}" name="${headlist.code}" type="text" value="${emkOfferPricePage[headlist.code]}" datatype="d"   class="inputxt"  style="width: 150px">
							</c:when>
							<c:when test="${headlist.column_type eq '3'}">
								<input id="${headlist.code}" name="${headlist.code}" type="text" value="${emkOfferPricePage[headlist.code]}" datatype="*"  style="width: 150px" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width: 150px;"    class="Wdate" >
							</c:when>
							<c:when test="${headlist.column_type eq '4'}">
								<textarea id="${headlist.code}" name="${headlist.code}" style="width:500px;height:60px"  datatype="*"  class="inputxt">${emkOfferPricePage[headlist.code]}</textarea>
							</c:when>
							<c:when test="${headlist.column_type eq '5' && headlist.code eq 'a01a13a02'}">
								<select class="form-control select2" id="cusId"  datatype="*"  >
									<option value=''>请选择</option>
								</select>
								<input id="${headlist.code}" name="${headlist.code}" type="hidden" style="width: 150px" class="inputxt"  ignore="ignore" />
								<input id="cusName" name="cusName" type="hidden"  value="${emkOfferPricePage.cusName }"  style="width: 150px" class="inputxt"  ignore="ignore" />
							</c:when>
							<c:when test="${headlist.column_type eq '5' && headlist.code eq 'a01a13a04'}">
								<select class="form-control select2" id="storageNameId" datatype="*">
									<option value=''>请选择</option>
								</select>
								<input id="${headlist.code}" name="${headlist.code}" type="hidden" value="${(ROLE.rolecode eq 'admin' || ROLE.rolecode eq 'useradmin') ? '':CUR_USER.storageId}" style="width: 150px" class="inputxt"  ignore="ignore" />
								<input id="storageName" name="storageName" type="hidden" value="${(ROLE.rolecode eq 'admin' || ROLE.rolecode eq 'useradmin') ? '':CUR_USER.storageName}"/>

							</c:when>
							<c:when test="${headlist.column_type eq '5' && headlist.code eq 'a01a13a05'}">
								<select class="form-control select2" id="businesserId"  datatype="*">
									<option value=''>请选择</option>
								</select>
								<input id="${headlist.code}" name="${headlist.code}" value="${CUR_USER.userName}" readonly type="hidden"  style="width: 150px" class="inputxt"  ignore="ignore" />
								<input id="businesser" name="businesser"  type="hidden" value="${CUR_USER.realName }" />

							</c:when>
							<c:when test="${headlist.column_type eq '7' && headlist.code eq 'a01a13a06'}">
								<t:dictSelect id="${headlist.code}" field="${headlist.code}" typeGroupCode="paytype"  defaultVal="${emkOfferPricePage[headlist.code] }" hasLabel="false" title="付款方式"></t:dictSelect>
							</c:when>
							<c:otherwise>
							</c:otherwise>
						</c:choose>
					</c:if>
					<c:if test="${headlist.required ne '0'}">
						<c:choose>
							<c:when test="${headlist.column_type eq '0'  || headlist.column_type eq '1'}">
								<input id="${headlist.code}" name="${headlist.code}" type="text" value="${emkOfferPricePage[headlist.code]}" <c:if test="${headlist.code eq 'a01a13a01'}">readonly</c:if>  ignore="ignore" style="width: 150px"  class="inputxt" >
							</c:when>
							<c:when test="${headlist.column_type eq '2'}">
								<input id="${headlist.code}" name="${headlist.code}" type="text" value="${emkOfferPricePage[headlist.code]}" datatype="n" ignore="ignore" style="width: 150px"  class="inputxt" >
							</c:when>
							<c:when test="${headlist.column_type eq '6'}">
								<input id="${headlist.code}" name="${headlist.code}" type="text" value="${emkOfferPricePage[headlist.code]}" datatype="d"   class="inputxt"  style="width: 150px">
							</c:when>
							<c:when test="${headlist.column_type eq '3'}">
								<input id="${headlist.code}" name="${headlist.code}" type="text"  value="${emkOfferPricePage[headlist.code]}" ignore="ignore" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width: 150px;"    class="Wdate" >
							</c:when>
							<c:when test="${headlist.column_type eq '4'}">
								<textarea id="${headlist.code}" name="${headlist.code}" value="${emkOfferPricePage[headlist.code]}" ignore="ignore" style="width:500px;height:60px"  class="inputxt"></textarea>
							</c:when>
							<c:when test="${headlist.column_type eq '5' && headlist.code eq 'a01a13a02'}">
								<select class="form-control select2" id="cusId"  datatype="*"  >
									<option value=''>请选择</option>
								</select>
								<input id="${headlist.code}" name="${headlist.code}" type="hidden" style="width: 150px" class="inputxt"  ignore="ignore" />
								<input id="cusName" name="cusName" type="hidden"  value="${emkOfferPricePage.cusName }"  style="width: 150px" class="inputxt"  ignore="ignore" />
							</c:when>
							<c:when test="${headlist.column_type eq '5' && headlist.code eq 'a01a13a04'}">
								<select class="form-control select2" id="storageNameId" datatype="*">
									<option value=''>请选择</option>
								</select>
								<input id="${headlist.code}" name="${headlist.code}" type="hidden" value="${(ROLE.rolecode eq 'admin' || ROLE.rolecode eq 'useradmin') ? '':CUR_USER.storageId}" style="width: 150px" class="inputxt"  ignore="ignore" />
								<input id="storageName" name="storageName" type="hidden" value="${(ROLE.rolecode eq 'admin' || ROLE.rolecode eq 'useradmin') ? '':CUR_USER.storageName}"/>
							</c:when>
							<c:when test="${headlist.column_type eq '5' && headlist.code eq 'a01a13a05'}">
								<select class="form-control select2" id="businesserId"  datatype="*">
									<option value=''>请选择</option>
								</select>
								<input id="${headlist.code}" name="${headlist.code}" value="${CUR_USER.userName}" readonly type="hidden"  style="width: 150px" class="inputxt"  ignore="ignore" />
								<input id="businesser" name="businesser"  type="hidden" value="${CUR_USER.realName }" />

							</c:when>
							<c:when test="${headlist.column_type eq '7' && headlist.code eq 'a01a13a06'}">
								<t:dictSelect id="${headlist.code}" field="${headlist.code}" typeGroupCode="paytype"  defaultVal="${emkOfferPricePage[headlist.code] }" hasLabel="false" title="付款方式"></t:dictSelect>
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
								<input id="${headlist.code}" name="${headlist.code}" type="text" value="${emkOfferPricePage[headlist.code]}" <c:if test="${headlist.code ne 'a01a03a07'}">datatype="*"</c:if> <c:if test="${headlist.code eq 'a01a03a07'}">readonly</c:if>   style="width: 150px"  class="inputxt" >
							</c:when>
							<c:when test="${headlist.column_type eq '2'}">
								<input id="${headlist.code}" name="${headlist.code}" type="text" value="${emkOfferPricePage[headlist.code]}" datatype="n"   class="inputxt"  style="width: 150px">
							</c:when>
							<c:when test="${headlist.column_type eq '6'}">
								<input id="${headlist.code}" name="${headlist.code}" type="text" value="${emkOfferPricePage[headlist.code]}" datatype="d"   class="inputxt"  style="width: 150px">
							</c:when>
							<c:when test="${headlist.column_type eq '3'}">
								<input id="${headlist.code}" name="${headlist.code}" type="text" value="${emkOfferPricePage[headlist.code]}"  datatype="*"  value="${headlist.code eq 'a01a03a10' ? kdDate:''}" style="width: 150px" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width: 150px;"    class="Wdate" >
							</c:when>
							<c:when test="${headlist.column_type eq '4'}">
								<textarea id="${headlist.code}" name="${headlist.code}" value="${emkOfferPricePage[headlist.code]}" style="width:500px;height:60px"  datatype="*"  class="inputxt"></textarea>
							</c:when>
							<c:when test="${headlist.column_type eq '5' && headlist.code eq 'a01a13a02'}">
								<select class="form-control select2" id="cusId"  datatype="*"  >
									<option value=''>请选择</option>
								</select>
								<input id="${headlist.code}" name="${headlist.code}" type="hidden" style="width: 150px" class="inputxt"  ignore="ignore" />
								<input id="cusName" name="cusName" type="hidden"  value="${emkOfferPricePage.cusName }"  style="width: 150px" class="inputxt"  ignore="ignore" />
							</c:when>
							<c:when test="${headlist.column_type eq '5' && headlist.code eq 'a01a13a04'}">
								<select class="form-control select2" id="storageNameId" datatype="*">
									<option value=''>请选择</option>
								</select>
								<input id="${headlist.code}" name="${headlist.code}" type="hidden" value="${(ROLE.rolecode eq 'admin' || ROLE.rolecode eq 'useradmin') ? '':CUR_USER.storageId}" style="width: 150px" class="inputxt"  ignore="ignore" />
								<input id="storageName" name="storageName" type="hidden" value="${(ROLE.rolecode eq 'admin' || ROLE.rolecode eq 'useradmin') ? '':CUR_USER.storageName}"/>

							</c:when>
							<c:when test="${headlist.column_type eq '5' && headlist.code eq 'a01a13a05'}">
								<select class="form-control select2" id="businesserId"  datatype="*">
									<option value=''>请选择</option>
								</select>
								<input id="${headlist.code}" name="${headlist.code}" value="${CUR_USER.userName}" readonly type="hidden"  style="width: 150px" class="inputxt"  ignore="ignore" />
								<input id="businesser" name="businesser"  type="hidden" value="${CUR_USER.realName }" />

							</c:when>
							<c:when test="${headlist.column_type eq '7' && headlist.code eq 'a01a13a06'}">
								<t:dictSelect id="${headlist.code}" field="${headlist.code}" typeGroupCode="paytype"  defaultVal="${emkOfferPricePage[headlist.code] }" hasLabel="false" title="付款方式"></t:dictSelect>
							</c:when>
							<c:otherwise>
							</c:otherwise>
						</c:choose>
					</c:if>
					<c:if test="${headlist.required ne '0'}">
						<c:choose>
							<c:when test="${headlist.column_type eq '0'  || headlist.column_type eq '1'}">
								<input id="${headlist.code}" name="${headlist.code}" type="text" value="${emkOfferPricePage[headlist.code]}" <c:if test="${headlist.code eq 'a01a03a07'}">readonly</c:if>  style="width: 150px" ignore="ignore"  class="inputxt"  >
							</c:when>
							<c:when test="${headlist.column_type eq '2'}">
								<input id="${headlist.code}" name="${headlist.code}" type="text" value="${emkOfferPricePage[headlist.code]}" datatype="n" style="width: 150px"  ignore="ignore" class="inputxt"  >
							</c:when>
							<c:when test="${headlist.column_type eq '6'}">
								<input id="${headlist.code}" name="${headlist.code}" type="text" value="${emkOfferPricePage[headlist.code]}" datatype="d"   class="inputxt"  style="width: 150px">
							</c:when>
							<c:when test="${headlist.column_type eq '3'}">
								<input id="${headlist.code}" name="${headlist.code}" type="text"  value="${emkOfferPricePage[headlist.code]}" ignore="ignore" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"   style="width: 150px;"    class="Wdate" >
							</c:when>
							<c:when test="${headlist.column_type eq '4'}">
								<textarea id="${headlist.code}" name="${headlist.code}" value="${emkOfferPricePage[headlist.code]}" ignore="ignore" style="width:500px;height:60px"  class="inputxt" ></textarea>
							</c:when>
							<c:when test="${headlist.column_type eq '5' && headlist.code eq 'a01a13a02'}">
								<select class="form-control select2" id="cusId"  datatype="*"  >
									<option value=''>请选择</option>
								</select>
								<input id="${headlist.code}" name="${headlist.code}" type="hidden" style="width: 150px" class="inputxt"  ignore="ignore" />
								<input id="cusName" name="cusName" type="hidden"  value="${emkOfferPricePage.cusName }"  style="width: 150px" class="inputxt"  ignore="ignore" />
							</c:when>
							<c:when test="${headlist.column_type eq '5' && headlist.code eq 'a01a13a04'}">
								<select class="form-control select2" id="storageNameId" datatype="*">
									<option value=''>请选择</option>
								</select>
								<input id="${headlist.code}" name="${headlist.code}" type="hidden" value="${(ROLE.rolecode eq 'admin' || ROLE.rolecode eq 'useradmin') ? '':CUR_USER.storageId}" style="width: 150px" class="inputxt"  ignore="ignore" />
								<input id="storageName" name="storageName" type="hidden" value="${(ROLE.rolecode eq 'admin' || ROLE.rolecode eq 'useradmin') ? '':CUR_USER.storageName}"/>

							</c:when>
							<c:when test="${headlist.column_type eq '5' && headlist.code eq 'a01a13a05'}">
								<select class="form-control select2" id="businesserId"  datatype="*">
									<option value=''>请选择</option>
								</select>
								<input id="${headlist.code}" name="${headlist.code}" value="${CUR_USER.userName}" readonly type="hidden"  style="width: 150px" class="inputxt"  ignore="ignore" />
								<input id="businesser" name="businesser"  type="hidden" value="${CUR_USER.realName }" />

							</c:when>
							<c:when test="${headlist.column_type eq '7' && headlist.code eq 'a01a13a06'}">
								<t:dictSelect id="${headlist.code}" field="${headlist.code}" typeGroupCode="paytype"  defaultVal="${emkOfferPricePage[headlist.code] }" hasLabel="false" title="付款方式"></t:dictSelect>
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
			<td align="right" width="150px;">
				<label class="Validform_label">
					是否开票:
				</label>
			</td>
			<td class="value">
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">是否开票</label>
			</td>
		</tr>
	</table>

</t:formvalid>

</body>
<script>
	$(document).ready(function(){
		height = window.top.document.body.offsetHeight;
		headTb = $("#headTb").height();
		footTb = $("#footTb").height();
		console.log(headTb);
		console.log(footTb);
		<%--<c:if test="${headTb ne null && headTb ne ''}">
			headTb = '${headTb}';
			footTb = '${footTb}';
		</c:if>
		console.log(headTb);
		console.log(footTb);--%>
		$("#detailId").load("emkOfferPriceController.do?offerMxList&offerPriceId=${emkOfferPricePage.id}");
		$("#detailId").css("height",height-headTb-footTb);
	});
</script>
</html>

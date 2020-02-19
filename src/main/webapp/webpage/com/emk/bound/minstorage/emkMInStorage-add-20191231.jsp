<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>入库单</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<%@include file="/context/header2.jsp"%>
	<script src="${webRoot}/context/positionSelect.js"></script>
	<script src="${webRoot}/context/storage.js"></script>
	<script src="${webRoot}/context/gys.js"></script>

	<script type="text/javascript">
		//编写自定义JS代码

		$(document).ready(function(){
			$("#detailId").load("emkMInStorageController.do?orderMxList&inStorageId=${emkMInStoragePage.id}");
		});


	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkMInStorageController.do?doAdd" tiptype="1">
	<input id="emkMInStorageId" name="emkMInStorageId" type="hidden" value="${emkMInStoragePage.id }"/>
	<input id="storageId" name="storageId" type="hidden" value="${emkMInStoragePage.storageId }"/>
	<input id="storageName" name="storageName" type="hidden" value="${emkMInStoragePage.storageName }"/>
	<input id="positionId" name="positionId" type="hidden" value="${emkMInStoragePage.positionId }"/>
	<input id="positionName" name="positionName" type="hidden" value="${emkMInStoragePage.positionName }"/>
	<input id="total" name="total" type="hidden" value="${emkMInStoragePage.total }"/>
	<input id="money" name="money" type="hidden" value="${emkMInStoragePage.money }"/>

	<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">

		<tr>
			<td align="right">
				<label class="Validform_label">
					供应商:
				</label>
			</td>
			<td class="value">
				<select class="form-control select2" id="gysId"  datatype="*"  >
					<option value=''>请选择</option>
				</select>
				<input id="gysCode" name="gysCode" type="hidden" value="${emkMInStoragePage.gysCode }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<input id="gys" name="gys" type="hidden"  value="${emkMInStoragePage.gys }"  style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">供应商</label>
			</td>
			<td align="right" >
				<label class="Validform_label">
					采购单号:
				</label>
			</td>
			<td class="value" >
				<input id="rkNo" name="rkNo" readonly value="${emkMInStoragePage.rkNo}"  style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">采购单号</label>
			</td>
			<td align="right" >
				<label class="Validform_label">
					业务员:
				</label>
			</td>
			<td class="value" >
				<select class="form-control select2" id="businesserId"  datatype="*">
					<option value=''>请选择</option>
				</select>
				<input id="businesser" name="businesser" readonly type="hidden"  value="${emkMInStoragePage.businesser }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<input id="businesserName" name="businesserName"  type="hidden" value="${emkMInStoragePage.businesserName }" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">业务员</label>
			</td>

		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					进货仓库:
				</label>
			</td>
			<td class="value">
				<select class="form-control select2" id="storageNameId" datatype="*">
					<option value=''>请选择</option>
				</select>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">进货仓库</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					付款方式:
				</label>
			</td>
			<td class="value">
				<t:dictSelect id="type" field="type" typeGroupCode="paytype"  defaultVal="${emkMInStoragePage.type }" hasLabel="false" title="付款方式"></t:dictSelect>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">付款方式</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					开单日期:
				</label>
			</td>
			<td class="value">
				<input id="kdDate" name="kdDate" readonly value="${kdDate }" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  type="text" style="width: 150px" class="Wdate"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">开单日期</label>
			</td>
			</tr>
		<tr>
			<td align="right" >
				<label class="Validform_label">
					联系人:
				</label>
			</td>
			<td class="value" >
				<input id="lxr" name="lxr"  value="${emkMInStoragePage.lxr}"  style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">联系人</label>
			</td>
			<td align="right" >
				<label class="Validform_label">
					联系电话:
				</label>
			</td>
			<td class="value" >
				<input id="telphone" name="telphone"  value="${emkMInStoragePage.telphone}"  style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">联系电话</label>
			</td>
			<td align="right" >
				<label class="Validform_label">
					地址:
				</label>
			</td>
			<td class="value" >
				<input id="address" name="address"  value="${emkMInStoragePage.address}"  style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">地址</label>
			</td>
		</tr>

		<tr>

		<%--	<td align="right">
				<label class="Validform_label">
					货位:
				</label>
			</td>
			<td class="value">
				<select class="form-control select2" id="positionNameId" >
					<option value=''>请选择</option>
				</select>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">货位</label>
			</td>--%>

			<td align="right">
				<label class="Validform_label">
					送货日期:
				</label>
			</td>
			<td class="value">
				<input id="sendDate" name="sendDate" readonly value="${emkMInStoragePage.sendDate }" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  type="text" style="width: 150px" class="Wdate"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">送货日期</label>
			</td>
			<td align="right" >
				<label class="Validform_label">
					备注:
				</label>
			</td>
			<td class="value" >
				<input id="remark" name="remark"  value="${emkMInStoragePage.remark}"  style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">备注</label>
			</td>
		</tr>
			<%--	<tr>
                    <td align="right" >
                        <label class="Validform_label">
                            入库人:
                        </label>
                    </td>
                    <td class="value" colspan="7">
                        <input id="sender" name="sender" type="text" value="${emkMInStoragePage.rker }"  readonly style="width:  30%" class="inputxt" >
                        <input name="senderUserNames"   type="hidden" value="${emkMInStoragePage.rkerId }"  id="senderUserNames" type="text"  />
                        <t:choose  hiddenName="senderUserNames"  hiddenid="userName" url="userController.do?userdept0" name="userList0" width="700px" height="500px"
                                   icon="icon-search" title="选择处理人" textname="sender,sendDeptName,sendDeptCode" isclear="true" isInit="true"></t:choose>
                    </td>
                </tr>--%>


	</table>
	<div id="detailId" style="overflow-x:hidden;overflow-y: hidden"></div>
	<table style="width: 100%;position:fixed; left:0; bottom:0;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">
					应付金额:
				</label>
			</td>
			<td class="value">
				<input id="yfMoney" name="yfMoney" readonly value="${yfMoney}"  style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">应付金额</label>
			</td>
			<td align="right" >
				<label class="Validform_label">
					优惠金额:
				</label>
			</td>
			<td class="value" >
				<input id="yhMoney" name="yhMoney" readonly value="${yhMoney}"  style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">优惠金额</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					实付金额:
				</label>
			</td>
			<td class="value">
				<input id="sfMoney" name="yfMoney" readonly value="${sfMoney}"  style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">实付金额</label>
			</td>
			<td align="right" >
				<label class="Validform_label">
					本次欠款:
				</label>
			</td>
			<td class="value" >
				<input id="qkMoney" name="qkMoney" readonly value="${qkMoney}"  style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">本次欠款</label>
			</td>
		</tr>
		<tr>
			<td align="right" >
				<label class="Validform_label">
					前次欠款:
				</label>
			</td>
			<td class="value" >
				<input id="preqkMoney" name="preqkMoney" readonly value="${preqkMoney}"  style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">本次欠款</label>
			</td>
			<td align="right" >
				<label class="Validform_label">
					累欠货款:
				</label>
			</td>
			<td class="value" colspan="5">
				<input id="lqhkMoney" name="lqhkMoney" readonly value="${lqhkMoney}"  style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">累欠货款</label>
			</td>
		</tr>
	</table>
	<%--<t:tabs id="inboundDetail" iframe="false"  tabPosition="top" fit="false">
		<t:tab href="emkMInStorageController.do?orderMxList&type=${param.type}&inStorageId=${id}" icon="icon-search" title="${pactTypeName}" id="detail"></t:tab>
	</t:tabs>--%>

</t:formvalid>
</body>
</html>

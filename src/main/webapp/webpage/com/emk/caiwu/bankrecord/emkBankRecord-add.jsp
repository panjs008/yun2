<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>现金银行</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkBankRecordController.do?doAdd" >
					<input id="id" name="id" type="hidden" value="${emkBankRecordPage.id }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							交易日期:
						</label>
					</td>
					<td class="value">
					     	 <input id="dealDate" name="dealDate" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">交易日期</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							仓库ID:
						</label>
					</td>
					<td class="value">
					     	 <input id="storageId" name="storageId" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
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
					     	 <input id="storageName" name="storageName" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">仓库名称</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							账户:
						</label>
					</td>
					<td class="value">
					     	 <input id="bankAccount" name="bankAccount" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">账户</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							摘要:
						</label>
					</td>
					<td class="value">
					     	 <input id="remark" name="remark" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">摘要</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							收入:
						</label>
					</td>
					<td class="value">
					     	 <input id="income" name="income" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">收入</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							支出:
						</label>
					</td>
					<td class="value">
					     	 <input id="outcome" name="outcome" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">支出</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							余额:
						</label>
					</td>
					<td class="value">
					     	 <input id="balance" name="balance" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">余额</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							转账人:
						</label>
					</td>
					<td class="value">
					     	 <input id="transfer" name="transfer" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">转账人</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							转账人ID:
						</label>
					</td>
					<td class="value">
					     	 <input id="transferId" name="transferId" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">转账人ID</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							备注:
						</label>
					</td>
					<td class="value">
					     	 <input id="beizhu" name="beizhu" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">备注</label>
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
  <script src = "webpage/com/emk/caiwu/bankrecord/emkBankRecord.js"></script>		

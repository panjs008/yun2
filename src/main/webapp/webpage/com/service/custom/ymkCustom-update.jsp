<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>客户表</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<%--<script src="${webRoot}/context/storage.js"></script>--%>
	<%@include file="/context/header2.jsp"%>

	<script type="text/javascript">
		//编写自定义JS代码
		$(function(){
			$("#businessType").val("${ymkCustomPage.businessType}");
			$("#cusLevel").val("${ymkCustomPage.cusLevel}");

			$.ajax({
				url : "ymkCustomController.do?getCity&code=A01A05",
				type : 'post',
				cache : false,
				data: null,
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						var msg = d.msg;
						var dataItems = new Array(); //定义一数组
						dataItems = d.obj.split(";"); //字符分割
						//W.document.location.reload(true);
						$('#shengFen').empty();
						var option1 = '<option value="">--选择--</option>';
						var firstJgmc;
						for (i=0;i<dataItems.length ;i++ ) {
							var dataitem = new Array(); //定义一数组
							dataitem = dataItems[i].split(","); //字符分割
							if(dataitem[0]!="") {
								if(dataitem[0] == '${ymkCustomPage.shengFen}'){
									option1 += '<option value='+dataitem[0]+' selected>'+dataitem[1]+'</option>';
								}else{
									option1 += '<option value='+dataitem[0]+'>'+dataitem[1]+'</option>';
								}
							}
						}
						$("#shengFen").append(option1);
						if(${ymkCustomPage.chengShi ne '' && ymkCustomPage.chengShi ne null && ymkCustomPage.shengFen ne '' and ymkCustomPage.shengFen ne null}){
							$.ajax({
								url : "ymkCustomController.do?getCity&code=${ymkCustomPage.shengFen}",
								type : 'post',
								cache : false,
								data: null,
								success : function(data) {
									var d = $.parseJSON(data);
									if (d.success) {
										var msg = d.msg;
										var dataItems = new Array(); //定义一数组
										dataItems = d.obj.split(";"); //字符分割
										//W.document.location.reload(true);
										$('#chengShi').empty();
										var option1='';
										for (i=0;i<dataItems.length ;i++ ) {
											var dataitem = new Array(); //定义一数组
											dataitem = dataItems[i].split(","); //字符分割
											if(dataitem[0] == '${ymkCustomPage.chengShi}'){
												option1 += '<option value='+dataitem[0]+' selected>'+dataitem[1]+'</option>';
											}else{
												option1 += '<option value='+dataitem[0]+'>'+dataitem[1]+'</option>';
											}
										}
										$("#chengShi").append(option1);

										if(${ymkCustomPage.pianQu ne '' && ymkCustomPage.pianQu ne null && ymkCustomPage.chengShi ne '' and ymkCustomPage.chengShi ne null}) {
											$.ajax({
												url : "ymkCustomController.do?getCity&code=${ymkCustomPage.chengShi}",
												type : 'post',
												cache : false,
												data: null,
												success : function(data) {
													var d = $.parseJSON(data);
													if (d.success) {
														var msg = d.msg;
														var dataItems = new Array(); //定义一数组
														dataItems = d.obj.split(";"); //字符分割
														//W.document.location.reload(true);
														$('#pianQu').empty();
														var option1='';
														for (i=0;i<dataItems.length ;i++ ) {
															var dataitem = new Array(); //定义一数组
															dataitem = dataItems[i].split(","); //字符分割
															if(dataitem[0] == '${ymkCustomPage.pianQu}'){
																option1 += '<option value='+dataitem[0]+' selected>'+dataitem[1]+'</option>';
															}else{
																option1 += '<option value='+dataitem[0]+'>'+dataitem[1]+'</option>';
															}
														}
														$("#pianQu").append(option1);
													}
												}
											});
										}

									}
								}
							});
						}

					}
				}
			});

			$("#shengFen").change(function(){
				$.ajax({
					url : "ymkCustomController.do?getCity&code="+$("#shengFen").val(),
					type : 'post',
					cache : false,
					data: null,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							var dataItems = new Array(); //定义一数组
							dataItems = d.obj.split(";"); //字符分割
							//W.document.location.reload(true);
							$('#chengShi').empty();
							$('#pianQu').empty();

							var option3='';
							var firstJgmc;

							for (i=0;i<dataItems.length ;i++ ) {
								var dataitem = new Array(); //定义一数组
								dataitem = dataItems[i].split(","); //字符分割
								if(i == 0){
									firstJgmc = dataitem[0];
								}
								if(dataitem[0]!="") {
									option3 += '<option value='+dataitem[0]+'>'+dataitem[1]+'</option>';
								}
							}
							$("#chengShi").append(option3);

							$.ajax({
								url : "ymkCustomController.do?getCity&code="+firstJgmc,
								type : 'post',
								cache : false,
								data: null,
								success : function(data) {
									var d = $.parseJSON(data);
									if (d.success) {

										var msg = d.msg;
										var dataItems = new Array(); //定义一数组
										dataItems = d.obj.split(";"); //字符分割
										//W.document.location.reload(true);
										$('#pianQu').empty();
										var option1='';
										for (i=0;i<dataItems.length ;i++ ) {
											var dataitem = new Array(); //定义一数组
											dataitem = dataItems[i].split(","); //字符分割
											if(dataitem[0]!="") {
												option1 += '<option value='+dataitem[0]+'>'+dataitem[1]+'</option>';
											}
										}
										$("#pianQu").append(option1);
									}
								}
							});
						}
					}
				});
			});

			$("#chengShi").change(function(){
				$.ajax({
					url : "ymkCustomController.do?getCity&code="+$("#chengShi").val(),
					type : 'post',
					cache : false,
					data: null,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							var dataItems = new Array(); //定义一数组
							dataItems = d.obj.split(";"); //字符分割
							//W.document.location.reload(true);
							$('#pianQu').empty();
							var option3='';

							for (i=0;i<dataItems.length ;i++ ) {
								var dataitem = new Array(); //定义一数组
								dataitem = dataItems[i].split(","); //字符分割
								if(dataitem[0]!="") {
									option3 += '<option value='+dataitem[0]+'>'+dataitem[1]+'</option>';
								}
							}
							$("#pianQu").append(option3);
						}
					}
				});
			});
		});
	</script>
</head>
<body>
<%--<iframe scrolling="no" id="processFrm" frameborder="0" style="overflow-x: hidden;overflow-y: hidden"  src="${webRoot}/context/progress.jsp?finishColums=${countMap.finishColums}&Colums=${countMap.Colums}&recent=${recent}" width="100%" height="20px"></iframe>--%>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="ymkCustomController.do?doUpdate" tiptype="1">
	<input id="id" name="id" type="hidden" value="${ymkCustomPage.id }"/>
	<input id="businesser" name="businesser" type="hidden" value="${ymkCustomPage.businesser }"/>
	<input id="businesserName" name="businesserName" type="hidden" value="${ymkCustomPage.businesserName }"/>


	<table style="width:100%;" cellpadding="0" cellspacing="1" class="formtable">
		<%--<tr>
			<td class="value" align="right" colspan="3">
				<label class="Validform_label">
					档案编号:
				</label>
			</td>
			<td class="value">
				<input id="daanNum" name="daanNum" readonly type="text" value="${ymkCustomPage.daanNum }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">档案编号</label>
			</td>
		</tr>--%>
		<tr>
			<td align="right">
				<label class="Validform_label">
					客户类型:
				</label>
			</td>
			<td class="value">
				<t:dictSelect id="cusType" field="cusType" typeGroupCode="custom" defaultVal="${ymkCustomPage.cusType}" hasLabel="false" title="客户类型"></t:dictSelect>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">客户类型</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					业务员:
				</label>
			</td>
			<td class="value">
				<select class="form-control select2" id="businesserId"   >
					<option value=''>请选择</option>
				</select>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">业务员</label>
			</td>
		</tr>
		<tr>
			<td align="right" width="15%">
				<label class="Validform_label">
					客户代码:
				</label>
			</td>
			<td class="value" width="35%">
				<input id="cusNum" name="cusNum" type="text" readonly value="${ymkCustomPage.cusNum }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">客户代码</label>
			</td>
			<td align="right" width="15%">
				<label class="Validform_label">
					客户名称:
				</label>
			</td>
			<td class="value" width="35%">
				<input id="cusName" name="cusName" type="text" value="${ymkCustomPage.cusName }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">客户名称</label>
			</td>

		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					区域:
				</label>
			</td>

			<td class="value" colspan="3">
				<select id="shengFen" name="shengFen"  style="width:155px;">
					<option>--区域--</option>
				</select>
				<select id="chengShi" name="chengShi" style="width:150px;">
					<option>--城市--</option>
				</select>
				<select id="pianQu" name="pianQu" style="width:150px;">
					<option>--片区--</option>
				</select>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">区域</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					客户级别:
				</label>
			</td>
			<td class="value">
				<t:dictSelect id="cusLevel" field="cusLevel" typeGroupCode="jgmc"  defaultVal="${ymkCustomPage.cusLevel}" hasLabel="false" title="价格名称"></t:dictSelect>
				<%--<select id="cusLevel" name="cusLevel" >
					<option value="">--级别--</option>
					<c:forEach items="${emkProductPriceEntityList}" var="proprice">
						<option value="${proprice.priceNo}">${proprice.priceName}</option>
					</c:forEach>
				</select>--%>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">客户级别</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					折扣:
				</label>
			</td>
			<td class="value" >
				<input id="cusZk" name="cusZk" type="text" datatype="d" value="${ymkCustomPage.cusZk }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">折扣</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					客户地址:
				</label>
			</td>
			<td class="value">
				<input id="address" name="address"  type="text" style="width: 150px"  value="${ymkCustomPage.address }" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">客户地址</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					建立商业关系时间:
				</label>
			</td>
			<td class="value">
				<input id="relationDate" name="relationDate" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${ymkCustomPage.relationDate}" style="width: 150px" class="Wdate"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">建立商业关系时间</label>
			</td>
		</tr>

		<tr>
			<td align="right">
				<label class="Validform_label">
					业务类型:
				</label>
			</td>
			<td class="value">
				<select id="businessType" name="businessType">
					<option name="0">直接</option>
					<option name="1">中间</option>
				</select>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">业务类型</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					潜在业务量/年:
				</label>
			</td>
			<td class="value" >
				<input id="qzywl" name="qzywl" type="text" datatype="*" value="${ymkCustomPage.qzywl }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">潜在业务量/年</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					开户行:
				</label>
			</td>
			<td class="value">
				<input id="bankName" name="bankName" type="text" value="${ymkCustomPage.bankName }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">开户行</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					开户账号:
				</label>
			</td>
			<td class="value" >
				<input id="bankAccount" name="bankAccount" type="text" value="${ymkCustomPage.bankAccount }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">开户账号</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					税号:
				</label>
			</td>
			<td class="value">
				<input id="suiNum" name="suiNum" type="text" value="${ymkCustomPage.suiNum }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">税号</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					期初应付:
				</label>
			</td>
			<td class="value">
				<input id="bcqkMoney" name="bcqkMoney" type="text"  datatype="d" value="${ymkCustomPage.bcqkMoney }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">期初应付</label>
			</td>
		</tr>

		<tr>
			<td align="right">
				<label class="Validform_label">
					主营产品:
				</label>
			</td>
			<td class="value" colspan="3">
				<textarea id="mainContent" style="width:90%;height:50px" class="inputxt" rows="3" name="mainContent">${ymkCustomPage.mainContent }</textarea>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">主营产品</label>
			</td>
		</tr>

		<tr>
			<td align="right" >
				<label class="Validform_label">
					业务联系人
				</label>
			</td>
			<td class="value" colspan="3">
				<table style="width:100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr >
						<td width="50" height="32" align="center"></td>
						<td width="120" >&nbsp;&nbsp;联系人一</td>
						<td width="120" >&nbsp;&nbsp;联系人二</td>
						<td width="120">&nbsp;&nbsp;联系人三</td>
						<td width="120">&nbsp;&nbsp;联系人四</td>
						<td width="120">&nbsp;&nbsp;联系人五</td>
						<td width="360" colspan="3">&nbsp;&nbsp;</td>
					</tr>
					<tr>
						<td width="50" height="32" align="center">姓名</td>
						<td class="value" height="32">
							<input id="zlxr" name="zlxr" type="text" datatype="*" value="${ymkCustomPage.zlxr }" style="width: 90px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">主联系人</label>
						</td>
						<td class="value" height="32">
							<input id="ywlxr2" name="ywlxr2" type="text" value="${ymkCustomPage.ywlxr2 }" style="width: 90px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">联系人二</label>
						</td>
						<td class="value" height="32">
							<input id="ywlxr3" name="ywlxr3" type="text"  value="${ymkCustomPage.ywlxr3 }" style="width: 90px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">联系人三</label>
						</td>
						<td class="value" height="32">
							<input id="ywlxr4" name="ywlxr4" type="text"  value="${ymkCustomPage.ywlxr4 }" style="width: 90px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">联系人四</label>
						</td>
						<td class="value" height="32">
							<input id="ywlxr5" name="ywlxr5" type="text"  value="${ymkCustomPage.ywlxr5 }" style="width: 90px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">联系人五</label>
						</td>
						<td class="value" colspan="3" height="32">
					</tr>
					<tr>
						<td width="50" height="32" align="center">邮箱</td>
						<td class="value">
							<input id="email" name="email" type="text" value="${ymkCustomPage.email }" style="width: 90px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">邮箱</label>
						</td>
						<td class="value" height="32">
							<input id="ywlxr2Email" name="ywlxr2Email" type="text" datatype="e" value="${ymkCustomPage.ywlxr2Email }" style="width: 90px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">联系人二</label>
						</td>
						<td class="value" height="32">
							<input id="ywlxr3Email" name="ywlxr3Email" type="text"  datatype="e" value="${ymkCustomPage.ywlxr3Email }" style="width: 90px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">联系人三</label>
						</td>
						<td class="value" height="32">
							<input id="ywlxr4Email" name="ywlxr4Email" type="text"  datatype="e" value="${ymkCustomPage.ywlxr4Email }" style="width: 90px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">联系人四</label>
						</td>
						<td class="value" height="32">
							<input id="ywlxr5Email" name="ywlxr5Email" type="text"  datatype="e" value="${ymkCustomPage.ywlxr5Email }" style="width: 90px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">联系人五</label>
						</td>
						<td class="value" colspan="3" height="32">
					</tr>
					<tr>
						<td width="50" height="32" align="center">电话</td>
						<td class="value">
							<input id="workphone" name="workphone" value="${ymkCustomPage.workphone }" type="text" style="width: 90px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">办公电话</label>
						</td>
						<td class="value" height="32">
							<input id="ywlxr2Telphone" name="ywlxr2Telphone"  type="text" value="${ymkCustomPage.ywlxr2Telphone }" style="width: 90px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">联系人二</label>
						</td>
						<td class="value" height="32">
							<input id="ywlxr3Telphone" name="ywlxr3Telphone"  type="text"  value="${ymkCustomPage.ywlxr3Telphone }" style="width: 90px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">联系人三</label>
						</td>
						<td class="value" height="32">
							<input id="ywlxr4Telphone" name="ywlxr4Telphone"  type="text"  value="${ymkCustomPage.ywlxr4Telphone }" style="width: 90px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">联系人四</label>
						</td>
						<td class="value" height="32">
							<input id="ywlxr5Telphone" name="ywlxr5Telphone"  type="text"  value="${ymkCustomPage.ywlxr5Telphone }" style="width: 90px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">联系人五</label>
						</td>
						<td class="value" colspan="3" height="32">
					</tr>
				</table>
			</td>
		</tr>

	</table>
</t:formvalid>
</body>
<script src = "webpage/com/service/custom/ymkCustom.js"></script>

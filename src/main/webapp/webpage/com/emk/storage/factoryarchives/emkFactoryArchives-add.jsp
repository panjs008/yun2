<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>供应商</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<script type="text/javascript">
		//编写自定义JS代码
		$(function(){
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
								if(dataitem[0] == '${emkFactoryArchivesPage.shengFen}'){
									option1 += '<option value='+dataitem[0]+' selected>'+dataitem[1]+'</option>';
								}else{
									option1 += '<option value='+dataitem[0]+'>'+dataitem[1]+'</option>';
								}
							}
						}
						$("#shengFen").append(option1);
						if(${emkFactoryArchivesPage.chengShi ne '' && emkFactoryArchivesPage.chengShi ne null}){
							$.ajax({
								url : "ymkCustomController.do?getCity&code=${emkFactoryArchivesPage.shengFen}",
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
											if(dataitem[0] == '${emkFactoryArchivesPage.chengShi}'){
												option1 += '<option value='+dataitem[0]+' selected>'+dataitem[1]+'</option>';
											}else{
												option1 += '<option value='+dataitem[0]+'>'+dataitem[1]+'</option>';
											}
										}
										$("#chengShi").append(option1);

										$.ajax({
											url : "ymkCustomController.do?getCity&code=${emkFactoryArchivesPage.chengShi}",
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
														if(dataitem[0] == '${emkFactoryArchivesPage.pianQu}'){
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
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="emkFactoryArchivesController.do?doAdd" tiptype="1">
	<input id="id" name="id" type="hidden" value="${emkFactoryArchivesPage.id }"/>
	<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
		<%--<tr>
			<td class="value" align="right" colspan="3">
				<label class="Validform_label">
					档案编号:
				</label>
			</td>
			<td class="value">
				<input id="archivesNo" name="archivesNo"  value="${emkFactoryArchivesPage.archivesNo }"  type="text" readonly style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">档案编号</label>
			</td>
		</tr>--%>
		<tr>
			<td align="right">
				<label class="Validform_label">
					供应商名称:
				</label>
			</td>
			<td class="value">
				<input id="companyNameZn" name="companyNameZn" value="${emkFactoryArchivesPage.companyNameZn }" datatype="*" type="text" style="width: 150px" class="inputxt" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">供应商名称</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					供应商代码:
				</label>
			</td>
			<td class="value">
				<input id="companyCode" name="companyCode" readonly value="${emkFactoryArchivesPage.companyCode }" type="text" style="width: 150px" class="inputxt" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">供应商代码</label>
			</td>


		</tr>

		<tr>
			<td align="right">
				<label class="Validform_label">
					省份:
				</label>
			</td>
			<td class="value">
				<select id="shengFen" name="shengFen"  style="width:155px;">
					<option>--选择--</option>
				</select>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">省份</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					城市:
				</label>
			</td>
			<td class="value">
				<select id="chengShi" name="chengShi" style="width:155px;">
					<option>--选择--</option>
				</select>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">城市</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					片区:
				</label>
			</td>
			<td class="value">
				<select id="pianQu" name="pianQu" style="width:150px;">
					<option>--片区--</option>
				</select>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">片区</label>
			</td>
			<td align="right" >
				<label class="Validform_label">
					联系人:
				</label>
			</td>
			<td class="value" >
				<input id="primaryContact" name="primaryContact" value="${emkFactoryArchivesPage.primaryContact }" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">主要联系人</label>
			</td>
		</tr>

		<tr>
			<td align="right" >
				<label class="Validform_label">
					地址:
				</label>
			</td>
			<td class="value" colspan="3">
				<input id="addressZn" name="addressZn" type="text" value="${emkFactoryArchivesPage.addressZn }" style="width: 80%" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">地址</label>
			</td>
		</tr>

		<tr>
			<td align="right">
				<label class="Validform_label">
					手机号码:
				</label>
			</td>
			<td class="value">
				<input id="primaryContactTel" name="primaryContactTel" value="${emkFactoryArchivesPage.primaryContactTel }" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">手机号码</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					邮箱:
				</label>
			</td>
			<td class="value">
				<input id="primaryContactEmail" name="primaryContactEmail" value="${emkFactoryArchivesPage.primaryContactEmail }" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">邮箱</label>
			</td>
		</tr>

		<tr>
			<td align="right">
				<label class="Validform_label">
					固定电话:
				</label>
			</td>
			<td class="value">
				<input id="gdhm" name="gdhm" value="${emkFactoryArchivesPage.gdhm }" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">固定电话</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					邮编:
				</label>
			</td>
			<td class="value">
				<input id="postCode" name="postCode" value="${emkFactoryArchivesPage.postCode }" type="text" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">固定电话</label>
			</td>
		</tr>

		<tr>
			<td align="right">
				<label class="Validform_label">
					开户行:
				</label>
			</td>
			<td class="value">
				<input id="bankName" name="bankName" type="text" value="${emkFactoryArchivesPage.bankName }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">开户行</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					账号:
				</label>
			</td>
			<td class="value" >
				<input id="bankAccount" name="bankAccount" type="text" value="${emkFactoryArchivesPage.bankAccount }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">账号</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					期初应付:
				</label>
			</td>
			<td class="value" colspan="3">
				<input id="bcqkMoney" name="bcqkMoney" type="text"  datatype="d" value="${emkFactoryArchivesPage.bcqkMoney }" style="width: 150px" class="inputxt"  ignore="ignore" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">期初应付</label>
			</td>

		</tr>
	</table>

</t:formvalid>
</body>
<script src = "webpage/com/emk/storage/factoryarchives/emkFactoryArchives.js"></script>

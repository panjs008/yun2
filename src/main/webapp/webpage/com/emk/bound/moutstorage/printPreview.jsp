<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<html>
<title>打印预览</title>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<style>

body{
	margin:0px auto;
	text-align:center;
}

body{
	margin:0px auto;
	text-align:center;
}

#pcd_main{
	width:100%;
	height:500px;
	text-align:center;
	font-size:14px;
	margin-top:0px;
	padding:0px;

}

#pcd_content{
	width:100%;
	font-size:14px;
	margin-bottom: 0px;
}

.pcd_content_tb{
	width:100%;
	height:220px;
		font-size:14px;
		border:1px solid #000000;
		border-collapse:collapse;

}

.pcd_tb_left{
	border:1px solid #000000;
	width:638px;

}

 .pcd_left_title{
 	width:122px;
 	height:24px;
	text-align:left;
	white-space:nowrap;
	font-size:14px;
 }

 .pcd_left_text_noline{
 	width:260px;
 	height:24px;
 }


 #pcd_normal_div{
 	width:670px;
 	margin-top:0px;
 }

 #pcd_normal_title2{
 	margin-top:0px;
	 font-size:16px;
	font-weight:bold;

 }
.pcd_normal_tb{
 	width:100%;
	border:1px solid #000000;
	border-collapse:collapse;
     text-align: center;
 }

.pcd_normal_top_td_center{
	border:1px solid #000000;
	width:80px;
	line-height:22px;
	padding:0px 0px 0px 0px;
	word-wrap:break-word;
	word-break:break-all;
	text-align: center;
}
.pcd_normal_top_td_center0{
	border:1px solid #000000;
	width:150px;
	line-height:22px;
	padding:0px 0px 0px 0px;
	word-wrap:break-word;
	word-break:break-all;
	text-align: center;
}

.pcd_normal_top_td_center1{
	border:1px solid #000000;
	width:55px;
	line-height:22px;
	padding:0px 0px 0px 0px;
	word-wrap:break-word;
	word-break:break-all;
	text-align: center;
}

.pcd_normal_top_td_center2{
	border:1px solid #000000;
	width:110px;
	line-height:22px;
	word-wrap:break-word;
	word-break:break-all;
	padding:0px 0px 0px 0px;
	text-align: center;
}
.pcd_normal_top_td_center3{
	border:1px solid #000000;
	width:90px;
	line-height:22px;
	padding:0px 0px 0px 0px;
	word-wrap:break-word;
	word-break:break-all;
	text-align: center;
}
.pcd_normal_top_td_xh{
	border:1px solid #000000;
	width:25px;
	line-height:22px;
	padding:0px 0px 0px 0px;
	word-wrap:break-word;
	word-break:break-all;
	text-align: center;
}
.pcd_normal_top_td_xh1{
	border:1px solid #000000;
	width:25px;
	line-height:22px;
	padding:0px 0px 0px 0px;
	word-wrap:break-word;
	word-break:break-all;
	text-align: center;
}
 .pcd_normal_top_td{
 	border:1px solid #000000;
		width:30px;
		line-height:50px;
		padding:0px 0px 0px 0px;
 }
 .pcd_normal_top_td2{
 	    border:1px solid #000000;
		width:180px;
		line-height:50px;
		padding:0px 0px 0px 0px;
 }

 .pcd_normal_title{
	 width:100%;
	 margin-top:0px;
	 font-size:10px;
     text-align: left;
 }

.pcd_normal_title1{
	width:100%;
	margin-top:0px;
	font-size:8px;
	line-height:80px;
	text-align: left;
}

 .pcd_normal_tb_foot{
 	width:670px;
    padding-left:0px;
    text-align: left;
    font-size: 15px;
    margin-top: 0px;
 }
</style>

    <script type="text/javascript">
	function printpreview1(){  //打印预览 (使用IE自带控件)
		//先设置IE的页边距
		//pageSetup_IE(header,footer,topMargin,bottomMargin,leftMargin,leftMargin);

		var obj=parent.document.getElementById("pcd_main");
       	obj.style.overflow="visible";
     	//if($.browser.msie&&$.browser.version=="8.0"&&$("html")[0].scrollHeight>$("html").height()){
		//		$("html").css("overflow","visible");
		//	}
		document.all.wb.execwb(7,1);
		//setTimeout("function(){window.wb.execwb(45,1);//关闭}",5000);//5秒后关闭打印预览页面
		var obj=parent.document.getElementById("pcd_main");
       	obj.style.overflow="scroll";

	}
	function previewArea(){
		$("#div_print").css("display","none");
		$("#div_print2").css("display","");
		window.print();
		$("#div_print").css("display","");
		$("#div_print2").css("display","none");
	}
	</script>

<style>
@media print{
	.noPrintTab{display:none;}
}
</style>
</head>

<body>
<!--startprint1-->
<div id="pcd_main" style="width:100%;height: 100%;">
	<div id="div_print" style="width:100%;height:14.85cm;">
		<br/>
		<span  style="font: '宋体';font-size: 26px;text-align: center;font-weight: bolder;">${emkMOutStorage.type eq '2' ? '销售申请单':'出库申请单'}</span><br/><br/>

		<table class="pcd_normal_tb_foot" style="width:100%;">
			<c:if test="${emkMOutStorage.type ne '2'}">
				<tr>
					<td width="55%" align="left" height="22">出库日期：${emkMOutStorage.outDate}</td>
					<td width="45%" align="left">仓库名称：${emkMOutStorage.storageName}&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</tr>
				<tr>
					<td width="55%" align="left" height="22">申请人：${emkMOutStorage.appler}</td>
					<td width="45%" align="left">单号：${emkMOutStorage.ckNo}&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</tr>
				<tr>
					<td width="55%" align="left" height="22">客户名称：${emkMOutStorage.cusName}</td>
					<td width="45%" align="left">出库类型：${emkMOutStorage.type eq '3' ? '正常出库':'临时出库'}&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</tr>
			</c:if>
			<c:if test="${emkMOutStorage.type eq '2'}">
				<tr>
					<td width="55%" align="left" height="22">销售日期：${emkMOutStorage.outDate}</td>
					<td width="45%" align="left">客户名称：${emkMOutStorage.cusName}&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</tr>
				<tr>
					<td width="55%" align="left" height="22">申请人：${emkMOutStorage.appler}</td>
					<td width="45%" align="left">单号：${emkMOutStorage.ckNo}&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</tr>
				<tr>
					<td width="55%" align="left" height="22">企业注册地址：${emkMOutStorage.qyzcAddress}</td>
					<td width="45%" align="left">收票收件地址：${emkMOutStorage.spAddress}&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</tr>

				<tr>
					<td width="55%" align="left" height="22">开户行：${emkMOutStorage.bankName}</td>
					<td width="45%" align="left">开户账号：${emkMOutStorage.bankAccount}&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</tr>
			</c:if>
		</table>
		<div  style="width:98%;">
			<table  class="pcd_normal_tb" style="width:100%;margin-left:6px;">
				<tr class="pcd_normal_title">
					<td class='pcd_normal_top_td_xh'  >序号</td>
					<td class='pcd_normal_top_td_center2'  >产品名称</td>
					<td class='pcd_normal_top_td_center1'  >产品编号</td>
					<td class='pcd_normal_top_td_center'  >规格</td>
					<td class='pcd_normal_top_td_center3'  >型号</td>
					<td class='pcd_normal_top_td_xh'  >单位</td>
					<td class='pcd_normal_top_td_center0'  >生产企业</td>
					<td class='pcd_normal_top_td_center'  >许可证号</td>
					<td class='pcd_normal_top_td_center'  >注册证号</td>
					<td class='pcd_normal_top_td_center1'  >生产日期</td>
					<td class='pcd_normal_top_td_center1'  >有效日期</td>
					<td class='pcd_normal_top_td_center'  >批号</td>
					<td class='pcd_normal_top_td_xh1'  >储运</td>
				</tr>
	
				<c:forEach var="list" items="${rkglMxEntities}" varStatus="status">
					<tr id="page${status.index}" class="pcd_normal_title1">
						<td class='pcd_normal_top_td_xh' >${status.index+1}</td>
						<td class='pcd_normal_top_td_center2' >${list.proZnName}</td>
						<td class='pcd_normal_top_td_center1' >${list.proNum}</td>
						<td class='pcd_normal_top_td_center' >${list.standards}</td>
						<td class='pcd_normal_top_td_center3' >${list.brand}</td>
						<td class='pcd_normal_top_td_xh' >${list.unit}</td>
						<td class='pcd_normal_top_td_center0' >${list.scqy}</td>
						<td class='pcd_normal_top_td_center' >${list.lincese}</td>
						<td class='pcd_normal_top_td_center' >${list.zcNum}</td>
						<td class='pcd_normal_top_td_center1' >${list.scDate}</td>
						<td class='pcd_normal_top_td_center1' >${list.limitDate}</td>
						<td class='pcd_normal_top_td_center' >${list.betch}</td>
						<td class='pcd_normal_top_td_xh1' >${list.cytj}</td>
					</tr>
					</c:forEach>
		  </table>
		</div>
      </div>

	<div id="div_print2" style="width:100%;height:14.85cm;display: none;margin-left:10px;">
		<%int i=1;%>
		<c:forEach var="data" items="${dataList}" varStatus="status">
			<br/>
			<span  style="font: '宋体';font-size: 26px;text-align: center;font-weight: bolder;">${emkMOutStorage.type eq '2' ? '销售申请单':'出库申请单'}</span><br/><br/>
			<div id="page${status.index}" style="page-break-after:always">
				<table class="pcd_normal_tb_foot" style="width:100%;">
					<c:if test="${emkMOutStorage.type ne '2'}">
						<tr>
							<td width="55%" align="left" height="22">出库日期：${emkMOutStorage.outDate}</td>
							<td width="45%" align="left">仓库名称：${emkMOutStorage.storageName}&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>
						<tr>
							<td width="55%" align="left" height="22">申请人：${emkMOutStorage.appler}</td>
							<td width="45%" align="left">单号：${emkMOutStorage.ckNo}&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>
						<tr>
							<td width="55%" align="left" height="22">客户名称：${emkMOutStorage.cusName}</td>
							<td width="45%" align="left">出库类型：${emkMOutStorage.type eq '3' ? '正常出库':'临时出库'}&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>
					</c:if>
					<c:if test="${emkMOutStorage.type eq '2'}">
						<tr>
							<td width="55%" align="left" height="22">销售日期：${emkMOutStorage.outDate}</td>
							<td width="45%" align="left">客户名称：${emkMOutStorage.cusName}&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>
						<tr>
							<td width="55%" align="left" height="22">申请人：${emkMOutStorage.appler}</td>
							<td width="45%" align="left">单号：${emkMOutStorage.ckNo}&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>
						<tr>
							<td width="55%" align="left" height="22">企业注册地址：${emkMOutStorage.qyzcAddress}</td>
							<td width="45%" align="left">收票收件地址：${emkMOutStorage.spAddress}&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>

						<tr>
							<td width="55%" align="left" height="22">开户行：${emkMOutStorage.bankName}</td>
							<td width="45%" align="left">开户账号：${emkMOutStorage.bankAccount}&nbsp;&nbsp;&nbsp;&nbsp;</td>
						</tr>
					</c:if>
				</table>
				<div id="pcd_normal_div" style="width:98%;">
					<table id="tb" class="pcd_normal_tb" style="width:100%;">
						<tr class="pcd_normal_title">
							<td class='pcd_normal_top_td_xh'  >序号</td>
							<td class='pcd_normal_top_td_center2'  >产品名称</td>
							<td class='pcd_normal_top_td_center1'  >产品编号</td>
							<td class='pcd_normal_top_td_center'  >规格</td>
							<td class='pcd_normal_top_td_center3'  >型号</td>
							<td class='pcd_normal_top_td_xh'  >单位</td>
							<td class='pcd_normal_top_td_center0'  >生产企业</td>
							<td class='pcd_normal_top_td_center'  >许可证号</td>
							<td class='pcd_normal_top_td_center'  >注册证号</td>
							<td class='pcd_normal_top_td_center1'  >生产日期</td>
							<td class='pcd_normal_top_td_center1'  >有效日期</td>
							<td class='pcd_normal_top_td_center'  >批号</td>
							<td class='pcd_normal_top_td_xh1'  >储运</td>
						</tr>
						<c:forEach var="list" items="${data.subdataList}" varStatus="status">
							<tr  class="pcd_normal_title1">
								<td class='pcd_normal_top_td_xh' ><%=i%></td>
								<td class='pcd_normal_top_td_center2' >${list.proZnName}</td>
								<td class='pcd_normal_top_td_center1' >${list.proNum}</td>
								<td class='pcd_normal_top_td_center' >${list.standards}</td>
								<td class='pcd_normal_top_td_center3' >${list.brand}</td>
								<td class='pcd_normal_top_td_xh' >${list.unit}</td>
								<td class='pcd_normal_top_td_center0' >${list.scqy}</td>
								<td class='pcd_normal_top_td_center' >${list.lincese}</td>
								<td class='pcd_normal_top_td_center' >${list.zcNum}</td>
								<td class='pcd_normal_top_td_center1' >${list.scDate}</td>
								<td class='pcd_normal_top_td_center1' >${list.limitDate}</td>
								<td class='pcd_normal_top_td_center' >${list.betch}</td>
								<td class='pcd_normal_top_td_xh1' >${list.cytj}</td>
							</tr>
							<%i++;%>
						</c:forEach>

					</table>
				</div>
			</div>
		</c:forEach>
	</div>

</div>

</body>
</html>


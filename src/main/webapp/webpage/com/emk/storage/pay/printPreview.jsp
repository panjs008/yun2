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
	height:200px;
		font-size:14px;
		border:1px solid #000000;
		border-collapse:collapse;

}


.pcd_vertical_word{
	padding-top:20px;
	layout-flow:vertical-ideographic;
	border:1px solid #000000;
	width:30px;

}

.pcd_tb_right{
	border:1px solid #000000;
	width:638px;

}

 .pcd_left_title{
 	width:120px;
 	height:24px;
	text-align:right;
	white-space:nowrap;
	font-size:14px;
 }

 .pcd_right_text_noline{
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
	width:60px;
	line-height:22px;
	padding:0px 0px 0px 0px;
	word-wrap:break-word;
	word-break:break-all;
	text-align: center;
}
.pcd_normal_top_td_center0{
	border:1px solid #000000;
	width:220px;
	line-height:22px;
	padding:0px 0px 0px 0px;
	word-wrap:break-word;
	word-break:break-all;
	text-align: center;
}
.pcd_normal_top_td_center01{
	border:1px solid #000000;
	width:180px;
	line-height:22px;
	padding:0px 0px 0px 0px;
	word-wrap:break-word;
	word-break:break-all;
	text-align: center;
}

.pcd_normal_top_td_center1{
	border:1px solid #000000;
	width:150px;
	line-height:22px;
	padding:0px 0px 0px 0px;
	word-wrap:break-word;
	word-break:break-all;
	text-align: center;
}
.pcd_normal_top_td_center11{
	border:1px solid #000000;
	width:130px;
	line-height:22px;
	padding:0px 0px 0px 0px;
	word-wrap:break-word;
	word-break:break-all;
	text-align: center;
}
.pcd_normal_top_td_center2{
	border:1px solid #000000;
	width:100px;
	line-height:22px;
	word-wrap:break-word;
	word-break:break-all;
	padding:0px 0px 0px 0px;
	text-align: center;
}
.pcd_normal_top_td_center3{
	border:1px solid #000000;
	width:100px;
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
    padding-right:0px;
    text-align: right;
    font-size: 15px;
    margin-top: 0px;
 }
</style>

    <script type="text/javascript">
	function printpreview1(){  //打印预览 (使用IE自带控件)
		//先设置IE的页边距
		//pageSetup_IE(header,footer,topMargin,bottomMargin,leftMargin,rightMargin);

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
		window.print();
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
		<span  style="font: '宋体';font-size: 26px;text-align: center;font-weight: bolder;">${emkPayEntity.payDate}结算单</span><br/>
		<table class="pcd_normal_tb_foot" style="width:100%;">
			<tr>
				<td width="50%" align="left" height="40">供应商名称：${emkPayEntity.gys }</td>
				<td width="50%" align="right">订单号：${emkPayEntity.orderNo}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			</tr>
		</table>
		<div  style="width:98%;">
				<table  class="pcd_normal_tb" style="width:100%;margin-left:6px;">
					<tr class="pcd_normal_title">
						<td class='pcd_normal_top_td_center' >订单总金额</td>
						<td class='pcd_normal_top_td_center' >叫布费用</td>
						<td class='pcd_normal_top_td_center' >其他物料费用</td>
						<td class='pcd_normal_top_td_center' >发货总金额</td>
						<td class='pcd_normal_top_td_center' >退货总金额</td>
						<td class='pcd_normal_top_td_center' >付款总金额</td>
						<td class='pcd_normal_top_td_center' >未付款</td>

					</tr>

					<tr class="pcd_normal_title1">
						<td class='pcd_normal_top_td_center'>${orderMoney}</td>
						<td class='pcd_normal_top_td_center'>${setjbMoney}</td>
						<td class='pcd_normal_top_td_center'>${wlMoney}</td>
						<td class='pcd_normal_top_td_center'>${fhMoney}</td>
						<td class='pcd_normal_top_td_center'>${thMoney}</td>
						<td class='pcd_normal_top_td_center'>${fkMoney}</td>
						<td class='pcd_normal_top_td_center'>${wfk}</td>
					</tr>
				</table>
				<table class="pcd_normal_tb_foot" style="width:100%;">
					<tr>
						<td width="100%" align="left" height="20">付款信息</td>
					</tr>
				</table>
				<table  class="pcd_normal_tb" style="width:100%;margin-left:6px;margin-top: 2px;">
					<tr class="pcd_normal_title">
						<td class='pcd_normal_top_td_center'  >序号</td>
						<td class='pcd_normal_top_td_center' >订单号</td>
						<td class='pcd_normal_top_td_center' >日期</td>
						<td class='pcd_normal_top_td_center' >金额</td>
						<td class='pcd_normal_top_td_center' >方式</td>
						<td class='pcd_normal_top_td_center' >备注</td>
						<td class='pcd_normal_top_td_center'  >状态</td>
					</tr>
					<c:forEach var="list" items="${payEntityList}" varStatus="status">
						<tr  class="pcd_normal_title1">
							<td class='pcd_normal_top_td_center'>${status.index+1}</td>
							<td class='pcd_normal_top_td_center'>${list.orderNo}</td>
							<td class='pcd_normal_top_td_center'>${list.payDate}</td>
							<td class='pcd_normal_top_td_center'>${list.money}</td>
							<td class='pcd_normal_top_td_center'>
								<c:if test="${list.type eq 'zfb'}">支付宝</c:if>
								<c:if test="${list.type eq 'xj'}">现金</c:if>
								<c:if test="${list.type eq 'wx'}">微信</c:if>
								<c:if test="${list.type eq 'yhzz'}">银行转账</c:if>
							</td>
							<td class='pcd_normal_top_td_center'>${list.remark}</td>
							<td class='pcd_normal_top_td_center'>
								<c:if test="${list.state eq '1'}">提交</c:if>
							</td>

						</tr>
					</c:forEach>
				</table>

				<table class="pcd_normal_tb_foot" style="width:100%;">
					<tr>
						<td width="100%" align="left" height="20">已发货明细（扣除退货）</td>
					</tr>
				</table>

				<table  class="pcd_normal_tb" style="width:100%;margin-left:6px;">
					<tr class="pcd_normal_title">
						<td class='pcd_normal_top_td_center' >序号</td>
						<td class='pcd_normal_top_td_center0'>款号-颜色-内长</td>
						<td class='pcd_normal_top_td_center' >${emkSizePage.sizeA eq null ? '27':emkSizePage.sizeA}</td>
						<td class='pcd_normal_top_td_center' >${emkSizePage.sizeB eq null ? '28':emkSizePage.sizeB}</td>
						<td class='pcd_normal_top_td_center' >${emkSizePage.sizeC eq null ? '29':emkSizePage.sizeC}</td>
						<td class='pcd_normal_top_td_center' >${emkSizePage.sizeD eq null ? '30':emkSizePage.sizeD}</td>
						<td class='pcd_normal_top_td_center' >${emkSizePage.sizeE eq null ? '31':emkSizePage.sizeE}</td>
						<td class='pcd_normal_top_td_center' >${emkSizePage.sizeF eq null ? '32':emkSizePage.sizeF}</td>
						<td class='pcd_normal_top_td_center' >${emkSizePage.sizeG eq null ? '33':emkSizePage.sizeG}</td>
						<td class='pcd_normal_top_td_center' >${emkSizePage.sizeH eq null ? '34':emkSizePage.sizeH}</td>
						<td class='pcd_normal_top_td_center' >${emkSizePage.sizeI eq null ? '36':emkSizePage.sizeI}</td>
						<td class='pcd_normal_top_td_center' >${emkSizePage.sizeJ eq null ? '38':emkSizePage.sizeJ}</td>
						<td class='pcd_normal_top_td_center' >${emkSizePage.sizeK eq null ? '40':emkSizePage.sizeK}</td>
						<td class='pcd_normal_top_td_center' >${emkSizePage.sizeL eq null ? 'XS':emkSizePage.sizeL}</td>
						<td class='pcd_normal_top_td_center' >${emkSizePage.sizeM eq null ? 'S':emkSizePage.sizeM}</td>
						<td class='pcd_normal_top_td_center' >${emkSizePage.sizeN eq null ? 'M':emkSizePage.sizeN}</td>
						<td class='pcd_normal_top_td_center' >${emkSizePage.sizeO eq null ? 'L':emkSizePage.sizeO}</td>
						<td class='pcd_normal_top_td_center' >${emkSizePage.sizeP eq null ? 'XL':emkSizePage.sizeP}</td>
						<td class='pcd_normal_top_td_center' >${emkSizePage.sizeQ}</td>
						<td class='pcd_normal_top_td_center' >${emkSizePage.sizeR}</td>
						<td class='pcd_normal_top_td_center' >${emkSizePage.sizeS}</td>
						<td class='pcd_normal_top_td_center' >${emkSizePage.sizeT}</td>
						<td class='pcd_normal_top_td_center' >${emkSizePage.sizeU}</td>
						<td class='pcd_normal_top_td_center' >${emkSizePage.sizeV}</td>
						<td class='pcd_normal_top_td_center' >合计</td>
						<td class='pcd_normal_top_td_center' >单价</td>
						<td class='pcd_normal_top_td_center2' >金额</td>
					</tr>
					<c:set var="zjs" value="0"/>
					<c:set var="zj" value="0"/>

					<c:forEach var="list" items="${emkProOrderDetailEntities}" varStatus="status">
						<tr  class="pcd_normal_title1">
							<td class='pcd_normal_top_td_center' >${status.index+1}</td>
							<td class='pcd_normal_top_td_center' >${list.color_value}-${list.color}-${list.size}</td>
							<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_a ne '0.0' ? list.total_a:''}" /></td>
							<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_b ne '0.0' ? list.total_b:''}" /></td>
							<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_c ne '0.0' ? list.total_c:''}" /></td>
							<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_d ne '0.0' ? list.total_d:''}" /></td>
							<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_e ne '0.0' ? list.total_e:''}" /></td>
							<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_f ne '0.0' ? list.total_f:''}" /></td>
							<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_g ne '0.0' ? list.total_g:''}" /></td>
							<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_h ne '0.0' ? list.total_h:''}" /></td>
							<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_i ne '0.0' ? list.total_i:''}" /></td>
							<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_j ne '0.0' ? list.total_j:''}" /></td>
							<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_k ne '0.0' ? list.total_k:''}" /></td>
							<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_l ne '0.0' ? list.total_l:''}" /></td>
							<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_m ne '0.0' ? list.total_m:''}" /></td>
							<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_n ne '0.0' ? list.total_n:''}" /></td>
							<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_o ne '0.0' ? list.total_o:''}" /></td>
							<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_p ne '0.0' ? list.total_p:''}" /></td>
							<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_q ne '0.0' ? list.total_q:''}" /></td>
							<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_r ne '0.0' ? list.total_r:''}" /></td>
							<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_s ne '0.0' ? list.total_s:''}" /></td>
							<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_t ne '0.0' ? list.total_t:''}" /></td>
							<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_u ne '0.0' ? list.total_u:''}" /></td>
							<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_v ne '0.0' ? list.total_v:''}" /></td>

							<td class='pcd_normal_top_td_center' >${list.total}</td>
							<td class='pcd_normal_top_td_center' >${list.price}</td>
							<td class='pcd_normal_top_td_center2' >${list.total*list.price}</td>

						</tr>
						<c:set var="zjs" value="${zjs+list.total}"/>
						<c:set var="zj" value="${zj+list.total*list.price}"/>
					</c:forEach>

					<tr>
						<td  class='pcd_normal_top_td_center' align="center" colspan="24">总计</td>
						<td class='pcd_normal_top_td_center'>${zjs}</td>
						<td  class='pcd_normal_top_td_center'>&nbsp;</td>
						<td class='pcd_normal_top_td_center'>${zj}</td>
					</tr>
			  </table>


			<table>
				<tr>
					<td width="70%" valign="top">
						<table class="pcd_normal_tb_foot" style="width:100%;">
							<tr>
								<td width="100%" align="left" height="20">叫布费用明细合计</td>
							</tr>
						</table>

						<table  class="pcd_normal_tb" style="width:100%;margin-left:6px;">
							<tr class="pcd_normal_title">
								<td class='pcd_normal_top_td_center' >序号</td>
								<td class='pcd_normal_top_td_center0'>品名色号</td>
								<td class='pcd_normal_top_td_center' >${emkSizePage.sizeA eq null ? '1':emkSizePage.sizeA}</td>
								<td class='pcd_normal_top_td_center' >${emkSizePage.sizeB eq null ? '2':emkSizePage.sizeB}</td>
								<td class='pcd_normal_top_td_center' >${emkSizePage.sizeC eq null ? '3':emkSizePage.sizeC}</td>
								<td class='pcd_normal_top_td_center' >${emkSizePage.sizeD eq null ? '4':emkSizePage.sizeD}</td>
								<td class='pcd_normal_top_td_center' >${emkSizePage.sizeE eq null ? '5':emkSizePage.sizeE}</td>
								<td class='pcd_normal_top_td_center' >${emkSizePage.sizeF eq null ? '6':emkSizePage.sizeF}</td>
								<td class='pcd_normal_top_td_center' >${emkSizePage.sizeG eq null ? '7':emkSizePage.sizeG}</td>
								<td class='pcd_normal_top_td_center' >${emkSizePage.sizeH eq null ? '8':emkSizePage.sizeH}</td>
								<td class='pcd_normal_top_td_center' >${emkSizePage.sizeI eq null ? '9':emkSizePage.sizeI}</td>
								<td class='pcd_normal_top_td_center' >${emkSizePage.sizeJ eq null ? '10':emkSizePage.sizeJ}</td>
								<td class='pcd_normal_top_td_center' >${emkSizePage.sizeK}</td>
								<td class='pcd_normal_top_td_center' >${emkSizePage.sizeL}</td>
								<td class='pcd_normal_top_td_center' >${emkSizePage.sizeM}</td>
								<td class='pcd_normal_top_td_center' >${emkSizePage.sizeN}</td>
								<td class='pcd_normal_top_td_center' >${emkSizePage.sizeO}</td>
								<td class='pcd_normal_top_td_center' >${emkSizePage.sizeP}</td>

								<td class='pcd_normal_top_td_center' >米数</td>
								<td class='pcd_normal_top_td_center' >单价</td>
								<td class='pcd_normal_top_td_center2' >金额</td>
							</tr>
							<c:set var="zjs" value="0"/>
							<c:set var="zj" value="0"/>

							<c:forEach var="list" items="${formatrailEntityList}" varStatus="status">
								<tr  class="pcd_normal_title1">
									<td class='pcd_normal_top_td_center' >${status.index+1}</td>
									<td class='pcd_normal_top_td_center' >${list.color_num}-${list.pm}</td>
									<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_a ne '0.0' ? list.total_a:''}" /></td>
									<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_b ne '0.0' ? list.total_b:''}" /></td>
									<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_c ne '0.0' ? list.total_c:''}" /></td>
									<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_d ne '0.0' ? list.total_d:''}" /></td>
									<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_e ne '0.0' ? list.total_e:''}" /></td>
									<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_f ne '0.0' ? list.total_f:''}" /></td>
									<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_g ne '0.0' ? list.total_g:''}" /></td>
									<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_h ne '0.0' ? list.total_h:''}" /></td>
									<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_i ne '0.0' ? list.total_i:''}" /></td>
									<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_j ne '0.0' ? list.total_j:''}" /></td>
									<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_k ne '0.0' ? list.total_k:''}" /></td>
									<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_l ne '0.0' ? list.total_l:''}" /></td>
									<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_m ne '0.0' ? list.total_m:''}" /></td>
									<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_n ne '0.0' ? list.total_n:''}" /></td>
									<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_o ne '0.0' ? list.total_o:''}" /></td>
									<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.total_p ne '0.0' ? list.total_p:''}" /></td>

									<td class='pcd_normal_top_td_center' >${list.total}</td>
									<td class='pcd_normal_top_td_center' >${list.price}</td>
									<td class='pcd_normal_top_td_center2' >${list.money}</td>
								</tr>
								<c:set var="zjs" value="${zjs+list.total}"/>
								<c:set var="zj" value="${zj+list.total*list.price}"/>
							</c:forEach>

							<tr>
								<td  class='pcd_normal_top_td_center' align="center" colspan="18">总计</td>
								<td class='pcd_normal_top_td_center'>${zjs}</td>
								<td  class='pcd_normal_top_td_center'>&nbsp;</td>
								<td class='pcd_normal_top_td_center'>${zj}</td>
							</tr>
						</table>
					</td>
					<td width="30%" valign="top">
						<table class="pcd_normal_tb_foot" style="width:100%;">
							<tr>
								<td width="100%" align="left" height="20">其他物料明细合计</td>
							</tr>
						</table>

						<table  class="pcd_normal_tb" style="width:100%;margin-left:6px;">
							<tr class="pcd_normal_title">
								<td class='pcd_normal_top_td_center' >序号</td>
								<td class='pcd_normal_top_td_center0'>物料名称</td>
								<td class='pcd_normal_top_td_center' >单价</td>
								<td class='pcd_normal_top_td_center' >数量</td>
								<td class='pcd_normal_top_td_center2' >金额</td>
							</tr>
							<c:set var="zjs" value="0"/>
							<c:set var="zj" value="0"/>

							<c:forEach var="list" items="${otherformatrailEntityList}" varStatus="status">
								<tr  class="pcd_normal_title1">
									<td class='pcd_normal_top_td_center' >${status.index+1}</td>
									<td class='pcd_normal_top_td_center' >${list.wllx}</td>
									<td class='pcd_normal_top_td_center' >${list.price}</td>
									<td class='pcd_normal_top_td_center' >${list.total}</td>
									<td class='pcd_normal_top_td_center2' >${list.money}</td>
								</tr>
								<c:set var="zjs" value="${zjs+list.total}"/>
								<c:set var="zj" value="${zj+list.total*list.price}"/>
							</c:forEach>

							<tr>
								<td  class='pcd_normal_top_td_center' align="center" colspan="4">总计</td>
								<td class='pcd_normal_top_td_center'>${zj}</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>

		</div>
      </div>

	<%--<div id="div_print2" style="width:100%;height:14.85cm;display: none;margin-left:10px;">--%>
		<%--<%int i=1;%>--%>
		<%--<c:forEach var="data" items="${dataList}" varStatus="status">--%>
			<%--<br/>--%>
			<%--<span  style="font: '宋体';font-size: 26px;text-align: center;font-weight: bolder;">${emkPayEntity.payDate}报单</span><br/>--%>
			<%--<div id="page${status.index}" style="page-break-after:always">--%>
				<%--<table class="pcd_normal_tb_foot" style="width:100%;">--%>
					<%--<tr>--%>
						<%--<td width="50%" align="left" height="40">供应商名称：${emkPayEntity.gys }</td>--%>
						<%--<td width="50%" align="right">订单号：${emkPayEntity.orderNo}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>--%>
					<%--</tr>--%>
				<%--</table>--%>
				<%--<div id="pcd_normal_div" style="width:98%;">--%>
					<%--<table id="tb" class="pcd_normal_tb" style="width:100%;">--%>
						<%--<tr class="pcd_normal_title">--%>
							<%--<td class='pcd_normal_top_td_center' >序号</td>--%>
							<%--<td class='pcd_normal_top_td_center0'>款号-颜色-内长</td>--%>
							<%--<td class='pcd_normal_top_td_center' >${emkSizePage.sizeA eq null ? '27':emkSizePage.sizeA}</td>--%>
							<%--<td class='pcd_normal_top_td_center' >${emkSizePage.sizeB eq null ? '28':emkSizePage.sizeB}</td>--%>
							<%--<td class='pcd_normal_top_td_center' >${emkSizePage.sizeC eq null ? '29':emkSizePage.sizeC}</td>--%>
							<%--<td class='pcd_normal_top_td_center' >${emkSizePage.sizeD eq null ? '30':emkSizePage.sizeD}</td>--%>
							<%--<td class='pcd_normal_top_td_center' >${emkSizePage.sizeE eq null ? '31':emkSizePage.sizeE}</td>--%>
							<%--<td class='pcd_normal_top_td_center' >${emkSizePage.sizeF eq null ? '32':emkSizePage.sizeF}</td>--%>
							<%--<td class='pcd_normal_top_td_center' >${emkSizePage.sizeG eq null ? '33':emkSizePage.sizeG}</td>--%>
							<%--<td class='pcd_normal_top_td_center' >${emkSizePage.sizeH eq null ? '34':emkSizePage.sizeH}</td>--%>
							<%--<td class='pcd_normal_top_td_center' >${emkSizePage.sizeI eq null ? '36':emkSizePage.sizeI}</td>--%>
							<%--<td class='pcd_normal_top_td_center' >${emkSizePage.sizeJ eq null ? '38':emkSizePage.sizeJ}</td>--%>
							<%--<td class='pcd_normal_top_td_center' >${emkSizePage.sizeK eq null ? '40':emkSizePage.sizeK}</td>--%>
							<%--<td class='pcd_normal_top_td_center' >${emkSizePage.sizeL eq null ? 'XS':emkSizePage.sizeL}</td>--%>
							<%--<td class='pcd_normal_top_td_center' >${emkSizePage.sizeM eq null ? 'S':emkSizePage.sizeM}</td>--%>
							<%--<td class='pcd_normal_top_td_center' >${emkSizePage.sizeN eq null ? 'M':emkSizePage.sizeN}</td>--%>
							<%--<td class='pcd_normal_top_td_center' >${emkSizePage.sizeO eq null ? 'L':emkSizePage.sizeO}</td>--%>
							<%--<td class='pcd_normal_top_td_center' >${emkSizePage.sizeP eq null ? 'XL':emkSizePage.sizeP}</td>--%>
							<%--<td class='pcd_normal_top_td_center' >${emkSizePage.sizeQ}</td>--%>
							<%--<td class='pcd_normal_top_td_center' >${emkSizePage.sizeR}</td>--%>
							<%--<td class='pcd_normal_top_td_center' >${emkSizePage.sizeS}</td>--%>
							<%--<td class='pcd_normal_top_td_center' >${emkSizePage.sizeT}</td>--%>
							<%--<td class='pcd_normal_top_td_center' >${emkSizePage.sizeU}</td>--%>
							<%--<td class='pcd_normal_top_td_center' >${emkSizePage.sizeV}</td>--%>
							<%--<td class='pcd_normal_top_td_center' >合计</td>--%>
							<%--<td class='pcd_normal_top_td_center' >单价</td>--%>
							<%--<td class='pcd_normal_top_td_center2' >金额</td>--%>

						<%--</tr>--%>

						<%--<c:forEach var="list" items="${data.subdataList}" varStatus="status">--%>
							<%--<tr id="page${status.index}" class="pcd_normal_title1">--%>
								<%--<td class='pcd_normal_top_td_center' >${status.index+1}</td>--%>
								<%--<td class='pcd_normal_top_td_center' >${list.colorValue}-${list.color}-${list.size}</td>--%>
								<%--<td class='pcd_normal_top_td_center' >${list.emkSizeTotalEntity.totalA}</td>--%>
								<%--<td class='pcd_normal_top_td_center' >${list.emkSizeTotalEntity.totalB}</td>--%>
								<%--<td class='pcd_normal_top_td_center' >${list.emkSizeTotalEntity.totalC}</td>--%>
								<%--<td class='pcd_normal_top_td_center' >${list.emkSizeTotalEntity.totalD}</td>--%>
								<%--<td class='pcd_normal_top_td_center' >${list.emkSizeTotalEntity.totalE}</td>--%>
								<%--<td class='pcd_normal_top_td_center' >${list.emkSizeTotalEntity.totalF}</td>--%>
								<%--<td class='pcd_normal_top_td_center' >${list.emkSizeTotalEntity.totalG}</td>--%>
								<%--<td class='pcd_normal_top_td_center' >${list.emkSizeTotalEntity.totalH}</td>--%>
								<%--<td class='pcd_normal_top_td_center' >${list.emkSizeTotalEntity.totalI}</td>--%>
								<%--<td class='pcd_normal_top_td_center' >${list.emkSizeTotalEntity.totalJ}</td>--%>
								<%--<td class='pcd_normal_top_td_center' >${list.emkSizeTotalEntity.totalK}</td>--%>
								<%--<td class='pcd_normal_top_td_center' >${list.emkSizeTotalEntity.totalL}</td>--%>
								<%--<td class='pcd_normal_top_td_center' >${list.emkSizeTotalEntity.totalM}</td>--%>
								<%--<td class='pcd_normal_top_td_center' >${list.emkSizeTotalEntity.totalN}</td>--%>
								<%--<td class='pcd_normal_top_td_center' >${list.emkSizeTotalEntity.totalO}</td>--%>
								<%--<td class='pcd_normal_top_td_center' >${list.emkSizeTotalEntity.totalP}</td>--%>
								<%--<td class='pcd_normal_top_td_center' >${list.emkSizeTotalEntity.totalQ}</td>--%>
								<%--<td class='pcd_normal_top_td_center' >${list.emkSizeTotalEntity.totalR}</td>--%>
								<%--<td class='pcd_normal_top_td_center' >${list.emkSizeTotalEntity.totalS}</td>--%>
								<%--<td class='pcd_normal_top_td_center' >${list.emkSizeTotalEntity.totalT}</td>--%>
								<%--<td class='pcd_normal_top_td_center' >${list.emkSizeTotalEntity.totalU}</td>--%>
								<%--<td class='pcd_normal_top_td_center' >${list.emkSizeTotalEntity.totalV}</td>--%>

								<%--<td class='pcd_normal_top_td_center' >${list.total}</td>--%>
								<%--<td class='pcd_normal_top_td_center' >${list.price}</td>--%>
								<%--<td class='pcd_normal_top_td_center2' >${list.money}</td>--%>
							<%--</tr>--%>
						<%--</c:forEach>--%>
						<%--<tr>--%>
							<%--<td  class='pcd_normal_top_td_center' align="center" colspan="24">总计</td>--%>
							<%--<td class='pcd_normal_top_td_center'>${zjs}</td>--%>
							<%--<td  class='pcd_normal_top_td_center'>&nbsp;</td>--%>
							<%--<td class='pcd_normal_top_td_center2'>${zj}</td>--%>
						<%--</tr>--%>
					<%--</table>--%>
				<%--</div>--%>
			<%--</div>--%>
		<%--</c:forEach>--%>
	<%--</div>--%>

</div>

</body>
</html>


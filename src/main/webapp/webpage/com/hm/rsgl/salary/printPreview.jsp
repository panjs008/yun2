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
	width:170px;
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
	width:80px;
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
		<span  style="font: '宋体';font-size: 26px;text-align: center;font-weight: bolder;">${param.monthS}工资总表</span><br/>
		<table class="pcd_normal_tb_foot" style="width:100%;">
			<tr>
					<td width="55%" align="right" height="40">填报日期：${param.date}</td>
					<td width="45%" align="right">单位：元</td>
				</tr>
		</table>
		<div  style="width:98%;">
			<table  class="pcd_normal_tb" style="width:100%;margin-left:6px;">
				<tr class="pcd_normal_title">
					<td class='pcd_normal_top_td_center' rowspan="3" >序号</td>
					<td class='pcd_normal_top_td_center0' rowspan="3" >部门</td>
					<td class='pcd_normal_top_td_center1' rowspan="3" >车间</td>
					<td class='pcd_normal_top_td_center01' rowspan="3" >班组</td>

					<td class='pcd_normal_top_td_center3' rowspan="3" >姓名</td>
					<td class='pcd_normal_top_td_center' rowspan="2" colspan="2">出勤时间</td>
					<td class='pcd_normal_top_td_center'  colspan="${zSize+7}" >应发金额</td>
					<td class='pcd_normal_top_td_center' colspan="${fSize+4}">应扣金额</td>
					<td class='pcd_normal_top_td_center3' rowspan="3" >实发<br/>工资</td>
				</tr>
				<tr class="pcd_normal_title">
					<td class='pcd_normal_top_td_center' colspan="${zSize+2}" >基本部分</td>
					<td class='pcd_normal_top_td_center'  rowspan="2">合计</td>
					<td class='pcd_normal_top_td_center' colspan="3" >出勤</td>
					<td class='pcd_normal_top_td_center' rowspan="2" >应发<br/>合计</td>
					<td class='pcd_normal_top_td_center'  colspan="${fSize+3}">应扣部分</td>
					<td class='pcd_normal_top_td_center' rowspan="2">应扣<br/>合计</td>
				</tr>
				<tr class="pcd_normal_title">
					<td class='pcd_normal_top_td_center2'  >正常班</td>
					<td class='pcd_normal_top_td_center2'  >加班</td>
					<c:forEach var="list" items="${zcategoryEntities}" varStatus="status">
						<td class='pcd_normal_top_td_center'>${list.name}</td>
					</c:forEach>
					<td class='pcd_normal_top_td_center'  >其他<br/>补贴</td>
					<td class='pcd_normal_top_td_center'  >满勤奖</td>
					<td class='pcd_normal_top_td_center'  >迟到<br/>早退</td>
					<td class='pcd_normal_top_td_center'  >病事假</td>
					<td class='pcd_normal_top_td_center2'  >旷工无打卡</td>
					<c:forEach var="list" items="${fcategoryEntities}" varStatus="status">
						<td class='pcd_normal_top_td_center'>${list.name}</td>
					</c:forEach>
					<td class='pcd_normal_top_td_center'  >劳保</td>
					<td class='pcd_normal_top_td_center'  >奖罚</td>
					<td class='pcd_normal_top_td_center'  >其他扣回</td>
				</tr>
				<c:forEach var="list" items="${salaryEntityList}" varStatus="status">
					<tr id="page${status.index}" class="pcd_normal_title1">
						<td class='pcd_normal_top_td_center' >${status.index+1}</td>
						<td class='pcd_normal_top_td_center' >${list.deptName}</td>
						<td class='pcd_normal_top_td_center' >${list.workName}</td>
						<td class='pcd_normal_top_td_center' >${list.groupName}</td>

						<td class='pcd_normal_top_td_center' >${list.realName}</td>
						<%--<td class='pcd_normal_top_td_center' >${list.zcb eq null ? '':'正常班<br/>'}${list.zcb}<br/>${list.jiab eq null ? '':'加班<br/>'}${list.jiab}</td>--%>
						<td class='pcd_normal_top_td_center' >${(list.zcb ne null && list.zcb ne '0.0') ? list.zcb:''}</td>
						<td class='pcd_normal_top_td_center' ><c:if test="${(list.jiab ne null && list.jiab ne '0.0')}"><fmt:formatNumber type="number" value="${list.jiab/60}" pattern="#.0"/></c:if></td>
						<c:forEach var="category" items="${zcategoryEntities}" varStatus="status">
							<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${(list[category.code] ne null && list[category.code] ne '0.0' && list[category.code] ne '0') ? list[category.code]:''}"/></td>
						</c:forEach>
						<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.otherBt ne null  &&  list.otherBt ne '0'? list.otherBt:''}"/></td>
						<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.fullHourMoney ne null  &&  list.fullHourMoney ne '0'? list.fullHourMoney:''}"/></td>
						<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.baseHj  &&  list.baseHj ne '0' ? list.baseHj:''}"/></td>
						<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.cdzt eq null  &&  list.cdzt ne '0'? list.cdzt:''}"/></td>
						<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.bsj ne null  &&  list.bsj ne '0'? list.bsj:''}"/></td>
						<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.kgwdk ne null  &&  list.kgwdk ne '0'? list.kgwdk:''}"/></td>
						<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.yfHj ne null &&  list.yfHj ne '0.0' ? list.yfHj:''}"/></td>
						<c:forEach var="category" items="${fcategoryEntities}" varStatus="status">
							<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${(list[category.code] ne null && list[category.code] ne '0.0' && list[category.code] ne '0') ? list[category.code]:''}"/></td>
						</c:forEach>
						<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.lb ne null &&  list.lb ne '0' ? list.lb:''}"/></td>
						<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.jf ne null &&  list.jf ne '0'  ? list.jf:''}"/></td>
						<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.qtkh ne null &&  list.qtkh ne '0'  ? list.qtkh:''}"/></td>
						<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.ykHj ne null &&  list.ykHj ne '0.0'  ? list.ykHj:''}"/></td>
						<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.money ne null &&  list.money ne '0.0'  ? list.money:''}"/></td>
					</tr>
					</c:forEach>
		  </table>
		</div>
      </div>

	<div id="div_print2" style="width:100%;height:14.85cm;display: none;margin-left:10px;">
		<%int i=1;%>
		<c:forEach var="data" items="${dataList}" varStatus="status">
			<br/>
			<span  style="font: '宋体';font-size: 26px;text-align: center;font-weight: bolder;">${param.monthS}工资总表</span><br/>
			<div id="page${status.index}" style="page-break-after:always">
				<table class="pcd_normal_tb_foot" style="width:100%;">
					<tr>
						<td width="55%" align="right" height="40">填报日期：${param.date}</td>
						<td width="45%" align="right">单位：元&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>
				</table>
				<div id="pcd_normal_div" style="width:98%;">
					<table id="tb" class="pcd_normal_tb" style="width:100%;">
						<tr class="pcd_normal_title">
							<td class='pcd_normal_top_td_center' rowspan="3" >序号</td>
							<td class='pcd_normal_top_td_center0' rowspan="3" >部门</td>
							<td class='pcd_normal_top_td_center1' rowspan="3" >车间</td>
							<td class='pcd_normal_top_td_center01' rowspan="3" >班组</td>

							<td class='pcd_normal_top_td_center3' rowspan="3" >姓名</td>
							<td class='pcd_normal_top_td_center' rowspan="2" colspan="2">出勤时间</td>
							<td class='pcd_normal_top_td_center'  colspan="${zSize+7}" >应发金额</td>
							<td class='pcd_normal_top_td_center' colspan="${fSize+4}">应扣金额</td>
							<td class='pcd_normal_top_td_center3' rowspan="3" >实发<br/>工资</td>

						</tr>
						<tr class="pcd_normal_title">
							<td class='pcd_normal_top_td_center' colspan="${zSize+2}" >基本部分</td>
							<td class='pcd_normal_top_td_center'  rowspan="2">合计</td>
							<td class='pcd_normal_top_td_center' colspan="3" >出勤</td>
							<td class='pcd_normal_top_td_center' rowspan="2" >应发<br/>合计</td>
							<td class='pcd_normal_top_td_center'  colspan="${fSize+3}">应扣部分</td>
							<td class='pcd_normal_top_td_center' rowspan="2">应扣<br/>合计</td>
						</tr>
						<tr class="pcd_normal_title">
							<td class='pcd_normal_top_td_center2'  >正常班</td>
							<td class='pcd_normal_top_td_center2'  >加班</td>
							<c:forEach var="list" items="${zcategoryEntities}" varStatus="status">
								<td class='pcd_normal_top_td_center'>${list.name}</td>
							</c:forEach>
							<td class='pcd_normal_top_td_center'  >其他<br/>补贴</td>
							<td class='pcd_normal_top_td_center'  >满勤奖</td>
							<td class='pcd_normal_top_td_center'  >迟到<br/>早退</td>
							<td class='pcd_normal_top_td_center'  >病事假</td>
							<td class='pcd_normal_top_td_center2'  >旷工无打卡</td>
							<c:forEach var="list" items="${fcategoryEntities}" varStatus="status">
								<td class='pcd_normal_top_td_center'>${list.name}</td>
							</c:forEach>
							<td class='pcd_normal_top_td_center'  >劳保</td>
							<td class='pcd_normal_top_td_center'  >奖罚</td>
							<td class='pcd_normal_top_td_center'  >其他扣回</td>
						</tr>
						<c:forEach var="list" items="${data.subdataList}" varStatus="status">
							<tr  class="pcd_normal_title1">
								<td class='pcd_normal_top_td_center' ><%=i%></td>
								<td class='pcd_normal_top_td_center' >${list.deptName}</td>
								<td class='pcd_normal_top_td_center' >${list.workName}</td>
								<td class='pcd_normal_top_td_center' >${list.groupName}</td>

								<td class='pcd_normal_top_td_center' >${list.realName}</td>
								<td class='pcd_normal_top_td_center' >${(list.zcb ne null && list.zcb ne '0.0') ? list.zcb:''}</td>
								<td class='pcd_normal_top_td_center' ><c:if test="${(list.jiab ne null && list.jiab ne '0.0')}"><fmt:formatNumber type="number" value="${list.jiab/60}" pattern="#.0"/></c:if></td>
								<c:forEach var="category" items="${zcategoryEntities}" varStatus="status">
									<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${(list[category.code] ne null && list[category.code] ne '0.0' && list[category.code] ne '0') ? list[category.code]:''}"/></td>
								</c:forEach>
								<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.otherBt ne null  &&  list.otherBt ne '0'? list.otherBt:''}"/></td>
								<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.fullHourMoney ne null  &&  list.fullHourMoney ne '0'? list.fullHourMoney:''}"/></td>
								<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.baseHj  &&  list.baseHj ne '0' ? list.baseHj:''}"/></td>
								<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.cdzt eq null  &&  list.cdzt ne '0'? list.cdzt:''}"/></td>
								<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.bsj ne null  &&  list.bsj ne '0'? list.bsj:''}"/></td>
								<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.kgwdk ne null  &&  list.kgwdk ne '0'? list.kgwdk:''}"/></td>
								<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.yfHj ne null &&  list.yfHj ne '0.0' ? list.yfHj:''}"/></td>
								<c:forEach var="category" items="${fcategoryEntities}" varStatus="status">
									<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${(list[category.code] ne null && list[category.code] ne '0.0' && list[category.code] ne '0') ? list[category.code]:''}"/></td>
								</c:forEach>
								<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.lb ne null &&  list.lb ne '0' ? list.lb:''}"/></td>
								<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.jf ne null &&  list.jf ne '0'  ? list.jf:''}"/></td>
								<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.qtkh ne null &&  list.qtkh ne '0'  ? list.qtkh:''}"/></td>
								<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.ykHj ne null &&  list.ykHj ne '0.0'  ? list.ykHj:''}"/></td>
								<td class='pcd_normal_top_td_center' ><fmt:parseNumber integerOnly="true" value="${list.money ne null &&  list.money ne '0.0'  ? list.money:''}"/></td>
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


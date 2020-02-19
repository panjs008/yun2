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
	width:80px;
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
	width:30px;
	line-height:22px;
	padding:0px 0px 0px 0px;
	word-wrap:break-word;
	word-break:break-all;
	text-align: center;
}

.pcd_normal_top_td_center1{
	border:1px solid #000000;
	width:70px;
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
		<%int i=1;%>
		<div  style="width:98%;">
			<%i=1;%>
			<c:forEach var="data" items="${dataList2}" varStatus="status0">
				<br/>
				<span  style="font: '宋体';font-size: 26px;text-align: center;font-weight: bolder;">${param.monthS}考勤工时核对条</span><br/>
				<div >
					<table class="pcd_normal_tb_foot" style="width:100%;">
						<tr>
							<td width="30%" align="left" height="40">部门：${data.subdataList ne null ? data.subdataList[0].deptName:''}</td>
							<td width="30%" align="right" height="40">填报日期：${param.date}</td>
							<td width="50%" align="right">单位：元</td>
						</tr>
					</table>
					<div i style="width:98%;">
						<table  class="pcd_normal_tb" style="width:100%;">
							<tr class="pcd_normal_title">
								<td class='pcd_normal_top_td_center01'  >序号</td>
								<%--<td class='pcd_normal_top_td_center0' >部门</td>--%>
								<td class='pcd_normal_top_td_center1'  >车间</td>
								<td class='pcd_normal_top_td_center1'  >班组</td>
								<td class='pcd_normal_top_td_center1'  >姓名</td>
								<td class='pcd_normal_top_td_center'  >出勤</td>
								<td class='pcd_normal_top_td_center3'  >加班</td>
								<c:forEach var="list" items="${cols}" varStatus="status">
									<td class='pcd_normal_top_td_center'  >${list.name}</td>
								</c:forEach>
							</tr>

							<c:forEach var="list" items="${data.subdataList}" varStatus="status">
								<tr  class="pcd_normal_title1">
									<c:if test="${status.index eq 0 || (status.index > 4 && (status.index+1) % 6 eq 1)}" >
										<td class='pcd_normal_top_td_center' rowspan="6"><%=i%><%i++;%></td>
										<%--<td class='pcd_normal_top_td_center' rowspan="6">${list.deptName}</td>--%>
										<td class='pcd_normal_top_td_center' rowspan="6">${list.workName}</td>
										<td class='pcd_normal_top_td_center' rowspan="6">${list.groupName}</td>
										<td class='pcd_normal_top_td_center' rowspan="6">${list.realName}</td>
									</c:if>
									<c:if test="${status.index eq 0 || (status.index > 2 && (status.index+1) % 3 eq 1)}" >
										<td class='pcd_normal_top_td_center' rowspan="3">${list.cqsjType}</td>
									</c:if>
									<td class='pcd_normal_top_td_center' ><c:if test="${list.cqsj eq 0 || list.cqsj eq 3}">正班</c:if><c:if test="${list.cqsj eq 1 || list.cqsj eq 4}">加班</c:if><c:if test="${list.cqsj eq 2 || list.cqsj eq 5}">金额</c:if></td>
									<c:forEach var="category" items="${cols}" varStatus="status">
										<td class='pcd_normal_top_td_center' >${(list[category.code] ne null && list[category.code] ne '0.0' && list[category.code] ne '0') ? list[category.code]:''}</td>

									</c:forEach>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
			</c:forEach>
		</div>
      </div>

	<div id="div_print2" style="width:100%;height:14.85cm;display: none;margin-left:10px;">
		<%i=1;int z=0;%>
		<c:forEach var="data" items="${dataList2}" varStatus="status0">
			<br/>
			<span  style="font: '宋体';font-size: 26px;text-align: center;font-weight: bolder;">${param.monthS}考勤工时核对条</span><br/>
			<div id="page${status0.index}" <%if(z==2){%>style="page-break-after:always"<% z=0;}else{z++;}%>>
				<table class="pcd_normal_tb_foot" style="width:100%;">
					<tr>
						<td width="30%" align="left" height="40">部门：${data.subdataList ne null ? data.subdataList[0].deptName:''}</td>
						<td width="30%" align="right" height="40">填报日期：${param.date}</td>
						<td width="50%" align="right">单位：元&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>
				</table>
				<div id="pcd_normal_div" style="width:98%;">
					<table id="tb" class="pcd_normal_tb" style="width:100%;">
						<tr class="pcd_normal_title">
							<td class='pcd_normal_top_td_center01'  >序号</td>
							<%--<td class='pcd_normal_top_td_center0' >部门</td>--%>
							<td class='pcd_normal_top_td_center1'  >车间</td>
							<td class='pcd_normal_top_td_center1'  >班组</td>
							<td class='pcd_normal_top_td_center1'  >姓名</td>
							<td class='pcd_normal_top_td_center'  >出勤</td>
							<td class='pcd_normal_top_td_center3'  >加班</td>
							<c:forEach var="list" items="${cols}" varStatus="status">
								<td class='pcd_normal_top_td_center'  >${list.name}</td>
							</c:forEach>
						</tr>

						<c:forEach var="list" items="${data.subdataList}" varStatus="status">
							<tr  class="pcd_normal_title1">
								<c:if test="${status.index eq 0 || (status.index > 4 && (status.index+1) % 6 eq 1)}" >
									<td class='pcd_normal_top_td_center' rowspan="6"><%=i%><%i++;%></td>
									<%--<td class='pcd_normal_top_td_center' rowspan="6">${list.deptName}</td>--%>
									<td class='pcd_normal_top_td_center' rowspan="6">${list.workName}</td>
									<td class='pcd_normal_top_td_center' rowspan="6">${list.groupName}</td>
									<td class='pcd_normal_top_td_center' rowspan="6">${list.realName}</td>
								</c:if>
								<c:if test="${status.index eq 0 || (status.index > 2 && (status.index+1) % 3 eq 1)}" >
									<td class='pcd_normal_top_td_center' rowspan="3">${list.cqsjType}</td>
								</c:if>
								<td class='pcd_normal_top_td_center' ><c:if test="${list.cqsj eq 0 || list.cqsj eq 3}">正班</c:if><c:if test="${list.cqsj eq 1 || list.cqsj eq 4}">加班</c:if><c:if test="${list.cqsj eq 2 || list.cqsj eq 5}">金额</c:if></td>
								<c:forEach var="category" items="${cols}" varStatus="status">
									<td class='pcd_normal_top_td_center' >${(list[category.code] ne null && list[category.code] ne '0.0' && list[category.code] ne '0') ? list[category.code]:''}</td>

									<%--<td class='pcd_normal_top_td_center' ><c:if test="${list.cqsj ne 2 && list.cqsj ne 5}">${(list[category.code] ne null && list[category.code] ne '0.0' && list[category.code] ne '0') ? list[category.code]:''}</c:if><c:if test="${list.cqsj eq 2 || list.cqsj eq 5}"><fmt:parseNumber integerOnly="true" value="${(list[category.code] ne null && list[category.code] ne '0.0' && list[category.code] ne '0') ? list[category.code]:''}"/></c:if></td>--%>
								</c:forEach>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</c:forEach>
	</div>

</div>

</body>
</html>


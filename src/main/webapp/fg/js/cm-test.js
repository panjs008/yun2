/*tab*/
$(function(){ 
	tabs(".tabs","on",".tabc");
	tab(".tab_dia","cur",".tab_dia_c");
	$(".menu-one li").hover(
		function(){
			$(this).addClass("cur");
		},function(){
			$(this).removeClass("cur");
		}
	)
	
	//popWin("mainDiabox");

		
	$('#loginOut').on('click', function(){
		layer.config({
			skin: 'layui-layer-lan',
		})
		layer.alert('见到你真的很高兴');
	})
	
})


function tabs(tabMenu,on,tabContent){          
	$(tabContent).each(function(){ 
		$(this).children().eq(0).show();              
	});       
	$(tabMenu).each(function(){ 
		$(this).children().eq(0).addClass(on);
	});          
	$(tabMenu).children().click(function(){
		$(this).addClass(on).siblings().removeClass(on); 
		var index = $(tabMenu).children().index(this);
		$(tabContent).children().eq(index).show().siblings().hide();
	});
} 
function tab(tabMenu,on){                   
	$(tabMenu).children().click(function(){
		$(this).addClass(on).siblings().removeClass(on); 
	});
} 


	
function menuFun(action, permission) {
	var _url = "http://localhost:8090/jingtai/dialog/" + action;
	$("#mainDiabox").empty();
	//var op = $('#omap').openlayerPlugin();
	//op.restoreInitial();
	$("#mainDiabox").load(_url, function () {
		$('.main_dialog').removeAttr("style");
		popWin("mainDiabox");
		$("select.select").select();
		$(document).on('click','#m_dialog_close',function(){
        	$('#mainDiabox').empty();
		});
		$(document).on('click','.label_check',function(){
			if($('.para_box input').length) {
				$('.label_check').each(function(){
					$(this).removeClass('chr_on');
				});
				$('.label_check input:checked').each(function(){
					$(this).parent('label').addClass('chr_on');
				});
			}
		});
		$('#table-java-base').bootstrapTable({
			cache : false,
			striped : true,
			pagination : true,
			clickToSelect:true,
			pageNumber : 1,// 分页的时候设置当前的页码。
			pageSize: 10,
			pageList: [10],
			url: "data1.json"
		})
		/*$('#table-java-operta').bootstrapTable({
			cache : false,
			striped : true,
			pagination : true,
			pageNumber : 1,// 分页的时候设置当前的页码。
			pageSize: 10,
			pageList: [10],
			url: "data1.json"
		})*/
		oftop()
		layerinit();
		
	})
}
/*window.onload =window.onresize= function(){winresize();}
function winresize()
{
	D=document.documentElement||document.body;
	dv=$('#mainDiabox').height();
	h=D.clientHeight-dv-152;
	if(D.clientHeight >= 600 && D.clientHeight <= 800){
		$(".layer-rt .layui-layer-content").css({height:h+"px"});
	}
}*/
 function oftop() {
	dv=$('#mainDiabox').height()
	h=dv+106
	return h
}
function ofleft(){
	D=document.documentElement||document.body;
	w=D.clientWidth-300;
	$('.layer-rt').css("left",w)
}

function layerinit(){
	$('#para_c').on('click', function(){
		layer.closeAll('');
		layer.open({
				title: '选择常用参数',
				type: 1,
				shade: false, //点击遮罩关闭
				content: $('#commonParam')
		})
	})
	$('#query_r').on('click', function(){
		$('#table-java').bootstrapTable({
			cache : false,
			striped : true,
			pagination : true,
			pageNumber : 1,// 分页的时候设置当前的页码。
			pageSize: 8,
			pageList: [8],
			url: "data2.json"
		})
		layer.closeAll('');
		layer.config({
			skin: 'layer-rt',
		})
		layer.open({
				title: '查询结果',
				type: 1,
				shade: false, //点击遮罩关闭
				content: $('#queryresult'),
				offset: 'ra',
		});
	})
	$('#total_r').on('click', function(){
		$('#table-java02').bootstrapTable({
			cache : false,
			striped : true,
			pagination : true,
			pageNumber : 1,// 分页的时候设置当前的页码。
			pageSize: 6,
			pageList: [6],
			pagination: false,
			url: "data3.json"
		})
		layer.closeAll('');
		layer.open({
				title: '统计结果',
				type: 1,
				shade: false, //点击遮罩关闭
				content: $('#total_result')
		});
	})
	$('#pu_add').on('click', function(){
		layer.closeAll('');
		layer.open({
				title: '增加或修改',
				type: 1,
				shade: false, //点击遮罩关闭
				content: $('#pu_add_box')
		});
	})
	$('#pj_add').on('click', function(){
		layer.closeAll('');
		layer.open({
				title: '增加或修改',
				type: 1,
				shade: false, //点击遮罩关闭
				content: $('#project_box')
		});
	})
	$('#issue').on('click', function(){
		layer.closeAll('');
		layer.open({
				title: '下发',
				type: 1,
				shade: false, //点击遮罩关闭
				content: $('#issue_box')
		});
	})
	task = function(obj){
		layer.closeAll('');
		layer.open({
				title: '任务内容',
				type: 1,
				shade: false, //点击遮罩关闭
				content: $('#task_box')
		})
	}
	inpcheck = function(obj){
		layer.closeAll('')
		layer.open({
			title: '现场情况',
			type: 1,
			shade: false, //点击遮罩关闭
			content: $('#slide_box')
		})	
		var swiper = new Swiper('.swiper-container', {
			pagination: '.swiper-pagination',
			nextButton: '.swiper-button-next',
			prevButton: '.swiper-button-prev',
			slidesPerView: 1,
			paginationClickable: true,
			spaceBetween: 30,
			loop: true
		});
	}
	inptransfer = function(obj){
		layer.closeAll('')
		layer.confirm('确认移交报告吗？', {
			title: '移交',
		  	shade: false //点击遮罩关闭
		})
	}
	review = function(obj){
		layer.closeAll('')
		layer.open({
			title: '复查反馈详情',
			type: 1,
			shade: false, //点击遮罩关闭
			content: $('#slide_box')
		})	
		var swiper = new Swiper('.swiper-container', {
			pagination: '.swiper-pagination',
			nextButton: '.swiper-button-next',
			prevButton: '.swiper-button-prev',
			slidesPerView: 1,
			paginationClickable: true,
			spaceBetween: 30,
			loop: true
		});
	}
}

function checkFormat(value,row,index){
    return '<a href="javascript:;" onclick="inpcheck(\''+row.id+'\')">查看</a>';
}
function tranFormat(value,row,index){
    return '<a href="javascript:;" onclick="inptransfer(\''+row.id+'\')">移交</a>';
}
function reviewFormat(value,row,index){
    return '<a href="javascript:;" onclick="review(\''+row.id+'\')">不合格</a>';
}
function taskFormat(value,row,index){
    return '<a href="javascript:;" onclick="task(\''+row.id+'\')">查看</a>';
}

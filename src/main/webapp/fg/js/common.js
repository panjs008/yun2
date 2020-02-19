/*tab*/
$(function(){
	tabs(".tabs","on",".tabc");
	tabs(".u-pop-tabs","on",".u-pop-tabs-content");
    tabi(".tabm","on",".tabr");
	tab(".tab_dia","cur",".tab_dia_c");
    singleSelect('.singleClick','active');
    multipleSelect('.multipleSelect','active')
});
function tabi(tabMenu,on,tabContent){

    $(tabContent).each(function(){
        $(this).children().eq(0).show()
    });
    $(tabMenu).each(function(){
        $(this).children().eq(0).addClass(on)
    });
    $(tabMenu).children('.tabmenu').click(function(){
        var txt=$(this).children('p').html();
        $(this).addClass(on).siblings().removeClass(on);
        var index = $(tabMenu).children('.tabmenu').index(this);
        $(tabContent).children('.fix').eq(index).show().siblings().hide();
    })
}


function tabs(tabMenu,on,tabContent){

	$(tabContent).each(function(){
		$(this).children().eq(0).show()
	});
	$(tabMenu).each(function(){
		$(this).children().eq(0).addClass(on)
	});
	$(tabMenu).children().click(function(){
		$(this).addClass(on).siblings().removeClass(on);
		var index = $(tabMenu).children().index(this);
		$(tabContent).children().eq(index).show().siblings().hide();
        $(tabContent).children().mCustomScrollbar('destroy');
        $(tabContent).children().mCustomScrollbar({
            scrollInertia:150
        });
	});
}

function tab(tabMenu,on,tabContent){
	$(tabContent).each(function(){
		$(this).children().eq(0).show()
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


window.onload =window.onresize= function(){winresize();}
function winresize()
{
	D=document.documentElement||document.body;
	h=D.clientHeight-72;
	/*table的高度多减82*/
	h2=D.clientHeight-312;
	h3=D.clientHeight-98;
	h4=D.clientHeight-208;
	$("#main_box").css({height:h+"px"});
    $(".table-side-bzfx .fixed-table-container").css({height:h2+"px"});
    $(".table-side-sbyc .fixed-table-container").css({height:h2+"px"});
    // $(".table-side-base .fixed-table-body").css({height:h3+"px"});
    // $(".table-side-base2 .fixed-table-container").css({height:h4+"px"});
}

	/**
	 * 下载文件
	 * @param url
	 * @param data
	 * @param method
	 */
function downloadFile(url, data, method) {
	var inputs;
	if (url && data) {
		for (name in data) {
			inputs += '<input type="hidden" name="' + name + '" value="' + data[name] + '" />';
		}
		jQuery('<form action="' + url + '" method="' + (method || 'post') + '">' + inputs + '</form>')
			.appendTo('body').submit().remove();
	}
}

function mainBox(action){
	var _url = "http://localhost:8090/html1/page/" + action;
	$("#mapAll").hide();
    $("#main_box2").hide();
    $("#main_box").show().css('background','#fff');;
	$("#main_box .iframe").attr('src',_url);
}

function mainBox1(action){
    var _url = "http://localhost:8090/html1/page/" + action;
    $("#mapAll").hide();
    $("#main_box2").hide();
    $("#main_box").show().css('background','#0e1e2d');
    $("#main_box .iframe").attr('src',_url);
}

function mainBox2(action){
    var _url = "http://localhost:8090/html1/page/" + action;
    $("#mapAll").hide();
    $("#main_box").hide();
    $("#main_box2").show().css('background','#0e1e2d');
    $("#main_box2 .iframe").attr('src',_url);
}

function menuFun(action, permission) {
    var _url = "http://localhost:8090/html1/dialog/" + action;
    $("#mainDiabox").empty();
    $("#mainDiabox").load(_url, function () {
        $('.main_dialog').removeAttr("style");
        popWin("mainDiabox");
        $("select.select").select();
        $(document).on('click','#m_dialog_close',function(){
            $('#mainDiabox').empty();
        });
        oftop('#mainDiabox');
        $('.star-end a').on('click',function(){
            $(this).addClass('btn_cur').siblings().removeClass('btn_cur');
        })
    })
}
function showMap() {
    $("#main_box").hide();
    $("#mapAll").show();

}
function oftop(id)
{
	dv=$(id).height()
	h=dv+106
	return h
}
function ofleft(){
	D=document.documentElement||document.body;
	w=D.clientWidth-300;
	$('.layer-rt').css("left",w)
}




function  layOpen(pop,title) {
	layer.open({
		skin:"layui-layer-sys",
		type: 1,
		title:title,
		shade:false,
		content: $(pop)
	})
}
function  layOpenT(pop,title,W) {
	layer.open({
		skin:"layui-layer-table",
		type: 1,
		area:W,
		title:title,
		shade:false,
		content: $(pop)
	})
}



function  layOpenW(pop,title,W) {
	layer.open({
		skin:"layui-layer-sys layui-layer-sys1",
		type: 1,
		area:W,
		title:title,
		shade:false,
		content: $(pop)
	})
}
function  layOpenW2(pop,title,W) {
    layer.open({
        skin:"layui-layer-sys layui-layer-sys2",
        type: 1,
        area:W,
        title:title,
        shade:false,
		offset:'auto',
        content: $(pop)
    })
}
function  layOpenW3(pop,title,W) {
    layer.open({
        skin:"layui-layer-sys layui-layer-sys1 layui-layer-shadow",
        type: 1,
        area:W,
        title:title,
        shade:false,
        content: $(pop)
    })
}

function  layDel() {
	layer.config({
		skin:"layui-layer-sys"
	})
	layer.confirm('确定要删除选中数据？', function(index){
		//do something
		layer.close(index);
	});
}

function uTab(tabMenu,on,tabContent){
    $(tabMenu).children().click(function(){
        $(this).addClass(on).siblings().removeClass(on);
        var index = $(this).index();
        $(this).parent().siblings(tabContent).children().eq(index).show().addClass(on).siblings().hide();
    });
}

function specifyTab(tabMenu,on,tabContent,specify){
    $(tabMenu).children(specify).click(function(){
        $(this).addClass(on).siblings().removeClass(on);
        var index = $(this).index();
        $(this).parent().siblings(tabContent).children().eq(index).show().siblings().hide();
    });
}

function hideuTab(that) {
	$(that).parent().parent().parent().hide();
    var index = $(that).parent().parent().parent().index();
    $(that).parent().parent().parent().parent().siblings('.u-drop').children().removeClass('active');
}
function  hidePPP(that) {
	$(that).parent().parent().parent().hide()
}
function  hideuTab1(that) {
    $(that).parent().hide();
    var index = $(that).parent().parent().parent().index();
    $(that).parent().parent().siblings('.u-drop').children().removeClass('active');
}
function uLayTab(tabMenu,on,tabContent) {
    $(tabMenu).children().click(function(){
        $(this).addClass(on).siblings().removeClass(on);
        var index = $(this).index();
        $(this).parent().siblings(tabContent).children().eq(index).addClass(on).siblings().removeClass(on);
    });
}
/*单选*/
function singleSelect(parent,on){
	$(parent).children().on('click',function () {
		$(this).addClass(on).siblings().removeClass(on);
    })
}
/*多选*/
function multipleSelect(parent,on){
    $(parent).children().on('click',function () {
    	var show=$(this).hasClass(on);
    	if(show){
            $(this).removeClass(on);
		}else{
            $(this).addClass(on);
		}
    })
}
/*进度条*/
function ProgressBar(){
    var percents=document.querySelectorAll('[data-percent]');
    for(var i=0;i<percents.length;i++){
        var W=percents[i].getAttribute('data-percent');
        percents[i].style.width=W+'%';
    }
}
/*抽屉*/
function Drawer(ck,on) {
    $(ck).on('click',function () {
        var show=$(this).parent().hasClass(on);
        if(show){
            $(this).parent().removeClass(on);
        }else{
            $(this).parent().addClass(on);
        }
        $(this).parent().siblings().removeClass(on);
    })
}

$('.u-clicked').on('click',function () {
    var show=$(this).hasClass('active');
    if(show){
        $(this).removeClass('active');
    }else{
        $(this).addClass('active');
    }
})

/*阻止鼠标滚轮冒泡*/
$.fn.extend({
    "preventScroll":function(){
        $(this).each(function(){
            var _this = this;
            if(navigator.userAgent.indexOf('Firefox') >= 0){   //firefox
                _this.addEventListener('DOMMouseScroll',function(e){
                    _this.scrollTop += e.detail > 0 ? 60 : -60;
                    e.preventDefault();
                },false);
            }else{
                _this.onmousewheel = function(e){
                    e = e || window.event;
                    _this.scrollTop += e.wheelDelta > 0 ? -60 : 60;
                    return false;
                };
            }
        })
    }
});



function hideP(that) {
    $(that).parent().hide();
}


/*textarea*/
function makeExpandingArea(el,ex,eh,es) {
    var timer = null;
    //由于ie8有溢出堆栈问题，故调整了这里
    var setStyle = function(el, auto) {
        if (auto) el.style.height = 'auto';
        el.style.height = el.scrollHeight + 'px';
        var height=$(ex).height()+18;
        var maxH=height + 48;
        $(eh).css({top:maxH+"px"});
        $(es).mCustomScrollbar('destroy');
        $(es).mCustomScrollbar({
            scrollInertia:150
        });
        var h=$('.xj-prospectus-date').height()+20;
        var h1=$('.xj-prospectus-date-plan').height()+17;
        h3=h-101- h1;
        $('.table-side-xj .fixed-table-container').css({height:h3+'px'});
    }
    var delayedResize = function(el) {
        if (timer) {
            clearTimeout(timer);
            timer = null;
        }
        timer = setTimeout(function() {
            setStyle(el)
        }, 200);
    }
    if (el.addEventListener) {
        el.addEventListener('input', function() {
            setStyle(el, 1);
        }, false);
        setStyle(el)
    } else if (el.attachEvent) {
        el.attachEvent('onpropertychange', function() {
            setStyle(el)
        })
        setStyle(el)
    }
    if (window.VBArray && window.addEventListener) { //IE9
        el.attachEvent("onkeydown", function() {
            var key = window.event.keyCode;
            if (key == 8 || key == 46) delayedResize(el);

        });
        el.attachEvent("oncut", function() {
            delayedResize(el);
        }); //处理粘贴
    }



    
}

function makeExpandingArea1(el,es) {
    var timer = null;
    //由于ie8有溢出堆栈问题，故调整了这里
    var setStyle = function(el, auto) {
        if (auto) el.style.height = 'auto';
        el.style.height = el.scrollHeight + 'px';
        $(es).mCustomScrollbar('destroy');
        $(es).mCustomScrollbar({
            scrollInertia:150
        });
    }
    var delayedResize = function(el) {
        if (timer) {
            clearTimeout(timer);
            timer = null;
        }
        timer = setTimeout(function() {
            setStyle(el)
        }, 200);
    }
    if (el.addEventListener) {
        el.addEventListener('input', function() {
            setStyle(el, 1);
        }, false);
        setStyle(el)
    } else if (el.attachEvent) {
        el.attachEvent('onpropertychange', function() {
            setStyle(el)
        })
        setStyle(el)
    }
    if (window.VBArray && window.addEventListener) { //IE9
        el.attachEvent("onkeydown", function() {
            var key = window.event.keyCode;
            if (key == 8 || key == 46) delayedResize(el);

        });
        el.attachEvent("oncut", function() {
            delayedResize(el);
        }); //处理粘贴
    }
}

function makeExpandingArea2(el) {
    var timer = null;
    //由于ie8有溢出堆栈问题，故调整了这里
    var setStyle = function(el, auto) {
        if (auto) el.style.height = 'auto';
        el.style.height = el.scrollHeight + 'px';
        var h=$('.xj-prospectus-date').height()+20;
        var h1=$('.xj-prospectus-date-plan').height()+17;
        h3=h-101- h1;
        $('.table-side-xj .fixed-table-container').css({height:h3+'px'})
    }
    var delayedResize = function(el) {
        if (timer) {
            clearTimeout(timer);
            timer = null;
        }
        timer = setTimeout(function() {
            setStyle(el)
        }, 200);
    }
    if (el.addEventListener) {
        el.addEventListener('input', function() {
            setStyle(el, 1);
        }, false);
        setStyle(el)
    } else if (el.attachEvent) {
        el.attachEvent('onpropertychange', function() {
            setStyle(el)
        })
        setStyle(el)
    }
    if (window.VBArray && window.addEventListener) { //IE9
        el.attachEvent("onkeydown", function() {
            var key = window.event.keyCode;
            if (key == 8 || key == 46) delayedResize(el);

        });
        el.attachEvent("oncut", function() {
            delayedResize(el);
        }); //处理粘贴
    }
}























$('.s-clicked').children().on('click',function () {
    $(this).addClass('active');
    $(this).siblings().removeClass('active');
})
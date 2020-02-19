/*gis 图层管理*/
$('.tcgl-show .title').on('click',function () {
    var close=$(this).parent().hasClass('tcgl-clsoe');
    if(close){
        $(this).parent().removeClass('tcgl-clsoe')
    }
})

$('.tcgl-show .close').on('click',function () {
    var open=$(this).parent().hasClass('tcgl-clsoe');
    if(!open){
        $(this).parent().addClass('tcgl-clsoe')
    }
})


/*系统菜单*/
$('#hd_menu').hover(function(){
        $(this).addClass('on');
        $('#menu-one').show();
    },
    function(){
        $(this).removeClass('on');
        $('#menu-one').hide();
    })
$('#menu-one').on('click','ul li.tabmenu',function(){
    var text=$(this).children('p').html();
    $('.head-menu-logo').html(text);
})
$('.menu-tre-ul li').on('click',function(){
    $(this).addClass('on').siblings('.on').removeClass('on');
})


function showTable(id) {
    $(id).show();
    $(".scada_table_exhibition_body").mCustomScrollbar({
        scrollInertia:150
    });
}
function hideTable(id) {
    $(".scada_table_exhibition_body").mCustomScrollbar("destroy");
    $(id).hide();

}



function hideWarn(that) {
    $(that).parent().parent().parent().removeClass('scada-warn-show')
}
function showWarn(that) {
    $(that).addClass('scada-warn-show');
}
$(document).ready(function(){
    BindSelectCustom("cusId","ymkCustomController.do?findCustomListForSelect",1,$("#a01a07a02").val()+","+$("#cusName").val()+","+$("#bcqkMoney").val());

    $("#cusId").change(function(){
        var itemarr = $("#cusId").val().split(","); //字符分割
        $("#a01a07a02").val(itemarr[0]);
        $("#cusName").val(itemarr[1]);
        $("#qcqkMoney").val(itemarr[2]);

        $(".select2").on('select2:close',function() {
            var el = $(this);
            if(el.val()==="NEW") {
                var newval = prompt("客户名称: ");
                //var newval = $.dialog.prompt('客户名称：');
                if(newval !== null) {
                    var v = newval+","+newval;
                    el.append('<option value='+v+'>'+newval+'</option>')
                        .val(newval);
                    $("#cusId").select2('val',v);
                }
            }
        });
        /*if($("#orderNoV").val()!= null){
            BindSelectOrder("orderNo","emkFormaterialController.do?findOrderList&gysCode="+$("#gysCode").val(),1,$("#orderNoV").val());
        }*/
    });
});
function BindSelectCustom(ctrlName, url,type,categoryId) {
    var control = $('#' + ctrlName);
    //设置Select2的处理
    control.select2({
        formatResult: formatState,
        formatSelection: formatState,
        escapeMarkup: function (m) {
            return m;
        }
    });
    //绑定Ajax的内容
    $.getJSON(url, function (data) {
        control.empty();//清空下拉框
        control.append("<option value=''>请选择</option>");
        control.append("<option value='NEW'>新增客户</option>");

        $.each(data.obj, function (i, item) {
            if($("#a01a07a02").val() == item.cusNum){
                control.append("<option value='" + item.cusNum + ","+item.cusName +","+$("#bcqkMoney").val()+"'>" + item.cusName + "</option>");
            }else{
                control.append("<option value='" + item.cusNum + ","+item.cusName +","+item.bcqkMoney+"'>" + item.cusName + "</option>");
            }
            //control.append("<option value='" + item.cusNum + ","+item.cusName+"'>" + item.cusName + "</option>");
        });
        if(type ==1 && categoryId !='' && categoryId != null && categoryId!= 'undefined,undefined'){
            $("#"+ctrlName).select2('val',categoryId);
        }
    });
}
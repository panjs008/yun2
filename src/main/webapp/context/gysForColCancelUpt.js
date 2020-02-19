$(document).ready(function(){
    BindSelectSupply("gysId","ymkCustomController.do?findSupplierList",1,$("#a01a16a06").val()+","+$("#gys").val()+","+$("#bcqkMoney").val());

    $("#gysId").change(function(){
        var itemarr = $("#gysId").val().split(","); //字符分割
        $("#a01a16a06").val(itemarr[0]);
        $("#gys").val(itemarr[1]);
    
    });
});
function BindSelectSupply(ctrlName, url,type,categoryId) {
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

        $.each(data.obj, function (i, item) {
            if($("#a01a16a06").val() == item.companyCode){
                control.append("<option value='" + item.companyCode + ","+item.companyNameZn +","+$("#bcqkMoney").val()+"'>" + item.companyNameZn + "</option>");
            }else{
                control.append("<option value='" + item.companyCode + ","+item.companyNameZn +","+item.bcqkMoney+"'>" + item.companyNameZn + "</option>");
            }
        });
        if(type ==1 && categoryId !='' && categoryId != null && categoryId!= 'undefined,undefined'){
            $("#"+ctrlName).select2('val',categoryId);
        }
    });
}
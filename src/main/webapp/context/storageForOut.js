$(document).ready(function(){
    BindStorageSupply("outstorageNameId","emkStorageSetController.do?findStorageList",1,$("#outStorageId").val()+","+$("#outStorageName").val());

    $("#outstorageNameId").change(function(){
        var itemarr = $("#outstorageNameId").val().split(","); //字符分割
        $("#outStorageId").val(itemarr[0]);
        $("#outStorageName").val(itemarr[1]);
    });
});



function BindStorageSupply(ctrlName, url,type,categoryId) {

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
            control.append("<option value='" + item.id + "," + item.storageName + "'>" + item.storageName + "</option>");
        });
        if(type ==1 && categoryId !='' && categoryId != null && categoryId!= 'undefined'){
            $("#"+ctrlName).select2('val',categoryId);
        }
    });
}
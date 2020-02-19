$(document).ready(function(){
    BindStorageSupply("storageNameId","emkStorageSetController.do?findStorageList",1,$("#storageId").val()+","+$("#storageName").val());

    $("#storageNameId").change(function(){
        var itemarr = $("#storageNameId").val().split(","); //字符分割
        $("#storageId").val(itemarr[0]);
        $("#storageName").val(itemarr[1]);

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
function getBirthdayFromIdCard() {
    var birthday = "";
    var idCard = $("#idCard").val();
    if(idCard!= null && idCard != ""){
        if(idCard.length == 15){
            birthday = "19"+idCard.substr(6,6);
        } else if(idCard.length == 18){
            birthday = idCard.substr(6,8);
        }
        birthday = birthday.replace(/(.{4})(.{2})/,"$1-$2-");
        $("#birthDay").val(birthday);
    }
}
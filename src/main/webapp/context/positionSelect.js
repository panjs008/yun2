$(document).ready(function(){
    if($("#storageId").val()){
        //BindSelectPosition("positionNameId","emkStorageSetController.do?findPositionList&storageId="+$("#storageId").val(),1,$("#positionId").val()+","+$("#positionName").val());
        $("#positionNameId").change(function(){
            var itemarr = $("#positionNameId").val().split(","); //字符分割
            $("#positionId").val(itemarr[0]);
            $("#positionName").val(itemarr[1]);
        });
    }
});

function BindSelectPosition(ctrlName, url,type,categoryId) {
    var control = $('#' + ctrlName);
    //设置Select2的处理
    control.select2({
        formatResult: formatState,
        formatSelection: formatState,
        escapeMarkup: function (m) {
            return m;
        }
    });
    url = url+"&storageId="+$("#storageId").val();
    //绑定Ajax的内容
    $.getJSON(url, function (data) {
        control.empty();//清空下拉框
        control.append("<option value=''>请选择</option>");
        if(data.obj){
            $.each(data.obj, function (i, item) {
                control.append("<option value='" + item.id + "'>" + item.positionName + "</option>");
            });
            if(type ==1 && categoryId !='' && categoryId != null && categoryId!= 'undefined'){
                $("#"+ctrlName).select2('val',categoryId);
            }
        }
    });
}
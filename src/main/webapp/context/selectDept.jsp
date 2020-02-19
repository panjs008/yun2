<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link type="text/css" rel="stylesheet" href="plug-in/select2/css/select2.min.css"/>
<script type="text/javascript" src="plug-in/select2/js/select2.js"></script>
<script type="text/javascript" src="plug-in/select2/js/pinyin.js"></script>

<script type="text/javascript">
    //编写自定义JS代码
    $(function(){
        $.ajax({
            url : "hmStaffController.do?getDept&code=${orgCode}",
            type : 'post',
            cache : false,
            data: null,
            success : function(data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    var msg = d.msg;
                    var dataItems = new Array(); //定义一数组
                    dataItems = d.obj.split(";"); //字符分割
                    $('#deptCode').empty();
                    var option1 = '<option value="">--选择--</option>';
                    var firstJgmc;
                    for (i=0;i<dataItems.length ;i++ ) {
                        var dataitem = new Array(); //定义一数组
                        dataitem = dataItems[i].split(","); //字符分割
                        if(dataitem[0]!="") {
                            if(dataitem[0] == '${hmStaffPage.deptCode}'){
                                option1 += '<option value='+dataitem[0]+' selected>'+dataitem[1]+'</option>';
                            }else{
                                option1 += '<option value='+dataitem[0]+'>'+dataitem[1]+'</option>';
                            }
                        }
                    }
                    $("#deptCode").append(option1);
                    if(${hmStaffPage.workCode ne '' && hmStaffPage.workCode ne null}){
                        $.ajax({
                            url : "hmStaffController.do?getDept&code=${hmStaffPage.deptCode}",
                            type : 'post',
                            cache : false,
                            data: null,
                            success : function(data) {
                                var d = $.parseJSON(data);
                                if (d.success) {
                                    var msg = d.msg;
                                    var dataItems = new Array(); //定义一数组
                                    dataItems = d.obj.split(";"); //字符分割
                                    //W.document.location.reload(true);
                                    $('#workCode').empty();
                                    var option1='';
                                    for (i=0;i<dataItems.length ;i++ ) {
                                        var dataitem = new Array(); //定义一数组
                                        dataitem = dataItems[i].split(","); //字符分割
                                        if(dataitem[0] == '${hmStaffPage.workCode}'){
                                            option1 += '<option value='+dataitem[0]+' selected>'+dataitem[1]+'</option>';
                                        }else{
                                            option1 += '<option value='+dataitem[0]+'>'+dataitem[1]+'</option>';
                                        }
                                    }
                                    $("#workCode").append(option1);

                                    $.ajax({
                                        url : "hmStaffController.do?getDept&code=${hmStaffPage.workCode}",
                                        type : 'post',
                                        cache : false,
                                        data: null,
                                        success : function(data) {
                                            var d = $.parseJSON(data);
                                            if (d.success) {
                                                var msg = d.msg;
                                                var dataItems = new Array(); //定义一数组
                                                dataItems = d.obj.split(";"); //字符分割

                                                $('#groupCode').empty();
                                                var option1='';
                                                for (i=0;i<dataItems.length ;i++ ) {
                                                    var dataitem = new Array(); //定义一数组
                                                    dataitem = dataItems[i].split(","); //字符分割
                                                    if(dataitem[0] == '${hmStaffPage.groupCode}'){
                                                        option1 += '<option value='+dataitem[0]+' selected>'+dataitem[1]+'</option>';
                                                    }else{
                                                        option1 += '<option value='+dataitem[0]+'>'+dataitem[1]+'</option>';
                                                    }
                                                }
                                                $("#groupCode").append(option1);
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }

                }
            }
        });


        $("#deptCode").change(function(){
            $.ajax({
                url : "hmStaffController.do?getDept&code="+$("#deptCode").val(),
                type : 'post',
                cache : false,
                data: null,
                success : function(data) {
                    var d = $.parseJSON(data);
                    if (d.success) {
                        var msg = d.msg;
                        var dataItems = new Array(); //定义一数组
                        dataItems = d.obj.split(";"); //字符分割
                        //W.document.location.reload(true);
                        $('#workCode').empty();
                        $('#groupCode').empty();

                        var option3='<option value="">--选择--</option>';
                        var firstJgmc;

                        for (i=0;i<dataItems.length ;i++ ) {
                            var dataitem = new Array(); //定义一数组
                            dataitem = dataItems[i].split(","); //字符分割
                            if(i == 0){
                                firstJgmc = dataitem[0];
                            }
                            if(dataitem[0]!="") {
                                option3 += '<option value='+dataitem[0]+'>'+dataitem[1]+'</option>';
                            }
                        }
                        $("#workCode").append(option3);

                        $.ajax({
                            url : "hmStaffController.do?getDept&code="+firstJgmc,
                            type : 'post',
                            cache : false,
                            data: null,
                            success : function(data) {
                                var d = $.parseJSON(data);
                                if (d.success) {
                                    var msg = d.msg;
                                    var dataItems = new Array(); //定义一数组
                                    dataItems = d.obj.split(";"); //字符分割
                                    $('#groupCode').empty();
                                    var option1='<option value="">--选择--</option>';
                                    for (i=0;i<dataItems.length ;i++ ) {
                                        var dataitem = new Array(); //定义一数组
                                        dataitem = dataItems[i].split(","); //字符分割
                                        if(dataitem[0]!="") {
                                            option1 += '<option value='+dataitem[0]+'>'+dataitem[1]+'</option>';
                                        }
                                    }
                                    $("#groupCode").append(option1);
                                }
                            }
                        });
                    }
                }
            });
        });

        $("#workCode").change(function(){
            $.ajax({
                url : "hmStaffController.do?getDept&code="+$("#workCode").val(),
                type : 'post',
                cache : false,
                data: null,
                success : function(data) {
                    var d = $.parseJSON(data);
                    if (d.success) {
                        var msg = d.msg;
                        var dataItems = new Array(); //定义一数组
                        dataItems = d.obj.split(";"); //字符分割
                        $('#groupCode').empty();
                        var option3='<option value="">--选择--</option>';

                        for (i=0;i<dataItems.length ;i++ ) {
                            var dataitem = new Array(); //定义一数组
                            dataitem = dataItems[i].split(","); //字符分割
                            if(dataitem[0]!="") {
                                option3 += '<option value='+dataitem[0]+'>'+dataitem[1]+'</option>';
                            }
                        }
                        $("#groupCode").append(option3);
                    }
                }
            });

        });
    });
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

</script>
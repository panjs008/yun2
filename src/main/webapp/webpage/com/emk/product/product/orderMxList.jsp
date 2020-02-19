<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<script type="text/javascript">
    var flag = ${fn:length(storageList)};
    var currentFlag = ${fn:length(storageList)};
    $('#addBtn').linkbutton({
        iconCls: 'icon-add'
    });
    $('#delBtn').linkbutton({
        iconCls: 'icon-remove'
    });
    $('#setPriceBtn').linkbutton({
        iconCls: 'icon-comturn'
    });
    $('#setBtn').linkbutton({
        iconCls: 'icon-comturn'
    });
    $('#setBtn2').linkbutton({
        iconCls: 'icon-comturn'
    });
    $('#refreshBtn').linkbutton({
        iconCls: 'icon-reload'
    });
    document.onkeydown = function (e) { // 回车提交表单
        var theEvent = window.event || e;
        var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
        if (code == 13) {
            addTr();
        }
    }
    $('#addBtn').bind('click', function(){
        addTr();
    });
    $('#refreshBtn').bind('click', function(){
        location.reload();
    });

    function addTr(){
        flag++;
        if(flag == 1){
            $("#seqNum").html(1);
        }else{
            $("#seqNum").html(flag);
        }
        var tr =  $("#add_jeecgOrderProduct_table_template tr").clone();
        $("#add_jeecgOrderProduct_table").append(tr);
        $("#add_jeecgOrderProduct_table").find("[id='storageSetId00']").attr("id","storageSetId"+flag);
        $("#add_jeecgOrderProduct_table").find("[id='storageSetPosiId00']").attr("id","storageSetPosiId"+flag);

        resetTrNum('add_jeecgOrderProduct_table');
        $("#storageSetId"+flag).attr("datatype","*");
        $("#storageSetId"+flag).attr("onchange","getPosi("+flag+")");
        $("#orderMxListID").val($("#mxtb").find("tr").length-1);

        $("input").change(function()
        {
            $(this).css("border","");
        });
        $("input").bind({focus:function()
        {
            $(this).addClass("color");
            $("#mxtb input[type=text]").css("border","0");
        },
            blur:function()
            {
                $(this).removeClass("color");
            }});
        $("input").keydown(function(event)
        {
            var thisinput = $(this)[0];
            getfocus(thisinput);
        });
    }
    function getPosi(index){
        $('#storageSetPosiId'+index).empty();
        $.ajax({
            url : "emkStorageSetPositionController.do?getPosition&storage_id="+$("#storageSetId"+index).val(),
            type : 'post',
            cache : false,
            data: null,
            success : function(data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    var msg = d.msg;
                    var dataItems = new Array(); //定义一数组
                    dataItems = d.obj.split(";"); //字符分割
                    var  option1 = '<option value=""></option>';
                    for (i=0;i<dataItems.length ;i++ ) {
                        var dataitem = new Array(); //定义一数组
                        dataitem = dataItems[i].split(","); //字符分割
                        option1 += '<option value='+dataitem[0]+'>'+dataitem[1]+'</option>';
                    }
                    $("#storageSetPosiId"+index).append(option1);
                }
            }
        });
    }

    function getPosi2(index,positionId){
        $('#storageSetPosiId'+index).empty();
        $.ajax({
            url : "emkStorageSetPositionController.do?getPosition&storage_id="+$("#storageSetId"+index).val(),
            type : 'post',
            cache : false,
            data: null,
            success : function(data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    var msg = d.msg;
                    var dataItems = new Array(); //定义一数组
                    dataItems = d.obj.split(";"); //字符分割
                    var  option1 = '<option value=""></option>';
                    for (i=0;i<dataItems.length ;i++ ) {
                        var dataitem = new Array(); //定义一数组
                        dataitem = dataItems[i].split(","); //字符分割
                        if(dataitem[0] == positionId){
                            option1 += '<option value='+dataitem[0]+' selected>'+dataitem[1]+'</option>';
                        }else{
                            option1 += '<option value='+dataitem[0]+'>'+dataitem[1]+'</option>';
                        }
                    }
                    $("#storageSetPosiId"+index).append(option1);
                }
            }
        });
    }
    $('#setBtn2').bind('click', function(){
        $.dialog({
            content: 'url:hmCategoryController.do?for&forColumn&parentCode=A01A19',
            zIndex: getzIndex(),
            title : '明细列配置',
            cache:false,
            lock : true,
            width: 500,
            height: 580,
            cancelVal: '关闭',
            cancel: function(){
                location.reload();
            },
        });
    });

    $('#delBtn').bind('click', function(){
        var chk_value =[];
        $('input[name="ck"]:checked').each(function(){
            chk_value.push($(this).val());
        });
        $("#add_jeecgOrderProduct_table").find("input:checked").parent().parent().remove();
        resetTrNum('add_jeecgOrderProduct_table');
        $("#orderMxListID").val($("#mxtb").find("tr").length-1);

        if(chk_value.length>0){
            /* $.ajax({
             url : "dxRkglMxController.do?doBatchDel&ids="+chk_value,
             type : 'post',
             cache : false,
             data: null,
             success : function(data) {
             var d = $.parseJSON(data);
             if (d.success) {
             var msg = d.msg;
             tip(msg);
             //W.document.location.reload(true);
             }
             }
             });*/
        }

    });
    $(document).ready(function(){
        $(".datagrid-toolbar").parent().css("width","auto");
        //将表格的表头固定
        $("#jeecgOrderProduct_table").createhftable({
            height:'200px',
            width:'auto',
            fixFooter:false
        });
        <c:if test="${fn:length(storageList) eq 0}">
            $('#addBtn').click();
        </c:if>

    });


    //回车和小键盘切换input焦点
    function getfocus(thisinput) {
        var inputs = document.getElementsByTagName("input");
        if(event.keyCode == 13 || event.keyCode == 40) {
            for (var i=0; i < inputs.length; i++) {
                if (thisinput == inputs[i])
                {
                    if (i == (inputs.length - 1 ))
                    {
                        inputs[0].focus();
                        break;
                    }
                    else
                    {
                        inputs[i + 1].focus();
                        break;
                    }
                }
            }
        } else if(event.keyCode == 38) {
            for (var i=0; i < inputs.length; i++) {
                if (thisinput == inputs[i])
                {
                    if(i != 0)
                    {
                        inputs[i - 1].focus();
                        break;
                    }
                    else
                    {
                        inputs[inputs.length - 1].focus();
                        break;
                    }
                }
            }
        }
    }
    $(function() {
        $("input").change(function()
        {
            $(this).css("border","");
            $("#mxtb input[type=text]").css("border","0");
            $("#mxtb select").css("border","0");

        });
        $("input").bind({focus:function()
        {
            $(this).addClass("color");
            $("#mxtb input[type=text]").css("border","0");
            $("#mxtb select").css("border","0");

        }, blur:function()
        {
            $(this).removeClass("color");
            $("#mxtb input[type=text]").css("border","0");
            $("#mxtb select").css("border","0");


        }});
        $("input").keydown(function(event)
        {
            var thisinput = $(this)[0];
            getfocus(thisinput);
            $("#mxtb input[type=text]").css("border","0");
            $("#mxtb select").css("border","0");


        });
    });
</script>
<style>
    .table-c table{border-right:1px solid #ddd;border-bottom:1px solid #ddd;}
    .table-c table td{border-left:1px solid #ddd;border-top:1px solid #ddd;height: 36px;}
</style>
<!-- 添加明细模版-->
<table style="width:100%;display: none;border: 1px;" cellpadding="0" cellspacing="2" border="0">
    <tbody id="add_jeecgOrderProduct_table_template">
    <tr>
        <td align="center"><input style="width: 25px;" id="ck00" type="checkbox" name="ck" value="0"/></td>
        <td align="center" width="40"><span id="seqNum"></span></td>

        <td align="center">
            <select id="storageSetId00" name="orderMxList[#index#].storageSetId" nullmsg="请选择仓库！"  style="width:90%;border: 0">';
                <option value="" ></option>
                <c:forEach var="list" items="${storageSetEntities}" varStatus="status">
                    <option value="${list.id}" >${list.storageName}</option>';
                </c:forEach>
            </select>
        </td>
        <td align="center">
            <select id="storageSetPosiId00" name="orderMxList[#index#].storageSetPosiId" nullmsg="请选择库位！" style="width:90%;border: 0">';
            </select>
        </td>
        <c:forEach var="list" items="${categoryEntities}" varStatus="status">
            <td align="center" style="display: ${list.is_show eq '0' ? '':'none'};">
                <input id="${list.code}00" nullmsg="请输入${list.name}！" errormsg="请输入${list.name}" name="orderMxList[#index#].${list.code}" maxlength="100" type="text"
                       style="width: ${(list.column_type eq '0' || list.column_type eq '2'  || list.column_type eq '6') ? 86:94}%;border:0;" />

            </td>
        </c:forEach>
        <td align="center"><input id="total00" nullmsg="请输入数量！"  errormsg="请输入数量" datatype="n" name="orderMxList[#index#].total" maxlength="100" type="text" value=""
                                  style="width: 90%;border:0;" ignore="ignore"></td>
    </tr>
    </tbody>

</table>
<div style="padding: 3px; height: 29px; width:100%;margin-bottom:4px;margin-top: 2px; " class="datagrid-toolbar"><a id="addBtn" href="#" title="新增">新增</a><a id="delBtn" href="#" title="删除">删除</a> <a id="setBtn2" href="#" title="明细列配置">列配置</a></div>
<%--<table border="0" cellpadding="2" cellspacing="0" id="jeecgOrderProduct_table">--%>
<input id="orderMxListID" type="hidden" name="orderMxListID" value="${fn:length(storageList)}"/>
<div class="table-c" >
    <table id="mxtb" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#F8F8F8" style="height: 32px;" >
            <td align="center"  width="25" ><input type="checkbox" onclick="selectAll(this.checked)"/></td>
            <td align="center"  width="40">序号</td>
            <td align="center"  width="100">仓库</td>
            <td align="center"  width="100">货位</td>

            <c:forEach var="cateList" items="${categoryEntities}" varStatus="status">
                <td  align="center"  style="display: ${cateList.is_show eq '0' ? '':'none'};" width="${(cateList.column_type eq '4') ? '160':((cateList.column_type eq '0' || cateList.column_type eq '2') || cateList.column_type eq '2' || cateList.column_type eq '6') ? '60':'140'}">${cateList.name}</td>
            </c:forEach>
            <td align="center"  width="100">数量</td>

        </tr>
        <tbody id="add_jeecgOrderProduct_table">
        <c:if test="${fn:length(storageList)  > 0 }">
            <c:forEach items="${storageList}" var="poVal" varStatus="ostatus">
                <tr>
                    <td align="center"><input style="width: 25px;" type="checkbox" name="ck" value="${poVal.id}"/>
                    </td>
                    <td align="center" width="35">${ostatus.index+1}<input style="width: 25px;" type="hidden" name="orderMxList[${ostatus.index}].id" value="${poVal.id}"/></td>
                    <td align="center">
                        <select id="storageSetId${ostatus.index}" datatype="*" name="orderMxList[${ostatus.index}].storageSetId" onchange="getPosi('${ostatus.index}')" nullmsg="请选择仓库！" style="width:90%;border: 0">';
                            <option value="" ></option>
                            <c:forEach var="list" items="${storageSetEntities}">
                                <option value="${list.id}" ${list.id eq poVal.storageId ? 'selected':''} >${list.storageName}</option>';
                            </c:forEach>
                        </select>
                    </td>
                    <td align="center">
                        <select id="storageSetPosiId${ostatus.index}" name="orderMxList[${ostatus.index}].storageSetPosiId" nullmsg="请选择库位！" style="width:90%;border: 0">';
                        </select>
                        <script>
                            getPosi2(${ostatus.index},'${poVal.positionId}');
                        </script>
                    </td>
                   <%-- <td align="center"><input id="color${ostatus.index}" nullmsg="请输入颜色！"  errormsg="请输入颜色" value="${poVal.color}"  name="orderMxList[${ostatus.index}].color" maxlength="100" type="text" value=""
                                              style="width: 90%;border:0;" ignore="ignore"></td>
                    <td align="center"><input id="total${ostatus.index}" nullmsg="请输入数量！"  errormsg="请输入数量" datatype="n" value="${poVal.total}"  name="orderMxList[${ostatus.index}].total" maxlength="100" type="text" value=""
                                              style="width: 90%;border:0;" ignore="ignore"></td>--%>
                    <c:forEach var="list" items="${categoryEntities}" varStatus="catestatus">
                        <td align="center" style="display: ${list.is_show eq '0' ? '':'none'};">
                            <input id="${list.code}${ostatus.index}" nullmsg="请输入${list.name}！"   datatype="${list.column_type eq '2' ? 'n':list.column_type eq '6' ? 'd':'*'}"   errormsg="请输入${list.name}" name="orderMxList[${ostatus.index}].${list.code}" maxlength="100" type="text"
                                   style="width: ${(list.column_type eq '0' || list.column_type eq '2') ? 86:94}%;border:0;" value="${poVal[list.code]}" <c:if test="${list.required ne '0'}">ignore="ignore"</c:if>/>

                        </td>
                    </c:forEach>
                    <td align="center"><input id="total${ostatus.index}" nullmsg="请输入数量！"  errormsg="请输入数量" datatype="n" value="${poVal.total}"  name="orderMxList[${ostatus.index}].total" maxlength="100" type="text" value=""
                                              style="width: 90%;border:0;" ignore="ignore"></td>
                </tr>

            </c:forEach>
        </c:if>
        </tbody>
    </table>
</div>



<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<script type="text/javascript">
    var flag = ${fn:length(priceList)};
    var currentFlag = ${fn:length(priceList)};
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
    $('#setBtn').bind('click', function(){
        $.dialog({
            content: 'url:hmCategoryController.do?for&forColumn&parentCode=A01A01',
            zIndex: getzIndex(),
            title : '列配置',
            cache:false,
            lock : true,
            width: 500,
            height: 500,
            cancelVal: '关闭',
            cancel: function(){
                location.reload();
            },
        });
    });
    $('#setPriceBtn').bind('click', function(){
        $.dialog({
            content: 'url:emkProductPriceController.do?list',
            zIndex: getzIndex(),
            title : '商品预设价格管理',
            cache:false,
            lock : true,
            width: 500,
            height: 500,
            cancelVal: '关闭',
            cancel: function(){
                location.reload();
            },
        });
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


        resetTrNum('add_jeecgOrderProduct_table');

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
        <c:if test="${param.productId eq null || param.productId eq ''}">
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

        });
        $("input").bind({focus:function()
        {
            $(this).addClass("color");
            $("#mxtb input[type=text]").css("border","0");

        },blur:function()
            {
                $(this).removeClass("color");
                $("#mxtb input[type=text]").css("border","0");

            }});
        $("input").keydown(function(event)
        {
            var thisinput = $(this)[0];
            getfocus(thisinput);
            $("#mxtb input[type=text]").css("border","0");

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
        <td align="center"><input id="priceNo00" nullmsg="请输入价格编号！"  errormsg="请输入价格编号"  name="orderMxList[#index#].priceNo" maxlength="100" type="text" value=""
                                  style="width: 90%;border:0;" ignore="ignore"></td>
        <td align="center"><%--<input id="priceName00" nullmsg="请输入价格名称！"  errormsg="请输入价格名称" name="orderMxList[#index#].priceName" maxlength="100" type="text" value=""
                                  style="width: 92%;border:0;" ignore="ignore">--%>
            <t:dictSelect id="priceName00" field="orderMxList[#index#].priceName" typeGroupCode="jgmc"  defaultVal="" hasLabel="false" title="价格名称"></t:dictSelect>

        </td>
        <td align="center"><input id="price00" nullmsg="请输入价格！"  datatype="d" errormsg="请输入价格" name="orderMxList[#index#].price" maxlength="100" type="text" value=""
                                  style="width: 92%;border:0;" ignore="ignore"></td>
        <td align="center">
            <t:dictSelect id="unit100" field="orderMxList[#index#].unit1" typeGroupCode="fzunit1"  defaultVal="" hasLabel="false" title="辅助单位1"></t:dictSelect>
        </td>
        <td align="center"><input id="hsPercentage00" nullmsg="请输入换算关系！"  datatype="d" errormsg="请输入换算关系" name="orderMxList[#index#].hsPercentage" maxlength="100" type="text" value=""
                                  style="width: 92%;border:0;" ignore="ignore"></td>
        <td align="center"><input id="priceT00" nullmsg="请输入价格！"  datatype="d" errormsg="请输入价格" name="orderMxList[#index#].priceT" maxlength="100" type="text" value=""
                                  style="width: 92%;border:0;" ignore="ignore"></td>
        <td align="center">
            <t:dictSelect id="unit200" field="orderMxList[#index#].unit2" typeGroupCode="fzunit2"  defaultVal="" hasLabel="false" title="辅助单位2"></t:dictSelect>
        </td>
        <td align="center"><input id="hsPercentage200" nullmsg="请输入换算关系！"  datatype="d" errormsg="请输入换算关系" name="orderMxList[#index#].hsPercentage2" maxlength="100" type="text" value=""
                                  style="width: 92%;border:0;" ignore="ignore"></td>
        <td align="center"><input id="priceT200" nullmsg="请输入价格！"  datatype="d" errormsg="请输入价格" name="orderMxList[#index#].priceT2" maxlength="100" type="text" value=""
                                  style="width: 92%;border:0;" ignore="ignore"></td>
    </tr>
    </tbody>

</table>

<div style="padding: 3px; height: 29px; width:100%;margin-bottom:4px;margin-top: 2px; " class="datagrid-toolbar"><a id="addBtn" href="#" title="添加零售价">添加</a> <a id="delBtn" href="#" title="删除零售价">删除</a><%--<a id="refreshBtn" href="#" title="刷新">刷新</a>--%><%--<a id="setPriceBtn" href="#" title="商品价格预设">商品价格预设</a>--%> <a id="setBtn" href="#" title="列配置">列配置</a> </div>
<%--<table border="0" cellpadding="2" cellspacing="0" id="jeecgOrderProduct_table">--%>
<input id="orderMxListID" type="hidden" name="orderMxListID" value="${fn:length(priceList)}"/>
<div class="table-c" >
    <table id="mxtb" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#F8F8F8" style="height: 32px;" >
            <td align="center"  width="25" ><input type="checkbox" onclick="selectAll(this.checked)"/></td>
            <td align="center"  width="40">序号</td>
            <td align="center"  width="50">价格编号</td>
            <td align="center"  width="100">价格名称</td>
            <td align="center"  width="100">价格</td>
            <td align="center"  width="80">辅助单位1</td>
            <td align="center"  width="80">换算关系</td>
            <td align="center"  width="100">价格</td>
            <td align="center"  width="80">辅助单位2</td>
            <td align="center"  width="80">换算关系</td>
            <td align="center"  width="100">价格</td>

        </tr>
        <tbody id="add_jeecgOrderProduct_table">
        <c:if test="${fn:length(priceList)  > 0 }">
            <c:forEach items="${priceList}" var="poVal" varStatus="status">
                <tr>
                    <td align="center"><input style="width: 25px;" type="checkbox" name="ck" value="${poVal.id}"/>
                    </td>
                    <td align="center" width="35">${status.index+1}</td>
                    <td align="center"><input  nullmsg="请输入价格编号！" id="priceNo${status.index}"   errormsg="请输入价格编号" value="${poVal.priceNo}" name="orderMxList[${status.index}].priceNo" maxlength="100" type="text" value=""
                                               style="width: 90%;border:0;" ignore="ignore"></td>
                    <td align="center">
                        <t:dictSelect id="priceName${status.index}" field="orderMxList[${status.index}].priceName" typeGroupCode="jgmc"  defaultVal="${poVal.priceName}" hasLabel="false" title="价格名称"></t:dictSelect>
                        <%--<input  nullmsg="请输入价格名称！" id="priceName${status.index}"  errormsg="请输入价格名称" value="${poVal.priceName}" name="orderMxList[${status.index}].priceName" maxlength="100" type="text" value=""
                                               style="width: 92%;border:0;" ignore="ignore">--%></td>
                    <td align="center"><input  nullmsg="请输入价格！"  id="price${status.index}" errormsg="请输入价格" value="${poVal.price}" name="orderMxList[${status.index}].price" maxlength="100" type="text" value=""
                                               style="width: 93%;border:0;" datatype="d"  ignore="ignore"></td>
                    <td align="center">
                        <t:dictSelect id="unit1${status.index}" field="orderMxList[${status.index}].unit1" typeGroupCode="fzunit1"  defaultVal="${poVal.unit1}" hasLabel="false" title="辅助单位1"></t:dictSelect>
                    </td>
                    <td align="center"><input id="hsPercentage${status.index}" nullmsg="请输入换算关系！"  value="${poVal.hsPercentage}"  datatype="d" errormsg="请输入换算关系" name="orderMxList[${status.index}].hsPercentage" maxlength="100" type="text" value=""
                                              style="width: 92%;border:0;" ignore="ignore"></td>
                    <td align="center"><input  nullmsg="请输入价格！"  id="priceT${status.index}" errormsg="请输入价格" value="${poVal.priceT}" name="orderMxList[${status.index}].priceT" maxlength="100" type="text" value=""
                                               style="width: 93%;border:0;" datatype="d"  ignore="ignore"></td>
                    <td align="center">
                        <t:dictSelect id="unit2${status.index}" field="orderMxList[${status.index}].unit2" typeGroupCode="fzunit2"  defaultVal="${poVal.unit2}" hasLabel="false" title="辅助单位2"></t:dictSelect>
                    </td>
                    <td align="center"><input id="hsPercentage2${status.index}" nullmsg="请输入换算关系！"  value="${poVal.hsPercentage2}"  datatype="d" errormsg="请输入换算关系" name="orderMxList[${status.index}].hsPercentage2" maxlength="100" type="text" value=""
                                              style="width: 92%;border:0;" ignore="ignore"></td>
                    <td align="center"><input  nullmsg="请输入价格！"  id="priceT2${status.index}" errormsg="请输入价格" value="${poVal.priceT2}" name="orderMxList[${status.index}].priceT2" maxlength="100" type="text" value=""
                                               style="width: 93%;border:0;" datatype="d"  ignore="ignore"></td>
                </tr>

            </c:forEach>
        </c:if>
        </tbody>
    </table>
</div>



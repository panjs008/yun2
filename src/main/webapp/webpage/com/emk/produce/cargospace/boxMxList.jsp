<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
    var boxflag = ${fn:length(emkProOrderBoxEntities)};
    var currentFlag = ${fn:length(emkProOrderBoxEntities)};
    var colorJson = JSON.parse(${color});


    $('#addBtnBox').linkbutton({
        iconCls: 'icon-add'
    });
    $('#delBtnBox').linkbutton({
        iconCls: 'icon-remove'
    });
    $('#addBtnBox').bind('click', function(){
        boxflag++;
        //$("#add_jeecgOrderProduct_tableBox").find("[id='signPrice00']").attr("datatype","d");

        var tr =  $("#add_jeecgOrderProduct_tableBox_template tr").clone();
        $("#add_jeecgOrderProduct_tableBox").append(tr);

        $("#add_jeecgOrderProduct_tableSR").find("[id='color00']").attr("id","color"+boxflag);
        BindColorSelect("color"+boxflag, colorJson,"1","");

        if(boxflag>1 || ${param.proOrderId ne null}){
            $("html,body").animate({scrollTop:1000},1);
        }

        /*$("#add_jeecgOrderProduct_tableBox").find("[id='atotalBox00']").attr("id","atotalBox"+boxflag);
        $("#add_jeecgOrderProduct_tableBox").find("[id='asumZsl00']").attr("id","asumZsl"+boxflag);*/
        resetTrNum('add_jeecgOrderProduct_tableBox');
        $("#orderMxListIDBox").val($("#mxtbBox").find("tr").length-1);
    });
    $('#delBtnBox').bind('click', function(){
        var chk_value =[];
        $('input[name="ck"]:checked').each(function(){
            chk_value.push($(this).val());
        });
        $("html,body").animate({scrollTop:1000},1);
        $("#add_jeecgOrderProduct_tableBox").find("input:checked").parent().parent().remove();
        resetTrNum('add_jeecgOrderProduct_tableBox');
        $("#orderMxListIDBox").val($("#mxtbBox").find("tr").length-1);

        if(chk_value.length>0){

        }

    });
    $(document).ready(function(){
        $(".datagrid-toolbar").parent().css("width","auto");
        <c:if test="${param.cargoId eq null || param.cargoId eq ''}">
            $('#addBtnBox').click();
        </c:if>
    });

</script>
<style>
    .table-c table{border-right:1px solid #ddd;border-bottom:1px solid #ddd}
    .table-c table td{border-left:1px solid #ddd;border-top:1px solid #ddd;height: 36px;}
</style>
<!-- 添加明细模版-->
<table style="width:100%;display: none;border: 1px;" cellpadding="0" cellspacing="2" border="0">
    <tbody id="add_jeecgOrderProduct_tableBox_template">
    <tr>
        <td align="center"><input style="width: 40px;" type="checkbox" name="ck" /></td>
        <td align="center">
            <select name="orderMxList[#index#].gyzl00" style="width: 86%;" nullmsg="请输入工艺种类！" errormsg="请输入工艺种类" datatype="*">
                <c:forEach items="${gylxList}" var="category">
                    <option value="${category.typecode}">${category.typename}</option>
                </c:forEach>
            </select>
           <%-- <input id="gyzl00" nullmsg="请输入工艺种类！"  errormsg="请输入工艺种类" name="orderMxList[#index#].gyzl00" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore">--%></td>
        <td align="center"><input id="proTypeName00" nullmsg="请输入款式大类！"  errormsg="请输入款式大类" name="orderMxList[#index#].proTypeName00" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>

        <td align="center">
            <%--<select name="orderMxList[#index#].acolorName00" style="width: 86%;" nullmsg="请输入颜色！" errormsg="请输入颜色" datatype="*">
                <c:forEach items="${colorList}" var="category">
                    <option value="${category.typecode}">${category.typename}</option>
                </c:forEach>
            </select>--%>
            <select id="color00" name="orderMxList[#index#].acolorName00" class="form-control select2" style="width:100px;">
                <option value=''>请选择</option>
            </select>

        </td>
        <td align="center"><input id="totalA" nullmsg="请输入数量！" datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].totalA" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalB" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].totalB" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalC" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].totalC" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalD" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].totalD" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalE" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].totalE" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalF" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].totalF" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalG" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].totalG" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalH" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].totalH" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalI" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].totalI" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalJ" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].totalJ" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalK" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].totalK" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>

        <%--<td align="center"><input id="ainboxTotal00"  nullmsg="请输入数量！" datatype="n" errormsg="请输入数量" name="orderMxList[#index#].ainboxTotal00" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>--%>
        <td align="center"><input id="asumZsl00" datatype="n"  nullmsg="请输入总数量！"  errormsg="请输入整数" name="orderMxList[#index#].asumZsl00" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="alongVal00" datatype="d"  nullmsg="请输入长！"  errormsg="请输入数值型数据" name="orderMxList[#index#].alongVal00" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="awidthVal00" datatype="d" nullmsg="请输入宽！"  errormsg="请输入数值型数据" name="orderMxList[#index#].awidthVal00" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="aheightVal00" datatype="d" nullmsg="请输入高！"  errormsg="请输入数值型数据" name="orderMxList[#index#].heightVal00" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="atotalBox00" datatype="n"  nullmsg="请输入箱数！"  errormsg="请输入整数" name="orderMxList[#index#].atotalBox00" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="price00" datatype="d" nullmsg="请输入单价！"  errormsg="请输入数值型数据" name="orderMxList[#index#].price00" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="money00" datatype="d" nullmsg="请输入金额！"  errormsg="请输入数值型数据" name="orderMxList[#index#].money00" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="aboxWeightMao00" datatype="d" nullmsg="请输入单箱毛重！"  errormsg="请输入数值型数据" name="orderMxList[#index#].aboxWeightMao00" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="aboxWeightJz00" datatype="d" nullmsg="请输入单箱净重！"  errormsg="请输入数值型数据" name="orderMxList[#index#].aboxWeightJz00" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="aboxVolume00" datatype="d" nullmsg="请输入单箱体积！"  errormsg="请输入数值型数据" name="orderMxList[#index#].aboxVolume00" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="asumVolume00" datatype="d" nullmsg="请输入总体积！"  errormsg="请输入数值型数据" name="orderMxList[#index#].asumVolume00" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="asumWeightMao00" datatype="d" nullmsg="请输入总毛重！"  errormsg="请输入数值型数据" name="orderMxList[#index#].asumWeightMao00" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="asumWeightJz00" datatype="d" nullmsg="请输入总净重！"  errormsg="请输入数值型数据" name="orderMxList[#index#].asumWeightJz00" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
    </tr>
    </tbody>

</table>
<div style="padding: 3px; height: 25px; width:100%;margin-bottom:4px " class="datagrid-toolbar"><a id="addBtnBox" href="#"></a> <a id="delBtnBox" href="#"></a></div>
<%--<table border="0" cellpadding="2" cellspacing="0" id="jeecgOrderProduct_table">--%>
<input id="orderMxListIDBox" type="hidden" name="orderMxListIDBox" value="${fn:length(emkProOrderBoxEntities)}"/>
<div class="table-c" style="overflow: auto; width: 100%;">
    <table id="mxtbBox" width="2000px"  border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#F8F8F8" style="height: 32px;" >
            <td align="center"  width="40" rowspan="2"><input type="checkbox" onclick="selectAll(this.checked)" /></td>
            <td align="center"  width="90" rowspan="2">工艺种类</td>
            <td align="center"  width="90" rowspan="2">款式大类</td>
            <td align="center"  width="90" rowspan="2">颜色名称</td>
            <%--<td align="center"  width="90">尺码</td>--%>
            <td align="center"  width="400" colspan="11" >尺码</td>
        <%--<td align="center"  width="90">数量</td>--%>
            <td align="center"  width="90" rowspan="2">总数量</td>
            <td align="center"  width="90" rowspan="2">长（CM)</td>
            <td align="center"  width="90" rowspan="2">宽(cm)</td>
            <td align="center"  width="90" rowspan="2">高(CM)</td>
            <td align="center"  width="90" rowspan="2">箱数</td>
            <td align="center"  width="90" rowspan="2">单价</td>
            <td align="center"  width="90" rowspan="2">金额</td>
            <td align="center"  width="90" rowspan="2">单箱毛重</td>
            <td align="center"  width="90" rowspan="2">单箱净重</td>
            <td align="center"  width="90" rowspan="2">单箱体积</td>
            <td align="center"  width="90" rowspan="2">总体积</td>
            <td align="center"  width="90" rowspan="2">总毛重</td>
            <td align="center"  width="90" rowspan="2">总净重</td>
        </tr>
        <tr>
            <td align="center" ><input  value="${emkSizePage.sizeA}" name="sizeA" maxlength="100" type="text" value="" style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeB}" name="sizeB" maxlength="100" type="text" value="" style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeC}" name="sizeC" maxlength="100" type="text" value="" style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeD}" name="sizeD" maxlength="100" type="text" value="" style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeE}" name="sizeE" maxlength="100" type="text" value="" style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeF}" name="sizeF" maxlength="100" type="text" value="" style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeG}" name="sizeG" maxlength="100" type="text" value="" style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeH}" name="sizeH" maxlength="100" type="text" value="" style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeI}" name="sizeI" maxlength="100" type="text" value="" style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeJ}" name="sizeJ" maxlength="100" type="text" value="" style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeK}" name="sizeK" maxlength="100" type="text" value="" style="width: 80%;" ignore="ignore"></td>
        </tr>
        <tbody id="add_jeecgOrderProduct_tableBox">
        <c:if test="${fn:length(emkProOrderBoxEntities)  > 0 }">
            <c:set var="zxs" value="0"/>
            <c:set var="zje" value="0"/>

            <c:forEach items="${emkProOrderBoxEntities}" var="poVal" varStatus="status">
                <tr>
                    <td align="center"><input style="width: 40px;" type="checkbox" name="ck" value="${poVal.id}"/>
                    </td>
                    <td align="center">
                        <select name="orderMxList[${status.index}].gyzl00" style="width: 86%;" nullmsg="请输入工艺种类！" errormsg="请输入工艺种类" datatype="*">
                            <c:forEach items="${gylxList}" var="category">
                                <option value="${category.typecode}" ${category.typecode eq poVal.gyzl ? 'selected':''}>${category.typename}</option>
                            </c:forEach>
                        </select>
                        <%--<input  nullmsg="请输入工艺种类！"  errormsg="请输入工艺种类" value="${poVal.gyzl}" name="orderMxList[${status.index}].gyzl00" maxlength="100" type="text" value=""
                                               style="width: 86%;" ignore="ignore">--%>
                    </td>
                    <td align="center">
                        <input  nullmsg="请输入款式大类！"  errormsg="请输入款式大类" value="${poVal.proTypeName}" name="orderMxList[${status.index}].proTypeName00" maxlength="100" type="text" value=""
                                               style="width: 86%;" ignore="ignore">
                    </td>

                    <td align="center">
                        <%--<select name="orderMxList[${status.index}].acolorName00" style="width: 86%;" nullmsg="请输入颜色！" errormsg="请输入颜色" datatype="*">
                            <c:forEach items="${colorList}" var="category">
                                <option value="${category.typecode}" ${category.typecode eq poVal.colorName ? 'selected':''}>${category.typename}</option>
                            </c:forEach>
                        </select>--%>

                        <select id="color${status.index}" name="orderMxList[${status.index}].acolorName00" class="form-control select2" style="width:100px;">
                            <option value=''>请选择</option>
                        </select>
                        <script>
                            BindColorSelect("color${status.index}", colorJson,"1","${poVal.colorName}");
                        </script>
                    </td>
                   <%-- <td align="center">
                        <select name="orderMxList[${status.index}].asizeBox00" style="width: 86%;" nullmsg="请输入尺码！" errormsg="请输入尺码" datatype="*">
                            <c:forEach items="${sizeList}" var="category">
                                <option value="${category.typecode}" ${category.typecode eq poVal.size ? 'selected':''}>${category.typename}</option>
                            </c:forEach>
                        </select>
                    </td>--%>
                  <%--  <td align="center"><input  nullmsg="请输入数量！" datatype="n"  errormsg="请输入数量" value="${poVal.total}" name="orderMxList[${status.index}].ainboxTotal00" maxlength="100" type="text" value=""
                                               style="width: 86%;" ignore="ignore"></td>--%>
                    <c:set var="zsl" value="0"/>
                    <c:set var="zxs" value="${zxs+poVal.total}"/>
                    <c:set var="zsl" value="${zsl+poVal.emkSizeTotalEntity.totalA+poVal.emkSizeTotalEntity.totalB+poVal.emkSizeTotalEntity.totalC+poVal.emkSizeTotalEntity.totalD+poVal.emkSizeTotalEntity.totalE}"/>
                    <c:set var="zsl" value="${zsl+poVal.emkSizeTotalEntity.totalF+poVal.emkSizeTotalEntity.totalG+poVal.emkSizeTotalEntity.totalH+poVal.emkSizeTotalEntity.totalI+poVal.emkSizeTotalEntity.totalJ+poVal.emkSizeTotalEntity.totalK}"/>
                    <c:set var="zje" value="${zje+zsl*poVal.price}"/>

                    <td align="center"><input  nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" value="${poVal.emkSizeTotalEntity.totalA}" name="orderMxList[${status.index}].totalA" maxlength="100" type="text" value=""
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input  nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数"  value="${poVal.emkSizeTotalEntity.totalB}" name="orderMxList[${status.index}].totalB" maxlength="100" type="text" value=""
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input  nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数"  value="${poVal.emkSizeTotalEntity.totalC}" name="orderMxList[${status.index}].totalC" maxlength="100" type="text" value=""
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数"  value="${poVal.emkSizeTotalEntity.totalD}" name="orderMxList[${status.index}].totalD" maxlength="100" type="text" value=""
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数"  value="${poVal.emkSizeTotalEntity.totalE}" name="orderMxList[${status.index}].totalE" maxlength="100" type="text" value=""
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input  nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数"  value="${poVal.emkSizeTotalEntity.totalF}" name="orderMxList[${status.index}].totalF" maxlength="100" type="text" value=""
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input  nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数"  value="${poVal.emkSizeTotalEntity.totalG}" name="orderMxList[${status.index}].totalG" maxlength="100" type="text" value=""
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input  nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数"  value="${poVal.emkSizeTotalEntity.totalH}" name="orderMxList[${status.index}].totalH" maxlength="100" type="text" value=""
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input  nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数"  value="${poVal.emkSizeTotalEntity.totalI}" name="orderMxList[${status.index}].totalI" maxlength="100" type="text" value=""
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input  nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数"  value="${poVal.emkSizeTotalEntity.totalJ}" name="orderMxList[${status.index}].totalJ" maxlength="100" type="text" value=""
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input  nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数"  value="${poVal.emkSizeTotalEntity.totalK}" name="orderMxList[${status.index}].totalK" maxlength="100" type="text" value=""
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input  nullmsg="请输入总数量！" datatype="n"  errormsg="请输入整数" value="${zsl}" name="orderMxList[${status.index}].asumZsl00" maxlength="100" type="text" value=""
                                               style="width: 86%;" ignore="ignore"></td>

                    <td align="center"><input  nullmsg="请输入长！" datatype="d" errormsg="请输入数值型数据" value="${poVal.longVal}" name="orderMxList[${status.index}].alongVal00" maxlength="100" type="text" value=""
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input  nullmsg="请输入宽！"  datatype="d" errormsg="请输入数值型数据" value="${poVal.widthVal}" name="orderMxList[${status.index}].awidthVal00" maxlength="100" type="text" value=""
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input  nullmsg="请输入高！"  datatype="d" errormsg="请输入数值型数据" value="${poVal.heightVal}" name="orderMxList[${status.index}].aheightVal00" maxlength="100" type="text" value=""
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input  nullmsg="请输入箱数！"  datatype="n" errormsg="请输入整数" value="${poVal.total}" name="orderMxList[${status.index}].atotalBox00" maxlength="100" type="text" value=""
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input  nullmsg="请输入单价！"  datatype="d" errormsg="请输入数值型数据" value="${poVal.price}" name="orderMxList[${status.index}].price00" maxlength="100" type="text" value=""
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input  nullmsg="请输入金额！"  datatype="d" errormsg="请输入数值型数据" value="${poVal.price*zsl}" name="orderMxList[${status.index}].money00" maxlength="100" type="text" value=""
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input  nullmsg="请输入单箱毛重！"  datatype="d" errormsg="请输入数值型数据" value="${poVal.boxWeightMao}" name="orderMxList[${status.index}].aboxWeightMao00" maxlength="100" type="text" value=""
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input  nullmsg="请输入单箱净重！"  datatype="d" errormsg="请输入数值型数据" value="${poVal.boxWeightJz}" name="orderMxList[${status.index}].aboxWeightJz00" maxlength="100" type="text" value=""
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input  nullmsg="请输入单箱体积！"  datatype="d" errormsg="请输入数值型数据" value="${poVal.boxVolume}" name="orderMxList[${status.index}].aboxVolume00" maxlength="100" type="text" value=""
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input  nullmsg="请输入总体积！"  datatype="d" errormsg="请输入数值型数据" value="${poVal.total*poVal.boxVolume}" name="orderMxList[${status.index}].asumVolume00" maxlength="100" type="text" value=""
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input  nullmsg="请输入总毛重！"  datatype="d" errormsg="请输入数值型数据" value="${poVal.total*poVal.boxWeightMao}" name="orderMxList[${status.index}].asumWeightMao00" maxlength="100" type="text" value=""
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input  nullmsg="请输入总净重！"  datatype="d" errormsg="请输入数值型数据" value="${poVal.total*poVal.boxWeightJz}" name="orderMxList[${status.index}].asumWeightJz00" maxlength="100" type="text" value=""
                                               style="width: 86%;" ignore="ignore"></td>

                </tr>
            </c:forEach>
            <tr>
                <td colspan="19"></td>
                <td align="center"><input value="${zxs}"  maxlength="100" type="text" value="" style="width: 86%;" ignore="ignore"></td>
                </td>
                <td></td>
                <td align="center"><input value="${zje}"  maxlength="100" type="text" value="" style="width: 86%;" ignore="ignore"></td>
                </td>
                <td colspan="6"></td>

            </tr>
        </c:if>
        </tbody>
    </table>
</div>

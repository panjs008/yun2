<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
    var flag = ${fn:length(emkProOrderDetailEntities)};
    var currentFlag = ${fn:length(emkProOrderDetailEntities)};
    var colorJson = JSON.parse(${color});

    $('#addBtn').linkbutton({
        iconCls: 'icon-add'
    });
    $('#delBtn').linkbutton({
        iconCls: 'icon-remove'
    });
    $('#addBtn').bind('click', function(){
        flag++;

        if(flag>1 || ${param.proOrderId ne null}){
            $("html,body").animate({scrollTop:100},1);
        }
        var tr =  $("#add_jeecgOrderProduct_table_template tr").clone();
        $("#add_jeecgOrderProduct_table").append(tr);

        $("#add_jeecgOrderProduct_table").find("[id='color00']").attr("id","color"+flag);
        $("#add_jeecgOrderProduct_table").find("[id='colorNum00']").attr("id","colorNum"+flag);

        BindColorSelect("color"+flag, colorJson,"1","");
        /*$("#add_jeecgOrderProduct_table").find("[id='signPrice00']").attr("datatype","d");
        $("#add_jeecgOrderProduct_table").find("[id='signPrice00']").attr("id","signPrice"+flag);*/
        resetTrNum('add_jeecgOrderProduct_table');
        $("#orderMxListIDSR").val($("#mxtb").find("tr").length-1);
    });
    $('#delBtn').bind('click', function(){
        var chk_value =[];
        $("html,body").animate({scrollTop:200},1);
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
        <c:if test="${param.proOrderId eq null || param.proOrderId eq ''}">
            $('#addBtn').click();
        </c:if>
    });

</script>
<style>
    .table-c table{border-right:1px solid #ddd;border-bottom:1px solid #ddd}
    .table-c table td{border-left:1px solid #ddd;border-top:1px solid #ddd;height: 36px;}
</style>

<div style="padding: 3px; height: 25px; width:100%;margin-bottom:4px " class="datagrid-toolbar"><a id="addBtn" href="#"></a> <a id="delBtn" href="#"></a></div>
<%--<table border="0" cellpadding="2" cellspacing="0" id="jeecgOrderProduct_table">--%>
<input id="orderMxListIDSR" type="hidden" name="orderMxListIDSR" value="${fn:length(emkProOrderDetailEntities)}"/>
<!-- 添加明细模版-->
<table style="width:100%;display: none;border: 1px;" cellpadding="0" cellspacing="2" border="0">
    <tbody id="add_jeecgOrderProduct_table_template">
    <tr>
        <td align="center"><input style="width: 40px;" type="checkbox" name="ck" />
        <td align="center">
           <%-- <select name="orderMxList[#index#].color" style="width: 86%;" nullmsg="请输入颜色！" errormsg="请输入颜色" datatype="*">
                <c:forEach items="${colorList}" var="category">
                    <option value="${category.typecode}">${category.typename}</option>
                </c:forEach>
            </select>--%>
               <select id="color00" name="orderMxList[#index#].color" class="form-control select2" style="width:100px;">
                   <option value=''>请选择</option>
               </select>
               <input id="colorNum00" type="hidden"/>
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
    </tr>
    </tbody>

</table>
<div class="table-c">
    <table id="mxtb" width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#F8F8F8" style="height: 32px;" >
            <td align="center"  width="40" rowspan="2">序号</td>
            <td align="center"  width="150" rowspan="2">颜色</td>
            <td align="center"  width="150" colspan="11">尺码</td>
            <%--<td align="center"  width="150" rowspan="2">数量</td>--%>
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
        <tbody id="add_jeecgOrderProduct_table">
        <c:if test="${fn:length(emkProOrderDetailEntities)  > 0 }">
            <c:set var="totalA" value="0"/>
            <c:set var="totalB" value="0"/>
            <c:set var="totalC" value="0"/>
            <c:set var="totalD" value="0"/>
            <c:set var="totalE" value="0"/>
            <c:set var="totalF" value="0"/>
            <c:set var="totalG" value="0"/>
            <c:set var="totalH" value="0"/>
            <c:set var="totalI" value="0"/>
            <c:set var="totalJ" value="0"/>
            <c:set var="totalK" value="0"/>
            <c:forEach items="${emkProOrderDetailEntities}" var="poVal" varStatus="status">
                <c:set var="zjs" value="0"/>
                <c:set var="totalA" value="${totalA+(poVal.emkSizeTotalEntity.totalA ne '' ? poVal.emkSizeTotalEntity.totalA:0)}"/>
                <c:set var="totalB" value="${totalB+(poVal.emkSizeTotalEntity.totalB ne '' ? poVal.emkSizeTotalEntity.totalB:0)}"/>
                <c:set var="totalC" value="${totalC+(poVal.emkSizeTotalEntity.totalC ne '' ? poVal.emkSizeTotalEntity.totalC:0)}"/>
                <c:set var="totalD" value="${totalD+(poVal.emkSizeTotalEntity.totalD ne '' ? poVal.emkSizeTotalEntity.totalD:0)}"/>
                <c:set var="totalE" value="${totalE+(poVal.emkSizeTotalEntity.totalE ne '' ? poVal.emkSizeTotalEntity.totalE:0)}"/>
                <c:set var="totalF" value="${totalF+(poVal.emkSizeTotalEntity.totalF ne '' ? poVal.emkSizeTotalEntity.totalF:0)}"/>
                <c:set var="totalG" value="${totalG+(poVal.emkSizeTotalEntity.totalG ne '' ? poVal.emkSizeTotalEntity.totalG:0)}"/>
                <c:set var="totalH" value="${totalH+(poVal.emkSizeTotalEntity.totalH ne '' ? poVal.emkSizeTotalEntity.totalH:0)}"/>
                <c:set var="totalI" value="${totalI+(poVal.emkSizeTotalEntity.totalI ne '' ? poVal.emkSizeTotalEntity.totalI:0)}"/>
                <c:set var="totalJ" value="${totalJ+(poVal.emkSizeTotalEntity.totalJ ne '' ? poVal.emkSizeTotalEntity.totalJ:0)}"/>
                <c:set var="totalK" value="${totalK+(poVal.emkSizeTotalEntity.totalK ne '' ? poVal.emkSizeTotalEntity.totalK:0)}"/>
                <tr>
                    <td align="center"><input style="width: 40px;" type="checkbox" name="ck" value="${poVal.id}"/>
                    </td>
                    <td align="center">
                      <%--  <select name="orderMxList[${status.index}].color" style="width: 86%;" nullmsg="请输入颜色！" errormsg="请输入颜色" datatype="*">
                            <c:forEach items="${colorList}" var="category">
                                <option value="${category.typecode}" ${category.typecode eq poVal.color ? 'selected':''}>${category.typename}</option>
                            </c:forEach>
                        </select>--%>
                          <select id="color${status.index}" name="orderMxList[${status.index}].color" onchange="setColorNumItem(${status.index})" class="form-control select2" style="width:100px;">
                              <option value=''>请选择</option>
                          </select>
                          <script>
                              BindColorSelect("color${status.index}", colorJson,"1","${poVal.colorValue},${poVal.color}");
                          </script>
                          <input id="colorNum${status.index}" type="hidden"/>

                    </td>
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

                </tr>
                <c:set var="zjs" value="${zjs+totalA+totalB+totalC+totalD+totalE+totalF+totalH+totalI+totalJ+totalK}"/>

            </c:forEach>

        </c:if>
        </tbody>
        <tr>
            <td colspan="2"></td>
            <td align="center"><input  style="width: 86%;" type="text" value="${totalA eq 0 ? '':totalA}"></td>
            <td align="center"><input  style="width: 86%;" type="text" value="${totalB eq 0 ? '':totalB}"></td>
            <td align="center"><input  style="width: 86%;" type="text" value="${totalC eq 0 ? '':totalC}"></td>
            <td align="center"><input  style="width: 86%;" type="text" value="${totalD eq 0 ? '':totalD}"></td>
            <td align="center"><input  style="width: 86%;" type="text" value="${totalE eq 0 ? '':totalE}"></td>
            <td align="center"><input  style="width: 86%;" type="text" value="${totalF eq 0 ? '':totalF}"></td>
            <td align="center"><input  style="width: 86%;" type="text" value="${totalG eq 0 ? '':totalG}"></td>
            <td align="center"><input  style="width: 86%;" type="text" value="${totalH eq 0 ? '':totalH}"></td>
            <td align="center"><input  style="width: 86%;" type="text" value="${totalI eq 0 ? '':totalI}"></td>
            <td align="center"><input  style="width: 86%;" type="text" value="${totalJ eq 0 ? '':totalJ}"></td>
            <td align="center"><input  style="width: 86%;" type="text" value="${totalK eq 0 ? '':totalK}"></td>
            <td></td>
        </tr>
        <tr>
            <td colspan="2" align="center">总件数</td>
            <td align="center" colspan="11">${zjs}</td>
        </tr>
        </tbody>
    </table>
</div>


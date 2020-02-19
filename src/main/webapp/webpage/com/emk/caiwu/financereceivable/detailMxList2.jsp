<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
    var srflag2 = 0;
    var currentFlag2 = 0;


    $('#addBtnSR2').linkbutton({
        iconCls: 'icon-add'
    });
    $('#delBtn2').linkbutton({
        iconCls: 'icon-remove'
    });
    $('#addBtnSR2').bind('click', function(){
        srflag2++;
        var tr =  $("#add_jeecgOrderProduct_tableSR2_template tr").clone();
        $("#add_jeecgOrderProduct_tableSR22").append(tr);

        /*$("#add_jeecgOrderProduct_tableSR22").find("[id='signPrice00']").attr("datatype","d");
         $("#add_jeecgOrderProduct_tableSR22").find("[id='signPrice00']").attr("id","signPrice"+srflag2);*/
        resetTrNum('add_jeecgOrderProduct_tableSR22');
        $("#orderMxListIDSR2").val($("#mxtbSR2").find("tr").length-1);
    });
    $('#delBtn2').bind('click', function(){
        var chk_value =[];
        $('input[name="ck"]:checked').each(function(){
            chk_value.push($(this).val());
        });
        $("#add_jeecgOrderProduct_tableSR22").find("input:checked").parent().parent().remove();
        resetTrNum('add_jeecgOrderProduct_tableSR22');
        $("#orderMxListIDSR2").val($("#mxtbSR2").find("tr").length-1);

        if(chk_value.length>0){

        }

    });
    $(document).ready(function(){
        $(".datagrid-toolbar").parent().css("width","auto");
        <c:if test="${param.rId eq null || param.rId eq ''}">
            $('#addBtnSR2').click();
        </c:if>
    });

</script>
<style>
    .table-c table{border-right:1px solid #ddd;border-bottom:1px solid #ddd}
    .table-c table td{border-left:1px solid #ddd;border-top:1px solid #ddd;height: 36px;}
</style>
<!-- 添加明细模版-->
<table style="width:100%;display: none;border: 1px;" cellpadding="0" cellspacing="2" border="0">
    <tbody id="add_jeecgOrderProduct_tableSR22_template">
    <tr>
        <td align="center"><input style="width: 40px;" type="checkbox" name="ck" />
        <td align="center"><input id="tdNum00" nullmsg="请输入提单号！"  errormsg="请输入提单号" name="orderMxList[#index#].tdNum00" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="cargoNo00" nullmsg="请输入订舱通知单号！"  errormsg="请输入订舱通知单号" name="orderMxList[#index#].cargoNo00" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="outForumNo00" nullmsg="请输入出货通知单号！"  errormsg="请输入出货通知单号" name="orderMxList[#index#].outForumNo00" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="order2No00" nullmsg="请输入订单号！"  errormsg="请输入订单号" name="orderMxList[#index#].order2No00" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="atotalBox00" datatype="n"  nullmsg="请输入总箱数！"  errormsg="请输入整数" name="orderMxList[#index#].atotalBox00" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="asumVolume00" datatype="d" nullmsg="请输入总体积！"  errormsg="请输入数值型数据" name="orderMxList[#index#].asumVolume00" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="asumWeightMao00" datatype="d" nullmsg="请输入总毛重！"  errormsg="请输入数值型数据" name="orderMxList[#index#].asumWeightMao00" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="asumWeightJz00" datatype="d" nullmsg="请输入总净重！"  errormsg="请输入数值型数据" name="orderMxList[#index#].asumWeightJz00" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="cost00" datatype="d" nullmsg="请输入货代费用金额！"  errormsg="请输入数值型数据" name="orderMxList[#index#].cost00" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>

    </tr>
    </tbody>

</table>
<div style="padding: 3px; height: 25px; width:100%;margin-bottom:4px " class="datagrid-toolbar"><a id="addBtnSR2" href="#"></a> <a id="delBtn2" href="#"></a></div>
<%--<table border="0" cellpadding="2" cellspacing="0" id="jeecgOrderProduct_table">--%>
<input id="orderMxListIDSR2" type="hidden" name="orderMxListIDSR2" value="${fn:length(emkTdhdCostDetailEntities)}"/>
<div class="table-c">
    <table id="mxtbSR2" width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#F8F8F8" style="height: 32px;" >
            <td align="center"  width="40" >序号</td>
            <td align="center"  width="180">提单号</td>
            <td align="center"  width="180">订舱通知单号</td>
            <td align="center"  width="180">出货通知单号</td>
            <td align="center"  width="150">订单号</td>
            <td align="center"  width="70">总箱数</td>
            <td align="center"  width="80">总体积</td>
            <td align="center"  width="80">总毛重</td>
            <td align="center"  width="80">总净重</td>
            <td align="center"  width="130">货代费用金额</td>
        </tr>

        <tbody id="add_jeecgOrderProduct_tableSR2">
        <c:if test="${fn:length(emkTdhdCostDetailEntities)  > 0 }">
            <c:forEach items="${emkTdhdCostDetailEntities}" var="poVal" varStatus="status">
                <tr>
                    <td align="center"><input style="width: 40px;" type="checkbox" name="ck" value="${poVal.id}"/>
                    </td>
                    <td align="center"><input nullmsg="请输入提单号！"  errormsg="请输入提单号" value="${poVal.tdNum}" name="orderMxList[${status.index}].tdNum00" maxlength="100" type="text" value=""
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input  nullmsg="请输入订舱通知单号！"  errormsg="请输入订舱通知单号" value="${poVal.cargoNo}" name="orderMxList[${status.index}].cargoNo00" maxlength="100" type="text" value=""
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input  nullmsg="请输入出货通知单号！"  errormsg="请输入出货通知单号" value="${poVal.outForumNo}" name="orderMxList[${status.index}].outForumNo00" maxlength="100" type="text" value=""
                                               style="width: 86%;" ignore="ignore"></td>

                    <td align="center"><input  nullmsg="请输入订单号！"  errormsg="请输入订单号" value="${poVal.orderNo}" name="orderMxList[${status.index}].order2No00" maxlength="100" type="text" value=""
                                               style="width: 86%;" ignore="ignore"></td>

                    <td align="center"><input  nullmsg="请输入总箱数！"  datatype="n" errormsg="请输入整数" value="${poVal.sumBoxTotal}" name="orderMxList[${status.index}].atotalBox00" maxlength="100" type="text" value=""
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input  nullmsg="请输入总体积！"  datatype="d" errormsg="请输入数值型数据" value="${poVal.sumBoxVolume}" name="orderMxList[${status.index}].asumVolume00" maxlength="100" type="text" value=""
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input  nullmsg="请输入总毛重！"  datatype="d" errormsg="请输入数值型数据" value="${poVal.sumBoxMao}" name="orderMxList[${status.index}].asumWeightMao00" maxlength="100" type="text" value=""
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input  nullmsg="请输入总净重！"  datatype="d" errormsg="请输入数值型数据" value="${poVal.sumBoxJz}" name="orderMxList[${status.index}].asumWeightJz00" maxlength="100" type="text" value=""
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input datatype="d" nullmsg="请输入货代费用金额！"  errormsg="请输入数值型数据" value="${poVal.cost}"  name="orderMxList[${status.index}].cost00" maxlength="100" type="text" value=""
                                              style="width: 86%;" ignore="ignore"></td>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>
</div>


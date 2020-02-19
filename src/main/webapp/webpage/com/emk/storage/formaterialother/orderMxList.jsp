<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
    var flag = ${fn:length(emkFormaterailOtherDetailEntityList)};
    var currentFlag = ${fn:length(emkFormaterailOtherDetailEntityList)};

    $('#addBtn').linkbutton({
        iconCls: 'icon-add'
    });
    $('#delBtn').linkbutton({
        iconCls: 'icon-remove'
    });
    $('#addBtn').bind('click', function(){
        flag++;
        if(flag>1 || ${param.proOrderId ne null}){
            $("html,body").animate({scrollTop:400},1);
        }
        $("#seqNum").html(flag+parseInt($("#listSize").val()));

        var tr =  $("#add_jeecgOrderProduct_table_template tr").clone();
        $("#add_jeecgOrderProduct_table").append(tr);


        $("#add_jeecgOrderProduct_table").find("[id='total00']").attr("id","total"+flag);
        $("#add_jeecgOrderProduct_table").find("[id='price00']").attr("id","price"+flag);
        $("#add_jeecgOrderProduct_table").find("[id='money00']").attr("id","money"+flag);

        $("#price"+flag).attr("onkeyup","setMoney("+flag+")");
        $("#total"+flag).attr("onkeyup","setMoney("+flag+")");


        resetTrNum('add_jeecgOrderProduct_table');
        $("#orderMxListID").val($("#mxtb").find("tr").length-1);
    });
    $('#delBtn').bind('click', function(){
        var chk_value =[];
        $("html,body").animate({scrollTop:400},1);
        $('input[name="ck"]:checked').each(function(){
            chk_value.push($(this).val());
        });
        $("#add_jeecgOrderProduct_table").find("input:checked").parent().parent().remove();
        resetTrNum('add_jeecgOrderProduct_table');
        $("#orderMxListID").val($("#mxtb").find("tr").length-1);

        if(chk_value.length>0){

        }
    });

    function setMoney(f){
        var t = 0;
        t = parseFloat($("#total"+f).val())*parseFloat($("#price"+f).val());
        if(!isNaN(t)) {
            $("#money"+f).val(toDecimal(t));
        }
    }
    $(document).ready(function(){
        $(".datagrid-toolbar").parent().css("width","auto");
        //将表格的表头固定
        $("#jeecgOrderProduct_table").createhftable({
            height:'200px',
            width:'auto',
            fixFooter:false
        });
        <c:if test="${param.formaterialOtherId eq null || param.formaterialOtherId eq ''}">
            $('#addBtn').click();
        </c:if>
    });

</script>
<style>
    .table-c table{border-right:1px solid #ddd;border-bottom:1px solid #ddd}
    .table-c table td{border-left:1px solid #ddd;border-top:1px solid #ddd;height: 36px;}
</style>
<!-- 添加明细模版-->
<table style="width:100%;display: none;border: 1px;" cellpadding="0" cellspacing="2" border="0">
    <tbody id="add_jeecgOrderProduct_table_template">
    <tr>
        <td align="center"><input style="width: 20px;" type="checkbox" name="ck" /></td>
        <td align="center" width="40"><span id="seqNum"></span></td>
        <td align="center"><input  nullmsg="请输入物料名称！"  datatype="*"  errormsg="请输入物料名称" value="" name="orderMxList[#index#].wllx" maxlength="100" type="text"
                                   style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="price00" nullmsg="请输入单价！"  datatype="d"  errormsg="请输入数值" name="orderMxList[#index#].price" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="total00" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入数量" name="orderMxList[#index#].total" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="money00" nullmsg="请输入金额！"  datatype="d"  errormsg="请输入数值" name="orderMxList[#index#].money" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
    </tr>
    </tbody>

</table>
<div style="padding: 3px; height: 25px; width:100%;margin-bottom:4px " class="datagrid-toolbar"><a id="addBtn" href="#"></a> <a id="delBtn" href="#"></a></div>

<%--<table border="0" cellpadding="2" cellspacing="0" id="jeecgOrderProduct_table">--%>
<input id="listSize" type="hidden" value="${fn:length(emkFormaterailOtherDetailEntityList)}"/>
<input id="orderMxListID" type="hidden" name="dataRowsVal" value="${fn:length(emkFormaterailOtherDetailEntityList)}"/>
<div class="table-c" style="margin-top:5px;">
    <table id="mxtb" width="80%" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#F8F8F8" style="height: 32px;" >
            <td align="center"  width="20" ><input type="checkbox" onclick="selectAll(this.checked)" /></td>
            <td align="center" width="40" >序号</td>
            <td align="center"  width="140" >物料名称</td>
            <td align="center"  width="60"  >单价</td>
            <td align="center"  width="60"  >数量</td>
            <td align="center"  width="60"  >金额</td>
        </tr>
     
        <tbody id="add_jeecgOrderProduct_table">
        <c:if test="${fn:length(emkFormaterailOtherDetailEntityList)  > 0 }">
            <c:set var="zj" value="0"/>
            <c:forEach items="${emkFormaterailOtherDetailEntityList}" var="poVal" varStatus="status">
                <tr>
                    <td align="center"><input style="width: 20px;" type="checkbox" name="ck" value="${poVal.id}"/>
                    </td>
                    <td align="center" width="40">${status.index+1}</td>
                    <td align="center"><input  nullmsg="请输入物料名称！"  datatype="*"  errormsg="请输入物料名称" value="${poVal.wllx}" name="orderMxList[${status.index}].wllx" maxlength="100" type="text"
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="price${status.index}" nullmsg="请输入单价！"  datatype="d" onkeyup="setMoney(${status.index})"  errormsg="请输入数值" value="${poVal.price}"  name="orderMxList[${status.index}].price" maxlength="100" type="text"
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="total${status.index}" nullmsg="请输入数量！"  datatype="n" onkeyup="setMoney(${status.index})"  errormsg="请输入整数" value="${poVal.total}"  name="orderMxList[${status.index}].total" maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="money${status.index}" nullmsg="请输入金额！"  datatype="d"  errormsg="请输入数值" value="${poVal.money}"  name="orderMxList[${status.index}].money" maxlength="100" type="text"
                                               style="width: 86%;" ignore="ignore"></td>
                </tr>
                <c:set var="zj" value="${zj+poVal.money}"/>

            </c:forEach>

        </c:if>
        </tbody>

        <tr>
            <td  align="center"></td>
            <td  align="center"></td>
            <td  align="center">总计</td>
            <td  align="center" colspan="2"></td>
            <td align="center"><input  nullmsg="请输入金额！"  datatype="d"  errormsg="请输入数值" value="${zj}"   maxlength="100" type="text"
                                       style="width: 86%;" ignore="ignore"></td>
        </tr>
    </table>
</div>


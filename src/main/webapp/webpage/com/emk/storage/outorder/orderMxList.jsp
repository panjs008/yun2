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
            $("html,body").animate({scrollTop:400},1);
        }
        $("#seqNum").html(flag+parseInt($("#listSize").val()));

        var tr =  $("#add_jeecgOrderProduct_table_template tr").clone();
        $("#add_jeecgOrderProduct_table").append(tr);

        for(var i = 1 ; i < 24 ; i++){
            var code = "total"+String.fromCharCode(64+i);
            $("#add_jeecgOrderProduct_table").find('[id=\''+code+'\']').attr("id",code+flag);
            $("#"+code+flag).attr("onkeyup","setTotal("+flag+")");
        }

        $("#add_jeecgOrderProduct_table").find("[id='color00']").attr("id","color"+flag);
        $("#add_jeecgOrderProduct_table").find("[id='colorNum00']").attr("id","colorNum"+flag);
        $("#add_jeecgOrderProduct_table").find("[id='total00']").attr("id","total"+flag);
        $("#add_jeecgOrderProduct_table").find("[id='price00']").attr("id","price"+flag);
        $("#add_jeecgOrderProduct_table").find("[id='money00']").attr("id","money"+flag);

        BindColorSelect("color"+flag, colorJson,"1","");
        $("#color"+flag).attr("onchange","setColorNumItem("+flag+")");
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

    function setTotal(f){
        var t = 0;
        for(var i = 1 ; i < 24 ; i++){
            var code = "total"+String.fromCharCode(64+i);
            if($("#"+code+f).val() != '' && $("#"+code+f).val() != null){
                t += parseInt($("#"+code+f).val());
            }
        }
        if(!isNaN(t)) {
            $("#total"+f).val(t);
        }
        t = parseFloat($("#total"+f).val())*parseFloat($("#price"+f).val());
        if(!isNaN(t)) {
            $("#money"+f).val(toDecimal(t));
        }
    }
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
        <c:if test="${param.proOrderId eq null || param.proOrderId eq ''}">
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
        <td align="center"><input style="width: 40px;" type="checkbox" name="ck" /></td>
        <td align="center" width="40"><span id="seqNum"></span></td>
        <td align="center">
           <%-- <select id="color00" name="orderMxList[#index#].color" style="width: 86%;" nullmsg="请输入款号-颜色-内长！" errormsg="请输入款号-颜色-内长" datatype="*">
                <c:forEach items="${colorList}" var="category">
                    <option value="${category.typecode}">${category.typename}</option>
                </c:forEach>
            </select>--%>

            <select id="color00" name="orderMxList[#index#].color" class="form-control select2" style="width:150px;">
                <option value=''>请选择</option>
            </select>
        </td>

        <td align="center"><input id="totalA" nullmsg="请输入数量！" datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].totalA" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalB" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].totalB" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalC" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].totalC" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalD" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].totalD" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalE" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].totalE" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalF" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].totalF" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalG" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].totalG" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalH" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].totalH" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalI" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].totalI" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalJ" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].totalJ" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalK" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].totalK" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalL" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].totalL" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalM" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].totalM" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalN" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].totalN" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalO" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].totalO" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalP" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].totalP" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalQ" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].totalQ" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalR" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].totalR" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalS" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].totalS" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalT" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].totalT" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalU" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].totalU" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalV" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].totalV" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="total00" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].total" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="price00" nullmsg="请输入单价！"   datatype="d"  errormsg="请输入数值" name="orderMxList[#index#].price" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="money00" nullmsg="请输入金额！"  datatype="d"  errormsg="请输入数值" name="orderMxList[#index#].money" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
    </tr>
    </tbody>

</table>
<div style="padding: 3px; height: 25px; width:100%;margin-bottom:4px " class="datagrid-toolbar"><a id="addBtn" href="#"></a> <a id="delBtn" href="#"></a></div>

<%--<table border="0" cellpadding="2" cellspacing="0" id="jeecgOrderProduct_table">--%>
<input id="listSize" type="hidden" value="${fn:length(emkProOrderDetailEntities)}"/>
<input id="orderMxListID" type="hidden" name="dataRowsVal" value="${fn:length(emkProOrderDetailEntities)}"/>
<div class="table-c" style="margin-top:5px;">
    <table id="mxtb" width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#F8F8F8" style="height: 32px;" >
            <td align="center"  width="40" rowspan="2"><input type="checkbox" onclick="selectAll(this.checked)" /></td>
            <td align="center" width="40" rowspan="2">序号</td>
            <td align="center"  width="120" rowspan="2">款号-颜色-内长</td>
            <td align="center"  width="400" colspan="22" >尺码</td>
            <td align="center"  width="60"  rowspan="2">合计</td>
            <td align="center"  width="60"  rowspan="2">单价</td>
            <td align="center"  width="60"  rowspan="2">金额</td>
        </tr>
        <tr>
            <td align="center" ><input  value="${emkSizePage.sizeA eq null ? '27':emkSizePage.sizeA}" name="sizeA" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeB eq null ? '28':emkSizePage.sizeB}" name="sizeB" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeC eq null ? '29':emkSizePage.sizeC}" name="sizeC" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeD eq null ? '30':emkSizePage.sizeD}" name="sizeD" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeE eq null ? '31':emkSizePage.sizeE}" name="sizeE" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeF eq null ? '32':emkSizePage.sizeF}" name="sizeF" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeG eq null ? '33':emkSizePage.sizeG}" name="sizeG" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeH eq null ? '34':emkSizePage.sizeH}" name="sizeH" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeI eq null ? '36':emkSizePage.sizeI}" name="sizeI" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeJ eq null ? '38':emkSizePage.sizeJ}" name="sizeJ" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeK eq null ? '40':emkSizePage.sizeK}" name="sizeK" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeL eq null ? 'XS':emkSizePage.sizeL}" name="sizeL" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeM eq null ? 'S':emkSizePage.sizeM}" name="sizeM" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeN eq null ? 'M':emkSizePage.sizeN}" name="sizeN" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeO eq null ? 'L':emkSizePage.sizeO}" name="sizeO" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeP eq null ? 'XL':emkSizePage.sizeP}" name="sizeP" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeQ eq null ? 'XXL':emkSizePage.sizeQ}" name="sizeQ" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeR}" name="sizeR" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeS}" name="sizeS" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeT}" name="sizeT" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeU}" name="sizeU" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeV}" name="sizeV" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>

        </tr>
        <tbody id="add_jeecgOrderProduct_table">
        <c:if test="${fn:length(emkProOrderDetailEntities)  > 0 }">
            <c:set var="zjs" value="0"/>
            <c:set var="zj" value="0"/>

            <c:forEach items="${emkProOrderDetailEntities}" var="poVal" varStatus="status">
                <tr>
                    <td align="center"><input style="width: 40px;" type="checkbox" name="ck" value="${poVal.id}"/>
                    </td>
                    <td align="center" width="40">${status.index+1}</td>
                    <td align="center">
                        <select id="color${status.index}" name="orderMxList[${status.index}].color" onchange="setColorNumItem(${status.index})" class="form-control select2" style="width:150px;">
                            <option value=''>请选择</option>
                        </select>
                        <script>
                            BindColorSelect("color${status.index}", colorJson,"1","${poVal.colorValue}-${poVal.color}-${poVal.size}");
                        </script>
                    </td>

                    <td align="center"><input id="totalA${status.index}"  nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" onkeyup="setTotal(${status.index})" value="${poVal.emkSizeTotalEntity.totalA}" name="orderMxList[${status.index}].totalA" maxlength="100" type="text"
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalB${status.index}"  nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数"  onkeyup="setTotal(${status.index})" value="${poVal.emkSizeTotalEntity.totalB}" name="orderMxList[${status.index}].totalB" maxlength="100" type="text"
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalC${status.index}"  nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数"  onkeyup="setTotal(${status.index})" value="${poVal.emkSizeTotalEntity.totalC}" name="orderMxList[${status.index}].totalC" maxlength="100" type="text"
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalD${status.index}"  nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数"  onkeyup="setTotal(${status.index})" value="${poVal.emkSizeTotalEntity.totalD}" name="orderMxList[${status.index}].totalD" maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalE${status.index}" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数"   onkeyup="setTotal(${status.index})" value="${poVal.emkSizeTotalEntity.totalE}" name="orderMxList[${status.index}].totalE" maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalF${status.index}"  nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数"  onkeyup="setTotal(${status.index})" value="${poVal.emkSizeTotalEntity.totalF}" name="orderMxList[${status.index}].totalF" maxlength="100" type="text"
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalG${status.index}"  nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数"  onkeyup="setTotal(${status.index})" value="${poVal.emkSizeTotalEntity.totalG}" name="orderMxList[${status.index}].totalG" maxlength="100" type="text"
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalH${status.index}"  nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数"  onkeyup="setTotal(${status.index})" value="${poVal.emkSizeTotalEntity.totalH}" name="orderMxList[${status.index}].totalH" maxlength="100" type="text"
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalI${status.index}"  nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数"  onkeyup="setTotal(${status.index})" value="${poVal.emkSizeTotalEntity.totalI}" name="orderMxList[${status.index}].totalI" maxlength="100" type="text"
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalJ${status.index}"  nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数"  onkeyup="setTotal(${status.index})" value="${poVal.emkSizeTotalEntity.totalJ}" name="orderMxList[${status.index}].totalJ" maxlength="100" type="text"
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalK${status.index}"  nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数"  onkeyup="setTotal(${status.index})" value="${poVal.emkSizeTotalEntity.totalK}" name="orderMxList[${status.index}].totalK" maxlength="100" type="text"
                                               style="width: 86%;" ignore="ignore"></td>

                    <td align="center"><input id="totalL${status.index}" nullmsg="请输入数量！"  datatype="n"  onkeyup="setTotal(${status.index})" value="${poVal.emkSizeTotalEntity.totalL}" errormsg="请输入整数" name="orderMxList[${status.index}].totalL" maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalM${status.index}" nullmsg="请输入数量！"  datatype="n"  onkeyup="setTotal(${status.index})" value="${poVal.emkSizeTotalEntity.totalM}" errormsg="请输入整数" name="orderMxList[${status.index}].totalM" maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalN${status.index}" nullmsg="请输入数量！"  datatype="n"  onkeyup="setTotal(${status.index})" value="${poVal.emkSizeTotalEntity.totalN}" errormsg="请输入整数" name="orderMxList[${status.index}].totalN" maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalO${status.index}" nullmsg="请输入数量！"  datatype="n"  onkeyup="setTotal(${status.index})" value="${poVal.emkSizeTotalEntity.totalO}" errormsg="请输入整数" name="orderMxList[${status.index}].totalO" maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalP${status.index}" nullmsg="请输入数量！"  datatype="n"  onkeyup="setTotal(${status.index})" value="${poVal.emkSizeTotalEntity.totalP}" errormsg="请输入整数" name="orderMxList[${status.index}].totalP" maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalQ${status.index}" nullmsg="请输入数量！"  datatype="n"  onkeyup="setTotal(${status.index})" value="${poVal.emkSizeTotalEntity.totalQ}" errormsg="请输入整数" name="orderMxList[${status.index}].totalQ" maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalR${status.index}" nullmsg="请输入数量！"  datatype="n"  onkeyup="setTotal(${status.index})" value="${poVal.emkSizeTotalEntity.totalR}" errormsg="请输入整数" name="orderMxList[${status.index}].totalR" maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalS${status.index}" nullmsg="请输入数量！"  datatype="n"  onkeyup="setTotal(${status.index})" value="${poVal.emkSizeTotalEntity.totalS}" errormsg="请输入整数" name="orderMxList[${status.index}].totalS" maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalT${status.index}" nullmsg="请输入数量！"  datatype="n"  onkeyup="setTotal(${status.index})" value="${poVal.emkSizeTotalEntity.totalT}" errormsg="请输入整数" name="orderMxList[${status.index}].totalT" maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalU${status.index}" nullmsg="请输入数量！"  datatype="n"  onkeyup="setTotal(${status.index})" value="${poVal.emkSizeTotalEntity.totalU}" errormsg="请输入整数" name="orderMxList[${status.index}].totalU" maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalV${status.index}" nullmsg="请输入数量！"  datatype="n"  onkeyup="setTotal(${status.index})" value="${poVal.emkSizeTotalEntity.totalV}" errormsg="请输入整数" name="orderMxList[${status.index}].totalV" maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>

                    <td align="center"><input id="total${status.index}"   nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" value="${poVal.total}"  name="orderMxList[${status.index}].total" maxlength="100" type="text"
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="price${status.index}" nullmsg="请输入单价！"  datatype="d" onkeyup="setMoney(${status.index})"  errormsg="请输入数值" value="${poVal.price}"  name="orderMxList[${status.index}].price" maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="money${status.index}" nullmsg="请输入金额！"  datatype="d"  errormsg="请输入数值" value="${poVal.money}"  name="orderMxList[${status.index}].money" maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                </tr>
                <c:set var="zjs" value="${zjs+poVal.total}"/>
                <c:set var="zj" value="${zj+poVal.total*poVal.price}"/>

            </c:forEach>

        </c:if>
        </tbody>

        <tr>
            <td  align="center"></td>
            <td  align="center"></td>
            <td  align="center">总计</td>
            <td  align="center" colspan="22">&nbsp;</td>
            <td align="center"><input  nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" value="${zjs}"   maxlength="100" type="text"
                                       style="width: 86%;" ignore="ignore"></td>
            <td  align="center">&nbsp;</td>
            <td align="center"><input   value="${zj}"   maxlength="100" type="text"
                                       style="width: 86%;" ignore="ignore"></td>
        </tr>
    </table>
</div>


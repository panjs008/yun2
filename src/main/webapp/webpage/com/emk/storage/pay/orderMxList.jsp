<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
    var flag = ${fn:length(emkProOrderDetailEntities)};


    $(document).ready(function(){
        $(".datagrid-toolbar").parent().css("width","auto");
        //将表格的表头固定
        $("#jeecgOrderProduct_table").createhftable({
            height:'200px',
            width:'auto',
            fixFooter:false
        });

    });

    function finishOrder(){
        var index = layer.load(1, {
            skin:"layui-layer-sys1",
            shade: [0.1,'#fff'] //0.1透明度的白色背景
        });
        $("#add_jeecgOrderProduct_table").html("");
        $("#add_jeecgOrderProduct_tableformatrial").html("");

        $("#zjs").val("");
        $("#zj").val("");
        var total=0,zj=0;
        $.getJSON( "emkPayController.do?finishOrder&orderNo="+$("#orderNo").val(), function (data) {
            $.each(data.obj.emkOutOrderEntityList, function (i, item) {
                $("#color00").val(item.color_value+'-'+item.color+'-'+item.size);
                $("#total00").val(item.total);
                $("#price00").val(item.price);
                $("#money00").val(item.total*item.price);
                total += item.total;
                zj += item.total*item.price;
                $("#zjs").val(total);
                $("#zj").val(zj);

                $("#totalA").val(item.total_a != '0' ? item.total_a:'');
                $("#totalB").val(item.total_b != '0' ? item.total_b:'');
                $("#totalC").val(item.total_c != '0' ? item.total_c:'');
                $("#totalD").val(item.total_d != '0' ? item.total_d:'');
                $("#totalE").val(item.total_e != '0' ? item.total_e:'');
                $("#totalF").val(item.total_f != '0' ? item.total_f:'');
                $("#totalG").val(item.total_g != '0' ? item.total_g:'');
                $("#totalH").val(item.total_h != '0' ? item.total_h:'');
                $("#totalI").val(item.total_i != '0' ? item.total_i:'');
                $("#totalJ").val(item.total_j != '0' ? item.total_j:'');
                $("#totalK").val(item.total_k != '0' ? item.total_k:'');
                $("#totalL").val(item.total_l != '0' ? item.total_l:'');
                $("#totalM").val(item.total_m != '0' ? item.total_m:'');
                $("#totalN").val(item.total_n != '0' ? item.total_n:'');
                $("#totalO").val(item.total_o != '0' ? item.total_o:'');
                $("#totalP").val(item.total_p != '0' ? item.total_p:'');
                $("#totalQ").val(item.total_q != '0' ? item.total_q:'');
                $("#totalR").val(item.total_r != '0' ? item.total_r:'');
                $("#totalS").val(item.total_s != '0' ? item.total_s:'');
                $("#totalT").val(item.total_t != '0' ? item.total_u:'');
                $("#totalU").val(item.total_u != '0' ? item.total_u:'');
                $("#totalV").val(item.total_v != '0' ? item.total_v:'');
                $("#totalW").val(item.total_w != '0' ? item.total_w:'');
                flag++;
                $("#seqNum").html(flag);
                 var tr =  $("#add_jeecgOrderProduct_table_template tr").clone();
                 $("#add_jeecgOrderProduct_table").append(tr);
            });

            total=0,zj=0;
            $.each(data.obj.formatrailEntityList, function (i, item) {
                $("#color01").val(item.color_num+'-'+item.pm);
                $("#total01").val(item.total);
                $("#price01").val(item.price);
                $("#money01").val(item.total*item.price);
                total += item.total;
                zj += item.total*item.price;
                $("#zjs01").val(total);
                $("#zj01").val(zj);

                $("#totalA01").val(item.total_a != '0' ? item.total_a:'');
                $("#totalB01").val(item.total_b != '0' ? item.total_b:'');
                $("#totalC01").val(item.total_c != '0' ? item.total_c:'');
                $("#totalD01").val(item.total_d != '0' ? item.total_d:'');
                $("#totalE01").val(item.total_e != '0' ? item.total_e:'');
                $("#totalF01").val(item.total_f != '0' ? item.total_f:'');
                $("#totalG01").val(item.total_g != '0' ? item.total_g:'');
                $("#totalH01").val(item.total_h != '0' ? item.total_h:'');
                $("#totalI01").val(item.total_i != '0' ? item.total_i:'');
                $("#totalJ01").val(item.total_j != '0' ? item.total_j:'');
                $("#totalK01").val(item.total_k != '0' ? item.total_k:'');
                $("#totalL01").val(item.total_l != '0' ? item.total_l:'');
                $("#totalM01").val(item.total_m != '0' ? item.total_m:'');
                $("#totalN01").val(item.total_n != '0' ? item.total_n:'');
                $("#totalO01").val(item.total_o != '0' ? item.total_o:'');
                $("#totalP01").val(item.total_p != '0' ? item.total_p:'');

                formatrailflag++;
                $("#seqNum01").html(formatrailflag);

                var tr =  $("#add_jeecgOrderProduct_tableformatrial_template tr").clone();
                $("#add_jeecgOrderProduct_tableformatrial").append(tr);
            });

            total=0,zj=0;
            $.each(data.obj.otherEntityList, function (i, item) {
                $("#wllx02").val(item.wllx);
                $("#total02").val(item.total);
                $("#price02").val(item.price);
                $("#money02").val(item.total*item.price);
                zj += item.total*item.price;
                $("#zj02").val(zj);
                otherflag++;
                $("#seqNum02").html(otherflag);

                var tr =  $("#add_jeecgOrderProduct_tableother_template tr").clone();
                $("#add_jeecgOrderProduct_tableother").append(tr);
            });

        });
        layer.close(index);

    }

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

        <td align="center"><input id="color00"   maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalA"   maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalB"  maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalC"   maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalD"   maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalE"  maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalF"  maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalG"   maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalH"   maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalI"  maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalJ"  maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalK"   maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalL"   maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalM"   maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalN"  maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalO"  maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalP"   maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalQ"  maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalR"   maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalS"   maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalT"  maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalU"   maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalV"  maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="total00"  maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="price00"       maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="money00"      maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
    </tr>
    </tbody>

</table>
<div style="padding: 3px; height: 0px; width:100%;margin-bottom:4px " class="datagrid-toolbar"></div>

<input id="listSize" type="hidden" value="${fn:length(emkProOrderDetailEntities)}"/>
<input id="orderMxListID" type="hidden" name="dataRowsVal" value="${fn:length(emkProOrderDetailEntities)}"/>
<c:if test="${param.type eq 1}"><strong>已发货明细（扣除退货）</strong><br/></c:if>
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
            <td align="center" ><input  value="${emkSizePage.sizeA eq null ? '27':emkSizePage.sizeA}" name="sizeB" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
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
                    <td align="center"><input    maxlength="100" type="text"
                                                 style="width: 86%;" ignore="ignore" value="${poVal.color_value}-${poVal.color}-${poVal.size}"></td>
                    <td align="center"><input id="totalA${status.index}"    value="<fmt:parseNumber integerOnly="true" value="${poVal.total_a ne '0.0' ? poVal.total_a:''}" />"  maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalB${status.index}"     value="<fmt:parseNumber integerOnly="true" value="${poVal.total_b ne '0.0' ? poVal.total_b:''}" />" maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalC${status.index}"     value="<fmt:parseNumber integerOnly="true" value="${poVal.total_c ne '0.0' ? poVal.total_c:''}" />"  maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalD${status.index}"     value="<fmt:parseNumber integerOnly="true" value="${poVal.total_d ne '0.0' ? poVal.total_d:''}" />"  maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalE${status.index}"     value="<fmt:parseNumber integerOnly="true" value="${poVal.total_e ne '0.0' ? poVal.total_e:''}" />" maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalF${status.index}"     value="<fmt:parseNumber integerOnly="true" value="${poVal.total_f ne '0.0' ? poVal.total_f:''}" />"  maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalG${status.index}"     value="<fmt:parseNumber integerOnly="true" value="${poVal.total_g ne '0.0' ? poVal.total_g:''}" />"  maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalH${status.index}"     value="<fmt:parseNumber integerOnly="true" value="${poVal.total_h ne '0.0' ? poVal.total_h:''}" />"  maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalI${status.index}"     value="<fmt:parseNumber integerOnly="true" value="${poVal.total_i ne '0.0' ? poVal.total_i:''}" />"  maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalJ${status.index}"     value="<fmt:parseNumber integerOnly="true" value="${poVal.total_j ne '0.0' ? poVal.total_j:''}" />"  maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalK${status.index}"     value="<fmt:parseNumber integerOnly="true" value="${poVal.total_k ne '0.0' ? poVal.total_k:''}" />"  maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>

                    <td align="center"><input id="totalL${status.index}"      value="<fmt:parseNumber integerOnly="true" value="${poVal.total_l ne '0.0' ? poVal.total_l:''}" />" errormsg="请输入整数"  maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalM${status.index}"      value="<fmt:parseNumber integerOnly="true" value="${poVal.total_m ne '0.0' ? poVal.total_m:''}" />" errormsg="请输入整数"  maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalN${status.index}"      value="<fmt:parseNumber integerOnly="true" value="${poVal.total_n ne '0.0' ? poVal.total_n:''}" />" errormsg="请输入整数"  maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalO${status.index}"      value="<fmt:parseNumber integerOnly="true" value="${poVal.total_o ne '0.0' ? poVal.total_o:''}" />" errormsg="请输入整数"  maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalP${status.index}"      value="<fmt:parseNumber integerOnly="true" value="${poVal.total_p ne '0.0' ? poVal.total_p:''}" />" errormsg="请输入整数"  maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalQ${status.index}"      value="<fmt:parseNumber integerOnly="true" value="${poVal.total_q ne '0.0' ? poVal.total_q:''}" />" errormsg="请输入整数"  maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalR${status.index}"      value="<fmt:parseNumber integerOnly="true" value="${poVal.total_r ne '0.0' ? poVal.total_r:''}" />" errormsg="请输入整数"  maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalS${status.index}"      value="<fmt:parseNumber integerOnly="true" value="${poVal.total_s ne '0.0' ? poVal.total_s:''}" />" errormsg="请输入整数"  maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalT${status.index}"      value="<fmt:parseNumber integerOnly="true" value="${poVal.total_t ne '0.0' ? poVal.total_t:''}" />" errormsg="请输入整数"  maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalU${status.index}"      value="<fmt:parseNumber integerOnly="true" value="${poVal.total_u ne '0.0' ? poVal.total_u:''}" />" errormsg="请输入整数" maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalV${status.index}"      value="<fmt:parseNumber integerOnly="true" value="${poVal.total_v ne '0.0' ? poVal.total_v:''}" />" errormsg="请输入整数" maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>

                    <td align="center"><input id="total${status.index}"    value="${poVal.total}"  name="orderMxList[${status.index}].total" maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="price${status.index}"       value="${poVal.price}"  name="orderMxList[${status.index}].price" maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="money${status.index}"      value="${poVal.total*poVal.price}"  name="orderMxList[${status.index}].money" maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                </tr>
                <c:set var="zjs" value="${zjs+poVal.total}"/>
                <c:set var="zj" value="${zj+poVal.total*poVal.price}"/>

            </c:forEach>

        </c:if>
        </tbody>

        <tr>

            <td  align="center" colspan="25">总计</td>
            <td align="center"><input id="zjs"   value="${zjs}"   maxlength="100" type="text"
                                       style="width: 86%;" ignore="ignore"></td>
            <td  align="center">&nbsp;</td>
            <td align="center"><input  id="zj"  value="${zj}"   maxlength="100" type="text"
                                       style="width: 86%;" ignore="ignore"></td>
        </tr>
    </table>
</div>


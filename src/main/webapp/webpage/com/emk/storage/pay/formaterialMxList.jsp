<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
    var formatrailflag = ${fn:length(formatrailEntityList)};
    $(document).ready(function(){
        $(".datagrid-toolbar").parent().css("width","auto");
        //将表格的表头固定
        $("#jeecgOrderProduct_table").createhftable({
            height:'200px',
            width:'auto',
            fixFooter:false
        });

    });

</script>
<style>
    .table-c table{border-right:1px solid #ddd;border-bottom:1px solid #ddd}
    .table-c table td{border-left:1px solid #ddd;border-top:1px solid #ddd;height: 36px;}
</style>
<!-- 添加明细模版-->
<table style="width:100%;display: none;border: 1px;" cellpadding="0" cellspacing="2" border="0">
    <tbody id="add_jeecgOrderProduct_tableformatrial_template">
    <tr>
        <td align="center"><input style="width: 40px;" type="checkbox" name="ck" /></td>
        <td align="center" width="40"><span id="seqNum01"></span></td>

        <td align="center"><input id="color01"  name="orderMxList[#index#].color" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalA01"  name="orderMxList[#index#].totalA" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalB01"  name="orderMxList[#index#].totalB" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalC01"  name="orderMxList[#index#].totalC" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalD01"  name="orderMxList[#index#].totalD" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalE01"  name="orderMxList[#index#].totalE" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalF01"  name="orderMxList[#index#].totalF" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalG01"  name="orderMxList[#index#].totalG" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalH01"  name="orderMxList[#index#].totalH" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalI01"  name="orderMxList[#index#].totalI" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalJ01"  name="orderMxList[#index#].totalJ" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalK01"  name="orderMxList[#index#].totalK" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalL01"  name="orderMxList[#index#].totalL" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalM01"  name="orderMxList[#index#].totalM" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalN01"  name="orderMxList[#index#].totalN" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalO01"  name="orderMxList[#index#].totalO" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="totalP01"  name="orderMxList[#index#].totalP" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="total01"  maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="price01" name="orderMxList[#index#].price" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="money01" name="orderMxList[#index#].money" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
    </tr>
    </tbody>

</table>
<div style="padding: 3px; height: 0px; width:100%;margin-bottom:4px " class="datagrid-toolbar"></div>
<c:if test="${param.type eq 1}"><strong>叫布费用明细合计</strong><br/></c:if>
<div class="table-c" style="margin-top:5px;">
    <table  width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#F8F8F8" style="height: 32px;" >
            <td align="center"  width="40" rowspan="2"><input type="checkbox" onclick="selectAll(this.checked)" /></td>
            <td align="center" width="40" rowspan="2">序号</td>
            <td align="center"  width="120" rowspan="2">品名色号</td>
            <td align="center"  width="400" colspan="16" >尺码</td>
            <td align="center"  width="60"  rowspan="2">米数</td>
            <td align="center"  width="60"  rowspan="2">单价</td>
            <td align="center"  width="60"  rowspan="2">金额</td>
        </tr>
        <tr>
            <td align="center" ><input  value="${emkSizePage.sizeA eq null ? '1':emkSizePage.sizeA}" name="sizeA" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeB eq null ? '2':emkSizePage.sizeB}" name="sizeB" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeC eq null ? '3':emkSizePage.sizeC}" name="sizeC" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeD eq null ? '4':emkSizePage.sizeD}" name="sizeD" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeE eq null ? '5':emkSizePage.sizeE}" name="sizeE" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeF eq null ? '6':emkSizePage.sizeF}" name="sizeF" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeG eq null ? '7':emkSizePage.sizeG}" name="sizeG" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeH eq null ? '8':emkSizePage.sizeH}" name="sizeH" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeI eq null ? '9':emkSizePage.sizeI}" name="sizeI" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeJ eq null ? '10':emkSizePage.sizeJ}" name="sizeJ" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeK }" name="sizeK" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeL}" name="sizeL" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeM}" name="sizeM" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeN}" name="sizeN" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeO}" name="sizeO" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>
            <td align="center" ><input  value="${emkSizePage.sizeP}" name="sizeP" maxlength="100" type="text"  style="width: 80%;" ignore="ignore"></td>


        </tr>
        <tbody id="add_jeecgOrderProduct_tableformatrial">
        <c:if test="${fn:length(formatrailEntityList)  > 0 }">
            <c:set var="zjs" value="0"/>
            <c:set var="zj" value="0"/>

            <c:forEach items="${formatrailEntityList}" var="poVal" varStatus="status">
                <tr>
                    <td align="center"><input style="width: 40px;" type="checkbox" name="ck" value="${poVal.id}"/>
                    </td>
                    <td align="center" width="40">${status.index+1}</td>
                    <td align="center"><input    maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore" value="${poVal.color_num}-${poVal.pm}"></td>
                    <td align="center"><input id="totalA${status.index}"    value="<fmt:parseNumber integerOnly="true" value="${poVal.total_a ne '0.0' ? poVal.total_a:''}" />" name="orderMxList[${status.index}].totalA" maxlength="100" type="text"
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalB${status.index}"     value="<fmt:parseNumber integerOnly="true" value="${poVal.total_b ne '0.0' ? poVal.total_b:''}" />" name="orderMxList[${status.index}].totalB" maxlength="100" type="text"
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalC${status.index}"     value="<fmt:parseNumber integerOnly="true" value="${poVal.total_c ne '0.0' ? poVal.total_c:''}" />" name="orderMxList[${status.index}].totalC" maxlength="100" type="text"
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalD${status.index}"     value="<fmt:parseNumber integerOnly="true" value="${poVal.total_d ne '0.0' ? poVal.total_d:''}" />" name="orderMxList[${status.index}].totalD" maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalE${status.index}"     value="<fmt:parseNumber integerOnly="true" value="${poVal.total_e ne '0.0' ? poVal.total_e:''}" />" name="orderMxList[${status.index}].totalE" maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalF${status.index}"     value="<fmt:parseNumber integerOnly="true" value="${poVal.total_f ne '0.0' ? poVal.total_f:''}" />" name="orderMxList[${status.index}].totalF" maxlength="100" type="text"
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalG${status.index}"     value="<fmt:parseNumber integerOnly="true" value="${poVal.total_g ne '0.0' ? poVal.total_g:''}" />" name="orderMxList[${status.index}].totalG" maxlength="100" type="text"
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalH${status.index}"     value="<fmt:parseNumber integerOnly="true" value="${poVal.total_h ne '0.0' ? poVal.total_h:''}" />" name="orderMxList[${status.index}].totalH" maxlength="100" type="text"
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalI${status.index}"     value="<fmt:parseNumber integerOnly="true" value="${poVal.total_i ne '0.0' ? poVal.total_i:''}" />" name="orderMxList[${status.index}].totalI" maxlength="100" type="text"
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalJ${status.index}"     value="<fmt:parseNumber integerOnly="true" value="${poVal.total_j ne '0.0' ? poVal.total_j:''}" />" name="orderMxList[${status.index}].totalJ" maxlength="100" type="text"
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalK${status.index}"     value="<fmt:parseNumber integerOnly="true" value="${poVal.total_k ne '0.0' ? poVal.total_k:''}" />" name="orderMxList[${status.index}].totalK" maxlength="100" type="text"
                                               style="width: 86%;" ignore="ignore"></td>

                    <td align="center"><input id="totalL${status.index}"      value="<fmt:parseNumber integerOnly="true" value="${poVal.total_l ne '0.0' ? poVal.total_l:''}" />" errormsg="请输入整数" name="orderMxList[${status.index}].totalL" maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalM${status.index}"      value="<fmt:parseNumber integerOnly="true" value="${poVal.total_m ne '0.0' ? poVal.total_m:''}" />" errormsg="请输入整数" name="orderMxList[${status.index}].totalM" maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalN${status.index}"      value="<fmt:parseNumber integerOnly="true" value="${poVal.total_n ne '0.0' ? poVal.total_n:''}" />" errormsg="请输入整数" name="orderMxList[${status.index}].totalN" maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalO${status.index}"      value="<fmt:parseNumber integerOnly="true" value="${poVal.total_o ne '0.0' ? poVal.total_o:''}" />" errormsg="请输入整数" name="orderMxList[${status.index}].totalO" maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="totalP${status.index}"      value="<fmt:parseNumber integerOnly="true" value="${poVal.total_p ne '0.0' ? poVal.total_p:''}" />" errormsg="请输入整数" name="orderMxList[${status.index}].totalP" maxlength="100" type="text"
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

            <td  align="center" colspan="19">总计</td>
            <td align="center"><input id="zjs01"   value="${zjs}"   maxlength="100" type="text"
                                       style="width: 86%;" ignore="ignore"></td>
            <td  align="center">&nbsp;</td>
            <td align="center"><input  id="zj01"  value="${zj}"   maxlength="100" type="text"
                                       style="width: 86%;" ignore="ignore"></td>
        </tr>
    </table>
</div>


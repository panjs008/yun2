<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
    var otherflag = ${fn:length(otherEntityList)};
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
    <tbody id="add_jeecgOrderProduct_tableother_template">
    <tr>
        <td align="center"><input style="width: 40px;" type="checkbox" name="ck" /></td>
        <td align="center" width="40"><span id="seqNum02"></span></td>
        <td align="center"><input id="wllx02"  name="orderMxList[#index#].wllx" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="price02" name="orderMxList[#index#].price" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="total02" name="orderMxList[#index#].total" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="money02" name="orderMxList[#index#].money" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>
    </tr>
    </tbody>

</table>
<div style="padding: 3px; height: 0px; width:100%;margin-bottom:4px " class="datagrid-toolbar"></div>
<c:if test="${param.type eq 1}"><strong>其他物料明细合计</strong><br/></c:if>
<div class="table-c" style="margin-top:5px;">
    <table  width="50%" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#F8F8F8" style="height: 32px;" >
            <td align="center"  width="40" ><input type="checkbox" onclick="selectAll(this.checked)" /></td>
            <td align="center" width="40" >序号</td>
            <td align="center"  width="120" >物料名称</td>
            <td align="center"  width="60"  >单价</td>
            <td align="center"  width="60"  >数量</td>
            <td align="center"  width="60"  >金额</td>
        </tr>

        <tbody id="add_jeecgOrderProduct_tableother">
        <c:if test="${fn:length(otherEntityList)  > 0 }">
            <c:set var="zjs" value="0"/>
            <c:set var="zj" value="0"/>

            <c:forEach items="${otherEntityList}" var="poVal" varStatus="status">
                <tr>
                    <td align="center"><input style="width: 40px;" type="checkbox" name="ck" value="${poVal.id}"/>
                    </td>
                    <td align="center" width="40">${status.index+1}</td>
                    <td align="center"><input    maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore" value="${poVal.wllx}"></td>
                    <td align="center"><input id="price${status.index}"    value="${poVal.price}"  name="orderMxList[${status.index}].price" maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input id="total${status.index}"    value="${poVal.total}"  name="orderMxList[${status.index}].total" maxlength="100" type="text"
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
            <td  align="center" colspan="5">总计</td>
            <td align="center"><input  id="zj02"  value="${zj}"   maxlength="100" type="text"
                                       style="width: 86%;" ignore="ignore"></td>
        </tr>
    </table>
</div>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<script type="text/javascript">
    var flag = ${fn:length(rkglMxList)};
    var currentFlag = ${fn:length(rkglMxList)};
    $('#addBtn').linkbutton({
        iconCls: 'icon-add'
    });
    $('#delBtn').linkbutton({
        iconCls: 'icon-remove'
    });
    $('#addBtn').bind('click', function(){
        flag++;
        if(flag == 1){
            $("#seqNum").html(1);
        }else{
            $("#seqNum").html(flag);
        }
        var tr =  $("#add_jeecgOrderProduct_table_template tr").clone();
        $("#add_jeecgOrderProduct_table").append(tr);

        $("#add_jeecgOrderProduct_table").find("[id='proZnName00']").attr("id","proZnName"+flag);
        $("#add_jeecgOrderProduct_table").find("[id='proNum00']").attr("id","proNum"+flag);
        $("#add_jeecgOrderProduct_table").find("[id='standards00']").attr("id","standards"+flag);
        $("#add_jeecgOrderProduct_table").find("[id='brand00']").attr("id","brand"+flag);
        $("#add_jeecgOrderProduct_table").find("[id='unit00']").attr("id","unit"+flag);
        $("#add_jeecgOrderProduct_table").find("[id='scqy00']").attr("id","scqy"+flag);
        $("#add_jeecgOrderProduct_table").find("[id='lincese00']").attr("id","lincese"+flag);
        $("#add_jeecgOrderProduct_table").find("[id='zcNum00']").attr("id","zcNum"+flag);
        $("#add_jeecgOrderProduct_table").find("[id='scDate00']").attr("id","scDate"+flag);
        $("#add_jeecgOrderProduct_table").find("[id='limitDate00']").attr("id","limitDate"+flag);
        $("#add_jeecgOrderProduct_table").find("[id='betch00']").attr("id","betch"+flag);
        $("#add_jeecgOrderProduct_table").find("[id='price00']").attr("id","price"+flag);

        /*$("#add_jeecgOrderProduct_table").find("[id='signPrice00']").attr("datatype","d");
         $("#add_jeecgOrderProduct_table").find("[id='signPrice00']").attr("id","signPrice"+flag);*/
        resetTrNum('add_jeecgOrderProduct_table');
        //$("#proNum"+flag).attr("onclick","productLook("+flag+")");
        //$("#proZnName"+flag).attr("onclick","productLook("+flag+")");

        $("#proZnName"+flag).attr("ondblclick","productLook("+flag+")");
        $("#proZnName"+flag).attr("onkeyup","getProduct("+flag+")");
        $("#proNum"+flag).attr("onkeyup","getProductNum("+flag+")");

        $("#orderMxListID").val($("#mxtb").find("tr").length-1);
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
        <c:if test="${param.useId eq null || param.useId eq ''}">
        $('#addBtn').click();
        </c:if>
    });

    function  getProduct(fIndex){
        var pNum = $("#proZnName"+fIndex).val();
        if(pNum != null && pNum != ""){
            $.ajax({
                url : "emkProductController.do?getProductInfo&proNum="+pNum,
                type : 'post',
                cache : false,
                data: null,
                success : function(data) {
                    var d = $.parseJSON(data);
                    if (d.success) {
                        var obj = d.obj;
                        $("#proZnName"+fIndex).val(obj.proZnName);
                        $("#unit"+fIndex).val(obj.unit);
                        $("#standards"+fIndex).val(obj.standards);
                        $("#brand"+fIndex).val(obj.brand);
                        $("#scqy"+fIndex).val(obj.scqy);
                        $("#lincese"+fIndex).val(obj.lincese);
                        $("#zcNum"+fIndex).val(obj.zcNum);
                        $("#scDate"+fIndex).val(obj.scDate);
                        $("#limitDate"+fIndex).val(obj.limitDate);
                        $("#betch"+fIndex).val(obj.betch);
                        $("#price"+fIndex).val(obj.price);
                    }
                }
            });
        }

    }

    function  getProductNum(fIndex) {
        var pNum = $("#proNum" + fIndex).val();
        if (pNum != null && pNum != "" && pNum.length == 18) {
            $("#proNum" + fIndex).val(pNum.substr(pNum.indexOf('21')+2));
            //$("#scDate" + fIndex).val("20"+pNum.substr(2,2)+"-"+pNum.substr(4,2)+"-"+pNum.substr(6,2));
        }
    }
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
        <td align="center"><input id="proZnName00" nullmsg="请输入产品名称！"  errormsg="请输入产品名称"  name="orderMxList[#index#].proZnName" maxlength="100" type="text" value=""
                                  style="width: 92%;" ignore="ignore"></td>
        <td align="center"><input id="proNum00" nullmsg="请输入产品编号！"  errormsg="请输入产品编号" name="orderMxList[#index#].proNum" maxlength="100" type="text" value=""
                                  style="width: 92%;" ignore="ignore"></td>
        <td align="center"><input id="standards00" nullmsg="请输入规格！"  errormsg="请输入规格" name="orderMxList[#index#].standards" maxlength="100" type="text" value=""
                                  style="width: 92%;" ignore="ignore"></td>
        <td align="center"><input id="brand00" nullmsg="请输入型号！"  errormsg="请输入型号" name="orderMxList[#index#].brand" maxlength="100" type="text" value=""
                                  style="width: 92%;" ignore="ignore"></td>
        <td align="center"><input id="unit00" nullmsg="请输入单位！"  errormsg="请输入单位" name="orderMxList[#index#].unit" maxlength="100" type="text" value=""
                                  style="width: 70%;" ignore="ignore"></td>
        <td align="center"><input id="scqy00" nullmsg="请输入生产企业！"  errormsg="请输入生产企业" name="orderMxList[#index#].scqy" maxlength="100" type="text" value=""
                                  style="width: 92%;" ignore="ignore"></td>
        <td align="center"><input id="lincese00" nullmsg="请输入许可证号！"  errormsg="请输入许可证号" name="orderMxList[#index#].lincese" maxlength="100" type="text" value=""
                                  style="width: 92%;" ignore="ignore"></td>
        <td align="center"><input id="zcNum00" nullmsg="请输入注册证号！"  errormsg="请输入注册证号" name="orderMxList[#index#].zcNum" maxlength="100" type="text" value=""
                                  style="width: 92%;" ignore="ignore"></td>
        <td align="center"><input id="scDate00" nullmsg="请输入生产日期！" readonly onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  class="Wdate" errormsg="请输入生产日期" name="orderMxList[#index#].scDate" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="limitDate00" nullmsg="请输入有效日期！" readonly onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" errormsg="请输入有效日期" name="orderMxList[#index#].limitDate" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="betch00" nullmsg="请输入批号！"  errormsg="请输入批号" name="orderMxList[#index#].betch" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
        <td align="center"><input id="price00" nullmsg="请输入单价！" datatype="d"  errormsg="请输入单价" name="orderMxList[#index#].price" maxlength="100" type="text" value=""
                                  style="width: 86%;" ignore="ignore"></td>
    </tr>
    </tbody>

</table>
<div style="display:none">
    <input id="proNum" name="proNum" type="text"/>
    <input id="proType" name="proType" type="text" />
    <input id="proTypeName" name="proTypeName" type="text" />
    <input id="proZnName" name="proZnName" type="text" />
    <input id="standards" name="standards" type="text" />
    <input id="brand" name="brand" type="text" />
    <input id="unit" name="unit" type="text" />
    <input id="scqy" name="scqy" type="text" />
    <input id="lincese" name="lincese" type="text" />
    <input id="zcNum" name="zcNum" type="text" />
    <input id="scDate" name="scDate" type="text" />
    <input id="limitDate" name="limitDate" type="text" />
    <input id="betch" name="betch" type="text" />
    <input id="price" name="price" type="text" />

    <input id="id" name="id" type="text" />
    <t:choose  hiddenName="id"  hiddenid="id" url="emkStorageController.do?proSelectForCustom" name="emkStorageList" width="820px" height="500px"
               icon="icon-search" title="选择产品" textname="id,proNum,proType,proTypeName,proZnName,standards,brand,unit,scqy,lincese,zcNum,scDate,limitDate,betch,price" isclear="true" isInit="true"></t:choose>
</div>
<div style="padding: 3px; height: 25px; width:100%;margin-bottom:4px " class="datagrid-toolbar"><a id="addBtn" href="#"></a> <a id="delBtn" href="#"></a></div>
<%--<table border="0" cellpadding="2" cellspacing="0" id="jeecgOrderProduct_table">--%>
<input id="orderMxListID" type="hidden" name="orderMxListID" value="${fn:length(rkglMxList)}"/>
<div class="table-c" >
    <table id="mxtb" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#F8F8F8" style="height: 32px;" >
            <td align="center"  width="25" ><input type="checkbox" onclick="selectAll(this.checked)"/></td>
            <td align="center"  width="40">序号</td>
            <td align="center"  width="150">产品名称</td>
            <td align="center"  width="100">产品编号</td>
            <td align="center"  width="140">规格</td>
            <td align="center"  width="140">型号</td>
            <td align="center"  width="45">单位</td>
            <td align="center"  width="150">生产企业</td>
            <td align="center"  width="130">许可证号</td>
            <td align="center"  width="130">注册证号</td>
            <td align="center"  width="100">生产日期</td>
            <td align="center"  width="100">有效日期</td>
            <td align="center"  width="100">批号</td>
            <td align="center"  width="60">单价</td>

        </tr>
        <tbody id="add_jeecgOrderProduct_table">
        <c:if test="${fn:length(rkglMxList)  > 0 }">
            <c:forEach items="${rkglMxList}" var="poVal" varStatus="status">
                <tr>
                    <td align="center"><input style="width: 25px;" type="checkbox" name="ck" value="${poVal.id}"/>
                    </td>
                    <td align="center" width="35">${status.index+1}</td>
                    <td align="center"><input  nullmsg="请输入产品名称！" id="proZnName${status.index}" ondblclick="productLook(${status.index});" onkeyup="getProduct(${status.index})"  errormsg="请输入产品名称" value="${poVal.proZnName}" name="orderMxList[${status.index}].proZnName" maxlength="100" type="text" value=""
                                               style="width: 92%;" ignore="ignore"></td>
                    <td align="center"><input  nullmsg="请输入产品编号！" id="proNum${status.index}" onkeyup="getProductNum(${status.index})"  errormsg="请输入产品编号" value="${poVal.proNum}" name="orderMxList[${status.index}].proNum" maxlength="100" type="text" value=""
                                               style="width: 92%;" ignore="ignore"></td>
                    <td align="center"><input  nullmsg="请输入规格！"  id="standards${status.index}" errormsg="请输入规格" value="${poVal.standards}" name="orderMxList[${status.index}].standards" maxlength="100" type="text" value=""
                                               style="width: 93%;" ignore="ignore"></td>
                    <td align="center"><input  nullmsg="请输入型号！"  id="brand${status.index}" errormsg="请输入型号" value="${poVal.brand}" name="orderMxList[${status.index}].brand" maxlength="100" type="text" value=""
                                               style="width: 92%;" ignore="ignore"></td>
                    <td align="center"><input nullmsg="请输入单位！" id="unit${status.index}" errormsg="请输入单位" value="${poVal.unit}" name="orderMxList[${status.index}].unit" maxlength="100" type="text" value=""
                                              style="width: 70%;" ignore="ignore"></td>
                    <td align="center"><input nullmsg="请输入生产企业！" id="scqy${status.index}" errormsg="请输入生产企业" value="${poVal.scqy}" name="orderMxList[${status.index}].scqy" maxlength="100" type="text" value=""
                                              style="width: 92%;" ignore="ignore"></td>
                    <td align="center"><input nullmsg="请输入许可证号！" id="lincese${status.index}"  errormsg="请输入许可证号" value="${poVal.lincese}" name="orderMxList[${status.index}].lincese" maxlength="100" type="text" value=""
                                              style="width: 92%;" ignore="ignore"></td>
                    <td align="center"><input nullmsg="请输入注册证号！" id="zcNum${status.index}"  errormsg="请输入注册证号" value="${poVal.zcNum}" name="orderMxList[${status.index}].zcNum" maxlength="100" type="text" value=""
                                              style="width: 92%;" ignore="ignore"></td>
                    <td align="center"><input  nullmsg="请输入生产日期！" id="scDate${status.index}" readonly onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  value="${poVal.scDate}" class="Wdate" errormsg="请输入生产日期" name="orderMxList[${status.index}].scDate" maxlength="100" type="text" value=""
                                               style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input i nullmsg="请输入有效日期！" id="limitDate${status.index}" readonly onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${poVal.limitDate}" class="Wdate" errormsg="请输入有效日期" name="orderMxList[${status.index}].limitDate" maxlength="100" type="text" value=""
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input nullmsg="请输入批号！"  id="betch${status.index}" errormsg="请输入批号" value="${poVal.betch}" name="orderMxList[${status.index}].betch" maxlength="100" type="text" value=""
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center"><input nullmsg="请输入单价！" datatype="d" id="price${status.index}" errormsg="请输入单价" value="${poVal.price}" name="orderMxList[${status.index}].price" maxlength="100" type="text" value=""
                                              style="width: 86%;" ignore="ignore"></td>
                </tr>

            </c:forEach>
        </c:if>
        </tbody>
    </table>
</div>
<script type="text/javascript">
    function productLook(indexFlag){
        currentFlag = indexFlag;
        $("#chkInfoForPro").click();
    }

    function returnToVal(){
        var a1 =  $("#proNum").val().split(",");
        var a2 =  $("#proZnName").val().split(",");
        var a3 =  $("#unit").val().split(",");
        var a4 =  $("#brand").val().split(",");
        var a5 =  $("#scqy").val().split(",");
        var a6 =  $("#lincese").val().split(",");
        var a7 =  $("#zcNum").val().split(",");
        var a8 =  $("#scDate").val().split(",");
        var a9 =  $("#limitDate").val().split(",");
        var a10 =  $("#betch").val().split(",");
        var a11 =  $("#price").val().split(",");
        var a12 =  $("#standards").val().split(",");

        if(flag == 1){
            flag = 1;
            $("#add_jeecgOrderProduct_table").html("");
        }
        for(var i = 0 ; i < a2.length ; i++){
            $('#addBtn').click();
        }

        for(var i = 0 ; i < a2.length ; i++){
            $("#proNum"+(flag-a1.length+i+1)).val(a1[i]);
            $("#proZnName"+(flag-a2.length+i+1)).val(a2[i]);
            $("#unit"+(flag-a3.length+i+1)).val(a3[i]);
            $("#standards"+(flag-a12.length+i+1)).val(a12[i]);
            $("#brand"+(flag-a4.length+i+1)).val(a4[i]);
            $("#scqy"+(flag-a5.length+i+1)).val(a5[i]);
            $("#lincese"+(flag-a6.length+i+1)).val(a6[i]);
            $("#zcNum"+(flag-a7.length+i+1)).val(a7[i]);
            $("#scDate"+(flag-a8.length+i+1)).val(a8[i]);
            $("#limitDate"+(flag-a9.length+i+1)).val(a9[i]);
            $("#betch"+(flag-a10.length+i+1)).val(a10[i]);
            $("#price"+(flag-a11.length+i+1)).val(a11[i]);
        }


    }
</script>


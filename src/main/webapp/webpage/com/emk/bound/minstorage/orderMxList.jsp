<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<script type="text/javascript">
    var flag = ${fn:length(rkglMxList)};
    var currentFlag = ${fn:length(rkglMxList)};
    var returnCount = ${fn:length(rkglMxList)};
    $('#addBtn').linkbutton({
        iconCls: 'icon-add'
    });
    $('#copyBtn').linkbutton({
        iconCls: 'icon-save'
    });
    $('#delBtn').linkbutton({
        iconCls: 'icon-remove'
    });
    $('#setBtn').linkbutton({
        iconCls: 'icon-comturn'
    });
    $('#setBtn2').linkbutton({
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
    $("#storageNameId").change(function(){
        var itemarr = $("#storageNameId").val().split(","); //字符分割

        $("#storageId").val(itemarr[0]);
        $("#storageName").val(itemarr[1]);
        if($("#storageId").val()){
            selectPosition();
        }
    });
    function  selectPosition(){
        var htm = "";
        if(!isNaN(flag)) {
            for(var ii = 0 ; ii <= flag ; ii++){
                $("#a01a09a05"+ii).empty();
            }
            $.ajax({
                url : "emkStorageSetController.do?findPositionList&storageId="+$("#storageId").val(),
                type : 'post',
                cache : false,
                success : function(data) {
                    var d = $.parseJSON(data);
                    if(d.obj){
                        htm += "<option value=''></option>";
                        $.each(d.obj, function (i, item) {
                            htm += "<option value='" + item.id + "'>" + item.positionName + "</option>";
                        });
                        for(var ii = 0 ; ii <= flag ; ii++){
                            $("#a01a09a05"+ii).append(htm);
                            $("#a01a09a05"+ii).val($("#a01a09a05H"+ii).val());
                        }
                    }
                }
            });
        }

    }
    $('#addBtn').bind('click', function(){
        addTr();
    });
    $('#setBtn').bind('click', function(){
        $.dialog({
            content: 'url:hmCategoryController.do?for&forColumn&parentCode=A01A03',
            zIndex: getzIndex(),
            title : '表头列配置',
            cache:false,
            lock : true,
            width: 500,
            height: 580,
            cancelVal: '关闭',
            cancel: function(){
                refreshT();
            },
        });
    });
    $('#setBtn2').bind('click', function(){
        $.dialog({
            content: 'url:hmCategoryController.do?for&forColumn&parentCode=A01A09',
            zIndex: getzIndex(),
            title : '明细列配置',
            cache:false,
            lock : true,
            width: 500,
            height: 580,
            cancelVal: '关闭',
            cancel: function(){
                refreshT();
            },
        });
    });
    function refreshT(){
        $.ajax({
            url : "emkMInStorageController.do?setHeightSession&headTb="+headTb+"&footTb="+footTb,
            type : 'post',
            cache : false,
            success : function(data) {
                location.reload();
            }
        });

    }
    function addTr(){
        flag++;
        if(flag == 1){
            $("#seqNum").html(1);
        }else{
            $("#seqNum").html(flag);
        }
        var tr =  $("#add_jeecgOrderProduct_table_template tr").clone();
        $("#add_jeecgOrderProduct_table").append(tr);
        $("#add_jeecgOrderProduct_table").find("[id='ck00']").attr("id","ck"+flag);

        <c:forEach var="list" items="${categoryEntities}" varStatus="status">
            $("#add_jeecgOrderProduct_table").find("[id='${list.code}00']").attr("id","${list.code}"+flag);
            <c:if test="${list.column_type eq '2'}">
                $("#${list.code}"+flag).attr("datatype","n");
            </c:if>
            <c:if test="${list.column_type eq '6'}">
                $("#${list.code}"+flag).attr("datatype","d");
            </c:if>
            <c:if test="${list.required eq '0'}">
                $("#${list.code}"+flag).attr("datatype","*");
            </c:if>
            <%--<c:if test="${list.required ne '0'}">
                $("#${list.code}"+flag).attr("ignore","ignore");
            </c:if>--%>
            $("#${list.code}"+flag).attr("ignore","ignore");

            <%--<c:if test="${list.code ne 'a01a09a07' && list.code ne 'a01a09a02'  && list.code ne 'a01a09a11'}">
                $("#${list.code}"+flag).attr("ignore","ignore");
            </c:if>--%>

        </c:forEach>
        resetTrNum('add_jeecgOrderProduct_table');

        $("#ck"+flag).val(flag);
        $("#a01a09a07"+flag).attr("ondblclick","productLook("+flag+")");
        if(flag == 1){
            $("#a01a09a07"+flag).focus();
        }
        $("#a01a09a07"+flag).attr("onkeyup","setrequired("+flag+")");

        $("#a01a09a12"+flag).attr("onkeyup","setMoney("+flag+");setTotalAndMoney()");
        $("#a01a09a01"+flag).attr("onkeyup","setMoney("+flag+");setTotalAndMoney()");
        $("#a01a09a02"+flag).attr("onkeyup","setMoney("+flag+");setTotalAndMoney()");
        $("#a01a09a28"+flag).attr("onkeyup","setMoney("+flag+");");

        $("#a01a09a03"+flag).attr("onkeyup","setTotalAndMoney()");

        $("#orderMxListID").val($("#mxtb").find("tr").length-1);

        $("input").change(function()
        {
            $(this).css("border","");
        });
        $("input").bind({focus:function()
        {
            $(this).addClass("color");
            $("#mxtb input[type=text]").css("border","0");
            $("#mxtb select").css("border","0");

        },
            blur:function()
            {
                $(this).removeClass("color");
            }});
        $("input").keydown(function(event)
        {
            /*$(this).css("outline","none");
            $(this).css("border","");*/
            var thisinput = $(this)[0];
            getfocus(thisinput);
        });
    }
    function setrequired(index){
        <c:forEach var="list" items="${categoryEntities}" varStatus="status">
            <c:if test="${list.required eq '0'}">
                $("#${list.code}"+index).removeAttr("ignore");
            </c:if>
        </c:forEach>
    }
    $('#copyBtn').bind('click', function(){
        if($("input[type='checkbox']").is(':checked')){
            $('input[name="ck"]:checked').each(function(){
                var posi = $(this).val();
                addTr();
                <c:forEach var="list" items="${categoryEntities}" varStatus="status">
                    $("#${list.code}"+flag).val($("#${list.code}"+posi).val());
                </c:forEach>
                $("#proZnName"+flag).val($("#proZnName"+posi).val());
                //$("#proNum"+flag).val($("#proNum"+posi).val());
                $("#standards"+flag).val($("#standards"+posi).val());
                $("#brand"+flag).val($("#brand"+posi).val());
                $("#unit"+flag).val($("#unit"+posi).val());
            });
        }else{
            tip("请勾选行复制");
        }
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
        <c:if test="${param.inStorageId eq null || param.inStorageId eq ''}">
            for(var i = 1 ; i <=9 ; i++){
                $('#addBtn').click();
            }
        </c:if>
        selectPosition();
    });

    function setTotalAndMoney(){
        var total = 0,money = 0;
        for(var zz = 0 ; zz <= flag ; zz++){
            if($("#a01a09a02"+zz).val()){
                total += parseInt($("#a01a09a02"+zz).val());
            }
            if($("#a01a09a03"+zz).val()){
                money += parseFloat($("#a01a09a03"+zz).val());
            }
        }
        if(!isNaN(total)) {
            $("#a01a09a02Boot").html("&nbsp;"+total);
            $("#total").val(total);
        }
        if(!isNaN(money)) {
            $("#a01a09a03Boot").html("&nbsp;"+toDecimal(money));
            $("#money").val(toDecimal(money));
            $("#yfMoney").val(toDecimal(money));
        }
    }

    function setMoney(fIndex){
        var money = 0,tax = 0,zkprice=0;
        if($("#a01a09a02"+fIndex).val()){
            money = parseFloat($("#a01a09a02"+fIndex).val())*parseFloat($("#a01a09a12"+fIndex).val());
        }
        if($("#a01a09a01"+fIndex).val()){
            tax = parseFloat($("#a01a09a01"+fIndex).val())*money;
            money = (parseFloat($("#a01a09a01"+fIndex).val())+1)*money;
        }
        if($("#a01a09a28"+fIndex).val()){
            zkprice = parseFloat($("#a01a09a28"+fIndex).val())*money;
        }
        if(!isNaN(tax)) {
            $("#a01a09a27"+fIndex).val(toDecimal(tax));
        }
        if(!isNaN(tax)) {
            $("#a01a09a26"+fIndex).val(toDecimal(zkprice));
        }
        if(!isNaN(money)) {
            $("#a01a09a03"+fIndex).val(toDecimal(money));
            if($("#a01a09a26"+fIndex).val()){
                $("#yfMoney").val(toDecimal(zkprice));
            }else{
                $("#yfMoney").val(toDecimal(money));
            }
            setPay();
        }

    }



    function  getProduct(fIndex) {
        var pNum = $("#proZnName" + fIndex).val();
        if (pNum != null && pNum != "") {
            $.ajax({
                url: "emkProductController.do?getProductInfo&proNum=" + pNum,
                type: 'post',
                cache: false,
                data: null,
                success: function (data) {
                    var d = $.parseJSON(data);
                    if (d.success) {
                        var obj = d.obj;
                        $("#proZnName" + fIndex).val(obj.proZnName);
                        $("#unit" + fIndex).val(obj.unit);
                        $("#standards" + fIndex).val(obj.standards);
                        $("#brand" + fIndex).val(obj.brand);
                        $("#scqy" + fIndex).val(obj.scqy);
                        $("#lincese" + fIndex).val(obj.lincese);
                        $("#zcNum" + fIndex).val(obj.zcNum);
                        $("#scDate" + fIndex).val(obj.scDate);
                        $("#limitDate" + fIndex).val(obj.limitDate);
                        $("#betch" + fIndex).val(obj.betch);
                        $("#cytj" + fIndex).val(obj.cytj);
                    }
                }
            });
        }
    }

    function  getProductNum(fIndex) {
        var pNum = $("#proNum" + fIndex).val();
        if (pNum != null && pNum != "" && pNum.length == 18) {
            $("#proNum" + fIndex).val(pNum.substr(pNum.indexOf('21')+2));
        }
    }
    //回车和小键盘切换input焦点
    function getfocus(thisinput) {
        var inputs = document.getElementsByTagName("input");
        //if(event.keyCode == 13 || event.keyCode == 40) {
        if(event.keyCode == 40) {
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
            $("#mxtb select").css("border","0");

        });
        $("input").bind({focus:function()
        {
            $(this).addClass("color");
            $("#mxtb input[type=text]").css("border","0");
            $("#mxtb select").css("border","0");

        }, blur:function()
            {
                $(this).removeClass("color");
                $("#mxtb input[type=text]").css("border","0");
                $("#mxtb select").css("border","0");


            }});
        $("input").keydown(function(event)
        {
            var thisinput = $(this)[0];
            getfocus(thisinput);
            $("#mxtb input[type=text]").css("border","0");
            $("#mxtb select").css("border","0");


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

        <c:forEach var="list" items="${categoryEntities}" varStatus="status">
            <td align="center" style="display: ${list.is_show eq '0' ? '':'none'};">
                <c:if test="${list.code eq 'a01a09a05'}">
                    <select id="${list.code}00" name="orderMxList[#index#].${list.code}" style="width:100px;border: 0">
                        <option value=''>请选择</option>
                    </select>
                </c:if>
                <c:if test="${list.code ne 'a01a09a05'}">
                    <input id="${list.code}00" nullmsg="请输入${list.name}！" errormsg="请输入${list.name}" name="orderMxList[#index#].${list.code}" maxlength="100" type="text"
                           style="width: ${(list.column_type eq '0' || list.column_type eq '2'  || list.column_type eq '6') ? 86:94}%;border:0;" />
                </c:if>

            </td>
        </c:forEach>
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
    <input id="costPrice" name="costPrice" type="text" />
    <input id="sellPrice" name="sellPrice" type="text" />

    <c:forEach var="list" items="${productcategoryEntities}" varStatus="status">
            <input id="${list.code}" name="${list.code}" type="text"/>
        </c:forEach>
    <input id="id" name="id" type="text" />
    <t:choose  hiddenName="id"  hiddenid="id" url="emkProductController.do?proSelect&selectType=0" name="emkProductList" width="900px" height="500px"
               icon="icon-search" title="选择产品" textname="id,proType,proNum,proTypeName,proZnName,standards,brand,unit,costPrice,sellPrice,${columns}" isclear="true" isInit="true"></t:choose>
</div>

<div style="padding: 3px; height: 29px; width:100%;margin-bottom:4px;margin-top: 2px; " class="datagrid-toolbar"><a id="addBtn" href="#" title="新增">新增</a><a id="copyBtn" href="#" title="复制行">复制行</a><a id="delBtn" href="#" title="删除">删除</a> <%--<a id="refreshBtn" href="#" title="刷新">刷新</a>--%> <a id="setBtn" href="#" title="表头列配置">表头列</a>  <a id="setBtn2" href="#" title="明细列配置">列配置</a></div>
<%--<table border="0" cellpadding="2" cellspacing="0" id="jeecgOrderProduct_table">--%>
<input id="orderMxListID" type="hidden" name="orderMxListID" value="${fn:length(rkglMxList)}"/>
<div class="table-c" >
    <table id="mxtb" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#F8F8F8" style="height: 32px;" >
            <td align="center"  width="25" ><input type="checkbox" onclick="selectAll(this.checked)"/></td>
            <td align="center"  width="40">序号</td>

            <c:forEach var="cateList" items="${categoryEntities}" varStatus="status">
                <c:if test="${cateList.code eq 'a01a09a11'}">
                    <td  align="center"  width="30" style="display: ${cateList.is_show eq '0' ? '':'none'};">${cateList.name}</td>
                </c:if>
                <c:if test="${cateList.code ne 'a01a09a11'}">
                    <td  align="center"  style="display: ${cateList.is_show eq '0' ? '':'none'};" width="${(cateList.column_type eq '4') ? '160':((cateList.column_type eq '0' || cateList.column_type eq '2') || cateList.column_type eq '2' || cateList.column_type eq '6') ? '60':'140'}">${cateList.name}</td>
                </c:if>
            </c:forEach>
        </tr>
        <tbody id="add_jeecgOrderProduct_table">
        <c:if test="${fn:length(rkglMxList)  > 0 }">
            <c:set var="sl" value="0"/>
            <c:set var="zj" value="0"/>
            <c:forEach items="${rkglMxList}" var="poVal" varStatus="status">
                <tr>
                    <td align="center"><input style="width: 25px;" id="ck${status.index}" type="checkbox" name="ck" value="${status.index}"/>
                    </td>
                    <td align="center" width="35">${status.index+1}<input style="width: 25px;" type="hidden" name="orderMxList[${status.index}].id" value="${poVal.id}"/></td>

                    <c:forEach var="list" items="${categoryEntities}" varStatus="catestatus">
                        <td align="center" style="display: ${list.is_show eq '0' ? '':'none'};">
                            <c:if test="${list.code eq 'a01a09a02'}">
                                <c:set var="sl" value="${sl+poVal[list.code]}"/>
                            </c:if>
                            <c:if test="${list.code eq 'a01a09a03'}">
                                <c:set var="zj" value="${zj+poVal[list.code]}"/>
                            </c:if>
                            <c:if test="${list.code eq 'a01a09a05'}">
                                <input type="hidden" id="a01a09a05H${status.index}" value="${poVal.a01a09a05}"/>
                                <select id="a01a09a05${status.index}" name="orderMxList[${status.index}].${list.code}" value="${poVal[list.code]}" style="width:100px;border:0">
                                    <option value=''>请选择</option>
                                </select>
                            </c:if>
                            <c:if test="${list.code ne 'a01a09a05'}">
                                <input id="${list.code}${status.index}" nullmsg="请输入${list.name}！"  <c:if test="${list.code eq 'a01a09a02' || list.code eq 'a01a09a03' || list.code eq 'a01a09a01' || list.code eq 'a01a09a12' || list.code eq 'a01a09a28'}">onkeyup="setMoney(${status.index});setTotalAndMoney()" </c:if>  datatype="${list.column_type eq '2' ? 'n':list.column_type eq '6' ? 'd':'*'}"   errormsg="请输入${list.name}" name="orderMxList[${status.index}].${list.code}" maxlength="100" type="text"
                                       style="width: ${(list.column_type eq '0' || list.column_type eq '2') ? 86:94}%;border:0;" value="${poVal[list.code]}" <c:if test="${list.required ne '0'}">ignore="ignore"</c:if>/>
                            </c:if>

                        </td>
                    </c:forEach>
                </tr>

            </c:forEach>
        </c:if>
        </tbody>
        <tr>
            <td  align="center"></td>
            <td  align="center"></td>
            <c:forEach var="list" items="${categoryEntities}" varStatus="catestatus">
                <td align="left" style="display: ${list.is_show eq '0' ? '':'none'};">
                        <span id="${list.code}Boot" style="color:#0000FF ">
                            <c:if test="${list.code eq 'a01a09a02'}">
                                &nbsp;${sl}
                            </c:if>
                             <c:if test="${list.code eq 'a01a09a03'}">
                                 &nbsp;${zj}
                             </c:if>
                        </span>
                </td>
            </c:forEach>
        </tr>

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
        var a5 =  $("#costPrice").val().split(",");

        var a6;
        if($("#a01a01a01").val()){
            a6 =  $("#a01a01a01").val().split(",");
        }
        var a7;
        if($("#a01a01a12").val()){
            a7 =  $("#a01a01a12").val().split(",");
        }
        var a8;
        if($("#a01a01a13").val()){
            a8 =  $("#a01a01a13").val().split(",");
        }
        var a9;
        if($("#a01a01a14").val()){
            a9 =  $("#a01a01a14").val().split(",");
        }
        var a10;
        if($("#a01a01a15").val()){
            a10 =  $("#a01a01a15").val().split(",");
        }
        var a11;
        if($("#a01a01a16").val()){
            a11 =  $("#a01a01a16").val().split(",");
        }
        var a12 =  $("#standards").val().split(",");
        var a13;
        if($("#a01a01a17").val()){
            a13 =  $("#a01a01a17").val().split(",");
        }
        var a14 =  $("#sellPrice").val().split(",");

        if(flag == 1){
            flag = 0;
            $("#add_jeecgOrderProduct_table").html("");
        }


        for(var i = 0 ; i < a2.length ; i++){
            $("#a01a09a08"+(returnCount+i+1)).val(a1[i]);
            $("#a01a09a07"+(returnCount+i+1)).val(a2[i]);
            $("#a01a09a11"+(returnCount+i+1)).val(a3[i]);
            $("#a01a09a09"+(returnCount+i+1)).val(a12[i]);
            $("#a01a09a10"+(returnCount+i+1)).val(a4[i]);
            $("#a01a09a12"+(returnCount+i+1)).val(a5[i]);


            if($("#a01a01a12").val()){
                $("#a01a09a15"+(returnCount+i+1)).val(a7[i]);
            }
            if($("#a01a01a13").val()){
                $("#a01a09a16"+(returnCount+i+1)).val(a8[i]);
            }
            if($("#a01a01a14").val()){
                $("#a01a09a17"+(returnCount+i+1)).val(a9[i]);
            }
            if($("#a01a01a15").val()){
                $("#a01a09a18"+(returnCount+i+1)).val(a10[i]);
            }
            if($("#a01a01a16").val()){
                $("#a01a09a19"+(returnCount+i+1)).val(a11[i]);
            }
            if($("#a01a01a17").val()){
                $("#a01a09a20"+(returnCount+i+1)).val(a13[i]);
            }
            if($("#a01a01a01").val()){
                $("#a01a09a06"+(returnCount+i+1)).val(a6[i]);
            }
            $("#a01a09a21"+(returnCount+i+1)).val(a14[i]);

            $("#a01a09a07"+(returnCount+i+1)).removeAttr("ignore");
            $("#a01a09a08"+(returnCount+i+1)).removeAttr("ignore");
            $("#a01a09a10"+(returnCount+i+1)).removeAttr("ignore");
            $("#a01a09a12"+(returnCount+i+1)).removeAttr("ignore");
            $("#a01a09a02"+(returnCount+i+1)).removeAttr("ignore");
            $("#a01a09a03"+(returnCount+i+1)).removeAttr("ignore");

            $("#a01a09a08"+(returnCount+i+1)).attr("readonly","readonly");
            $("#a01a09a07"+(returnCount+i+1)).attr("readonly","readonly");
            $("#a01a09a11"+(returnCount+i+1)).attr("readonly","readonly");
            $("#a01a09a09"+(returnCount+i+1)).attr("readonly","readonly");
            $("#a01a09a10"+(returnCount+i+1)).attr("readonly","readonly");

        }
        returnCount += a2.length;

        <%--<c:forEach var="list" items="${categoryEntities}" varStatus="status">--%>
            <%--var arr =  $('#${list.code}').val().split(",");--%>
            <%--for(var i = 0 ; i < a2.length ; i++){--%>
                <%--$('#${list.code}'+(flag-a2.length+i+1)).val(arr[i]);--%>
            <%--}--%>
        <%--</c:forEach>--%>
        selectPosition();
    }
    </script>


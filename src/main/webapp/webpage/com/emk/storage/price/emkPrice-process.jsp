<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>报修单</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script>
        $(document).ready(function(){
            var height =window.top.document.body.offsetHeight*0.79;
            //$("#proFrm").css("height",height);
            $("#priceFrm0").css("height",height);
            $("#priceFrm").css("height",height);
            $("#proFrm").css("height",height);

        });

        function resetTrNum(tableId) {
            $tbody = $("#"+tableId+"");
            $tbody.find('>tr').each(function(i){
                $(':input, select', this).each(function(){
                    var $this = $(this), name = $this.attr('name'), val = $this.val();
                    if(name!=null && name != 'ck'){
                        if (name.indexOf("#index#") >= 0){
                            $this.attr("name",name.replace('#index#',i));
                        }else{
                            var s = name.indexOf("[");
                            var e = name.indexOf("]");
                            var new_name = name.substring(s+1,e);
                            $this.attr("name",name.replace(new_name,i));
                        }
                    }
                });
            });
        }
        function toDecimal(x) {
            if(!x){
                return x;
            }
            var result = parseFloat(x);
            if (isNaN(result)) {
                return x;
            }
            result = Math.round(x * 100) / 100;
            return result;
        }
        function selectAll(selectStatus){//传入参数（全选框的选中状态）
            //根据name属性获取到单选框的input，使用each方法循环设置所有单选框的选中状态
            if(selectStatus){
                $("input[name='ck']").each(function(i,n){
                    n.checked = true;
                });
            }else{
                $("input[name='ck']").each(function(i,n){
                    n.checked = false;
                });
            }
        }
        function setYongliang(ii){
            if($("#base").contents().find("#gyzl").val() == 'wufeng'){
                var yongliangV = toDecimal((parseFloat($("#sunhaoPrecent"+ii).val())+1)*parseFloat($('#precent'+ii).val())/100);
                $("#yongliang"+ii).val(yongliangV);
            }
        }
        function setPrice(ii){
            var chengbenV = toDecimal((parseFloat($("#sunhaoPrecent"+ii).val())+1)*parseFloat($('#yongliang'+ii).val()))*parseFloat($('#signPrice'+ii).val());
            if(!isNaN(chengbenV)){
                $("#chengben"+ii).val(toDecimal(chengbenV));
            }
        }
        function setPrice2(ii){
            var chengbenV = toDecimal((parseFloat($("#bsunhaoPrecent"+ii).val())+1)*parseFloat($('#byongliang'+ii).val()))*parseFloat($('#bsignPrice'+ii).val());
            if(!isNaN(chengbenV)){
                $("#bchengben"+ii).val(toDecimal(chengbenV));
            }

        }
        function setPrice3(ii){
            var chengbenV = toDecimal((parseFloat($("#csunhaoPrecent"+ii).val())+1)*parseFloat($('#cyongliang'+ii).val()))*parseFloat($('#csignPrice'+ii).val());
            if(!isNaN(chengbenV)){
                $("#cchengben"+ii).val(toDecimal(chengbenV));
            }
        }
        function setGxTotal(ii){
            var chengbenV = toDecimal(28800/parseFloat($('#gxdjhs'+ii).val()));
            if(!isNaN(chengbenV)){
                $("#gxproductTotal"+ii).val(toDecimal(chengbenV));
            }
        }
        function setRanPrice(ii){
            var chengbenV = toDecimal(parseFloat($('#rsdj'+ii).val())*parseFloat($('#rskz'+ii).val()))/1000;
            if(!isNaN(chengbenV)){
                $("#rscb"+ii).val(toDecimal(chengbenV));
            }
        }
        function setZjPrice(ii){
            var chengbenV = toDecimal(parseFloat($('#zjrsdj'+ii).val())*parseFloat($('#zjrskz'+ii).val()))/1000;
            if(!isNaN(chengbenV)){
                $("#zjrscb"+ii).val(toDecimal(chengbenV));
            }
        }
        function setXjkz(){
            $("#rskz00").val($("#xjkz").val());
            $("#zjrskz00").val($("#xjkz").val());
        }
        function connactPriceFrm(){
           /* var val = $('input:radio[name="isPass"]:checked').val();
            if(val == null){
                //tip("报价选项卡中的操作类型必填");
                tip("操作类型必填");
                return false;
            }*/
            var baseformArray =  $("#tabfrm").contents().find("#formpb").serializeArray();
            baseformArray = baseformArray.concat($("#priceFrm").serializeArray());
            baseformArray = baseformArray.concat($("#tabfrm").contents().find("#ylfrm").serializeArray());
            baseformArray = baseformArray.concat($("#tabfrm").contents().find("#fzfrm").serializeArray());
            baseformArray = baseformArray.concat($("#tabfrm").contents().find("#bzfrm").serializeArray());
            baseformArray = baseformArray.concat($("#tabfrm").contents().find("#gxfrm").serializeArray());
            baseformArray = baseformArray.concat($("#tabfrm").contents().find("#ranfrm").serializeArray());
            baseformArray = baseformArray.concat($("#tabfrm").contents().find("#zjfrm").serializeArray());

            baseformArray = baseformArray.concat($("#tabfrm").contents().find("#yinfrm").serializeArray());
            baseformArray = baseformArray.concat($("#tabfrm").contents().find("#formmange").serializeArray());
            baseformArray = baseformArray.concat($("#tabfrm").contents().find("#formprice").serializeArray());
            console.log($("#isSave").val());
            $.ajax({
                url : "emkPriceController.do?doSubmit&id=${param.id}",
                type : 'post',
                cache : false,
                data: baseformArray,
                success : function(data) {
                    var d = $.parseJSON(data);
                    var msg = d.msg;
                    tip(msg);
                    if (d.success) {
                        W.document.location.reload(true);
                    }
                }
            });
        }
    </script>

</head>
<body>

<t:tabs id="priceTabId" iframe="false" heigth="${param.hVal}" tabPosition="top" fit="true" >
    <%--<c:if test="${ROLE.rolecode eq 'cw'}">
        <t:tab title="任务处理" id="orderFrm0"  heigth="600px"  width="100%" iframe="emkPriceController.do?goWork&id=${param.id}" icon="fa fa-crosshairs"></t:tab>
        <t:tab title="报价单" id="priceFrm0"  heigth="${param.hVal}"  width="100%" href="emkPriceController.do?goTab1&hVal2=${param.hVal2}&hVal=${param.hVal}&id=${param.id}" icon="fa fa-calendar"></t:tab>
    </c:if>
    <c:if test="${ROLE.rolecode ne 'cw'}">
        <t:tab title="任务处理" id="orderFrm"  heigth="600px"  width="100%" href="emkPriceController.do?goWork&node=${param.node}&id=${param.id}" icon="fa fa-crosshairs"></t:tab>
        <t:tab title="报价单" id="priceFrm"  heigth="${param.hVal}"  width="100%" iframe="emkPriceController.do?goUpdate2&hVal2=${param.hVal2}&hVal=${param.hVal}&id=${param.id}" icon="fa fa-calendar"></t:tab>
    </c:if>--%>
    <t:tab title="任务处理" id="orderFrm0"  heigth="600px"  width="100%" href="emkPriceController.do?goWork&node=${param.node}&id=${param.id}" icon="fa fa-crosshairs"></t:tab>
    <t:tab title="报价单" id="priceFrm"  heigth="${param.hVal}"  width="100%" href="emkPriceController.do?goUpdate2&hVal2=${param.hVal2}&hVal=${param.hVal}&id=${param.id}" icon="fa fa-calendar"></t:tab>
    <t:tab title="流程图" id="proFrm"  heigth="600px" width="100%" icon="fa fa-sitemap" iframe="flowController.do?process&processUrl=com/emk/storage/price/process&id=${param.id}"></t:tab>
</t:tabs>

</body>


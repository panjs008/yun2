<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript" src="js/ajaxfileupload.js"></script>

<script type="text/javascript">
    var qaflag = ${fn:length(emkCancelQaEntityList)};
    var qacurrentFlag = ${fn:length(emkCancelQaEntityList)};

    $('#qaaddBtn').linkbutton({
        iconCls: 'icon-add'
    });
    $('#qadelBtn').linkbutton({
        iconCls: 'icon-remove'
    });
    $('#qaaddBtn').bind('click', function(){
        qaflag++;
        if(qaflag>1 || ${param.proOrderId ne null}){
            $("html,body").animate({scrollTop:400},1);
        }
        $("#qaseqNum").html(qaflag+parseInt($("#qalistSize").val()));

        var tr =  $("#qaadd_jeecgOrderProduct_table_template tr").clone();
        $("#qaadd_jeecgOrderProduct_table").append(tr);


        $("#qaadd_jeecgOrderProduct_table").find("[id='question00']").attr("id","question"+qaflag);
        $("#qaadd_jeecgOrderProduct_table").find("[id='saveFileName00']").attr("id","saveFileName"+qaflag);
        $("#qaadd_jeecgOrderProduct_table").find("[id='fileNameUrl00']").attr("id","fileNameUrl"+qaflag);
        $("#qaadd_jeecgOrderProduct_table").find("[id='fileName00']").attr("id","fileName"+qaflag);
        $("#qaadd_jeecgOrderProduct_table").find("[id='backuploadFile00']").attr("id","backuploadFile"+qaflag);
        $("#qaadd_jeecgOrderProduct_table").find("[id='backuploadFileId00']").attr("id","backuploadFile"+qaflag+"Id");

        $("#backuploadFile"+qaflag).attr("onchange","saveFile2('uploadControl.do?upload&fileDir=qa','backuploadFile"+qaflag+"','fileName"+qaflag+"','formobj','saveFileName"+qaflag+"','fileNameUrl"+qaflag+"','qa')");
        $("#fileName"+qaflag).attr("onclick","uploadClick('backuploadFile"+qaflag+"')");

        resetTrNum('qaadd_jeecgOrderProduct_table');
        $("#qaorderMxListID").val($("#mxtb").find("tr").length-1);
    });
    $('#qadelBtn').bind('click', function(){
        var chk_value =[];
        $("html,body").animate({scrollTop:400},1);
        $('input[name="ck"]:checked').each(function(){
            chk_value.push($(this).val());
        });
        $("#qaadd_jeecgOrderProduct_table").find("input:checked").parent().parent().remove();
        resetTrNum('qaadd_jeecgOrderProduct_table');
        $("#qaorderMxListID").val($("#mxtb").find("tr").length-1);

        if(chk_value.length>0){

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
        <c:if test="${param.cancelId eq null || param.cancelId eq ''}">
            $('#qaaddBtn').click();
        </c:if>
    });

</script>
<style>
    .table-c table{border-right:1px solid #ddd;border-bottom:1px solid #ddd}
    .table-c table td{border-left:1px solid #ddd;border-top:1px solid #ddd;height: 36px;}
</style>
<!-- 添加明细模版-->
<table style="width:100%;display: none;border: 1px;" cellpadding="0" cellspacing="2" border="0">
    <tbody id="qaadd_jeecgOrderProduct_table_template">
    <tr>
        <td align="center"><input style="width: 20px;" type="checkbox" name="ck" /></td>
        <td align="center" width="20"><span id="qaseqNum"></span></td>
        <td align="center"><input id="question00" nullmsg="请输入问题描述！" datatype="*"  errormsg="请输入问题描述" name="orderMxList[#index#].question" maxlength="100" type="text"
                                  style="width: 96%;" ignore="ignore"></td>
        <td align="center">
            <input type="hidden" id="saveFileName00" name="orderMxList[#index#].saveFileName">
            <input type="hidden" id="fileNameUrl00" name="orderMxList[#index#].fileNameUrl">
            <input name="files" id="backuploadFile00"  style="width:300px;display: none" type="file" accept=".png,.gif,.jpg,.jpeg"  />
            <input id="fileName00" type="text" name="orderMxList[#index#].fileName" style="width: 70%;" readonly >
            <span id="backuploadFileId00"></span>
        </td>
        <td align="center"><input id="totalQa00" nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" name="orderMxList[#index#].totalQa" maxlength="100" type="text"
                                  style="width: 86%;" ignore="ignore"></td>

    </tr>
    </tbody>

</table>
<div style="padding: 3px; height: 25px; width:100%;margin-bottom:4px " class="datagrid-toolbar"><a id="qaaddBtn" href="#"></a> <a id="qadelBtn" href="#"></a></div>

<%--<table border="0" cellpadding="2" cellspacing="0" id="jeecgOrderProduct_table">--%>
<input id="qalistSize" type="hidden" value="${fn:length(emkCancelQaEntityList)}"/>
<input id="qaorderMxListID" type="hidden" name="qadataRowsVal" value="${fn:length(emkCancelQaEntityList)}"/>
<div class="table-c" style="margin-top:5px;">
    <table id="mxtb" width="80%" border="0" cellspacing="0" cellpadding="0">
        <tr bgcolor="#F8F8F8" style="height: 32px;" >
            <td align="center"  width="20" ><input type="checkbox" onclick="selectAll(this.checked)" /></td>
            <td align="center" width="20" >序号</td>
            <td align="center"  width="150" >问题描述</td>
            <td align="center"  width="90" >图片</td>
            <td align="center"  width="60" >数量</td>
        </tr>

        <tbody id="qaadd_jeecgOrderProduct_table">
        <c:if test="${fn:length(emkCancelQaEntityList)  > 0 }">
            <c:set var="zjs" value="0"/>

            <c:forEach items="${emkCancelQaEntityList}" var="poVal" varStatus="status">
                <tr>
                    <td align="center"><input style="width: 40px;" type="checkbox" name="ck" value="${poVal.id}"/>
                    </td>
                    <td align="center" width="40">${status.index+1}</td>
                    <td align="center"><input  nullmsg="请输入问题描述！"  datatype="*"  errormsg="请输入问题描述"  value="${poVal.question}" name="orderMxList[${status.index}].question" maxlength="100" type="text"
                                              style="width: 86%;" ignore="ignore"></td>
                    <td align="center">
                        <input type="hidden" id="saveFileName${status.index}" name="orderMxList[${status.index}].saveFileName" value="${poVal.saveFileName}" >
                        <input type="hidden" id="fileNameUrl${status.index}" name="orderMxList[${status.index}].fileNameUrl" value="${poVal.fileNameUrl}" >
                        <input name="files" id="backuploadFile${status.index}" onchange="saveFile2('uploadControl.do?upload&fileDir=qa','backuploadFile${status.index}','fileName${status.index}','formobj','saveFileName${status.index}','fileNameUrl${status.index}','qa');"  style="width:300px;display: none" type="file" accept=".png,.gif,.jpg,.jpeg"  />
                        <input id="fileName${status.index}" type="text" name="orderMxList[${status.index}].fileName" onclick="uploadClick('backuploadFile${status.index}')" value="${poVal.fileName}" style="width: 70%;" readonly >
                        <span id="backuploadFileId${status.index}">
                             <c:if test="${poVal.fileNameUrl ne '' && poVal.fileNameUrl ne null}">
                                 <input class="btn" type="button" value="查看" onclick="findDetail('${webRoot}/imp/qa/${poVal.saveFileName }')" style="background:#18a689 none repeat scroll 0 0;height:28px;width:50px !important;border-radius:5px;color: #fff;" >
                             </c:if>
                        </span>

                    </td>
                    <td align="center"><input nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数"  value="${poVal.total}" name="orderMxList[${status.index}].totalQa" maxlength="100" type="text"
                                               style="width: 86%;" ignore="ignore"></td>

                </tr>
                <c:set var="zjs" value="${zjs+poVal.total}"/>

            </c:forEach>

        </c:if>
        </tbody>

        <tr>
            <td  align="center" colspan="4">总计</td>
            <td align="center"><input  nullmsg="请输入数量！"  datatype="n"  errormsg="请输入整数" value="${zjs}"   maxlength="100" type="text"
                                       style="width: 86%;" ignore="ignore"></td>

        </tr>
    </table>
</div>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="hmSalaryList" checkbox="false"  sortName="deptName,workName,groupName" pageSize="20" pagination="true" fitColumns="false" title="" actionUrl="hmSalaryController.do?datagrid&levalStaff=${param.levalStaff}" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="工号"  hidden="true"   field="workNo"  queryMode="single"  width="80"></t:dgCol>
            <t:dgCol title="月份"  rowspan="3" frozenColumn="true"  field="month" query="true"    queryMode="single"  width="60"></t:dgCol>
            <t:dgCol title="" rowspan="3" field="jiab" hidden="true" formatterjs="setCqsj" queryMode="single"  width="100"></t:dgCol>
            <%--<t:dgCol title="部门" rowspan="3"  frozenColumn="true" query="true" field="deptCode" dictionary="t_s_depart,departname,org_code"  queryMode="single"  width="90"></t:dgCol>--%>
            <t:dgCol title="部门" rowspan="3" frozenColumn="true"  query="true" field="deptName"  queryMode="single"  width="90"></t:dgCol>
            <t:dgCol title="车间"  rowspan="3"  frozenColumn="true" query="true" field="workName"  queryMode="single"  width="90"></t:dgCol>
            <t:dgCol title="班组" rowspan="3"   frozenColumn="true" query="true" field="groupName"  queryMode="single"  width="90"></t:dgCol>
            <t:dgCol title="姓名" rowspan="3"  frozenColumn="true" query="true" field="realName"  queryMode="single"  width="70"></t:dgCol>
            <t:dgCol title="薪酬类别" rowspan="3"  frozenColumn="true" field="xclb"  query="true" dictionary="xclb" queryMode="single"  width="70"></t:dgCol>
            <t:dgCol title="带工" rowspan="3"  frozenColumn="true" field="taker" query="true" dictionary="dg"  queryMode="single"  width="60"></t:dgCol>

            <t:dgCol title="出勤时间" rowspan="3" field="zcb" frozenColumn="true"  formatterjs="setCqsj" queryMode="single"  width="105"></t:dgCol>
            <t:dgCol title="摘要" rowspan="3" frozenColumn="true" query="true"  field="remark"  queryMode="single"  width="80"></t:dgCol>
            <t:dgCol title="天数" rowspan="3" frozenColumn="true" query="true"  field="cqDay"  queryMode="single"  width="50"></t:dgCol>


            <t:dgCol title="应发金额" colspan="${zSize+7}"   queryMode="single"  width="70"></t:dgCol>
            <t:dgCol title="应扣金额" colspan="${fSize+4}" queryMode="single"  width="70"></t:dgCol>
            <t:dgCol title="实发工资" field="money" rowspan="3" formatterjs="setColorBlue" newColumn="true"   queryMode="single"  width="80"></t:dgCol>

            <t:dgCol title="基本部分" colspan="${zSize+2}"  queryMode="single"  width="70"></t:dgCol>
            <t:dgCol title="合计" field="baseHj" formatterjs="setColorBlue" rowspan="2"  queryMode="single"  width="70"></t:dgCol>
            <t:dgCol title="出勤" colspan="3"  queryMode="single"  width="70"></t:dgCol>
            <t:dgCol title="应发合计" field="yfHj" rowspan="2"  formatterjs="setColorBlue" queryMode="single"  width="70"></t:dgCol>

            <t:dgCol title="应扣部分" colspan="${fSize+3}"  queryMode="single"  width="70"></t:dgCol>
            <t:dgCol title="应扣合计" field="ykHj" rowspan="2"  formatterjs="setColorRed" newColumn="true"    queryMode="single"  width="70"></t:dgCol>

            <c:forEach var="list" items="${zcategoryEntities}" varStatus="status">
                <t:dgCol title="${list.name}"  field="${list.code}"  formatterjs="setColorBlue" queryMode="single" query="${list.queryed eq 0 ? 'true':'false'}"  width="${list.column_type eq '4' ? '210':(list.column_type eq '0' || list.column_type eq '2') ? '50':'70'}"></t:dgCol>
            </c:forEach>
            <t:dgCol title="其他补贴" field="otherBt" formatterjs="setColorBlue" queryMode="single"  width="70"></t:dgCol>
            <t:dgCol title="满勤奖" field="fullHourMoney" formatterjs="setColorBlue" queryMode="single"  width="70"></t:dgCol>

            <t:dgCol title="迟到早退" field="cdzt" formatterjs="setColorRed" queryMode="single"  width="70"></t:dgCol>
            <t:dgCol title="病事假" field="bsj" formatterjs="setColorRed" queryMode="single"  width="70"></t:dgCol>
            <t:dgCol title="旷工无打卡" field="kgwdk" formatterjs="setColorRed"  width="70"></t:dgCol>
            <c:forEach var="list" items="${fcategoryEntities}" varStatus="status">
                <t:dgCol title="${list.name}"  field="${list.code}" formatterjs="setColorRed" queryMode="single" query="${list.queryed eq 0 ? 'true':'false'}"  width="${list.column_type eq '4' ? '210':(list.column_type eq '0' || list.column_type eq '2') ? '50':'70'}"></t:dgCol>
            </c:forEach>
            <t:dgCol title="劳保" field="lb" formatterjs="setColorRed" queryMode="single"  width="70"></t:dgCol>
            <t:dgCol title="奖罚" field="jf"  queryMode="single" formatterjs="setColorRed2"  width="70"></t:dgCol>
            <t:dgCol title="其他扣回" formatterjs="setColorRed" field="qtkh" queryMode="single"  width="70"></t:dgCol>

            <t:dgToolBar title="生成月度工资表" icon="fa fa-arrow-circle-down" funname="doSc"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="fa fa-edit" url="hmSalaryController.do?goUpdate&winTitle=编辑工资表信息" funname="update" height="630"></t:dgToolBar>
            <t:dgToolBar title="删除"  icon="fa fa-remove" url="hmSalaryController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
            <t:dgToolBar title="打印工资总表" icon="fa fa-print" url="eEnsureUserController.do?goUpdate" funname="printPreview"></t:dgToolBar>
            <t:dgToolBar title="打印发放明细表" icon="fa fa-print" url="eEnsureUserController.do?goUpdate" funname="printPreviewDeptWork"></t:dgToolBar>
            <t:dgToolBar title="打印工资条" icon="fa fa-print" url="eEnsureUserController.do?goUpdate" funname="printPreviewGzt"></t:dgToolBar>

        </t:datagrid>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        $("#hmSalaryListtb").find("input[name='month']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM'});});
        //$("#hmSalaryListtb").find("input[name='month']").val('${month}');
        layer.msg("请选择月份查看月结工资");
    });
    function setColorBlue(val,row,index){
        if(val != null && val != ''){
            if(val>0){
                return '<font color="blue">'+parseInt(val)+'</font>';
            }
        }else{
            return '';
        }
    }
    function setColorRed(val,row,index){
        if(val != null && val != ''){
            if(val>0){
                return '<font color="red">'+parseInt(val)+'</font>';
            }
        }else{
            return '';
        }
    }
    function setColorRed2(val,row,index){
        return '<font color="red">'+val+'</font>';
    }
    function setPoint(val,row,index){
        if(val != null && val != ''){
            if(row.realName != null && row.realName != ''){
                if(val>0){
                    return parseInt(val);
                }
            }else{
                return '<font color="blue">'+parseInt(val)+'</font>';
            }
        }else{
            return '';
        }
    }
    function printPreview(){
        width = window.top.document.body.offsetWidth*0.85;
        height =window.top.document.body.offsetHeight-100;
        var month =  $("#hmSalaryListtb").find("input[name='month']").val();

        var deptName = $("#hmSalaryListtb").find("input[name='deptName']").val();
        var workName = $("#hmSalaryListtb").find("input[name='workName']").val();
        var groupName = $("#hmSalaryListtb").find("input[name='groupName']").val();
        var realName = $("#hmSalaryListtb").find("input[name='realName']").val();
        var taker = $("#hmSalaryListtb").find("select[name='taker']").val();
        openwindow("打印预览",'hmSalaryController.do?jump&r=qtPrintSheetFrm&url=hmSalaryController.do?printPreview&leaveT=&month='+month+'&deptName='+deptName+'&workName='+workName+"&groupName="+groupName+"&realName="+realName+"&taker="+taker,"hmSalaryList",width,height);
    }
    function printPreviewDeptWork(){
        width = window.top.document.body.offsetWidth*0.85;
        height =window.top.document.body.offsetHeight-100;
        var month =  $("#hmSalaryListtb").find("input[name='month']").val();

        var deptName = $("#hmSalaryListtb").find("input[name='deptName']").val();
        var workName = $("#hmSalaryListtb").find("input[name='workName']").val();
        var groupName = $("#hmSalaryListtb").find("input[name='groupName']").val();
        var realName = $("#hmSalaryListtb").find("input[name='realName']").val();
        var taker = $("#hmSalaryListtb").find("select[name='taker']").val();
        openwindow("打印预览",'hmSalaryController.do?jump&r=qtPrintSheetFrm&url=hmSalaryController.do?printPreviewDeptWork&leaveT=0&month='+month+'&deptName='+deptName+'&workName='+workName+"&groupName="+groupName+"&realName="+realName+"&taker="+taker,"hmSalaryList",width,height);
    }
    function printPreviewGzt(){
        var width = window.top.document.body.offsetWidth*0.85;
        var height =window.top.document.body.offsetHeight-100;
        var month =  $("#hmSalaryListtb").find("input[name='month']").val();

        var deptName = $("#hmSalaryListtb").find("input[name='deptName']").val();
        var workName = $("#hmSalaryListtb").find("input[name='workName']").val();
        var groupName = $("#hmSalaryListtb").find("input[name='groupName']").val();
        var realName = $("#hmSalaryListtb").find("input[name='realName']").val();
//        var taker = $("#hmSalaryListtb").find("option:selected").val();
        var taker = $("#hmSalaryListtb").find("select[name='taker']").val();
        openwindow("打印预览",'hmSalaryController.do?jump&r=qtPrintSheetFrm&url=hmSalaryController.do?printPreviewGzt&leaveT=0&month='+month+'&deptName='+deptName+'&workName='+workName+"&groupName="+groupName+"&realName="+realName+"&taker="+taker,"hmSalaryList",width,height);
    }
    function toDecimal(x) {
        if(!x){
            return x;
        }
        var result = parseFloat(x);
        if (isNaN(result)) {
            return x;
        }
        result = Math.round(x * 10) / 10;
        return result;
    }
    function setCqsj(val,row,index){
        if(row.zcb != null && row.zcb != ''&& row.jiab != null && row.jiab != ''){
            var htm = '正班:'+parseInt(row.zcb);
            if(row.jiab>0){
                htm += '加:'+toDecimal(row.jiab/60);
            }
            return htm;
        }else{
            return '';
        }
    }
    function doSc() {
        $.dialog.confirm('您是否确定生成月度工资表?', function (r) {
            if (r) {
                var index = layer.load(1, {
                    skin:"layui-layer-sys1",
                    shade: [0.1,'#fff'] //0.1透明度的白色背景
                });
                $.ajax({
                    url : "hmSalaryController.do?doAdd&month="+$("#hmSalaryListtb").find("input[name='month']").val(),
                    type : 'post',
                    cache : false,
                    data: null,
                    success : function(data) {
                        var d = $.parseJSON(data);
                        tip(d.msg);
                        if (d.success) {
                            $('#hmSalaryList').datagrid('reload');
                        }
                        layer.close(index);
                    }
                });
            }
        });
    }
    //导入
    function ImportXls() {
        $.dialog({
            content: 'url:hmBaseSalaryController.do?upload',
            zIndex: getzIndex(),
            title : 'Excel导入',
            cache:false,
            lock : true,
            width: 700,
            height: 300
        });

    }

    //导出
    function ExportXls() {
        JeecgExcelExport("hmBaseSalaryController.do?exportXls","hmSalaryList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("hmBaseSalaryController.do?exportXlsByT","hmSalaryList");
    }

</script>

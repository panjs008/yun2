<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script>
    function formatDetail(val,row,index){
        if(val != null && val != ''  && val != '0.0' && val != '0'){
            var s = '<a href="javascript:detailReport(\''+row.month+'\',\''+row.taker+'\')"><font color="blue">'+val+'</font></a>';
            return s;
        }else{
            return '';
        }
    }
</script>
<div id="main_list" class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="hmSalaryList" checkbox="false" pagination="true"  sortOrder="asc" pageSize="20" fitColumns="false" title="" actionUrl="hmSalaryController.do?listKfReportByJdbc&c=A01A02A09" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="月份" field="month"  queryMode="single"  width="70"></t:dgCol>
            <t:dgCol title="带工" field="dg"  queryMode="single"  width="70"></t:dgCol>
            <t:dgCol title="taker" field="taker" hidden="true"  queryMode="single"  width="70"></t:dgCol>
            <t:dgCol title="合计" formatterjs="formatDetail"  field="kf" queryMode="single"  width="70" ></t:dgCol>
        </t:datagrid>
    </div>
</div>


<div data-options="region:'east',
	title:'明细',
	collapsed:true,
	split:true,
	border:false,
	onExpand : function(){
		li_east = 1;
	},
	onCollapse : function() {
	    li_east = 0;
	}"
     style="width: 500px; overflow: hidden;" id="eastPanel">
    <div class="easyui-panel" style="padding:0px;border:0px" fit="true" border="false" id="proDetialListpanel"></div>
</div>
<script type="text/javascript">
    var zrsP = 0;
    $(document).ready(function(){
       /* $("#hmSalaryListtb").find("select[name='c']").empty();
        var html = '<option value="A01A02A08">卫生费</option>';
        html += '<option value="A01A02A09">回扣</option>';
        $("#hmSalaryListtb").find("select[name='c']").append(html);*/
    });
    function detailReport(month,taker){
        var title = "明细："+month;
        if(li_east == 0 || $('#main_list').layout('panel','east').panel('options').title != title){
            $('#main_list').layout('expand','east');
        }
        $('#main_list').layout('panel','east').panel('setTitle', title);
        $('#main_list').layout('panel','east').panel('resize', {width: 900});

        $('#proDetialListpanel').panel("refresh", "hmSalaryController.do?forKfDetailReport&taker="+taker+"&c=A01A02A09&month=" + month);
    }
   /* function queryDetail(workCode,month){
        var url = "hmSalaryController.do?forGroupReport&workCode=" + workCode+"&month=" + month;
        $("iframe").attr("src",url);
        $("iframe").attr("scrolling","auto");

    }*/
</script>
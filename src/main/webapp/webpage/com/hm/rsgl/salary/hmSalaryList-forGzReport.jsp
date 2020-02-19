<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script>
    <c:forEach var="list" items="${orgList}" varStatus="status">
        function formatDetail${status.index}(val,row,index){
            if(val != null && val != ''  && val != '0.0' && val != '0'){
                var s = '<a href="javascript:detailReport(\'${list.orgCode}\',\''+row.month+'\')"><font color="blue">'+parseInt(val)+'</font></a>';
                return s;
            }else{
                return '';
            }
        }
    </c:forEach>
</script>
<div id="main_list" class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="hmSalaryList" checkbox="false" pagination="true"  sortName="deptCode,workCode" sortOrder="asc" pageSize="20" fitColumns="false" title="" actionUrl="hmSalaryController.do?listGzReportByJdbc" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="月份" rowspan="2"  field="month" query="true" frozenColumn="true"  queryMode="single"  width="70"></t:dgCol>
            <t:dgCol title="部门" rowspan="2" frozenColumn="true"  query="true" field="deptName"  queryMode="single"  width="90"></t:dgCol>
            <t:dgCol title="部门"  hidden="true" field="deptCode"  queryMode="single"  width="90"></t:dgCol>
            <t:dgCol title="工资" rowspan="2"  frozenColumn="true"  formatterjs="setTotal0" field="bmgz"  queryMode="single"  width="60"></t:dgCol>
            <t:dgCol title="各个车间" colspan="${fn:length(orgList)}" newColumn="true"   queryMode="single"  width="120"></t:dgCol>
            <c:forEach var="list" items="${orgList}" varStatus="status">
                <t:dgCol title="${list.departname}" formatterjs="formatDetail${status.index}"  field="${list.orgCode}" queryMode="single"  width="56" ></t:dgCol>
            </c:forEach>
        </t:datagrid>
    </div>
</div>
<div id="tempSearchColums" style="display: none;width:100px;">
    <div name="searchColums">
        <table align="right" style="margin-right: 10px;margin-top: 5px;">
            <tr>

                <td>
                    &nbsp;&nbsp;<font color="blue">总计：${zgz}</font>&nbsp;&nbsp;
                </td>

            </tr>
        </table>

    </div>
</div>
<%--<div id="southD" region="south" border="false" style="height: 250px;width:100%;overflow: hidden;" id="southDiv">
    <t:tabs id="rkglTabs" iframe="false" tabPosition="top" fit="false">
        <t:tab title="班组工资明细" id="mxID"  heigth="100%" width="100%" icon="" iframe="hmSalaryController.do?forGroupReport"></t:tab>
    </t:tabs>
</div>--%>
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
        /*$("#hmSalaryListtb").find("select[name='month']").empty();
        var html = '<option value="0">一年以上三年以下</option>';
        html += '<option value="1">三年以上至5年以下</option>';
        html += '<option value="2">5年以上</option>';
        $("#hmSalaryListtb").find("select[name='month']").append(html);*/
        $("#hmSalaryListtb").find("input[name='month']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM'});});
        $("#hmSalaryListtb").find("input[name='month']").val('${month}');
        $("#hmSalaryListtb").find("div[name='searchColums']").find("form#hmSalaryListForm").append($("#tempSearchColums div[name='searchColums']").html());
        $("#tempSearchColums").html('');
    });
    function setPoint(val,row,index){
        if(val != null && val != ''  && val != '0.0' && val != '0'){
            var s = '<a href="javascript:queryDetail(\''+row.workCode+'\',\''+row.month+'\')"><font color="blue">'+parseInt(val)+'</font></a>';
            return s;
        }else{
            return '';
        }
    }
    function setTotal0(val,row,index){
        if(val != null && val != ''  && val != '0'){
            return '<font color="blue">'+parseInt(val)+'</font>';
        }else{
            return '';
        }
    }

    function detailReport(workCode,month){
        var title = "班组工资明细："+month;
        if(li_east == 0 || $('#main_list').layout('panel','east').panel('options').title != title){
            $('#main_list').layout('expand','east');
        }
        $('#main_list').layout('panel','east').panel('setTitle', title);
        $('#main_list').layout('panel','east').panel('resize', {width: 500});

        $('#proDetialListpanel').panel("refresh", "hmSalaryController.do?forGroupReport&workCode=" + workCode+"&month=" + month);
    }
   /* function queryDetail(workCode,month){
        var url = "hmSalaryController.do?forGroupReport&workCode=" + workCode+"&month=" + month;
        $("iframe").attr("src",url);
        $("iframe").attr("scrolling","auto");

    }*/
</script>
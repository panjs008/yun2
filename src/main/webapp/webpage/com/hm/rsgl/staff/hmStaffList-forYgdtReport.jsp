<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script>
    function formatDetail(val,row,index){
        var s = '<a href="javascript:detailReport(\''+row.groupCode+'\',\''+row.deptName+'\',\''+row.workName+'\')"><font color="blue">'+val+'</font></a>';
        return s;
    }
    function detailReport(groupCode,deptName,worName){
        var month =  $("#hmStaffListtb").find("input[name='month']").val();
        var title = "员工动态明细："+month+"，"+deptName+"，"+worName;
        if(li_east == 0 || $('#main_list').layout('panel','east').panel('options').title != title){
            $('#main_list').layout('expand','east');
        }
        $('#main_list').layout('panel','east').panel('setTitle', title);
        $('#main_list').layout('panel','east').panel('resize', {width: 600});

        $('#proDetialListpanel').panel("refresh", "hmStaffController.do?forYgdtMx&groupCode=" + groupCode+"&month=" + month);
    }
    function formatDetail2(val,row,index){
        var s = '<a href="javascript:detailReport2(\''+row.groupCode+'\',\''+row.deptName+'\',\''+row.workName+'\',\'0\')"><font color="blue">'+val+'</font></a>';
        return s;
    }
    function formatDetail3(val,row,index){
        var s = '<a href="javascript:detailReport2(\''+row.groupCode+'\',\''+row.deptName+'\',\''+row.workName+'\',\'1\')"><font color="blue">'+val+'</font></a>';
        return s;
    }
    function formatDetail4(val,row,index){
        var s = '<a href="javascript:detailReport2(\''+row.groupCode+'\',\''+row.deptName+'\',\''+row.workName+'\',\'2\')"><font color="blue">'+val+'</font></a>';
        return s;
    }
    function detailReport2(groupCode,deptName,worName,type){
        var month =  $("#hmStaffListtb").find("input[name='month']").val();
        var title = "员工动态明细："+month+"，"+deptName+"，"+worName;
        if(li_east == 0 || $('#main_list').layout('panel','east').panel('options').title != title){
            $('#main_list').layout('expand','east');
        }
        $('#main_list').layout('panel','east').panel('setTitle', title);
        $('#main_list').layout('panel','east').panel('resize', {width: 600});

        $('#proDetialListpanel').panel("refresh", "hmStaffController.do?forLzYgdtMx&groupCode=" + groupCode+"&month=" + month+"&type="+type);
    }
</script>
<div id="main_list" class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="hmStaffList" checkbox="false" pagination="true" onClick="queryDetail" sortOrder="asc" pageSize="20" fitColumns="false" title="" actionUrl="hmStaffController.do?listAllYgdtByJdbc" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="月份"  field="month" query="true"   hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="部门" frozenColumn="true"   query="true" field="deptName"  queryMode="single"  width="85"></t:dgCol>
            <t:dgCol title="部门"  hidden="true" field="deptCode"  queryMode="single"  width="90"></t:dgCol>
            <t:dgCol title="车间"  frozenColumn="true"  field="workName" query="true" queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="班组" field="groupName" query="true" queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="班组" field="groupCode" hidden="true" queryMode="single"  width="120"></t:dgCol>

            <t:dgCol title="性别" field="sex"  queryMode="single"  width="70"></t:dgCol>
            <t:dgCol title="入职" field="rzrs" formatterjs="formatDetail"  queryMode="single"  width="80"></t:dgCol>
            <t:dgCol title="申请离职" field="sqrs" formatterjs="formatDetail2"  queryMode="single"  width="80"></t:dgCol>
            <t:dgCol title="已离职" field="ylrs"  formatterjs="formatDetail3" queryMode="single"  width="80"></t:dgCol>
            <t:dgCol title="自离" field="zlrs"  formatterjs="formatDetail4" queryMode="single"  width="80"></t:dgCol>
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
<script src = "webpage/com/hm/rsgl/staff/hmStaffList.js"></script>
<script type="text/javascript">
    var zrsP = 0;
    $(document).ready(function(){
        $("#hmStaffListtb").find("input[name='month']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM'});});
        $("#hmStaffListtb").find("input[name='month']").val('${month}');
    });
    function setPoint(val,row,index){
        if(val != null && val != ''  && val != '0'){
            return parseInt(val);
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


    function queryDetail(){
        /*$('#hmStaffList').datagrid('unselectAll');
        $('#hmStaffList').datagrid({
            onClickRow: function(rowIndex, rowData){
                var url = "hmStaffController.do?forAnsily&deptCode=" + rowData.deptCode;
                if (rowData) {
                    $("iframe").attr("src",url);
                    $("iframe").attr("scrolling","auto");
                }else{
                }
            }
        });*/
    }
</script>
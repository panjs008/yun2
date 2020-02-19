<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script>
    var flag = 1;
    function subStringNum(a,num) {
        var a_type = typeof(a);
        if(a_type == "number"){
            var aStr = a.toString();
            var aArr = aStr.split('.');
        }else if(a_type == "string"){
            var aArr = a.split('.');
        }
        if(aArr.length > 1) {
            a = aArr[0] + "." + aArr[1].substr(0, num);
        }
        return a
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
    <c:forEach var="list" items="${cols}" varStatus="status">
        function formatDetail${list.name}(val,row,index){
            if(val != null && val != '' && val != 0){
                var m = parseInt(val);
                //var m = toDecimal(val);
                //var m = subStringNum(val,1);
                var s = '<a href="javascript:detailBill(\'${list.name}\',\''+row.workNo+'\',\''+m+'\')"><font color="blue">'+m+'</font></a>';
                return s;
            }else{
                return '';
            }
        }
    </c:forEach>
</script>
<div id="main_list" class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="hmSalaryList" checkbox="false" pagination="true" fitColumns="false" pageSize="20" title="" actionUrl="hmSalaryController.do?listAllByJdbc" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="月份"  field="month" query="true"   hidden="true"  queryMode="single"  width="120"></t:dgCol>

      <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="姓名"  frozenColumn="true" query="true" field="realName"  queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="部门"  frozenColumn="true" query="true"  field="deptName"  queryMode="single"  width="90"></t:dgCol>
      <t:dgCol title="车间"  frozenColumn="true" query="true"  field="workName"  queryMode="single"  width="90"></t:dgCol>
      <t:dgCol title="班组"  frozenColumn="true" query="true"  field="groupName"  queryMode="single"  width="90"></t:dgCol>
      <t:dgCol title="workno"  hidden="true" field="workNo"  queryMode="single"  width="90"></t:dgCol>
      <%--<t:dgCol title="每日工资合计" colspan="31"  newColumn="true"  queryMode="single"  width="80"></t:dgCol>--%>
      <c:forEach var="list" items="${cols}" varStatus="status">
          <t:dgCol title="${list.name}"  field="${list.code}" formatterjs="formatDetail${list.name}" queryMode="single"  width="40" ></t:dgCol>
      </c:forEach>
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
 $(document).ready(function(){
     $("#hmSalaryListtb").find("input[name='month']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM'});});
     $("#hmSalaryListtb").find("input[name='month']").val('${month}');
 });

/* function formatDetail(val,row,index){
     console.log(index);
     var s = '<a href="javascript:detail('+flag+','+row.workNo+')">'+val+'</a>';
     return s;
 }*/

 function setPoint(val,row,index){
     if(val != null && val != ''){
         return parseInt(val);
     }else{
         return '';
     }
 }
 function detailBill(day,workNo,m){
     var monthVal = $("#hmSalaryListtb").find("input[name='month']").val();
     if(day<10){
         day = '0'+day;
     }
     var title = "明细："+monthVal+"-"+day+"，工资："+m;
     if(li_east == 0 || $('#main_list').layout('panel','east').panel('options').title != title){
         $('#main_list').layout('expand','east');
     }
     $('#main_list').layout('panel','east').panel('setTitle', title);
     $('#main_list').layout('panel','east').panel('resize', {width: 540});

     $('#proDetialListpanel').panel("refresh", "hmSalaryController.do?detail&workNo="+workNo+"&day="+day+"&month="+monthVal);
 }
 //导入
 function ImportXls() {
     $.dialog({
         content: 'url:hmBaseSalaryController.do?upload',
         zIndex: getzIndex(),
         title : 'Excel导入',
         cache:false,
         lock : true,
         width: 600,
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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script>
    function gridLoadSuccess(data) {
        var num = 6,num2 = 3,rowsLen = data.rows.length;
        for(var i=0; i<rowsLen/num; i++){
            $('#hmSalaryList').datagrid('mergeCells',{
                index:i*num,
                field:'deptName',
                rowspan:num
            });
            $('#hmSalaryList').datagrid('mergeCells',{
                index:i*num,
                field:'workName',
                rowspan:num
            });
            $('#hmSalaryList').datagrid('mergeCells',{
                index:i*num,
                field:'groupName',
                rowspan:num
            });
            $('#hmSalaryList').datagrid('mergeCells',{
                index:i*num,
                field:'realName',
                rowspan:num
            });
        }
        for(var i=0; i<rowsLen/num2; i++){
            $('#hmSalaryList').datagrid('mergeCells',{
                index:i*num2,
                field:'cqsjType',
                rowspan:num2
            });
        }
    }
</script>
<div id="main_list" class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="hmSalaryList" checkbox="false" onLoadSuccess="gridLoadSuccess" pagination="true" fitColumns="false" pageSize="48" title="" actionUrl="hmSalaryController.do?listWorkTimeByJdbc" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="月份"  field="month" query="true"   hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>

      <t:dgCol title="部门" frozenColumn="true" query="true" dictionary="dg" field="deptName"  queryMode="single"  width="90"></t:dgCol>
      <t:dgCol title="车间"  frozenColumn="true" query="true"  dictionary="dg" field="workName"  queryMode="single"  width="90"></t:dgCol>
      <t:dgCol title="班组"  frozenColumn="true" query="true"  dictionary="dg" field="groupName"  queryMode="single"  width="90"></t:dgCol>
      <t:dgCol title="姓名"  frozenColumn="true" query="true" field="realName"  queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="带工" rowspan="3" hidden="true"  frozenColumn="true" field="taker" query="true" dictionary="dg"  queryMode="single"  width="60"></t:dgCol>

      <%--<t:dgCol title="出勤时间"   colspan="2"  queryMode="single"  width="70"></t:dgCol>--%>
      <t:dgCol title="出勤"  frozenColumn="true" field="cqsjType"  queryMode="single"  width="60"></t:dgCol>
      <t:dgCol title="加班"  frozenColumn="true" field="cqsj" replace="正常班_0,加班_1,金额_2,正常班_3,加班_4,金额_5" queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="workno"  hidden="true" field="workNo"  queryMode="single"  width="90"></t:dgCol>
      <%--<t:dgCol title="每日工资合计" colspan="31"  newColumn="true"  queryMode="single"  width="80"></t:dgCol>--%>
      <c:forEach var="list" items="${cols}" varStatus="status">
          <t:dgCol title="${list.name}"  field="${list.code}" formatterjs="setPoint" queryMode="single"  width="50" ></t:dgCol>
      </c:forEach>
      <t:dgToolBar title="打印工时核对条" icon="fa fa-print" url="eEnsureUserController.do?goUpdate" funname="printPreviewGshdt"></t:dgToolBar>
      <t:dgToolBar title="打印工时核对总表" icon="fa fa-print" url="eEnsureUserController.do?goUpdate" funname="printPreviewGshdtZb"></t:dgToolBar>

  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
     $("#hmSalaryListtb").find("input[name='month']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM'});});
     $("#hmSalaryListtb").find("input[name='month']").val('${month}');
     $("#hmSalaryListtb").find("select[name='deptName']").empty();
     $("#hmSalaryListtb").find("select[name='workName']").empty();
     $("#hmSalaryListtb").find("select[name='groupName']").empty();

     var html = '<option value="">--选择--</option>';
     <c:forEach var="item" items="${departList}">
        html += '<option value="${item.orgCode}">${item.departname}</option>';
     </c:forEach>
     $("#hmSalaryListtb").find("select[name='deptName']").append(html);
     $("#hmSalaryListtb").find("select[name='deptName']").change(function() {
         $("#hmSalaryListtb").find("select[name='workName']").empty();
         $.ajax({
             url: "hmStaffController.do?getDept&code=" + $("#hmSalaryListtb").find("select[name='deptName']").val(),
             type: 'post',
             cache: false,
             data: null,
             success: function (data) {
                 var d = $.parseJSON(data);
                 if (d.success) {
                     var msg = d.msg;
                     var dataItems = new Array(); //定义一数组
                     dataItems = d.obj.split(";"); //字符分割
                     var option1 = '<option value="">--选择--</option>';
                     for (i = 0; i < dataItems.length; i++) {
                         var dataitem = new Array(); //定义一数组
                         dataitem = dataItems[i].split(","); //字符分割
                         option1 += '<option value=' + dataitem[0] + '>' + dataitem[1] + '</option>';
                     }
                     $("#hmSalaryListtb").find("select[name='workName']").append(option1);
                 }
             }
         });
     });
     $("#hmSalaryListtb").find("select[name='workName']").change(function() {
         $("#hmSalaryListtb").find("select[name='groupName']").empty();
         $.ajax({
             url: "hmStaffController.do?getDept&code=" + $("#hmSalaryListtb").find("select[name='workName']").val(),
             type: 'post',
             cache: false,
             data: null,
             success: function (data) {
                 var d = $.parseJSON(data);
                 if (d.success) {
                     var msg = d.msg;
                     var dataItems = new Array(); //定义一数组
                     dataItems = d.obj.split(";"); //字符分割
                     var option1 = '<option value="">--选择--</option>';
                     for (i = 0; i < dataItems.length; i++) {
                         var dataitem = new Array(); //定义一数组
                         dataitem = dataItems[i].split(","); //字符分割
                         option1 += '<option value=' + dataitem[0] + '>' + dataitem[1] + '</option>';
                     }
                     $("#hmSalaryListtb").find("select[name='groupName']").append(option1);
                 }
             }
         });
     });
    /* $("#hmSalaryListtb").find("div[name='searchColums']").find("form#hmSalaryListForm").append($("#tempSearchColums div[name='searchColums']").html());
     $("#tempSearchColums").html('');*/
 });
 function setPoint1(val,row,index){
     if(val != null && val != ''  && val != '0.0'){
         if(row.cqsj == '0' || row.cqsj == '1' || row.cqsj == '2')
         return val;
     }else{
         return '';
     }
 }
/* function formatDetail(val,row,index){
     console.log(index);
     var s = '<a href="javascript:detail('+flag+','+row.workNo+')">'+val+'</a>';
     return s;
 }*/
 function printPreviewGshdt(){
     width = window.top.document.body.offsetWidth*0.85;
     height =window.top.document.body.offsetHeight-100;
     var month =  $("#hmSalaryListtb").find("input[name='month']").val();
     var deptName = $("#hmSalaryListtb").find("select[name='deptName']").val();
     var workName = $("#hmSalaryListtb").find("select[name='workName']").val();
     var groupName = $("#hmSalaryListtb").find("select[name='groupName']").val();
     var realName = $("#hmSalaryListtb").find("input[name='realName']").val();

     var taker = $("#hmSalaryListtb").find("select[name='taker']").val();

     //var taker = $("#hmSalaryListtb").find("option:selected").val();

     openwindow("打印预览",'hmSalaryController.do?jump&r=qtPrintSheetFrm&url=hmSalaryController.do?printPreviewGshdt&month='+month+'&deptName='+deptName+'&workName='+workName+"&groupName="+groupName+"&realName="+realName+"&taker="+taker,"hmSalaryList",width,height);
 }
 function printPreviewGshdtZb(){
     width = window.top.document.body.offsetWidth*0.85;
     height =window.top.document.body.offsetHeight-100;
     var month =  $("#hmSalaryListtb").find("input[name='month']").val();
     var deptName = $("#hmSalaryListtb").find("select[name='deptName']").val();
     var workName = $("#hmSalaryListtb").find("select[name='workName']").val();
     var groupName = $("#hmSalaryListtb").find("select[name='groupName']").val();
     //var taker = $("#hmSalaryListtb").find("option:selected").val();
     var realName = $("#hmSalaryListtb").find("input[name='realName']").val();
     var taker = $("#hmSalaryListtb").find("select[name='taker']").val();
     openwindow("打印预览",'hmSalaryController.do?jump&r=qtPrintSheetFrm&url=hmSalaryController.do?printPreviewGshdtZb&month='+month+'&deptName='+deptName+'&workName='+workName+"&groupName="+groupName+"&realName="+realName+"&taker="+taker,"hmSalaryList",width,height);
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
 function setPoint(val,row,index){
     if(val > 0){
         if(row.cqsj == '2' || row.cqsj == '5'){
             return "<font color='blue'>"+parseInt(val)+"</font>";
         }else{
             return "<font color='blue'>"+val+"</font>";
         }
     }else{
         return 0;
     }
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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="hmLeaveStaffList" checkbox="false" pageSize="20" sortName="deptCode,workCode,groupCode" pagination="true" fitColumns="true" title="" actionUrl="hmLeaveStaffController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="姓名"  query="true" field="realName"  queryMode="single"  width="100"></t:dgCol>
      <t:dgCol title="部门"  query="true" field="deptName"  queryMode="single"  width="170"></t:dgCol>
      <t:dgCol title="workNo"  hidden="true" field="workNo"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="部门代码"  hidden="true" field="deptCode"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="车间" query="true" field="workName"  queryMode="single"  width="170"></t:dgCol>
      <t:dgCol title="车间代码" hidden="true"  field="workCode"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="班组" query="true" field="groupName"  queryMode="single"  width="170"></t:dgCol>
      <t:dgCol title="班组代码"  hidden="true"  field="groupCode"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="职务"  field="job"  dictionary="job" queryMode="single"  width="150"></t:dgCol>
      <t:dgCol title="级别"  field="jb"  dictionary="jb" queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="薪酬类别"  field="xclb"  dictionary="xclb" queryMode="single"  width="90"></t:dgCol>
      <t:dgCol title="性别"  field="sex" dictionary="sex"  queryMode="single"  width="60"></t:dgCol>
      <t:dgCol title="员工类别"  field="yglb" dictionary="yglb"  queryMode="single"  width="110"></t:dgCol>
      <t:dgCol title="入职日期"  field="rzDate"  queryMode="single"  width="130"></t:dgCol>
      <t:dgCol title="离职时间"  field="leaveDate" query="true"  queryMode="group"  width="130"></t:dgCol>
      <t:dgCol title="离职类别"  field="leaveType" replace="正常离职_0,自离_1"  query="true" queryMode="single"  width="100"></t:dgCol>

      <t:dgCol title="申请时间"  field="applyDate"  queryMode="group" query="true"  width="130"></t:dgCol>

      <t:dgCol title="结算日期"  field="annualDate" query="true"  queryMode="group"  width="130"></t:dgCol>
      <t:dgCol title="状态"  field="state" replace="未结算_0,已结算_1,不予结算_2"  formatterjs="setColor" query="true" queryMode="single"  width="130"></t:dgCol>

      <t:dgToolBar title="录入" icon="fa fa-plus" url="hmLeaveStaffController.do?goAdd&winTitle=录入离职人员" funname="add" width="1200" height="600"></t:dgToolBar>
       <t:dgToolBar title="编辑" icon="fa fa-edit" url="hmLeaveStaffController.do?goUpdate&winTitle=编辑离职人员" funname="update" width="1200" height="600"></t:dgToolBar>
      <t:dgToolBar title="结算" icon="fa fa-arrow-circle-right" url="hmLeaveStaffController.do?goAnnual&winTitle=结算" funname="update" width="500" height="200"></t:dgToolBar>
      <t:dgToolBar title="删除"  icon="fa fa-remove" url="hmLeaveStaffController.do?doBatchDel" funname="deleteALLSelect" width="1200" height="600"></t:dgToolBar>
       <t:dgToolBar title="查看" icon="fa fa-search" url="hmLeaveStaffController.do?goUpdate" funname="detail" width="1200" height="600"></t:dgToolBar>
     <%-- <t:dgToolBar title="打印工资条" icon="fa fa-print" url="eEnsureUserController.do?goUpdate" funname="printPreviewGzt"></t:dgToolBar>
      <t:dgToolBar title="打印工时核对条" icon="fa fa-print" url="eEnsureUserController.do?goUpdate" funname="printPreviewGshdt"></t:dgToolBar>--%>

  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/hm/rsgl/leavestaff/hmLeaveStaffList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
     $("#hmLeaveStaffListtb").find("input[name='applyDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
     $("#hmLeaveStaffListtb").find("input[name='applyDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});

     $("#hmLeaveStaffListtb").find("input[name='leaveDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
     $("#hmLeaveStaffListtb").find("input[name='leaveDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});

     $("#hmLeaveStaffListtb").find("input[name='annualDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
     $("#hmLeaveStaffListtb").find("input[name='annualDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});

     $("#hmLeaveStaffListtb").find("input[name='applyDate_begin']").css("width","120px");
     $("#hmLeaveStaffListtb").find("input[name='applyDate_end']").css("width","120px");

     $("#hmLeaveStaffListtb").find("input[name='leaveDate_begin']").css("width","120px");
     $("#hmLeaveStaffListtb").find("input[name='leaveDate_end']").css("width","120px");

     $("#hmLeaveStaffListtb").find("input[name='annualDate_begin']").css("width","120px");
     $("#hmLeaveStaffListtb").find("input[name='annualDate_end']").css("width","120px");

 });

 function setColor(val,row,index){
     if(val == '1'){
         return '<font color="blue">已结算</font>';
     }else if(val == "2"){
         return '<font color="red">不予结算</font>';
     }else{
         return '<font color="red">未结算</font>';
     }
 }
 function printPreviewGzt(){
     width = window.top.document.body.offsetWidth*0.85;
     height =window.top.document.body.offsetHeight-100;
     var rows=$('#hmLeaveStaffList').datagrid("getChecked");
     if(rows.length==0){
         tip("请选择人员");
         return false;
     }
     openwindow("打印预览",'hmSalaryController.do?jump&r=qtPrintSheetFrm&url=hmSalaryController.do?printPreviewGzt&workNo='+rows[0].workNo+'&month='+(rows[0].annualDate).substr(0,7),"hmSalaryList",width,height);
 }
 function printPreviewGshdt(){
     width = window.top.document.body.offsetWidth*0.85;
     height =window.top.document.body.offsetHeight-100;
     var rows=$('#hmLeaveStaffList').datagrid("getChecked");
     if(rows.length==0){
         tip("请选择人员");
         return false;
     }
     openwindow("打印预览",'hmSalaryController.do?jump&r=qtPrintSheetFrm&url=hmSalaryController.do?printPreviewGshdt&workNo='+rows[0].workNo+'&month='+(rows[0].annualDate).substr(0,7),"hmSalaryList",width,height);
 }
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'hmLeaveStaffController.do?upload', "hmLeaveStaffList");
}

//导出
function ExportXls() {
	JeecgExcelExport("hmLeaveStaffController.do?exportXls","hmLeaveStaffList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("hmLeaveStaffController.do?exportXlsByT","hmLeaveStaffList");
}

 </script>
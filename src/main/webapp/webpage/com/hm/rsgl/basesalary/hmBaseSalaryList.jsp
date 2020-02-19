<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="hmBaseSalaryList" checkbox="true" pagination="true" pageSize="20" fitColumns="true" title="" actionUrl="hmBaseSalaryController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="月份"  field="month" query="true"    queryMode="single"  width="60"></t:dgCol>

      <%--<t:dgCol title="工号"   query="true"  field="workNo"  queryMode="single"  width="60"></t:dgCol>--%>
      <t:dgCol title="姓名"   query="true" field="realName"  queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="部门"   field="deptName"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="车间"   field="workName"  queryMode="single"  width="80"></t:dgCol>

      <c:forEach var="list" items="${categoryEntities}" varStatus="status">
          <t:dgCol title="${list.name}"  field="${list.code}"  queryMode="single" query="${list.queryed eq 0 ? 'true':'false'}"  width="${list.column_type eq '4' ? '130':(list.column_type eq '0' || list.column_type eq '2') ? '50':'60'}"></t:dgCol>
      </c:forEach>

      <t:dgToolBar title="录入" icon="fa fa-plus" url="hmBaseSalaryController.do?goAdd&winTitle=录入薪酬预设" funname="add"></t:dgToolBar>
       <t:dgToolBar title="编辑" icon="fa fa-edit" url="hmBaseSalaryController.do?goUpdate&winTitle=编辑薪酬预设" funname="update"></t:dgToolBar>
       <t:dgToolBar title="删除"  icon="fa fa-remove" url="hmBaseSalaryController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
       <t:dgToolBar title="查看" icon="fa fa-search" url="hmBaseSalaryController.do?goUpdate&winTitle=编辑薪酬预设" funname="detail"></t:dgToolBar>
      <t:dgToolBar title="生成薪酬预设" icon="fa fa-arrow-circle-down" funname="doSc"></t:dgToolBar>

      <t:dgToolBar title="调薪" icon="fa fa-arrow-circle-up" url="hmBaseSalaryController.do?listChange" funname="tx"></t:dgToolBar>
      <t:dgToolBar title="导入" icon="fa fa-arrow-circle-left" funname="ImportXls"></t:dgToolBar>
       <t:dgToolBar title="导出" icon="fa fa-arrow-circle-right" funname="ExportXls"></t:dgToolBar>
      <t:dgToolBar title="模板下载" icon="fa fa-arrow-circle-o-down" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/hm/rsgl/basesalary/hmBaseSalaryList.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
     $("#hmBaseSalaryListtb").find("input[name='month']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM'});});
     $("#hmBaseSalaryListtb").find("input[name='month']").val('${month}');

 });

 function doSc() {
     $.dialog.confirm('您是否生成每月薪酬预设数据?', function (r) {
         if (r) {
             var index = layer.load(1, {
                 skin:"layui-layer-sys1",
                 shade: [0.1,'#fff'] //0.1透明度的白色背景
             });
             $.ajax({
                 url : "hmBaseSalaryController.do?doRe&month="+$("#hmBaseSalaryListtb").find("input[name='month']").val(),
                 type : 'post',
                 cache : false,
                 data: null,
                 success : function(data) {
                     var d = $.parseJSON(data);
                     tip(d.msg);
                     if (d.success) {
                         $('#hmBaseSalaryList').datagrid('reload');
                     }
                     layer.close(index);
                 }
             });
         }
     });
 }

 //调薪
 function tx() {
     var rowsData = $('#hmBaseSalaryList').datagrid('getSelections');
     var ids = [];
     if (!rowsData || rowsData.length == 0) {
         layer.msg('请选择数据');
         return;
     }
     if (rowsData.length >1) {
         layer.msg('只能选择一条数据');
         return;
     }
     $.dialog({
         content: 'url:hmBaseSalaryController.do?listChange&realName='+rowsData[0].realName+'&month='+rowsData[0].month,
         zIndex: getzIndex(),
         title : '调薪',
         cache:false,
         lock : true,
         width: 1200,
         height: 600
     });
 }

 //导入
 function ImportXls() {
     $.dialog({
         content: 'url:hmBaseSalaryController.do?upload',
         zIndex: getzIndex(),
         title : '薪酬预设导入',
         cache:false,
         lock : true,
         width: 600,
         height: 300
     });
 }

//导出
function ExportXls() {
	JeecgExcelExport("hmBaseSalaryController.do?exportXls","hmBaseSalaryList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("hmBaseSalaryController.do?exportXlsByT","hmBaseSalaryList");
}

 </script>

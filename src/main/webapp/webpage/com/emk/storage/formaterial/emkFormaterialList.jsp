<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="emkFormaterialList" checkbox="false" sortName="kdDate" sortOrder="desc" pageSize="20" pagination="true" fitColumns="false" title="" actionUrl="emkFormaterialController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="布行名称"  query="true" field="bhmc"  queryMode="single"  width="160"></t:dgCol>
   <t:dgCol title="开单日期"  field="kdDate" query="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="单号"  query="true" field="orderNo"  queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="合计"  field="hj"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="状态" formatterjs="formatColor"  field="state"  queryMode="single"  width="90"></t:dgCol>

      <t:dgToolBar title="录入" icon="fa fa-plus"  url="emkFormaterialController.do?goAdd&winTitle=录入叫布" funname="add" width="1200"></t:dgToolBar>
      <t:dgToolBar title="编辑" icon="fa fa-edit" url="emkFormaterialController.do?goUpdate&winTitle=编辑叫布" funname="update"  width="1200"></t:dgToolBar>
      <t:dgToolBar title="查看" icon="fa fa-search"  url="emkFormaterialController.do?goUpdate&winTitle=查叫布" funname="detail"  width="1200"></t:dgToolBar>
      <t:dgToolBar title="删除"  icon="fa fa-remove"  url="emkFormaterialController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
      <t:dgToolBar title="导入" icon="fa fa-arrow-circle-left" funname="ImportXls"></t:dgToolBar>
      <t:dgToolBar title="提交"  icon="fa fa-arrow-circle-up"  funname="doSubmitV"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/emk/storage/formaterial/emkFormaterialList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
     $("#emkFormaterialListtb").find("input[name='kdDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
     $("#emkFormaterialListtb").find("input[name='kdDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });

 function ImportXls() {
     $.dialog({
         content: 'url:emkFormaterialController.do?upload',
         zIndex: getzIndex(),
         title : '导入叫布 ',
         cache:false,
         lock : true,
         width: 800,
         height: 400
     });

 }
 function doSubmitV() {
     var rowsData = $('#emkFormaterialList').datagrid('getSelections');
     var ids = [];
     if (!rowsData || rowsData.length == 0) {
         layer.msg('请选择需要提交的叫布');
         return;
     }
     for ( var i = 0; i < rowsData.length; i++) {
         ids.push(rowsData[i].id);
     }
     $.dialog.confirm('您是否确定提交叫布?', function(r) {
         if (r) {
             var index = layer.load(1, {
                 skin:"layui-layer-sys1",
                 shade: [0.1,'#fff'] //0.1透明度的白色背景
             });
             $.ajax({
                 url : "emkFormaterialController.do?doSubmit&ids="+ids,
                 type : 'post',
                 cache : false,
                 data: null,
                 success : function(data) {
                     var d = $.parseJSON(data);
                     layer.msg(d.msg);
                     if (d.success) {
                         $('#emkFormaterialList').datagrid('reload');
                     }
                     layer.close(index);
                 }
             });
         }
     });
 }
 function formatColor(val,row){
     if(row.state=="1"){
         return '<span style="color:	#0000FF;">已提交</span>';
     }else if(row.state=="2"){
         return '<span style="color:	#00FF00;">完成</span>';
     }else if(row.state=="3"){
         return '<span style="color:	#FF0000;">退回</span>';
     }else if(row.state=="4"){
         return '<span style="color:	#0000FF;">核实通过</span>';
     }else{
         return '创建';
     }
 }
//导出
function ExportXls() {
	JeecgExcelExport("emkFormaterialController.do?exportXls","emkFormaterialList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("emkFormaterialController.do?exportXlsByT","emkFormaterialList");
}

 </script>
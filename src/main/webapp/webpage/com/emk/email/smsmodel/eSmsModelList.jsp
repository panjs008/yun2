<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="eSmsModelList" checkbox="false" pagination="true" nowrap="false" fitColumns="false" title="" actionUrl="eSmsModelController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="组织机构" field="orgCode" dictionary="t_s_depart,org_code,departname" width="120"></t:dgCol>
      <t:dgCol title="创建人"  field="createName" queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="短信类型"  field="type" dictionary="smsType"  queryMode="single"  width="90"></t:dgCol>

      <t:dgCol title="短信内容"  field="content"  queryMode="single"  width="500"></t:dgCol>
      <t:dgCol title="状态"  field="status" formatterjs="formatColor"  queryMode="single"  width="100"></t:dgCol>

      <t:dgToolBar title="录入" icon="fa fa-plus" url="eSmsModelController.do?goAdd" funname="add"></t:dgToolBar>
       <t:dgToolBar title="编辑" icon="fa fa-edit" url="eSmsModelController.do?goUpdate" funname="update"></t:dgToolBar>
      <t:dgToolBar title="提交"  icon="fa fa-arrow-circle-up" operationCode="submit" funname="doSubmitV"></t:dgToolBar>

      <t:dgToolBar title="删除"  icon="fa fa-remove" url="eSmsModelController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
       <t:dgToolBar title="查看" icon="fa fa-search" url="eSmsModelController.do?goUpdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/emk/email/smsmodel/eSmsModelList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });

 function formatColor(val,row){
     if(row.status=="1"){
         return '<span style="color:	#0000FF;">已提交</span>';
     }else if(row.status=="2"){
         return '<span style="color:	#0000FF;">审核通过</span>';
     }else{
         return '创建';
     }
 }
 function doSubmitV() {
     var rowsData = $('#eSmsModelList').datagrid('getSelections');
     var ids = [];
     if (!rowsData || rowsData.length == 0) {
         tip('请选择需要提交审核的短信模板');
         return;
     }
     for ( var i = 0; i < rowsData.length; i++) {
         ids.push(rowsData[i].id);
     }

     $.dialog.confirm('您是否确定提交短信模板审核?', function(r) {
         var index = layer.load(1, {
             skin:"layui-layer-sys1",
             shade: [0.1,'#fff'] //0.1透明度的白色背景
         });
         if (r) {
             $.ajax({
                 url : "eSmsModelController.do?doSubmit&ids="+ids,
                 type : 'post',
                 cache : false,
                 data: null,
                 success : function(data) {
                     layer.close(index);
                     var d = $.parseJSON(data);
                     layer.msg(d.msg);
                     if (d.success) {
                         $('#eSmsModelList').datagrid('reload');
                     }
                 }
             });
         }
     });
 }
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'eSmsModelController.do?upload', "eSmsModelList");
}

//导出
function ExportXls() {
	JeecgExcelExport("eSmsModelController.do?exportXls","eSmsModelList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("eSmsModelController.do?exportXlsByT","eSmsModelList");
}

 </script>
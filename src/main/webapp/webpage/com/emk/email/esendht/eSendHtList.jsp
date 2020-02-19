<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="eSendHtList" checkbox="true" pagination="true" pageSize="30" fitColumns="false" title="" actionUrl="eSendHtController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="同事编号"  field="workNo" query="true" queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="同事姓名"  field="userName"  query="true" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="续签期限" query="true"  field="xqqx"   queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="模板编号"  field="mbbh"  queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="经理姓名" query="true"  field="manger"  queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="经理电话"  field="mangerTelphone" query="true" queryMode="single"  width="100"></t:dgCol>
      <t:dgCol title="经理邮箱"  field="email" query="true" queryMode="single"  width="140"></t:dgCol>
      <t:dgCol title="发送状态"  field="sendState" replace="创建_0,已发送_1" query="true" formatterjs="formatSendStateColor" queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="发送时间" query="true"  field="sendTime"  queryMode="group"  width="140"></t:dgCol>
      <t:dgCol title="回复状态"  field="state" query="true" formatterjs="formatStateColor" replace="创建_0,已确认_1,拒绝_2"  queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="回复时间"  field="replyTime"  queryMode="single"  width="140"></t:dgCol>
      <t:dgCol title="同事电话"  field="telphone"  queryMode="single"  width="90"></t:dgCol>
      <t:dgCol title="职业危害"  field="zywh"  queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="上岗证"  field="sgxx"  queryMode="single"  width="80"></t:dgCol>

      <%--<t:dgCol title="地址"  field="adress"  queryMode="single"  width="160"></t:dgCol>--%>
   <t:dgCol title="备注"  field="remark" hidden="true"  queryMode="single"  width="120"></t:dgCol>
       <t:dgToolBar title="录入" icon="fa fa-plus" url="eSendHtController.do?goAdd" funname="add"></t:dgToolBar>
       <t:dgToolBar title="编辑" icon="fa fa-edit" url="eSendHtController.do?goUpdate" funname="update"></t:dgToolBar>
       <t:dgToolBar title="删除"  icon="fa fa-remove" url="eSendHtController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
       <t:dgToolBar title="查看" icon="fa fa-search" url="eSendHtController.do?goUpdate" funname="detail"></t:dgToolBar>
      <t:dgToolBar title="发送邮箱" icon="fa fa-arrow-circle-up" funname="doSendEmail"></t:dgToolBar>
      <t:dgToolBar title="导入" icon="fa fa-arrow-circle-left" funname="ImportXls"></t:dgToolBar>
       <t:dgToolBar title="导出" icon="fa fa-arrow-circle-right" funname="ExportXls"></t:dgToolBar>
       <t:dgToolBar title="模板下载" icon="fa fa-arrow-circle-o-down" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/emk/email/esendht/eSendHtList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
     $("#eSendHtListtb").find("input[name='sendTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
     $("#eSendHtListtb").find("input[name='sendTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
     /*$("#eSendHtListtb").find("input[name='sendTime_begin']").css("width","120px");
     $("#eSendHtListtb").find("input[name='sendTime_end']").css("width","120px");*/

 });

 function formatSendStateColor(val,row){
     if(row.sendState=="1"){
         return '<span style="color:	#0000FF;">已发送</span>';
     }else if(row.sendState=="2"){
         return '<span style="color:	#00FF00;">完成</span>';
     }else{
         return '创建';
     }
 }
 function formatStateColor(val,row){
     if(row.state=="1"){
         return '<span style="color:	#0000FF;">已确认</span>';
     }else if(row.state=="2"){
         return '<span style="color:	#FF0000;">拒绝</span>';
     }else{
         return '创建';
     }
 }

 function doSendEmail() {
     var rowsData = $('#eSendHtList').datagrid('getSelections');
     var ids = [];
     if (!rowsData || rowsData.length == 0) {
         layer.msg('请选择需要发送邮件的数据');
         return;
     }
     for ( var i = 0; i < rowsData.length; i++) {
         ids.push(rowsData[i].id);
     }
     $.dialog.confirm('您是否确定提交发送邮件?', function(r) {
         if (r) {
             var index = layer.load(1, {
                 skin:"layui-layer-sys1",
                 shade: [0.1,'#fff'] //0.1透明度的白色背景
             });
             $.ajax({
                 url : "eSendHtController.do?doSendEmail&ids="+ids,
                 type : 'post',
                 cache : false,
                 data: null,
                 success : function(data) {
                     var d = $.parseJSON(data);
                     layer.msg(d.msg);
                     if (d.success) {
                         $('#eSendHtList').datagrid('reload');
                         layer.close(index);
                     }
                 }
             });
         }
     });
 }
 //导入
 function ImportXls() {
     $.dialog({
         content: 'url:eSendHtController.do?upload',
         zIndex: getzIndex(),
         title : '导入数据',
         cache:false,
         lock : true,
         width: 500,
         height: 300
     });

 }


//导出
function ExportXls() {
	JeecgExcelExport("eSendHtController.do?exportXls","eSendHtList");
}

 //模板下载
 function ExportXlsByT() {
     window.open("${webRoot}/context/数据模板.xls");
 }

 </script>
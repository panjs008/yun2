<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="emkMOutStorageList" checkbox="true" singleSelect="true" pagination="true" pageSize="20" sortOrder="desc" sortName="a01a07a01" fitColumns="true" title="销售单查询" actionUrl="emkMOutStorageController.do?datagrid" idField="id" fit="true" btnCls="bootstrap"  queryMode="group">
      <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="所属部门"  field="sysOrgCode"  hidden="true"  queryMode="single"  width="120"></t:dgCol>

      <c:forEach var="list" items="${headcategoryEntities}" varStatus="status">
          <c:if test="${list.code ne 'a01a07a02' && list.code ne 'a01a07a04' }">
              <t:dgCol title="${list.name}"  field="${list.code}" queryMode="single" dictionary="${list.code eq 'a01a07a06' ? 'paytype':''}" query="${list.queryed eq 0 ? 'true':'false'}"  width="${fn:contains(list.name,'单号') ? '120':( list.column_type eq '4' ? '210':(list.column_type eq '0' || list.column_type eq '2') ? '50':'100')}"></t:dgCol>
          </c:if>
          <c:if test="${list.code eq 'a01a07a02'}">
              <t:dgCol title="客户名称"  field="cusName"   query="true" queryMode="single"  width="180"></t:dgCol>
          </c:if>
          <c:if test="${list.code eq 'a01a07a04' }">
              <t:dgCol title="出货仓库"  field="storageName"  query="true" queryMode="single"  width="120"></t:dgCol>
          </c:if>
      </c:forEach>
      <t:dgCol title="商品数量"  field="total"  queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="金额"  field="money"  queryMode="single"  width="80"></t:dgCol>

      <t:dgCol title="审核状态"  field="state" formatterjs="formatColor"  queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="付款状态"  field="payState" formatterjs="formatColor2"  queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="发货状态"  field="recevieState" formatterjs="formatColor3"  queryMode="single"  width="80"></t:dgCol>

      <t:dgToolBar title="录入" icon="fa fa-plus" operationCode="add" url="emkMOutStorageController.do?goAdd&winTitle=录入销售开单" funname="add" height="600" width="1000"></t:dgToolBar>
      <t:dgToolBar title="编辑" icon="fa fa-edit" operationCode="edit" url="emkMOutStorageController.do?goUpdate&winTitle=编辑销售开单" funname="update" height="600" width="1200"></t:dgToolBar>

      <t:dgToolBar title="查看" icon="fa fa-search" operationCode="look"  url="emkMOutStorageController.do?goUpdate&winTitle=查看销售开单" funname="detail" height="600" width="1200"></t:dgToolBar>

      <%--<t:dgToolBar title="流程进度" operationCode="process" icon="fa fa-plus" funname="goToProcess"></t:dgToolBar>--%>
      <%--<t:dgToolBar title="收款"  icon="fa fa-rmb" operationCode="paysubmit" url="emkMOutStorageController.do?goPay&winTitle=销售开单付款" funname="update" height="200" width="400"></t:dgToolBar>--%>

      <t:dgToolBar title="审核"  icon="fa fa-check" operationCode="submit" funname="doSubmitV"></t:dgToolBar>
      <t:dgToolBar title="反审核"  icon="fa 	fa-remove" operationCode="cancelsubmit" funname="doCancelApproval"></t:dgToolBar>
      <t:dgToolBar title="收款"  icon="fa fa-rmb" operationCode="paysubmit" url="emkMOutStorageController.do?goPay&winTitle=销售开单付款" funname="update" height="200" width="400"></t:dgToolBar>
      <t:dgToolBar title="发货"  icon="fa fa-check-square-o" operationCode="recsubmit" url="emkMOutStorageController.do?goRecevie&winTitle=销售开单发货" funname="update" height="200" width="400"></t:dgToolBar>

      <%--<t:dgToolBar title="审核" icon="fa fa-plus" funname="doSubmitV" height="600" width="1000"></t:dgToolBar>--%>
      <t:dgToolBar title="删除" operationCode="delete"  icon="fa fa-remove" url="emkMOutStorageController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
      <t:dgToolBar title="表头列"  icon="fa fa-gear" funname="setHeader"></t:dgToolBar>
      <t:dgToolBar title="列配置"  icon="fa fa-gear" funname="setCommon"></t:dgToolBar>

  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/emk/bound/moutstorage/emkMOutStorageList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
    /* $("#emkMOutStorageListtb").find("input[name='outDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
     $("#emkMOutStorageListtb").find("input[name='outDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});*/
 });
 function setHeader(){
     $.dialog({
         content: 'url:hmCategoryController.do?for&forColumn&parentCode=a01a07',
         zIndex: getzIndex(),
         title : '表头列配置',
         cache:false,
         lock : true,
         width: 500,
         height: 580,
         cancelVal: '关闭',
         cancel: function(){
             location.reload();
         },
     });
 }
 function setCommon(){
     $.dialog({
         content: 'url:hmCategoryController.do?for&forColumn&parentCode=A01A11',
         zIndex: getzIndex(),
         title : '明细列配置',
         cache:false,
         lock : true,
         width: 500,
         height: 580,
         cancelVal: '关闭',
         cancel: function(){
             location.reload();
         },
     });
 }
 function formatColor(val,row){
     if(row.state=="1"){
         return '<span style="color:	#0000FF;">已审核</span>';
     }else if(row.state=="0"){
         return '<span style="color:	#FF0000;">未审核</span>';
     }
 }
 function formatColor2(val,row){
     if(row.payState=="1"){
         return '<span style="color:	#0000FF;">已付款</span>';
     }else if(row.payState=="0"){
         return '<span style="color:	#FF0000;">未付款</span>';
     }
 }
 function formatColor3(val,row){
     if(row.recevieState=="1"){
         return '<span style="color:	#0000FF;">已发货</span>';
     }else if(row.recevieState=="0"){
         return '<span style="color:	#FF0000;">未发货</span>';
     }
 }
 function doSubmitV() {
     var rowsData = $('#emkMOutStorageList').datagrid('getSelections');
     var ids = [];
     if (!rowsData || rowsData.length == 0) {
         tip('请选择需销售开单');
         return;
     }
     for ( var i = 0; i < rowsData.length; i++) {
         ids.push(rowsData[i].id);
     }

     $.dialog.confirm('您是否确定提交销售开单?', function(r) {
         var index = layer.load(1, {
             skin:"layui-layer-sys1",
             shade: [0.1,'#fff'] //0.1透明度的白色背景
         });
         if (r) {
             $.ajax({
                 url : "emkMOutStorageController.do?doApproval&ids="+ids,
                 type : 'post',
                 cache : false,
                 data: null,
                 success : function(data) {
                     layer.close(index);
                     var d = $.parseJSON(data);
                     layer.msg(d.msg);
                     if (d.success) {
                         $('#emkMOutStorageList').datagrid('reload');
                     }

                 }
             });
         }
     });
 }
 function doCancelApproval() {
     var rowsData = $('#emkMOutStorageList').datagrid('getSelections');
     var ids = [];
     if (!rowsData || rowsData.length == 0) {
         tip('请选择需销售开单');
         return;
     }
     for ( var i = 0; i < rowsData.length; i++) {
         ids.push(rowsData[i].id);
     }

     $.dialog.confirm('您是否确定反审核销售开单?', function(r) {
         var index = layer.load(1, {
             skin:"layui-layer-sys1",
             shade: [0.1,'#fff'] //0.1透明度的白色背景
         });
         if (r) {
             $.ajax({
                 url : "emkMOutStorageController.do?doCancelApproval&ids="+ids,
                 type : 'post',
                 cache : false,
                 data: null,
                 success : function(data) {
                     layer.close(index);
                     var d = $.parseJSON(data);
                     layer.msg(d.msg);
                     if (d.success) {
                         $('#emkMOutStorageList').datagrid('reload');
                     }

                 }
             });
         }
     });
 }
 function doPay() {
     var rowsData = $('#emkMOutStorageList').datagrid('getSelections');
     var ids = [];
     if (!rowsData || rowsData.length == 0) {
         tip('请选择需销售开单');
         return;
     }
     for ( var i = 0; i < rowsData.length; i++) {
         ids.push(rowsData[i].id);
     }

     $.dialog.confirm('您是否确定付款销售开单?', function(r) {
         var index = layer.load(1, {
             skin:"layui-layer-sys1",
             shade: [0.1,'#fff'] //0.1透明度的白色背景
         });
         if (r) {
             $.ajax({
                 url : "emkMOutStorageController.do?doPay&ids="+ids,
                 type : 'post',
                 cache : false,
                 data: null,
                 success : function(data) {
                     layer.close(index);
                     var d = $.parseJSON(data);
                     layer.msg(d.msg);
                     if (d.success) {
                         $('#emkMOutStorageList').datagrid('reload');
                     }

                 }
             });
         }
     });
 }
 function printPreview(){
     width = window.top.document.body.offsetWidth*0.85;
     height =window.top.document.body.offsetHeight-100;
     var rowsData = $('#emkMOutStorageList').datagrid('getSelections');
     var ids = [];
     if (!rowsData || rowsData.length == 0) {
         tip('请选择需要打印的销售申请单');
         return;
     }
     openwindow("打印预览",'emkMOutStorageController.do?jump&r=qtPrintSheetFrm&url=emkMOutStorageController.do?printPreview&ckNo='+rowsData[0].ckNo,"hmSalaryList",width,height);
 }
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'emkMOutStorageController.do?upload', "emkMOutStorageList");
}

//导出
function ExportXls() {
	JeecgExcelExport("emkMOutStorageController.do?exportXls&type=2","emkMOutStorageList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("emkMOutStorageController.do?exportXlsByT","emkMOutStorageList");
}

 </script>